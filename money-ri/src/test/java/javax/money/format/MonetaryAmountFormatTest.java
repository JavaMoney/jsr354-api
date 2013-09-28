/**
 * 
 */
package javax.money.format;

import static org.junit.Assert.fail;

import java.util.Locale;

import javax.money.Money;

import org.junit.Test;

/**
 * @author Anatole
 * 
 */
public class MonetaryAmountFormatTest {

	/**
	 * Test method for
	 * {@link javax.money.format.MonetaryAmountFormat#getAmountStyle()}.
	 */
	@Test
	public void testGetAmountStyle() {
		MonetaryAmountFormat defaultFormat = new MonetaryAmountFormat.Builder(
				Locale.GERMANY).build();
		System.out.println("CHF 12.50 -> "
				+ defaultFormat.format(Money.of("CHF", 12.50)));
		System.out
				.println("INR 123456789101112.123456 -> "
						+ defaultFormat.format(Money.of("INR",
								123456789101112.123456)));
		defaultFormat = new MonetaryAmountFormat.Builder(new Locale("", "IN"))
				.build();
		System.out.println("CHF 12.50 -> "
				+ defaultFormat.format(Money.of("CHF", 12.50)));
		System.out
				.println("INR 123456789101112.123456 -> "
						+ defaultFormat.format(Money.of("INR",
								123456789101112.123456)));
		Locale india = new Locale("", "IN");
		defaultFormat = new MonetaryAmountFormat.Builder(india).withNumberGroupSizes(3, 2).build();
		System.out
				.println("INR 123456789101112.123456 -> "
						+ defaultFormat.format(Money.of("INR",
								123456789101112.123456)));
	}

	/**
	 * Test method for
	 * {@link javax.money.format.MonetaryAmountFormat#getDefaultCurrency()}.
	 */
	@Test
	public void testGetDefaultCurrency() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.format.MonetaryAmountFormat#format(javax.money.MonetaryAmount)}
	 * .
	 */
	@Test
	public void testFormat() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.format.MonetaryAmountFormat#print(java.lang.Appendable, javax.money.MonetaryAmount)}
	 * .
	 */
	@Test
	public void testPrint() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.format.MonetaryAmountFormat#parse(java.lang.CharSequence)}
	 * .
	 */
	@Test
	public void testParse() {
		fail("Not yet implemented");
	}

}
