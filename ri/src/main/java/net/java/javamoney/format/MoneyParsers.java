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
package net.java.javamoney.format;

import java.util.Locale;

import javax.money.format.AmountParser;
import javax.money.format.CurrencyParser;
import javax.money.format.LocalizationStyle;

/**
 * This class represent the singleton for money related formatting and parsing
 * functionality.
 * 
 * @author Anatole Tresch
 */
public final class MoneyParsers {

	/**
	 * Singleton constructor.
	 */
	private MoneyParsers() {
	}

	/**
	 * This method returns a parser instance for {@link MonetaryAmount}
	 * instances formatted in the given {@link Locale}. The instance returned
	 * must be provided by the registered AmountParserFactory SPI
	 * implementation.
	 * 
	 * @param locale
	 *            The target locale. The locale will be converted into an
	 *            according {@link LocalizationStyle} using
	 *            {@link LocalizationStyle#of(Locale)}.
	 * @return the according parser, if available.
	 * @throws
	 */
	public AmountParser getAmountParser(Locale locale) {
		// TODO implement this
		return null;
	}

	/**
	 * This method returns a parser instance for {@link MonetaryAmount}
	 * instances formatted in the given {@link Locale}. The instance returned
	 * must be provided by the registered AmountParserFactory SPI
	 * implementation.
	 * 
	 * @param style
	 *            The target localization style.
	 * @return the according parser, if available.
	 * @throws
	 */
	public AmountParser getAmountParser(LocalizationStyle style) {
		// TODO implement this
		return null;
	}

	/**
	 * This method returns an instance of a {@link CurrencyParser}.
	 * 
	 * @param namespace
	 *            the target name space of currencies.
	 * @param style
	 *            The target localization style.
	 * @return a currency formatter.
	 */
	public CurrencyParser getCurrencyParser(String namespace,
			LocalizationStyle style) {
		// TODO implement this
		return null;
	}

	/**
	 * This method returns an instance of a {@link CurrencyParser}.
	 * 
	 * @param namespace
	 *            the target name space of currencies.
	 * @param locale
	 *            The target locale.
	 * @return a currency formatter.
	 */
	public CurrencyParser getCurrencyParser(String namespace, Locale locale) {
		// TODO implement this
		return null;
	}

}
