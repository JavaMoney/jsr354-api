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
		assertEquals("ISO-4217:CHF", MoneyCurrency.of("CHF").toString());
	}

}
