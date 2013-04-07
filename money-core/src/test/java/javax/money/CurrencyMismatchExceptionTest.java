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
		new CurrencyMismatchException(MoneyCurrency.of("CHF"),
				MoneyCurrency.of("USD"));
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyMismatchException#CurrencyMismatchException(javax.money.CurrencyUnit, javax.money.CurrencyUnit)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCurrencyMismatchException_FirstParamNull() {
		new CurrencyMismatchException(null, MoneyCurrency.of("USD"));
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyMismatchException#CurrencyMismatchException(javax.money.CurrencyUnit, javax.money.CurrencyUnit)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCurrencyMismatchException_SecondParamNull() {
		new CurrencyMismatchException(MoneyCurrency.of("CHF"), null);
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
		CurrencyUnit src = MoneyCurrency.of("CHF");
		CurrencyUnit tgt = MoneyCurrency.of("USD");
		CurrencyMismatchException ex = new CurrencyMismatchException(src, tgt);
		assertEquals(src, ex.getSource());
	}

	/**
	 * Test method for
	 * {@link javax.money.CurrencyMismatchException#getTargetCurrency()}.
	 */
	@Test
	public void testGetTargetCurrency() {
		CurrencyUnit src = MoneyCurrency.of("CHF");
		CurrencyUnit tgt = MoneyCurrency.of("USD");
		CurrencyMismatchException ex = new CurrencyMismatchException(src, tgt);
		assertEquals(tgt, ex.getTarget());
	}

	/**
	 * Test method for {@link javax.money.CurrencyMismatchException#toString()}.
	 */
	@Test
	public void testToString() {
		CurrencyUnit src = MoneyCurrency.of("CHF");
		CurrencyUnit tgt = MoneyCurrency.of("USD");
		CurrencyMismatchException ex = new CurrencyMismatchException(src, tgt);
		String toString = ex.toString();
		assertNotNull(toString);
		assertTrue("Doe not contain src currency in toString.",
				toString.contains(src.getCurrencyCode()));
		assertTrue("Doe not contain tgt currency in toString.",
				toString.contains(tgt.getCurrencyCode()));
	}

}