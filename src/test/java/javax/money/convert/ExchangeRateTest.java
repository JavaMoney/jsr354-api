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


import javax.money.CurrencyUnit;
import javax.money.TestCurrency;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class ExchangeRateTest {

    @Test
    public void testExchangeRateExchangeRateTypeCurrencyUnitCurrencyUnitNumberString() {
        CurrencyUnit base = TestCurrency.of("CHF");
        CurrencyUnit term = TestCurrency.of("EUR");
        ExchangeRate rate = new DefaultExchangeRate.Builder("myProvider", RateType.DEFERRED).setBaseCurrency(base)
                .setTermCurrency(term).setFactor(TestNumberValue.of(1.5)).build();
        assertEquals(base, rate.getBaseCurrency());
        assertEquals(term, rate.getCurrency());
        assertTrue(1.5d == rate.getFactor().doubleValue());
        assertEquals(ConversionContext.of("myProvider", RateType.DEFERRED),
                rate.getContext());
        assertEquals(Arrays.asList(new ExchangeRate[]{rate}),
                rate.getExchangeRateChain());
    }

    @Test
    public void testExchangeRateExchangeRateTypeCurrencyUnitCurrencyUnitNumberStringLongLong() {
        CurrencyUnit base = TestCurrency.of("CHF");
        CurrencyUnit term = TestCurrency.of("EUR");
        ExchangeRate rate = new DefaultExchangeRate.Builder("test", RateType.DEFERRED).setBaseCurrency(base)
                .setTermCurrency(term).setFactor(TestNumberValue.of(1.5)).build();
        assertEquals(base, rate.getBaseCurrency());
        assertEquals(term, rate.getCurrency());
        assertTrue(1.5d == rate.getFactor().doubleValue());
        assertEquals(ConversionContext.of("test", RateType.DEFERRED),
                rate.getContext());
        assertEquals(Arrays.asList(new ExchangeRate[]{rate}),
                rate.getExchangeRateChain());
    }

    @Test
    public void testExchangeRateExchangeRateTypeCurrencyUnitCurrencyUnitNumberStringExchangeRateArray() {
        CurrencyUnit base = TestCurrency.of("CHF");
        CurrencyUnit baseTerm = TestCurrency.of("EUR");
        CurrencyUnit term = TestCurrency.of("USD");
        ExchangeRate rate1 = new DefaultExchangeRate.Builder("test", RateType.DEFERRED).setBaseCurrency(base)
                .setTermCurrency(baseTerm).setFactor(TestNumberValue.of(0.8)).build();
        ExchangeRate rate2 = new DefaultExchangeRate.Builder("test", RateType.DEFERRED).setBaseCurrency(baseTerm)
                .setTermCurrency(term).setFactor(TestNumberValue.of(1.4)).build();

        // derived rate
        ExchangeRate rate = new DefaultExchangeRate.Builder("test", RateType.DEFERRED).setBaseCurrency(base)
                .setTermCurrency(term).setFactor(TestNumberValue.of(0.8 * 1.4)).setRateChain(rate1, rate2).build();

        assertEquals(base, rate.getBaseCurrency());
        assertEquals(term, rate.getCurrency());
        assertTrue(0.8d * 1.4d == rate.getFactor().doubleValue());
        assertEquals(ConversionContext.of("test", RateType.DEFERRED),
                rate.getContext());
        assertEquals(Arrays.asList(new ExchangeRate[]{rate1, rate2}),
                rate.getExchangeRateChain());
    }

    @Test
    public void testExchangeRateExchangeRateTypeCurrencyUnitCurrencyUnitNumberStringLongLongExchangeRateArray() {
        CurrencyUnit base = TestCurrency.of("CHF");
        CurrencyUnit baseTerm = TestCurrency.of("EUR");
        CurrencyUnit term = TestCurrency.of("USD");
        ExchangeRate rate1 = new DefaultExchangeRate.Builder("test", RateType.DEFERRED).setBaseCurrency(base)
                .setTermCurrency(baseTerm).setFactor(TestNumberValue.of(0.8)).build();
        ExchangeRate rate2 = new DefaultExchangeRate.Builder("test", RateType.DEFERRED).setBaseCurrency(baseTerm)
                .setTermCurrency(term).setFactor(TestNumberValue.of(1.4)).build();

        // derived rate
        ExchangeRate rate = new DefaultExchangeRate.Builder("test", RateType.DEFERRED).setBaseCurrency(base)
                .setTermCurrency(term).setFactor(TestNumberValue.of(0.8 * 1.4)).setRateChain(rate1, rate2).build();
        assertEquals(base, rate.getBaseCurrency());
        assertEquals(term, rate.getCurrency());
        assertTrue(0.8d * 1.4d == rate.getFactor().doubleValue());
        assertEquals(ConversionContext.of("test", RateType.DEFERRED),
                rate.getContext());
        assertEquals(Arrays.asList(new ExchangeRate[]{rate1, rate2}),
                rate.getExchangeRateChain());
    }

    @Test
    public void testToString() {
        CurrencyUnit base = TestCurrency.of("CHF");
        CurrencyUnit baseTerm = TestCurrency.of("EUR");
        CurrencyUnit term = TestCurrency.of("USD");
        ExchangeRate rate1 = new DefaultExchangeRate.Builder("test", RateType.DEFERRED).setBaseCurrency(base)
                .setTermCurrency(baseTerm).setFactor(TestNumberValue.of(0.8)).build();
        ExchangeRate rate2 = new DefaultExchangeRate.Builder("test", RateType.DEFERRED).setBaseCurrency(baseTerm)
                .setTermCurrency(term).setFactor(TestNumberValue.of(1.4)).build();

        // derived rate
        ExchangeRate rate = new DefaultExchangeRate.Builder("test", RateType.DEFERRED).setBaseCurrency(base)
                .setTermCurrency(term).setFactor(TestNumberValue.of(0.8 * 1.4)).setRateChain(rate1, rate2).build();
        String toString = rate.toString();
        assertTrue(toString.contains("ExchangeRate ["));
        assertTrue(toString.contains("baseCurrency=CHF, factor=1.1199999999999999, conversionContext="));
        assertTrue(toString.contains("RateType"));
        assertTrue(toString.contains("=DEFERRED"));
        assertTrue(toString.contains("provider=test"));
    }

}
