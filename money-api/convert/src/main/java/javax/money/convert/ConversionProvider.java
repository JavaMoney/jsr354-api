/**
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
package javax.money.convert;

import javax.money.CurrencyUnit;
import javax.money.MonetaryOperator;

/**
 * This interface defines access to the exchange conversion logic of JavaMoney.
 * It is provided by the {@link MonetaryConversions} singleton.
 * 
 * @author Anatole Tresch
 */
public interface ConversionProvider {

	/**
	 * Access the {@link ExchangeRateType} for this {@link ConversionProvider}.
	 * Each instance of {@link ConversionProvider} services conversion data for
	 * exact one {@link ExchangeRateType}.
	 * 
	 * @return the {@link ExchangeRateType}, never {@code null}.
	 */
	public ExchangeRateType getExchangeRateType();

	/**
	 * Checks if an {@link ExchangeRate} between two {@link CurrencyUnit} is
	 * defined.
	 * 
	 * @param type
	 *            the {@link ExchangeRateType} required that this provider
	 *            instance is providing data for.
	 * @param base
	 *            the base {@link CurrencyUnit}
	 * @param term
	 *            the term {@link CurrencyUnit}
	 * @return true, if such an {@link ExchangeRate} is currently defined.
	 */
	public boolean isAvailable(CurrencyUnit base, CurrencyUnit term);

	/**
	 * Checks if an {@link ExchangeRate} between two {@link CurrencyUnit} is
	 * defined.
	 * 
	 * @param base
	 *            the base {@link CurrencyUnit}
	 * @param term
	 *            the term {@link CurrencyUnit}
	 * @param timestamp
	 *            the target timestamp for which the {@link ExchangeRate} is
	 *            queried.
	 * @return {@code true}, if such an {@link ExchangeRate} is currently
	 *         defined.
	 */
	public boolean isAvailable(CurrencyUnit base, CurrencyUnit term,
			long timestamp);

	/**
	 * Get an {@link ConversionRate} for a given timestamp (including historic
	 * rates).
	 * 
	 * @param base
	 *            The base {@link CurrencyUnit}
	 * @param term
	 *            The term {@link CurrencyUnit}
	 * @param timestamp
	 *            the target timestamp for which the {@link ExchangeRate} is
	 *            queried.
	 * @return the matching {@link ExchangeRate}, or {@code null}.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit base,
			CurrencyUnit term, long timestamp);

	/**
	 * Access a {@link ExchangeRate} using the given currencies. The
	 * {@link ExchangeRate} may be, depending on the data provider, eal-time or
	 * deferred.
	 * 
	 * @param base
	 *            base {@link CurrencyUnit}.
	 * @param term
	 *            term {@link CurrencyUnit}.
	 * @return the matching {@link ExchangeRate}, or {@code null}.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term);

	/**
	 * The method reverses the {@link ExchangeRate} to a rate mapping from term
	 * to base {@link CurrencyUnit}. Hereby the factor must <b>not</b> be
	 * recalculated as {@code 1/oldFactor}, since typically reverse rates are
	 * not symmetric in most cases.
	 * 
	 * @return the matching reversed {@link ExchangeRate}, or {@code null}.
	 */
	public ExchangeRate getReversed(ExchangeRate rate);

	/**
	 * Access a {@link CurrencyConverter} that can be applied as a
	 * {@link MonetaryOperator} to an amount.
	 * 
	 * @return a new instance of a corresponding {@link CurrencyConverter},
	 *         never {@code null}.
	 * @throws CurrencyConversionException
	 *             If the required target {@link CurrencyUnit} is not supported.
	 */
	public CurrencyConverter getConverter();

}
