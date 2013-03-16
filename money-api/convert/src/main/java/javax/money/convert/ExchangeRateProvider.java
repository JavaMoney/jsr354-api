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

/**
 * This interface defines access to the exchange conversion logic of JavaMoney.
 * It is provided by the Money singleton. It is provided by the Money singleton.
 * 
 * @author Anatole Tresch
 */
public interface ExchangeRateProvider {

	/**
	 * Access the {@link ExchangeRateType} for this {@link ExchangeRateProvider}
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
	 * @param source
	 *            the source currency
	 * @param target
	 *            the target currency
	 * @return true, if such an exchange is currently defined.
	 */
	public boolean isAvailable(CurrencyUnit src, CurrencyUnit target);

	/**
	 * Checks if an exchange of a currency is defined.
	 * 
	 * @param source
	 *            the source currency
	 * @param target
	 *            the target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried,
	 *            or {@code null}.
	 * @return true, if such an exchange is currently defined.
	 */
	public boolean isAvailable(CurrencyUnit CurrencyUnit, CurrencyUnit target,
			Long timestamp);

	/**
	 * Get an {@link ConversionRate} for a given timestamp (including historic
	 * rates).
	 * 
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried,
	 *            or {@code null}.
	 * @return the matching {@link ExchangeRate}, or {@code null}.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit sourceCurrency,
			CurrencyUnit targetCurrency, Long timestamp);

	/**
	 * Access a exchange rate using the given currencies. The rate may be,
	 * depending on the data provider, be real-time or deferred.
	 * 
	 * @param source
	 *            source currency.
	 * @param target
	 *            target currency.
	 * @return the matching {@link ExchangeRate}, or {@code null}.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit source, CurrencyUnit target);

	/**
	 * The method reverses the exchange rate to a rate mapping from target to
	 * source. Hereby the factor must <b>not</b> be recalculated as
	 * {@code 1/oldFactor}, since typically reverse rates are not symmetric in
	 * most cases.
	 * 
	 * @return the matching reversed {@link ExchangeRate}, or {@code null}.
	 */
	public ExchangeRate getReversed(ExchangeRate rate);

}
