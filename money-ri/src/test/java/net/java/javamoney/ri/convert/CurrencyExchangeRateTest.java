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
package net.java.javamoney.ri.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import javax.money.convert.ExchangeRateType;

import org.junit.Test;

public class CurrencyExchangeRateTest extends ConvertTestBase {

	private static final ExchangeRateType TYPE = SingletonExchangeRateType
			.of("test");

	@Test
	public void testGetSourceCurrency() {
		CurrencyExchangeRate rate = new CurrencyExchangeRate(TYPE, EURO,
				DOLLAR, 1.30693d, System.currentTimeMillis(),
				TEN_MINUTES_IN_MILLIS);
		assertNotNull(rate.getSource());
		assertEquals("EUR", rate.getSource().getCurrencyCode());
	}

	@Test
	public void testGetTargetCurrency() {
		CurrencyExchangeRate rate = new CurrencyExchangeRate(TYPE, EURO,
				DOLLAR, BigDecimal.valueOf(1.30693d),
				System.currentTimeMillis(), TEN_MINUTES_IN_MILLIS);
		assertNotNull(rate.getTarget());
		assertEquals("USD", rate.getTarget().getCurrencyCode());
	}

	@Test
	public void testGetTimestamp() {
		long timestamp = System.currentTimeMillis();
		CurrencyExchangeRate rate = new CurrencyExchangeRate(TYPE, EURO,
				DOLLAR, BigDecimal.valueOf(1.30693d), timestamp,
				TEN_MINUTES_IN_MILLIS);
		assertEquals(Long.valueOf(timestamp), rate.getTimestamp());
	}

}
