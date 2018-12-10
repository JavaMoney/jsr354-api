/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
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
package javax.money.spi;

import org.testng.annotations.Test;

import javax.money.*;
import javax.money.convert.*;
import java.util.*;

import static org.testng.AssertJUnit.*;

/**
 * Tests the default methods on MonetaryAmountsSingletonSpi.
 */
public class MonetaryConversionsSingletonSpiTest {

    private final MonetaryConversionsSingletonSpi testSpi = new MonetaryConversionsSingletonSpi() {

        @Override
        public Collection<String> getProviderNames() {
            return Arrays.asList("b", "a");
        }

        @Override
        public List<String> getDefaultProviderChain() {
            return Arrays.asList("a", "b");
        }

        @Override
        public ExchangeRateProvider getExchangeRateProvider(ConversionQuery conversionQuery) {
            if (conversionQuery.getProviderNames().contains("a")) {
                return new ExchangeRateProvider() {
                    @Override
                    public ProviderContext getContext() {
                        return ProviderContext.of("a");
                    }

                    @Override
                    public ExchangeRate getExchangeRate(ConversionQuery conversionQuery) {
                        return null;
                    }

                    @Override
                    public CurrencyConversion getCurrencyConversion(ConversionQuery conversionQuery) {
                        return new CurrencyConversion() {
                            @Override
                            public ConversionContext getContext() {
                                return null;
                            }

                            @Override
                            public ExchangeRate getExchangeRate(MonetaryAmount sourceAmount) {
                                return null;
                            }

                            @Override
                            public ExchangeRateProvider getExchangeRateProvider() {
                                return null;
                            }

                            @Override
                            public CurrencyUnit getCurrency() {
                                return null;
                            }

                            @Override
                            public MonetaryAmount apply(MonetaryAmount monetaryAmount) {
                                return null;
                            }
                        };
                    }
                };
            } else if (conversionQuery.getProviderNames().contains("b")) {
                return new ExchangeRateProvider() {
                    @Override
                    public ProviderContext getContext() {
                        return ProviderContext.of("b");
                    }

                    @Override
                    public ExchangeRate getExchangeRate(ConversionQuery conversionQuery) {
                        return null;
                    }

                    @Override
                    public CurrencyConversion getCurrencyConversion(ConversionQuery conversionQuery) {
                        return new CurrencyConversion() {
                            @Override
                            public ConversionContext getContext() {
                                return null;
                            }

                            @Override
                            public ExchangeRate getExchangeRate(MonetaryAmount sourceAmount) {
                                return null;
                            }

                            @Override
                            public ExchangeRateProvider getExchangeRateProvider() {
                                return null;
                            }

                            @Override
                            public CurrencyUnit getCurrency() {
                                return null;
                            }

                            @Override
                            public MonetaryAmount apply(MonetaryAmount monetaryAmount) {
                                return null;
                            }
                        };
                    }
                };
            }
            return null;
        }
    };

    @Test
    public void testGetExchangeRateProvider() {
        assertNotNull(testSpi.getExchangeRateProvider("a"));
        assertNull(testSpi.getExchangeRateProvider("foo"));
    }

    @Test
    public void testGetExchangeRateProviders() {
        assertFalse(testSpi.getExchangeRateProviders("a").isEmpty());
        assertFalse(testSpi.getExchangeRateProviders("b").isEmpty());
        assertFalse(testSpi.getExchangeRateProviders("a", "b").isEmpty());
    }

    @Test(expectedExceptions = {MonetaryException.class})
    public void testGetExchangeRateProviders_BC1() {
        assertTrue(testSpi.getExchangeRateProviders("foo").isEmpty());
    }

    @Test(expectedExceptions = {MonetaryException.class})
    public void testGetExchangeRateProviders_BC2() {
        assertTrue(testSpi.getExchangeRateProviders("foo", "a").isEmpty());
    }

    @Test(expectedExceptions = {MonetaryException.class})
    public void testGetExchangeRateProviders_BC3() {
        assertTrue(testSpi.getExchangeRateProviders("a", "foo").isEmpty());
    }

    @Test
    public void testIsConversionAvailable() {
        assertTrue(testSpi.isConversionAvailable(ConversionQueryBuilder.of().setProviderNames("a").setTermCurrency(TestCurrency.of("CHF")).build()));
        assertTrue(testSpi.isConversionAvailable(ConversionQueryBuilder.of().setProviderNames("b").setTermCurrency(TestCurrency.of("CHF")).build()));
        assertTrue(testSpi.isConversionAvailable(ConversionQueryBuilder.of().setProviderNames("b", "b").setTermCurrency(TestCurrency.of("CHF")).build()));
        assertFalse(testSpi.isConversionAvailable(ConversionQueryBuilder.of().setProviderNames("foo").setTermCurrency(TestCurrency.of("CHF")).build()));
    }

}
