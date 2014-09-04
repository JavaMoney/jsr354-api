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
 * Created by Anatole on 05.03.14.
 */
public class MonetaryConversionsTest{
    @Test
    public void testGetConversion() throws Exception{
        assertNotNull(MonetaryConversions.getConversion(TestCurrency.of("CHF")));
    }

    @Test
    public void testGetConversion1() throws Exception{
        assertNotNull(MonetaryConversions.getConversion(
                ConversionQueryBuilder.of().setTermCurrency(TestCurrency.of("CHF")).build()));
    }

    @Test
    public void testGetConversion2() throws Exception{
        assertNotNull(MonetaryConversions.getConversion(
                ConversionQueryBuilder.of().setTermCurrency(TestCurrency.of("CHF")).setProviders("test").build()));
    }

    @Test
    public void testGetConversion3() throws Exception{
        assertNotNull(MonetaryConversions.getConversion(TestCurrency.of("CHF"), "test"));
    }

    @Test
    public void testGetExchangeRateProvider() throws Exception{
        assertNotNull(MonetaryConversions.getExchangeRateProvider("test"));
    }

    @Test(expectedExceptions = MonetaryException.class)
    public void testGetExchangeRateProvider_Invalid() throws Exception{
        MonetaryConversions.getExchangeRateProvider("fooBarAnyBla");
    }

    @Test
    public void testGetProviderNames() throws Exception{
        assertNotNull(MonetaryConversions.getProviderNames());
        assertTrue(MonetaryConversions.getProviderNames().contains("test"));
        assertTrue(MonetaryConversions.getProviderNames().size() == 1);
    }

    @Test
    public void testGetDefaultProviderChain() throws Exception{
        assertNotNull(MonetaryConversions.getDefaultProviderChain());
        assertFalse(MonetaryConversions.getDefaultProviderChain().isEmpty());
        assertEquals(1, MonetaryConversions.getDefaultProviderChain().size());
    }
}
