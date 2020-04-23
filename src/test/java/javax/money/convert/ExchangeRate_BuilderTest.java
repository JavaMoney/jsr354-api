/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2020 Werner Keil, Otavio Santana, Trivadis AG
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

import org.testng.annotations.Test;

import javax.money.CurrencyUnit;
import javax.money.TestCurrency;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class ExchangeRate_BuilderTest {

    @Test
    public void testWithConversionContext() {
        DefaultExchangeRate.Builder b = new DefaultExchangeRate.Builder("test", RateType.DEFERRED);
        DefaultExchangeRate.Builder b2 = b.setContext(ConversionContext.of("test", RateType.DEFERRED));
        assertSame(b, b2);
        b2 = b.setContext(ConversionContext.of("test2", RateType.DEFERRED));
        assertSame(b, b2);
    }

    @Test
    public void testGetSetBase() {
        DefaultExchangeRate.Builder b = new DefaultExchangeRate.Builder("test", RateType.DEFERRED);
        DefaultExchangeRate.Builder b2 = b.setBaseCurrency(TestCurrency.of("CHF"));
        assertSame(b, b2);
    }

    @Test
    public void testGetSetTerm() {
        DefaultExchangeRate.Builder b = new DefaultExchangeRate.Builder("test", RateType.DEFERRED);
        DefaultExchangeRate.Builder b2 = b.setTermCurrency(TestCurrency.of("CHF"));
        assertSame(b, b2);
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
        assertSame(b, b2);
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
        assertSame(b, b2);
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
        assertEquals(TestCurrency.of("CHF"), rate.getBaseCurrency());
        assertEquals(TestCurrency.of("USD"), rate.getCurrency());
        assertEquals(BigDecimal.valueOf(2.0), rate.getFactor().numberValue(BigDecimal.class));
    }
}
