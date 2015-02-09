package javax.money;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RoundingContextTest {

    @Test
    public void testGetRoundingName() throws Exception {
        RoundingContext ctx = RoundingContextBuilder.of("prov", "r1").build();
        assertNotNull(ctx);
        assertEquals(ctx.getRoundingName(), "r1");
    }

    @Test
    public void testGetCurrency() throws Exception {
        CurrencyUnit cu = TestCurrency.of("CHF");
        RoundingContext ctx = RoundingContextBuilder.of("prov", "r1").
                setCurrency(cu).build();
        assertNotNull(ctx);
        assertEquals(ctx.getCurrency(), cu);
    }

    @Test
    public void testToBuilder() throws Exception {
        CurrencyUnit cu = TestCurrency.of("CHF");
        RoundingContext ctx = RoundingContextBuilder.of("prov1", "rounding2")
                .setCurrency(cu).build();
        RoundingContextBuilder b = ctx.toBuilder();
        assertNotNull(b);
        assertEquals(b.build(), ctx);
    }
}