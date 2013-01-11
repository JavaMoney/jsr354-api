/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
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
 * Contributors:
 *    Anatole Tresch - initial implementation
 *    Wernner Keil - extensions and adaptions.
 */
package javax.money;

import javax.money.convert.ExchangeRates;
import javax.money.format.MoneyFormats;
import javax.money.format.MoneyParsers;

/**
 * This is the main accessor component for Java Money.
 * 
 * @author Anatole Tresch
 * 
 */
public final class Money {

	/**
	 * Singleton constructor.
	 */
	private Money() {
	}

	/**
	 * Access the {@link Regions} component.
	 * 
	 * @return the {@link Regions} component, never {@code null}.
	 */
	public static Regions getRegions() {
		return null;
	}

	/**
	 * Access the {@link MonetaryAmounts} component.
	 * 
	 * @return the {@link MonetaryAmounts} component, never {@code null}.
	 */
	public static MonetaryAmounts getAmounts() {
		return null;
	}

	/**
	 * Access the {@link Currencies} component.
	 * 
	 * @return the {@link Currencies} component, never {@code null}.
	 */
	public static Currencies getCurrencies() {
		return null;
	}

	/**
	 * Access the {@link ExchangeRates} component.
	 * 
	 * @return the {@link ExchangeRates} component, never {@code null}.
	 */
	public static ExchangeRates getExchangeRates() {
		return null;
	}

	/**
	 * Access the {@link MoneyFormats} component.
	 * 
	 * @return the {@link MoneyFormats} component, never {@code null}.
	 */
	public static MoneyFormats getFormats() {
		return null;
	}

	/**
	 * Access the {@link MoneyParsers} component.
	 * 
	 * @return the {@link MoneyParsers} component, never {@code null}.
	 */
	public static MoneyParsers getParsers() {
		return null;
	}

}
