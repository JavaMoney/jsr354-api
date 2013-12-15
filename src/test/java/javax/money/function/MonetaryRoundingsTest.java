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
package javax.money.function;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Locale;

import javax.money.MonetaryContext;
import javax.money.MonetaryCurrencies;
import javax.money.MonetaryOperator;

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
				.getRounding(new MonetaryContext.Builder().build());
		assertNotNull(op);
	}
}
