/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.money.internal.DefaultMonetaryAmountsSingletonQuerySpi;
import javax.money.internal.DefaultMonetaryAmountsSingletonSpi;
import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryAmountsSingletonQuerySpi;
import javax.money.spi.MonetaryAmountsSingletonSpi;
import javax.money.spi.MonetaryCurrenciesSingletonSpi;
import javax.money.spi.MonetaryRoundingsSingletonSpi;
import javax.money.spi.RoundingProviderSpi;
import javax.money.spi.ServiceProvider;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.Test;

/**
 * Test to ensure that singleton SPIs used by {@link Monetary} handle substitution of {@link ServiceProvider} to use when
 * calling {@link Bootstrap#init(javax.money.spi.ServiceProvider)}. (e.g. in an OSGI environment)
 * @author Matthias Hanisch
 *
 */
public class MonetaryDynamicServiceProviderTest extends AbstractDynamicServiceProviderTest {
    
    
    /**
     * Default SPI: {@link DefaultMonetaryCurrenciesSingletonSpi} supports currencies test1 and test2.
     * Dynamic SPI: Mock supporting currencies test1 and test3. 
     * Testing following steps:
     * <ul>
     * <li>use default SPI</li>
     * <li>currency test1 available</li>
     * <li>currency test2 available</li>
     * <li>currency test3 <b>not</b> available</li>
     * <li>use dynamic SPI</li>
     * <li>currency test1 available</li>
     * <li>currency test2 <b>not</b> available</li>
     * <li>currency test3 available</li>
     * <li>use default SPI</li>
     * <li>currency test1 available</li>
     * <li>currency test2 available</li>
     * <li>currency test3 <b>not</b> available</li>
     * </ul>
     * 
     */
    @Test
    public void testMonetaryCurrenciesSingletonSpi() {
        // DefaultMonetaryCurrenciesSingletonSpi is used
        assertCurrencyAvailable("test1");
        assertCurrencyAvailable("test2");
        assertCurrencyMissing("test3");
        MonetaryCurrenciesSingletonSpi mockSingleton = Mockito.mock(MonetaryCurrenciesSingletonSpi.class);
        registerService(MonetaryCurrenciesSingletonSpi.class, mockSingleton);
        doAnswer(new Answer<CurrencyUnit>() {
            private List<String> supportedCurrencies = Arrays.asList("test1","test3");
            @Override
            public CurrencyUnit answer(InvocationOnMock invocation)
                    throws Throwable {
                String currencyCode =(String)invocation.getArguments()[0];
                if(supportedCurrencies.contains(currencyCode)) {
                    return new TestCurrency(currencyCode, 1,2);
                }
                throw new UnknownCurrencyException(currencyCode);
            }
        }).when(mockSingleton).getCurrency(anyString());
        initTestServiceProvider();
        // DynamicMonetaryCurrenciesSingletonSpi is used
        assertCurrencyAvailable("test1");
        assertCurrencyMissing("test2");
        assertCurrencyAvailable("test3");
        initOriginalServiceProvider();
        // DefaultMonetaryCurrenciesSingletonSpi is used again
        assertCurrencyAvailable("test1");
        assertCurrencyAvailable("test2");
        assertCurrencyMissing("test3");
    }
    
    /**
     * Default SPI: {@link DefaultMonetaryAmountsSingletonSpi} supports a {@link MonetaryAmountFactory} creating instances of DummyAmount.
     * Dynamic SPI: Mock supporting a {@link MonetaryAmountFactory} creating a mocked {@link MonetaryAmount}
     * Testing following steps:
     * <ul>
     * <li>use default SPI</li>
     * <li>created MonetaryAmount should be a DummyAmount</li>
     * <li>use dynamic SPI</li>
     * <li>created MonetaryAmount should match the mocked Amount</li>
     * <li>use default SPI</li>
     * <li>created MonetaryAmount should be a DummyAmount</li>
     * </ul>     
     */
    @Test
    public void testMonetaryAmountsSingletonSpi() {
        assertTrue(Monetary.getDefaultAmountFactory().create()instanceof DummyAmount);
        MonetaryAmountsSingletonSpi mockSingleton = mock(MonetaryAmountsSingletonSpi.class);
        MonetaryAmountFactory<?> mockFactory = mock(MonetaryAmountFactory.class);
        MonetaryAmount mockAmount = mock(MonetaryAmount.class);
        doReturn(mockFactory).when(mockSingleton).getDefaultAmountFactory();
        doReturn(mockAmount).when(mockFactory).create();
        registerService(MonetaryAmountsSingletonSpi.class, mockSingleton);
        initTestServiceProvider();
        assertFalse(Monetary.getDefaultAmountFactory().create()instanceof DummyAmount);
        assertEquals(Monetary.getDefaultAmountFactory().create(), mockAmount);
        initOriginalServiceProvider();
        assertTrue(Monetary.getDefaultAmountFactory().create()instanceof DummyAmount);
    }
    
    /**
     * Default SPI: {@link DefaultMonetaryAmountsSingletonQuerySpi} supports {@link MonetaryAmountFactoryQuery} for DummyAmount.
     * Dynamic SPI: Mock not supporting {@link MonetaryAmountFactoryQuery} for none MonetaryMount at all.
     * Testing following steps:
     * <ul>
     * <li>use default SPI</li>
     * <li>query for DummyAmount should be true</li>
     * <li>use dynamic SPI</li>
     * <li>query for DummyAmount should be false</li>
     * <li>use default SPI</li>
     * <li>query for DummyAmount should be true</li>
     * </ul>
     */
    @Test
    public void testMonetaryAmountsSingletonQuerySpi() {
        MonetaryAmountFactoryQuery query = MonetaryAmountFactoryQueryBuilder.of()
        .setTargetType(DummyAmount.class).build();
        assertTrue(Monetary.isAvailable(query));
        MonetaryAmountsSingletonQuerySpi mock= mock(MonetaryAmountsSingletonQuerySpi.class);
        doReturn(Boolean.FALSE).when(mock).isAvailable(any(MonetaryAmountFactoryQuery.class));
        registerService(MonetaryAmountsSingletonQuerySpi.class, mock);
        initTestServiceProvider();
        assertFalse(Monetary.isAvailable(query));
        initOriginalServiceProvider();
        assertTrue(Monetary.isAvailable(query));
    }
    
    /**
     * Default SPI: {@link DefaultMonetaryRoundingsSingletonSpi} uses {@link RoundingProviderSpi} supporting two rounding names.
     * Dynamic SPI: Mock supporting one rounding name.
     * Testing following steps:
     * <ul>
     * <li>use default SPI</li>
     * <li>number of rounding names should be two</li>
     * <li>use dynamic SPI</li>
     * <li>number of rounding names should be one</li>
     * <li>use default SPI</li>
     * <li>number of rounding names should be two</li>
     * </ul>
     */
    @Test
    public void testMonetaryRoundingsSingletonSpi() {
        assertEquals(Monetary.getRoundingNames().size(),2);
        MonetaryRoundingsSingletonSpi mock = mock(MonetaryRoundingsSingletonSpi.class);
        doReturn(new HashSet<>(Arrays.asList("dummyRounding"))).when(mock).getRoundingNames();
        registerService(MonetaryRoundingsSingletonSpi.class, mock);
        initTestServiceProvider();
        assertEquals(Monetary.getRoundingNames().size(),1);
        initOriginalServiceProvider();
        assertEquals(Monetary.getRoundingNames().size(),2);
    }
    
    private void assertCurrencyAvailable(String currency) {
        CurrencyUnit cur = Monetary.getCurrency(currency);
        assertNotNull(cur);
    }
    
    private void assertCurrencyMissing(String currency) {
        try {
            CurrencyUnit cur = Monetary.getCurrency(currency);
            fail(String.format("currency %s should not be available, but got %s", currency, cur));
        } catch(UnknownCurrencyException ex) {
            ex.printStackTrace();
            // expected
        }
    }
    

}
