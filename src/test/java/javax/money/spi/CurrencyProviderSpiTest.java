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
 * Copyright (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import org.testng.annotations.Test;

import javax.money.CurrencyQueryBuilder;
import javax.money.CurrencyUnit;
import javax.money.TestCurrency;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Test class to test the default methods of CurrencyProviderSpi.
 */
public class CurrencyProviderSpiTest {

    private static final CurrencyProviderSpi testProvider = query -> {
        Set<CurrencyUnit> result = new HashSet<>();
        if (query.getCurrencyCodes().contains("CHF")) {
            result.add(TestCurrency.of("CHF"));
        }
        return result;
    };

    @Test
    public void testGetProviderName() throws Exception {
        assertEquals(testProvider.getProviderName(), testProvider.getClass().getSimpleName());

    }

    @Test
    public void testIsCurrencyAvailable() throws Exception {
        assertTrue(testProvider.isCurrencyAvailable(CurrencyQueryBuilder.of().setCurrencyCodes("CHF").build()));
        assertFalse(testProvider.isCurrencyAvailable(CurrencyQueryBuilder.of().setCurrencyCodes("foofoo").build()));
    }

}