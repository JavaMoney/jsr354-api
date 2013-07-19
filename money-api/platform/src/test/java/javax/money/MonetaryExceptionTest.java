/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Anatole
 * 
 */
public class MonetaryExceptionTest {

	/**
	 * Test method for
	 * {@link javax.money.MonetaryException#MonetaryException(java.lang.String)}
	 * .
	 */
	@Test
	public void testMonetaryExceptionString() {
		assertTrue(new MonetaryException("test") {
		}.getMessage().equals("test"));
	}

	/**
	 * Test method for
	 * {@link javax.money.MonetaryException#MonetaryException(java.lang.String, java.lang.Throwable)}
	 * .
	 */
	@Test
	public void testMonetaryExceptionStringThrowable() {
		Exception cause = new Exception("testEx");
		MonetaryException ex = new MonetaryException("test", cause) {
		};
		assertTrue(ex.getMessage().equals("test"));
		assertTrue(ex.getCause() == cause);
	}

}
