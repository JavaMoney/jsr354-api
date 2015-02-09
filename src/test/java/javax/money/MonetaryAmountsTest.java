/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import java.util.Collection;

/**
 * @author Anatole
 */
public class MonetaryAmountsTest {

    /**
     * Test method for
     * {@link javax.money.MonetaryAmounts#getAmountFactory(java.lang.Class)}.
     */
    @Test
    public void testGetFactory() {
        assertNotNull(MonetaryAmounts.getDefaultAmountFactory());
        assertNotNull(MonetaryAmounts.getAmountFactory(DummyAmount.class));
        assertTrue(MonetaryAmounts.getDefaultAmountFactory().getClass() ==
                MonetaryAmounts.getAmountFactory(DummyAmount.class).getClass());
    }

    /**
     * Test method for {@link javax.money.MonetaryAmounts#getAmountTypes()}.
     */
    @Test
    public void testGetTypes() {
        assertNotNull(MonetaryAmounts.getAmountTypes());
        assertTrue(MonetaryAmounts.getAmountTypes().size() == 1);
        assertTrue(MonetaryAmounts.getAmountTypes().contains(DummyAmount.class));
    }

    /**
     * Test method for
     * {@link javax.money.MonetaryAmounts#getDefaultAmountType()}.
     */
    @Test
    public void testGetDefaultAmountFactory() {
        assertNotNull(MonetaryAmounts.getDefaultAmountFactory());
        assertEquals(DummyAmountBuilder.class, MonetaryAmounts.getDefaultAmountFactory().getClass());
    }

    /**
     * Test method for
     * {@link MonetaryAmounts#getAmountFactories()}.
     */
    @Test
    public void testGetAmountFactories() {
        Collection<MonetaryAmountFactory<?>> factories = MonetaryAmounts.getAmountFactories();
        assertNotNull(factories);
        assertFalse(factories.isEmpty());
    }

    /**
     * Test method for
     * {@link MonetaryAmounts#getDefaultAmountType()}.
     */
    @Test
    public void testGetDefaultAmountType() {
        Class<? extends MonetaryAmount> type = MonetaryAmounts.getDefaultAmountType();
        assertNotNull(type);
        assertEquals(type, DummyAmount.class);
    }

    /**
     * Test method for
     * {@link MonetaryAmounts#getAmountFactory(MonetaryAmountFactoryQuery)}.
     */
    @Test
    public void testGetAmountFactory_Query() {
        MonetaryAmountFactory f = MonetaryAmounts.getAmountFactory(MonetaryAmountFactoryQueryBuilder.of()
                .setTargetType(DummyAmount.class).build());
        assertNotNull(f);
        assertEquals(f.getClass(), DummyAmountBuilder.class);

    }

    /**
     * Test method for
     * {@link MonetaryAmounts#getAmountFactories(MonetaryAmountFactoryQuery)}.
     */
    @Test
    public void testGetAmountFactories_Query() {
        Collection<MonetaryAmountFactory<?>> factories = MonetaryAmounts.
                getAmountFactories(MonetaryAmountFactoryQueryBuilder.of()
                        .setTargetType(DummyAmount.class).build());
        assertNotNull(factories);
        assertTrue(factories.size() == 1);
        factories = MonetaryAmounts.
                getAmountFactories(MonetaryAmountFactoryQueryBuilder.of()
                        .setProviderName("gigigig2").build());
        assertNotNull(factories);
        assertTrue(factories.isEmpty());
    }

    /**
     * Test method for
     * {@link MonetaryAmounts#getAmountFactories(MonetaryAmountFactoryQuery)}.
     */
    @Test
    public void testIsAvailable_Query() {
        assertTrue(MonetaryAmounts.
                isAvailable(MonetaryAmountFactoryQueryBuilder.of()
                        .setTargetType(DummyAmount.class).build()));
        assertFalse(MonetaryAmounts.
                isAvailable(MonetaryAmountFactoryQueryBuilder.of()
                        .setProviderName("gigigig2").build()));
    }

}
