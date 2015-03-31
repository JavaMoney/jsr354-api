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
