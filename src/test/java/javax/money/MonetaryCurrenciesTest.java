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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import org.junit.Test;

/**
 * Tests for the {@link CurrencyUnitImpl} class.
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
		assertEquals(cur.getDefaultFractionDigits(),
				2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testgetCurrencyString_NA() {
		MonetaryCurrencies.getCurrency("testGetInstanceCurrency_NA");
	}

	@Test
	public void testgetCurrencyLocale() {
		CurrencyUnit cur = MonetaryCurrencies.getCurrency(new Locale("",
				"TEST1L"));
		assertNotNull(cur);
		assertEquals(cur.getCurrencyCode(), "TEST1L");
		assertEquals(cur.getNumericCode(), 1);
		assertEquals(cur.getDefaultFractionDigits(),
				2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testgetCurrencyLocale_NA() {
		MonetaryCurrencies.getCurrency(new Locale("", "sdsdsd"));
	}

}
