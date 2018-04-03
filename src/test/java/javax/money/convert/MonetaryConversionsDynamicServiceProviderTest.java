/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
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
