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
 */
package net.java.javamoney.ri.format;

import javax.money.format.ItemParseException;
import javax.money.format.LocalizationStyle;

import net.java.javamoney.ri.format.common.ParseContext;

/**
 * Formats instances of T to a {@link String} or an {@link Appendable}.
 * 
 * @TODO check if this class can be moved to {@code java.util}.
 */
public interface ParserToken<T> {

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
	public void parse(ParseContext context) throws ItemParseException;

	/**
	 * Method that allows a {@link ParserToken} being declared as optional.
	 * Optional instances may not raise {@link ItemParseException}s, when they could
	 * not be consumed from the {@link ParseContext}.
	 * 
	 * @return true, if the instance is optional.
	 */
	public boolean isOptional();

	/**
	 * Apply a new decorator instance.
	 * 
	 * @param decorator
	 *            The deocrator to be used, may be null.
	 */
	public void setParseDecorator(ParseDecorator<T> decorator);

	/**
	 * Get the current assigned decorator.
	 * 
	 * @return the current decorator asigned, or {@code null}.
	 */
	public ParseDecorator<T> getParseDecorator();
}
