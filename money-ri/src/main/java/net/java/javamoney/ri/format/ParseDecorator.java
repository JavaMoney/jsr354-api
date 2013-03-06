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
 * This interface defines an item that may be set as a decorator to a
 * {@link FormatterToken}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The target type.
 */
public interface ParseDecorator<T> {

	/**
	 * Method to call ancapsulate a parsing part into another.
	 * 
	 * @param context
	 *            The current parsing context.
	 * @param style
	 *            The style passed.
	 * @return the final result of this formatting operation. This may be equals
	 *         to {@code formattedString}, when no decoration is effective, or
	 *         something completely different, return as the result of the
	 *         decorated {@link FormatterToken} instance.
	 */
	public void decorateParse(ParseContext context, LocalizationStyle style)
			throws ItemParseException;
	
	public void setParseDecorator(ParseDecorator<T> deocrator);
	
	public ParseDecorator<T> getParseDecorator();

}
