/*
 *  Copyright (c) 2013, Werner Keil.
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
 *    Werner Keil - initial implementation.
 */
package net.java.javamoney.ri.core;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

import net.java.javamoney.ri.RITestBase;
import net.java.javamoney.ri.core.BigDecimalAmount;

import org.junit.Test;

public class BigDecimalAmountTest extends RITestBase {

	@Test
	public void testGetCurrency() {
		MonetaryAmount money = BigDecimalAmount.valueOf(BigDecimal.TEN, EURO);
		assertNotNull(money.getCurrency());
		assertEquals("EUR", money.getCurrency().getCurrencyCode());
	}

	@Test
	public void testAddNumber() {
		MonetaryAmount money1 = BigDecimalAmount.valueOf(BigDecimal.TEN, EURO);
		MonetaryAmount money2 = BigDecimalAmount.valueOf(BigDecimal.ONE, EURO);
		MonetaryAmount moneyResult = money1.add(money2);
		assertNotNull(moneyResult);
		assertEquals(11d, moneyResult.doubleValue(), 0d);
	}

	@Test
	public void testSubtractMonetaryAmount() {
		MonetaryAmount money1 = BigDecimalAmount.valueOf(BigDecimal.TEN, EURO);
		MonetaryAmount money2 = BigDecimalAmount.valueOf(BigDecimal.ONE, EURO);
		MonetaryAmount moneyResult = money1.subtract(money2);
		assertNotNull(moneyResult);
		assertEquals(9d, moneyResult.doubleValue(), 0d);
	}

	@Test
	public void testDivideAndRemainder_BigDecimal() {
		MonetaryAmount money1 = BigDecimalAmount.valueOf(BigDecimal.ONE, EURO);
		MonetaryAmount money2 = BigDecimalAmount.valueOf(new BigDecimal("0.50000000000000000001"), EURO);
		MonetaryAmount[] divideAndRemainder = money1.divideAndRemainder(money2);
		assertThat(divideAndRemainder[0].asType(BigDecimal.class), equalTo(BigDecimal.ONE));
		assertThat(divideAndRemainder[1].asType(BigDecimal.class), equalTo(new BigDecimal("0.49999999999999999999")));
	}

	@Test
	public void testDivideToIntegralValue_BigDecimal() {
		MonetaryAmount money1 = BigDecimalAmount.valueOf(BigDecimal.ONE, EURO);
		MonetaryAmount money2 = BigDecimalAmount.valueOf(new BigDecimal("0.50000000000000000001"), EURO);
		MonetaryAmount result = money1.divideToIntegralValue(money2);
		assertThat(result.asType(BigDecimal.class), equalTo(BigDecimal.ONE));
	}

}
