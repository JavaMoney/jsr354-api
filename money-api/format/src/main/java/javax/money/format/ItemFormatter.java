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

import java.io.IOException;
import java.util.Locale;

/**
 * Formats instances of T to a {@link String} or an {@link Appendable}.
 */
public interface ItemFormatter<T> {

	/**
	 * Return the target type this {@link ItemFormatter} is expecting.
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
	 * Formats a value of T to a {@code String}. This method uses a
	 * {@link LocalizationStyle} as an input parameter. Styles allows to define
	 * detailed and customized formatting input parameters. This allows to
	 * implement also complex formatting requirements using this interface.
	 * 
	 * @param item
	 *            the item to print, not null
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 */
	public String format(T item);

	/**
	 * Prints a item value to an {@code Appendable}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder},
	 * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
	 * and {@code StringBuffer} never throw an {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param item
	 *            the item to print, not null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void print(Appendable appendable, T item) throws IOException;

}
