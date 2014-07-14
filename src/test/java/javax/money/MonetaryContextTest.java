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

import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

/**
 * Created by Anatole on 05.03.14.
 */
public class MonetaryContextTest{

    @Test
    public void testGetPrecision() throws Exception{
        MonetaryContext ctx = new MonetaryContext.Builder(MonetaryAmount.class).setPrecision(299).build();
        assertTrue(ctx.getPrecision() == 299);
    }

    @Test
    public void testIsFixedScale() throws Exception{
        MonetaryContext ctx = new MonetaryContext.Builder(MonetaryAmount.class).setFixedScale(true).build();
        assertTrue(ctx.isFixedScale());
    }

    @Test
    public void testGetMaxScale() throws Exception{
        MonetaryContext ctx = new MonetaryContext.Builder(MonetaryAmount.class).setMaxScale(122).build();
        assertTrue(ctx.getMaxScale() == 122);
    }

    @Test
    public void testGetAmountType() throws Exception{
        MonetaryContext ctx = new MonetaryContext.Builder(MonetaryAmount.class).setMaxScale(122).build();
        assertEquals(ctx.getAmountType(), MonetaryAmount.class);
        abstract class TestAmount implements MonetaryAmount{}
        ctx = new MonetaryContext.Builder(TestAmount.class).build();
        assertEquals(ctx.getAmountType(), TestAmount.class);
    }

    @Test
    public void testHashCode() throws Exception{
        List<MonetaryContext> contexts = new ArrayList<>();
        contexts.add(new MonetaryContext.Builder(MonetaryAmount.class).setMaxScale(122).build());
        contexts.add(new MonetaryContext.Builder(MonetaryAmount.class).setPrecision(299).build());
        contexts.add(new MonetaryContext.Builder(MonetaryAmount.class).setFixedScale(true).build());
        Set<Integer> hashCodes = new HashSet<>();
        contexts.forEach(ctx -> hashCodes.add(ctx.hashCode()));
        // Check we have 5 distinct hash codes...
        assertTrue(hashCodes.size() == 3);
    }

    @Test
    public void testEquals() throws Exception{
        List<MonetaryContext> contexts = new ArrayList<>();
        contexts.add(new MonetaryContext.Builder(MonetaryAmount.class).setMaxScale(122).build());
        contexts.add(new MonetaryContext.Builder(MonetaryAmount.class).setPrecision(299).build());
        contexts.add(new MonetaryContext.Builder(MonetaryAmount.class).setFixedScale(true).build());
        Set<MonetaryContext> checkContexts = new HashSet<>();
        for(MonetaryContext ctx : contexts){
            checkContexts.add(ctx);
            checkContexts.add(ctx);
        }
        // Check we have 5 distinct hash codes...
        assertTrue(checkContexts.size() == 3);
    }

    @Test
    public void testToString() throws Exception{
        abstract class TestAmount implements MonetaryAmount{}
        MonetaryContext ctx = new MonetaryContext.Builder(TestAmount.class)
                .setFixedScale(true).setMaxScale(111).setPrecision(200)
                .set("myKey", "myValue").set("TEST").build();
        assertNotNull(ctx.toString());
        System.out.println(ctx.toString());
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
