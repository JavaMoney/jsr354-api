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

import org.junit.Test;

import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MonetaryRoundingsTest{

    @Test
    public void testMonetaryRoundingsGetRoundingCurrencyUnit(){
        MonetaryOperator op = MonetaryRoundings.getRounding(MonetaryCurrencies.getCurrency("test1"));
        assertNotNull(op);
    }

    @Test
    public void testMonetaryRoundingsGetRoundingCurrencyUnitLong(){
        MonetaryOperator op = MonetaryRoundings.getRounding(
                new RoundingContext.Builder().setCurrencyUnit(MonetaryCurrencies.getCurrency("test1"))
                        .setAttribute("timestamp", 200L).build()
        );
        assertNotNull(op);
    }

    @Test(expected = MonetaryException.class)
    public void testMonetaryRoundingsGetRoundingCurrencyUnit_Error(){
        MonetaryOperator op =
                MonetaryRoundings.getRounding(MonetaryCurrencies.getCurrency(new Locale("", "TEST_ERROR")));
        assertNotNull(op);
    }

    @Test
    public void testMonetaryRoundingsGetRounding(){
        MonetaryOperator op = MonetaryRoundings.getRounding();
        assertNotNull(op);
    }

    @Test
    public void testMonetaryRoundingsGetRoundingString(){
        MonetaryOperator op = MonetaryRoundings.getRounding("custom1");
        assertNotNull(op);
    }

    @Test
    public void testMonetaryRoundingsGetCustomRoundingIds(){
        Set<String> ids = MonetaryRoundings.getRoundingIds();
        assertNotNull(ids);
        assertTrue(ids.size() == 2);
    }
}
