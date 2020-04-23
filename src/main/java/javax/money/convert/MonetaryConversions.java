/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2020 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.money.convert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryException;
import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryConversionsSingletonSpi;

/**
 * This singleton defines access to the exchange and currency conversion logic
 * of JavaMoney. It allows to evaluate the currently available exchange rate
 * type instances and provides access to the corresponding
 * {@link ExchangeRateProvider} and {@link javax.money.convert.CurrencyConversion} instances.
 * <p>
 * This class is thread safe.
 * <p>
 * This class is designed to support also contextual behaviour, e.g. in Java EE
 * containers each application may provide its own {@link ExchangeRateProvider}
 * instances, e.g. by registering them as CDI beans. An EE container can
 * register an according {@link javax.money.spi.MonetaryConversionsSingletonSpi} that manages the
 * different application contexts transparently. In a SE environment this class
 * is expected to behave like an ordinary singleton, loading its SPIs e.g. from the
 * JDK {@link ServiceLoader} or an alternate component and service provider.
 * <p>
 * This class is thread-safe. Hereby it is important to know that it delegates
 * to the registered {@link javax.money.spi.MonetaryConversionsSingletonSpi} SPI, which also is required
 * to be thread-safe.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryConversions{

    /**
     * The SPI currently active, use {@link ServiceLoader} to register an
     * alternate implementation.
     */
    private static MonetaryConversionsSingletonSpi getMonetaryConversionsSpi() {
        return Optional.ofNullable(Bootstrap.getService(MonetaryConversionsSingletonSpi.class))
                .orElseThrow(() -> new MonetaryException("No MonetaryConversionsSingletonSpi " +
                        "loaded, " +
                        "query functionality is not " +
                        "available."));
    }

    /**
     * Private singleton constructor.
     */
    private MonetaryConversions(){
    }

    /**
     * Access an instance of {@link CurrencyConversion} for the given providers.
     * Use {@link #getConversionProviderNames()} to check, which are available.
     *
     * @param termCurrency the terminating or target currency, not {@code null}
     * @param providers    Additional providers, for building a provider chain
     * @return the exchange rate type if this instance.
     * @throws IllegalArgumentException if no such {@link ExchangeRateProvider} is available.
     */
    public static CurrencyConversion getConversion(CurrencyUnit termCurrency, String... providers){
        Objects.requireNonNull(providers);
        Objects.requireNonNull(termCurrency);
        if(providers.length == 0){
            return getMonetaryConversionsSpi().getConversion(
                    ConversionQueryBuilder.of().setTermCurrency(termCurrency).setProviderNames(getDefaultConversionProviderChain())
                            .build());
        }
        return getMonetaryConversionsSpi().getConversion(
                ConversionQueryBuilder.of().setTermCurrency(termCurrency).setProviderNames(providers).build());
    }

    /**
     * Access an instance of {@link CurrencyConversion} for the given providers.
     * Use {@link #getConversionProviderNames()}} to check, which are available.
     *
     * @param termCurrencyCode the terminating or target currency code, not {@code null}
     * @param providers        Additional providers, for building a provider chain
     * @return the exchange rate type if this instance.
     * @throws MonetaryException if no such {@link ExchangeRateProvider} is available or if no {@link CurrencyUnit} was
     *                           matching the given currency code.
     */
    public static CurrencyConversion getConversion(String termCurrencyCode, String... providers){
        Objects.requireNonNull(termCurrencyCode, "Term currency code may not be null");
        return getConversion(Monetary.getCurrency(termCurrencyCode), providers);
    }

    /**
     * Access an instance of {@link CurrencyConversion} for the given providers.
     * Use {@link #getConversionProviderNames()}} to check, which are available.
     *
     * @param conversionQuery The {@link ConversionQuery} required, not {@code null}
     * @return the {@link CurrencyConversion}  instance matching.
     * @throws IllegalArgumentException if the query defines {@link ExchangeRateProvider}s that are not available.
     */
    public static CurrencyConversion getConversion(ConversionQuery conversionQuery){
        return getMonetaryConversionsSpi().getConversion(conversionQuery);
    }

    /**
     * Checks if a {@link javax.money.convert.CurrencyConversion} is available for the given parameters.
     *
     * @param conversionQuery the {@link javax.money.convert.ConversionQuery}, not null.
     * @return true, if a conversion is accessible from {@link #getConversion(ConversionQuery)}.
     */
    public static boolean isConversionAvailable(ConversionQuery conversionQuery){
        return getMonetaryConversionsSpi().isConversionAvailable(conversionQuery);
    }

    /**
     * Checks if a {@link javax.money.convert.CurrencyConversion} is available for the given parameters.
     *
     * @param currencyCode The currency code, resolvable by {@link javax.money.Monetary#getCurrency
     *                     (String, String...)}
     * @param providers    Additional providers, for building a provider chain
     * @return true, if a conversion is accessible from {@link #getConversion(String, String...)}.
     */
    public static boolean isConversionAvailable(String currencyCode, String... providers){
        return getMonetaryConversionsSpi()
                .isConversionAvailable(Monetary.getCurrency(currencyCode), providers);
    }

    /**
     * Checks if a {@link javax.money.convert.CurrencyConversion} is available for the given parameters.
     *
     * @param termCurrency the terminating or target currency, not {@code null}
     * @param providers    Additional providers, for building a provider chain
     * @return true, if a conversion is accessible from {@link #getConversion(String, String...)}.
     */
    public static boolean isConversionAvailable(CurrencyUnit termCurrency, String... providers){
        return getMonetaryConversionsSpi()
                .isConversionAvailable(termCurrency, providers);
    }

    /**
     * Access an instance of {@link CurrencyConversion} using the given
     * providers as a provider chain. Use {@link #getConversionProviderNames()}s
     * to check, which are available.
     *
     * @param providers    Additional providers, for building a provider chain
     * @return the exchange rate provider.
     * @throws IllegalArgumentException if no such {@link ExchangeRateProvider} is available.
     */
    public static ExchangeRateProvider getExchangeRateProvider(String... providers){
        if(providers.length == 0){
            List<String> defaultProviderChain = getDefaultConversionProviderChain();
            return getMonetaryConversionsSpi().getExchangeRateProvider(ConversionQueryBuilder.of().setProviderNames(
                    defaultProviderChain.toArray(new String[defaultProviderChain.size()])).build());
        }
        ExchangeRateProvider provider = getMonetaryConversionsSpi()
                .getExchangeRateProvider(ConversionQueryBuilder.of().setProviderNames(providers).build());
        return Optional.ofNullable(provider)
                .orElseThrow(() -> new MonetaryException("No such rate provider: " + Arrays.toString(providers)));
    }

	/**
	 * Access an instance of {@link CurrencyConversion} using the
	 * {@link ExchangeRateProviderSupplier}.
	 *
	 * @param provider
	 *            the exchange rate provider.
	 * @param providers
	 *            the exchange rate provider.
	 * @return the exchange rate provider.
	 * @throws IllegalArgumentException
	 *             if no such {@link ExchangeRateProvider} is available.
	 */
	public static ExchangeRateProvider getExchangeRateProvider(
			ExchangeRateProviderSupplier provider,
			ExchangeRateProviderSupplier... providers) {

		List<ExchangeRateProviderSupplier> suppliers = new ArrayList<>();
		suppliers.add(Objects.requireNonNull(provider));
        suppliers.addAll(Arrays.asList(providers));

		String[] array = suppliers.stream()
				.map(ExchangeRateProviderSupplier::get).toArray(String[]::new);
		return getExchangeRateProvider(array);

	}
    /**
     * Access an instance of {@link CurrencyConversion} using the given
     * providers as a provider chain. Use {@link #getConversionProviderNames()}
     * to check, which are available.
     *
     * @param conversionQuery the {@link javax.money.convert.ConversionQuery}, not null.
     * @return the exchange rate provider.
     * @throws IllegalArgumentException if no such {@link ExchangeRateProvider} is available.
     */
    public static ExchangeRateProvider getExchangeRateProvider(ConversionQuery conversionQuery){
        return getMonetaryConversionsSpi()
                .getExchangeRateProvider(conversionQuery);
    }

    /**
     * Checks if a {@link javax.money.convert.ExchangeRateProvider} is available for the given parameters.
     *
     * @param conversionQuery the {@link javax.money.convert.ConversionQuery}, not null.
     * @return true, if a rate provider is accessible from {@link #getExchangeRateProvider(ConversionQuery)}}.
     */
    public static boolean isExchangeRateProviderAvailable(ConversionQuery conversionQuery){
        return getMonetaryConversionsSpi()
                .isExchangeRateProviderAvailable(conversionQuery);
    }


    /**
     * Return the (non localized) names of all providers available in the
     * current context. Each id can be used to obtain
     * {@link ExchangeRateProvider} or {@link CurrencyConversion} instances.
     *
     * @return all supported provider ids, never {@code null}.
     */
    public static Collection<String> getConversionProviderNames(){
        Collection<String> providers = getMonetaryConversionsSpi().getProviderNames();
        if(Objects.isNull(providers)){
            Logger.getLogger(MonetaryConversions.class.getName()).warning(
                    "No supported rate/conversion providers returned by SPI: " +
                            getMonetaryConversionsSpi().getClass().getName());
            return Collections.emptySet();
        }
        return providers;
    }

    /**
     * Get the default provider used.
     *
     * @return the default provider, never {@code null}.
     */
    public static List<String> getDefaultConversionProviderChain(){
        List<String> defaultChain = getMonetaryConversionsSpi()
                .getDefaultProviderChain();
        Objects.requireNonNull(defaultChain, "No default provider chain provided by SPI: " +
                getMonetaryConversionsSpi().getClass().getName());
        return defaultChain;
    }

}
