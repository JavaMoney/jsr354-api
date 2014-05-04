/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2014, Credit Suisse All rights
 * reserved.
 */
package javax.money.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.money.CurrencyUnit;
import javax.money.TestCurrency;
import javax.money.convert.ConversionContext;
import javax.money.convert.ExchangeRate;
import javax.money.convert.RateType;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 *
 */
public class ExchangeRate_BuilderTest {

	@Test
	public void testWithConversionContext() {
		ExchangeRate.Builder b = new ExchangeRate.Builder("test",  RateType.DEFERRED);
		ExchangeRate.Builder b2 = b.setContext(ConversionContext.of("test",  RateType.DEFERRED));
		assertTrue(b == b2);
		b2 = b.setContext(ConversionContext.of("test2",  RateType.DEFERRED));
		assertTrue(b == b2);
	}

	@Test
	public void testGetSetBase() {
		ExchangeRate.Builder b = new ExchangeRate.Builder("test",  RateType.DEFERRED);
		ExchangeRate.Builder b2 = b.setBase(TestCurrency.of("CHF"));
		assertTrue(b == b2);
	}

	@Test
	public void testGetSetTerm() {
		ExchangeRate.Builder b = new ExchangeRate.Builder("test",  RateType.DEFERRED);
		ExchangeRate.Builder b2 = b.setTerm(TestCurrency.of("CHF"));
		assertTrue(b == b2);
	}

	@Test
	public void testGetSetExchangeRateChain() {
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit baseTerm = TestCurrency.of("EUR");
		CurrencyUnit term = TestCurrency.of("USD");
		ExchangeRate rate1 = new ExchangeRate.Builder("test",  RateType.DEFERRED)
				.setBase(base)
				.setTerm(baseTerm).setFactor(TestNumberValue.of(0.8))
				.build();
		ExchangeRate rate2 = new ExchangeRate.Builder("test",  RateType.DEFERRED)
				.setBase(baseTerm)
				.setTerm(term).setFactor(TestNumberValue.of(1.4))
				.build();
		ExchangeRate.Builder b = new ExchangeRate.Builder("test",  RateType.DEFERRED).setBase(base)
				.setTerm(term)
				.setRateChain(rate1, rate2);
		ExchangeRate rate = b.setFactor(TestNumberValue.of(9))
				.setContext(ConversionContext.of("test",  RateType.DEFERRED))
				.build();
		assertEquals(rate.getFactor().numberValue(BigDecimal.class), BigDecimal.valueOf(9));
		assertEquals(rate.getExchangeRateChain(),
				Arrays.asList(new ExchangeRate[] { rate1, rate2 }));
	}

	@Test
	public void testGetSetBaseLeadingFactor() {
		ExchangeRate.Builder b = new ExchangeRate.Builder("test",  RateType.DEFERRED);
		ExchangeRate.Builder b2 = b.setFactor(TestNumberValue.of(Long.MAX_VALUE));
		assertTrue(b == b2);
		b.setFactor(TestNumberValue.of(100L));
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit term = TestCurrency.of("USD");
		ExchangeRate rate = b.setBase(base)
				.setContext(ConversionContext.of("test",  RateType.DEFERRED))
				.setTerm(term).build();
		assertEquals(BigDecimal.valueOf(100L), rate.getFactor().numberValue(BigDecimal.class));
	}

	@Test
	public void testGetSetTermLeadingFactorBigDecimal() {
		ExchangeRate.Builder b = new ExchangeRate.Builder("test",  RateType.DEFERRED);
		ExchangeRate.Builder b2 = b.setFactor(TestNumberValue.of(1.2));
		assertTrue(b == b2);
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit term = TestCurrency.of("USD");
		ExchangeRate rate = b.setBase(base)
				.setContext(ConversionContext.of("test",  RateType.DEFERRED))
				.setTerm(term).build();
		assertEquals(TestNumberValue.of(1.2), rate.getFactor());
	}


	@Test
	public void testBuild() {
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit baseTerm = TestCurrency.of("EUR");
		CurrencyUnit term = TestCurrency.of("USD");
		ExchangeRate rate1 = new ExchangeRate.Builder("test",  RateType.DEFERRED)
				.setContext(ConversionContext.of("test",  RateType.DEFERRED))
				.setBase(base).setTerm(baseTerm)
				.setFactor(TestNumberValue.of(0.8)).build();
		ExchangeRate rate2 = new ExchangeRate.Builder("test",  RateType.DEFERRED)
				.setContext(ConversionContext.of("test",  RateType.DEFERRED))
				.setBase(baseTerm).setTerm(term)
				.setFactor(TestNumberValue.of(1.4)).build();

		ExchangeRate.Builder b = new ExchangeRate.Builder("test",  RateType.DEFERRED);
		b.setContext(ConversionContext.of("bla",  RateType.DEFERRED));
		b.setBase(base);
		b.setTerm(term);
		b.setFactor(TestNumberValue.of(2.2));
		ExchangeRate rate = b.build();
		assertEquals(rate.getConversionContext(), ConversionContext.of("bla",  RateType.DEFERRED));
		assertEquals(base, rate.getBase());
		assertEquals(term, rate.getTerm());
		assertEquals(BigDecimal.valueOf(2.2d), rate.getFactor().numberValue(BigDecimal.class));

		b = new ExchangeRate.Builder("test",  RateType.DEFERRED);
		b.setBase(TestCurrency.of("CHF"));
		b.setTerm(TestCurrency.of("USD"));
		b.setRateChain(rate1, rate2);
		b.setFactor(TestNumberValue.of(2.0));
		rate = b.build();
		assertEquals(rate.getConversionContext(), ConversionContext.of("test",  RateType.DEFERRED));
		Assert.assertEquals(TestCurrency.of("CHF"), rate.getBase());
		Assert.assertEquals(TestCurrency.of("USD"), rate.getTerm());
		assertEquals(BigDecimal.valueOf(2.0), rate.getFactor().numberValue(BigDecimal.class));
	}
}
