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
 * Copyright (c) 2012-2015, Credit Suisse All rights reserved.
 */
package javax.money;

import javax.money.spi.Bootstrap;
import javax.money.spi.CurrencyProviderSpi;
import javax.money.spi.MonetaryAmountsSingletonQuerySpi;
import javax.money.spi.MonetaryAmountsSingletonSpi;
import javax.money.spi.MonetaryCurrenciesSingletonSpi;
import javax.money.spi.MonetaryRoundingsSingletonSpi;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory singleton for {@link CurrencyUnit}, {@link javax.money.MonetaryAmount} and
 * {@link javax.money.MonetaryRounding} instances as provided by the
 * different registered SPI instances.
 * <p/>
 * This class is thread safe.
 *
 * @author Anatole Tresch
 */
public final class Monetary {
    /**
     * The used {@link javax.money.spi.MonetaryCurrenciesSingletonSpi} instance.
     */
    private static final MonetaryCurrenciesSingletonSpi MONETARY_CURRENCIES_SINGLETON_SPI() {
        try {
            return Optional.ofNullable(Bootstrap
                    .getService(MonetaryCurrenciesSingletonSpi.class)).orElseGet(
                    DefaultMonetaryCurrenciesSingletonSpi::new);
        } catch (Exception e) {
            Logger.getLogger(Monetary.class.getName())
                    .log(Level.INFO, "Failed to load MonetaryCurrenciesSingletonSpi, using default.", e);
            return new DefaultMonetaryCurrenciesSingletonSpi();
        }
    }

    /**
     * The used {@link javax.money.spi.MonetaryAmountsSingletonSpi} instance.
     */
    private static final MonetaryAmountsSingletonSpi MONETARY_AMOUNTS_SINGLETON_SPI() {
        try {
            return Bootstrap.getService(MonetaryAmountsSingletonSpi.class);
        } catch (Exception e) {
            Logger.getLogger(Monetary.class.getName())
                    .log(Level.SEVERE, "Failed to load MonetaryAmountsSingletonSpi.", e);
            return null;
        }
    }

    /**
     * The used {@link javax.money.spi.MonetaryAmountsSingletonSpi} instance.
     */
    private static final MonetaryAmountsSingletonQuerySpi MONETARY_AMOUNTS_SINGLETON_QUERY_SPI() {
        try {
            return Bootstrap.getService(MonetaryAmountsSingletonQuerySpi.class);
        } catch (Exception e) {
            Logger.getLogger(Monetary.class.getName()).log(Level.SEVERE, "Failed to load " +
                    "MonetaryAmountsSingletonQuerySpi, " +
                    "query functionality will not be " +
                    "available.", e);
            return null;
        }
    }

    /**
     * The used {@link javax.money.spi.MonetaryCurrenciesSingletonSpi} instance.
     */
    private static final MonetaryRoundingsSingletonSpi MONETARY_ROUNDINGS_SINGLETON_SPI() {
        try {
            return Optional.ofNullable(Bootstrap
                    .getService(MonetaryRoundingsSingletonSpi.class))
                    .orElseGet(DefaultMonetaryRoundingsSingletonSpi::new);
        } catch (Exception e) {
            Logger.getLogger(Monetary.class.getName())
                    .log(Level.SEVERE, "Failed to load MonetaryCurrenciesSingletonSpi, using default.", e);
            return new DefaultMonetaryRoundingsSingletonSpi();
        }
    }

    /**
     * Required for deserialization only.
     */
    private Monetary() {
    }

    /**
     * Allows to access the names of the current registered providers.
     *
     * @return the set of provider names, never {@code null}.
     */
    public static Set<String> getRoundingProviderNames() {
        return Optional.ofNullable(MONETARY_ROUNDINGS_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryRoundingsSpi loaded, query functionality is not available."))
                .getProviderNames();
    }

    /**
     * Allows to access the default providers chain used if no provider chain was passed explicitly..
     *
     * @return the chained list of provider names, never {@code null}.
     */
    public static List<String> getDefaultRoundingProviderChain() {
        return Optional.ofNullable(MONETARY_ROUNDINGS_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryRoundingsSpi loaded, query functionality is not available."))
                .getDefaultProviderChain();
    }

    /**
     * Creates a rounding that can be added as {@link MonetaryOperator} to
     * chained calculations. The instance will lookup the concrete
     * {@link MonetaryOperator} instance from the {@link Monetary}
     * based on the input {@link MonetaryAmount}'s {@link CurrencyUnit}.
     *
     * @return the (shared) default rounding instance.
     */
    public static MonetaryRounding getDefaultRounding() {
        return Optional.ofNullable(MONETARY_ROUNDINGS_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryRoundingsSpi loaded, query functionality is not available."))
                .getDefaultRounding();
    }

    /**
     * Creates an {@link MonetaryOperator} for rounding {@link MonetaryAmount}
     * instances given a currency.
     *
     * @param currencyUnit The currency, which determines the required scale. As
     *                     {@link java.math.RoundingMode}, by default, {@link java.math.RoundingMode#HALF_UP}
     *                     is used.
     * @param providers    the providers and ordering to be used. By default providers and ordering as defined in
     *                     #getDefaultProviders is used.
     * @return a new instance {@link MonetaryOperator} implementing the
     * rounding, never {@code null}.
     */
    public static MonetaryRounding getRounding(CurrencyUnit currencyUnit, String... providers) {
        return Optional.ofNullable(MONETARY_ROUNDINGS_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryRoundingsSpi loaded, query functionality is not available."))
                .getRounding(currencyUnit, providers);
    }

    /**
     * Access an {@link MonetaryOperator} for custom rounding
     * {@link MonetaryAmount} instances.
     *
     * @param roundingName The rounding identifier.
     * @param providers    the providers and ordering to be used. By default providers and ordering as defined in
     *                     #getDefaultProviders is used.
     * @return the corresponding {@link MonetaryOperator} implementing the
     * rounding, never {@code null}.
     * @throws IllegalArgumentException if no such rounding is registered using a
     *                                  {@link javax.money.spi.RoundingProviderSpi} instance.
     */
    public static MonetaryRounding getRounding(String roundingName, String... providers) {
        return Optional.ofNullable(MONETARY_ROUNDINGS_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryRoundingsSpi loaded, query functionality is not available."))
                .getRounding(roundingName, providers);
    }

    /**
     * Access a {@link MonetaryRounding} using a possibly complex query.
     *
     * @param roundingQuery The {@link javax.money.RoundingQuery} that may contains arbitrary parameters to be
     *                      evaluated.
     * @return the corresponding {@link javax.money.MonetaryRounding}, never {@code null}.
     * @throws IllegalArgumentException if no such rounding is registered using a
     *                                  {@link javax.money.spi.RoundingProviderSpi} instance.
     */
    public static MonetaryRounding getRounding(RoundingQuery roundingQuery) {
        return Optional.ofNullable(MONETARY_ROUNDINGS_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryRoundingsSpi loaded, query functionality is not available."))
                .getRounding(roundingQuery);
    }

    /**
     * Checks if a {@link MonetaryRounding} is available given a roundingId.
     *
     * @param roundingName The rounding identifier.
     * @param providers    the providers and ordering to be used. By default providers and ordering as defined in
     *                     #getDefaultProviders is used.
     * @return true, if a corresponding {@link javax.money.MonetaryRounding} is available.
     * @throws IllegalArgumentException if no such rounding is registered using a
     *                                  {@link javax.money.spi.RoundingProviderSpi} instance.
     */
    public static boolean isRoundingAvailable(String roundingName, String... providers) {
        return Optional.ofNullable(MONETARY_ROUNDINGS_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryRoundingsSpi loaded, query functionality is not available."))
                .isRoundingAvailable(roundingName, providers);
    }

    /**
     * Checks if a {@link MonetaryRounding} is available given a roundingId.
     *
     * @param currencyUnit The currency, which determines the required scale. As {@link java.math.RoundingMode},
     *                     by default, {@link java.math.RoundingMode#HALF_UP} is used.
     * @param providers    the providers and ordering to be used. By default providers and ordering as defined in
     *                     #getDefaultProviders is used.
     * @return true, if a corresponding {@link javax.money.MonetaryRounding} is available.
     * @throws IllegalArgumentException if no such rounding is registered using a
     *                                  {@link javax.money.spi.RoundingProviderSpi} instance.
     */
    public static boolean isRoundingAvailable(CurrencyUnit currencyUnit, String... providers) {
        return Optional.ofNullable(MONETARY_ROUNDINGS_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryRoundingsSpi loaded, query functionality is not available."))
                .isRoundingAvailable(currencyUnit, providers);
    }

    /**
     * Checks if a {@link MonetaryRounding} matching the query is available.
     *
     * @param roundingQuery The {@link javax.money.RoundingQuery} that may contains arbitrary parameters to be
     *                      evaluated.
     * @return true, if a corresponding {@link javax.money.MonetaryRounding} is available.
     * @throws IllegalArgumentException if no such rounding is registered using a
     *                                  {@link javax.money.spi.RoundingProviderSpi} instance.
     */
    public static boolean isRoundingAvailable(RoundingQuery roundingQuery) {
        return Optional.ofNullable(MONETARY_ROUNDINGS_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryRoundingsSpi loaded, query functionality is not available."))
                .isRoundingAvailable(roundingQuery);
    }


    /**
     * Access multiple {@link MonetaryRounding} instances using a possibly complex query
     *
     * @param roundingQuery The {@link javax.money.RoundingQuery} that may contains arbitrary parameters to be
     *                      evaluated.
     * @return all {@link javax.money.MonetaryRounding} instances matching the query, never {@code null}.
     */
    public static Collection<MonetaryRounding> getRoundings(RoundingQuery roundingQuery) {
        return Optional.ofNullable(MONETARY_ROUNDINGS_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryRoundingsSpi loaded, query functionality is not available."))
                .getRoundings(roundingQuery);
    }


    /**
     * Allows to access the names of the current defined roundings.
     *
     * @param providers the providers and ordering to be used. By default providers and ordering as defined in
     *                  #getDefaultProviders is used.
     * @return the set of custom rounding ids, never {@code null}.
     */
    public static Set<String> getRoundingNames(String... providers) {
        return Optional.ofNullable(MONETARY_ROUNDINGS_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryRoundingsSpi loaded, query functionality is not available."))
                .getRoundingNames(providers);
    }

    /**
     * Access an {@link MonetaryAmountFactory} for the given {@link MonetaryAmount} implementation
     * type.
     *
     * @param amountType {@link MonetaryAmount} implementation type, nor {@code null}.
     * @return the corresponding {@link MonetaryAmountFactory}, never {@code null}.
     * @throws MonetaryException if no {@link MonetaryAmountFactory} targeting the given {@link MonetaryAmount}
     *                           implementation class is registered.
     */
    public static <T extends MonetaryAmount> MonetaryAmountFactory<T> getAmountFactory(Class<T> amountType) {
        Optional.ofNullable(MONETARY_AMOUNTS_SINGLETON_SPI())
                .orElseThrow(() -> new MonetaryException("No MonetaryAmountsSingletonSpi loaded."));

        MonetaryAmountFactory<T> factory = MONETARY_AMOUNTS_SINGLETON_SPI().getAmountFactory(amountType);
        return Optional.ofNullable(factory).orElseThrow(
                () -> new MonetaryException("No AmountFactory available for type: " + amountType.getName()));
    }

    /**
     * Access the default {@link MonetaryAmountFactory} as defined by
     * {@link javax.money.spi.MonetaryAmountsSingletonSpi#getDefaultAmountFactory()}.
     *
     * @return the {@link MonetaryAmountFactory} corresponding to default amount type,
     * never {@code null}.
     * @throws MonetaryException if no {@link MonetaryAmountFactory} targeting the default amount type
     *                           implementation class is registered.
     */
    public static MonetaryAmountFactory<?> getDefaultAmountFactory() {
        return Optional.ofNullable(MONETARY_AMOUNTS_SINGLETON_SPI())
                .orElseThrow(() -> new MonetaryException("No MonetaryAmountsSingletonSpi loaded."))
                .getDefaultAmountFactory();
    }

    /**
     * Access all currently available {@link MonetaryAmount} implementation classes that are
     * accessible from this {@link MonetaryAmount} singleton.
     *
     * @return all currently available {@link MonetaryAmount} implementation classes that have
     * corresponding {@link MonetaryAmountFactory} instances provided, never {@code null}
     */
    public static Collection<MonetaryAmountFactory<?>> getAmountFactories() {
        return Optional.ofNullable(MONETARY_AMOUNTS_SINGLETON_SPI())
                .orElseThrow(() -> new MonetaryException("No MonetaryAmountsSingletonSpi loaded."))
                .getAmountFactories();
    }

    /**
     * Access all currently available {@link MonetaryAmount} implementation classes that are
     * accessible from this {@link MonetaryAmount} singleton.
     *
     * @return all currently available {@link MonetaryAmount} implementation classes that have
     * corresponding {@link MonetaryAmountFactory} instances provided, never {@code null}
     */
    public static Collection<Class<? extends MonetaryAmount>> getAmountTypes() {
        return Optional.ofNullable(MONETARY_AMOUNTS_SINGLETON_SPI())
                .orElseThrow(() -> new MonetaryException("No MonetaryAmountsSingletonSpi loaded.")).getAmountTypes();
    }

    /**
     * Access the default {@link MonetaryAmount} implementation class that is
     * accessible from this {@link MonetaryAmount} singleton.
     *
     * @return all current default {@link MonetaryAmount} implementation class, never {@code null}
     */
    public static Class<? extends MonetaryAmount> getDefaultAmountType() {
        return Optional.ofNullable(MONETARY_AMOUNTS_SINGLETON_SPI())
                .orElseThrow(() -> new MonetaryException("No MonetaryAmountsSingletonSpi loaded."))
                .getDefaultAmountType();
    }

    /**
     * Executes the query and returns the factory found, if there is only one factory.
     * If multiple factories match the query, one is selected.
     *
     * @param query the factory query, not null.
     * @return the factory found, or null.
     */
    @SuppressWarnings("rawtypes")
	public static MonetaryAmountFactory getAmountFactory(MonetaryAmountFactoryQuery query) {
        return Optional.ofNullable(MONETARY_AMOUNTS_SINGLETON_QUERY_SPI()).orElseThrow(() -> new MonetaryException(
                "No MonetaryAmountsSingletonQuerySpi loaded, query functionality is not available."))
                .getAmountFactory(query);
    }

    /**
     * Returns all factory instances that match the query.
     *
     * @param query the factory query, not null.
     * @return the instances found, never null.
     */
    public static Collection<MonetaryAmountFactory<?>> getAmountFactories(MonetaryAmountFactoryQuery query) {
        return Optional.ofNullable(MONETARY_AMOUNTS_SINGLETON_QUERY_SPI()).orElseThrow(() -> new MonetaryException(
                "No MonetaryAmountsSingletonQuerySpi loaded, query functionality is not available."))
                .getAmountFactories(query);
    }

    /**
     * Allows to check if any of the <i>get</i>XXX methods return non empty/non null results of {@link javax.money
     * .MonetaryAmountFactory}.
     *
     * @param query the factory query, not null.
     * @return true, if at least one {@link MonetaryAmountFactory} matches the query.
     */
    public static boolean isAvailable(MonetaryAmountFactoryQuery query) {
        return Optional.ofNullable(MONETARY_AMOUNTS_SINGLETON_QUERY_SPI()).orElseThrow(() -> new MonetaryException(
                "No MonetaryAmountsSingletonQuerySpi loaded, query functionality is not available."))
                .isAvailable(query);
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
        return Optional.ofNullable(MONETARY_CURRENCIES_SINGLETON_SPI()).orElseThrow(
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
        return Optional.ofNullable(MONETARY_CURRENCIES_SINGLETON_SPI()).orElseThrow(
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
        return Optional.ofNullable(MONETARY_CURRENCIES_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup."))
                .getCurrencies(locale, providers);
    }

    /**
     * Allows to check if a {@link CurrencyUnit} instance is defined, i.e.
     * accessible from {@link Monetary#getCurrency(String, String...)}.
     *
     * @param code      the currency code, not {@code null}.
     * @param providers the (optional) specification of providers to consider.
     * @return {@code true} if {@link Monetary#getCurrency(String, java.lang.String...)}
     * would return a result for the given code.
     */
    public static boolean isCurrencyAvailable(String code, String... providers) {
        return Objects.nonNull(MONETARY_CURRENCIES_SINGLETON_SPI()) && MONETARY_CURRENCIES_SINGLETON_SPI().isCurrencyAvailable(code, providers);
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
        return Objects.nonNull(MONETARY_CURRENCIES_SINGLETON_SPI()) && MONETARY_CURRENCIES_SINGLETON_SPI().isCurrencyAvailable(locale, providers);
    }

    /**
     * Access all currencies known.
     *
     * @param providers the (optional) specification of providers to consider.
     * @return the list of known currencies, never null.
     */
    public static Collection<CurrencyUnit> getCurrencies(String... providers) {
        return Optional.ofNullable(MONETARY_CURRENCIES_SINGLETON_SPI()).orElseThrow(
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
        return Optional.ofNullable(MONETARY_CURRENCIES_SINGLETON_SPI()).orElseThrow(
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
        return Optional.ofNullable(MONETARY_CURRENCIES_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup."))
                .getCurrencies(query);
    }

    /**
     * Query all currencies matching the given query.
     *
     * @return the list of known currencies, never null.
     */
    public static Set<String> getCurrencyProviderNames() {
        return Optional.ofNullable(MONETARY_CURRENCIES_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup."))
                .getProviderNames();
    }

    /**
     * Query the list and ordering of provider names modelling the default provider chain to be used, if no provider
     * chain was explicitly set..
     *
     * @return the ordered list provider names, modelling the default provider chain used, never null.
     */
    public static List<String> getDefaultCurrencyProviderChain() {
        return Optional.ofNullable(MONETARY_CURRENCIES_SINGLETON_SPI()).orElseThrow(
                () -> new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup."))
                .getDefaultProviderChain();
    }


}
