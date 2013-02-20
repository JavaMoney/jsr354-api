/**
 * 
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
	public void testUnknownCurrencyException_NoNamespace() {
		new UnknownCurrencyException(null, "code");
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#UnknownCurrencyException(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testUnknownCurrencyException_NoCode() {
		new UnknownCurrencyException("ns", null);
	}

	/**
	 * Test method for
	 * {@link javax.money.UnknownCurrencyException#UnknownCurrencyException(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testUnknownCurrencyException_NoParams() {
		new UnknownCurrencyException(null, null);
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
