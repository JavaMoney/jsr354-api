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
package javax.money.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import javax.money.Money;
import javax.money.function.Permil;

import org.junit.Test;

/**
 * @author Werner Keil
 * 
 */
public class PermilTest {

	@Test
	public void testOf() {
		Permil perc = new Permil(BigDecimal.ONE);
	}

	@Test
	public void testApply() {
		Money m = Money.of("CHF", BigDecimal.valueOf(2.35d));
		assertEquals(Money.of("CHF", BigDecimal.valueOf(0.0235d)),
				MoneyFunctions.permil(BigDecimal.TEN).apply(m));
	}

	@Test
	public void testApply10() {
		Money m = Money.of("CHF", 3);
		assertEquals(Money.of("CHF", BigDecimal.valueOf(0.03d)),
				MoneyFunctions.permil(BigDecimal.TEN).apply(m));
	}

	@Test
	public void testApply20() {
		Money m = Money.of("CHF", 12);
		assertEquals(Money.of("CHF", BigDecimal.valueOf(0.24d)),
				MoneyFunctions.permil(BigDecimal.valueOf(20)).apply(m));
	}

	@Test
	public void testApply30() {
		Money m = Money.of("CHF", 12);
		assertEquals(Money.of("CHF", BigDecimal.valueOf(0.36d)),
				MoneyFunctions.permil(BigDecimal.valueOf(30)).apply(m));
	}

	@Test
	public void testToString() {
		assertEquals("15 \u2030", MoneyFunctions.permil(BigDecimal.valueOf(15)).toString());
	}
}
