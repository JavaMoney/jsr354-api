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

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.money.convert.ExchangeRate;

import org.junit.Test;

public class ExchangeRateImplTest extends ConvertTestBase {

	@Test
	public void testGetSourceCurrency() {
		ExchangeRate rate = new ExchangeRateImpl(EURO, DOLLAR, BigDecimal.valueOf(1.30693d), 
				System.currentTimeMillis(),	TEN_MINUTES_IN_MILLIS);
		assertNotNull(rate.getSourceCurrency());
		assertEquals("EUR", rate.getSourceCurrency().getCurrencyCode());
	}

	@Test
	public void testGetTargetCurrency() {
		ExchangeRate rate = new ExchangeRateImpl(EURO, DOLLAR, BigDecimal.valueOf(1.30693d), 
				System.currentTimeMillis(),	TEN_MINUTES_IN_MILLIS);
		assertNotNull(rate.getTargetCurrency());
		assertEquals("USD", rate.getTargetCurrency().getCurrencyCode());
	}

	@Test
	public void testGetTimestamp() {
		long timestamp = System.currentTimeMillis();
		ExchangeRate rate = new ExchangeRateImpl(EURO, DOLLAR, BigDecimal.valueOf(1.30693d), timestamp, 
				TEN_MINUTES_IN_MILLIS);
		assertEquals(timestamp, rate.getTimestamp());
	}

}
