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
public class UnknownCurrencyExceptionTest {

	@Test
	public void testIsRuntimeException() {
		assertTrue(RuntimeException.class.isAssignableFrom(UnknownCurrencyException.class));
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#UnknownCurrencyException(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testUnknownCurrencyException() {
		new UnknownCurrencyException("ns", "code");
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#UnknownCurrencyException(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testUnknownCurrencyException_NoNamespace()  throws IllegalArgumentException{
		new UnknownCurrencyException((String)null, "code");
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#UnknownCurrencyException(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testUnknownCurrencyException_NoCode() throws IllegalArgumentException{
		new UnknownCurrencyException("ns", (String)null);
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#UnknownCurrencyException(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalCurrencyException_NoParams()  throws IllegalArgumentException{
		new UnknownCurrencyException((String)null, (String)null);
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#getNamespace()}.
	 */
	@Test
	public void testGetNamespace() {
		UnknownCurrencyException ex = new UnknownCurrencyException("ns", "code");
		assertNotNull(ex.getNamespace());
		assertEquals("ns", ex.getNamespace());
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#getCurrencyCode()}.
	 */
	@Test
	public void testGetCurrencyCode() {
		UnknownCurrencyException ex = new UnknownCurrencyException("ns",
				"code01");
		assertNotNull(ex.getCurrencyCode());
		assertEquals("code01", ex.getCurrencyCode());
	}

}