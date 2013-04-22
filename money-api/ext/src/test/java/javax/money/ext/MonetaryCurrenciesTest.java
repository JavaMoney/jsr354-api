/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

import javax.money.MoneyCurrency;
import javax.money.UnknownCurrencyException;

import org.junit.Test;

/**
 * @author Anatole
 * 
 */
public class MonetaryCurrenciesTest {

	@Test(expected = UnknownCurrencyException.class)
	public void testGetString() {
		MonetaryCurrencies.get("CHF");
	}

	@Test(expected = UnknownCurrencyException.class)
	public void testGetStringString() {
		MonetaryCurrencies.get(MoneyCurrency.ISO_NAMESPACE, "CHF");
	}

}
