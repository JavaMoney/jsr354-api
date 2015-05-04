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
     * {@link javax.money.Monetary#getAmountFactory(java.lang.Class)}.
     */
    @Test
    public void testGetFactory() {
        assertNotNull(Monetary.getDefaultAmountFactory());
        assertNotNull(Monetary.getAmountFactory(DummyAmount.class));
        assertTrue(Monetary.getDefaultAmountFactory().getClass() ==
                Monetary.getAmountFactory(DummyAmount.class).getClass());
    }

    /**
     * Test method for {@link javax.money.Monetary#getAmountTypes()}.
     */
    @Test
    public void testGetTypes() {
        assertNotNull(Monetary.getAmountTypes());
        assertTrue(Monetary.getAmountTypes().size() == 1);
        assertTrue(Monetary.getAmountTypes().contains(DummyAmount.class));
    }

    /**
     * Test method for
     * {@link javax.money.Monetary#getDefaultAmountType()}.
     */
    @Test
    public void testGetDefaultAmountFactory() {
        assertNotNull(Monetary.getDefaultAmountFactory());
        assertEquals(DummyAmountBuilder.class, Monetary.getDefaultAmountFactory().getClass());
    }

    /**
     * Test method for
     * {@link Monetary#getAmountFactories()}.
     */
    @Test
    public void testGetAmountFactories() {
        Collection<MonetaryAmountFactory<?>> factories = Monetary.getAmountFactories();
        assertNotNull(factories);
        assertFalse(factories.isEmpty());
    }

    /**
     * Test method for
     * {@link Monetary#getDefaultAmountType()}.
     */
    @Test
    public void testGetDefaultAmountType() {
        Class<? extends MonetaryAmount> type = Monetary.getDefaultAmountType();
        assertNotNull(type);
        assertEquals(type, DummyAmount.class);
    }

    /**
     * Test method for
     * {@link Monetary#getAmountFactory(MonetaryAmountFactoryQuery)}.
     */
    @Test
    public void testGetAmountFactory_Query() {
        @SuppressWarnings("rawtypes")
		MonetaryAmountFactory f = Monetary.getAmountFactory(MonetaryAmountFactoryQueryBuilder.of()
                .setTargetType(DummyAmount.class).build());
        assertNotNull(f);
        assertEquals(f.getClass(), DummyAmountBuilder.class);

    }

    /**
     * Test method for
     * {@link Monetary#getAmountFactories(MonetaryAmountFactoryQuery)}.
     */
    @Test
    public void testGetAmountFactories_Query() {
        Collection<MonetaryAmountFactory<?>> factories = Monetary.
                getAmountFactories(MonetaryAmountFactoryQueryBuilder.of()
                        .setTargetType(DummyAmount.class).build());
        assertNotNull(factories);
        assertTrue(factories.size() == 1);
        factories = Monetary.
                getAmountFactories(MonetaryAmountFactoryQueryBuilder.of()
                        .setProviderName("gigigig2").build());
        assertNotNull(factories);
        assertTrue(factories.isEmpty());
    }

    /**
     * Test method for
     * {@link Monetary#getAmountFactories(MonetaryAmountFactoryQuery)}.
     */
    @Test
    public void testIsAvailable_Query() {
        assertTrue(Monetary.
                isAvailable(MonetaryAmountFactoryQueryBuilder.of()
                        .setTargetType(DummyAmount.class).build()));
        assertFalse(Monetary.
                isAvailable(MonetaryAmountFactoryQueryBuilder.of()
                        .setProviderName("gigigig2").build()));
    }

}
