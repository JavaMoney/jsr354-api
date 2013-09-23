/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.money.MonetaryAmount;
import javax.money.Money;

import org.javamoney.function.Minimum;
import org.javamoney.function.MonetaryFunctions;
import org.junit.Test;

public class MinimumTest {

	@Test
	public void testNew() {
		Minimum max = new Minimum();
	}

	@Test
	public void testFromIterableOfMonetaryAmount() {
		Money m = Money.of("CHF", 1);
		List<Money> set = new ArrayList<Money>();
		set.add(m);
		assertEquals(Money.of("CHF", 1), MonetaryFunctions.minimum().calculate(set));
		set.add(m);
		m = Money.of("CHF", 3);
		set.add(m);
		assertEquals(Money.of("CHF", 1), MonetaryFunctions.minimum().calculate(set));
		m = Money.of("CHF", 0);
		set.add(m);
		assertEquals(Money.of("CHF", 0), MonetaryFunctions.minimum().calculate(set));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromIterableOfMonetaryAmount_BadCase1() {
		MonetaryFunctions.minimum().calculate((Iterable) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromIterableOfMonetaryAmount_BadCase2() {
		MonetaryFunctions.minimum().calculate(new HashSet());
	}

	@Test
	public void testApply() {
		Money m = Money.of("CHF", 1);
		List<Money> set = new ArrayList<Money>();
		set.add(m);
		assertEquals(Money.of("CHF", 1), MonetaryFunctions.minimum().calculate(set));
		set.add(m);
		m = Money.of("CHF", 3);
		set.add(m);
		assertEquals(Money.of("CHF", 1), MonetaryFunctions.minimum().calculate(set));
		m = Money.of("CHF", 0);
		set.add(m);
		assertEquals(Money.of("CHF", 0), MonetaryFunctions.minimum().calculate(set));
	}

	@Test
	public void testToString() {
		assertEquals("Minimum [Iterable<MonetaryAmount> -> MonetaryAmount]",
				new Minimum().toString());
	}
}
