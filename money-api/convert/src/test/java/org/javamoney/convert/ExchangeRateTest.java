package org.javamoney.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import javax.money.CurrencyUnit;

import org.javamoney.TestCurrency;
import org.javamoney.convert.ExchangeRate;
import org.javamoney.convert.ExchangeRateType;
import org.junit.Test;

public class ExchangeRateTest {

	@Test
	public void testExchangeRateExchangeRateTypeCurrencyUnitCurrencyUnitNumberString() {
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit term = TestCurrency.of("EUR");
		ExchangeRate rate = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(base).withTerm(term).withFactor(1.5)
				.withProvider("myProvider").build();
		assertEquals(base, rate.getBase());
		assertEquals(term, rate.getTerm());
		assertTrue(1.5d == rate.getFactor().doubleValue());
		assertEquals(ExchangeRateType.of("test"), rate.getExchangeRateType());
		assertEquals(Arrays.asList(new ExchangeRate[] { rate }),
				rate.getExchangeRateChain());
		assertEquals("myProvider", rate.getProvider());
	}

	@Test
	public void testExchangeRateExchangeRateTypeCurrencyUnitCurrencyUnitNumberStringLongLong() {
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit term = TestCurrency.of("EUR");
		ExchangeRate rate = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(base).withTerm(term).withFactor(1.5)
				.withProvider("myProvider").withValidFromMillis(10L)
				.withValidToMillis(100L).build();
		assertEquals(base, rate.getBase());
		assertEquals(term, rate.getTerm());
		assertTrue(1.5d == rate.getFactor().doubleValue());
		assertEquals(ExchangeRateType.of("test"), rate.getExchangeRateType());
		assertEquals(Arrays.asList(new ExchangeRate[] { rate }),
				rate.getExchangeRateChain());
		assertEquals("myProvider", rate.getProvider());
		assertEquals(10, rate.getValidFromMillis().longValue());
		assertEquals(100, rate.getValidToMillis().longValue());
	}

	@Test
	public void testExchangeRateExchangeRateTypeCurrencyUnitCurrencyUnitNumberStringExchangeRateArray() {
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit baseTerm = TestCurrency.of("EUR");
		CurrencyUnit term = TestCurrency.of("USD");
		ExchangeRate rate1 = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(base).withTerm(baseTerm).withFactor(0.8)
				.withProvider("myProvider").build();
		ExchangeRate rate2 = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(baseTerm).withTerm(term).withFactor(1.4)
				.withProvider("myProvider").build();

		// derived rate
		ExchangeRate rate = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(base).withTerm(term).withFactor(0.8 * 1.4)
				.withExchangeRateChain(rate1, rate2)
				.withProvider("myProvider").build();

		assertEquals(base, rate.getBase());
		assertEquals(term, rate.getTerm());
		assertTrue(0.8d * 1.4d == rate.getFactor().doubleValue());
		assertEquals(ExchangeRateType.of("test"), rate.getExchangeRateType());
		assertEquals(Arrays.asList(new ExchangeRate[] { rate1, rate2 }),
				rate.getExchangeRateChain());
		assertEquals("myProvider", rate.getProvider());
		assertNull(rate.getValidFromMillis());
		assertNull(rate.getValidToMillis());
	}

	@Test
	public void testExchangeRateExchangeRateTypeCurrencyUnitCurrencyUnitNumberStringLongLongExchangeRateArray() {
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit baseTerm = TestCurrency.of("EUR");
		CurrencyUnit term = TestCurrency.of("USD");
		ExchangeRate rate1 = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(base).withTerm(baseTerm).withFactor(0.8)
				.withProvider("myProvider").build();
		ExchangeRate rate2 = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(baseTerm).withTerm(term).withFactor(1.4)
				.withProvider("myProvider").build();

		// derived rate
		ExchangeRate rate = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(base).withTerm(term).withFactor(0.8 * 1.4)
				.withExchangeRateChain(rate1, rate2).withValidFromMillis(10L)
				.withValidToMillis(100L)
				.withProvider("myProvider").build();
		assertEquals(base, rate.getBase());
		assertEquals(term, rate.getTerm());
		assertTrue(0.8d * 1.4d == rate.getFactor().doubleValue());
		assertEquals(ExchangeRateType.of("test"), rate.getExchangeRateType());
		assertEquals(Arrays.asList(new ExchangeRate[] { rate1, rate2 }),
				rate.getExchangeRateChain());
		assertEquals("myProvider", rate.getProvider());
		assertEquals(10, rate.getValidFromMillis().longValue());
		assertEquals(100, rate.getValidToMillis().longValue());
	}

	@Test
	public void testToString() {
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit baseTerm = TestCurrency.of("EUR");
		CurrencyUnit term = TestCurrency.of("USD");
		ExchangeRate rate1 = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(base).withTerm(baseTerm).withFactor(0.8)
				.withProvider("myProvider").build();
		ExchangeRate rate2 = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(baseTerm).withTerm(term).withFactor(1.4)
				.withProvider("myProvider").build();

		// derived rate
		ExchangeRate rate = new ExchangeRate.Builder()
				.withExchangeRateType(ExchangeRateType.of("test"))
				.withBase(base).withTerm(term).withFactor(0.8 * 1.4)
				.withExchangeRateChain(rate1, rate2).withValidFromMillis(10L)
				.withValidToMillis(100L)
				.withProvider("myProvider").build();
		assertEquals(
				"ExchangeRate [type=test, base=CHF, term=USD, factor=1.1199999999999999, validFrom=10, validTo=100, provider=myProvider]",
				rate.toString());
	}

}
