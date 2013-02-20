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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

/**
 * Tests for the {@link MoneyCurrency} class.
 * 
 * @author Anatole Tresch
 */
public class MoneyCurrencyTest {

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency#getInstance(java.util.Currency)}.
	 */
	@Test
	public void testGetInstanceCurrency() {
		Currency jdkCurrency = Currency.getInstance("CHF");
		CurrencyUnit cur = MoneyCurrency.getInstance(jdkCurrency);
		assertNotNull(cur);
		assertEquals(jdkCurrency.getCurrencyCode(), cur.getCurrencyCode());
		assertEquals(jdkCurrency.getNumericCode(), cur.getNumericCode());
		assertEquals(jdkCurrency.getDefaultFractionDigits(),
				cur.getDefaultFractionDigits());
		assertEquals(MoneyCurrency.class.getName() + "$JDKCurrencyAdapter", cur.getClass().getName());
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
	 * {@link javax.money.MoneyCurrency#getInstance(java.lang.String)}.
	 */
	@Test
	public void testGetInstanceString() {
		CurrencyUnit cur = MoneyCurrency.getInstance("CHF");
		assertNotNull(cur);
		Currency jdkCurrency = Currency.getInstance("CHF");
		assertEquals(jdkCurrency.getCurrencyCode(), cur.getCurrencyCode());
		assertEquals(jdkCurrency.getNumericCode(), cur.getNumericCode());
		assertEquals(jdkCurrency.getDefaultFractionDigits(),
				cur.getDefaultFractionDigits());
		assertEquals(MoneyCurrency.class.getName() + "$JDKCurrencyAdapter", cur.getClass().getName());
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
	 * {@link javax.money.MoneyCurrency#getInstance(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetInstanceStringString() {
		CurrencyUnit cur = MoneyCurrency.getInstance("USD");
		CurrencyUnit cur2 = MoneyCurrency.getInstance(
				CurrencyUnit.ISO_NAMESPACE, "USD");
		assertNotNull(cur2);
		assertTrue(cur == cur2);
		Currency jdkCurrency = Currency.getInstance("USD");
		assertEquals(jdkCurrency.getCurrencyCode(), cur.getCurrencyCode());
		assertEquals(jdkCurrency.getNumericCode(), cur.getNumericCode());
		assertEquals(jdkCurrency.getDefaultFractionDigits(),
				cur.getDefaultFractionDigits());
		assertEquals(MoneyCurrency.class.getName() + "$JDKCurrencyAdapter", cur.getClass().getName());
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
	 * Test method for {@link javax.money.MoneyCurrency#isVirtual()}.
	 */
	@Test
	public void testIsVirtual() {
		CurrencyUnit cur = MoneyCurrency.getInstance("USD");
		assertFalse(cur.isVirtual());
		cur = MoneyCurrency.getInstance("XAU");
		assertFalse(cur.isVirtual());
	}

	/**
	 * Test method for {@link javax.money.MoneyCurrency#getNamespace()}.
	 */
	@Test
	public void testGetNamespace() {
		CurrencyUnit cur = MoneyCurrency.getInstance("USD");
		assertEquals(CurrencyUnit.ISO_NAMESPACE, cur.getNamespace());
	}

	/**
	 * Test method for {@link javax.money.MoneyCurrency#getValidFrom()}.
	 */
	@Test
	public void testGetValidFrom() {
		CurrencyUnit cur = MoneyCurrency.getInstance("USD");
		assertNull(cur.getValidFrom());
	}

	/**
	 * Test method for {@link javax.money.MoneyCurrency#getValidUntil()}.
	 */
	@Test
	public void testGetValidUntil() {
		CurrencyUnit cur = MoneyCurrency.getInstance("USD");
		assertNull(cur.getValidUntil());
	}

	/**
	 * Test method for {@link javax.money.MoneyCurrency#getCurrencyCode()}.
	 */
	@Test
	public void testGetCurrencyCode() {
		CurrencyUnit cur = MoneyCurrency.getInstance("USD");
		assertEquals("USD", cur.getCurrencyCode());
		cur = MoneyCurrency.getInstance("EUR");
		assertEquals("EUR", cur.getCurrencyCode());
	}

	/**
	 * Test method for {@link javax.money.MoneyCurrency#getNumericCode()}.
	 */
	@Test
	public void testGetNumericCode() {
		CurrencyUnit cur = MoneyCurrency.getInstance("USD");
		assertEquals(840, cur.getNumericCode());
		cur = MoneyCurrency.getInstance("EUR");
		assertEquals(978, cur.getNumericCode());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency#getDefaultFractionDigits()}.
	 */
	@Test
	public void testGetDefaultFractionDigits() {
		CurrencyUnit cur = MoneyCurrency.getInstance("USD");
		assertEquals(2, cur.getDefaultFractionDigits());
		cur = MoneyCurrency.getInstance("JPY");
		assertEquals(0, cur.getDefaultFractionDigits());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency#getAttribute(java.lang.String, java.lang.Class)}
	 * .
	 */
	@Test
	public void testGetAttribute() {
		CurrencyUnit cur = MoneyCurrency.getInstance("USD");
		assertNull(cur.getAttribute("A", Object.class));
	}

	/**
	 * Test method for {@link javax.money.MoneyCurrency#getAttributeKeys()}.
	 */
	@Test
	public void testGetAttributeKeys() {
		CurrencyUnit cur = MoneyCurrency.getInstance("USD");
		assertNotNull(cur.getAttributeKeys());
		assertFalse(cur.getAttributeKeys().hasMoreElements());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency#getAttributeType(java.lang.String)}.
	 */
	@Test
	public void testGetAttributeType() {
		CurrencyUnit cur = MoneyCurrency.getInstance("USD");
		assertNull(cur.getAttributeType("A"));
	}

	/**
	 * Test method for {@link javax.money.MoneyCurrency#isLegalTender()}.
	 */
	@Test
	public void testIsLegalTender() {
		CurrencyUnit cur = MoneyCurrency.getInstance("USD");
		assertTrue(cur.isLegalTender());
		cur = MoneyCurrency.getInstance("XAU");
		assertFalse(cur.isLegalTender());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency#compareTo(javax.money.CurrencyUnit)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCompareTo() {
		CurrencyUnit cur1 = MoneyCurrency.getInstance("USD");
		CurrencyUnit cur2 = MoneyCurrency.getInstance("EUR");
		assertTrue(cur1 instanceof Comparable);
		assertTrue(cur2 instanceof Comparable);
		assertTrue(0<((Comparable<CurrencyUnit>)cur1).compareTo(cur2));
		assertTrue(0>((Comparable<CurrencyUnit>)cur2).compareTo(cur1));
		assertEquals(0,((Comparable<CurrencyUnit>)cur1).compareTo(cur1));
		assertEquals(0,((Comparable<CurrencyUnit>)cur2).compareTo(cur2));
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder();
		builder.setNamespace("Test");
		builder.setCurrencyCode("TEST");
		CurrencyUnit cur3 = builder.build();
		assertTrue(cur3 instanceof Comparable);
		assertTrue(0<((Comparable<CurrencyUnit>)cur3).compareTo(cur2));
		assertTrue(0<((Comparable<CurrencyUnit>)cur3).compareTo(cur1));
		assertTrue(0>((Comparable<CurrencyUnit>)cur1).compareTo(cur3));
		assertTrue(0>((Comparable<CurrencyUnit>)cur2).compareTo(cur3));
		assertEquals(0,((Comparable<CurrencyUnit>)cur3).compareTo(cur3));
	}

	/**
	 * Test method for {@link javax.money.MoneyCurrency#toString()}.
	 */
	@Test
	public void testToString() {
		CurrencyUnit cur1 = MoneyCurrency.getInstance("USD");
		String toString = cur1.toString();
		assertNotNull(toString);
		assertTrue("Does not contain currency code.", toString.contains("USD"));
		assertFalse("Does contain ISO namespace!", toString.contains(CurrencyUnit.ISO_NAMESPACE));
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder();
		builder.setNamespace("Test");
		builder.setCurrencyCode("TEST");
		CurrencyUnit cur3 = builder.build();
		toString = cur3.toString();
		assertTrue("Does not contain currency code.", toString.contains("Test"));
		assertTrue("Does not contain namespace.", toString.contains("TEST"));
	}

}
