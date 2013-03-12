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

import java.util.Enumeration;

/**
 * This interface defines access to the exchange and currency conversion logic
 * of JavaMoney.
 * 
 * @author Anatole Tresch
 */
public interface ConversionProvider {

	/**
	 * Access an instance of {@link CurrencyConverter} for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param type
	 *            the exchange rate type that this provider instance is
	 *            providing data for, not {@code null}.
	 * 
	 * @return the {@link ExchangeRateType} if this instance.
	 */
	public CurrencyConverter getCurrencyConverter(ExchangeRateType type);

	/**
	 * Access an instance of {@link ExchangeRateProvider} for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param type
	 *            the exchange rate type that this provider instance is
	 *            providing data for, not {@code null}.
	 * 
	 * @return the {@link ExchangeRateType} if this instance.
	 */
	public ExchangeRateProvider getExchangeRateProvider(ExchangeRateType type);

	/**
	 * Return all supported {@link ExchangeRateType} instances for which
	 * {@link ExchangeRateProvider} instances can be obtained.
	 * 
	 * @return all supported {@link ExchangeRateType} instances, never
	 *         {@code null}.
	 */
	public Enumeration<ExchangeRateType> getSupportedExchangeRateTypes();

	/**
	 * CHecks if a {@link ExchangeRateProvider} can be accessed for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param type
	 *            the required {@link ExchangeRateType}, not {@code null}.
	 * @return true, if a {@link ExchangeRateProvider} for this
	 *         {@link ExchangeRateType} can be obtained from this
	 *         {@link ConversionProvider} instance.
	 */
	public boolean isSupportedExchangeRateType(ExchangeRateType type);

}
