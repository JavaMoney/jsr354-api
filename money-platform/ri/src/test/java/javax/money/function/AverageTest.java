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

public class AverageTest {

	@Test
	public void testOf() {
		Average avg = Average.of();
		assertNotNull(avg);
	}

	@Test
	public void testFromIterableOfMonetaryAmount() {
		Money m = Money.of("CHF", 1);
		List<Money> set = new ArrayList<Money>();
		set.add(m);
		assertEquals(Money.of("CHF", 1), Average.from(set));
		set.add(m);
		set.add(m);
		assertEquals(Money.of("CHF", 1), Maximum.from(set));
		m = Money.of("CHF", 3);
		set.add(m);
		assertEquals(Money.of("CHF", 1.5), Average.from(set));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromIterableOfMonetaryAmount_BadCase1() {
		Average.from((Iterable) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromIterableOfMonetaryAmount_BadCase2() {
		Average.from(new HashSet());
	}

	@Test
	public void testFromMonetaryAmountArray() {
		Money m = Money.of("CHF", 1);
		assertEquals(Money.of("CHF", 1), Average.from(m));
		assertEquals(Money.of("CHF", 1), Average.from(m, m, m));
		assertEquals(Money.of("CHF", 1.5),
				Average.from(m, m, m, Money.of("CHF", 3)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromMonetaryAmountArray_BadCase1() {
		Average.from();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromMonetaryAmountArray_BadCase2() {
		Average.from((MonetaryAmount[]) null);
	}

	@Test
	public void testApply() {
		Money m = Money.of("CHF", 1);
		List<Money> set = new ArrayList<Money>();
		set.add(m);
		assertEquals(Money.of("CHF", 1), Average.of().apply(set));
		set.add(m);
		set.add(m);
		m = Money.of("CHF", 3);
		set.add(m);
		assertEquals(Money.of("CHF", 1.5), Average.of().apply(set));
	}

	@Test
	public void testToString() {
		assertEquals("Average [Iterable<MonetaryAmount> -> MonetaryAmount]",
				Average.of().toString());
	}
}
