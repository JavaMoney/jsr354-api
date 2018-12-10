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

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Tests for {@link javax.money.MonetaryContext}.
 */
public class MonetaryContextTest {

    @Test
    public void testGetPrecision() throws Exception {
        MonetaryContext ctx = MonetaryContextBuilder.of(MonetaryAmount.class).setPrecision(299).build();
        assertTrue(ctx.getPrecision() == 299);
    }

    @Test
    public void testIsFixedScale() throws Exception {
        MonetaryContext ctx = MonetaryContextBuilder.of(MonetaryAmount.class).setFixedScale(true).build();
        assertTrue(ctx.isFixedScale());
    }

    @Test
    public void testGetMaxScale() throws Exception {
        MonetaryContext ctx = MonetaryContextBuilder.of(MonetaryAmount.class).setMaxScale(122).build();
        assertTrue(ctx.getMaxScale() == 122);
    }

    @Test
    public void testGetAmountTypeExplicit() throws Exception {
        MonetaryContext ctx = MonetaryContextBuilder.of(MonetaryAmount.class).setAmountType(DummyAmount.class).build();
        assertTrue(ctx.getAmountType() == DummyAmount.class);
    }

    @Test
    public void testOf_MonetaryContext() throws Exception {
        MonetaryContext ctx = MonetaryContextBuilder.of(MonetaryAmount.class).setAmountType(DummyAmount.class).build();
        assertEquals(ctx, MonetaryContextBuilder.of(ctx).build());
    }

    @Test
    public void testGetAmountType() throws Exception {
        MonetaryContext ctx = MonetaryContextBuilder.of(MonetaryAmount.class).setMaxScale(122).build();
        assertEquals(ctx.getAmountType(), MonetaryAmount.class);
        //noinspection ClassMayBeInterface
        abstract class TestAmount implements MonetaryAmount {
        }
        ctx = MonetaryContextBuilder.of(TestAmount.class).build();
        assertEquals(ctx.getAmountType(), TestAmount.class);
    }

    @Test
    public void testHashCode() throws Exception {
        List<MonetaryContext> contexts = new ArrayList<>();
        contexts.add(MonetaryContextBuilder.of(MonetaryAmount.class).setMaxScale(122).build());
        contexts.add(MonetaryContextBuilder.of(MonetaryAmount.class).setPrecision(299).build());
        contexts.add(MonetaryContextBuilder.of(MonetaryAmount.class).setFixedScale(true).build());
        Set<Integer> hashCodes = new HashSet<>();
        contexts.forEach(ctx -> hashCodes.add(ctx.hashCode()));
        // Check we have 5 distinct hash codes...
        assertTrue(hashCodes.size() == 3);
    }

    @Test
    public void testEquals() throws Exception {
        List<MonetaryContext> contexts = new ArrayList<>();
        contexts.add(MonetaryContextBuilder.of(MonetaryAmount.class).setMaxScale(122).build());
        contexts.add(MonetaryContextBuilder.of(MonetaryAmount.class).setPrecision(299).build());
        contexts.add(MonetaryContextBuilder.of(MonetaryAmount.class).setFixedScale(true).build());
        Set<MonetaryContext> checkContexts = new HashSet<>();
        for (MonetaryContext ctx : contexts) {
            checkContexts.add(ctx);
            checkContexts.add(ctx);
        }
        // Check we have 5 distinct hash codes...
        assertTrue(checkContexts.size() == 3);
    }

    @Test
    public void testToString() throws Exception {
        //noinspection ClassMayBeInterface
        abstract class TestAmount implements MonetaryAmount {
        }
        MonetaryContext ctx =
                MonetaryContextBuilder.of(TestAmount.class).setFixedScale(true).setMaxScale(111).setPrecision(200)
                        .set("myKey", "myValue").set("TEST").build();
        assertNotNull(ctx.toString());
        assertTrue(ctx.toString().contains("111"));
        assertTrue(ctx.toString().contains("200"));
        assertTrue(ctx.toString().contains("TEST"));
        assertTrue(ctx.toString().contains("myKey"));
        assertTrue(ctx.toString().contains("myValue"));
        assertTrue(ctx.toString().contains("String"));
        assertTrue(ctx.toString().contains("maxScale"));
        assertTrue(ctx.toString().contains("precision"));
        assertTrue(ctx.toString().contains("fixedScale"));
        assertTrue(ctx.toString().contains("TestAmount"));
        assertTrue(ctx.toString().contains("amountType"));
        assertTrue(ctx.toString().contains("MonetaryContext"));
    }
}
