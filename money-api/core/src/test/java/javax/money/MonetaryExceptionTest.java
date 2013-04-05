/**
 * 
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
