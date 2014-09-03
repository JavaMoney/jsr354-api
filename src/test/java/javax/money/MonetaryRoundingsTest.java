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
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class MonetaryRoundingsTest{

    @Test
    public void testMonetaryRoundingsGetRoundingCurrencyUnit(){
        MonetaryOperator op = MonetaryRoundings.getRounding(MonetaryCurrencies.getCurrency("test1"));
        assertNotNull(op);
    }

    @Test
    public void testMonetaryRoundingsGetRoundingQueryWithLongTS(){
        MonetaryOperator op = MonetaryRoundings.getRounding(
                RoundingQueryBuilder.create().setCurrency(MonetaryCurrencies.getCurrency("test1"))
                        .set("timestamp", 200L).build());
        assertNotNull(op);
    }

    @Test
    public void testMonetaryRoundingsGetRoundingQueryAny(){
        Collection<MonetaryRounding> roundings = MonetaryRoundings.getRoundings(RoundingQueryBuilder.create().build());
        assertNotNull(roundings);
        assertFalse(roundings.isEmpty());
    }

    @Test
    public void testMonetaryRoundingsGetDefaultRounding(){
        MonetaryOperator op = MonetaryRoundings.getDefaultRounding();
        assertNotNull(op);
    }

    @Test
    public void testMonetaryRoundingsGetRoundingWithId(){
        MonetaryOperator op = MonetaryRoundings.getRounding("custom1");
        assertNotNull(op);
    }

    @Test
    public void testMonetaryRoundingsGetCustomRoundingIds(){
        Set<String> ids = MonetaryRoundings.getRoundingNames();
        assertNotNull(ids);
        assertTrue(ids.size() == 2);
    }

    @Test
    public void testMonetaryRoundingsGetProviderNames(){
        Set<String> names = MonetaryRoundings.getProviderNames();
        assertNotNull(names);
        assertTrue(names.size() == 1);
    }
}
