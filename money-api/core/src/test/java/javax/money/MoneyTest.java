/**
 * 
 */
package javax.money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.Test;

/**
 * @author Anatole
 * 
 */
public class MoneyTest {

	/**
	 * Test method for {@link javax.money.Money#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		Money m1 = Money.of("CHF", 10);
		Money m2 = Money.of("CHF", 10);
		Money m3 = Money.of("CHF", 11);
		assertEquals(m1.hashCode(), m2.hashCode());
		assertNotSame(m2.hashCode(), m3.hashCode());
		m1 = Money.of("CHF", 10);
		m2 = Money.of("CHF", 10);
		m3 = Money.of("USD", 10);
		assertEquals(m1.hashCode(), m2.hashCode());
		assertNotSame(m2.hashCode(), m3.hashCode());
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#Money(javax.money.CurrencyUnit, java.lang.Number)}
	 * .
	 */
	@Test
	public void testMoneyCurrencyUnitNumber() {
		CurrencyUnit curr = MoneyCurrency.of("CHF");
		new Money(curr, 100);
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#Money(javax.money.CurrencyUnit, java.lang.Number, java.math.MathContext)}
	 * .
	 */
	@Test
	public void testMoneyCurrencyUnitNumberMathContext() {
		CurrencyUnit curr = MoneyCurrency.of("CHF");
		new Money(curr, 100, MathContext.DECIMAL32);
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#of(javax.money.CurrencyUnit, java.math.BigDecimal)}
	 * .
	 */
	@Test
	public void testOfCurrencyUnitBigDecimal() {
		CurrencyUnit curr = MoneyCurrency.of("CHF");
		MonetaryAmount m = Money.of(curr, BigDecimal.ONE);
		assertNotNull(m);
		assertEquals(curr, m.getCurrency());
		assertEquals(BigDecimal.ONE, m.asType(BigDecimal.class));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#of(javax.money.CurrencyUnit, java.lang.Number)}.
	 */
	@Test
	public void testOfCurrencyUnitNumber() {
		CurrencyUnit curr = MoneyCurrency.of("CHF");
		MonetaryAmount m = Money.of(curr, 1);
		assertNotNull(m);
		assertEquals(curr, m.getCurrency());
		assertEquals(BigDecimal.ONE.intValue(), m.asType(BigDecimal.class)
				.intValue());
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#of(javax.money.CurrencyUnit, java.lang.Number, java.math.MathContext)}
	 * .
	 */
	@Test
	public void testOfCurrencyUnitNumberMathContext() {
		CurrencyUnit curr = MoneyCurrency.of("CHF");
		MonetaryAmount m = Money.of(curr, 100, MathContext.DECIMAL32);
		assertNotNull(m);
		assertEquals(curr, m.getCurrency());
		assertEquals(100, m.intValue());
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#of(java.lang.String, java.lang.Number)}.
	 */
	@Test
	public void testOfStringNumber() {
		CurrencyUnit curr = MoneyCurrency.of("CHF");
		MonetaryAmount m = Money.of("CHF", 100);
		assertNotNull(m);
		assertEquals(curr, m.getCurrency());
		assertEquals(100, m.intValue());
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#of(java.lang.String, java.lang.Number, java.math.MathContext)}
	 * .
	 */
	@Test
	public void testOfStringNumberMathContext() {
		CurrencyUnit curr = MoneyCurrency.of("CHF");
		MonetaryAmount m = Money.of("CHF", 100, MathContext.DECIMAL32);
		assertNotNull(m);
		assertEquals(curr, m.getCurrency());
		assertEquals(100, m.intValue());
	}

	/**
	 * Test method for {@link javax.money.Money#zero(javax.money.CurrencyUnit)}.
	 */
	@Test
	public void testZeroCurrencyUnit() {
		CurrencyUnit curr = MoneyCurrency.of("CHF");
		MonetaryAmount m = Money.zero(curr);
		assertNotNull(m);
		assertEquals(curr, m.getCurrency());
		assertTrue(0.0d == m.doubleValue());
	}

	/**
	 * Test method for {@link javax.money.Money#zero(javax.money.CurrencyUnit)}.
	 */
	@Test
	public void testZeroString() {
		CurrencyUnit curr = MoneyCurrency.of("CHF");
		MonetaryAmount m = Money.zero("CHF");
		assertNotNull(m);
		assertEquals(curr, m.getCurrency());
		assertTrue(0.0d == m.doubleValue());
	}

	/**
	 * Test method for {@link javax.money.Money#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		Money m1 = Money.of("CHF", 10);
		Money m2 = Money.of("CHF", 10);
		Money m3 = Money.of("CHF", 11);
		assertEquals(m1, m2);
		assertNotSame(m2, m3);
		m1 = Money.of("CHF", 10);
		m2 = Money.of("CHF", 10);
		m3 = Money.of("USD", 10);
		assertEquals(m1, m2);
		assertNotSame(m2, m3);
		assertFalse(m1.equals(null));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#compareTo(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testCompareTo() {
		Money m1 = Money.of("CHF", 10);
		Money m2 = Money.of("CHF", 10);
		Money m3 = Money.of("CHF", 11);
		assertTrue(m1.compareTo(m2) == 0);
		assertTrue(m2.compareTo(m1) == 0);
		assertTrue(m1.compareTo(m1) == 0);
		assertTrue(m2.compareTo(m2) == 0);
		assertTrue(m1.compareTo(m3) < 0);
		assertTrue(m3.compareTo(m1) > 0);
		m1 = Money.of("CHF", 10);
		m2 = Money.of("CHF", 10);
		m3 = Money.of("USD", 10);
		assertTrue(m1.compareTo(m2) == 0);
		assertTrue(m2.compareTo(m1) == 0);
		assertTrue(m1.compareTo(m1) == 0);
		assertTrue(m2.compareTo(m2) == 0);
		assertTrue(m1.compareTo(m3) < 0);
		assertTrue(m3.compareTo(m1) > 0);
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#compareTo(javax.money.MonetaryAmount)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompareTo_Null() {
		Money m1 = Money.of("CHF", 10);
		m1.compareTo(null);
	}

	/**
	 * Test method for {@link javax.money.Money#getCurrency()}.
	 */
	@Test
	public void testGetCurrency() {
		CurrencyUnit curr = MoneyCurrency.of("CHF");
		Money m = Money.of(curr, 100);
		assertTrue(m.getCurrency() == curr);
	}

	/**
	 * Test method for {@link javax.money.Money#abs()}.
	 */
	@Test
	public void testAbs() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount abs = m.abs();
		assertEquals(dec.abs(), abs.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#min(javax.money.MonetaryAmount)}
	 * .
	 */
	@Test
	public void testMin() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.min(Money.of("CHF", dec2));
		assertEquals(dec.min(dec2), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#max(javax.money.MonetaryAmount)}
	 * .
	 */
	@Test
	public void testMax() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.max(Money.of("CHF", dec2));
		assertEquals(dec.max(dec2), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#add(javax.money.MonetaryAmount)}
	 * .
	 */
	@Test
	public void testAddMonetaryAmount() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.add(Money.of("CHF", dec2));
		assertEquals(dec.add(dec2), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#add(java.lang.Number)}.
	 */
	@Test
	public void testAddNumber() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.add(dec2);
		assertEquals(dec.add(dec2), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#divide(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testDivideMonetaryAmount() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("2");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.divide(Money.of("CHF", dec2));
		assertEquals(dec.divide(dec2), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#divide(java.lang.Number)}.
	 */
	@Test
	public void testDivideNumber() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("5");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.divide(dec2);
		assertEquals(dec.divide(dec2), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#divideAndRemainder(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testDivideAndRemainderMonetaryAmount() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount[] res = m.divideAndRemainder(Money.of("CHF", dec2));
		BigDecimal[] res2 = dec.divideAndRemainder(dec2);
		assertEquals(res2[0], res[0].asType(BigDecimal.class));
		assertEquals(res2[1], res[1].asType(BigDecimal.class));
		assertEquals("CHF", res[0].getCurrency().getCurrencyCode());
		assertEquals("CHF", res[1].getCurrency().getCurrencyCode());
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#divideAndRemainder(java.lang.Number)}.
	 */
	@Test
	public void testDivideAndRemainderNumber() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount[] res = m.divideAndRemainder(dec2);
		BigDecimal[] res2 = dec.divideAndRemainder(dec2);
		assertEquals(res2[0], res[0].asType(BigDecimal.class));
		assertEquals(res2[1], res[1].asType(BigDecimal.class));
		assertEquals("CHF", res[0].getCurrency().getCurrencyCode());
		assertEquals("CHF", res[1].getCurrency().getCurrencyCode());
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#divideToIntegralValue(javax.money.MonetaryAmount)}
	 * .
	 */
	@Test
	public void testDivideToIntegralValueMonetaryAmount() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.divideToIntegralValue(Money.of("CHF", dec2));
		assertEquals(dec.divideToIntegralValue(dec2),
				res.asType(BigDecimal.class));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#divideToIntegralValue(java.lang.Number)}.
	 */
	@Test
	public void testDivideToIntegralValueNumber() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.divideToIntegralValue(dec2);
		assertEquals(dec.divideToIntegralValue(dec2),
				res.asType(BigDecimal.class));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#multiply(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testMultiplyMonetaryAmount() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.multiply(Money.of("CHF", dec2));
		assertEquals(dec.multiply(dec2), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#multiply(java.lang.Number)}.
	 */
	@Test
	public void testMultiplyNumber() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.multiply(dec2);
		assertEquals(dec.multiply(dec2), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#negate()}.
	 */
	@Test
	public void testNegate() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.negate();
		assertEquals(dec.negate(), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#plus()}.
	 */
	@Test
	public void testPlus() {
		BigDecimal dec = new BigDecimal("-135135.151757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.plus();
		assertEquals(dec.plus(), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#subtract(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testSubtractMonetaryAmount() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.subtract(Money.of("CHF", dec2));
		assertEquals(dec.subtract(dec2), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#subtract(java.lang.Number)}.
	 */
	@Test
	public void testSubtractNumber() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.subtract(dec2);
		assertEquals(dec.subtract(dec2), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#pow(int)}.
	 */
	@Test
	public void testPow() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.pow(15);
		assertEquals(dec.pow(15), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#ulp()}.
	 */
	@Test
	public void testUlp() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.ulp();
		assertEquals(dec.ulp(), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#remainder(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testRemainderMonetaryAmount() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.remainder(Money.of("CHF", dec2));
		assertEquals(dec.remainder(dec2), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#remainder(java.lang.Number)}.
	 */
	@Test
	public void testRemainderNumber() {
		BigDecimal dec = new BigDecimal("135135.151757");
		BigDecimal dec2 = new BigDecimal("1335.1518798797757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.remainder(dec2);
		assertEquals(dec.remainder(dec2), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#scaleByPowerOfTen(int)}.
	 */
	@Test
	public void testScaleByPowerOfTen() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		MonetaryAmount res = m.scaleByPowerOfTen(4);
		assertEquals(dec.scaleByPowerOfTen(4), res.asType(BigDecimal.class));
	}

	/**
	 * Test method for {@link javax.money.Money#getMajorLong()}.
	 */
	@Test
	public void testGetMajorLong() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		assertEquals(dec.setScale(0, RoundingMode.DOWN).longValueExact(),
				m.getMajorLong());
	}

	/**
	 * Test method for {@link javax.money.Money#getMajorInt()}.
	 */
	@Test
	public void testGetMajorInt() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		assertEquals(dec.setScale(0, RoundingMode.DOWN).intValueExact(),
				m.getMajorInt());
	}

	/**
	 * Test method for {@link javax.money.Money#getMinorLong()}.
	 */
	@Test
	public void testGetMinorLong() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		assertEquals(dec.movePointRight(dec.precision()).longValueExact(),
				m.getMinorLong());
	}

	/**
	 * Test method for {@link javax.money.Money#getMinorInt()}.
	 */
	@Test
	public void testGetMinorInt() {
		BigDecimal dec = new BigDecimal("1234.12");
		Money m = Money.of("CHF", dec);
		assertEquals(dec.movePointRight(dec.precision())
				.intValueExact(),
				m.getMinorInt());
	}

	/**
	 * Test method for {@link javax.money.Money#isZero()}.
	 */
	@Test
	public void testIsZero() {
		assertTrue(Money.of("CHF", 0).isZero());
		assertTrue(Money.of("CHF", 0.0).isZero());
		assertTrue(Money.of("CHF", 0.0d).isZero());
		assertTrue(Money.of("CHF", -0.0d).isZero());
		assertTrue(Money.of("CHF", 0L).isZero());
		assertTrue(Money.of("CHF", (byte) 0).isZero());
		assertTrue(Money.of("CHF", (short) 0).isZero());
		assertTrue(Money.of("CHF", BigDecimal.ZERO).isZero());
	}

	/**
	 * Test method for {@link javax.money.Money#isPositive()}.
	 */
	@Test
	public void testIsPositive() {
		assertFalse(Money.of("CHF", -1).isPositive());
		assertFalse(Money.of("CHF", BigDecimal.ZERO).isPositive());
		assertFalse(Money.of("CHF", 0.0d).isPositive());
		assertFalse(Money.of("CHF", -0.0d).isPositive());
		assertTrue(Money.of("CHF", BigDecimal.ONE).isPositive());
	}

	/**
	 * Test method for {@link javax.money.Money#isPositiveOrZero()}.
	 */
	@Test
	public void testIsPositiveOrZero() {
		assertFalse(Money.of("CHF", -1).isPositiveOrZero());
		assertTrue(Money.of("CHF", BigDecimal.ZERO).isPositiveOrZero());
		assertTrue(Money.of("CHF", 0.0d).isPositiveOrZero());
		assertTrue(Money.of("CHF", -0.0d).isPositiveOrZero());
		assertTrue(Money.of("CHF", BigDecimal.ONE).isPositiveOrZero());
	}

	/**
	 * Test method for {@link javax.money.Money#isNegative()}.
	 */
	@Test
	public void testIsNegative() {
		assertTrue(Money.of("CHF", -1).isNegative());
		assertFalse(Money.of("CHF", BigDecimal.ZERO).isNegative());
		assertFalse(Money.of("CHF", 0.0d).isNegative());
		assertFalse(Money.of("CHF", -0.0d).isNegative());
		assertFalse(Money.of("CHF", BigDecimal.ONE).isNegative());
	}

	/**
	 * Test method for {@link javax.money.Money#isNegativeOrZero()}.
	 */
	@Test
	public void testIsNegativeOrZero() {
		assertTrue(Money.of("CHF", -1).isNegativeOrZero());
		assertTrue(Money.of("CHF", BigDecimal.ZERO).isNegativeOrZero());
		assertTrue(Money.of("CHF", 0.0d).isNegativeOrZero());
		assertTrue(Money.of("CHF", -0.0d).isNegativeOrZero());
		assertFalse(Money.of("CHF", BigDecimal.ONE).isNegativeOrZero());
	}

	/**
	 * Test method for {@link javax.money.Money#withAmount(java.lang.Number)}.
	 */
	@Test
	public void testWithAmount() {
		MonetaryAmount amt = Money.of("CHF", 10);
		assertTrue(BigDecimal.ONE.doubleValue() == amt.withAmount(1)
				.asType(BigDecimal.class).doubleValue());
	}

	/**
	 * Test method for {@link javax.money.Money#getScale()}.
	 */
	@Test
	public void testGetScale() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		assertEquals(dec.scale(), m.getScale());
		m = Money.of("CHF", 1);
		assertEquals(1, m.getScale());
	}

	/**
	 * Test method for {@link javax.money.Money#getPrecision()}.
	 */
	@Test
	public void testGetPrecision() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		assertEquals(dec.precision(), m.getPrecision());
		m = Money.of("CHF", 1);
		assertEquals(2, m.getPrecision());
	}

	/**
	 * Test method for {@link javax.money.Money#intValue()}.
	 */
	@Test
	public void testIntValue() {
		assertTrue(0 == Money.of("CHF", 0).intValue());
		assertTrue(11 == Money.of("CHF", 11).intValue());
		assertTrue(11 == Money.of("CHF", 11.6).intValue());
		assertTrue(11 == Money.of("CHF", BigDecimal.valueOf(11)).intValue());
		assertTrue(11 == Money.of("CHF", new BigDecimal("11.3765375375"))
				.intValue());
	}

	/**
	 * Test method for {@link javax.money.Money#intValueExact()}.
	 */
	@Test
	public void testIntValueExact() {
		assertTrue(0 == Money.of("CHF", 0.0).intValueExact());
		assertTrue(11 == Money.of("CHF", 11).intValueExact());
	}

	/**
	 * Test method for {@link javax.money.Money#intValueExact()}.
	 */
	@Test(expected = ArithmeticException.class)
	public void testIntValueExact_BadCase() {
		assertTrue(0 == Money.of("CHF", 0.65).intValueExact());
	}

	/**
	 * Test method for {@link javax.money.Money#longValue()}.
	 */
	@Test
	public void testLongValue() {
		assertTrue(0 == Money.of("CHF", 0.0).longValue());
		assertTrue(11 == Money.of("CHF", 11).longValue());
	}

	/**
	 * Test method for {@link javax.money.Money#longValueExact()}.
	 */
	@Test
	public void testLongValueExact() {
		assertTrue(0 == Money.of("CHF", 0.0).longValueExact());
		assertTrue(11 == Money.of("CHF", 11).longValueExact());
	}

	/**
	 * Test method for {@link javax.money.Money#floatValue()}.
	 */
	@Test
	public void testFloatValue() {
		assertTrue((float) 0.5678 == Money.of("CHF", 0.5678).floatValue());
		assertTrue(6.0 == Money.of("CHF", 6).floatValue());
		assertTrue(6.0 == Money.of("CHF", 6.0d).floatValue());
	}

	/**
	 * Test method for {@link javax.money.Money#doubleValue()}.
	 */
	@Test
	public void testDoubleValue() {
		assertTrue(0.5678d == Money.of("CHF", 0.5678).doubleValue());
		assertTrue(6.0d == Money.of("CHF", 6).doubleValue());
		assertTrue(6.0d == Money.of("CHF", 6.0d).doubleValue());
	}

	/**
	 * Test method for {@link javax.money.Money#byteValue()}.
	 */
	@Test
	public void testByteValue() {
		assertTrue(0 == Money.of("CHF", 0.5678).byteValue());
		assertTrue(6 == Money.of("CHF", 6).byteValue());
		assertTrue(6 == Money.of("CHF", 6.0d).byteValue());
	}

	/**
	 * Test method for {@link javax.money.Money#shortValue()}.
	 */
	@Test
	public void testShortValue() {
		assertTrue(0 == Money.of("CHF", 0.5678).shortValue());
		assertTrue(6 == Money.of("CHF", 6).shortValue());
		assertTrue(6 == Money.of("CHF", 6.0d).shortValue());
	}

	/**
	 * Test method for {@link javax.money.Money#shortValueExact()}.
	 */
	@Test
	public void testShortValueExact() {
		assertTrue(0 == Money.of("CHF", 0.0).shortValueExact());
		assertTrue(6 == Money.of("CHF", 6).shortValueExact());
		assertTrue(6 == Money.of("CHF", 6.0d).shortValueExact());
	}

	/**
	 * Test method for {@link javax.money.Money#signum()}.
	 */
	@Test
	public void testSignum() {
		assertEquals(-1, Money.of("CHF", -1).signum());
		assertEquals(0, Money.of("CHF", -0.0).signum());
		assertEquals(0, Money.of("CHF", 0.0).signum());
		assertEquals(1, Money.of("CHF", 1).signum());
	}

	/**
	 * Test method for {@link javax.money.Money#toEngineeringString()}.
	 */
	@Test
	public void testToEngineeringString() {
		BigDecimal bd = BigDecimal.valueOf(15716876876.76876876d);
		assertEquals("CHF " + bd.toEngineeringString(),
				Money.of("CHF", 15716876876.76876876d).toEngineeringString());
	}

	/**
	 * Test method for {@link javax.money.Money#toPlainString()}.
	 */
	@Test
	public void testToPlainString() {
		BigDecimal bd = BigDecimal.valueOf(15716876876.76876876d);
		assertEquals("CHF " + bd.toPlainString(),
				Money.of("CHF", 15716876876.76876876d).toPlainString());
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isLessThan(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testIsLessThanMonetaryAmount() {
		Money m1 = Money.of("CHF", 5);
		Money m2 = Money.of("CHF", 10);
		assertTrue(m1.isLessThan(m2));
		assertFalse(m1.isLessThan(m1));
		assertFalse(m2.isLessThan(m1));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isLessThan(javax.money.MonetaryAmount)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsLessThanMonetaryAmount_null() {
		Money m1 = Money.of("CHF", 5);
		assertFalse(m1.isLessThan((Money) null));
	}

	/**
	 * Test method for {@link javax.money.Money#isLessThan(java.lang.Number)}.
	 */
	@Test
	public void testIsLessThanNumber() {
		Money m1 = Money.of("CHF", 5);
		assertTrue(m1.isLessThan(10));
		assertFalse(m1.isLessThan(5));
	}

	/**
	 * Test method for {@link javax.money.Money#isLessThan(java.lang.Number)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsLessThanNumber_null() {
		Money m1 = Money.of("CHF", 5);
		assertFalse(m1.isLessThan((Number) null));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isLessThanOrEqualTo(javax.money.MonetaryAmount)}
	 * .
	 */
	@Test
	public void testIsLessThanOrEqualToMonetaryAmount() {
		Money m1 = Money.of("CHF", 5);
		Money m2 = Money.of("CHF", 10);
		assertTrue(m1.isLessThanOrEqualTo(m2));
		assertTrue(m1.isLessThanOrEqualTo(m1));
		assertFalse(m2.isLessThanOrEqualTo(m1));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isLessThanOrEqualTo(javax.money.MonetaryAmount)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsLessThanOrEqualToMonetaryAmount_null() {
		Money m1 = Money.of("CHF", 5);
		assertFalse(m1.isLessThanOrEqualTo((Money) null));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isLessThanOrEqualTo(java.lang.Number)}.
	 */
	@Test
	public void testIsLessThanOrEqualToNumber() {
		Money m1 = Money.of("CHF", 5);
		assertTrue(m1.isLessThanOrEqualTo(10));
		assertTrue(m1.isLessThanOrEqualTo(m1));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isLessThanOrEqualTo(java.lang.Number)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsLessThanOrEqualToNumber_Null() {
		Money m1 = Money.of("CHF", 5);
		assertFalse(m1.isLessThanOrEqualTo((Number) null));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isGreaterThan(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testIsGreaterThanMonetaryAmount() {
		Money m1 = Money.of("CHF", 5);
		Money m2 = Money.of("CHF", 10);
		assertFalse(m1.isGreaterThan(m2));
		assertFalse(m1.isGreaterThan(m1));
		assertTrue(m2.isGreaterThan(m1));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isGreaterThan(javax.money.MonetaryAmount)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsGreaterThanMonetaryAmount_null() {
		Money m1 = Money.of("CHF", 5);
		assertFalse(m1.isGreaterThan((Money) null));
	}

	/**
	 * Test method for {@link javax.money.Money#isGreaterThan(java.lang.Number)}
	 * .
	 */
	@Test
	public void testIsGreaterThanNumber() {
		Money m1 = Money.of("CHF", 5);
		assertFalse(m1.isGreaterThan(10));
		assertFalse(m1.isGreaterThan(m1));
	}

	/**
	 * Test method for {@link javax.money.Money#isGreaterThan(java.lang.Number)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsGreaterThanNumber_Null() {
		Money m1 = Money.of("CHF", 5);
		assertFalse(m1.isGreaterThan((Money) null));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isGreaterThanOrEqualTo(javax.money.MonetaryAmount)}
	 * .
	 */
	@Test
	public void testIsGreaterThanOrEqualToMonetaryAmount() {
		Money m1 = Money.of("CHF", 5);
		Money m2 = Money.of("CHF", 10);
		assertFalse(m1.isGreaterThanOrEqualTo(m2));
		assertTrue(m1.isGreaterThanOrEqualTo(m1));
		assertTrue(m2.isGreaterThanOrEqualTo(m1));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isGreaterThanOrEqualTo(javax.money.MonetaryAmount)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsGreaterThanOrEqualToMonetaryAmount_null() {
		Money m1 = Money.of("CHF", 5);
		assertFalse(m1.isGreaterThanOrEqualTo((Money) null));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isGreaterThanOrEqualTo(java.lang.Number)}.
	 */
	@Test
	public void testIsGreaterThanOrEqualToNumber() {
		Money m1 = Money.of("CHF", 5);
		assertFalse(m1.isGreaterThanOrEqualTo(10));
		assertTrue(m1.isGreaterThanOrEqualTo(5));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isGreaterThanOrEqualTo(java.lang.Number)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsGreaterThanOrEqualToNumber_Null() {
		Money m1 = Money.of("CHF", 5);
		m1.isGreaterThanOrEqualTo((Number) null);
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isEqualTo(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testIsEqualTo() {
		assertFalse(Money.of("CHF", 5).isEqualTo(Money.of("CHF", 10)));
		assertTrue(Money.of("CHF", 5).isEqualTo(Money.of("CHF", 5)));
		assertTrue(Money.of("CHF", 5).isEqualTo(Money.of("CHF", 5.00d)));
		assertTrue(Money.of("CHF", BigDecimal.ONE)
				.isEqualTo(Money.of("CHF", 1)));
		assertFalse(Money.of("CHF", BigDecimal.ONE).isEqualTo(
				Money.of("CHF", 1.1)));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#hasSameNumberAs(java.lang.Number)}.
	 */
	@Test
	public void testHasSameNumberAs() {
		assertFalse(Money.of("CHF", 5).hasSameNumberAs(10));
		assertTrue(Money.of("CHF", 5).hasSameNumberAs(5));
		assertTrue(Money.of("CHF", 5).hasSameNumberAs(5.00d));
		assertTrue(Money.of("CHF", BigDecimal.ONE).hasSameNumberAs(1));
		assertFalse(Money.of("CHF", BigDecimal.ONE).hasSameNumberAs(1.1));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#isNotEqualTo(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testIsNotEqualToMonetaryAmount() {
		assertTrue(Money.of("CHF", 5).isNotEqualTo(Money.of("CHF", 10)));
		assertFalse(Money.of("CHF", 5).isNotEqualTo(Money.of("CHF", 5)));
		assertFalse(Money.of("CHF", 5).isNotEqualTo(Money.of("CHF", 5.00d)));
		assertFalse(Money.of("CHF", BigDecimal.ONE).isNotEqualTo(
				Money.of("CHF", 1)));
		assertTrue(Money.of("CHF", BigDecimal.ONE).isNotEqualTo(
				Money.of("CHF", 1.1)));
	}

	/**
	 * Test method for {@link javax.money.Money#isNotEqualTo(java.lang.Number)}.
	 */
	@Test
	public void testIsNotEqualToNumber() {
		assertTrue(Money.of("CHF", 5).isNotEqualTo(10));
		assertFalse(Money.of("CHF", 5).isNotEqualTo(5));
		assertFalse(Money.of("CHF", 5).isNotEqualTo(5.00d));
		assertFalse(Money.of("CHF", BigDecimal.ONE).isNotEqualTo(1));
		assertTrue(Money.of("CHF", BigDecimal.ONE).isNotEqualTo(1.1));
	}

	/**
	 * Test method for {@link javax.money.Money#getMajorPart()}.
	 */
	@Test
	public void testGetMajorPart() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		assertEquals(Money.of("CHF", m.getMajorLong()), m.getMajorPart());
	}

	/**
	 * Test method for {@link javax.money.Money#getMinorPart()}.
	 */
	@Test
	public void testGetMinorPart() {
		BigDecimal dec = new BigDecimal("135135.151757");
		Money m = Money.of("CHF", dec);
		assertEquals(Money.of("CHF", m.getMinorLong()), m.getMinorPart());
	}

	/**
	 * Test method for {@link javax.money.Money#getNumberType()}.
	 */
	@Test
	public void testGetNumberType() {
		assertTrue(BigDecimal.class == Money.of("CHF", 188).getNumberType());
	}

	/**
	 * Test method for {@link javax.money.Money#asType(java.lang.Class)}.
	 */
	@Test
	public void testAsTypeClassOfT() {
		Money m = Money.of("CHF", 1);
		assertEquals(Byte.valueOf((byte) 1), m.asType(Byte.class));
		assertEquals(Short.valueOf((byte) 1), m.asType(Short.class));
		assertEquals(Float.valueOf((byte) 1), m.asType(Float.class));
		assertEquals(Double.valueOf((byte) 1), m.asType(Double.class));
		assertEquals(Integer.valueOf((byte) 1), m.asType(Integer.class));
		assertEquals(Long.valueOf((byte) 1), m.asType(Long.class));
		assertEquals(BigDecimal.ONE.intValue(), m.asType(BigDecimal.class)
				.intValue());
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#asType(java.lang.Class, javax.money.Rounding)}.
	 */
	@Test
	public void testAsTypeClassOfTRounding() {
		Rounding rounding = new Rounding() {

			@Override
			public MonetaryAmount round(MonetaryAmount amount) {
				return Money.of(amount.getCurrency(), amount.doubleValue() * 2);
			}

		};
		assertEquals(Long.valueOf(2L),
				Money.of("CHF", 1).asType(Long.class, rounding));
		assertEquals(Integer.valueOf(2),
				Money.of("CHF", 1).asType(Integer.class, rounding));
		assertEquals(Short.valueOf((short) 2),
				Money.of("CHF", 1).asType(Short.class, rounding));
		assertEquals(Double.valueOf(1.6d),
				Money.of("CHF", 0.8).asType(Double.class, rounding));
		assertEquals(Float.valueOf((float) 1.6),
				Money.of("CHF", 0.8).asType(Float.class, rounding));
		assertTrue(BigDecimal.ONE.doubleValue() == Money.of("CHF", 0.5)
				.asType(BigDecimal.class, rounding).doubleValue());
	}

	/**
	 * Test method for {@link javax.money.Money#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals("CHF 12345.1234567", Money.of("CHF", 12345.1234567d)
				.toString());
		assertEquals("CHF -12345.1234567", Money.of("CHF", -12345.1234567d)
				.toString());
		assertEquals("CHF 0.0", Money.of("CHF", -0.0).toString());
		assertEquals("CHF 0.0", Money.of("CHF", -0.0).toString());
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#hasSameCurrencyAs(javax.money.MonetaryAmount)}.
	 */
	@Test
	public void testHasSameCurrencyAs() {
		assertTrue(Money.of("CHF", 0).hasSameCurrencyAs(Money.of("CHF", 45)));
		assertFalse(Money.of("CHF", 0).hasSameCurrencyAs(Money.of("EUR", 45)));
	}

	/**
	 * Test method for
	 * {@link javax.money.Money#setMathContext(java.math.MathContext)}.
	 */
	@Test
	public void testSetMathContext() {
		Money.of("CHF", 0).setMathContext(MathContext.DECIMAL128);
	}

}
