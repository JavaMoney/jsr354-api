/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * Contributors:
 *    Anatole Tresch - initial version.
 */
package javax.money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for {@link CurrencyMismatchException}.
 * 
 * @author Anatole
 */
public class CurrencyMismatchExceptionTest {

	/**
	 * Test method for
	 * {@link javax.money.CurrencyMismatchException#CurrencyMismatchException(javax.money.CurrencyUnit, javax.money.CurrencyUnit)}
	 * .
	 */
	@Test
	public void testCurrencyMismatchException() {
		new CurrencyMismatchException(CurrencyUnitImpl.getInstance("CHF"),
				CurrencyUnitImpl.getInstance("USD"));
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyMismatchException#CurrencyMismatchException(javax.money.CurrencyUnit, javax.money.CurrencyUnit)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCurrencyMismatchException_FirstParamNull() {
		new CurrencyMismatchException(null, CurrencyUnitImpl.getInstance("USD"));
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyMismatchException#CurrencyMismatchException(javax.money.CurrencyUnit, javax.money.CurrencyUnit)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCurrencyMismatchException_SecondParamNull() {
		new CurrencyMismatchException(CurrencyUnitImpl.getInstance("CHF"), null);
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyMismatchException#CurrencyMismatchException(javax.money.CurrencyUnit, javax.money.CurrencyUnit)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCurrencyMismatchException_BothParamsNull() {
		new CurrencyMismatchException(null, null);
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyMismatchException#getSourceCurrency()}.
	 */
	@Test
	public void testGetSourceCurrency() {
		CurrencyUnit src = CurrencyUnitImpl.getInstance("CHF");
		CurrencyUnit tgt = CurrencyUnitImpl.getInstance("USD");
		CurrencyMismatchException ex = new CurrencyMismatchException(src, tgt);
		assertEquals(src, ex.getSource());
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyMismatchException#getTargetCurrency()}.
	 */
	@Test
	public void testGetTargetCurrency() {
		CurrencyUnit src = CurrencyUnitImpl.getInstance("CHF");
		CurrencyUnit tgt = CurrencyUnitImpl.getInstance("USD");
		CurrencyMismatchException ex = new CurrencyMismatchException(src, tgt);
		assertEquals(tgt, ex.getTarget());
	}

	/**
	 * Test method for {@link javax.money.CurrencyMismatchException#toString()}.
	 */
	@Test
	public void testToString() {
		CurrencyUnit src = CurrencyUnitImpl.getInstance("CHF");
		CurrencyUnit tgt = CurrencyUnitImpl.getInstance("USD");
		CurrencyMismatchException ex = new CurrencyMismatchException(src, tgt);
		String toString = ex.toString();
		assertNotNull(toString);
		assertTrue("Doe not contain src currency in toString.",
				toString.contains(src.getCurrencyCode()));
		assertTrue("Doe not contain tgt currency in toString.",
				toString.contains(tgt.getCurrencyCode()));
	}

}