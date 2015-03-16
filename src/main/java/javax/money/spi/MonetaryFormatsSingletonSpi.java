/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import javax.money.MonetaryException;
import javax.money.format.AmountFormatQuery;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import java.util.*;

/**
 * This interface models the singleton functionality of {@link javax.money.format.MonetaryFormats}.
 * <p>
 * Implementations of this interface must be thread-safe.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface MonetaryFormatsSingletonSpi {

    /**
     * Get all available locales. This equals to {@link MonetaryAmountFormatProviderSpi#getAvailableLocales()}.
     *
     * @param providers The (optional) providers to be used, ordered correspondingly.
     * @return all available locales, never {@code null}.
     */
    Set<Locale> getAvailableLocales(String... providers);

    /**
     * Access all {@link javax.money.format.MonetaryAmountFormat} instances matching the given {@link javax.money.format
     * .AmountFormatQuery}.
     *
     * @param formatQuery The format query defining the requirements of the formatter.
     * @return the corresponding {@link javax.money.format.MonetaryAmountFormat} instances, never null
     */
    Collection<MonetaryAmountFormat> getAmountFormats(AmountFormatQuery formatQuery);

    /**
     * Get the names of the currently registered format providers.
     *
     * @return the provider names, never null.
     */
    Set<String> getProviderNames();

    /**
     * Get the default provider chain, identified by the unique provider names in order as evaluated and used.
     *
     * @return the default provider chain, never null.
     */
    List<String> getDefaultProviderChain();

    /**
     * Access an {@link javax.money.format.MonetaryAmountFormat} given a {@link javax.money.format
     * .AmountFormatQuery}.
     *
     * @param formatQuery The format query defining the requirements of the formatter.
     * @return the corresponding {@link javax.money.format.MonetaryAmountFormat}
     * @throws javax.money.MonetaryException if no registered {@link javax.money.spi
     *                                       .MonetaryAmountFormatProviderSpi} can provide a
     *                                       corresponding {@link javax.money.format.MonetaryAmountFormat} instance.
     */
    default MonetaryAmountFormat getAmountFormat(AmountFormatQuery formatQuery) {
        Collection<MonetaryAmountFormat> formats = getAmountFormats(formatQuery);
        if (formats.isEmpty()) {
            throw new MonetaryException("No MonetaryAmountFormat for AmountFormatQuery " + formatQuery);
        }
        return formats.iterator().next();
    }

    /**
     * Checks if a {@link javax.money.format.MonetaryAmountFormat} is available given a {@link javax.money.format
     * .AmountFormatQuery}.
     *
     * @param formatQuery The format query defining the requirements of the formatter.
     * @return true, if a t least one {@link javax.money.format.MonetaryAmountFormat} is matching the query.
     */
    default boolean isAvailable(AmountFormatQuery formatQuery) {
        return !getAmountFormats(formatQuery).isEmpty();
    }

    /**
     * Checks if a {@link javax.money.format.MonetaryAmountFormat} is available given a {@link javax.money.format
     * .AmountFormatQuery}.
     *
     * @param locale    the target {@link Locale}, not {@code null}.
     * @param providers The (optional) providers to be used, ordered correspondingly.
     * @return true, if a t least one {@link javax.money.format.MonetaryAmountFormat} is matching the query.
     */
    default boolean isAvailable(Locale locale, String... providers) {
        return isAvailable(AmountFormatQuery.of(locale, providers));
    }

    /**
     * Access the default {@link MonetaryAmountFormat} given a {@link Locale}.
     *
     * @param locale    the target {@link Locale}, not {@code null}.
     * @param providers The (optional) providers to be used, ordered correspondingly.
     * @return the matching {@link MonetaryAmountFormat}
     * @throws MonetaryException if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
     *                           corresponding {@link MonetaryAmountFormat} instance.
     */
    default MonetaryAmountFormat getAmountFormat(Locale locale, String... providers) {
        return getAmountFormat(AmountFormatQueryBuilder.of(locale).setProviderNames(providers).build());
    }

    /**
     * Access the default {@link MonetaryAmountFormat} given a {@link Locale}.
     *
     * @param formatName the target format name, not {@code null}.
     * @param providers  The (optional) providers to be used, ordered correspondingly.
     * @return the matching {@link MonetaryAmountFormat}
     * @throws MonetaryException if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
     *                           corresponding {@link MonetaryAmountFormat} instance.
     */
    default MonetaryAmountFormat getAmountFormat(String formatName, String... providers) {
        return getAmountFormat(AmountFormatQueryBuilder.of(formatName).setProviderNames(providers).build());
    }

}
