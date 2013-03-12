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
import javax.money.MonetaryAmount;

/**
 * This interface defines access to the exchange conversion logic of JavaMoney.
 * It is provided by the Money singleton. It is provided by the Money singleton.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyConverter {

	/**
	 * Get the exchange rate type that this provider instance is providing data
	 * for.
	 * 
	 * @return the {@link ExchangeRateType} if this instance.
	 */
	public ExchangeRateType getExchangeRateType();

	/**
	 * Method that converts the source {@link MonetaryAmount} to an
	 * {@link MonetaryAmount} with the given target {@link CurrencyUnit}.<br/>
	 * By default this method should use a real time conversion, but may also
	 * fall back to deferred data.
	 * 
	 * @param amount
	 *            The source amount
	 * @param target
	 *            The target currency
	 * @return The converted amount, never null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target);

	/**
	 * Method that converts the source {@link MonetaryAmount} to an
	 * {@link MonetaryAmount} with the given target {@link CurrencyUnit}.
	 * 
	 * @param amount
	 *            The source amount
	 * @param target
	 *            The target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried,
	 *            or {@code null}.
	 * @return The converted amount, never null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target,
			Long timestamp);

	/**
	 * Method that converts the source {@code double} amount in source
	 * {@link CurrencyUnit} to an {@link MonetaryAmount} with the given target
	 * {@link CurrencyUnit}.<br/>
	 * By default this method should use a real time conversion, but may also
	 * fall back to deferred data.
	 * 
	 * @param amount
	 *            The amount.
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @return the converted {@code value} as {@code double}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public MonetaryAmount convert(Number amount, CurrencyUnit sourceCurrency,
			CurrencyUnit targetCurrency);

	/**
	 * Method that converts the source {@code double} amount in source
	 * {@link CurrencyUnit} to an {@link MonetaryAmount} with the given target
	 * {@link CurrencyUnit}.
	 * 
	 * @param amount
	 *            The amount.
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried,
	 *            or {@code null}.
	 * @return the converted {@code value} as {@code double}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public MonetaryAmount convert(Number amount, CurrencyUnit source,
			CurrencyUnit target, Long timestamp);

}
