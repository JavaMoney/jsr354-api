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

import javax.money.CurrencyUnit;
import javax.money.convert.*;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

/**
 * SPI (conversoin) that implements the functionalities provided by the
 * {@code MonetaryConversions} singleton accessor. It should be registered as a
 * service using the JDK {@code ServiceLoader}. Hereby only one instance can be
 * registered at a time.<br/>
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
public interface MonetaryConversionsSingletonSpi{

    /**
     * Access an instance of {@link ExchangeRateProvider}.
     *
     * @param providers The providers to be used, in order of precedence, for building
     *                  a provider chain. At least one provider must be passed.
     * @return an {@link ExchangeRateProvider} built up with the given sub
     * providers, never {@code null}
     * @throws IllegalArgumentException if a provider could not be found or not at least one provider
     *                                  name is passed.
     * @see #isProviderAvailable(String)
     */
    ExchangeRateProvider getExchangeRateProvider(String... providers);

    /**
     * Get all currently registered provider names.
     *
     * @return all currently registered provider names
     * @see ProviderContext#getProvider()
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
     * Allows to quickly check, if a {@link ProviderContext} is supported.
     *
     * @param provider The provider required, not {@code null}
     * @return {@code true}, if the rate is supported, meaning an according
     * {@link ExchangeRateProvider} or {@link CurrencyConversion} can be
     * loaded.
     * @see #getConversion(CurrencyUnit, ConversionContext, String...)
     * @see #getExchangeRateProvider(String...)
     */
    default boolean isProviderAvailable(String provider){
        return getProviderNames().contains(provider);
    }


    /**
     * Get the {@link ProviderContext} for a provider.
     *
     * @param provider the provider name, not {@code null}.
     * @return the corresponding {@link ProviderContext}, not {@code null}.
     * @throws IllegalArgumentException if no such provider is registered.
     */
    default ProviderContext getProviderContext(String provider){
        return getExchangeRateProvider(provider).getProviderContext();
    }

    /**
     * Access an instance of {@link CurrencyConversion}.
     *
     * @param termCurrency      the terminating or target currency, not {@code null}
     * @param conversionContext The {@link ConversionContext} required, not {@code null}
     * @param providers         The providers to be used, in order of precedence, for building
     *                          a provider chain. At least one provider must be passed.
     * @return the provider, if it is a registered rate type, never null.
     * @throws IllegalArgumentException if a provider could not be found or not at least one provider
     *                                  name is passed.
     * @see #isProviderAvailable(String)
     */
    default CurrencyConversion getConversion(CurrencyUnit termCurrency, ConversionContext conversionContext,
                                     String... providers){
        return getExchangeRateProvider(providers).getCurrencyConversion(termCurrency, conversionContext);
    }

}