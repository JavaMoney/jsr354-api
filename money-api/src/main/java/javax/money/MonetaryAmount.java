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
 * This JSR additionally defines a couple of interoperability rules that each
 * implementation must follow:
 * <ul>
 * <li>Rounding is never done automatically, exception internal rounding implied
 * by the numeric implementation type.</li>
 * <li>Each implementation must at least provide two constructors, one taking
 * {@code (BigDecimal, CurrencyUnit)}, one taking
 * {@code (BigDecimal, CurrencyUnit)}.</li>
 * <li>External rounding must be applied, when accessing the numeric part of an
 * amount, if implied by the required target type. E.g. externalizing to a
 * double may truncate precision. If the target type supports the current
 * scale/precision, it is required that no external rounding is performed.</li>
 * <li>It is required that each implementation supports externalization to
 * {@link BigDecimal}.</li>
 * <li>Since implementations are required to be immutable, an operation must
 * never change any internal state of an instance. Given an instance, all
 * operations are required to be fully reproducible.</li>
 * <li>Finally the result of calling {@link #with(MonetaryAdjuster)} must be of
 * the same type as type on which {@code with} was called. The {@code with}
 * method also defines additional interoperability requirements.</li>
 * </ul>
 * Nevertheless basically an amount provides a functionality similar to
 * {@link BigDecimal}. Also it is required that implementations of this
 * interface are
 * <ul>
 * <li>immutable</li>
 * <li>thread-safe</li>
 * <li>final</li>
 * <li>serializable, hereby writing the numeric representation, e.g.
 * {@link BigDecimal} and a serialized {@link CurrencyUnit}.</li>
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
	 * Gets the amount's currency.
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
	 * 
	 * @return the currency, not null
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
	 * 
	 * @return the currency, not null
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
	 * 
	 * @return the currency, not null
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
	 * Returns an adjusted object of the same type as this object with the
	 * adjustment made.
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
	 * @param adjuster
	 *            the adjuster to use, not null
	 * @return an object of the same type with the specified adjustment made,
	 *         not null
	 */
	public MonetaryAmount with(MonetaryAdjuster adjuster);

}
