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
 *
 * Contributors:
 *    Anatole Tresch - initial implementation
 *    Wernner Keil - extensions and adaptions.
 */
package net.java.javamoney.ri.core;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MoneyCurrency;

import net.java.javamoney.ri.IntegralMoney;

import org.junit.Test;

public class IntegralMoneyTest {

	private static final BigDecimal TEN = new BigDecimal(10.0d);
	protected static final CurrencyUnit EURO = MoneyCurrency.of("EUR");
	protected static final CurrencyUnit DOLLAR = MoneyCurrency
			.of("USD");

	@Test
	public void testGetInstanceCurrencyBigDecimal() {
		IntegralMoney m = IntegralMoney.of(MoneyCurrency.of("EUR"), TEN);
		assertEquals(TEN, m.asType(BigDecimal.class));
		assertEquals(Long.valueOf(10L), m.asType(Long.class));
	}

	@Test
	public void testGetInstanceCurrencyDouble() {
		IntegralMoney m = IntegralMoney.of(MoneyCurrency.of("EUR"), 10.0d);
		assertTrue(TEN.doubleValue() == m.doubleValue());
	}

	@Test
	public void testGetCurrency() {
		MonetaryAmount money = IntegralMoney.of(EURO, BigDecimal.TEN);
		assertNotNull(money.getCurrency());
		assertEquals("EUR", money.getCurrency().getCurrencyCode());
	}

	@Test
	public void testAddNumber() {
		MonetaryAmount money1 = IntegralMoney.of(EURO, BigDecimal.TEN);
		MonetaryAmount money2 = IntegralMoney.of(EURO, BigDecimal.ONE);
		MonetaryAmount moneyResult = money1.add(money2);
		assertNotNull(moneyResult);
		assertEquals(11d, moneyResult.doubleValue(), 0d);
	}

	@Test
	public void testSubtractMonetaryAmount() {
		MonetaryAmount money1 = IntegralMoney.of(EURO, BigDecimal.TEN);
		MonetaryAmount money2 = IntegralMoney.of(EURO, BigDecimal.ONE);
		MonetaryAmount moneyResult = money1.subtract(money2);
		assertNotNull(moneyResult);
		assertEquals(9d, moneyResult.doubleValue(), 0d);
	}

	@Test
	public void testDivideAndRemainder() {
		MonetaryAmount money1 = IntegralMoney.of(EURO, 1000);
		MonetaryAmount money2 = IntegralMoney.of(EURO, 11);
		MonetaryAmount[] divideAndRemainder = money1.divideAndRemainder(money2);
		assertEquals(90L, divideAndRemainder[0].longValue());
		assertEquals(10L, divideAndRemainder[1].longValue());
	}

	@Test
	public void testDivideToIntegralValue() {
		MonetaryAmount money1 = IntegralMoney.of(EURO, 1000);
		MonetaryAmount money2 = IntegralMoney.of(EURO, 5);
		MonetaryAmount result = money1.divideToIntegralValue(money2);
		assertEquals(200L, result.longValue());
	}
}
