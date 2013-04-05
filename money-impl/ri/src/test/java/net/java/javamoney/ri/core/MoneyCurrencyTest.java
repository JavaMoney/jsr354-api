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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Currency;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.LocalizableCurrencyUnit;
import javax.money.MoneyCurrency;

import org.junit.Test;

/**
 * Tests for the {@link CurrencyUnitImpl} class.
 * 
 * @author Anatole Tresch
 */
public class MoneyCurrencyTest {

	/**
	 * Test method for
	 * {@link net.java.javamoney.ri.CurrencyUnitImpl#of(java.util.Currency)} .
	 */
	@Test
	public void testGetInstanceCurrency() {
		Currency jdkCurrency = Currency.getInstance("CHF");
		CurrencyUnit cur = MoneyCurrency.of(jdkCurrency);
		assertNotNull(cur);
		assertEquals(jdkCurrency.getCurrencyCode(), cur.getCurrencyCode());
		assertEquals(jdkCurrency.getNumericCode(), cur.getNumericCode());
		assertEquals(jdkCurrency.getDefaultFractionDigits(),
				cur.getDefaultFractionDigits());
		assertEquals(MoneyCurrency.class.getName() + "$JDKCurrencyAdapter", cur
				.getClass().getName());
		assertTrue(cur instanceof LocalizableCurrencyUnit);
		LocalizableCurrencyUnit lCur = (LocalizableCurrencyUnit) cur;
		assertEquals(jdkCurrency.getDisplayName(), lCur.getDisplayName());
		assertEquals(jdkCurrency.getDisplayName(Locale.GERMAN),
				lCur.getDisplayName(Locale.GERMAN));
		assertEquals(jdkCurrency.getSymbol(), lCur.getSymbol());
		assertEquals(jdkCurrency.getSymbol(Locale.GERMAN),
				lCur.getSymbol(Locale.GERMAN));
	}

	/**
	 * Test method for
	 * {@link net.java.javamoney.ri.CurrencyUnitImpl#of(java.lang.String)} .
	 */
	@Test
	public void testGetInstanceString() {
		CurrencyUnit cur = MoneyCurrency.of("CHF");
		assertNotNull(cur);
		Currency jdkCurrency = Currency.getInstance("CHF");
		assertEquals(jdkCurrency.getCurrencyCode(), cur.getCurrencyCode());
		assertEquals(jdkCurrency.getNumericCode(), cur.getNumericCode());
		assertEquals(jdkCurrency.getDefaultFractionDigits(),
				cur.getDefaultFractionDigits());
		assertEquals(MoneyCurrency.class.getName() + "$JDKCurrencyAdapter", cur
				.getClass().getName());
		assertTrue(cur instanceof LocalizableCurrencyUnit);
		LocalizableCurrencyUnit lCur = (LocalizableCurrencyUnit) cur;
		assertEquals(jdkCurrency.getDisplayName(), lCur.getDisplayName());
		assertEquals(jdkCurrency.getDisplayName(Locale.GERMAN),
				lCur.getDisplayName(Locale.GERMAN));
		assertEquals(jdkCurrency.getSymbol(), lCur.getSymbol());
		assertEquals(jdkCurrency.getSymbol(Locale.GERMAN),
				lCur.getSymbol(Locale.GERMAN));
	}

	/**
	 * Test method for
	 * {@link net.java.javamoney.ri.CurrencyUnitImpl#of(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetInstanceStringString() {
		CurrencyUnit cur = MoneyCurrency.of("USD");
		CurrencyUnit cur2 = MoneyCurrency
				.of(MoneyCurrency.ISO_NAMESPACE, "USD");
		assertNotNull(cur2);
		assertTrue(cur == cur2);
		Currency jdkCurrency = Currency.getInstance("USD");
		assertEquals(jdkCurrency.getCurrencyCode(), cur.getCurrencyCode());
		assertEquals(jdkCurrency.getNumericCode(), cur.getNumericCode());
		assertEquals(jdkCurrency.getDefaultFractionDigits(),
				cur.getDefaultFractionDigits());
		assertEquals(MoneyCurrency.class.getName() + "$JDKCurrencyAdapter", cur
				.getClass().getName());
		assertTrue(cur instanceof LocalizableCurrencyUnit);
		LocalizableCurrencyUnit lCur = (LocalizableCurrencyUnit) cur;
		assertEquals(jdkCurrency.getDisplayName(), lCur.getDisplayName());
		assertEquals(jdkCurrency.getDisplayName(Locale.GERMAN),
				lCur.getDisplayName(Locale.GERMAN));
		assertEquals(jdkCurrency.getSymbol(), lCur.getSymbol());
		assertEquals(jdkCurrency.getSymbol(Locale.GERMAN),
				lCur.getSymbol(Locale.GERMAN));
	}

	/**
	 * Test method for
	 * {@link net.java.javamoney.ri.CurrencyUnitImpl#isVirtual()}.
	 */
	@Test
	public void testIsVirtual() {
		CurrencyUnit cur = MoneyCurrency.of("USD");
		assertFalse(cur.isVirtual());
		cur = MoneyCurrency.of("XAU");
		assertFalse(cur.isVirtual());
	}

	/**
	 * Test method for
	 * {@link net.java.javamoney.ri.CurrencyUnitImpl#getNamespace()}.
	 */
	@Test
	public void testGetNamespace() {
		CurrencyUnit cur = MoneyCurrency.of("USD");
		assertEquals(MoneyCurrency.ISO_NAMESPACE, cur.getNamespace());
	}

	/**
	 * Test method for
	 * {@link net.java.javamoney.ri.CurrencyUnitImpl#getValidFrom()}.
	 */
	@Test
	public void testGetValidFrom() {
		CurrencyUnit cur = MoneyCurrency.of("USD");
		assertNull(cur.getValidFrom());
	}

	/**
	 * Test method for
	 * {@link net.java.javamoney.ri.CurrencyUnitImpl#getValidUntil()}.
	 */
	@Test
	public void testGetValidUntil() {
		CurrencyUnit cur = MoneyCurrency.of("USD");
		assertNull(cur.getValidUntil());
	}

	/**
	 * Test method for
	 * {@link net.java.javamoney.ri.CurrencyUnitImpl#getCurrencyCode()}.
	 */
	@Test
	public void testGetCurrencyCode() {
		CurrencyUnit cur = MoneyCurrency.of("USD");
		assertEquals("USD", cur.getCurrencyCode());
		cur = MoneyCurrency.of("EUR");
		assertEquals("EUR", cur.getCurrencyCode());
	}

	/**
	 * Test method for
	 * {@link net.java.javamoney.ri.CurrencyUnitImpl#getNumericCode()}.
	 */
	@Test
	public void testGetNumericCode() {
		CurrencyUnit cur = MoneyCurrency.of("USD");
		assertEquals(840, cur.getNumericCode());
		cur = MoneyCurrency.of("EUR");
		assertEquals(978, cur.getNumericCode());
	}

	/**
	 * Test method for
	 * {@link net.java.javamoney.ri.CurrencyUnitImpl#getDefaultFractionDigits()}
	 * .
	 */
	@Test
	public void testGetDefaultFractionDigits() {
		CurrencyUnit cur = MoneyCurrency.of("USD");
		assertEquals(2, cur.getDefaultFractionDigits());
		cur = MoneyCurrency.of("JPY");
		assertEquals(0, cur.getDefaultFractionDigits());
	}

	/**
	 * Test method for
	 * {@link net.java.javamoney.ri.CurrencyUnitImpl#isLegalTender()}.
	 */
	@Test
	public void testIsLegalTender() {
		CurrencyUnit cur = MoneyCurrency.of("USD");
		assertTrue(cur.isLegalTender());
		cur = MoneyCurrency.of("XAU");
		assertFalse(cur.isLegalTender());
	}

	/**
	 * Test method for
	 * {@link net.java.javamoney.ri.CurrencyUnitImpl#compareTo(javax.money.CurrencyUnit)}
	 * .
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCompareTo() {
		CurrencyUnit cur1 = MoneyCurrency.of("USD");
		CurrencyUnit cur2 = MoneyCurrency.of("EUR");
		assertTrue(cur1 instanceof Comparable);
		assertTrue(cur2 instanceof Comparable);
		assertTrue(0 < ((Comparable<CurrencyUnit>) cur1).compareTo(cur2));
		assertTrue(0 > ((Comparable<CurrencyUnit>) cur2).compareTo(cur1));
		assertEquals(0, ((Comparable<CurrencyUnit>) cur1).compareTo(cur1));
		assertEquals(0, ((Comparable<CurrencyUnit>) cur2).compareTo(cur2));
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder();
		builder.setNamespace("Test");
		builder.setCurrencyCode("TEST");
		CurrencyUnit cur3 = builder.build();
		assertTrue(cur3 instanceof Comparable);
		assertTrue(0 < ((Comparable<CurrencyUnit>) cur3).compareTo(cur2));
		assertTrue(0 < ((Comparable<CurrencyUnit>) cur3).compareTo(cur1));
		assertTrue(0 > ((Comparable<CurrencyUnit>) cur1).compareTo(cur3));
		assertTrue(0 > ((Comparable<CurrencyUnit>) cur2).compareTo(cur3));
		assertEquals(0, ((Comparable<CurrencyUnit>) cur3).compareTo(cur3));
	}

	/**
	 * Test method for {@link net.java.javamoney.ri.CurrencyUnitImpl#toString()}
	 * .
	 */
	@Test
	public void testToString() {
		CurrencyUnit cur1 = MoneyCurrency.of("USD");
		String toString = cur1.toString();
		assertNotNull(toString);
		assertTrue("Does not contain currency code.", toString.contains("USD"));
		assertFalse("Does contain ISO namespace!",
				toString.contains(MoneyCurrency.ISO_NAMESPACE));
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder();
		builder.setNamespace("Test");
		builder.setCurrencyCode("TEST");
		CurrencyUnit cur3 = builder.build();
		toString = cur3.toString();
		assertTrue("Does not contain currency code.", toString.contains("Test"));
		assertTrue("Does not contain namespace.", toString.contains("TEST"));
	}

}
