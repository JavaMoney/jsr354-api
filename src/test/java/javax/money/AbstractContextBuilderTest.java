/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

@SuppressWarnings("rawtypes")
public class AbstractContextBuilderTest {


	@SuppressWarnings("unchecked")
	private AbstractContextBuilder<AbstractContextBuilder, AbstractContext> createBuilder() {
        //noinspection unchecked
        return new AbstractContextBuilder() {
            @Override
            public AbstractContext build() {
                return new AbstractContext(this) {
					private static final long serialVersionUID = -5720498720434069240L;
                };
            }
        };
    }

    @Test
    public void testImportContext() throws Exception {
        AbstractContextBuilder b = createBuilder();
        b.set("myKey", BigDecimal.valueOf(10.0d));
        AbstractContext ctx = b.build();

        AbstractContextBuilder b2 = createBuilder();
        b2.importContext(ctx);
        AbstractContext ctx2 = b2.build();

        assertEquals(ctx, ctx2);
        assertEquals(ctx.get("myKey", BigDecimal.class), BigDecimal.valueOf(10.0d));
        assertEquals(ctx2.get("myKey", BigDecimal.class), BigDecimal.valueOf(10.0d));
    }

    @Test
    public void testImportContext1() throws Exception {
        AbstractContextBuilder b = createBuilder();
        b.set("myKey", BigDecimal.valueOf(5.0d));
        AbstractContext ctx = b.build();

        AbstractContextBuilder b2 = createBuilder();
        b2.set("myKey", BigDecimal.valueOf(10.0d));
        b2.importContext(ctx);
        AbstractContext ctx2 = b2.build();

        assertNotEquals(ctx, ctx2);
        assertEquals(ctx.get("myKey", BigDecimal.class), BigDecimal.valueOf(5.0d));
        assertEquals(ctx2.get("myKey", BigDecimal.class), BigDecimal.valueOf(10.0d));
    }

    @Test
    public void testSet_Int() throws Exception {
        AbstractContextBuilder b = createBuilder();
        b.set("myKey", 11);
        AbstractContext ctx = b.build();
        assertEquals(ctx.getInt("myKey").intValue(), 11);
    }

    @Test
    public void testSet_Boolean() throws Exception {
        AbstractContextBuilder b = createBuilder();
        b.set("myKey", true);
        AbstractContext ctx = b.build();
        assertEquals(ctx.getBoolean("myKey").booleanValue(), true);
    }

    @Test
    public void testSet_Long() throws Exception {
        AbstractContextBuilder b = createBuilder();
        b.set("myKey", 12345L);
        AbstractContext ctx = b.build();
        assertEquals(ctx.getLong("myKey").longValue(), 12345L);
    }

    @Test
    public void testSet_Float() throws Exception {
        AbstractContextBuilder b = createBuilder();
        b.set("myKey", 1.5f);
        AbstractContext ctx = b.build();
        assertEquals(ctx.getFloat("myKey").floatValue(), 1.5f, 0.0f);
    }

    @Test
    public void testSet_Double() throws Exception {
        AbstractContextBuilder b = createBuilder();
        b.set("myKey", 1.5d);
        AbstractContext ctx = b.build();
        assertEquals(ctx.getDouble("myKey"), 1.5d, 0.0d);
    }

    @Test
    public void testSet_String() throws Exception {
        AbstractContextBuilder b = createBuilder();
        b.set("myKey", "yes");
        AbstractContext ctx = b.build();
        assertEquals(ctx.getText("myKey"), "yes");
    }

    @Test
    public void testSet7_Any() throws Exception {
        AbstractContextBuilder b = createBuilder();
        b.set("myKey", BigDecimal.valueOf(10.0d));
        AbstractContext ctx = b.build();
        assertEquals(ctx.get("myKey", BigDecimal.class), BigDecimal.valueOf(10.0d));
    }

    @Test
    public void testSet_Any_Explicit() throws Exception {
        AbstractContextBuilder b = createBuilder();
        b.set("myKey", BigDecimal.valueOf(10.0d));
        AbstractContext ctx = b.build();
        assertEquals(ctx.get("myKey", Number.class), BigDecimal.valueOf(10.0d));
    }

    @Test
    public void testSet_Object() throws Exception {
        AbstractContextBuilder b = createBuilder();
        b.set(BigDecimal.valueOf(10.0d));
        AbstractContext ctx = b.build();
        assertEquals(ctx.get(BigDecimal.class), BigDecimal.valueOf(10.0d));
    }

    @Test
    public void testSetCollection() throws Exception {
        AbstractContextBuilder b = createBuilder();
        List<Number> list = new ArrayList<>();
        list.add(BigDecimal.ONE);
        b.set("myKey", list);
        AbstractContext ctx = b.build();
        assertEquals(ctx.get("myKey", List.class), list);
    }

    @Test
    public void testSetList() throws Exception {
        AbstractContextBuilder b = createBuilder();
        List<Number> list = new ArrayList<>();
        list.add(BigDecimal.ONE);
        b.set("myKey", list);
        AbstractContext ctx = b.build();
        assertEquals(ctx.get("myKey", List.class), list);
    }

    @Test
    public void testSetMap() throws Exception {
        AbstractContextBuilder b = createBuilder();
        List<Number> list = new ArrayList<>();
        list.add(BigDecimal.ONE);
        b.set("myKey", list);
        AbstractContext ctx = b.build();
        assertEquals(ctx.get("myKey", List.class), list);
    }

    @Test
    public void testSetSet() throws Exception {
        AbstractContextBuilder b = createBuilder();
        Set<Number> set = new HashSet<>();
        set.add(BigDecimal.ONE);
        b.set("myKey", set);
        AbstractContext ctx = b.build();
        assertEquals(ctx.get("myKey", Set.class), set);
    }

    @Test
    public void testRemoveAttributes() throws Exception {
        AbstractContextBuilder b = createBuilder();
        Set<Number> set = new HashSet<>();
        set.add(BigDecimal.ONE);
        b.set("myKey", "test");
        AbstractContext ctx = b.build();
        assertEquals(ctx.getText("myKey"), "test");
        b.removeAttributes("gugus");
        assertEquals(ctx.getText("myKey"), "test");
        b.removeAttributes("myKey");
        ctx = b.build();
        assertEquals(ctx.getText("myKey"), null);
    }

    @Test
    public void testToString() throws Exception {
        AbstractContextBuilder b = createBuilder();
        Set<Number> set = new HashSet<>();
        set.add(BigDecimal.ONE);
        b.set("myKey", "test");
        String toString = b.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("myKey"));
        assertTrue(toString.contains("test"));
    }
}