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
package net.java.javamoney.ri.format.common;

import javax.money.format.common.LocalizationStyle;

/**
 * This interface defines an item that may be set as a decorator to a
 * {@link FormatterToken}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The target type.
 */
public interface FormatDecorator<T> {

	/**
	 * Method to call the decoration.
	 * 
	 * @param item
	 *            The target item being formatted.
	 * @param formattedString
	 *            the formatted value as formatted by the decorator's child.
	 * @param style
	 *            The style passed.
	 * @return the final result of this formatting operation. This may be equals
	 *         to {@code formattedString}, when no decoration is effective, or
	 *         something completely different, return as the result of the
	 *         decorated {@link FormatterToken} instance.
	 */
	public String decorateFormat(T item, String formattedString,
			LocalizationStyle style);

	public void setFormatDecorator(FormatDecorator<T> deocrator);
	
	public FormatDecorator<T> getFormatDecorator();

}
