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
