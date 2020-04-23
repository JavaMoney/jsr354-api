/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2020 Werner Keil, Otavio Santana, Trivadis AG
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

import javax.money.CurrencyQuery;
import javax.money.CurrencyQueryBuilder;
import javax.money.CurrencyUnit;
import javax.money.TestCurrency;
import java.util.*;

import static org.testng.AssertJUnit.*;

/**
 * Tests the default methods on MonetaryAmountsSingletonSpi.
 */
public class MonetarySingletonSpiTest {

    private final MonetaryCurrenciesSingletonSpi testSpi = new MonetaryCurrenciesSingletonSpi() {

        @Override
        public List<String> getDefaultProviderChain() {
            return Collections.singletonList("a");
        }

        @Override
        public Set<String> getProviderNames() {
            return new HashSet<>(Arrays.asList("a", "b"));
        }

        @Override
        public Set<CurrencyUnit> getCurrencies(CurrencyQuery query) {
            Collection<String> codes = query.getCurrencyCodes();
            if (codes.size() == 1) {
                return new HashSet<>(Arrays.asList(TestCurrency.of("USD")));
            }
            Collection<String> providers = query.getProviderNames();
            if (providers.size() == 1) {
                return new HashSet<>(Arrays.asList(TestCurrency.of("CHF")));
            }
            Collection<Locale> countries = query.getCountries();
            if (!countries.isEmpty()) {
                return new HashSet<>(Arrays.asList(TestCurrency.of("EUR")));
            }
            return Collections.emptySet();
        }
    };

    @Test
    public void testGetCurrency_String() {
        CurrencyUnit cu = testSpi.getCurrency("a");
        assertNotNull(cu);
        assertEquals(cu.getCurrencyCode(), "USD");
        cu = testSpi.getCurrency("scbjshbd");
        assertNotNull(cu);
        assertEquals(cu.getCurrencyCode(), "USD");
    }

    @Test
    public void testGetCurrency_Locale() {
        // CurrencyUnit getCurrency(Locale country, String... providers)
        CurrencyUnit cu = testSpi.getCurrency(Locale.GERMANY, "a");
        assertNotNull(cu);
    }

    @Test
    public void testGetCurrencies_Locale() {
        Set<CurrencyUnit> curs = testSpi.getCurrencies(Locale.FRANCE, "a");
        assertNotNull(curs);
    }

    @Test
    public void testIsCurrencyAvailable_String() {
        assertTrue(testSpi.isCurrencyAvailable("CHF", "a"));
        assertTrue(testSpi.isCurrencyAvailable("CHF"));
        assertTrue(testSpi.isCurrencyAvailable("foo2", "a"));
        assertTrue(testSpi.isCurrencyAvailable("foo2"));
    }

    @Test
    public void testIsCurrencyAvailable_Locale() {
        assertTrue(testSpi.isCurrencyAvailable(Locale.GERMANY, "a"));
        assertTrue(testSpi.isCurrencyAvailable(Locale.GERMANY));
        assertTrue(testSpi.isCurrencyAvailable(Locale.ROOT, "a"));
        assertTrue(testSpi.isCurrencyAvailable(Locale.ROOT));
    }

    @Test
    public void testGetCurrencies_String() {
        Set<CurrencyUnit> curs = testSpi.getCurrencies("a");
        assertNotNull(curs);
        assertFalse(curs.isEmpty());
        curs = testSpi.getCurrencies();
        assertNotNull(curs);
        assertTrue(curs.isEmpty());
        curs = testSpi.getCurrencies("fooBar");
        assertNotNull(curs);
        assertFalse(curs.isEmpty());
    }

    @Test
    public void testGetCurrency_Query() {
        CurrencyUnit cu = testSpi.getCurrency(CurrencyQueryBuilder.of().setCurrencyCodes("CHF").build());
        assertNotNull(cu);
        cu = testSpi.getCurrency(CurrencyQueryBuilder.of().setCurrencyCodes("fooBar").build());
        assertNotNull(cu);
        assertEquals(cu.getCurrencyCode(), "USD");
    }

}
