/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.convert;

import javax.money.CurrencyUnit;
import javax.money.MonetaryOperator;

/**
 * This interface defines access to the exchange conversion logic of JavaMoney.
 * It is provided by the Money singleton. It is provided by the Money singleton.
 * 
 * @author Anatole Tresch
 */
public interface ConversionProvider {

	/**
	 * Access the {@link ExchangeRateType} for this {@link ConversionProvider}
	 * .
	 * 
	 * @return the {@link ExchangeRateType}, never null.
	 */
	public ExchangeRateType getExchangeRateType();

	/**
	 * Checks if an exchange of a currency is defined.
	 * 
	 * @param type
	 *            the exchange rate type required that this provider instance is
	 *            providing data for.
	 * @param base
	 *            the base currency
	 * @param term
	 *            the term currency
	 * @return true, if such an exchange is currently defined.
	 */
	public boolean isAvailable(CurrencyUnit base, CurrencyUnit term);

	/**
	 * Checks if an exchange of a currency is defined.
	 * 
	 * @param base
	 *            the base currency
	 * @param term
	 *            the term currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried.
	 * @return true, if such an exchange is currently defined.
	 */
	public boolean isAvailable(CurrencyUnit base, CurrencyUnit term,
			long timestamp);

	/**
	 * Get an {@link ConversionRate} for a given timestamp (including historic
	 * rates).
	 * 
	 * @param base
	 *            The base currency
	 * @param term
	 *            The term currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried.
	 * @return the matching {@link ExchangeRate}, or {@code null}.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit base,
			CurrencyUnit term, long timestamp);

	/**
	 * Access a exchange rate using the given currencies. The rate may be,
	 * depending on the data provider, be real-time or deferred.
	 * 
	 * @param base
	 *            base currency.
	 * @param term
	 *            term currency.
	 * @return the matching {@link ExchangeRate}, or {@code null}.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term);

	/**
	 * The method reverses the exchange rate to a rate mapping from target to
	 * source. Hereby the factor must <b>not</b> be recalculated as
	 * {@code 1/oldFactor}, since typically reverse rates are not symmetric in
	 * most cases.
	 * 
	 * @return the matching reversed {@link ExchangeRate}, or {@code null}.
	 */
	public ExchangeRate getReversed(ExchangeRate rate);

	/**
	 * Access a {@link CurrencyConverter} that can be applied as a
	 * {@link MonetaryOperator} to an amount.
	 * 
	 * @return a new instance of a corresponding {@link CurrencyConverter},
	 *         never null.
	 * @throws CurrencyConversionException
	 *             If the required target {@link CurrencyUnit} is not supported.
	 */
	public CurrencyConverter getConverter();


}
