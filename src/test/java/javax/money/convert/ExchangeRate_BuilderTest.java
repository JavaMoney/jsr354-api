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

import org.testng.Assert;
import org.testng.annotations.Test;

import javax.money.CurrencyUnit;
import javax.money.TestCurrency;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class ExchangeRate_BuilderTest {

    @Test
    public void testWithConversionContext() {
        DefaultExchangeRate.Builder b = new DefaultExchangeRate.Builder("test", RateType.DEFERRED);
        DefaultExchangeRate.Builder b2 = b.setContext(ConversionContext.of("test", RateType.DEFERRED));
        assertTrue(b == b2);
        b2 = b.setContext(ConversionContext.of("test2", RateType.DEFERRED));
        assertTrue(b == b2);
    }

    @Test
    public void testGetSetBase() {
        DefaultExchangeRate.Builder b = new DefaultExchangeRate.Builder("test", RateType.DEFERRED);
        DefaultExchangeRate.Builder b2 = b.setBaseCurrency(TestCurrency.of("CHF"));
        assertTrue(b == b2);
    }

    @Test
    public void testGetSetTerm() {
        DefaultExchangeRate.Builder b = new DefaultExchangeRate.Builder("test", RateType.DEFERRED);
        DefaultExchangeRate.Builder b2 = b.setTermCurrency(TestCurrency.of("CHF"));
        assertTrue(b == b2);
    }

    @Test
    public void testGetSetExchangeRateChain() {
        CurrencyUnit base = TestCurrency.of("CHF");
        CurrencyUnit baseTerm = TestCurrency.of("EUR");
        CurrencyUnit term = TestCurrency.of("USD");
        DefaultExchangeRate rate1 = new DefaultExchangeRate.Builder("test", RateType.DEFERRED).setBaseCurrency(base)
                .setTermCurrency(baseTerm).setFactor(TestNumberValue.of(0.8)).build();
        ExchangeRate rate2 = new DefaultExchangeRate.Builder("test", RateType.DEFERRED).setBaseCurrency(baseTerm)
                .setTermCurrency(term).setFactor(TestNumberValue.of(1.4)).build();
        DefaultExchangeRate.Builder b =
                new DefaultExchangeRate.Builder("test", RateType.DEFERRED).setBaseCurrency(base).setTermCurrency(term)
                        .setRateChain(rate1, rate2);
        ExchangeRate rate =
                b.setFactor(TestNumberValue.of(9)).setContext(ConversionContext.of("test", RateType.DEFERRED)).build();
        assertEquals(rate.getFactor().numberValue(BigDecimal.class), BigDecimal.valueOf(9));
        assertEquals(rate.getExchangeRateChain(), Arrays.asList(rate1, rate2));
    }

    @Test
    public void testGetSetBaseLeadingFactor() {
        DefaultExchangeRate.Builder b = new DefaultExchangeRate.Builder("test", RateType.DEFERRED);
        DefaultExchangeRate.Builder b2 = b.setFactor(TestNumberValue.of(Long.MAX_VALUE));
        assertTrue(b == b2);
        b.setFactor(TestNumberValue.of(100L));
        CurrencyUnit base = TestCurrency.of("CHF");
        CurrencyUnit term = TestCurrency.of("USD");
        ExchangeRate rate = b.setBaseCurrency(base).setContext(ConversionContext.of("test", RateType.DEFERRED))
                .setTermCurrency(term).build();
        assertEquals(BigDecimal.valueOf(100L), rate.getFactor().numberValue(BigDecimal.class));
    }

    @Test
    public void testGetSetTermLeadingFactorBigDecimal() {
        DefaultExchangeRate.Builder b = new DefaultExchangeRate.Builder("test", RateType.DEFERRED);
        DefaultExchangeRate.Builder b2 = b.setFactor(TestNumberValue.of(1.2));
        assertTrue(b == b2);
        CurrencyUnit base = TestCurrency.of("CHF");
        CurrencyUnit term = TestCurrency.of("USD");
        ExchangeRate rate = b.setBaseCurrency(base).setContext(ConversionContext.of("test", RateType.DEFERRED))
                .setTermCurrency(term).build();
        assertEquals(TestNumberValue.of(1.2), rate.getFactor());
    }


    @Test
    public void testBuild() {
        CurrencyUnit base = TestCurrency.of("CHF");
        CurrencyUnit baseTerm = TestCurrency.of("EUR");
        CurrencyUnit term = TestCurrency.of("USD");
        DefaultExchangeRate rate1 = new DefaultExchangeRate.Builder("test", RateType.DEFERRED)
                .setContext(ConversionContext.of("test", RateType.DEFERRED)).setBaseCurrency(base)
                .setTermCurrency(baseTerm).setFactor(TestNumberValue.of(0.8)).build();
        DefaultExchangeRate rate2 = new DefaultExchangeRate.Builder("test", RateType.DEFERRED)
                .setContext(ConversionContext.of("test", RateType.DEFERRED)).setBaseCurrency(baseTerm)
                .setTermCurrency(term).setFactor(TestNumberValue.of(1.4)).build();

        DefaultExchangeRate.Builder b = new DefaultExchangeRate.Builder("test", RateType.DEFERRED);
        b.setContext(ConversionContext.of("bla", RateType.DEFERRED));
        b.setBaseCurrency(base);
        b.setTermCurrency(term);
        b.setFactor(TestNumberValue.of(2.2));
        ExchangeRate rate = b.build();
        assertEquals(rate.getContext(), ConversionContext.of("bla", RateType.DEFERRED));
        assertEquals(base, rate.getBaseCurrency());
        assertEquals(term, rate.getCurrency());
        assertEquals(BigDecimal.valueOf(2.2d), rate.getFactor().numberValue(BigDecimal.class));

        b = new DefaultExchangeRate.Builder("test", RateType.DEFERRED);
        b.setBaseCurrency(TestCurrency.of("CHF"));
        b.setTermCurrency(TestCurrency.of("USD"));
        b.setRateChain(rate1, rate2);
        b.setFactor(TestNumberValue.of(2.0));
        rate = b.build();
        assertEquals(rate.getContext(), ConversionContext.of("test", RateType.DEFERRED));
        Assert.assertEquals(TestCurrency.of("CHF"), rate.getBaseCurrency());
        Assert.assertEquals(TestCurrency.of("USD"), rate.getCurrency());
        assertEquals(BigDecimal.valueOf(2.0), rate.getFactor().numberValue(BigDecimal.class));
    }
}
