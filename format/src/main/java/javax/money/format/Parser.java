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

/**
 * Instance that implement this interface parse Strings into instances of of
 * type T.
 */
public interface Parser<T> {

	/**
	 * Fully parses the text into an instance of T.
	 * <p>
	 * The parse must complete normally and parse the entire text. If the parse
	 * completes without reading the entire length of the text, an exception is
	 * thrown. If any other problem occurs during parsing, an exception is
	 * thrown.
	 * <p>
	 * This method uses a {@link LocalizationStyle} as an input parameter.
	 * Styles allows to define detailed and customized formatting input
	 * parameters. This allows to implement also complex formatting requirements
	 * using this interface.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @param style
	 *            the localization style to be used for parsing
	 * @return the parsed value, never null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws ParseException
	 *             if there is a problem while parsing
	 */
	public T parse(CharSequence text, LocalizationStyle locale)
			throws ParseException;

	/**
	 * Fully parses the text into an instance of T.
	 * <p>
	 * The parse must complete normally and parse the entire text (currency and
	 * amount). If the parse completes without reading the entire length of the
	 * text, an exception is thrown. If any other problem occurs during parsing,
	 * an exception is thrown.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @param locale
	 *            The locale to be used for parsing, nnot null.
	 * @return the parsed value, never null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws ParseException
	 *             if there is a problem while parsing
	 */
	public T parse(CharSequence text, Locale locale) throws ParseException;

}
