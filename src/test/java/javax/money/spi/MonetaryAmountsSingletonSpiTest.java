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

import javax.money.DummyAmount;
import javax.money.DummyAmountBuilder;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Tests the default methods on MonetaryAmountsSingletonSpi.
 */
public class MonetaryAmountsSingletonSpiTest {

    private final MonetaryAmountsSingletonSpi testSpi = new MonetaryAmountsSingletonSpi() {
        @SuppressWarnings("unchecked")
        @Override
        public <T extends MonetaryAmount> MonetaryAmountFactory<T> getAmountFactory(Class<T> amountType) {
            if (amountType.equals(DummyAmount.class)) {
                return (MonetaryAmountFactory<T>) new DummyAmountBuilder();
            }
            return null;
        }

        @Override
        public Class<? extends MonetaryAmount> getDefaultAmountType() {
            return DummyAmount.class;
        }

        @Override
        public Collection<Class<? extends MonetaryAmount>> getAmountTypes() {
            Set<Class<? extends MonetaryAmount>> result = new HashSet<>();
            result.add(DummyAmount.class);
            return result;
        }
    };

    @Test
    public void testGetDefaultAmountFactory() {
        assertNotNull(testSpi.getDefaultAmountFactory());
        assertEquals(DummyAmountBuilder.class, testSpi.getDefaultAmountFactory().getClass());
    }

    @Test
    public void testGetAmountFactories() {
        Collection<MonetaryAmountFactory<?>> factories = testSpi.getAmountFactories();
        assertNotNull(factories);
        assertFalse(factories.isEmpty());
        assertEquals(1, factories.size());
        assertEquals(DummyAmountBuilder.class, factories.iterator().next().getClass());
    }

}
