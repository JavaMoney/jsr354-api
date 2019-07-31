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
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class MonetaryRoundingsTest {

    @Test
    public void testMonetaryRoundingsGetRoundingCurrencyUnit() {
        MonetaryOperator op = Monetary.getRounding(Monetary.getCurrency("test1"));
        assertNotNull(op);
    }

    @Test
    public void testMonetaryRoundingsGetRoundingQueryWithLongTS() {
        MonetaryOperator op = Monetary.getRounding(
                RoundingQueryBuilder.of().setCurrency(Monetary.getCurrency("test1")).set("timestamp", 200L)
                        .build());
        assertNotNull(op);
    }

    @Test
    public void testMonetaryRoundingsGetRoundingQueryAny() {
        Collection<MonetaryRounding> roundings = Monetary.getRoundings(RoundingQueryBuilder.of().build());
        assertNotNull(roundings);
        assertFalse(roundings.isEmpty());
    }

    @Test
    public void testMonetaryRoundingsGetDefaultRounding() {
        MonetaryOperator op = Monetary.getDefaultRounding();
        assertNotNull(op);
    }

    @Test
    public void testMonetaryRoundingsGetRoundingWithId() {
        MonetaryOperator op = Monetary.getRounding("custom1");
        assertNotNull(op);
    }

    @Test
    public void testIsRoundingsAvailable() {
        assertTrue(Monetary.isRoundingAvailable("custom1"));
        assertFalse(Monetary.isRoundingAvailable("foo"));
    }

    @Test
    public void testIsRoundingsAvailable_CurrencyUnit() {
        assertFalse(Monetary.isRoundingAvailable(TestCurrency.of("CHF"), "foo"));
        assertTrue(Monetary.isRoundingAvailable(TestCurrency.of("CHF")));
    }

    @Test
    public void testIsRoundingsAvailable_Query() {
        assertTrue(Monetary.isRoundingAvailable(
                RoundingQueryBuilder.of().setCurrency(TestCurrency.of("CHF")).set("timestamp", 200L)
                        .build()));
        assertFalse(Monetary.isRoundingAvailable(
                RoundingQueryBuilder.of().setCurrency(TestCurrency.of("CHF")).setProviderName("foo")
                        .build()));
    }

    @Test
    public void testGetDefaultProviderChain() {
        List<String> chain = Monetary.getDefaultRoundingProviderChain();
        assertNotNull(chain);
        assertFalse(chain.isEmpty());
    }

    @Test
    public void testMonetaryRoundingsGetCustomRoundingIds() {
        Set<String> ids = Monetary.getRoundingNames();
        assertNotNull(ids);
        assertTrue(ids.size() == 2);
    }

    @Test
    public void testMonetaryRoundingsGetProviderNames() {
        Set<String> names = Monetary.getRoundingProviderNames();
        assertNotNull(names);
        assertTrue(names.size() == 1);
    }
}
