/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * 
 * Contributors:
 *    Anatole Tresch - initial version.
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
