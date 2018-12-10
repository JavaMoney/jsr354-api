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
