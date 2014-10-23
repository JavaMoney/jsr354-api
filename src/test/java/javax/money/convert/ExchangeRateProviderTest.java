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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.math.BigDecimal;

import org.testng.annotations.Test;

/**
 * Test for {@link ExchangeRateProvider}.
 *
 * @author Philippe Marschall
 */
public class ExchangeRateProviderTest {

    /**
     * Test for {@link ExchangeRateProvider#getCurrencyConversion(String)}.
     */
    @Test
    public void testGetCurrencyConversion() {
        ExchangeRateProvider prov = MonetaryConversions
                .getExchangeRateProvider();
        CurrencyConversion conversion = prov.getCurrencyConversion("test1");
        assertNotNull(conversion);
        assertEquals("test1", conversion.getCurrency().getCurrencyCode());
    }

    /**
     * Test for {@link ExchangeRateProvider#getExchangeRate(String, String)}.
     */
    @Test
    public void testGetExchangeRate() {
        ExchangeRateProvider prov = MonetaryConversions
                .getExchangeRateProvider();
        ExchangeRate exchangeRate = prov.getExchangeRate("test1", "test2");
        assertNotNull(exchangeRate);
        assertEquals("test1", exchangeRate.getBaseCurrency().getCurrencyCode());
        assertEquals("test2", exchangeRate.getCurrency().getCurrencyCode());
        assertEquals(new BigDecimal("0.5"), exchangeRate.getFactor().numberValue(BigDecimal.class));
    }

    /**
     * Test for {@link ExchangeRateProvider#getReversed(ExchangeRate)}.
     */
    @Test
    public void testReversed() {
        ExchangeRateProvider prov = MonetaryConversions
                .getExchangeRateProvider();
        ExchangeRate exchangeRate = prov.getExchangeRate("test1", "test2");
        ExchangeRate reversed = prov.getReversed(exchangeRate);
        assertEquals("test2", reversed.getBaseCurrency().getCurrencyCode());
        assertEquals("test1", reversed.getCurrency().getCurrencyCode());
        assertEquals(new BigDecimal("2"), reversed.getFactor().numberValue(BigDecimal.class));
    }

}
