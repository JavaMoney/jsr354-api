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
	 * {@link javax.money.MoneyRounding#of(javax.money.CurrencyUnit, java.math.RoundingMode)}
	 * .
	 */
	@Test
	public void testGetRoundingCurrencyUnitRoundingMode() {
		MoneyRounding rounding = MoneyRounding.of(MoneyCurrency.of("CHF"),
				RoundingMode.FLOOR);
		assertNotNull(rounding);
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyRounding#of(javax.money.CurrencyUnit)}.
	 */
	@Test
	public void testGetRoundingCurrencyUnit() {
		MoneyRounding rounding = MoneyRounding.of(MoneyCurrency.of("CHF"));
		assertNotNull(rounding);
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyRounding#of(int, java.math.RoundingMode)}
	 * .
	 */
	@Test
	public void testGetRoundingIntRoundingMode() {
		MoneyRounding rounding = MoneyRounding.of(3, RoundingMode.FLOOR);
		assertNotNull(rounding);
	}

}
