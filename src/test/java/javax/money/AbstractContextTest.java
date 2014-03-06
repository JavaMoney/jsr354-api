/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package javax.money;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Anatole on 05.03.14.
 */
public class AbstractContextTest{
    @Test
    public void testSet() {
        TestContext ctx = new TestContext.Builder().set("Test").create();
        assertNotNull(ctx.getAttribute(String.class));
        assertEquals(ctx.getAttribute(String.class), "Test");
        assertEquals(ctx.getNamedAttribute(String.class, String.class), "Test");
        assertEquals(ctx.getNamedAttribute(String.class, "String"), null);
    }

    @Test
    public void testSetWithKey(){
        TestContext ctx = new TestContext.Builder().set("Test", "myKey").create();
        assertNull(ctx.getAttribute(String.class));
        assertEquals("Test", ctx.getNamedAttribute(String.class, "myKey"));
        assertEquals(ctx.getNamedAttribute(String.class, "myKey"), "Test");
        assertEquals(ctx.getNamedAttribute(String.class, String.class), null);
    }

    @Test
    public void testSetAll(){
        TestContext ctx = new TestContext.Builder().set("Test", "myKey").create();
        TestContext ctx2 = new TestContext.Builder().setAll(ctx).create();
        assertEquals(ctx, ctx2);
    }

    @Test
    public void testSetWithKeyAndType() {
        TestContext ctx = new TestContext.Builder().set(Integer.valueOf(2), "MyNum", Number.class).create();
        assertNull(ctx.getAttribute(String.class));
        assertEquals("myKey", ctx.getAttribute(String.class, "myKey"));
        assertEquals(ctx.getNamedAttribute(Number.class, "MyNum"), Integer.valueOf(2));
        assertEquals(ctx.getNamedAttribute(Integer.class, "MyNum"), null);
    }

    @Test
    public void testGetAttribute() {
        TestContext ctx = new TestContext.Builder().set("Test").create();
        assertNotNull(ctx.getAttribute(String.class));
        assertEquals(ctx.getNamedAttribute(String.class, "Gugus", "defaultValue"), "defaultValue");
        assertEquals(ctx.getAttribute(Boolean.class, Boolean.TRUE), Boolean.TRUE);
    }

    @Test
    public void testGetAttributeTypes(){
        TestContext ctx = new TestContext.Builder().set("Test").set(Integer.valueOf(2)).set(Long.valueOf(2)).create();
        Set<Class<?>> types = ctx.getAttributeTypes();
        assertNotNull(types);
        assertTrue(types.size()==3);
        assertTrue(types.contains(String.class));
        assertTrue(types.contains(Integer.class));
        assertTrue(types.contains(Long.class));
    }

    @Test
    public void testHashCode() {
        List<TestContext> contexts = new ArrayList<>();
        contexts.add(new TestContext.Builder().set("Test").set(Integer.valueOf(1)).set(Long.valueOf(2)).create());
        contexts.add(new TestContext.Builder().set("Test").set(Integer.valueOf(2)).set(Long.valueOf(1)).create());
        contexts.add(new TestContext.Builder().set("Test").set(Integer.valueOf(2)).create());
        contexts.add(new TestContext.Builder().set("Test").set(Long.valueOf(2)).create());
        contexts.add(new TestContext.Builder().set("Test").set(Boolean.TRUE).set("Test").create());
        Set<Integer> hashCodes = new HashSet<>();
        for(TestContext ctx : contexts){
            hashCodes.add(ctx.hashCode());
        }
        // Check we have 5 distinct hash codes...
        assertTrue(hashCodes.size() > 2);
    }

    @Test
    public void testEquals() {
        List<TestContext> contexts = new ArrayList<>();
        contexts.add(new TestContext.Builder().set("Test").set(Integer.valueOf(11)).set(Long.valueOf(2)).create());
        contexts.add(new TestContext.Builder().set("Test").set(Integer.valueOf(2)).set(Long.valueOf(11)).create());
        contexts.add(new TestContext.Builder().set("Test").set(Integer.valueOf(2)).create());
        contexts.add(new TestContext.Builder().set("Test").set(Long.valueOf(2)).create());
        contexts.add(new TestContext.Builder().set("Test").set(Boolean.TRUE).set("Test").create());
        Set<TestContext> checkContexts = new HashSet<>();
        for(TestContext ctx : contexts){
            checkContexts.add(ctx);
            checkContexts.add(ctx);
        }
        // Check we have 5 distinct hash codes...
        assertTrue(checkContexts.size() == 5);
    }

    @Test
    public void testToString() {
        TestContext ctx = new TestContext.Builder().set("Test").set(Integer.valueOf(1)).set(Long.valueOf(2)).create();
        assertNotNull(ctx.toString());
        System.out.println(ctx.toString());
        assertTrue(ctx.toString().contains("1"));
        assertTrue(ctx.toString().contains("2"));
        assertTrue(ctx.toString().contains("Test"));
        assertTrue(ctx.toString().contains("String"));
        assertTrue(ctx.toString().contains("Integer"));
        assertTrue(ctx.toString().contains("Long"));
        assertTrue(ctx.toString().contains("TestContext"));
    }

    private static class TestContext extends AbstractContext{

        /**
         * Private constructor, used by {@link javax.money.AbstractContext.AbstractBuilder}.
         *
         * @param builder the Builder.
         */
        protected TestContext(AbstractBuilder builder){
            super(builder);
        }

        private static final class Builder extends AbstractContext.AbstractBuilder<Builder>{
            @Override
            public TestContext create(){
                return new TestContext(this);
            }
        }

    }


}
