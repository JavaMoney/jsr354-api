/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import org.testng.annotations.Test;

import javax.money.*;
import java.util.*;

import static org.testng.AssertJUnit.*;

/**
 * Tests the default methods on MonetaryAmountsSingletonSpi.
 */
public class MonetaryRoundingsSingletonSpiTest {

    private static final MonetaryRounding R1 = new MonetaryRounding() {
        @Override
        public RoundingContext getRoundingContext() {
            return null;
        }

        @Override
        public MonetaryAmount apply(MonetaryAmount monetaryAmount) {
            return monetaryAmount;
        }
    };

    private final MonetaryRoundingsSingletonSpi testSpi = new MonetaryRoundingsSingletonSpi() {

        @Override
        public Set<String> getRoundingNames(String... providers) {
            Set<String> roundings = new HashSet<>();
            roundings.add("r1");
            return roundings;
        }

        @Override
        public Set<String> getProviderNames() {
            Set<String> providers = new HashSet<>();
            providers.add("p");
            return providers;
        }

        @Override
        public List<String> getDefaultProviderChain() {
            List<String> providers = new ArrayList<>();
            providers.add("a");
            return providers;
        }

        @Override
        public Collection<MonetaryRounding> getRoundings(RoundingQuery query) {
            List<MonetaryRounding> roundings = new ArrayList<>();
            if ("r1".equals(query.getRoundingName())) {
                roundings.add(R1);
            } else if (query.getCurrency() != null && "USD".equals(query.getCurrency().getCurrencyCode())) {
                roundings.add(R1);
            }
            return roundings;
        }

        @Override
        public MonetaryRounding getDefaultRounding() {
            return R1;
        }

    };


    @Test
    public void testGetRounding_CurrencyUnit_StringArr() {
        MonetaryRounding r = testSpi.getRounding(TestCurrency.of("USD"), "p");
        assertNotNull(r);
    }

    @Test(expectedExceptions = {MonetaryException.class})
    public void testGetRounding_CurrencyUnit_StringArr_BC() {
        testSpi.getRounding(TestCurrency.of("EUR"), "p");
    }

    @Test
    public void testGetRounding_String_StringArr() {
        assertNotNull(testSpi.getRounding("r1"));
        assertNotNull(testSpi.getRounding("r1", "p"));
    }

    @Test
    public void testGetRounding_RoundingQuery() {
        assertNotNull(testSpi.getRounding(RoundingQueryBuilder.of().setRoundingName("r1").build()));
    }

    @Test
    public void tesIsRoundingAvailable() {
        assertTrue(testSpi.isRoundingAvailable(RoundingQueryBuilder.of().setRoundingName("r1").build()));
        assertFalse(testSpi.isRoundingAvailable(RoundingQueryBuilder.of().setRoundingName("hgfhgf").build()));
    }

    @Test
    public void tesIsRoundingAvailable_String_StringArr() {
        assertTrue(testSpi.isRoundingAvailable("r1"));
        assertFalse(testSpi.isRoundingAvailable("sdsd"));
    }

    @Test
    public void tesIsRoundingAvailable_CurrencyUnit_StringArr() {
        assertTrue(testSpi.isRoundingAvailable(TestCurrency.of("USD"), "p"));
        assertTrue(testSpi.isRoundingAvailable(TestCurrency.of("USD")));
        assertFalse(testSpi.isRoundingAvailable(TestCurrency.of("CHF")));
        assertFalse(testSpi.isRoundingAvailable(TestCurrency.of("CHF"), "p"));
    }

}
