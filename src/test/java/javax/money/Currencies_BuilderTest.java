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
 * 
 * Contributors: Anatole Tresch - initial implementation Wernner Keil -
 * extensions and adaptions.
 */
package javax.money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.money.Currencies;

import org.junit.Test;

/**
 * Tests for {@link CurrencyUnitImpl.Builder}
 * 
 * @author Anatole Tresch
 */
public class Currencies_BuilderTest {

	/**
	 * Test method for {@link javax.money.format.CurrencyUnitImpl.Builder#Builder()}.
	 */
	@Test
	public void testBuilder() {
		new Currencies.Builder();
	}


	/**
	 * Test method for
	 * {@link javax.money.format.CurrencyUnitImpl.Builder#withCurrencyCode(java.lang.String)}
	 * and {@link javax.money.format.CurrencyUnitImpl.Builder#getCurrencyCode()} .
	 */
	@Test
	public void testGetSetCurrencyCode() {
		Currencies.Builder builder = new Currencies.Builder()
				.setCurrencyCode("code1");
	}

	/**
	 * Test method for
	 * {@link javax.money.format.CurrencyUnitImpl.Builder#withDefaultFractionDigits(int)}
	 * and
	 * {@link javax.money.format.CurrencyUnitImpl.Builder#getDefaultFractionDigits()}.
	 */
	@Test
	public void testGetSetDefaultFractionDigits() {
		Currencies.Builder builder = new Currencies.Builder()
				.setDefaultFractionDigits(10);
		builder.setDefaultFractionDigits(-1);
	}

	/**
	 * Test method for
	 * {@link javax.money.format.CurrencyUnitImpl.Builder#withDefaultFractionDigits(int)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetSetDefaultFractionDigits_InvalidInput() {
		new Currencies.Builder().setDefaultFractionDigits(-10);
	}

	/**
	 * Test method for
	 * {@link javax.money.format.CurrencyUnitImpl.Builder#withNumericCode(int)} and
	 * {@link javax.money.format.CurrencyUnitImpl.Builder#getNumericCode()}.
	 */
	@Test
	public void testGetSetNumericCode() {
		Currencies.Builder builder = new Currencies.Builder()
				.setNumericCode(10);
		builder.setNumericCode(-1);
	}

	/**
	 * Test method for
	 * {@link javax.money.format.CurrencyUnitImpl.Builder#withNumericCode(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetSetNumericCode_InvalidInput() {
		new Currencies.Builder().setNumericCode(-10);
	}

	/**
	 * Test method for {@link javax.money.format.CurrencyUnitImpl.Builder#build()}.
	 */
	@Test
	public void testBuild() {
		Currencies.Builder builder = new Currencies.Builder()
				.setCurrencyCode("cd")
				.setDefaultFractionDigits(101)
				.setNumericCode(7);
		CurrencyUnit cu = builder.build();
		assertEquals("cd", cu.getCurrencyCode());
		assertEquals(101, cu.getDefaultFractionDigits());
		assertEquals(7, cu.getNumericCode());
	}

	/**
	 * Test method for
	 * {@link javax.money.format.CurrencyUnitImpl.Builder#build(boolean)}.
	 */
	@Test
	public void testBuildBoolean() {
		Currencies.Builder builder = new Currencies.Builder()
				.setCurrencyCode("testBuildBoolean")
				.setDefaultFractionDigits(101).setNumericCode(7);
		CurrencyUnit cu = builder.build(false);
		assertEquals("testBuildBoolean", cu.getCurrencyCode());
		assertEquals(101, cu.getDefaultFractionDigits());
		assertEquals(7, cu.getNumericCode());
		assertFalse(Currencies.isAvailable("testBuildBoolean"));
		cu = builder.build(true);
		CurrencyUnit cu2 = Currencies.of("testBuildBoolean");
		assertTrue(cu2 == cu);
	}

}