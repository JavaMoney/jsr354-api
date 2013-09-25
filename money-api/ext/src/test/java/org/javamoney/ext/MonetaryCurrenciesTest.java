/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package org.javamoney.ext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.javamoney.ext.MonetaryCurrencies;
import org.junit.Test;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 */
public class MonetaryCurrenciesTest {

	@Test
	public void testGetString() {
		assertEquals("CHF", MonetaryCurrencies.get("CHF").getCurrencyCode());
	}

	@Test
	public void testGetStringString() {
		MonetaryCurrencies.get("CHF");
	}

	@Test
	public void testGetAllIso() {
		assertNotNull(MonetaryCurrencies.getAll("ISO-4217"));
	}
}
