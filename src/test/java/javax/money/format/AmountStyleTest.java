/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import org.junit.Test;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;
import java.text.DecimalFormat;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Anatole on 05.03.14.
 */
public class AmountStyleTest{
    @Test
    public void testOf() throws Exception{
        AmountFormatContext style = AmountFormatContext.of(Locale.ENGLISH);
        assertNotNull(style);
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
    }


    @Test
    public void testToBuilder() throws Exception{
        AmountFormatContext style = new AmountFormatContext.Builder(Locale.ENGLISH).build();
        AmountFormatContext.Builder builder = style.toBuilder();
        assertNotNull(builder);
        assertEquals(style, builder.build());
    }

    @Test
    public void testHashCode(){
        MonetaryOperator op = value -> value.multiply(2.0d);
        List<AmountFormatContext> contexts = new ArrayList<>();
        contexts.add(new AmountFormatContext.Builder(Locale.GERMAN).build());
        contexts.add(new AmountFormatContext.Builder(Locale.ENGLISH).build());
        Set<Integer> hashCodes = new HashSet<>();
        contexts.forEach(amountFormatContext -> hashCodes.add(amountFormatContext.hashCode()));
        // Check we have 5 distinct hash codes...
        assertTrue(hashCodes.size() == 2);
    }

    @Test
    public void testEquals(){
        MonetaryOperator op = value -> value.multiply(2.0d);
        List<AmountFormatContext> contexts = new ArrayList<>();
        contexts.add(new AmountFormatContext.Builder(Locale.ENGLISH).build());
        contexts.add(new AmountFormatContext.Builder(Locale.GERMAN).build());
        Set<AmountFormatContext> checkContexts = new HashSet<>();
        contexts.forEach(checkContexts::add);
        // Check we have 5 distinct hash codes...
        assertTrue(checkContexts.size() == 2);
    }

    @Test
    public void testToString() throws Exception{
        AmountFormatContext style = new AmountFormatContext.Builder(Locale.GERMAN).setAttribute("groupSizes", new int[]{1, 2, 3, 4})
                .setAttribute("currencyStyle", "NUMERIC_CODE").setAttribute("pattern", "###").build();
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
