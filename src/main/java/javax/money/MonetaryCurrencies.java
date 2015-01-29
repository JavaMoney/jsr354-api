/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import javax.money.spi.Bootstrap;
import javax.money.spi.CurrencyProviderSpi;
import javax.money.spi.MonetaryCurrenciesSingletonSpi;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory singleton for {@link CurrencyUnit} instances as provided by the
 * different registered {@link CurrencyProviderSpi} instances.
 * <p>
 * This class is thread safe.
 *
 * @author Anatole Tresch
 * @version 0.8
 */
public final class MonetaryCurrencies {
    /**
     * The used {@link javax.money.spi.MonetaryCurrenciesSingletonSpi} instance.
     */
    private static final MonetaryCurrenciesSingletonSpi monetaryCurrenciesSpi = loadMonetaryCurrenciesSingletonSpi();

    /**
     * Required for deserialization only.
     */
    private MonetaryCurrencies() {
    }

    /**
     * Loads the SPI backing bean.
     *
     * @return the {@link MonetaryCurrenciesSingletonSpi} backing bean to be used.
     */
    private static MonetaryCurrenciesSingletonSpi loadMonetaryCurrenciesSingletonSpi() {
        try {
            return Bootstrap
                    .getService(MonetaryCurrenciesSingletonSpi.class, new DefaultMonetaryCurrenciesSingletonSpi());
        } catch (Exception e) {
            Logger.getLogger(MonetaryCurrencies.class.getName())
                    .log(Level.SEVERE, "Failed to load MonetaryCurrenciesSingletonSpi, using default.", e);
            return new DefaultMonetaryCurrenciesSingletonSpi();
        }
    }

    /**
     * Access a new instance based on the currency code. Currencies are
     * available as provided by {@link CurrencyProviderSpi} instances registered
     * with the {@link javax.money.spi.Bootstrap}.
     *
     * @param currencyCode the ISO currency code, not {@code null}.
     * @param providers    the (optional) specification of providers to consider.
     * @return the corresponding {@link CurrencyUnit} instance.
     * @throws UnknownCurrencyException if no such currency exists.
     */
    public static CurrencyUnit getCurrency(String currencyCode, String... providers) {
        return Optional.ofNullable(monetaryCurrenciesSpi).orElseThrow(
                () -> new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup."))
                .getCurrency(currencyCode, providers);
    }

    /**
     * Access a new instance based on the {@link Locale}. Currencies are
     * available as provided by {@link CurrencyProviderSpi} instances registered
     * with the {@link javax.money.spi.Bootstrap}.
     *
     * @param locale    the target {@link Locale}, typically representing an ISO
     *                  country, not {@code null}.
     * @param providers the (optional) specification of providers to consider.
     * @return the corresponding {@link CurrencyUnit} instance.
     * @throws UnknownCurrencyException if no such currency exists.
     */
    public static CurrencyUnit getCurrency(Locale locale, String... providers) {
        return Optional.ofNullable(monetaryCurrenciesSpi).orElseThrow(
                () -> new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup."))
                .getCurrency(locale, providers);
    }

    /**
     * Access a new instance based on the {@link Locale}. Currencies are
     * available as provided by {@link CurrencyProviderSpi} instances registered
     * with the {@link javax.money.spi.Bootstrap}.
     *
     * @param locale    the target {@link Locale}, typically representing an ISO
     *                  country, not {@code null}.
     * @param providers the (optional) specification of providers to consider.
     * @return the corresponding {@link CurrencyUnit} instance.
     * @throws UnknownCurrencyException if no such currency exists.
     */
    public static Set<CurrencyUnit> getCurrencies(Locale locale, String... providers) {
        return Optional.ofNullable(monetaryCurrenciesSpi).orElseThrow(
                () -> new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup."))
                .getCurrencies(locale, providers);
    }

    /**
     * Allows to check if a {@link CurrencyUnit} instance is defined, i.e.
     * accessible from {@link MonetaryCurrencies#getCurrency(String, String...)}.
     *
     * @param code      the currency code, not {@code null}.
     * @param providers the (optional) specification of providers to consider.
     * @return {@code true} if {@link MonetaryCurrencies#getCurrency(String, java.lang.String...)}
     * would return a result for the given code.
     */
    public static boolean isCurrencyAvailable(String code, String... providers) {
        return Objects.nonNull(monetaryCurrenciesSpi) && monetaryCurrenciesSpi.isCurrencyAvailable(code, providers);
    }

    /**
     * Allows to check if a {@link javax.money.CurrencyUnit} instance is
     * defined, i.e. accessible from {@link #getCurrency(String, String...)}.
     *
     * @param locale    the target {@link Locale}, not {@code null}.
     * @param providers the (optional) specification of providers to consider.
     * @return {@code true} if {@link #getCurrencies(Locale, String...)} would return a
     * result containing a currency with the given code.
     */
    public static boolean isCurrencyAvailable(Locale locale, String... providers) {
        return Objects.nonNull(monetaryCurrenciesSpi) && monetaryCurrenciesSpi.isCurrencyAvailable(locale, providers);
    }

    /**
     * Access all currencies known.
     *
     * @param providers the (optional) specification of providers to consider.
     * @return the list of known currencies, never null.
     */
    public static Collection<CurrencyUnit> getCurrencies(String... providers) {
        return Optional.ofNullable(monetaryCurrenciesSpi).orElseThrow(
                () -> new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup."))
                .getCurrencies(providers);
    }

    /**
     * Query all currencies matching the given query.
     *
     * @param query The {@link javax.money.CurrencyQuery}, not null.
     * @return the list of known currencies, never null.
     */
    public static CurrencyUnit getCurrency(CurrencyQuery query) {
        return Optional.ofNullable(monetaryCurrenciesSpi).orElseThrow(
                () -> new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup."))
                .getCurrency(query);
    }


    /**
     * Query all currencies matching the given query.
     *
     * @param query The {@link javax.money.CurrencyQuery}, not null.
     * @return the list of known currencies, never null.
     */
    public static Collection<CurrencyUnit> getCurrencies(CurrencyQuery query) {
        return Optional.ofNullable(monetaryCurrenciesSpi).orElseThrow(
                () -> new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup."))
                .getCurrencies(query);
    }

    /**
     * Query all currencies matching the given query.
     *
     * @return the list of known currencies, never null.
     */
    public static Set<String> getProviderNames() {
        return Optional.ofNullable(monetaryCurrenciesSpi).orElseThrow(
                () -> new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup."))
                .getProviderNames();
    }

    /**
     * Query the list and ordering of provider names modelling the default provider chain to be used, if no provider
     * chain was explicitly setTyped..
     *
     * @return the orderend list provider names, modelling the default provider chain used, never null.
     */
    public static List<String> getDefaultProviderChain() {
        return Optional.ofNullable(monetaryCurrenciesSpi).orElseThrow(
                () -> new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup."))
                .getDefaultProviderChain();
    }

    /**
     * Factory singleton for {@link javax.money.CurrencyUnit} instances as provided by the
     * different registered {@link javax.money.spi.CurrencyProviderSpi} instances.
     * <p>
     * This class is thread safe.
     *
     * @author Anatole Tresch
     * @version 0.8
     */
    private static final class DefaultMonetaryCurrenciesSingletonSpi implements MonetaryCurrenciesSingletonSpi {

        @Override
        public Set<CurrencyUnit> getCurrencies(CurrencyQuery query) {
            Set<CurrencyUnit> result = new HashSet<>();
            for (CurrencyProviderSpi spi : Bootstrap.getServices(CurrencyProviderSpi.class)) {
                try {
                    result.addAll(spi.getCurrencies(query));
                } catch (Exception e) {
                    Logger.getLogger(DefaultMonetaryCurrenciesSingletonSpi.class.getName())
                            .log(Level.SEVERE, "Error loading currency provider names for " + spi.getClass().getName(),
                                    e);
                }
            }
            return result;
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
         * Get the names of the currently loaded providers.
         *
         * @return the names of the currently loaded providers, never null.
         */
        @Override
        public Set<String> getProviderNames() {
            Set<String> result = new HashSet<>();
            for (CurrencyProviderSpi spi : Bootstrap.getServices(CurrencyProviderSpi.class)) {
                try {
                    result.add(spi.getProviderName());
                } catch (Exception e) {
                    Logger.getLogger(DefaultMonetaryCurrenciesSingletonSpi.class.getName())
                            .log(Level.SEVERE, "Error loading currency provider names for " + spi.getClass().getName(),
                                    e);
                }
            }
            return result;
        }

    }

}
