/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
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
