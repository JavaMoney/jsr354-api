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
package javax.money.format.spi;

import java.util.Collection;
import java.util.Locale;
import java.util.ServiceLoader;

import javax.money.format.ItemFormat;
import javax.money.format.ItemFormatException;
import javax.money.format.LocalizationStyle;

/**
 * Instances of this class can be registered using the {@link ServiceLoader}
 * API. The formatter runtime will ask each registered instance of
 * {@link ItemFormatFactorySpi} for a formatter given the
 * {@link LocalizationStyle} provided, until an instance will return a non-null
 * instance of {@link AmountFormatter}. This instance finally will be returned
 * to the client.
 * <p>
 * Note that the formatter runtime does not perform any caching of instances
 * returned. It is the responsibility of the implementations of this interface,
 * to implement reuse of resources, where useful. Nevertheless keep in mind that
 * synchronization of shared resources can lead to severe performance issues.
 * Therefore in most of the cases it is reasonable to create a new formatter
 * instance on each access and to delegate caching aspects to the client using
 * this API. Similarly it is not required that the instances returned by the SPI
 * must be thread safe.
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
     * @return the supported style ids, never {@code null}.
     */
    public Collection<String> getSupportedStyleIds();

    /**
     * Access a configured default {@link LocalizationStyle} instance. If the
     * required styleId is part of the supported styles returned by this spi
     * implementation, then this method should return the according
     * {@link LocalizationStyle} instance.
     * 
     * @param targetType
     *            The target type, not null.
     * @param styleId
     *            The style id, may be null, acquiring a default style.
     * @return the style instance, or {@core null}.
     */
    public LocalizationStyle getLocalizationStyle(Class<?> targetType, String styleId);

    /**
     * Method to check, if a style is available.
     * 
     * @param styleId
     *            the target style.
     * @return true, if the style is available.
     */
    public boolean isSupportedStyle(String styleId);

    /**
     * Creates a new instance of the formatter defined by the passed
     * localization style instance, if the style (style id, one of the style's
     * locales or additional attributes) required are not supported by this
     * factory, {@code null} should be returned.
     * 
     * @see #getTargetClass()
     * @param style
     *            the {@link LocalizationStyle} that configures this
     *            {@link ItemFormat}, which also contains the target
     *            {@link Locale} instances to be used, as well as other
     *            attributes.
     * @return a formatter instance representing the given style, or null.
     * @throws ItemFormatException
     *             if the {@link LocalizationStyle} passed can not be used for
     *             configuring the {@link ItemFormat}.
     */
    public ItemFormat<T> getItemFormat(LocalizationStyle style) throws ItemFormatException;

}
