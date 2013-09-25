/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial version.
 */
package org.javamoney.format.spi;

import java.util.Collection;
import java.util.Locale;
import java.util.ServiceLoader;

import org.javamoney.format.ItemFormat;
import org.javamoney.format.ItemFormatException;
import org.javamoney.format.LocalizationStyle;
import org.javamoney.format.MonetaryFormats;

/**
 * This SPI must be registered using the {@code ServiceLoader} to be used as
 * delegate for the {@link MonetaryFormats} singleton. It is responsible for
 * loading and managing of the {@link ItemFormatFactorySpi} instances. Hereby
 * the {@link ItemFormatFactorySpi} instances can be loaded from the
 * {@link ServiceLoader} or by other mechanisms, e.g. CDI.
 * <p>
 * Instances of this class must be thread-safe. In a EE context they can be
 * implemented in a contextual way.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryFormatsSingletonSpi {

	/**
	 * Return the style id's supported by this {@link ItemFormatterFactorySpi}
	 * instance.
	 * 
	 * @see LocalizationStyle#getId()
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @return the supported style ids, never {@code null}.
	 */
	public Collection<String> getSupportedStyleIds(Class<?> targetType);

	/**
	 * Return a style given iots type and id.
	 * 
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @param styleId
	 *            the required style id.
	 * @return the supported style ids, never {@code null}.
	 */
	public LocalizationStyle getLocalizationStyle(Class<?> targetType,
			String styleId);

	/**
	 * Method allows to check if a named style is supported.
	 * 
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @param styleId
	 *            The style id.
	 * @return true, if a spi implementation is able to provide an
	 *         {@link ItemFormat} for the given style.
	 */
	public boolean isSupportedStyle(Class<?> targetType, String styleId);

	/**
	 * This method returns an instance of an {@link ItemFormat} .
	 * 
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @param style
	 *            the {@link LocalizationStyle} to be attached to this
	 *            {@link ItemFormat}, which also contains the target
	 *            {@link Locale} instances to be used, as well as other
	 *            attributes configuring this instance.
	 * @return the formatter required, if available.
	 * @throws ItemFormatException
	 *             if the {@link LocalizationStyle} passed can not be used for
	 *             configuring the {@link ItemFormat} and no matching
	 *             {@link ItemFormat} could be provided by any of the registered
	 *             {@link ItemFormatFactorySpi} instances.
	 */
	public <T> ItemFormat<T> getItemFormat(Class<T> targetType,
			LocalizationStyle style) throws ItemFormatException;
}
