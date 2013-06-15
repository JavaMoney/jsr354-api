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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
	 * {@link javax.money.CurrencyUnitImpl.Builder#Builder(java.lang.String)}.
	 */
	@Test
	public void testBuilderString() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder("test");
		assertEquals("test", builder.getCurrencyCode());
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#Builder(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBuilderString_BadCase() {
		new MoneyCurrency.Builder(null);
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#Builder(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testBuilderStringString() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder("ns", "test");
		assertEquals("test", builder.getCurrencyCode());
		assertEquals("ns", builder.getNamespace());
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#setNamespace(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetSetNamespace() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setNamespace("ns1");
		assertEquals("ns1", builder.getNamespace());
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#setCurrencyCode(java.lang.String)}
	 * and {@link javax.money.CurrencyUnitImpl.Builder#getCurrencyCode()} .
	 */
	@Test
	public void testGetSetCurrencyCode() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setCurrencyCode("code1");
		assertEquals("code1", builder.getCurrencyCode());
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#setDefaultFractionDigits(int)}
	 * and
	 * {@link javax.money.CurrencyUnitImpl.Builder#getDefaultFractionDigits()}.
	 */
	@Test
	public void testGetSetDefaultFractionDigits() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setDefaultFractionDigits(10);
		assertEquals(10, builder.getDefaultFractionDigits());
		builder.setDefaultFractionDigits(-1);
		assertEquals(-1, builder.getDefaultFractionDigits());
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#setDefaultFractionDigits(int)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetSetDefaultFractionDigits_InvalidInput() {
		new MoneyCurrency.Builder().setDefaultFractionDigits(-10);
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#setNumericCode(int)} and
	 * {@link javax.money.CurrencyUnitImpl.Builder#getNumericCode()}.
	 */
	@Test
	public void testGetSetNumericCode() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setNumericCode(10);
		assertEquals(10, builder.getNumericCode());
		builder.setNumericCode(-1);
		assertEquals(-1, builder.getNumericCode());
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#setNumericCode(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetSetNumericCode_InvalidInput() {
		new MoneyCurrency.Builder().setNumericCode(-10);
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#setLegalTender(boolean)} and
	 * {@link javax.money.CurrencyUnitImpl.Builder#hasLegalTender()}.
	 */
	@Test
	public void testGetSetLegalTender() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setLegalTender(true);
		assertTrue(builder.isLegalTender());
		builder.setLegalTender(false);
		assertFalse(builder.isLegalTender());
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#setVirtual(boolean)} and
	 * {@link javax.money.CurrencyUnitImpl.Builder#isVirtual()}.
	 */
	@Test
	public void testGetSetVirtual() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setVirtual(true);
		assertTrue(builder.isVirtual());
		builder.setVirtual(false);
		assertFalse(builder.isVirtual());
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#isBuildable()}.
	 */
	@Test
	public void testIsBuildable() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder();
		assertFalse(builder.isBuildable());
		builder.setNamespace("ns");
		assertFalse(builder.isBuildable());
		builder.setCurrencyCode("cd");
		assertTrue(builder.isBuildable());
	}

	/**
	 * Test method for {@link javax.money.CurrencyUnitImpl.Builder#build()}.
	 */
	@Test
	public void testBuild() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setNamespace("ns").setCurrencyCode("cd").setLegalTender(true)
				.setVirtual(false).setDefaultFractionDigits(101)
				.setNumericCode(7);
		CurrencyUnit cu = builder.build();
		assertEquals("ns", cu.getNamespace());
		assertEquals("cd", cu.getCurrencyCode());
		assertEquals(101, cu.getDefaultFractionDigits());
		assertEquals(7, cu.getNumericCode());
		assertTrue(cu.isLegalTender());
		assertFalse(cu.isVirtual());
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyUnitImpl.Builder#build(boolean)}.
	 */
	@Test
	public void testBuildBoolean() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setNamespace("testBuildBoolean").setCurrencyCode("cd")
				.setLegalTender(true).setVirtual(false)
				.setDefaultFractionDigits(101).setNumericCode(7);
		CurrencyUnit cu = builder.build(true);
		assertEquals("testBuildBoolean", cu.getNamespace());
		assertEquals("cd", cu.getCurrencyCode());
		assertEquals(101, cu.getDefaultFractionDigits());
		assertEquals(7, cu.getNumericCode());
		assertTrue(cu.isLegalTender());
		assertFalse(cu.isVirtual());
		CurrencyUnit cu2 = MoneyCurrency.of("testBuildBoolean", "cd");
		assertTrue(cu2 != cu);
		cu = builder.build(true);
		cu2 = MoneyCurrency.of("testBuildBoolean", "cd");
		assertTrue(cu2 == cu);
	}

}