/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;

import org.junit.Test;

/**
 * @author Anatole
 * 
 */
public class MonetaryCurrenciesTest {

	@Test
	public void testGetString() {
		CurrencyUnit unit = MonetaryCurrencies.get("CHF");
		assertNotNull(unit);
		assertEquals("CHF", unit.getCurrencyCode());
		assertEquals(MoneyCurrency.ISO_NAMESPACE, unit.getNamespace());
	}

	@Test
	public void testGetStringString() {
		CurrencyUnit unit = MonetaryCurrencies.get(MoneyCurrency.ISO_NAMESPACE,
				"CHF");
		assertNotNull(unit);
		assertEquals("CHF", unit.getCurrencyCode());
		assertEquals(MoneyCurrency.ISO_NAMESPACE, unit.getNamespace());
	}

}
