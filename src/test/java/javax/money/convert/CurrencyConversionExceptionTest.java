/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
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

import org.testng.Assert;
import org.testng.annotations.Test;

public class CurrencyConversionExceptionTest {

    private static final ConversionContext CONTEXT100 =
            ConversionContextBuilder.of().setProviderName("test").set("timestampMillis", 100L).build();

    @Test
    public void testCurrencyConversionExceptionCurrencyUnitCurrencyUnitContext() {
        CurrencyUnit base = TestCurrency.of("CHF");
        CurrencyUnit term = TestCurrency.of("EUR");
        CurrencyConversionException ex = new CurrencyConversionException(base, term, CONTEXT100);
        Assert.assertEquals(null, ex.getCause());
        Assert.assertEquals(base, ex.getBaseCurrency());
        Assert.assertEquals(term, ex.getTermCurrency());
        Assert.assertEquals(CONTEXT100, ex.getConversionContext());
        Assert.assertEquals("Cannot convert CHF into EUR", ex.getMessage());
    }

    @Test
    public void testCurrencyConversionExceptionCurrencyUnitCurrencyUnitLongStringContextStringThrowable() {
        CurrencyUnit base = TestCurrency.of("CHF");
        CurrencyUnit term = TestCurrency.of("EUR");
        Exception cause = new Exception("cause");
        CurrencyConversionException ex = new CurrencyConversionException(base, term, CONTEXT100, "blabla", cause);
        Assert.assertEquals(cause, ex.getCause());
        Assert.assertEquals(base, ex.getBaseCurrency());
        Assert.assertEquals(term, ex.getTermCurrency());
        Assert.assertEquals(CONTEXT100, ex.getConversionContext());
        Assert.assertEquals("Cannot convert CHF into EUR: blabla", ex.getMessage());
    }

    @Test
    public void testToString() {
        CurrencyUnit base = TestCurrency.of("CHF");
        CurrencyUnit term = TestCurrency.of("EUR");
        Exception cause = new Exception("cause");
        CurrencyConversionException ex = new CurrencyConversionException(base, term, CONTEXT100, "blabla", cause);
        String toString = ex.toString();
        Assert.assertNotNull(toString);
        Assert.assertTrue(toString.contains("CurrencyConversionException"));
        Assert.assertTrue(toString.contains("base=CHF"));
        Assert.assertTrue(toString.contains("term=EUR"));
        Assert.assertTrue(toString.contains("conversionContext=ConversionContext"));
        Assert.assertTrue(toString.contains("provider=test"));
        Assert.assertTrue(toString.contains("Cannot convert CHF into EUR"));
        Assert.assertTrue(toString.contains("blabla"));
    }

}
