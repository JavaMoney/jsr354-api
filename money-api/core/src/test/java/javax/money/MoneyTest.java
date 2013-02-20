/*
 * Copyright (c) 2012-2013, Credit Suisse
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.money;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class MoneyTest {

	private static final BigDecimal TEN = new BigDecimal(10.0d);
	protected static final CurrencyUnit EURO = MoneyCurrency.getInstance("EUR");
	protected static final CurrencyUnit DOLLAR = MoneyCurrency
			.getInstance("USD");

	@Test
	public void testGetInstanceCurrencyBigDecimal() {
		Money m = Money.valueOf(MoneyCurrency.getInstance("EUR"), TEN);
		assertEquals(TEN, m.asType(BigDecimal.class));
	}

	@Test
	public void testGetInstanceCurrencyDouble() {
		Money m = Money.valueOf(MoneyCurrency.getInstance("EUR"), 10.0d);
		assertTrue(TEN.doubleValue() == m.doubleValue());
	}

	@Test
	public void testGetCurrency() {
		MonetaryAmount money = Money.valueOf(EURO, BigDecimal.TEN);
		assertNotNull(money.getCurrency());
		assertEquals("EUR", money.getCurrency().getCurrencyCode());
	}

	@Test
	public void testAddNumber() {
		MonetaryAmount money1 = Money.valueOf(EURO, BigDecimal.TEN);
		MonetaryAmount money2 = Money.valueOf(EURO, BigDecimal.ONE);
		MonetaryAmount moneyResult = money1.add(money2);
		assertNotNull(moneyResult);
		assertEquals(11d, moneyResult.doubleValue(), 0d);
	}

	@Test
	public void testSubtractMonetaryAmount() {
		MonetaryAmount money1 = Money.valueOf(EURO, BigDecimal.TEN);
		MonetaryAmount money2 = Money.valueOf(EURO, BigDecimal.ONE);
		MonetaryAmount moneyResult = money1.subtract(money2);
		assertNotNull(moneyResult);
		assertEquals(9d, moneyResult.doubleValue(), 0d);
	}

	@Test
	public void testDivideAndRemainder_BigDecimal() {
		MonetaryAmount money1 = Money.valueOf(EURO, BigDecimal.ONE);
		MonetaryAmount money2 = Money.valueOf(EURO, new BigDecimal(
				"0.50000000000000000001"));
		MonetaryAmount[] divideAndRemainder = money1.divideAndRemainder(money2);
		assertThat(divideAndRemainder[0].asType(BigDecimal.class),
				equalTo(BigDecimal.ONE));
		assertThat(divideAndRemainder[1].asType(BigDecimal.class),
				equalTo(new BigDecimal("0.49999999999999999999")));
	}

	@Test
	public void testDivideToIntegralValue_BigDecimal() {
		MonetaryAmount money1 = Money.valueOf(EURO, BigDecimal.ONE);
		MonetaryAmount money2 = Money.valueOf(EURO, new BigDecimal(
				"0.50000000000000000001"));
		MonetaryAmount result = money1.divideToIntegralValue(money2);
		assertThat(result.asType(BigDecimal.class), equalTo(BigDecimal.ONE));
	}
}
