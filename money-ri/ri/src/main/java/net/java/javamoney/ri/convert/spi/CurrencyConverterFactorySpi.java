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
package net.java.javamoney.ri.convert.spi;

import javax.money.convert.ConversionProvider;
import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRateType;

/**
 * This SPI defines the factory that determines the default implementations of
 * {@link ConversionProvider} to be used for a given {@link ExchangeRateType},
 * if no explicit {@link ConversionProvider} was registered.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyConverterFactorySpi {

	/**
	 * Get the exchange rate type that this provider instance is providing data
	 * for.
	 * 
	 * @return the {@link ExchangeRateType} supported, never null.
	 */
	public CurrencyConverter createCurrencyConverter(ExchangeRateType type);

}
