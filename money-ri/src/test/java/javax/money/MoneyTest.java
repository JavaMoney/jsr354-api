/**
 * 
 */
package javax.money;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import javax.money.function.MonetaryRoundings;

import org.junit.Test;

/**
 * @author Anatole
 * 
 */
public class MoneyTest {

	private static final BigDecimal TEN = new BigDecimal(10.0d);
	protected static final CurrencyUnit EURO = MoneyCurrency.of("EUR");
	protected static final CurrencyUnit DOLLAR = MoneyCurrency
			.of("USD");

	/**
	 * Test method for
	 * {@link javax.money.Money#of(javax.money.CurrencyUnit, java.math.BigDecimal)}
	 * .
	 */
	@Test
	public void testOfCurrencyUnitBigDecimal() {
		Money m = Money.of(MoneyCurrency.of("EUR"), TEN);
		assertEquals(TEN, m.asType(BigDecimal.class));
	}

	@Test
	public void testOfCurrencyUnitDouble() {
		Money m = Money.of(MoneyCurrency.of("EUR"), 10.0d);
		assertTrue(TEN.doubleValue() == m.doubleValue());
	}

	/**
	 * Test method for {@link javax.money.Money#getCurrency()}.
	 */
	@Test
	public void testGetCurrency() {
		MonetaryAmount money = Money.of(EURO, BigDecimal.TEN);
		assertNotNull(money.getCurrency());
		assertEquals("EUR", money.getCurrency().getCurrencyCode());
	}


	@Test
	public void testSubtractMonetaryAmount() {
		Money money1 = Money.of(EURO, BigDecimal.TEN);
		Money money2 = Money.of(EURO, BigDecimal.ONE);
		Money moneyResult = money1.subtract(money2);
		assertNotNull(moneyResult);
		assertEquals(9d, moneyResult.doubleValue(), 0d);
	}

	@Test
	public void testDivideAndRemainder_BigDecimal() {
		Money money1 = Money.of(EURO, BigDecimal.ONE);
		Money[] divideAndRemainder = money1.divideAndRemainder(new BigDecimal(
				"0.50000000000000000001"));
		assertThat(divideAndRemainder[0].asType(BigDecimal.class),
				equalTo(BigDecimal.ONE));
		assertThat(divideAndRemainder[1].asType(BigDecimal.class),
				equalTo(new BigDecimal("0.49999999999999999999")));
	}

	@Test
	public void testDivideToIntegralValue_BigDecimal() {
		Money money1 = Money.of(EURO, BigDecimal.ONE);
		Money result = money1.divideToIntegralValue(new BigDecimal(
				"0.50000000000000000001"));
		assertThat(result.asType(BigDecimal.class), equalTo(BigDecimal.ONE));
	}

	@Test
	public void comparePerformance() {
		Money money1 = Money.of(EURO, BigDecimal.ONE);
		long start = System.currentTimeMillis();
		final int NUM = 1000000;
		for (int i = 0; i < NUM; i++) {
			money1 = money1.add(Money.of(EURO, 1234567.3444));
			money1 = money1.subtract(Money.of(EURO, 232323));
			money1 = money1.multiply(3.4);
			money1 = money1.divide(5.456);
//			money1 = money1.with(MonetaryRoundings.getRounding());
		}
		long end = System.currentTimeMillis();
		long duration = end - start;
		System.out.println("Duration for 1000000 operations (Money/BD): "
				+ duration + " ms (" + ((duration * 1000) / NUM) + " ns per loop) -> "
				+ money1);

		FastMoney money2 = FastMoney.of(EURO, BigDecimal.ONE);
		start = System.currentTimeMillis();
		for (int i = 0; i < NUM; i++) {
			money2 = money2.add(FastMoney.of(EURO, 1234567.3444));
			money2 = money2.subtract(FastMoney.of(EURO, 232323));
			money2 = money2.multiply(3.4);
			money2 = money2.divide(5.456);
//			money2 = money1.with(MonetaryRoundings.getRounding());
		}
		end = System.currentTimeMillis();
		duration = end - start;
		System.out.println("Duration for "+NUM+" operations (IntegralMoney/long): "
				+ duration + " ms (" + ((duration * 1000) / NUM) + " ns per loop) -> "
				+ money2);
		
		FastMoney money3 = FastMoney.of(EURO, BigDecimal.ONE);
		start = System.currentTimeMillis();
		for (int i = 0; i < NUM; i++) {
			money3 = money3.add(Money.of(EURO, 1234567.3444));
			money3 = money3.subtract(FastMoney.of(EURO, 232323));
			money3 = money3.multiply(3.4);
			money3 = money3.divide(5.456);
//			money3 = money3.with(MonetaryRoundings.getRounding());
		}
		end = System.currentTimeMillis();
		duration = end - start;
		System.out.println("Duration for "+NUM+" operations (IntegralMoney/Money mixed): "
				+ duration + " ms (" + ((duration * 1000) / NUM) + " ns per loop) -> "
				+ money3);
	}

	/**
	 * Test method for {@link javax.money.Money#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#getDefaultMathContext()}.
	 */
	@Test
	public void testGetDefaultMathContext() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#of(javax.money.CurrencyUnit, java.math.BigDecimal, java.math.MathContext)}
	 * .
	 */
	@Test
	public void testOfCurrencyUnitBigDecimalMathContext() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#of(javax.money.CurrencyUnit, java.lang.Number)}.
	 */
	@Test
	public void testOfCurrencyUnitNumber() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#of(javax.money.CurrencyUnit, java.lang.Number, java.math.MathContext)}
	 * .
	 */
	@Test
	public void testOfCurrencyUnitNumberMathContext() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#of(java.lang.String, java.lang.Number)}.
	 */
	@Test
	public void testOfStringNumber() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#of(java.lang.String, java.lang.Number, java.math.MathContext)}
	 * .
	 */
	@Test
	public void testOfStringNumberMathContext() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#ofZero(javax.money.CurrencyUnit)}.
	 */
	@Test
	public void testOfZeroCurrencyUnit() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#ofZero(java.lang.String)}.
	 */
	@Test
	public void testOfZeroString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#compareTo(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testCompareTo() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#getMathContext()}.
	 */
	@Test
	public void testGetMathContext() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#withMathContext(java.math.MathContext)}.
	 */
	@Test
	public void testWithMathContext() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#abs()}.
	 */
	@Test
	public void testAbs() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#add(javax.money.MonetaryAmount)}
	 * .
	 */
	@Test
	public void testAdd() {
		Money money1 = Money.of(EURO, BigDecimal.TEN);
		Money money2 = Money.of(EURO, BigDecimal.ONE);
		Money moneyResult = money1.add(money2);
		assertNotNull(moneyResult);
		assertEquals(11d, moneyResult.doubleValue(), 0d);
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#divide(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testDivideMonetaryAmount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#divide(java.lang.Number)}.
	 */
	@Test
	public void testDivideNumber() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#divideAndRemainder(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testDivideAndRemainderMonetaryAmount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#divideAndRemainder(java.lang.Number)}.
	 */
	@Test
	public void testDivideAndRemainderNumber() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#divideToIntegralValue(javax.money.MonetaryAmount)}
	 * .
	 */
	@Test
	public void testDivideToIntegralValueMonetaryAmount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#divideToIntegralValue(java.lang.Number)}.
	 */
	@Test
	public void testDivideToIntegralValueNumber() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#multiply(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testMultiplyMonetaryAmount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#multiply(java.lang.Number)}.
	 */
	@Test
	public void testMultiplyNumber() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#negate()}.
	 */
	@Test
	public void testNegate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#plus()}.
	 */
	@Test
	public void testPlus() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#subtract(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testSubtract() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#pow(int)}.
	 */
	@Test
	public void testPow() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#ulp()}.
	 */
	@Test
	public void testUlp() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#remainder(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testRemainderMonetaryAmount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#remainder(java.lang.Number)}.
	 */
	@Test
	public void testRemainderNumber() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#scaleByPowerOfTen(int)}.
	 */
	@Test
	public void testScaleByPowerOfTen() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#isZero()}.
	 */
	@Test
	public void testIsZero() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#isPositive()}.
	 */
	@Test
	public void testIsPositive() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#isPositiveOrZero()}.
	 */
	@Test
	public void testIsPositiveOrZero() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#isNegative()}.
	 */
	@Test
	public void testIsNegative() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#isNegativeOrZero()}.
	 */
	@Test
	public void testIsNegativeOrZero() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#with(java.lang.Number)}.
	 */
	@Test
	public void testWithNumber() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#with(javax.money.CurrencyUnit, java.lang.Number)}
	 * .
	 */
	@Test
	public void testWithCurrencyUnitNumber() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#getScale()}.
	 */
	@Test
	public void testGetScale() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#getPrecision()}.
	 */
	@Test
	public void testGetPrecision() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#longValue()}.
	 */
	@Test
	public void testLongValue() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#longValueExact()}.
	 */
	@Test
	public void testLongValueExact() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#doubleValue()}.
	 */
	@Test
	public void testDoubleValue() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#signum()}.
	 */
	@Test
	public void testSignum() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#toEngineeringString()}.
	 */
	@Test
	public void testToEngineeringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#toPlainString()}.
	 */
	@Test
	public void testToPlainString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isLessThan(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testIsLessThan() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isLessThanOrEqualTo(javax.money.MonetaryAmount)}
	 * .
	 */
	@Test
	public void testIsLessThanOrEqualTo() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isGreaterThan(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testIsGreaterThan() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isGreaterThanOrEqualTo(javax.money.MonetaryAmount)}
	 * .
	 */
	@Test
	public void testIsGreaterThanOrEqualTo() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isEqualTo(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testIsEqualTo() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isNotEqualTo(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testIsNotEqualTo() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#getNumberType()}.
	 */
	@Test
	public void testGetNumberType() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#query(javax.money.MonetaryQuery)}.
	 */
	@Test
	public void testQuery() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#asType(java.lang.Class)}.
	 */
	@Test
	public void testAsTypeClassOfT() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#asType(java.lang.Class, javax.money.MonetaryAdjuster)}
	 * .
	 */
	@Test
	public void testAsTypeClassOfTMonetaryAdjuster() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#asNumber()}.
	 */
	@Test
	public void testAsNumber() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#getAmountWhole()}.
	 */
	@Test
	public void testGetAmountWhole() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#getAmountFractionNumerator()}.
	 */
	@Test
	public void testGetAmountFractionNumerator() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link javax.money.Money#getAmountFractionDenominator()}.
	 */
	@Test
	public void testGetAmountFractionDenominator() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#with(javax.money.MonetaryAdjuster)}.
	 */
	@Test
	public void testWithMonetaryAdjuster() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#from(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testFrom() {
		fail("Not yet implemented");
	}

}
