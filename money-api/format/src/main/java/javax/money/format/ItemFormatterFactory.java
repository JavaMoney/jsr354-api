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

import java.util.Enumeration;
import java.util.Locale;

/**
 * This class represent the accessor interface for creating different kind of
 * {@link ItemFormatter} instances.
 * 
 * @see ItemFormatter
 * @author Anatole Tresch
 */
public interface ItemFormatterFactory {

	/**
	 * Return the style id's supported by this {@link ItemFormatterFactorySpi}
	 * instance.
	 * 
	 * @see LocalizationStyle#getId()
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @return the supported style ids, never {@code null}.
	 */
	public Enumeration<String> getSupportedStyleIds(Class<?> targetType);

	/**
	 * Method allows to check if a named style is supported.
	 * 
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @param styleId
	 *            The style id.
	 * @return true, if a spi implementation is able to provide an
	 *         {@link ItemFormatter} for the given style.
	 */
	public boolean isSupportedStyle(Class<?> targetType, String styleId);

	/**
	 * This method returns an instance of an {@link ItemFormatter} .
	 * 
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @param style
	 *            the {@link LocalizationStyle} to be attached to this
	 *            {@link ItemFormatter}, which also contains the target
	 *            {@link Locale} instances to be used, as well as other
	 *            attributes configuring this instance.
	 * @return the formatter required, if available.
	 * @throws ItemFormatException
	 *             if the {@link LocalizationStyle} passed can not be used for
	 *             configuring the {@link ItemFormatter} and no matching
	 *             {@link ItemFormatter} could be provided.
	 */
	public <T> ItemFormatter<T> getItemFormatter(Class<T> targetType,
			LocalizationStyle style) throws ItemFormatException;

	/**
	 * This method returns an instance of an {@link ItemFormatter}. This method
	 * is a convenience method for
	 * {@code getItemFormatter(LocalizationStyle.of(locale)) }.
	 * 
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @param locale
	 *            The target locale.
	 * @return the formatter required, if available.
	 * @throws ItemFormatException
	 *             if the {@link LocalizationStyle} passed can not be used for
	 *             configuring the {@link ItemFormatter} and no matching
	 *             {@link ItemFormatter} could be provided.
	 */
	public <T> ItemFormatter<T> getItemFormatter(Class<T> targetType,
			Locale locale) throws ItemFormatException;

}
