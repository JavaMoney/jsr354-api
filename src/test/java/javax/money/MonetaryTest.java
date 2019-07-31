/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2019 Werner Keil, Otavio Santana, Trivadis AG
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
package javax.money;

import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Tests for the {@link Monetary} class.
 *
 * @author Anatole Tresch
 */
public class MonetaryTest {

    @Test
    public void testgetCurrencyString() {
        CurrencyUnit cur = Monetary.getCurrency("test1");
        assertNotNull(cur);
        assertEquals(cur.getCurrencyCode(), "test1");
        assertEquals(cur.getNumericCode(), 1);
        assertEquals(cur.getDefaultFractionDigits(), 2);
    }

    @Test
    public void testIsAvailableString() {
        assertTrue(Monetary.isCurrencyAvailable("test1"));
        assertFalse(Monetary.isCurrencyAvailable("akjshakjshajsgdgsdgsdg"));
    }

    @Test
    public void testIsAvailableLocale() {
        assertFalse(Monetary.isCurrencyAvailable(Locale.CHINA));
        assertTrue(Monetary.isCurrencyAvailable(new Locale("", "TEST1L")));
    }

    @Test(expectedExceptions = UnknownCurrencyException.class)
    public void testgetCurrencyString_NA() {
        Monetary.getCurrency("testGetInstanceCurrency_NA");
    }

    @Test
    public void testgetCurrencyLocale() {
        Collection<CurrencyUnit> curs = Monetary.getCurrencies(new Locale("", "TEST1L"));
        assertNotNull(curs);
        assertEquals(curs.size(), 1);
        CurrencyUnit cur = curs.iterator().next();
        assertEquals(cur.getCurrencyCode(), "TEST1L");
        assertEquals(cur.getNumericCode(), 1);
        assertEquals(cur.getDefaultFractionDigits(), 2);
    }

    @Test
    public void testGetCurrencies_Providers() {
        Collection<CurrencyUnit> curs = Monetary.getCurrencies("test");
        assertNotNull(curs);
    }

    @Test
    public void testGetCurrency_CurrencyQuery() {
        CurrencyUnit cur = Monetary.getCurrency(
                CurrencyQueryBuilder.of().build());
        assertNull(cur);
        cur = Monetary.getCurrency(
                CurrencyQueryBuilder.of().setCurrencyCodes("test1").build());
        assertNotNull(cur);
    }


    @Test
    public void testGetCurrencies_CurrencyQuery() {
        Collection<CurrencyUnit> currencies = Monetary.getCurrencies(
                CurrencyQueryBuilder.of().build()
        );
        assertNotNull(currencies);
    }

    @Test
    public void testGetProviderNames() {
        Set<String> chain = Monetary.getCurrencyProviderNames();
        assertNotNull(chain);
    }

    @Test
    public void testGetDefaultProviderChain() {
        List<String> chain = Monetary.getDefaultCurrencyProviderChain();
        assertNotNull(chain);
    }

    @Test
    public void testgetCurrencyLocale_Empty() {
        Collection<CurrencyUnit> curs = Monetary.getCurrencies(Locale.CHINA);
        assertNotNull(curs);
        assertTrue(curs.isEmpty());
        curs = Monetary.getCurrencies(new Locale("", "sdsdsd"));
        assertNotNull(curs);
        assertTrue(curs.isEmpty());
    }

    @Test(expectedExceptions = UnknownCurrencyException.class)
    public void testgetCurrencyString_Error() {
        CurrencyUnit cur = Monetary.getCurrency("error");
        assertNull(cur);
        cur = Monetary.getCurrency("invalid");
        assertNull(cur);
    }


}
