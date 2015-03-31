/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

/**
 * Interface defining a monetary amount. The effective format representation of an amount may vary
 * depending on the implementation used. JSR 354 explicitly supports different types of monetary
 * amounts to be implemented and used. Reason behind is that the requirements to an implementation
 * heavily vary for different usage scenarios. E.g. product calculations may require high precision
 * and scale, whereas low latency order and trading systems require high calculation performance for
 * algorithmic operations.
 * <p>
 * Each instance of an amount provides additional meta-data in form of a {@link MonetaryContext}.
 * This context contains detailed information on the numeric capabilities, e.g. the supported
 * precision and maximal scale, as well as the common implementation flavor.
 *
 * Also a {@link MonetaryAmount} provides a {@link NumberValue}, which allows easily to extract the
 * numeric value, of the amount. And finally {@link #getFactory()} provides a
 * {@link MonetaryAmountFactory}, which allows to of instances of {@link MonetaryAmount} based
 * on the same numeric implementation.
 * <p>
 * This JSR additionally recommends to consider the following aspects:
 * <ul>
 * <li>Arithmetic operations should throw an {@link ArithmeticException}, if performing arithmetic
 * operations between amounts exceeds the capabilities of the numeric representation type used. Any
 * implicit truncating, that would lead to complete invalid and useless results, should be avoided.
 * This recommendation does not affect format rounding, as required by the format numeric
 * representation of a monetary amount.
 * <li>Monetary amounts should allow numbers as argument for arithmetic operations like division and
 * multiplication. Adding or subtracting of amounts must only be possible by passing instances of
 * {@link MonetaryAmount}.</li>
 * <li>Nevertheless numeric truncation is also explicitly supported when calling
 * {@link NumberValue#numberValue(Class)}, whereas the <i>exact</i> counterpart,
 * {@link NumberValue#numberValueExact(Class)}, works similar to
 * {@link java.math.BigDecimal#longValueExact()}.
 * <li>Since implementations are recommended to be immutable, an operation should never change any
 * format state of an instance. Given an instance, all operations are required to be fully
 * reproducible.</li>
 * <li>Finally the result of calling {@link #with(MonetaryOperator)} must be of the same type as
 * type on which {@code with} was called. The {@code with} method also defines additional
 * interoperability requirements that are important to enable this invariant.</li>
 * <li>To enable further interoperability a static method {@code from(MonetaryAmount)} is
 * recommended to be implemented on each implementation class, that allows conversion of a
 * {@code MonetaryAmount} to a concrete instance. E.g.a class {@code MyMoney extends MonetaryAmount}
 * would contain the following method:
 *
 * <blockquote>
 * <p>
 * <pre>
 * public final class MyMoney implements MonetaryAmount{
 *   ...
 *   public static MyMoney from(MonetaryAmount amount)(...)
 * }
 * </pre>
 * <p>
 * </blockquote></li>
 * </ul>
 * <h4>Implementation specification</h4>
 * Implementations of this interface must be
 * <ul>
 * <li>thread-safe</li>
 * </ul>
 * Implementations of this interface should be
 * <ul>
 * <li>final</li>
 * <li>serializable, hereby writing the numeric value, the {@link MonetaryContext} and a serialized
 * {@link CurrencyUnit}.</li>
 * </ul>
 * Implementations of this interface must be
 * <ul>
 * <li>thread-safe</li>
 * <li>immutable</li>
 * <li>comparable</li>
 * <li>must implement {@code equals/hashCode}, hereby considering
 * <ul>
 * <li>Implementation type
 * <li>CurrencyUnit
 * <li>Numeric value.
 * </ul>
 * This also means that two different implementations types with the same currency and numeric value
 * are NOT equal.</li>
 * </ul>
 * <p>
 *
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.8.2
 * @see #with(MonetaryOperator)
 */
public interface MonetaryAmount extends CurrencySupplier, NumberSupplier, Comparable<MonetaryAmount>{

    /**
     * Returns the {@link MonetaryContext} of this {@code MonetaryAmount}. The
     * {@link MonetaryContext} provides additional information about the numeric representation and
     * the numeric capabilities. This information can be used by code to determine situations where
     * {@code MonetaryAmount} instances must be converted to avoid implicit truncation, which can
     * lead to invalid results.
     *
     * @return the {@link MonetaryContext} of this {@code MonetaryAmount}, never {@code null} .
     */
    MonetaryContext getContext();

    /**
     * Queries this monetary amount for a value.
     * <p>
     * This queries this amount using the specified query strategy object.
     * <p>
     * Implementations must ensure that no observable state is altered when this read-only method is
     * invoked.
     *
     * @param <R>   the type of the result
     * @param query the query to invoke, not null
     * @return the query result, null may be returned (defined by the query)
     */
    default <R> R query(MonetaryQuery<R> query){
        return query.queryFrom(this);
    }

    /**
     * Returns an operated object <b>of the same type</b> as this object with the operation made.
     * Hereby returning an instannce <b>of the same type</b> is very important to prevent
     * uncontrolled mixup of implementations. Switching between implementations is still easily
     * possible, e.g. by using according {@link MonetaryAmountFactory} instances: <blockquote>
     * <p>
     * <pre>
     * // converting from Money to MyMoney
     * Money m = ...;
     * MonetartyAmountFactory<MyMoney> f = Monetary.queryAmountFactory(MyMoney.class);
     * MyMoney myMoney = f.setAmount(m).of();
     * </blockquote>
     * </pre>
     * <p>
     * This converts this monetary amount according to the rules of the specified operator. A
     * typical operator will change the amount and leave the currency unchanged. A more complex
     * operator might also change the currency.
     * <p>
     * Some example code indicating how and why this method is used:
     * <p>
     * <blockquote>
     * <p>
     * <pre>
     * MonetaryAmount money = money.with(amountMultipliedBy(2));
     * money = money.with(amountRoundedToNearestWholeUnit());
     * </pre>
     * <p>
     * </blockquote>
     * <p>
     * Hereby also the method signature on the implementation type must return the concrete type, to
     * enable a fluent API, e.g.
     * <p>
     * <blockquote>
     * <p>
     * <pre>
     * public final class MyMoney implements MonetaryAmount{
     *   ...
     *   public MyMoney with(MonetaryOperator operator){
     *     ...
     *   }
     *
     *   ...
     * }
     * </pre>
     * <p>
     * </blockquote>
     *
     * @param operator the operator to use, not null
     * @return an object of the same type with the specified conversion made, not null
     */
    default MonetaryAmount with(MonetaryOperator operator){
        return operator.apply(this);
    }

    /**
     * Creates a new {@code MonetaryAmountFactory}, returning the same implementation type Hereby
     * this given amount is used as a template, so reusing the {@link CurrencyUnit}, its numeric
     * value, the algorithmic implementation as well as the current {@link MonetaryContext}.
     * <p>
     * This method is used for creating a new amount result after having done calculations that are
     * not directly mappable to the default monetary arithmetics, e.g. currency conversion.
     *
     * @return the new {@code MonetaryAmountFactory} with the given {@link MonetaryAmount} as its
     * default values.
     */
    MonetaryAmountFactory<? extends MonetaryAmount> getFactory();

    /**
     * Compares two instances of {@link MonetaryAmount}, hereby ignoring non significant trailing
     * zeroes and different numeric capabilities.
     *
     * @param amount the {@code MonetaryAmount} to be compared with this instance.
     * @return {@code true} if {@code amount > this}.
     * @throws MonetaryException if the amount's currency is not equals to the currency of this instance.
     */
    boolean isGreaterThan(MonetaryAmount amount);

    /**
     * Compares two instances of {@link MonetaryAmount}, hereby ignoring non significant trailing
     * zeroes and different numeric capabilities.
     *
     * @param amount the {@link MonetaryAmount} to be compared with this instance.
     * @return {@code true} if {@code amount >= this}.
     * @throws MonetaryException if the amount's currency is not equals to the currency of this instance.
     */
    boolean isGreaterThanOrEqualTo(MonetaryAmount amount);

    /**
     * Compares two instances of {@link MonetaryAmount}, hereby ignoring non significant trailing
     * zeroes and different numeric capabilities.
     *
     * @param amount the {@link MonetaryAmount} to be compared with this instance.
     * @return {@code true} if {@code amount < this}.
     * @throws MonetaryException if the amount's currency is not equals to the currency of this instance.
     */
    boolean isLessThan(MonetaryAmount amount);

    /**
     * Compares two instances of {@link MonetaryAmount}, hereby ignoring non significant trailing
     * zeroes and different numeric capabilities.
     *
     * @param amt the {@link MonetaryAmount} to be compared with this instance.
     * @return {@code true} if {@code amount <= this}.
     * @throws MonetaryException if the amount's currency is not equals to the currency of this instance.
     */
    boolean isLessThanOrEqualTo(MonetaryAmount amt);

    /**
     * Compares two instances of {@link MonetaryAmount}, hereby ignoring non significant trailing
     * zeroes and different numeric capabilities.
     *
     * @param amount the {@link MonetaryAmount} to be compared with this instance.
     * @return {@code true} if {@code amount == this}.
     * @throws MonetaryException if the amount's currency is not equals to the currency of this instance.
     */
    boolean isEqualTo(MonetaryAmount amount);

    /**
     * Checks if a {@code MonetaryAmount} is negative.
     *
     * @return {@code true} if {@link #signum()} < 0.
     */
    default boolean isNegative(){
        return signum() < 0;
    }

    /**
     * Checks if a {@code MonetaryAmount} is negative or zero.
     *
     * @return {@code true} if {@link #signum()} <= 0.
     */
    default boolean isNegativeOrZero(){
        return signum() <= 0;
    }

    /**
     * Checks if a {@code MonetaryAmount} is positive.
     *
     * @return {@code true} if {@link #signum()} > 0.
     */
    default boolean isPositive(){
        return signum() > 0;
    }

    /**
     * Checks if a {@code MonetaryAmount} is positive or zero.
     *
     * @return {@code true} if {@link #signum()} >= 0.
     */
    default boolean isPositiveOrZero(){
        return signum() >= 0;
    }

    /**
     * Checks if an {@code MonetaryAmount} is zero.
     *
     * @return {@code true} if {@link #signum()} == 0.
     */
    default boolean isZero(){
        return signum() == 0;
    }

    /**
     * Returns the signum function of this {@code MonetaryAmount}.
     *
     * @return -1, 0, or 1 as the value of this {@code MonetaryAmount} is negative, zero, or
     * positive.
     */
    int signum();

    /**
     * Returns a {@code MonetaryAmount} whose value is <code>this + amount</code>, and whose scale is <code>max(this.scale(),
     * amount.scale()</code>.
     *
     * @param amount value to be added to this {@code MonetaryAmount}.
     * @return {@code this + amount}
     * @throws ArithmeticException if the result exceeds the numeric capabilities of this implementation class, i.e.
     *                             the {@link MonetaryContext} cannot be adapted as required.
     */
    MonetaryAmount add(MonetaryAmount amount);

    /**
     * Returns a {@code MonetaryAmount} whose value is <code>this -
     * amount</code>, and whose scale is <code>max(this.scale(),
     * subtrahend.scale()</code>.
     *
     * @param amount value to be subtracted from this {@code MonetaryAmount}.
     * @return {@code this - amount}
     * @throws ArithmeticException if the result exceeds the numeric capabilities of this implementation class, i.e.
     *                             the {@link MonetaryContext} cannot be adapted as required.
     */
    MonetaryAmount subtract(MonetaryAmount amount);

    /**
     * Returns a {@code MonetaryAmount} whose value is <tt>(this &times;
     * multiplicand)</tt>, and whose scale is <code>this.scale() +
     * multiplicand.scale()</code>.
     *
     * @param multiplicand value to be multiplied by this {@code MonetaryAmount}.
     * @return {@code this * multiplicand}
     * @throws ArithmeticException if the result exceeds the numeric capabilities of this implementation class, i.e.
     *                             the {@link MonetaryContext} cannot be adapted as required.
     */
    MonetaryAmount multiply(long multiplicand);

    /**
     * Returns a {@code MonetaryAmount} whose value is <tt>(this &times;
     * multiplicand)</tt>, and whose scale is <code>this.scale() +
     * multiplicand.scale()</code>.
     * By default the input value's scale will be rounded to
     * accommodate the format capabilities, and no {@link java.lang.ArithmeticException}
     * is thrown if the input number's scale exceeds the capabilities.
     *
     * @param multiplicand value to be multiplied by this {@code MonetaryAmount}. If the multiplicand's scale exceeds
     *                     the
     *                     capabilities of the implementation, it may be rounded implicitly.
     * @return {@code this * multiplicand}
     * @throws ArithmeticException if the result exceeds the numeric capabilities of this implementation class, i.e.
     *                             the {@link MonetaryContext} cannot be adapted as required.
     */
    MonetaryAmount multiply(double multiplicand);


    /**
     * Returns a {@code MonetaryAmount} whose value is <tt>(this &times;
     * multiplicand)</tt>, and whose scale is <code>this.scale() +
     * multiplicand.scale()</code>.
     *
     * @param multiplicand value to be multiplied by this {@code MonetaryAmount}. If the multiplicand's scale exceeds
     *                     the
     *                     capabilities of the implementation, it may be rounded implicitly.
     * @return {@code this * multiplicand}
     * @throws ArithmeticException if the result exceeds the numeric capabilities of this implementation class, i.e.
     *                             the {@link MonetaryContext} cannot be adapted as required.
     */
    MonetaryAmount multiply(Number multiplicand);

    /**
     * Returns a {@code MonetaryAmount} whose value is <code>this /
     * divisor</code>, and whose preferred scale is <code>this.scale() -
     * divisor.scale()</code>; if the exact quotient cannot be represented an {@code ArithmeticException}
     * is thrown.
     *
     * @param divisor value by which this {@code MonetaryAmount} is to be divided.
     * @return {@code this / divisor}
     * @throws ArithmeticException if the exact quotient does not have a terminating decimal expansion, or if the
     *                             result exceeds the numeric capabilities of this implementation class, i.e. the
     *                             {@link MonetaryContext} cannot be adapted as required.
     */
    MonetaryAmount divide(long divisor);

    /**
     * Returns a {@code MonetaryAmount} whose value is <code>this /
     * divisor</code>, and whose preferred scale is <code>this.scale() -
     * divisor.scale()</code>; if the exact quotient cannot be represented an {@code ArithmeticException}
     * is thrown.
     *
     * @param divisor value by which this {@code MonetaryAmount} is to be divided.
     * @return {@code this / divisor}
     * @throws ArithmeticException if the exact quotient does not have a terminating decimal expansion, or if the
     *                             result exceeds the numeric capabilities of this implementation class, i.e. the
     *                             {@link MonetaryContext} cannot be adapted as required.
     */
    MonetaryAmount divide(double divisor);

    /**
     * Returns a {@code MonetaryAmount} whose value is <code>this /
     * divisor</code>, and whose preferred scale is <code>this.scale() -
     * divisor.scale()</code>; if the exact quotient cannot be represented an {@code ArithmeticException}
     * is thrown.
     *
     * @param divisor value by which this {@code MonetaryAmount} is to be divided.
     * @return {@code this / divisor}
     * @throws ArithmeticException if the exact quotient does not have a terminating decimal expansion, or if the
     *                             result exceeds the numeric capabilities of this implementation class, i.e. the
     *                             {@link MonetaryContext} cannot be adapted as required.
     */
    MonetaryAmount divide(Number divisor);

    /**
     * Returns a {@code MonetaryAmount} whose value is <code>this % divisor</code>.
     * <p>
     * <p>
     * The remainder is given by
     * <code>this.subtract(this.divideToIntegralValue(divisor).multiply(divisor)</code> . Note that this
     * is not the modulo operation (the result can be negative).
     *
     * @param divisor value by which this {@code MonetaryAmount} is to be divided.
     * @return {@code this % divisor}.
     * @throws ArithmeticException if {@code divisor==0}, or if the result exceeds the numeric capabilities of this
     *                             implementation class, i.e. the {@link MonetaryContext} cannot be adapted as
     *                             required.
     */
    MonetaryAmount remainder(long divisor);

    /**
     * Returns a {@code MonetaryAmount} whose value is <code>this % divisor</code>.
     * <p>
     * <p>
     * The remainder is given by
     * <code>this.subtract(this.divideToIntegralValue(divisor).multiply(divisor)</code> . Note that this
     * is not the modulo operation (the result can be negative).
     *
     * @param divisor value by which this {@code MonetaryAmount} is to be divided.
     * @return {@code this % divisor}.
     * @throws ArithmeticException if {@code divisor==0}, or if the result exceeds the numeric capabilities of this
     *                             implementation class, i.e. the {@link MonetaryContext} cannot be adapted as
     *                             required.
     */
    MonetaryAmount remainder(double divisor);

    /**
     * Returns a {@code MonetaryAmount} whose value is <code>this % divisor</code>.
     * <p>
     * <p>
     * The remainder is given by
     * <code>this.subtract(this.divideToIntegralValue(divisor).multiply(divisor)</code> . Note that this
     * is not the modulo operation (the result can be negative).
     *
     * @param divisor value by which this {@code MonetaryAmount} is to be divided.
     * @return {@code this % divisor}.
     * @throws ArithmeticException if {@code divisor==0}, or if the result exceeds the numeric capabilities of this
     *                             implementation class, i.e. the {@link MonetaryContext} cannot be adapted as
     *                             required.
     */
    MonetaryAmount remainder(Number divisor);

    /**
     * Returns a two-element {@code MonetaryAmount} array containing the result of
     * {@code divideToIntegralValue} followed by the result of {@code remainder} on the two
     * operands.
     * <p>
     * <p>
     * Note that if both the integer quotient and remainder are needed, this method is faster than
     * using the {@code divideToIntegralValue} and {@code remainder} methods separately because the
     * division need only be carried out once.
     *
     * @param divisor value by which this {@code MonetaryAmount} is to be divided, and the remainder
     *                computed.
     * @return a two element {@code MonetaryAmount} array: the quotient (the result of
     * {@code divideToIntegralValue}) is the initial element and the remainder is the final
     * element.
     * @throws ArithmeticException if {@code divisor==0}, or if the result exceeds the numeric capabilities of this
     *                             implementation class, i.e. the {@link MonetaryContext} cannot be adapted as
     *                             required.
     * @see #divideToIntegralValue(long)
     * @see #remainder(long)
     */
    MonetaryAmount[] divideAndRemainder(long divisor);

    /**
     * Returns a two-element {@code MonetaryAmount} array containing the result of
     * {@code divideToIntegralValue} followed by the result of {@code remainder} on the two
     * operands.
     * <p>
     * <p>
     * Note that if both the integer quotient and remainder are needed, this method is faster than
     * using the {@code divideToIntegralValue} and {@code remainder} methods separately because the
     * division need only be carried out once.
     *
     * @param divisor value by which this {@code MonetaryAmount} is to be divided, and the remainder
     *                computed.
     * @return a two element {@code MonetaryAmount} array: the quotient (the result of
     * {@code divideToIntegralValue}) is the initial element and the remainder is the final
     * element.
     * @throws ArithmeticException if {@code divisor==0}, or if the result exceeds the numeric capabilities of this
     *                             implementation class, i.e. the {@link MonetaryContext} cannot be adapted as
     *                             required.
     * @see #divideToIntegralValue(double)
     * @see #remainder(double)
     */
    MonetaryAmount[] divideAndRemainder(double divisor);

    /**
     * Returns a two-element {@code MonetaryAmount} array containing the result of
     * {@code divideToIntegralValue} followed by the result of {@code remainder} on the two
     * operands.
     * <p>
     * <p>
     * Note that if both the integer quotient and remainder are needed, this method is faster than
     * using the {@code divideToIntegralValue} and {@code remainder} methods separately because the
     * division need only be carried out once.
     *
     * @param divisor value by which this {@code MonetaryAmount} is to be divided, and the remainder
     *                computed.
     * @return a two element {@code MonetaryAmount} array: the quotient (the result of
     * {@code divideToIntegralValue}) is the initial element and the remainder is the final
     * element.
     * @throws ArithmeticException if {@code divisor==0}, or if the result exceeds the numeric capabilities of this
     *                             implementation class, i.e. the {@link MonetaryContext} cannot be adapted as
     *                             required.
     * @see #divideToIntegralValue(Number)
     * @see #remainder(Number)
     */
    MonetaryAmount[] divideAndRemainder(Number divisor);

    /**
     * Returns a {@code MonetaryAmount} whose value is the integer part of the quotient
     * <code>this / divisor</code> rounded down. The preferred scale of the result is
     * <code>this.scale() -
     * divisor.scale()</code>.
     *
     * @param divisor value by which this {@code BigDecimal} is to be divided.
     * @return The integer part of {@code this / divisor}.
     * @throws ArithmeticException if {@code divisor==0}
     * @see java.math.BigDecimal#divideToIntegralValue(java.math.BigDecimal)
     */
    MonetaryAmount divideToIntegralValue(long divisor);

    /**
     * Returns a {@code MonetaryAmount} whose value is the integer part of the quotient
     * <code>this / divisor</code> rounded down. The preferred scale of the result is
     * <code>this.scale() - divisor.scale()</code>.
     *
     * @param divisor value by which this {@code BigDecimal} is to be divided.
     * @return The integer part of {@code this / divisor}.
     * @throws ArithmeticException if {@code divisor==0}
     * @see java.math.BigDecimal#divideToIntegralValue(java.math.BigDecimal)
     */
    MonetaryAmount divideToIntegralValue(double divisor);

    /**
     * Returns a {@code MonetaryAmount} whose value is the integer part of the quotient
     * <code>this / divisor</code> rounded down. The preferred scale of the result is
     * <code>this.scale() -
     * divisor.scale()</code>.
     *
     * @param divisor value by which this {@code BigDecimal} is to be divided.
     * @return The integer part of {@code this / divisor}.
     * @throws ArithmeticException if {@code divisor==0}
     * @see java.math.BigDecimal#divideToIntegralValue(java.math.BigDecimal)
     */
    MonetaryAmount divideToIntegralValue(Number divisor);

    /**
     * Returns a {@code MonetaryAmount} whose numerical value is equal to ( {@code this} *
     * 10<sup>n</sup>). The scale of the result is <code>this.scale() - n</code>.
     *
     * @param power the power.
     * @return the calculated amount value.
     * @throws ArithmeticException if the scale would be outside the range of a 32-bit integer, or if the result
     *                             exceeds the numeric capabilities of this implementation class, i.e. the
     *                             {@link MonetaryContext} cannot be adapted as required.
     */
    MonetaryAmount scaleByPowerOfTen(int power);

    /**
     * Returns a {@code MonetaryAmount} whose value is the absolute value of this
     * {@code MonetaryAmount}, and whose scale is {@code this.scale()}.
     *
     * @return <code>abs(this</code>
     */
    MonetaryAmount abs();

    /**
     * Returns a {@code MonetaryAmount} whose value is <code>-this</code>, and whose scale is
     * {@code this.scale()}.
     *
     * @return {@code -this}.
     */
    MonetaryAmount negate();

    /**
     * Returns a {@code MonetaryAmount} whose value is <code>+this</code>, with rounding according to
     * the context settings.
     *
     * @return {@code this}, rounded as necessary. A zero result will have a scale of 0.
     * @throws ArithmeticException if rounding fails.
     * @see java.math.BigDecimal#plus()
     */
    MonetaryAmount plus();

    /**
     * Returns a {@code MonetaryAmount} which is numerically equal to this one but with any trailing
     * zeros removed from the representation. For example, stripping the trailing zeros from the
     * {@code MonetaryAmount} value {@code CHF 600.0}, which has [{@code BigInteger}, {@code scale}]
     * components equals to [6000, 1], yields {@code 6E2} with [ {@code BigInteger}, {@code scale}]
     * components equals to [6, -2]
     *
     * @return a numerically equal {@code MonetaryAmount} with any trailing zeros removed.
     */
    MonetaryAmount stripTrailingZeros();

}
