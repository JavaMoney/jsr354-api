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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.Set;

import javax.money.*;

import org.junit.Test;

public class MonetaryRoundingsTest {

	@Test
	public void testMonetaryRoundingsGetCashRoundingCurrencyUnit() {
		MonetaryOperator op = MonetaryRoundings
				.getCashRounding(MonetaryCurrencies.getCurrency(new Locale("", "TEST1L")));
		assertNotNull(op);
	}

	@Test
	public void testMonetaryRoundingsGetCashRoundingCurrencyUnitLong() {
		MonetaryOperator op = MonetaryRoundings.getCashRounding(
				MonetaryCurrencies.getCurrency(new Locale("", "TEST1L")), 200L);
		assertNotNull(op);
	}

    @Test(expected=MonetaryException.class)
    public void testMonetaryRoundingsGetCashRoundingCurrencyUnit_Error() {
        MonetaryOperator op = MonetaryRoundings
                .getCashRounding(MonetaryCurrencies.getCurrency(new Locale("", "TEST_ERROR")));
        assertNotNull(op);
    }

	@Test
	public void testMonetaryRoundingsGetRoundingCurrencyUnit() {
		MonetaryOperator op = MonetaryRoundings.getRounding(MonetaryCurrencies
				.getCurrency("test1"));
		assertNotNull(op);
	}

	@Test
	public void testMonetaryRoundingsGetRoundingCurrencyUnitLong() {
		MonetaryOperator op = MonetaryRoundings.getRounding(
				MonetaryCurrencies.getCurrency("test1"), 200L);
		assertNotNull(op);
	}

    @Test(expected=MonetaryException.class)
    public void testMonetaryRoundingsGetRoundingCurrencyUnit_Error() {
        MonetaryOperator op = MonetaryRoundings
                .getRounding(MonetaryCurrencies.getCurrency(new Locale("", "TEST_ERROR")));
        assertNotNull(op);
    }

	@Test
	public void testMonetaryRoundingsGetRounding() {
		MonetaryOperator op = MonetaryRoundings.getRounding();
		assertNotNull(op);
	}

	@Test
	public void testMonetaryRoundingsGetRoundingString() {
		MonetaryOperator op = MonetaryRoundings.getRounding("custom1");
		assertNotNull(op);
	}

	@Test
	public void testMonetaryRoundingsGetRoundingMonetaryContext() {
		MonetaryOperator op = MonetaryRoundings
				.getRounding(new MonetaryContext.Builder().create());
		assertNotNull(op);
	}

    @Test
    public void testMonetaryRoundingsGetCustomRoundingIds() {
        Set<String> ids = MonetaryRoundings
                .getCustomRoundingIds();
        assertNotNull(ids);
        assertTrue(ids.size() == 2);
    }
}
