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
package javax.money.format;

import java.util.Locale;


/**
 * Instance that implement this interface parse Strings into instances of of
 * type T. TODO check if this class can be moved to {@code java.util}.
 */
public interface ItemParser<T>{

	/**
	 * Return the target type this {@link ItemParser} is expecting.
	 * 
	 * @return the target type, never {@code null}.
	 */
	public Class<T> getTargetClass();

	/**
	 * Access the {@link LocalizationStyle} attached to this
	 * {@link ItemFormatter}, which also contains the target {@link Locale}
	 * instances to be used, as well as other attributes configuring this
	 * formatter instance.
	 * 
	 * @return Returns the {@link LocalizationStyle} attached to this
	 *         {@link ItemFormatter}, never {@code null}.
	 */
	public LocalizationStyle getStyle();

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
	 * @return the parsed value, never null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws ItemParseException
	 *             if there is a problem while parsing
	 */
	public T parse(CharSequence text) throws ItemParseException;

}
