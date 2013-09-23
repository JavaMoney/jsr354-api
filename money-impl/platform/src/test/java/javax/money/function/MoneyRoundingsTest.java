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

import static org.junit.Assert.assertNotNull;

import java.math.RoundingMode;

import javax.money.MonetaryOperator;
import javax.money.MoneyCurrency;

import org.junit.Test;

/**
 * @author Anatole
 * 
 */
public class MoneyRoundingsTest {

	/**
	 * Test method for
	 * {@link javax.money.function.MonetaryRoundings#of(javax.money.CurrencyUnit, java.math.RoundingMode)}
	 * .
	 */
	@Test
	public void testGetRoundingCurrencyUnitRoundingMode() {
		MonetaryOperator rounding = MonetaryRoundings.getRounding(MoneyCurrency
				.of("CHF").getDefaultFractionDigits(),
				RoundingMode.FLOOR);
		assertNotNull(rounding);
	}

	/**
	 * Test method for
	 * {@link javax.money.function.MonetaryRoundings#of(javax.money.CurrencyUnit)}.
	 */
	@Test
	public void testGetRoundingCurrencyUnit() {
		MonetaryOperator rounding = MonetaryRoundings.getRounding(MoneyCurrency.of("CHF"));
		assertNotNull(rounding);
	}

	/**
	 * Test method for
	 * {@link javax.money.function.MonetaryRoundings#of(int, java.math.RoundingMode)}
	 * .
	 */
	@Test
	public void testGetRoundingIntRoundingMode() {
		MonetaryOperator rounding = MonetaryRoundings.getRounding(3, RoundingMode.FLOOR);
		assertNotNull(rounding);
	}

}
