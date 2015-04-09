/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import javax.money.*;
import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.*;

/**
 * Tests the default methods on MonetaryAmountsSingletonQuerySpi.
 */
public class MonetaryAmountsSingletonQuerySpiTest {

    private final MonetaryAmountsSingletonQuerySpi testSpi = query -> {
        List<MonetaryAmountFactory<? extends MonetaryAmount>> factories = new ArrayList<>();
        if (query.getBoolean("select")) {
            factories.add(new DummyAmountBuilder());
        }
        return factories;
    };

    public void testIsAvailable() {
        assertTrue(testSpi.isAvailable(MonetaryAmountFactoryQueryBuilder.of().set("select", true).build()));
        assertFalse(testSpi.isAvailable(MonetaryAmountFactoryQueryBuilder.of().set("select", false).build()));
    }

    public void testGetAmountType() {
        assertEquals(DummyAmount.class, testSpi.getAmountType(MonetaryAmountFactoryQueryBuilder.of().set("select", true).build()));
        assertEquals(null, testSpi.getAmountType(MonetaryAmountFactoryQueryBuilder.of().set("select", false).build()));
    }

    public void testGetAmountTypes() {
        assertFalse(testSpi.getAmountTypes(MonetaryAmountFactoryQueryBuilder.of().set("select", true).build()).isEmpty());
        assertTrue(testSpi.getAmountTypes(MonetaryAmountFactoryQueryBuilder.of().set("select", false).build()).isEmpty());
    }

    public void testGetAmountFactory() {
        assertNotNull(testSpi.getAmountFactory(MonetaryAmountFactoryQueryBuilder.of().set("select", true).build()));
        assertNull(testSpi.getAmountFactory(MonetaryAmountFactoryQueryBuilder.of().set("select", false).build()));
    }

}
