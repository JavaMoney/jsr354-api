/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package javax.money.spi;

import java.util.*;

import javax.money.CurrencyUnit;
import javax.money.MonetaryException;
import javax.money.convert.*;

/**
 * SPI (conversoin) that implements the functionalities provided by the
 * {@code MonetaryConversions} singleton accessor. It should be registered as a
 * service using the JDK {@code ServiceLoader}. Hereby only one instance can be
 * registered at a time.
 * <p>
 * This interface is designed to support also contextual behaviour, e.g. in Java
 * EE containers each application may provide its own
 * {@link ExchangeRateProvider} instances, e.g. by registering them as CDI
 * beans. An EE container can register an according
 * {@link MonetaryConversionsSingletonSpi} that manages the different application
 * contexts transparently. In a SE environment this class is expected to behave
 * like an ordinary singleton, loading its SPIs from the {@link ServiceLoader}.
 * <p>
 * Instances of this class must be thread safe. It is not a requirement that
 * they are serializable.
 * <p>
 * Only one instance can be registered using the {@link ServiceLoader}. When
 * registering multiple instances the {@link MonetaryConversions} accessor will
 * not work.
 *
 * @author Anatole Tresch
 */
public interface MonetaryConversionsSingletonSpi {

    /**
     * Get all currently registered provider names.
     *
     * @return all currently registered provider names
     * @see ProviderContext#getProviderName()
     */
    Collection<String> getProviderNames();


    /**
     * Get the default provider chain used. The ordering of the items is the
     * access order/precedence of the providers.
     *
     * @return the default provider chain, not {@code null} and not empty.
     */
    List<String> getDefaultProviderChain();

    /**
     * Access an instance of {@link ExchangeRateProvider}. By setting {@link javax.money.convert
     * .ConversionQuery#getProviderNames()} multiple providers can be selected,
     * that will be included into a <i>compound</i> instance, with the same order as returned by the {@link javax
     * .money.convert.ConversionQuery}.
     *
     * @param conversionQuery the {@link javax.money.convert.ConversionQuery} determining the tpye of conversion
     *                        required, not null.
     * @return an {@link ExchangeRateProvider} built up with the given sub
     * providers, never {@code null}
     * @throws MonetaryException if a provider could not be found.
     * @see #isExchangeRateProviderAvailable(javax.money.convert.ConversionQuery)
     */
    ExchangeRateProvider getExchangeRateProvider(ConversionQuery conversionQuery);

    /**
     * Allows to quickly check, if a {@link javax.money.convert.ExchangeRateProvider} is accessible for the given
     * {@link javax.money.convert.ConversionQuery}.
     *
     * @param conversionQuery the {@link javax.money.convert.ConversionQuery} determining the tpye of conversion
     *                        required, not null.
     * @return {@code true}, if such a conversion is supported, meaning an according
     * {@link ExchangeRateProvider} can be
     * accessed.
     * @see #getExchangeRateProvider(ConversionQuery)
     * @see #getExchangeRateProvider(String...)}
     */
    default boolean isExchangeRateProviderAvailable(ConversionQuery conversionQuery) {
        try {
            return getExchangeRateProvider(conversionQuery) != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Allows to quickly check, if a {@link javax.money.convert.CurrencyConversion} is accessible for the given
     * {@link javax.money.convert.ConversionQuery}.
     *
     * @param conversionQuery the {@link javax.money.convert.ConversionQuery} determining the tpye of conversion
     *                        required, not null.
     * @return {@code true}, if such a conversion is supported, meaning an according
     * {@link CurrencyConversion} can be
     * accessed.
     * @see #getConversion(javax.money.convert.ConversionQuery)
     * @see #getConversion(CurrencyUnit, String...)}
     */
    default boolean isConversionAvailable(ConversionQuery conversionQuery) {
        try {
            return getConversion(conversionQuery) != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Allows to quickly check, if a {@link javax.money.convert.CurrencyConversion} is accessible for the given
     * {@link javax.money.convert.ConversionQuery}.
     *
     * @param termCurrency the terminating/target currency unit, not null.
     * @param providers    the provider names defines a corresponding
     *                     prpovider chain that must be encapsulated by the resulting {@link javax
     *                     .money.convert.CurrencyConversion}. By default the provider
     *                     chain as defined by #getDefaultProviderChain will be used.
     * @return {@code true}, if such a conversion is supported, meaning an according
     * {@link CurrencyConversion} can be
     * accessed.
     * @see #getConversion(javax.money.convert.ConversionQuery)
     * @see #getConversion(CurrencyUnit, String...)}
     */
    default boolean isConversionAvailable(CurrencyUnit termCurrency, String... providers) {
        return isConversionAvailable(
                ConversionQueryBuilder.of().setTermCurrency(termCurrency).setProviderNames(providers).build());
    }

    /**
     * Access the current registered {@link javax.money.convert.ExchangeRateProvider} instances. If no provider
     * names are passed ALL current registered providers are returned in undefined order.
     *
     * @param providers the provider names of hte providers to be accessed
     * @return the list of providers, in the same order as requested.
     * @throws MonetaryException if a provider could not be resolved.
     */
    default List<ExchangeRateProvider> getExchangeRateProviders(String... providers) {
        List<ExchangeRateProvider> provInstances = new ArrayList<>();
        Collection<String> providerNames = Arrays.asList(providers);
        if (providerNames.isEmpty()) {
            providerNames = getProviderNames();
        }
        for (String provName : providerNames) {
            provInstances.add(Optional.ofNullable(getExchangeRateProvider(provName)).orElseThrow(
                    () -> new MonetaryException("Unsupported conversion/rate provider: " + provName)));
        }
        return provInstances;
    }

    /**
     * Access a compound instance of an {@link ExchangeRateProvider} based on the given provider chain.
     *
     * @param providers the {@link javax.money.convert.ConversionQuery} provider names defines a corresponding
     *                  prpovider chain that must be
     *                  encapsulated by the resulting {@link javax.money.convert.ExchangeRateProvider}. By default
     *                  the default
     *                  provider changes as defined in #getDefaultProviderChain will be used.
     * @return an {@link ExchangeRateProvider} built up with the given sub
     * providers, never {@code null}.
     * @throws MonetaryException if a provider listed could not be found.
     * @see #getProviderNames()
     * @see #isExchangeRateProviderAvailable(javax.money.convert.ConversionQuery)
     */
    default ExchangeRateProvider getExchangeRateProvider(String... providers) {
        return getExchangeRateProvider(ConversionQueryBuilder.of().setProviderNames(providers).build());
    }

    /**
     * Access an instance of {@link CurrencyConversion}.
     *
     * @param conversionQuery the {@link javax.money.convert.ConversionQuery} determining the tpye of conversion
     *                        required, not null.
     * @return the corresponding conversion, not null.
     * @throws javax.money.MonetaryException if no matching conversion could be found.
     * @see #isConversionAvailable(javax.money.convert.ConversionQuery)
     */
    default CurrencyConversion getConversion(ConversionQuery conversionQuery) {
        return getExchangeRateProvider(conversionQuery).getCurrencyConversion(
                Objects.requireNonNull(conversionQuery.getCurrency(), "Terminating Currency is required.")
        );
    }

    /**
     * Access an instance of {@link CurrencyConversion}.
     *
     * @param termCurrency the terminating/target currency unit, not null.
     * @param providers    the {@link javax.money.convert.ConversionQuery} provider names defines a corresponding
     *                     prpovider chain that must be encapsulated by the resulting {@link javax
     *                     .money.convert.CurrencyConversion}. By default the default
     *                     provider chain as defined by #getDefaultProviderChain will be used.
     * @return the correspöonding conversion, not null.
     * @throws javax.money.MonetaryException if no matching conversion could be found.
     * @see #isConversionAvailable(javax.money.convert.ConversionQuery)
     */
    default CurrencyConversion getConversion(CurrencyUnit termCurrency, String... providers) {
        return getConversion(ConversionQueryBuilder.of().setTermCurrency(termCurrency).setProviderNames(providers).build());
    }

}
