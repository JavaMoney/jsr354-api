/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
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
