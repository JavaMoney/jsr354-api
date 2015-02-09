package javax.money;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CurrencyContextBuilderTest {

    @Test
    public void testOf() throws Exception {
        CurrencyContext ctx = CurrencyContextBuilder.of("prov").build();
        assertNotNull(ctx);
        assertEquals(ctx.getProviderName(), "prov");
        CurrencyContext ctx2 = CurrencyContextBuilder.of(ctx).build();
        assertNotNull(ctx2);
        assertEquals(ctx2.getProviderName(), "prov");
    }
}