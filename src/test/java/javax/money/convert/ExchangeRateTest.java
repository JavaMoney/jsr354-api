/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2019 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        assertEquals(Arrays.asList(rate),
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
        assertEquals(Arrays.asList(rate),
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
        assertEquals(Arrays.asList(rate1, rate2),
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
        assertEquals(Arrays.asList(rate1, rate2),
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
