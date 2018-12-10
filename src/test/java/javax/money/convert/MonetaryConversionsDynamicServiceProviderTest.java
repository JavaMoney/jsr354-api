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

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryConversionsSingletonSpi;
import javax.money.spi.ServiceProvider;

import org.testng.annotations.Test;

import javax.money.AbstractDynamicServiceProviderTest;

/**
 * Test to ensure that singleton SPIs used by {@link MonetaryConversions} handle substitution of {@link ServiceProvider} to use when
 * calling {@link Bootstrap#init(javax.money.spi.ServiceProvider)}. (e.g. in an OSGI environment)
 * @author Matthias Hanisch
 *
 */
public class MonetaryConversionsDynamicServiceProviderTest
        extends AbstractDynamicServiceProviderTest {

    /**
     * Default test SPI: {@link TestMonetaryConversionsSingletonSpi} supports one conversion provider name.
     * Dynamic SPI: Mock supporting two conversion provider names.
     * Testing following steps:
     * <ul>
     * <li>use default SPI</li>
     * <li>number of conversion provider names should be one</li>
     * <li>use dynamic SPI</li>
     * <li>number of conversion provider names should be two</li>
     * <li>use default SPI</li>
     * <li>number of conversion provider names should be one</li>
     * </ul>
     */
    @Test
    public void testMonetaryConversionsSingletonSpi() {
        assertEquals(MonetaryConversions.getConversionProviderNames().size(),1);
        MonetaryConversionsSingletonSpi mock = mock(MonetaryConversionsSingletonSpi.class);
        doReturn(new HashSet<>(Arrays.asList("conversionProviderOne", "conversionProviderTwo"))).when(mock).getProviderNames();
        registerService(MonetaryConversionsSingletonSpi.class, mock);
        initTestServiceProvider();
        assertEquals(MonetaryConversions.getConversionProviderNames().size(),2);
        initOriginalServiceProvider();
        assertEquals(MonetaryConversions.getConversionProviderNames().size(),1);
        
    }
}
