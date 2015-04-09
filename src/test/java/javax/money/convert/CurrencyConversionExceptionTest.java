/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
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
