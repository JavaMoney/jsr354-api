/**
 * 
 */
package javax.money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.Test;

/**
 * @author Anatole
 * 
 */
public class MonetaryCalculationsTest {

	/**
	 * Test method for {@link javax.money.Money#getMajorLong()}.
	 */
	@Test
	public void testGetMajorLong() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		assertEquals(dec.setScale(0, RoundingMode.DOWN).longValueExact(),
				MonetaryCalculations.getMajorLong(m));
	}

	/**
	 * Test method for {@link javax.money.Money#getMajorInt()}.
	 */
	@Test
	public void testGetMajorInt() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		assertEquals(dec.setScale(0, RoundingMode.DOWN).intValueExact(),
				MonetaryCalculations.getMajorInt(m));
	}

	/**
	 * Test method for {@link javax.money.Money#getMinorLong()}.
	 */
	@Test
	public void testGetMinorLong() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		assertEquals(dec.movePointRight(dec.precision()).longValueExact(),
				MonetaryCalculations.getMinorLong(m));
	}

	/**
	 * Test method for {@link javax.money.Money#getMinorInt()}.
	 */
	@Test
	public void testGetMinorInt() {
		BigDecimal dec = new BigDecimal("1234.12");
		Money m = Money.of("CHF", dec);
		assertEquals(dec.movePointRight(dec.precision()).intValueExact(),
				MonetaryCalculations.getMinorInt(m));
	}

	/**
	 * Test method for {@link javax.money.Money#getMajorPart()}.
	 */
	@Test
	public void testGetMajorPart() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		assertEquals(Money.of("CHF", MonetaryCalculations.getMajorLong(m)),
				MonetaryCalculations.getMajorPart(m));
	}

	/**
	 * Test method for {@link javax.money.Money#getMinorPart()}.
	 */
	@Test
	public void testGetMinorPart() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		assertEquals(Money.of("CHF", MonetaryCalculations.getMinorLong(m)).longValue(),
				MonetaryCalculations.getMinorPart(m).longValue());
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#hasSameCurrencyAs(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testHasSameCurrencyAs() {
		assertTrue(MonetaryCalculations.hasSameCurrency(Money.of("CHF", 0),
				Money.of("CHF", 0), Money.of("CHF", 45)));
		assertFalse(MonetaryCalculations.hasSameCurrency(Money.of("CHF", 0),
				Money.of("CHF", 0), Money.of("EUR", 45), Money.of("CHF", 0)));
	}

}
