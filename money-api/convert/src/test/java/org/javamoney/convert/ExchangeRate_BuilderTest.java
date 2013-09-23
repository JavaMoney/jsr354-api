package org.javamoney.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import javax.money.CurrencyUnit;

import org.javamoney.TestCurrency;
import org.javamoney.convert.ExchangeRate;
import org.javamoney.convert.ExchangeRateType;
import org.javamoney.convert.ExchangeRate.Builder;
import org.junit.Ignore;
import org.junit.Test;

public class ExchangeRate_BuilderTest extends Builder {

	@Test
	public void testWithExchangeRateType() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		ExchangeRate.Builder b2 = b.withExchangeRateType(ExchangeRateType
				.of("test"));
		assertTrue(b == b2);
		b2 = b.withExchangeRateType(ExchangeRateType.of("test2"));
		assertTrue(b == b2);
	}

	@Test
	public void testGetSetBase() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		ExchangeRate.Builder b2 = b.withBase(TestCurrency.of("CHF"));
		assertTrue(b == b2);
	}

	@Test
	public void testGetSetTerm() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		ExchangeRate.Builder b2 = b.withTerm(TestCurrency.of("CHF"));
		assertTrue(b == b2);
	}

	@Test
	public void testGetSetValidFrom() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		ExchangeRate.Builder b2 = b.withValidFromMillis(100L);
		assertTrue(b == b2);
	}

	@Test
	public void testGetSetValidUntil() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		ExchangeRate.Builder b2 = b.withValidToMillis(100L);
		assertTrue(b == b2);
	}

	@Test
	public void testGetSetExchangeRateChain() {
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit baseTerm = TestCurrency.of("EUR");
		CurrencyUnit term = TestCurrency.of("USD");
		ExchangeRate rate1 = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(base)
				.withTerm(baseTerm).withFactor(0.8).withProvider("myProvider")
				.build();
		ExchangeRate rate2 = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(baseTerm)
				.withTerm(term).withFactor(1.4).withProvider("myProvider")
				.build();
		ExchangeRate.Builder b = new ExchangeRate.Builder().withBase(base)
				.withTerm(term)
				.withExchangeRateChain(rate1, rate2);
		ExchangeRate rate = b.withFactor(9)
				.withExchangeRateType(ExchangeRateType.of("test"))
				.build();
		assertEquals(rate.getFactor(), BigDecimal.valueOf(9.0d));
		assertEquals(rate.getExchangeRateChain(),
				Arrays.asList(new ExchangeRate[] { rate1, rate2 }));
	}

	@Test
	public void testGetSetBaseLeadingFactor() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		ExchangeRate.Builder b2 = b.withFactor(Long.MAX_VALUE);
		assertTrue(b == b2);
		b.withFactor(100L);
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit term = TestCurrency.of("USD");
		ExchangeRate rate = b.withBase(base)
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withTerm(term).build();
		assertEquals(BigDecimal.valueOf(100.0), rate.getFactor());
	}

	@Test
	@Ignore
	public void testGetSetTermLeadingFactorBigDecimal() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		ExchangeRate.Builder b2 = b.withFactor(1.2);
		assertTrue(b == b2);
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit term = TestCurrency.of("USD");
		ExchangeRate rate = b.withBase(base)
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withTerm(term).build();
		assertEquals(BigDecimal.ONE.divide(BigDecimal.valueOf(1),
				RoundingMode.HALF_EVEN), rate.getFactor());
	}

	@Test
	public void testGetSetProvider() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		ExchangeRate.Builder b2 = b.withProvider("testProvider");
		assertTrue(b == b2);
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit term = TestCurrency.of("USD");
		ExchangeRate rate = b.withBase(base)
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withTerm(term).withFactor(1).build();
		assertEquals("testProvider", rate.getProvider());
	}

	@Test
	public void testBuild() {
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit baseTerm = TestCurrency.of("EUR");
		CurrencyUnit term = TestCurrency.of("USD");
		ExchangeRate rate1 = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(base).withTerm(baseTerm)
				.withFactor(0.8).withProvider("myProvider").build();
		ExchangeRate rate2 = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(baseTerm).withTerm(term)
				.withFactor(1.4).withProvider("myProvider").build();

		ExchangeRate.Builder b = new ExchangeRate.Builder();
		b.withExchangeRateType(ExchangeRateType.of("bla"));
		b.withBase(base);
		b.withTerm(term);
		b.withFactor(2.2);
		b.withProvider("myProvider");
		ExchangeRate rate = b.build();
		assertEquals(rate.getExchangeRateType(), ExchangeRateType.of("bla"));
		assertEquals(base, rate.getBase());
		assertEquals(term, rate.getTerm());
		assertEquals(BigDecimal.valueOf(2.2d), rate.getFactor());
		assertEquals("myProvider", rate.getProvider());

		b = new ExchangeRate.Builder();
		b.withExchangeRateType(ExchangeRateType.of("test"));
		b.withBase(TestCurrency.of("CHF"));
		b.withTerm(TestCurrency.of("USD"));
		b.withExchangeRateChain(rate1, rate2);
		b.withFactor(2.0);
		rate = b.build();
		assertEquals(rate.getExchangeRateType(), ExchangeRateType.of("test"));
		assertEquals(TestCurrency.of("CHF"), rate.getBase());
		assertEquals(TestCurrency.of("USD"), rate.getTerm());
		assertEquals(BigDecimal.valueOf(2.0), rate.getFactor());
		assertEquals(null, rate.getProvider());
	}
}
