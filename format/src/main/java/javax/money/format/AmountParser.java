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

import javax.money.Amount;
import javax.money.CurrencyUnit;

/**
 * Formats instances of money to and from a String.
 * <p>
 * Instances of {@code MoneyFormatter} can be created by
 * {@code MoneyFormatterBuilder}.
 * <p>
 * This class is immutable and thread-safe.
 */
public interface AmountParser extends Parser<Amount> {

	/**
	 * Fully parses a number and combines it with a {@link CurrencyUnit} to an
	 * {@link Amount} instance. The amount of parsed decimals can hereby differ
	 * from the correct number of decimal places.
	 * <p>
	 * The parse must complete normally and parse the entire text (currency and
	 * amount). If the parse completes without reading the entire length of the
	 * text, an exception is thrown. If any other problem occurs during parsing,
	 * an exception is thrown.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @param currency
	 *            the target currency to be used (not the text to parse contains
	 *            only a number).
	 * @param style
	 *            The target localization style determining the input format.
	 * @return the parsed monetary value, never null
	 * 
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws FormatException
	 *             if there is a problem while parsing
	 * @throws ArithmeticException
	 *             if the scale of the parsed money exceeds the scale of the
	 *             currency
	 */
	public Amount parseNumber(CharSequence text, CurrencyUnit currency,
			LocalizationStyle style) throws ParseException;

	/**
	 * Fully parses the text into a {@code Money} requiring that the parsed
	 * amount has the correct number of decimal places.
	 * <p>
	 * The parse must complete normally and parse the entire text (currency and
	 * amount). If the parse completes without reading the entire length of the
	 * text, an exception is thrown. If any other problem occurs during parsing,
	 * an exception is thrown.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @param currency
	 *            the target currency to be used (not the text to parse contains
	 *            only a number).
	 * @param locale
	 *            The target locale determining the input format.
	 * @return the parsed monetary value, never null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws FormatException
	 *             if there is a problem while parsing
	 * @throws ArithmeticException
	 *             if the scale of the parsed money exceeds the scale of the
	 *             currency
	 */
	public Amount parseNumber(CharSequence text, CurrencyUnit currency,
			Locale locale) throws ParseException;

}
