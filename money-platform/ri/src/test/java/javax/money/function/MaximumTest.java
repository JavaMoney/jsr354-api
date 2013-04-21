/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.money.MonetaryAmount;
import javax.money.Money;

import org.junit.Test;

public class MaximumTest {

	@Test
	public void testOf() {
		Maximum max = Maximum.of();
		assertNotNull(max);
	}

	@Test
	public void testFromIterableOfMonetaryAmount() {
		Money m = Money.of("CHF", 1);
		List<Money> set = new ArrayList<Money>();
		set.add(m);
		assertEquals(Money.of("CHF", 1), Maximum.from(set));
		set.add(m);
		 m = Money.of("CHF", -1);
		set.add(m);
		assertEquals(Money.of("CHF", 1), Maximum.from(set));
		m = Money.of("CHF", 3);
		set.add(m);
		assertEquals(Money.of("CHF", 3), Maximum.from(set));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromIterableOfMonetaryAmount_BadCase1() {
		Maximum.from((Iterable) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromIterableOfMonetaryAmount_BadCase2() {
		Maximum.from(new HashSet());
	}

	@Test
	public void testFromMonetaryAmountArray() {
		Money m = Money.of("CHF", 1);
		Money m2 = Money.of("CHF", -1);
		Money m3 = Money.of("CHF", 3);
		assertEquals(Money.of("CHF", 1), Maximum.from(m));
		assertEquals(Money.of("CHF", 1), Maximum.from(m, m2, m));
		assertEquals(Money.of("CHF", 3),
				Maximum.from(m, m2, m3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromMonetaryAmountArray_BadCase1() {
		Maximum.from();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromMonetaryAmountArray_BadCase2() {
		Maximum.from((MonetaryAmount[]) null);
	}

	@Test
	public void testApply() {
		Money m = Money.of("CHF", 1);
		List<Money> set = new ArrayList<Money>();
		set.add(m);
		assertEquals(Money.of("CHF", 1), Maximum.of().apply(set));
		set.add(m);
		 m = Money.of("CHF", -1);
		set.add(m);
		assertEquals(Money.of("CHF", 1), Maximum.of().apply(set));
		m = Money.of("CHF", 3);
		set.add(m);
		assertEquals(Money.of("CHF", 3), Maximum.of().apply(set));
	}

	@Test
	public void testToString() {
		assertEquals("Maximum [Iterable<MonetaryAmount> -> MonetaryAmount]",
				Maximum.of().toString());
	}
}
