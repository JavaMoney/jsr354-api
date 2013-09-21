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
package net.java.javamoney.ri.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;

import org.junit.Test;

/**
 * Tests for {@link CurrencyUnitImpl.Builder}
 * 
 * @author Anatole Tresch
 */
public class MoneyCurrency_BuilderTest {

	/**
	 * Test method for {@link javax.money.CurrencyUnitImpl.Builder#Builder()}.
	 */
	@Test
	public void testBuilder() {
		new MoneyCurrency.Builder();
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#withNamespace(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetSetNamespace() {
		MoneyCurrency.Builder builder1 = new MoneyCurrency.Builder()
				.withNamespace("ns1");
		assertNotNull(builder1);
		MoneyCurrency.Builder builder2 = builder1
				.withNamespace("ns1");
		assertTrue(builder1 == builder2);
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#withCurrencyCode(java.lang.String)}
	 * and {@link javax.money.CurrencyUnitImpl.Builder#getCurrencyCode()} .
	 */
	@Test
	public void testGetSetCurrencyCode() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.withCurrencyCode("code1");
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#withDefaultFractionDigits(int)}
	 * and
	 * {@link javax.money.CurrencyUnitImpl.Builder#getDefaultFractionDigits()}.
	 */
	@Test
	public void testGetSetDefaultFractionDigits() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.withDefaultFractionDigits(10);
		builder.withDefaultFractionDigits(-1);
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#withDefaultFractionDigits(int)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetSetDefaultFractionDigits_InvalidInput() {
		new MoneyCurrency.Builder().withDefaultFractionDigits(-10);
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#withNumericCode(int)} and
	 * {@link javax.money.CurrencyUnitImpl.Builder#getNumericCode()}.
	 */
	@Test
	public void testGetSetNumericCode() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.withNumericCode(10);
		builder.withNumericCode(-1);
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#withNumericCode(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetSetNumericCode_InvalidInput() {
		new MoneyCurrency.Builder().withNumericCode(-10);
	}

	/**
	 * Test method for {@link javax.money.CurrencyUnitImpl.Builder#build()}.
	 */
	@Test
	public void testBuild() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.withNamespace("ns").withCurrencyCode("cd")
				.withDefaultFractionDigits(101)
				.withNumericCode(7);
		CurrencyUnit cu = builder.build();
		assertEquals("ns", cu.getNamespace().getId());
		assertEquals("cd", cu.getCurrencyCode());
		assertEquals(101, cu.getDefaultFractionDigits());
		assertEquals(7, cu.getNumericCode());
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#build(boolean)}.
	 */
	@Test
	public void testBuildBoolean() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.withNamespace("testBuildBoolean").withCurrencyCode("cd")
				.withDefaultFractionDigits(101).withNumericCode(7);
		CurrencyUnit cu = builder.build(false);
		assertEquals("testBuildBoolean", cu.getNamespace().getId());
		assertEquals("cd", cu.getCurrencyCode());
		assertEquals(101, cu.getDefaultFractionDigits());
		assertEquals(7, cu.getNumericCode());
		CurrencyUnit cu2 = MoneyCurrency.of("testBuildBoolean", "cd");
		assertTrue(cu2 != cu);
		cu = builder.build(true);
		cu2 = MoneyCurrency.of("testBuildBoolean", "cd");
		assertTrue(cu2 == cu);
	}

}