package javax.money.convert;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Anatole on 05.03.14.
 */
public class ConversionContextTest{
    @Test
    public void testGetRateType() throws Exception{
        ConversionContext ctx = new ConversionContext.Builder().setRateType(RateType.DEFERRED).create();
        assertEquals(RateType.DEFERRED, ctx.getRateType());
    }

    @Test
    public void testGetTimestamp() throws Exception{
        ConversionContext ctx = new ConversionContext.Builder().setTimestamp(222L).create();
        assertEquals(Long.valueOf(222L), ctx.getTimestamp());
    }

    @Test
    public void testGetTimestamp1() throws Exception{
        Date date = new Date();
        ConversionContext ctx = new ConversionContext.Builder().setTimestamp(date).create();
        assertEquals(date, ctx.getTimestamp(Date.class));
    }

    @Test
    public void testGetValidTo() throws Exception{
        ConversionContext ctx = new ConversionContext.Builder().setValidTo(222L).create();
        assertEquals(Long.valueOf(222L), ctx.getValidTo());
    }

    @Test
    public void testGetValidTo1() throws Exception{
        Date date = new Date();
        ConversionContext ctx = new ConversionContext.Builder().setValidTo(date).create();
        assertEquals(date, ctx.getValidTo(Date.class));
    }

    @Test
    public void testIsValid() throws Exception{
        ConversionContext ctx = new ConversionContext.Builder().setValidTo(222L).create();
        assertFalse(ctx.isValid(223L));
        assertTrue(ctx.isValid(221L));
        assertTrue(ctx.isValid(222L));
    }

    @Test
    public void testGetProvider() throws Exception{
        ConversionContext ctx = new ConversionContext.Builder().setProvider("myprov").create();
        assertEquals("myprov", ctx.getProvider());
    }

    @Test
    public void testToBuilder() throws Exception{
        ConversionContext ctx = new ConversionContext.Builder().setProvider("myprov").create();
        assertEquals(ctx, ctx.toBuilder().create());
    }

    @Test
    public void testOf() throws Exception{
        ConversionContext ctx = ConversionContext.of();
        ConversionContext ctx2 = ConversionContext.of();
        assertEquals(ctx, ctx2);
    }

    @Test
    public void testOf1() throws Exception{
        ConversionContext ctx = ConversionContext.of(RateType.REALTIME);
        ConversionContext ctx2 = ConversionContext.of(RateType.REALTIME);
        assertEquals(ctx, ctx2);
        assertEquals(RateType.REALTIME, ctx.getRateType());
    }

    @Test
    public void testOf2() throws Exception{
        ConversionContext ctx = ConversionContext.of("prov", RateType.REALTIME);
        ConversionContext ctx2 = ConversionContext.of("prov", RateType.REALTIME);
        assertEquals(ctx, ctx2);
        assertEquals(RateType.REALTIME, ctx.getRateType());
        assertEquals("prov", ctx.getProvider());
    }

    @Test
    public void testOf3() throws Exception{
        ConversionContext ctx = ConversionContext.of("prov", RateType.REALTIME, 222L);
        ConversionContext ctx2 = ConversionContext.of("prov", RateType.REALTIME, 222L);
        assertEquals(ctx, ctx2);
        assertEquals(RateType.REALTIME, ctx.getRateType());
        assertEquals("prov", ctx.getProvider());
        assertEquals(Long.valueOf(222L), ctx.getTimestamp());
    }
}
