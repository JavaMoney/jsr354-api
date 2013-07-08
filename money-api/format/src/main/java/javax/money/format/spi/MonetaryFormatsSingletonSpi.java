/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.money.format.spi;

import java.util.Collection;
import javax.money.format.ItemFormat;
import javax.money.format.ItemFormatException;
import javax.money.format.LocalizationStyle;

/**
 *
 * @author Anatole
 */
public interface MonetaryFormatsSingletonSpi {

    /**
     * Return the style id's supported by this {@link ItemFormatterFactorySpi}
     * instance.
     *
     * @see LocalizationStyle#getId()
     * @param targetType the target type, never {@code null}.
     * @return the supported style ids, never {@code null}.
     */
    public Collection<String> getSupportedStyleIds(Class<?> targetType);

    /**
     * Return a style given iots type and id.
     *
     * @param targetType the target type, never {@code null}.
     * @param styleId the required style id.
     * @return the supported style ids, never {@code null}.
     */
    public LocalizationStyle getLocalizationStyle(Class<?> targetType, String styleId);

    /**
     * Method allows to check if a named style is supported.
     *
     * @param targetType the target type, never {@code null}.
     * @param styleId The style id.
     * @return true, if a spi implementation is able to provide an
     * {@link ItemFormat} for the given style.
     */
    public boolean isSupportedStyle(Class<?> targetType, String styleId);

    /**
     * This method returns an instance of an {@link ItemFormat} .
     *
     * @param targetType the target type, never {@code null}.
     * @param style the {@link LocalizationStyle} to be attached to this
     * {@link ItemFormat}, which also contains the target {@link Locale}
     * instances to be used, as well as other attributes configuring this
     * instance.
     * @return the formatter required, if available.
     * @throws ItemFormatException if the {@link LocalizationStyle} passed can
     * not be used for configuring the {@link ItemFormat} and no matching
     * {@link ItemFormat} could be provided.
     */
    public <T> ItemFormat<T> getItemFormat(Class<T> targetType, LocalizationStyle style) throws ItemFormatException;
}
