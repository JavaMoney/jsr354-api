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