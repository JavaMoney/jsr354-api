/*
 *  Copyright 2012 Credit Suisse (Anatole Tresch)
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
 */
package javax.money.format;

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.UnknownCurrencyException;

/**
 * Parses instances of {@link CurrencyUnit} to and from a {@link CharSequence}.
 * <p>
 * Instances of {@code CurrencyParser} can be obtained by calling
 * {@code MoneyFormat#getCurrencyParser(Locale)} or
 * {@code MoneyFormat#getCurrencyParser(LocalizationStyle)}.
 */
public interface CurrencyParser extends Parser<CurrencyUnit> {

	/**
	 * Get the name space this parser is working on.
	 * 
	 * @return the name space, never null.
	 */
	public String getNamespace();

	/**
	 * Parses a {@link CurrencyUnit} based on the localized symbol.
	 * 
	 * @param symbol
	 *            the input data.
	 * @param locale
	 *            the target locale
	 * @return parsed {@link CurrencyUnit}, never null.
	 * @throws UnknownCurrencyException
	 *             if the required symbol can be mapped to a
	 *             {@link CurrencyUnit} on the given namespace.
	 * @throws ParseException
	 *             if there is a problem while parsing
	 */
	public CurrencyUnit parseSymbol(CharSequence symbol, Locale locale);

	/**
	 * Parses a {@link CurrencyUnit} based on the localized symbol.
	 * 
	 * @param symbol
	 *            the input data.
	 * @param style
	 *            the target style.
	 * @return parsed {@link CurrencyUnit}, never null.
	 * @throws UnknownCurrencyException
	 *             if the required symbol can be mapped to a
	 *             {@link CurrencyUnit} on the given namespace.
	 * @throws ParseException
	 *             if there is a problem while parsing
	 */
	public CurrencyUnit parseSymbol(CharSequence symbol, LocalizationStyle style);

}
