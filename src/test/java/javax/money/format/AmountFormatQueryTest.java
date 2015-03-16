/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import org.testng.annotations.Test;

import javax.money.MonetaryOperator;
import java.text.DecimalFormat;
import java.util.*;

import static org.testng.Assert.*;

/**
 * Tests for {@link AmountFormatQuery}.
 */
public class AmountFormatQueryTest {
    @Test
    public void testOf() throws Exception{
        AmountFormatQuery style = AmountFormatQuery.of(Locale.ENGLISH);
        assertNotNull(style);
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
        assertNotNull(df);
    }

    @Test
    public void testToBuilder() throws Exception{
        AmountFormatQuery style = AmountFormatQueryBuilder.of(Locale.ENGLISH).build();
        AmountFormatQueryBuilder builder = style.toBuilder();
        assertNotNull(builder);
        assertEquals(style, builder.build());
    }

    @Test
    public void testHashCode(){
        MonetaryOperator op = value -> value.multiply(2.0d);
        assertNotNull(op);
        List<AmountFormatContext> contexts = new ArrayList<>();
        contexts.add(AmountFormatContextBuilder.of(Locale.GERMAN).build());
        contexts.add(AmountFormatContextBuilder.of(Locale.ENGLISH).build());
        Set<Integer> hashCodes = new HashSet<>();
        contexts.forEach(amountFormatContext -> hashCodes.add(amountFormatContext.hashCode()));
        // Check we have 5 distinct hash codes...
        assertTrue(hashCodes.size() == 2);
    }

    @Test
    public void testEquals(){
        MonetaryOperator op = value -> value.multiply(2.0d);
        assertNotNull(op);
        List<AmountFormatContext> contexts = new ArrayList<>();
        contexts.add(AmountFormatContextBuilder.of(Locale.ENGLISH).build());
        contexts.add(AmountFormatContextBuilder.of(Locale.GERMAN).build());
        Set<AmountFormatContext> checkContexts = new HashSet<>();
        contexts.forEach(checkContexts::add);
        // Check we have 5 distinct hash codes...
        assertTrue(checkContexts.size() == 2);
    }

    @Test
    public void testToString() throws Exception{
        AmountFormatContext style =
                AmountFormatContextBuilder.of(Locale.GERMAN).set("groupSizes", new int[]{1, 2, 3, 4})
                        .set("currencyStyle", "NUMERIC_CODE").set("pattern", "###").build();
        String toString = style.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("DE"));
        assertTrue(toString.contains("java.util.Locale"));
        assertTrue(toString.contains("groupSizes"));
        assertTrue(toString.contains("###"));
        assertTrue(toString.contains("NUMERIC_CODE"));
        assertTrue(toString.contains("currencyStyle"));
        assertTrue(toString.contains("AmountFormatContext"));
    }
}
