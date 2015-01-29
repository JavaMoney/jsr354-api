/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import javax.money.MonetaryException;
import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryAmountFormatProviderSpi;
import javax.money.spi.MonetaryFormatsSingletonSpi;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class models the singleton accessor for {@link MonetaryAmountFormat} instances.
 * <p>
 * This class is thread-safe.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryFormats {

    private static final MonetaryFormatsSingletonSpi monetaryFormatsSingletonSpi = loadMonetaryFormatsSingletonSpi();

    /**
     * Private singleton constructor.
     */
    private MonetaryFormats() {
        // Singleton
    }

    /**
     * Loads the SPI backing bean.
     *
     * @return the instance of MonetaryFormatsSingletonSpi to be used by this singleton.
     */
    private static MonetaryFormatsSingletonSpi loadMonetaryFormatsSingletonSpi() {
        try {
            return Bootstrap.getService(MonetaryFormatsSingletonSpi.class, new DefaultMonetaryFormatsSingletonSpi());
        } catch (Exception e) {
            Logger.getLogger(MonetaryFormats.class.getName())
                    .log(Level.WARNING, "Failed to load MonetaryFormatsSingletonSpi, using default.", e);
            return new DefaultMonetaryFormatsSingletonSpi();
        }
    }

    /**
     * Checks if a {@link MonetaryAmountFormat} is available for the given {@link Locale} and providers.
     *
     * @param locale    the target {@link Locale}, not {@code null}.
     * @param providers The providers to be queried, if not setTyped the providers as defined by #getDefaultProviderChain()
     *                  are queried.
     * @return true, if a corresponding {@link MonetaryAmountFormat} is accessible.
     */
    public static boolean isAvailable(Locale locale, String... providers) {
        return Optional.ofNullable(monetaryFormatsSingletonSpi).orElseThrow(() -> new MonetaryException(
                "No MonetaryFormatsSingletonSpi " + "loaded, query functionality is not available."))
                .isAvailable(locale, providers);
    }

    /**
     * Access the default {@link MonetaryAmountFormat} given a {@link Locale}.
     *
     * @param locale    the target {@link Locale}, not {@code null}.
     * @param providers The providers to be queried, if not setTyped the providers as defined by #getDefaultProviderChain()
     *                  are queried.
     * @return the matching {@link MonetaryAmountFormat}
     * @throws MonetaryException if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
     *                           corresponding {@link MonetaryAmountFormat} instance.
     */
    public static MonetaryAmountFormat getAmountFormat(Locale locale, String... providers) {
        return getAmountFormat(AmountFormatQueryBuilder.of(locale).setProviders(providers).setLocale(locale).build());
    }

    /**
     * Checks if a {@link MonetaryAmountFormat} is available for the given {@link javax.money.format.AmountFormatQuery}.
     *
     * @param formatQuery the required {@link AmountFormatQuery}, not {@code null}. If the query does not define
     *                    any explicit provider chain, the providers as defined by #getDefaultProviderChain()
     *                    are used.
     * @return true, if a corresponding {@link MonetaryAmountFormat} is accessible.
     */
    public static boolean isAvailable(AmountFormatQuery formatQuery) {
        return Optional.ofNullable(monetaryFormatsSingletonSpi).orElseThrow(() -> new MonetaryException(
                "No MonetaryFormatsSingletonSpi " + "loaded, query functionality is not available."))
                .isAvailable(formatQuery);
    }

    /**
     * Access the default {@link MonetaryAmountFormat} given a {@link Locale}.
     *
     * @param formatQuery the required {@link AmountFormatQuery}, not {@code null}. If the query does not define
     *                    any explicit provider chain, the providers as defined by #getDefaultProviderChain()
     *                    are used.
     * @return the matching {@link MonetaryAmountFormat}
     * @throws MonetaryException if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
     *                           corresponding {@link MonetaryAmountFormat} instance.
     */
    public static MonetaryAmountFormat getAmountFormat(AmountFormatQuery formatQuery) {
        return Optional.ofNullable(monetaryFormatsSingletonSpi).orElseThrow(() -> new MonetaryException(
                "No MonetaryFormatsSingletonSpi " + "loaded, query functionality is not available."))
                .getAmountFormat(formatQuery);
    }

    /**
     * Access all {@link MonetaryAmountFormat} instances that match the given a {@link AmountFormatQuery}.
     *
     * @param formatQuery the required {@link AmountFormatQuery}, not {@code null}. If the query does not define
     *                    any explicit provider chain, the providers as defined by #getDefaultProviderChain()
     *                    are used.
     * @return the matching {@link MonetaryAmountFormat}
     * @throws MonetaryException if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
     *                           corresponding {@link MonetaryAmountFormat} instance.
     */
    public static Collection<MonetaryAmountFormat> getAmountFormats(AmountFormatQuery formatQuery) {
        return Optional.ofNullable(monetaryFormatsSingletonSpi).orElseThrow(() -> new MonetaryException(
                "No MonetaryFormatsSingletonSpi " + "loaded, query functionality is not available."))
                .getAmountFormats(formatQuery);
    }

    /**
     * Access the a {@link MonetaryAmountFormat} given its styleId.
     *
     * @param formatName the target format name, not {@code null}.
     * @param providers  The providers to be used, if not setTyped the providers as defined by #getDefaultProviderChain() are
     *                   used.
     * @return the matching {@link MonetaryAmountFormat}
     * @throws MonetaryException if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
     *                           corresponding {@link MonetaryAmountFormat} instance.
     */
    public static MonetaryAmountFormat getAmountFormat(String formatName, String... providers) {
        return getAmountFormat(AmountFormatQueryBuilder.of(formatName).setProviders(providers).build());
    }

    /**
     * Get all available locales. This equals to {@link MonetaryAmountFormatProviderSpi#getAvailableLocales()}.
     *
     * @param providers The providers to be used, if not setTyped the providers as defined by #getDefaultProviderChain() are
     *                  used.
     * @return all available locales, never {@code null}.
     */
    public static Set<Locale> getAvailableLocales(String... providers) {
        return monetaryFormatsSingletonSpi.getAvailableLocales(providers);
    }

    /**
     * Get the names of the currently registered format providers.
     *
     * @return the provider names, never null.
     */
    public static Collection<String> getProviderNames() {
        Collection<String> providers = Optional.ofNullable(monetaryFormatsSingletonSpi).orElseThrow(
                () -> new MonetaryException(
                        "No MonetaryConveresionsSingletonSpi loaded, query functionality is not available."))
                .getProviderNames();
        if (Objects.isNull(providers)) {
            Logger.getLogger(MonetaryFormats.class.getName()).warning(
                    "No supported rate/conversion providers returned by SPI: " +
                            monetaryFormatsSingletonSpi.getClass().getName());
            return Collections.emptySet();
        }
        return providers;
    }

    /**
     * Get the default provider chain, identified by the unique provider names in order as evaluated and used.
     *
     * @return the default provider chain, never null.
     */
    public static List<String> getDefaultProviderChain() {
        return Optional.ofNullable(monetaryFormatsSingletonSpi).orElseThrow(() -> new MonetaryException(
                "No MonetaryFormatsSingletonSpi " + "loaded, query functionality is not available."))
                .getDefaultProviderChain();
    }

    /**
     * This class models the singleton accessor for {@link javax.money.format.MonetaryAmountFormat} instances.
     * <p>
     * This class is thread-safe.
     *
     * @author Anatole Tresch
     * @author Werner Keil
     */
    public static final class DefaultMonetaryFormatsSingletonSpi implements MonetaryFormatsSingletonSpi {


        /**
         * Access an {@link javax.money.format.MonetaryAmountFormat} given a {@link javax.money.format
         * .AmountFormatContext}.
         *
         * @param formatQuery The format query defining the requirements of the formatter.
         * @return the corresponding {@link javax.money.format.MonetaryAmountFormat}
         * @throws javax.money.MonetaryException if no registered {@link javax.money.spi
         *                                       .MonetaryAmountFormatProviderSpi} can provide a
         *                                       corresponding {@link javax.money.format.MonetaryAmountFormat} instance.
         */
        public Collection<MonetaryAmountFormat> getAmountFormats(AmountFormatQuery formatQuery) {
            Collection<MonetaryAmountFormat> result = new ArrayList<>();
            for (MonetaryAmountFormatProviderSpi spi : Bootstrap.getServices(MonetaryAmountFormatProviderSpi.class)) {
                Collection<MonetaryAmountFormat> formats = spi.getAmountFormats(formatQuery);
                if (Objects.nonNull(formats)) {
                    result.addAll(formats);
                }
            }
            return result;
        }

        @Override
        public Set<String> getProviderNames() {
            return getSpisAsMap().keySet();
        }

        /**
         * This default implementation simply returns all providers defined in arbitrary order.
         *
         * @return the default provider chain, never null.
         */
        @Override
        public List<String> getDefaultProviderChain() {
            List<String> list = new ArrayList<>();
            list.addAll(getProviderNames());
            Collections.sort(list);
            return list;
        }


        /**
         * Get all available locales. This equals to {@link javax.money.spi
         * .MonetaryAmountFormatProviderSpi#getAvailableLocales()}.
         *
         * @return all available locales, never {@code null}.
         */
        public Set<Locale> getAvailableLocales(String... providerNames) {
            Set<Locale> locales = new HashSet<>();
            Collection<MonetaryAmountFormatProviderSpi> spis = getSpis(providerNames);
            for (MonetaryAmountFormatProviderSpi spi : spis) {
                locales.addAll(spi.getAvailableLocales());
            }
            return locales;
        }

        private Map<String, MonetaryAmountFormatProviderSpi> getSpisAsMap() {
            Map<String, MonetaryAmountFormatProviderSpi> spis = new ConcurrentHashMap<>();
            for (MonetaryAmountFormatProviderSpi spi : Bootstrap.getServices(MonetaryAmountFormatProviderSpi.class)) {
                if (spi.getProviderName() == null) {
                    Logger.getLogger(MonetaryFormats.class.getName()).warning("MonetaryAmountFormatProviderSpi " +
                            "returns null for " +
                            "getProviderName: " +
                            spi.getClass().getName());
                }
                spis.put(spi.getProviderName(), spi);
            }
            return spis;
        }

        private Collection<MonetaryAmountFormatProviderSpi> getSpis(String... providerNames) {
            List<MonetaryAmountFormatProviderSpi> providers = new ArrayList<>();
            Map<String, MonetaryAmountFormatProviderSpi> spis = getSpisAsMap();
            if (providerNames.length == 0) {
                providers.addAll(spis.values());
            } else {
                for (String provName : providerNames) {
                    MonetaryAmountFormatProviderSpi spi = spis.get(provName);
                    if (Objects.isNull(spi)) {
                        throw new IllegalArgumentException("MonetaryAmountFormatProviderSpi not found: " + provName);
                    }
                    providers.add(spi);
                }
            }
            return providers;
        }

    }
}
