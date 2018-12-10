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
