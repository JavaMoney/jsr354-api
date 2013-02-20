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