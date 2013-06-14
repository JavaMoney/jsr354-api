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
import javax.money.Money;
import javax.money.MoneyCurrency;

import net.java.javamoney.ri.IntegralMoney;

import org.junit.Test;

public class MoneyTest {

	private static final BigDecimal TEN = new BigDecimal(10.0d);
	protected static final CurrencyUnit EURO = MoneyCurrency.of("EUR");
	protected static final CurrencyUnit DOLLAR = MoneyCurrency
			.of("USD");

	@Test
	public void testGetInstanceCurrencyBigDecimal() {
		Money m = Money.of(MoneyCurrency.of("EUR"), TEN);
		assertEquals(TEN, m.asType(BigDecimal.class));
	}

	@Test
	public void testGetInstanceCurrencyDouble() {
		Money m = Money.of(MoneyCurrency.of("EUR"), 10.0d);
		assertTrue(TEN.doubleValue() == m.doubleValue());
	}

	@Test
	public void testGetCurrency() {
		MonetaryAmount money = Money.of(EURO, BigDecimal.TEN);
		assertNotNull(money.getCurrency());
		assertEquals("EUR", money.getCurrency().getCurrencyCode());
	}

	@Test
	public void testAddNumber() {
		MonetaryAmount money1 = Money.of(EURO, BigDecimal.TEN);
		MonetaryAmount money2 = Money.of(EURO, BigDecimal.ONE);
		MonetaryAmount moneyResult = money1.add(money2);
		assertNotNull(moneyResult);
		assertEquals(11d, moneyResult.doubleValue(), 0d);
	}

	@Test
	public void testSubtractMonetaryAmount() {
		MonetaryAmount money1 = Money.of(EURO, BigDecimal.TEN);
		MonetaryAmount money2 = Money.of(EURO, BigDecimal.ONE);
		MonetaryAmount moneyResult = money1.subtract(money2);
		assertNotNull(moneyResult);
		assertEquals(9d, moneyResult.doubleValue(), 0d);
	}

	@Test
	public void testDivideAndRemainder_BigDecimal() {
		MonetaryAmount money1 = Money.of(EURO, BigDecimal.ONE);
		MonetaryAmount money2 = Money.of(EURO, new BigDecimal(
				"0.50000000000000000001"));
		MonetaryAmount[] divideAndRemainder = money1.divideAndRemainder(money2);
		assertThat(divideAndRemainder[0].asType(BigDecimal.class),
				equalTo(BigDecimal.ONE));
		assertThat(divideAndRemainder[1].asType(BigDecimal.class),
				equalTo(new BigDecimal("0.49999999999999999999")));
	}

	@Test
	public void testDivideToIntegralValue_BigDecimal() {
		MonetaryAmount money1 = Money.of(EURO, BigDecimal.ONE);
		MonetaryAmount money2 = Money.of(EURO, new BigDecimal(
				"0.50000000000000000001"));
		MonetaryAmount result = money1.divideToIntegralValue(money2);
		assertThat(result.asType(BigDecimal.class), equalTo(BigDecimal.ONE));
	}
	
	@Test
	public void comparePerformance(){
		MonetaryAmount money1 = Money.of(EURO, BigDecimal.ONE);
		long start = System.currentTimeMillis();
		for(int i=0; i<100000;i++){
			money1 = money1.add(1234567);
			money1 = money1.subtract(232323);
			money1 = money1.multiply(3);
			money1 = money1.divide(3);
		}
		long end = System.currentTimeMillis();
		long duration = end - start;
		System.out.println("Duration for 100000 multiplications (Money/BD): " + duration + " ms ("+(duration/100) + " ns per loop) -> " + money1);
		
		MonetaryAmount money2 = IntegralMoney.of(EURO, BigDecimal.ONE);
		start = System.currentTimeMillis();
		for(int i=0; i<100000;i++){
			money2 = money2.add(1234567);
			money2 = money2.subtract(232323);
			money2 = money2.multiply(3);
			money2 = money2.divide(3);
		}
		end = System.currentTimeMillis();
		duration = end - start;
		System.out.println("Duration for 100000 multiplications (Money/BD): " + duration + " ms ("+(duration/100) + " ns per loop) -> " + money2);
	}
}
