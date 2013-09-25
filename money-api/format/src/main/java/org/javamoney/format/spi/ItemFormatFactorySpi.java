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

import org.javamoney.format.ItemFormat;
import org.javamoney.format.ItemFormatException;
import org.javamoney.format.LocalizationStyle;

/**
 * Instances of this class are loaded and managed by the
 * {@link MonetaryFormatsSingletonSpi}. The formatter runtime will ask each
 * registered instance of {@link ItemFormatFactorySpi} for a formatter given a
 * {@link LocalizationStyle} provided, until an instance will return a non-null
 * instance of {@link ItemFormat}. This instance finally will be returned to the
 * client.
 * <p>
 * Note that the formatter runtime does not perform any caching of instances
 * returned. It is the responsibility of the implementations of this interface,
 * to implement reuse of resources, where useful. Nevertheless keep in mind that
 * synchronization of shared resources can lead to severe performance issues.
 * Therefore in most of the cases it is reasonable to create a new formatter
 * instance on each access and to delegate caching aspects to the clients.
 * Similarly it is not required that the instances returned by the SPI must be
 * thread safe.
 * <p>
 * Instances of this class are required to be thread-safe. They can be
 * implemented as contextual bean in a EE context.
 * 
 * @author Anatole Tresch
 */
public interface ItemFormatFactorySpi<T> {

	/**
	 * Return the target type the owning artifact can be applied to.
	 * 
	 * @return the target type, never {@code null}.
	 */
	public Class<T> getTargetClass();

	/**
	 * Return the style id's supported by this {@link ItemFormatFactorySpi}
	 * instance.
	 * 
	 * @see LocalizationStyle#getId()
	 * @return the supported style identifiers, never {@code null}.
	 */
	public Collection<String> getSupportedStyleIds();

	/**
	 * Access a configured default {@link LocalizationStyle} instance. If the
	 * required styleId is part of the supported styles returned by this spi
	 * implementation, then this method should return the according
	 * {@link LocalizationStyle} instance.
	 * 
	 * @param targetType
	 *            The target type, not {@code null}.
	 * @param styleId
	 *            The style identifier, may be {@code null}, acquiring a
	 *            <i>default</i> style.
	 * @return the style instance, or {@code null}.
	 */
	public LocalizationStyle getLocalizationStyle(Class<?> targetType,
			String styleId);

	/**
	 * Method to check, if a style is available for the type this factory is
	 * producing {@link ItemFormat}s.
	 * 
	 * @see #getTargetClass()
	 * @param styleId
	 *            the target style identifier.
	 * @return {@code true}, if the style is available for the current target
	 *         type.
	 */
	public boolean isSupportedStyle(String styleId);

	/**
	 * Creates a new instance of {@link ItemFormat} configured by the given
	 * {@link LocalizationStyle} instance, if the style (style identifier, one
	 * of the style's attributes) required are not supported by this factory,
	 * {@code null} should be returned (different to the API, where an
	 * {@link ItemFormatException} must be thrown.
	 * 
	 * @see #getTargetClass()
	 * @param style
	 *            the {@link LocalizationStyle} that configures this
	 *            {@link ItemFormat}, which also contains the {@link ItemFormat}
	 *            's configuration attributes.
	 * @return a {@link ItemFormat} instance configured with the given style, or
	 *         {@code null}.
	 */
	public ItemFormat<T> getItemFormat(LocalizationStyle style)
			throws ItemFormatException;

}
