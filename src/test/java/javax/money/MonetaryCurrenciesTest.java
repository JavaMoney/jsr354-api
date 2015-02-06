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
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Tests for the {@link MonetaryCurrencies} class.
 *
 * @author Anatole Tresch
 */
public class MonetaryCurrenciesTest {

    @Test
    public void testgetCurrencyString() {
        CurrencyUnit cur = MonetaryCurrencies.getCurrency("test1");
        assertNotNull(cur);
        assertEquals(cur.getCurrencyCode(), "test1");
        assertEquals(cur.getNumericCode(), 1);
        assertEquals(cur.getDefaultFractionDigits(), 2);
    }

    @Test
    public void testIsAvailableString() {
        assertTrue(MonetaryCurrencies.isCurrencyAvailable("test1"));
        assertFalse(MonetaryCurrencies.isCurrencyAvailable("akjshakjshajsgdgsdgsdg"));
    }

    @Test
    public void testIsAvailableLocale() {
        assertFalse(MonetaryCurrencies.isCurrencyAvailable(Locale.CHINA));
        assertTrue(MonetaryCurrencies.isCurrencyAvailable(new Locale("", "TEST1L")));
    }

    @Test(expectedExceptions = UnknownCurrencyException.class)
    public void testgetCurrencyString_NA() {
        MonetaryCurrencies.getCurrency("testGetInstanceCurrency_NA");
    }

    @Test
    public void testgetCurrencyLocale() {
        Collection<CurrencyUnit> curs = MonetaryCurrencies.getCurrencies(new Locale("", "TEST1L"));
        assertNotNull(curs);
        assertEquals(curs.size(), 1);
        CurrencyUnit cur = curs.iterator().next();
        assertEquals(cur.getCurrencyCode(), "TEST1L");
        assertEquals(cur.getNumericCode(), 1);
        assertEquals(cur.getDefaultFractionDigits(), 2);
    }

    @Test
    public void testGetCurrencies_Providers() {
        Collection<CurrencyUnit> curs = MonetaryCurrencies.getCurrencies("test");
        assertNotNull(curs);
    }

    /**
     * Query all currencies matching the given query.
     *
     * @param query The {@link javax.money.CurrencyQuery}, not null.
     * @return the list of known currencies, never null.
     */
    @Test
    public void testGetCurrency_CurrencyQuery() {
        CurrencyUnit cur = MonetaryCurrencies.getCurrency(
                CurrencyQueryBuilder.of().build());
        assertNull(cur);
        cur = MonetaryCurrencies.getCurrency(
                CurrencyQueryBuilder.of().setCurrencyCodes("test1").build());
        assertNotNull(cur);
    }


    /**
     * Query all currencies matching the given query.
     *
     * @param query The {@link javax.money.CurrencyQuery}, not null.
     * @return the list of known currencies, never null.
     */
    @Test
    public void testGetCurrencies_CurrencyQuery() {
        Collection<CurrencyUnit> currencies = MonetaryCurrencies.getCurrencies(
                CurrencyQueryBuilder.of().build()
        );
        assertNotNull(currencies);
    }

    /**
     * Query all currencies matching the given query.
     *
     * @return the list of known currencies, never null.
     */
    @Test
    public void testGetProviderNames() {
        Set<String> chain = MonetaryCurrencies.getProviderNames();
        assertNotNull(chain);
    }

    /**
     * Query the list and ordering of provider names modelling the default provider chain to be used, if no provider
     * chain was explicitly set..
     *
     * @return the orderend list provider names, modelling the default provider chain used, never null.
     */
    @Test
    public void testGetDefaultProviderChain() {
        List<String> chain = MonetaryCurrencies.getDefaultProviderChain();
        assertNotNull(chain);
    }

    @Test
    public void testgetCurrencyLocale_Empty() {
        Collection<CurrencyUnit> curs = MonetaryCurrencies.getCurrencies(Locale.CHINA);
        assertNotNull(curs);
        assertTrue(curs.isEmpty());
        curs = MonetaryCurrencies.getCurrencies(new Locale("", "sdsdsd"));
        assertNotNull(curs);
        assertTrue(curs.isEmpty());
    }

    @Test(expectedExceptions = UnknownCurrencyException.class)
    public void testgetCurrencyString_Error() {
        CurrencyUnit cur = MonetaryCurrencies.getCurrency("error");
        assertNull(cur);
        cur = MonetaryCurrencies.getCurrency("invalid");
        assertNull(cur);
    }


}
