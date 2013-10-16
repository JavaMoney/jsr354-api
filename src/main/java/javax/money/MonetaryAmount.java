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

import java.math.BigDecimal;

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
 * <li>Arguments of type {@link Number} should be avoided, since
 * it does not allow to extract its numeric value in a feasible way.</li>
 * <li>If the numeric representation of a {@code MonetaryAmount} exceeds the
 * numeric capabilities of the concrete type {@code T from(MonetaryAmount)}, an
 * implementation should throw an {@code ArithemticOperationException}.</li>
 * <li>On the other hand, when the numeric value can not be mapped into the
 * numeric exchange format defined by this interface, by default also an
 * {@code ArithmeticException} should be thrown. Never should truncation be
 * performed implicitly.</li>
 * <li>Nevertheless truncation may be supported by passing additional parameters
 * or defining <i>exact</i> methods, similar to {@link BigDecimal#longValueExact()}.
 * <li>Rounding should never be done automatically, exception internal rounding
 * implied by the numeric implementation type.</li>
 * <li>Since implementations are recommended to be immutable, an operation
 * should never change any internal state of an instance. Given an instance, all
 * operations are required to be fully reproducible.</li>
 * <li>Finally the result of calling {@link #with(MonetaryAdjuster)} should be
 * of the same type as type on which {@code with} was called. The {@code with}
 * method also defines additional interoperability requirements.</li>
 * <li>To enable interoperability a static method {@code from(MonetaryAmount)}
 * is recommended to be implemented, that allows conversion of a
 * {@code MonetaryAmount} to a concrete type {@code T}:<br/>
 * 
 * <pre>
 * public static T from(MonetaryAmount amount);}
 * </pre>
 * 
 * This is particularly useful when implementing monetary adjusters or queries,
 * since arithmetic operations are not available on the MonetaryAmount
 * interface, which is defined for interoperability only.</li>
 * <li>Finally implementations should not implement a method {@code getAmount()}
 * . This methid is reserved for future integration into the JDK.</li>
 * </ul>
 * <h4>Implementation specification</h4>
 * Implementations of this interface should be
 * <ul>
 * <li>immutable</li>
 * <li>thread-safe</li>
 * <li>final</li>
 * <li>serializable, hereby writing the numeric value and a serialized
 * {@link CurrencyUnit}.</li>
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
 * <li>Additionally for the numeric representation of an amount,
 * <pre>
 * given w = getAmountWhole()
 *       n = getFractionNominator()
 *       d = getFractionDenominator()
 *       
 * the following must be always true:
 * 
 *       !(w<0 && n>0)  and
 *       !(w>0 && n<0)  and
 *       d>0            and
 *       |n| < d        // || = absolute value
 * </pre>
 * </ul>
 * <p>
 * Since {@link Number} is not an interface, this type is not extending
 * {@link Number}.
 * 
 * @see #with(MonetaryAdjuster)
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface MonetaryAmount {

	/**
	 * Returns the amount’s currency, modelled as {@link CurrencyUnit}.
	 * Implementations may co-variantly change the return type to a more
	 * specific implementation of {@link CurrencyUnit} if desired.
	 * 
	 * @return the currency, never {@code null}
	 */
	public CurrencyUnit getCurrency();

	/**
	 * Gets the amount in terms of whole units of the currency.
	 * <p>
	 * An amount is defined to consist of an amount of whole currency units plus
	 * a fraction of the unit. This method returns the amount of whole units,
	 * such as the number of complete US dollars represented.
	 * <p>
	 * For example, the amount of '12 dollars and 25 cents' would return 12 from
	 * this method, as there are 12 whole US dollars in the amount.
	 * <p>
	 * Hereby it is always required that
	 * <ul>
	 * <li>{@code !(amountWhole<0 && fractionNumerator > 0) }
	 * <li>{@code !(amountWhole>0 && fractionNumerator < 0) }
	 * </ul>
	 * 
	 * @return the amount's whole number
	 */
	public long getAmountWhole();

	/**
	 * Gets the numerator of the fractional amount of the currency.
	 * <p>
	 * An amount is defined to consist of an amount of whole currency units plus
	 * a fraction of the unit. This method returns the numerator of the fraction
	 * of the whole currency unit.
	 * <p>
	 * For example, the amount of '12 dollars and 25 cents' would typically
	 * return 25 from this method and 100 from the denominator method.
	 * <p>
	 * Hereby it is always required that
	 * <ul>
	 * <li>{@code fractionNumerator < fractionDenominator}
	 * </ul>
	 * 
	 * @return the fraction numerator
	 */
	public long getAmountFractionNumerator();

	/**
	 * Gets the denominator of the fractional amount of the currency.
	 * <p>
	 * An amount is defined to consist of an amount of whole currency units plus
	 * a fraction of the unit. This method returns the denominator of the
	 * fraction of the whole currency unit.
	 * <p>
	 * For example, the amount of '12 dollars and 25 cents' would typically
	 * return 100 from this method and 25 from the numerator method.
	 * <p>
	 * Hereby it is always required that
	 * <ul>
	 * <li>{@code fractionDenominator > 0}.
	 * <li>{@code fractionDenominator > abs(fractionNominator)}.
	 * <li>it is recommended that the denominator is a power of 10 (1, 10, 100,
	 * 1000,...).
	 * </ul>
	 * 
	 * @return the fraction denominator
	 */
	public long getAmountFractionDenominator();

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
	 * @param adjuster
	 *            the query to invoke, not null
	 * @return the query result, null may be returned (defined by the query)
	 */
	public <R> R query(MonetaryQuery<R> query);

	/**
	 * Returns an adjusted object <b>of the same type</b> as this object with
	 * the adjustment made.
	 * <p>
	 * This adjusts this monetary amount according to the rules of the specified
	 * adjuster. A typical adjuster will change the amount and leave the
	 * currency unchanged. A more complex adjuster might also change the
	 * currency.
	 * <p>
	 * Some example code indicating how and why this method is used:
	 * 
	 * <pre>
	 * money = money.with(amountMultipliedBy(2));
	 * date = date.with(amountRoundedToNearestWholeUnit());
	 * </pre>
	 * 
	 * Hereby also the method signatur on the implementation type must return
	 * the concrete type, to enable a fluent API, e.g.
	 * 
	 * <pre>
	 * public final class MM implements MonetaryAmount{
	 *   ...
	 *   public MM with(MonetaryAdjuster adjuster){
	 *     ... 
	 *   }
	 *   
	 *   ...
	 * }
	 * </pre>
	 * 
	 * @param adjuster
	 *            the adjuster to use, not null
	 * @return an object of the same type with the specified adjustment made,
	 *         not null
	 */
	public MonetaryAmount with(MonetaryAdjuster adjuster);

}
