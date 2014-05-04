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

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Anatole on 05.03.14.
 */
public class MonetaryContextTest{
    @Test
    public void testGetPrecision() throws Exception{
        MonetaryContext ctx = new MonetaryContext.Builder().setPrecision(299).build();
        assertTrue(ctx.getPrecision() == 299);
    }

    @Test
    public void testIsFixedScale() throws Exception{
        MonetaryContext ctx = new MonetaryContext.Builder().setFixedScale(true).build();
        assertTrue(ctx.isFixedScale());
    }

    @Test
    public void testGetMaxScale() throws Exception{
        MonetaryContext ctx = new MonetaryContext.Builder().setMaxScale(122).build();
        assertTrue(ctx.getMaxScale() == 122);
    }

    @Test
    public void testGetAmountType() throws Exception{
        MonetaryContext ctx = new MonetaryContext.Builder().setMaxScale(122).build();
        assertEquals(ctx.getAmountType(), MonetaryAmount.class);
        abstract class TestAmount implements MonetaryAmount{}
        ctx = new MonetaryContext.Builder().setAmountType(TestAmount.class).build();
        assertEquals(ctx.getAmountType(), TestAmount.class);
    }

    @Test
    public void testGetAmountFlavor() throws Exception{
        MonetaryContext ctx =
                new MonetaryContext.Builder().setFlavor(AmountFlavor.PERFORMANCE).build();
        assertEquals(AmountFlavor.PERFORMANCE, ctx.getAmountFlavor());
        ctx = new MonetaryContext.Builder().setFlavor(AmountFlavor.PRECISION).build();
        assertEquals(AmountFlavor.PRECISION, ctx.getAmountFlavor());
        ctx = new MonetaryContext.Builder().setFlavor(AmountFlavor.UNDEFINED).build();
        assertEquals(AmountFlavor.UNDEFINED, ctx.getAmountFlavor());
        ctx = new MonetaryContext.Builder().build();
        assertEquals(AmountFlavor.UNDEFINED, ctx.getAmountFlavor());
    }

    @Test
    public void testHashCode() throws Exception{
        List<MonetaryContext> contexts = new ArrayList<>();
        contexts.add(new MonetaryContext.Builder().setFlavor(AmountFlavor.PERFORMANCE).build());
        contexts.add(new MonetaryContext.Builder().setFlavor(AmountFlavor.PRECISION).build());
        contexts.add(new MonetaryContext.Builder().setMaxScale(122).build());
        contexts.add(new MonetaryContext.Builder().setPrecision(299).build());
        contexts.add(new MonetaryContext.Builder().setFixedScale(true).build());
        Set<Integer> hashCodes = new HashSet<>();
        for(MonetaryContext ctx : contexts){
            hashCodes.add(ctx.hashCode());
        }
        // Check we have 5 distinct hash codes...
        assertTrue(hashCodes.size() == 5);
    }

    @Test
    public void testEquals() throws Exception{
        List<MonetaryContext> contexts = new ArrayList<>();
        contexts.add(new MonetaryContext.Builder().setFlavor(AmountFlavor.PERFORMANCE).build());
        contexts.add(new MonetaryContext.Builder().setFlavor(AmountFlavor.PRECISION).build());
        contexts.add(new MonetaryContext.Builder().setMaxScale(122).build());
        contexts.add(new MonetaryContext.Builder().setPrecision(299).build());
        contexts.add(new MonetaryContext.Builder().setFixedScale(true).build());
        Set<MonetaryContext> checkContexts = new HashSet<>();
        for(MonetaryContext ctx : contexts){
            checkContexts.add(ctx);
            checkContexts.add(ctx);
        }
        // Check we have 5 distinct hash codes...
        assertTrue(checkContexts.size() == 5);
    }

    @Test
    public void testFrom() throws Exception{
        MonetaryContext rootCtx =
                new MonetaryContext.Builder().setFlavor(AmountFlavor.PERFORMANCE).build();
        MonetaryContext ctx = MonetaryContext.from(rootCtx, rootCtx.getAmountType());
        assertEquals(ctx, rootCtx);
        abstract class TestAmount implements MonetaryAmount{}
        ctx = MonetaryContext.from(rootCtx, TestAmount.class);
        assertFalse(ctx.equals(rootCtx));
    }

    @Test
    public void testToString() throws Exception{
        abstract class TestAmount implements MonetaryAmount{}
        MonetaryContext ctx = new MonetaryContext.Builder().setFlavor(AmountFlavor.PERFORMANCE)
                .setAmountType(TestAmount.class).setFixedScale(true).setMaxScale(111).setPrecision(200)
                .setAttribute("myKey", "myValue").setObject("TEST").build();
        assertNotNull(ctx.toString());
        System.out.println(ctx.toString());
        assertTrue(ctx.toString().contains("PERFORMANCE"));
        assertTrue(ctx.toString().contains("flavor"));
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
