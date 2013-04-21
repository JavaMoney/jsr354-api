/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money;

import static org.junit.Assert.assertNotNull;

import java.math.RoundingMode;

import org.junit.Test;

/**
 * @author Anatole
 * 
 */
public class MoneyRoundingTest {

	/**
	 * Test method for
	 * {@link javax.money.MoneyRounding#of(javax.money.CurrencyUnit, java.math.RoundingMode)}
	 * .
	 */
	@Test
	public void testGetRoundingCurrencyUnitRoundingMode() {
		MoneyRounding rounding = MoneyRounding.of(MoneyCurrency.of("CHF"),
				RoundingMode.FLOOR);
		assertNotNull(rounding);
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyRounding#of(javax.money.CurrencyUnit)}.
	 */
	@Test
	public void testGetRoundingCurrencyUnit() {
		MoneyRounding rounding = MoneyRounding.of(MoneyCurrency.of("CHF"));
		assertNotNull(rounding);
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyRounding#of(int, java.math.RoundingMode)}
	 * .
	 */
	@Test
	public void testGetRoundingIntRoundingMode() {
		MoneyRounding rounding = MoneyRounding.of(3, RoundingMode.FLOOR);
		assertNotNull(rounding);
	}

}
