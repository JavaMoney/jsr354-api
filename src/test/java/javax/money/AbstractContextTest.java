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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

/**
 * Tests for {@link javax.money.AbstractContext}.
 */
public class AbstractContextTest {
    @Test
    public void testSet() {
        TestContext ctx = new TestContext.Builder().set("Test").build();
        assertNotNull(ctx.get(String.class));
        assertEquals(ctx.get(String.class), "Test");
        assertEquals(ctx.get(String.class.getName(), String.class), "Test");
        assertEquals(ctx.get("String", String.class), null);
    }

    @Test
    public void testSetWithKey() {
        TestContext ctx = new TestContext.Builder().set("myKey", "Test").build();
        assertNull(ctx.get(String.class));
        assertEquals("Test", ctx.get("myKey", String.class));
        assertEquals(ctx.get("myKey", String.class), "Test");
        assertEquals(ctx.get(String.class.getName(), String.class), null);
    }

    @Test
    public void testSetAll() {
        TestContext ctx = new TestContext.Builder().set("myKey", "Test").build();
        TestContext ctx2 = new TestContext.Builder().importContext(ctx).build();
        assertEquals(ctx, ctx2);
    }

    @Test
    public void testSetWithKeyAndType() {
        TestContext ctx = new TestContext.Builder().set("MyNum", 2).build();
        assertNull(ctx.get(String.class));
        assertEquals(Integer.valueOf(2), ctx.getInt("MyNum"));
        assertEquals(ctx.get("MyNum", Number.class), 2);
        assertNotNull(ctx.get("MyNum", Integer.class));
    }

    @Test
    public void testHashCode() {
        List<TestContext> contexts = new ArrayList<>();
        contexts.add(new TestContext.Builder().set("Test").set(1).set((long) 2).build());
        contexts.add(new TestContext.Builder().set("Test").set(2).set((long) 1).build());
        contexts.add(new TestContext.Builder().set("Test").set(2).build());
        contexts.add(new TestContext.Builder().set("Test").set((long) 2).build());
        contexts.add(new TestContext.Builder().set("Test").set(Boolean.TRUE).set("Test").build());
        Set<Integer> hashCodes = new HashSet<>();
        contexts.forEach(ctx -> hashCodes.add(ctx.hashCode()));
        // Check we have 5 distinct hash codes...
        assertTrue(hashCodes.size() > 2);
    }

    @Test
    public void testEquals() {
        List<TestContext> contexts = new ArrayList<>();
        contexts.add(new TestContext.Builder().set("Test").set(11).set((long) 2).build());
        contexts.add(new TestContext.Builder().set("Test").set(2).set((long) 11).build());
        contexts.add(new TestContext.Builder().set("Test").set(2).build());
        contexts.add(new TestContext.Builder().set("Test").set((long) 2).build());
        contexts.add(new TestContext.Builder().set("Test").set(Boolean.TRUE).set("Test").build());
        Set<TestContext> checkContexts = new HashSet<>();
        for (TestContext ctx : contexts) {
            checkContexts.add(ctx);
            checkContexts.add(ctx);
        }
        // Check we have 5 distinct hash codes...
        assertTrue(checkContexts.size() == 5);
    }

    @Test
    public void testToString() {
        TestContext ctx = new TestContext.Builder().set("Test").set(1).set((long) 2).build();
        assertNotNull(ctx.toString());
        assertTrue(ctx.toString().contains("1"));
        assertTrue(ctx.toString().contains("2"));
        assertTrue(ctx.toString().contains("Test"));
        assertTrue(ctx.toString().contains("String"));
        assertTrue(ctx.toString().contains("Integer"));
        assertTrue(ctx.toString().contains("Long"));
        assertTrue(ctx.toString().contains("TestContext"));
    }

    @Test
    public void testGetKeys() {
        TestContext ctx = new TestContext.Builder().set("Test").set("a", 1).set("b", 2).build();
        Set<String> keys = ctx.getKeys(String.class);
        assertNotNull(keys);
        assertFalse(keys.isEmpty());
        assertEquals(String.class.getName(), keys.iterator().next());
        keys = ctx.getKeys(Integer.class);
        assertNotNull(keys);
        assertTrue(keys.size() == 2);
        assertTrue("a".equals(keys.iterator().next()) || "b".equals(keys.iterator().next()));
    }

    @Test
    public void testGetType() {
        TestContext ctx = new TestContext.Builder().set("Test").set("a", 1).set("b", 2).build();
        assertEquals(String.class, ctx.getType(String.class.getName()));
        assertEquals(Integer.class, ctx.getType("a"));
        assertEquals(Integer.class, ctx.getType("b"));
    }

    private static class TestContext extends AbstractContext {

        private static final long serialVersionUID = 1L;

        /**
         * Private constructor, used by {@link AbstractContextBuilder}.
         *
         * @param builder the Builder.
         */
        protected TestContext(@SuppressWarnings("rawtypes") AbstractContextBuilder builder) {
            super(builder);
        }

        private static final class Builder extends AbstractContextBuilder<Builder, TestContext> {
            @Override
            public TestContext build() {
                return new TestContext(this);
            }
        }

    }


}
