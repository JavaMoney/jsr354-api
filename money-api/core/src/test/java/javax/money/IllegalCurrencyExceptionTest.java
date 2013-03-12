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
 * Tests the {@link UnknownCurrencyException} class.
 * 
 * @author Anatole Tresch
 */
public class IllegalCurrencyExceptionTest {

	@Test
	public void testIsRuntimeException() {
		assertTrue(RuntimeException.class.isAssignableFrom(IllegalCurrencyException.class));
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#UnknownCurrencyException(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testIllegalCurrencyException() {
		new IllegalCurrencyException("ns", "code");
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#UnknownCurrencyException(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testIllegalCurrencyException_NoNamespace()  throws IllegalArgumentException{
		new IllegalCurrencyException((String)null, "code");
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#UnknownCurrencyException(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void tesIllegalCurrencyException_NoCode() throws IllegalArgumentException{
		new IllegalCurrencyException("ns", (String)null);
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#UnknownCurrencyException(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testIllegalCurrencyException_NoParams()  throws IllegalArgumentException{
		new IllegalCurrencyException((String)null, (String)null);
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#getNamespace()}.
	 */
	@Test
	public void testGetNamespace() {
		IllegalCurrencyException ex = new IllegalCurrencyException("ns", "code");
		assertNotNull(ex.getNamespace());
		assertEquals("ns", ex.getNamespace());
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#getCurrencyCode()}.
	 */
	@Test
	public void testGetCurrencyCode() {
		IllegalCurrencyException ex = new IllegalCurrencyException("ns",
				"code01");
		assertNotNull(ex.getCurrencyCode());
		assertEquals("code01", ex.getCurrencyCode());
	}

}