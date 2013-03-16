/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
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