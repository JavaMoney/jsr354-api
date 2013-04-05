/**
 * 
 */
package javax.money;

import static org.junit.Assert.assertNotNull;

import java.math.RoundingMode;

import org.junit.Test;

/**
 * @author Anatole
 * 
 */
public class MoneyRoundingTest {

	/**
	 * Test method for
	 * {@link javax.money.MoneyRounding#getRounding(javax.money.CurrencyUnit, java.math.RoundingMode)}
	 * .
	 */
	@Test
	public void testGetRoundingCurrencyUnitRoundingMode() {
		Rounding rounding = MoneyRounding.getRounding(MoneyCurrency.of("CHF"),
				RoundingMode.FLOOR);
		assertNotNull(rounding);
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyRounding#getRounding(javax.money.CurrencyUnit)}.
	 */
	@Test
	public void testGetRoundingCurrencyUnit() {
		Rounding rounding = MoneyRounding.getRounding(MoneyCurrency.of("CHF"));
		assertNotNull(rounding);
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyRounding#getRounding(int, java.math.RoundingMode)}
	 * .
	 */
	@Test
	public void testGetRoundingIntRoundingMode() {
		Rounding rounding = MoneyRounding.getRounding(3, RoundingMode.FLOOR);
		assertNotNull(rounding);
	}

}
