/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
