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
package javax.money.convert;


import org.testng.annotations.Test;

import javax.money.MonetaryException;
import javax.money.TestCurrency;

import static org.testng.Assert.*;

/**
 * Tests for {@link javax.money.convert.MonetaryConversions}.
 */
public class MonetaryConversionsTest {
    @Test
    public void testGetConversion() throws Exception {
        assertNotNull(MonetaryConversions.getConversion(TestCurrency.of("CHF")));
    }

    @Test
    public void testGetConversion1() throws Exception {
        assertNotNull(MonetaryConversions.getConversion(
                ConversionQueryBuilder.of().setTermCurrency(TestCurrency.of("CHF")).build()));
    }

    @Test
    public void testGetExchangeRateProvider1() throws Exception {
        assertNotNull(MonetaryConversions.getExchangeRateProvider(
                ConversionQueryBuilder.of().setTermCurrency(TestCurrency.of("CHF")).build()));
    }

    @Test
    public void testIsConversionAvailable1_Query() throws Exception {
        assertTrue(MonetaryConversions.isConversionAvailable(
                ConversionQueryBuilder.of().setTermCurrency(TestCurrency.of("CHF")).build()));
    }

    @Test
    public void testIsExchangeRateProviderAvailable1_Query() throws Exception {
        assertTrue(MonetaryConversions.isExchangeRateProviderAvailable(
                ConversionQueryBuilder.of().setTermCurrency(TestCurrency.of("CHF")).build()));
    }

    @Test
    public void testGetConversion2() throws Exception {
        assertNotNull(MonetaryConversions.getConversion(
                ConversionQueryBuilder.of().setTermCurrency(TestCurrency.of("CHF")).setProviderNames("test").build()));
        try {
            MonetaryConversions.getConversion(
                    ConversionQueryBuilder.of().setTermCurrency(TestCurrency.of("CHF")).setProviderNames("foo").build());
            fail("Should throw MonetaryException");
        } catch (MonetaryException e) {
            // OK
        }
    }

    @Test
    public void testIsConversionAvailable2() throws Exception {
        assertTrue(MonetaryConversions.isConversionAvailable(
                ConversionQueryBuilder.of().setTermCurrency(TestCurrency.of("CHF")).setProviderNames("test").build()));
    }

    @Test
    public void testGetConversion_String_StringArr() throws Exception {
        assertNotNull(MonetaryConversions.getConversion("test1", "test"));
        assertNotNull(MonetaryConversions.getConversion("test1"));
        try {
            MonetaryConversions.getConversion("test1", "foo");
            fail("Should throw MonetaryException");
        } catch (MonetaryException e) {
            // OK
        }
    }

    @Test
    public void testIsConversionAvailable_String_StringArr() throws Exception {
        assertTrue(MonetaryConversions.isConversionAvailable("test1", "test"));
        assertTrue(MonetaryConversions.isConversionAvailable("test1"));
        assertFalse(MonetaryConversions.isConversionAvailable("test1", "foo"));
    }

    @Test
    public void testGetConversion3() throws Exception {
        assertNotNull(MonetaryConversions.getConversion(TestCurrency.of("CHF"), "test"));
    }

    @Test
    public void testGetExchangeRateProvider() throws Exception {
        assertNotNull(MonetaryConversions.getExchangeRateProvider("test"));
    }

    @Test(expectedExceptions = MonetaryException.class)
    public void testGetExchangeRateProvider_Invalid() throws Exception {
        MonetaryConversions.getExchangeRateProvider("fooBarAnyBla");
    }

    @Test
    public void testGetProviderNames() throws Exception {
        assertNotNull(MonetaryConversions.getConversionProviderNames());
        assertTrue(MonetaryConversions.getConversionProviderNames().contains("test"));
        assertTrue(MonetaryConversions.getConversionProviderNames().size() == 1);
    }

    @Test
    public void testGetDefaultProviderChain() throws Exception {
        assertNotNull(MonetaryConversions.getDefaultConversionProviderChain());
        assertFalse(MonetaryConversions.getDefaultConversionProviderChain().isEmpty());
        assertEquals(1, MonetaryConversions.getDefaultConversionProviderChain().size());
    }
}
