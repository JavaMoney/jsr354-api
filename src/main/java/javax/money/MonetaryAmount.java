/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

/**
 * Interface defining a monetary amount. The effective internal representation
 * of an amount may vary depending on the implementation used. JSR 354
 * explicitly supports different types of MonetaryAmount to be implemented and
 * used. Reason behind is that the requirements to an implementation heavily
 * vary for different usage scenarios. E.g. product calculations may require
 * high precision and scale, whereas low latency order and trading systems
 * require high calculation performance for algorithmic operations.
 * <p>
 * This JSR additionally recommends to consider the following aspects:
 * <ul>
 * <li>Arithmetic operations should throw an {@link ArithmeticException}, if
 * performing arithmetic operations between amounts exceeds the capabilities of
 * the numeric representation type used. Any implicit truncating, that would
 * lead to complete invalid and useless results, should be avoided. This
 * recommendation does not affect internal rounding, as required by the internal
 * numeric representation of a monetary amount.
 * <li>Monetary amounts should allow numbers as argument for arithmetic
 * operations like division and multiplication additionally to a MonetaryAmount.
 * Adding or subtracting of amounts must only be possible by passing instances
 * of MonetaryAmount.</li>
 * <li>Arguments of type {@link Number} should be avoided, since it does not
 * allow to extract its numeric value in a feasible way.</li>
 * <li>If the numeric representation of a {@code MonetaryAmount} exceeds the
 * numeric capabilities of the concrete type {@code T from(MonetaryAmount)}, an
 * implementation should throw an {@code ArithemticOperationException}.</li>
 * <li>On the other hand, when the numeric value can not be mapped into the
 * numeric exchange format defined by this interface, by default also an
 * {@code ArithmeticException} should be thrown. Never should truncation be
 * performed implicitly.</li>
 * <li>Nevertheless truncation may be supported by passing additional parameters
 * or defining <i>exact</i> methods, similar to
 * {@link BigDecimal#longValueExact()}.
 * <li>Rounding should never be done automatically, exception internal rounding
 * implied by the numeric implementation type.</li>
 * <li>Since implementations are recommended to be immutable, an operation
 * should never change any internal state of an instance. Given an instance, all
 * operations are required to be fully reproducible.</li>
 * <li>Finally the result of calling {@link #with(MonetaryOperator)} should be
 * of the same type as type on which {@code with} was called. The {@code with}
 * method also defines additional interoperability requirements.</li>
 * <li>To enable further interoperability a static method
 * {@code from(MonetaryAmount)} is recommended to be implemented, that allows
 * conversion of a {@code MonetaryAmount} to a concrete type {@code M}:<br/>
 * 
 * <pre>
 * public static M from(MonetaryAmount amount);}
 * </pre>
 * 
 * </li>
 * <li>Finally implementations should not implement a method {@code getAmount()}
 * . This method is reserved for future integration into the JDK.</li>
 * </ul>
 * <h4>Implementation specification</h4>
 * Implementations of this interface should be
 * <ul>
 * <li>immutable</li>
 * <li>thread-safe</li>
 * <li>final</li>
 * <li>serializable, hereby writing the numeric value, the
 * {@link MonetaryContext} and a serialized {@link CurrencyUnit}.</li>
 * </ul>
 * Implementations of this interface must be
 * <ul>
 * <li>comparable</li>
 * <li>must implement {@code equals/hashCode}, hereby considering
 * <ul>
 * <li>Implementation type
 * <li>CurrencyUnit
 * <li>Numeric value.
 * </ul>
 * This also means that two different implementations types with the same
 * currency and numeric value are NOT equal.</li>
 * </ul>
 * <p>
 * Since {@link Number} is not an interface, this type is not extending
 * {@link Number}.
 * 
 * @see #with(MonetaryOperator)
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 * @param <T>
 *            The numeric representation type to be exposed by
 *            {@link #getNumber()}
 * @version 0.8
 */
public interface MonetaryAmount<T> {

	/**
	 * Returns the amount’s currency, modelled as {@link CurrencyUnit}.
	 * Implementations may co-variantly change the return type to a more
	 * specific implementation of {@link CurrencyUnit} if desired.
	 * 
	 * @return the currency, never {@code null}
	 */
	public CurrencyUnit getCurrency();

	/**
	 * Returns the <i>precision</i> of this {@code MonetaryAmount}. (The
	 * precision is the number of digits in the unscaled value.)
	 * 
	 * <p>
	 * The precision of a zero value is 1.
	 * 
	 * @return the precision of this {@code MonetaryAmount}.
	 */
	public int getPrecision();

	/**
	 * Returns the <i>scale</i> of this {@code MonetaryAmount}. If zero or
	 * positive, the scale is the number of digits to the right of the decimal
	 * point. If negative, the unscaled value of the number is multiplied by ten
	 * to the power of the negation of the scale. For example, a scale of
	 * {@code -3} means the unscaled value is multiplied by 1000.
	 * 
	 * @return the scale of this {@code MonetaryAmount}.
	 */
	public int getScale();

	/**
	 * Returns the {@link MonetaryContext} of this {@code MonetaryAmount}. The
	 * {@link MonetaryContext} provides additional information about the numeric
	 * representation and the numeric capabilities. This information can be used
	 * by code to determine situations where {@code MonetaryAmount} instances
	 * must be converted to avoid implicit truncation, which can lead to invalid
	 * results.
	 * 
	 * @return the {@link MonetaryContext} of this {@code MonetaryAmount}, never
	 *         {@code null}.
	 */
	public MonetaryContext getMonetaryContext();

	/**
	 * Access the numeric representation of this amount instance.
	 * 
	 * @return the numeric value of this amount instance.
	 */
	public T getNumber();

	/**
	 * Access the numeric representation of this amount instance as
	 * {@link Number}, if necessary truncate the value so it matches into the
	 * value returned. Hereby all types extending {@link Number} that are
	 * available on a platform must be supported.
	 * 
	 * @param type
	 *            the number type.
	 * @return the numeric value of this amount instance as {@link Number}.
	 */
	public <S extends Number> S getNumber(Class<S> type);

	/**
	 * Access the numeric representation of this amount instance as
	 * {@link Number}. If the value must be truncated, to matches into the value
	 * returned, an {@link ArithmeticException} is throwsn. Hereby all types
	 * extending {@link Number} that are available on a platform must be
	 * supported.
	 * 
	 * @param type
	 *            the number type.
	 * @return the numeric value of this amount instance as {@link Number}.
	 * @throws ArithmeticException
	 *             if the current numeric value exceeds the numeric capabilities
	 *             of the required number type, i.e. the {@code 1.2, or 500}
	 *             should be converted to {@link Byte}.
	 */
	public <S extends Number> S getNumberExact(Class<S> type);

	/**
	 * Queries this monetary amount for a value.
	 * <p>
	 * This queries this amount using the specified query strategy object.
	 * <p>
	 * Implementations must ensure that no observable state is altered when this
	 * read-only method is invoked.
	 * 
	 * @param <R>
	 *            the type of the result
	 * @param query
	 *            the query to invoke, not null
	 * @return the query result, null may be returned (defined by the query)
	 */
	public <R> R query(MonetaryQuery<R> query);

	/**
	 * Returns an adjusted object <b>of the same type</b> as this object with
	 * the adjustment made.
	 * <p>
	 * This adjusts this monetary amount according to the rules of the specified
	 * operator. A typical operator will change the amount and leave the
	 * currency unchanged. A more complex operator might also change the
	 * currency.
	 * <p>
	 * Some example code indicating how and why this method is used:
	 * 
	 * <pre>
	 * money = money.with(amountMultipliedBy(2));
	 * date = date.with(amountRoundedToNearestWholeUnit());
	 * </pre>
	 * 
	 * Hereby also the method signature on the implementation type must return
	 * the concrete type, to enable a fluent API, e.g.
	 * 
	 * <pre>
	 * public final class MM implements MonetaryAmount{
	 *   ...
	 *   public MM with(MonetaryOperator operator){
	 *     ... 
	 *   }
	 *   
	 *   ...
	 * }
	 * </pre>
	 * 
	 * @param operator
	 *            the operator to use, not null
	 * @return an object of the same type with the specified adjustment made,
	 *         not null
	 */
	public MonetaryAmount<T> with(MonetaryOperator operator);

	/**
	 * Creates a new {@link MonetaryAmount}, using the current amount as a
	 * template, reusing the algorithmic implementation, the current
	 * {@link MonetaryContext} and the numeric value.
	 * <p>
	 * This method is used for creating a new amount result after having done
	 * calculations that are not directly mappable to the default monetary
	 * arithmetics, e.g. currency conversion.
	 * 
	 * @param unit
	 *            the new {@link CurrencyUnit} of the amount to be created.
	 * @return the new {@link MonetaryAmount} with the given
	 *         {@link CurrencyUnit}, but the same numeric value and
	 *         {@link MonetaryContext}.
	 */
	public MonetaryAmount<T> with(CurrencyUnit unit);

	/**
	 * Creates a new {@link MonetaryAmount}, using the given amount as a
	 * template, e.g. reusing the algorithmic implementation and the current
	 * {@link MonetaryContext}.
	 * <p>
	 * This method is used for creating a new amount result after having done
	 * calculations that are not directly mappable to the default monetary
	 * arithmetics, e.g. currency conversion.
	 * 
	 * @param unit
	 *            the {@link CurrencyUnit} of the amount to be created.
	 * @param amount
	 *            the numeric value of the amount to be created.
	 * @return the new {@link MonetaryAmount} with the given
	 *         {@link CurrencyUnit} and numeric value.
	 */
	public MonetaryAmount<T> with(CurrencyUnit unit, long amount);

	/**
	 * Creates a new {@link MonetaryAmount}, using the given amount as a
	 * template, e.g. reusing the algorithmic implementation and the current
	 * {@link MonetaryContext}.
	 * <p>
	 * This method is used for creating a new amount result after having done
	 * calculations that are not directly mappable to the default monetary
	 * arithmetics, e.g. currency conversion.
	 * 
	 * @param unit
	 *            the {@link CurrencyUnit} of the amount to be created.
	 * @param amount
	 *            the numeric value of the amount to be created.
	 * @return the new {@link MonetaryAmount} with the given
	 *         {@link CurrencyUnit} and numeric value.
	 */
	public MonetaryAmount<T> with(CurrencyUnit unit, double amount);

	/**
	 * Creates a new {@link MonetaryAmount}, using the given amount as a
	 * template, e.g. reusing the algorithmic implementation and the current
	 * {@link MonetaryContext}.
	 * <p>
	 * This method is used for creating a new amount result after having done
	 * calculations that are not directly mappable to the default monetary
	 * arithmetics, e.g. currency conversion.
	 * 
	 * @param unit
	 *            the {@link CurrencyUnit} of the amount to be created.
	 * @param amount
	 *            the numeric value of the amount to be created.
	 * @return the new {@link MonetaryAmount} with the given
	 *         {@link CurrencyUnit} and numeric value.
	 */
	public MonetaryAmount<T> with(CurrencyUnit unit, Number amount);

	/**
	 * Compares two instances of {@link MonetaryAmount}, hereby ignoring non
	 * significant trailing zeroes and different numeric capabilities.
	 * 
	 * @param amt
	 *            the {@link MonetaryAmount} to be compared with this instance.
	 * @return {@code true} if {@code amount > this}.
	 * @throws MonetaryException
	 *             if the amount's currency is not equals to the currency of
	 *             this instance.
	 */
	public boolean isGreaterThan(MonetaryAmount<?> amount);

	/**
	 * Compares two instances of {@link MonetaryAmount}, hereby ignoring non
	 * significant trailing zeroes and different numeric capabilities.
	 * 
	 * @param amt
	 *            the {@link MonetaryAmount} to be compared with this instance.
	 * @return {@code true} if {@code amount >= this}.
	 * @throws MonetaryException
	 *             if the amount's currency is not equals to the currency of
	 *             this instance.
	 */
	public boolean isGreaterThanOrEqualTo(MonetaryAmount<?> amt);

	/**
	 * Compares two instances of {@link MonetaryAmount}, hereby ignoring non
	 * significant trailing zeroes and different numeric capabilities.
	 * 
	 * @param amt
	 *            the {@link MonetaryAmount} to be compared with this instance.
	 * @return {@code true} if {@code amount < this}.
	 * @throws MonetaryException
	 *             if the amount's currency is not equals to the currency of
	 *             this instance.
	 */
	public boolean isLessThan(MonetaryAmount<?> amt);

	/**
	 * Compares two instances of {@link MonetaryAmount}, hereby ignoring non
	 * significant trailing zeroes and different numeric capabilities.
	 * 
	 * @param amt
	 *            the {@link MonetaryAmount} to be compared with this instance.
	 * @return {@code true} if {@code amount <= this}.
	 * @throws MonetaryException
	 *             if the amount's currency is not equals to the currency of
	 *             this instance.
	 */
	public boolean isLessThanOrEqualTo(MonetaryAmount<?> amt);

	/**
	 * Compares two instances of {@link MonetaryAmount}, hereby ignoring non
	 * significant trailing zeroes and different numeric capabilities.
	 * 
	 * @param amt
	 *            the {@link MonetaryAmount} to be compared with this instance.
	 * @return {@code true} if {@code amount == this}.
	 * @throws MonetaryException
	 *             if the amount's currency is not equals to the currency of
	 *             this instance.
	 */
	public boolean isEqualTo(MonetaryAmount<?> amount);

	/**
	 * Checks if a {@link MonetaryAmount} is negative.
	 * 
	 * @return {@code true} if {@link #signum()} < 0.
	 */
	public boolean isNegative();

	/**
	 * Checks if a {@link MonetaryAmount} is negative or zero.
	 * 
	 * @return {@code true} if {@link #signum()} <= 0.
	 */
	public boolean isNegativeOrZero();

	/**
	 * Checks if a {@link MonetaryAmount} is positive.
	 * 
	 * @return {@code true} if {@link #signum()} > 0.
	 */
	public boolean isPositive();

	/**
	 * Checks if a {@link MonetaryAmount} is positive or zero.
	 * 
	 * @return {@code true} if {@link #signum()} >= 0.
	 */
	public boolean isPositiveOrZero();

	/**
	 * Checks if an {@link MonetaryAmount} is zero.
	 * 
	 * @return {@code true} if {@link #signum()} == 0.
	 */
	public boolean isZero();

	/**
	 * Returns the signum function of this {@code MonetaryAmount}.
	 * 
	 * @return -1, 0, or 1 as the value of this {@code MonetaryAmount} is
	 *         negative, zero, or positive.
	 */
	public int signum();

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this +
	 * amount)}, and whose scale is {@code max(this.scale(),
	 * amount.scale())}.
	 * 
	 * @param amount
	 *            value to be added to this {@code MonetaryAmount}.
	 * @return {@code this + amount}
	 * @throws ArithmeticException
	 *             if the result exceeds the numeric capabilities of this
	 *             implementation class, i.e. the {@link MonetaryContext} cannot
	 *             be adapted as required.
	 */
	public MonetaryAmount<T> add(MonetaryAmount<?> amount);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this -
	 * amount)}, and whose scale is {@code max(this.scale(),
	 * subtrahend.scale())}.
	 * 
	 * @param amount
	 *            value to be subtracted from this {@code MonetaryAmount}.
	 * @return {@code this - amount}
	 * @throws ArithmeticException
	 *             if the result exceeds the numeric capabilities of this
	 *             implementation class, i.e. the {@link MonetaryContext} cannot
	 *             be adapted as required.
	 */
	public MonetaryAmount<T> subtract(MonetaryAmount<?> amount);

	/**
	 * Returns a {@code MonetaryAmount} whose value is <tt>(this &times;
	 * multiplicand)</tt>, and whose scale is {@code (this.scale() +
	 * multiplicand.scale())}.
	 * 
	 * @param multiplicand
	 *            value to be multiplied by this {@code MonetaryAmount}.
	 * @return {@code this * multiplicand}
	 * @throws ArithmeticException
	 *             if the result exceeds the numeric capabilities of this
	 *             implementation class, i.e. the {@link MonetaryContext} cannot
	 *             be adapted as required.
	 */
	public MonetaryAmount<T> multiply(long multiplicand);

	/**
	 * Returns a {@code MonetaryAmount} whose value is <tt>(this &times;
	 * multiplicand)</tt>, and whose scale is {@code (this.scale() +
	 * multiplicand.scale())}.
	 * 
	 * @param multiplicand
	 *            value to be multiplied by this {@code MonetaryAmount}.
	 * @return {@code this * multiplicand}
	 * @throws ArithmeticException
	 *             if the result exceeds the numeric capabilities of this
	 *             implementation class, i.e. the {@link MonetaryContext} cannot
	 *             be adapted as required.
	 */
	public MonetaryAmount<T> multiply(double multiplicand);

	/**
	 * Returns a {@code MonetaryAmount} whose value is <tt>(this &times;
	 * multiplicand)</tt>, and whose scale is {@code (this.scale() +
	 * multiplicand.scale())}.
	 * 
	 * @param multiplicand
	 *            value to be multiplied by this {@code MonetaryAmount}.
	 * @return {@code this * multiplicand}
	 * @throws ArithmeticException
	 *             if the result exceeds the numeric capabilities of this
	 *             implementation class, i.e. the {@link MonetaryContext} cannot
	 *             be adapted as required.
	 */
	public MonetaryAmount<T> multiply(Number multiplicand);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this /
	 * divisor)}, and whose preferred scale is {@code (this.scale() -
	 * divisor.scale())}; if the exact quotient cannot be represented an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided.
	 * @throws ArithmeticException
	 *             if the exact quotient does not have a terminating decimal
	 *             expansion, or if the result exceeds the numeric capabilities
	 *             of this implementation class, i.e. the
	 *             {@link MonetaryContext} cannot be adapted as required.
	 * @return {@code this / divisor}
	 */
	public MonetaryAmount<T> divide(long amount);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this /
	 * divisor)}, and whose preferred scale is {@code (this.scale() -
	 * divisor.scale())}; if the exact quotient cannot be represented an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided.
	 * @throws ArithmeticException
	 *             if the exact quotient does not have a terminating decimal
	 *             expansion, or if the result exceeds the numeric capabilities
	 *             of this implementation class, i.e. the
	 *             {@link MonetaryContext} cannot be adapted as required.
	 * @return {@code this / divisor}
	 */
	public MonetaryAmount<T> divide(double amount);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this /
	 * divisor)}, and whose preferred scale is {@code (this.scale() -
	 * divisor.scale())}; if the exact quotient cannot be represented an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided.
	 * @throws ArithmeticException
	 *             if the exact quotient does not have a terminating decimal
	 *             expansion, or if the result exceeds the numeric capabilities
	 *             of this implementation class, i.e. the
	 *             {@link MonetaryContext} cannot be adapted as required.
	 * @return {@code this / divisor}
	 */
	public MonetaryAmount<T> divide(Number amount);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this % divisor)}.
	 * 
	 * <p>
	 * The remainder is given by
	 * {@code this.subtract(this.divideToIntegralValue(divisor).multiply(divisor))}
	 * . Note that this is not the modulo operation (the result can be
	 * negative).
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided.
	 * @return {@code this % divisor}.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}, or if the result exceeds the numeric
	 *             capabilities of this implementation class, i.e. the
	 *             {@link MonetaryContext} cannot be adapted as required.
	 */
	public MonetaryAmount<T> remainder(long amount);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this % divisor)}.
	 * 
	 * <p>
	 * The remainder is given by
	 * {@code this.subtract(this.divideToIntegralValue(divisor).multiply(divisor))}
	 * . Note that this is not the modulo operation (the result can be
	 * negative).
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided.
	 * @return {@code this % divisor}.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}, or if the result exceeds the numeric
	 *             capabilities of this implementation class, i.e. the
	 *             {@link MonetaryContext} cannot be adapted as required.
	 */
	public MonetaryAmount<T> remainder(double amount);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this % divisor)}.
	 * 
	 * <p>
	 * The remainder is given by
	 * {@code this.subtract(this.divideToIntegralValue(divisor).multiply(divisor))}
	 * . Note that this is not the modulo operation (the result can be
	 * negative).
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided.
	 * @return {@code this % divisor}.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}, or if the result exceeds the numeric
	 *             capabilities of this implementation class, i.e. the
	 *             {@link MonetaryContext} cannot be adapted as required.
	 */
	public MonetaryAmount<T> remainder(Number amount);

	/**
	 * Returns a two-element {@code MonetaryAmount} array containing the result
	 * of {@code divideToIntegralValue} followed by the result of
	 * {@code remainder} on the two operands.
	 * 
	 * <p>
	 * Note that if both the integer quotient and remainder are needed, this
	 * method is faster than using the {@code divideToIntegralValue} and
	 * {@code remainder} methods separately because the division need only be
	 * carried out once.
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided,
	 *            and the remainder computed.
	 * @return a two element {@code MonetaryAmount} array: the quotient (the
	 *         result of {@code divideToIntegralValue}) is the initial element
	 *         and the remainder is the final element.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}, or if the result exceeds the numeric
	 *             capabilities of this implementation class, i.e. the
	 *             {@link MonetaryContext} cannot be adapted as required.
	 * @see #divideToIntegralValue(long)
	 * @see #remainder(long)
	 */
	public MonetaryAmount<T>[] divideAndRemainder(long amount);

	/**
	 * Returns a two-element {@code MonetaryAmount} array containing the result
	 * of {@code divideToIntegralValue} followed by the result of
	 * {@code remainder} on the two operands.
	 * 
	 * <p>
	 * Note that if both the integer quotient and remainder are needed, this
	 * method is faster than using the {@code divideToIntegralValue} and
	 * {@code remainder} methods separately because the division need only be
	 * carried out once.
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided,
	 *            and the remainder computed.
	 * @return a two element {@code MonetaryAmount} array: the quotient (the
	 *         result of {@code divideToIntegralValue}) is the initial element
	 *         and the remainder is the final element.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}, or if the result exceeds the numeric
	 *             capabilities of this implementation class, i.e. the
	 *             {@link MonetaryContext} cannot be adapted as required.
	 * @see #divideToIntegralValue(double)
	 * @see #remainder(double)
	 */
	public MonetaryAmount<T>[] divideAndRemainder(double amount);

	/**
	 * Returns a two-element {@code MonetaryAmount} array containing the result
	 * of {@code divideToIntegralValue} followed by the result of
	 * {@code remainder} on the two operands.
	 * 
	 * <p>
	 * Note that if both the integer quotient and remainder are needed, this
	 * method is faster than using the {@code divideToIntegralValue} and
	 * {@code remainder} methods separately because the division need only be
	 * carried out once.
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided,
	 *            and the remainder computed.
	 * @return a two element {@code MonetaryAmount} array: the quotient (the
	 *         result of {@code divideToIntegralValue}) is the initial element
	 *         and the remainder is the final element.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}, or if the result exceeds the numeric
	 *             capabilities of this implementation class, i.e. the
	 *             {@link MonetaryContext} cannot be adapted as required.
	 * @see #divideToIntegralValue(Number)
	 * @see #remainder(Number)
	 */
	public MonetaryAmount<T>[] divideAndRemainder(Number amount);

	public MonetaryAmount<T> divideToIntegralValue(long divisor);

	public MonetaryAmount<T> divideToIntegralValue(double divisor);

	public MonetaryAmount<T> divideToIntegralValue(Number divisor);

	/**
	 * Returns a {@link MonetaryAmount} whose numerical value is equal to (
	 * {@code this} * 10<sup>n</sup>). The scale of the result is
	 * {@code (this.scale() - n)}.
	 * 
	 * @throws ArithmeticException
	 *             if the scale would be outside the range of a 32-bit integer,
	 *             or if the result exceeds the numeric capabilities of this
	 *             implementation class, i.e. the {@link MonetaryContext} cannot
	 *             be adapted as required.
	 */
	public MonetaryAmount<T> scaleByPowerOfTen(int power);

	/**
	 * Returns a {@code MonetaryAmount} whose value is the absolute value of
	 * this {@code MonetaryAmount}, and whose scale is {@code this.scale()}.
	 * 
	 * @return {@code abs(this)}
	 */
	public MonetaryAmount<T> abs();

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (-this)}, and
	 * whose scale is {@code this.scale()}.
	 * 
	 * @return {@code -this}.
	 */
	public MonetaryAmount<T> negate();

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (+this)}, with
	 * rounding according to the context settings.
	 * 
	 * @see BigDecimal#plus()
	 * 
	 * @return {@code this}, rounded as necessary. A zero result will have a
	 *         scale of 0.
	 * @throws ArithmeticException
	 *             if rounding fails.
	 */
	public MonetaryAmount<T> plus();

	/**
	 * Returns a {@code MonetaryAmount} whose value is
	 * <tt>(this<sup>n</sup>)</tt>, The power is computed exactly, to unlimited
	 * precision.
	 * 
	 * <p>
	 * The parameter {@code n} must be in the range 0 through 999999999,
	 * inclusive. {@code 0.pow(0)} returns {@code 1}.
	 * 
	 * @param n
	 *            power to raise this {@code MonetaryAmount} to.
	 * @return <tt>this<sup>n</sup></tt>
	 * @throws ArithmeticException
	 *             if {@code n} is out of range, or if the result exceeds the
	 *             numeric capabilities of this implementation class, i.e. the
	 *             {@link MonetaryContext} cannot be adapted as required.
	 */
	public MonetaryAmount<T> pow(int power);

	/**
	 * Returns a {@code MonetaryAmount} which is numerically equal to this one
	 * but with any trailing zeros removed from the representation. For example,
	 * stripping the trailing zeros from the {@code MonetaryAmount} value
	 * {@code CHF 600.0}, which has [{@code BigInteger}, {@code scale}]
	 * components equals to [6000, 1], yields {@code 6E2} with [
	 * {@code BigInteger}, {@code scale}] components equals to [6, -2]
	 * 
	 * @return a numerically equal {@code BigDecimal} with any trailing zeros
	 *         removed.
	 */
	public MonetaryAmount<T> stripTrailingZeros();

}
