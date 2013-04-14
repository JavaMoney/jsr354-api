/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Currency;

import org.junit.Test;

public class MoneyCurrencyTest {

	@Test
	public void testOfCurrency() {
		CurrencyUnit cur = MoneyCurrency.of(Currency.getInstance("CHF"));
		assertNotNull(cur);
		assertEquals("CHF", cur.getCurrencyCode());
	}

	@Test
	public void testOfString() {
		CurrencyUnit cur = MoneyCurrency.of("CHF");
		assertNotNull(cur);
		assertEquals("CHF", cur.getCurrencyCode());

	}

	@Test
	public void testOfStringString() {
		CurrencyUnit cur = MoneyCurrency.of(MoneyCurrency.ISO_NAMESPACE, "CHF");
		assertNotNull(cur);
		assertEquals("CHF", cur.getCurrencyCode());
		assertEquals(MoneyCurrency.ISO_NAMESPACE, cur.getNamespace());
	}

	@Test
	public void testIsVirtual() {
		CurrencyUnit cur = MoneyCurrency.of(MoneyCurrency.ISO_NAMESPACE, "CHF");
		assertNotNull(cur);
		assertFalse(cur.isVirtual());
	}

	@Test
	public void testGetNamespace() {
		CurrencyUnit cur = MoneyCurrency.of(MoneyCurrency.ISO_NAMESPACE, "CHF");
		assertNotNull(cur);
		assertEquals(MoneyCurrency.ISO_NAMESPACE, cur.getNamespace());
	}

	@Test
	public void testGetValidFrom() {
		CurrencyUnit cur = MoneyCurrency.of(MoneyCurrency.ISO_NAMESPACE, "CHF");
		assertNotNull(cur);
		assertNull(cur.getValidFrom());
	}

	@Test
	public void testGetValidUntil() {
		CurrencyUnit cur = MoneyCurrency.of(MoneyCurrency.ISO_NAMESPACE, "CHF");
		assertNotNull(cur);
		assertNull(cur.getValidUntil());
	}

	@Test
	public void testGetCurrencyCode() {
		CurrencyUnit cur = MoneyCurrency.of(MoneyCurrency.ISO_NAMESPACE, "CHF");
		assertNotNull(cur);
		assertEquals("CHF", cur.getCurrencyCode());
	}

	@Test
	public void testGetNumericCode() {
		CurrencyUnit cur = MoneyCurrency.of(MoneyCurrency.ISO_NAMESPACE, "CHF");
		assertNotNull(cur);
		assertEquals(Currency.getInstance("CHF").getNumericCode(),
				cur.getNumericCode());
	}

	@Test
	public void testGetDefaultFractionDigits() {
		CurrencyUnit cur = MoneyCurrency.of(MoneyCurrency.ISO_NAMESPACE, "CHF");
		assertNotNull(cur);
		assertEquals(Currency.getInstance("CHF").getDefaultFractionDigits(),
				cur.getDefaultFractionDigits());
	}

	@Test
	public void testIsLegalTender() {
		CurrencyUnit cur = MoneyCurrency.of(MoneyCurrency.ISO_NAMESPACE, "CHF");
		assertNotNull(cur);
		assertTrue(cur.isLegalTender());
		cur = MoneyCurrency.of(MoneyCurrency.ISO_NAMESPACE, "XXX");
		assertNotNull(cur);
		assertFalse(cur.isLegalTender());
	}

	@Test
	public void testCompareTo() {
		CurrencyUnit cu = MoneyCurrency.of("CHF");
		CurrencyUnit cu2 = MoneyCurrency.of("EUR");
		assertTrue(((Comparable) cu).compareTo(cu2) < 0);
		assertTrue(((Comparable) cu2).compareTo(cu) > 0);
		assertTrue(((Comparable) cu).compareTo(cu) == 0);
	}

	@Test
	public void testToString() {
		assertEquals("CHF", MoneyCurrency.of("CHF").toString());
	}

}
