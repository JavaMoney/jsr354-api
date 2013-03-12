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
 * {@link ItemParser} instances.
 * 
 * @see ItemParser
 * @author Anatole Tresch
 */
public interface ItemParserFactory {

	/**
	 * Return the style id's supported by this {@link ItemParserFactorySpi}
	 * instance.
	 * 
	 * @see LocalizationStyle#getId()
	 * @return the supported style ids, never {@code null}.
	 */
	public Enumeration<String> getSupportedStyleIds(Class<?> targetType);

	/**
	 * Method allows to check if a named style is supported.
	 * 
	 * @param styleId
	 *            The style id.
	 * @return true, if a spi implementation is able to provide an
	 *         {@link ItemParser} for the given style.
	 */
	public boolean isSupportedStyle(Class<?> targetType, String styleId);

	/**
	 * This method returns an instance of an {@link ItemParser} .
	 * 
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @param style
	 *            the {@link LocalizationStyle} to be attached to this
	 *            {@link ItemParser}, which also contains the target
	 *            {@link Locale} instances to be used, as well as other
	 *            attributes configuring this instance.
	 * @return the matching parser, if available.
	 * @throws ItemParseException
	 *             if the {@link LocalizationStyle} passed can not be used for
	 *             configuring the {@link ItemParser} and no matching
	 *             {@link ItemParser} could be provided.
	 */
	public <T> ItemParser<T> getItemParser(Class<T> targetType,
			LocalizationStyle style) throws ItemParseException;

	/**
	 * This method returns an instance of a fixed styled {@link ItemParser}.
	 * This method is a convenience method for
	 * {@code getItemParser(targetType, LocalizationStyle.of(locale)) }.
	 * 
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @param locale
	 *            The target locale.
	 * @return the formatter required, if available.
	 * @throws ItemParseException
	 *             if the {@link LocalizationStyle} passed can not be used for
	 *             configuring the {@link ItemParser} and no matching
	 *             {@link ItemParser} could be provided.
	 */
	public <T> ItemParser<T> getItemParser(Class<T> targetType, Locale locale)
			throws ItemParseException;

}
