package javax.money.format;

import org.testng.annotations.Test;

import javax.money.DummyAmountBuilder;
import javax.money.MonetaryAmountFactory;

import java.util.Locale;

import static org.testng.Assert.*;

public class AmountFormatContextTest {


    @Test
    public void testGetFormatName() throws Exception {
        AmountFormatContext ctx = AmountFormatContextBuilder.of("testest").build();
        assertEquals(ctx.getFormatName(), "testest");
    }

    @Test
    public void testGetLocale() throws Exception {
        AmountFormatContext ctx = AmountFormatContextBuilder.of(Locale.CANADA).build();
        assertEquals(ctx.getLocale(), Locale.CANADA);
    }

    @Test
    public void testGetParseFactory() throws Exception {
        @SuppressWarnings("rawtypes")
		MonetaryAmountFactory f = new DummyAmountBuilder();
        AmountFormatContext ctx = AmountFormatContextBuilder.of("blbl2").
                setMonetaryAmountFactory(f).build();
        assertEquals(ctx.getParseFactory(), f);
    }

    @Test
    public void testToBuilder() throws Exception {
        AmountFormatContext ctx = AmountFormatContextBuilder.of(Locale.CANADA).build();
        AmountFormatContextBuilder b = ctx.toBuilder();
        assertNotNull(b);
        assertEquals(b.build(), ctx);
    }
}