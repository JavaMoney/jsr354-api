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
        AmountStyle style = AmountStyle.of(Locale.ENGLISH);
        assertNotNull(style);
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
    }

    @Test
    public void testGetAvailableLocales() throws Exception{
        Set<Locale> locales = AmountStyle.getAvailableLocales();
        assertTrue(locales.contains(Locale.ENGLISH));
    }

    @Test
    public void testGetLocale() throws Exception{
        AmountStyle style = AmountStyle.of(Locale.ENGLISH);
        assertNotNull(style.getLocale());
        assertEquals(Locale.ENGLISH, style.getLocale());
    }

    @Test
    public void testGetPattern() throws Exception{
        AmountStyle style = new AmountStyle.Builder(Locale.ENGLISH).setPattern("###.##").create();
        assertNotNull(style.getPattern());
        assertEquals("###.##", style.getPattern());
    }

    @Test
    public void testGetLocalizedPattern() throws Exception{
        AmountStyle style = new AmountStyle.Builder(Locale.ENGLISH).setPattern("###.##").create();
        assertNotNull(style.getPattern());
        assertEquals("###.##", style.getLocalizedPattern());
    }

    @Test
    public void testGetCurrencyStyle() throws Exception{
        AmountStyle style = new AmountStyle.Builder(Locale.ENGLISH).setPattern("###.##").setCurrencyStyle(CurrencyStyle.NUMERIC_CODE).create();
        assertNotNull(style.getCurrencyStyle());
        assertEquals(CurrencyStyle.NUMERIC_CODE, style.getCurrencyStyle());
    }

    @Test
    public void testGetSymbols() throws Exception{
        AmountFormatSymbols symbols = AmountFormatSymbols.of(Locale.ENGLISH);
        AmountStyle style = new AmountStyle.Builder(Locale.ENGLISH).setPattern("###.##").setSymbols(symbols).create();
        assertNotNull(style.getSymbols());
        assertEquals(symbols, style.getSymbols());
    }

    @Test
    public void testGetDisplayConversion() throws Exception{
        MonetaryOperator op = new MonetaryOperator(){
            @Override
            public <T extends MonetaryAmount> T apply(T value){
                return (T) value.multiply(2.0d);
            }
        };
        AmountStyle style = new AmountStyle.Builder(Locale.ENGLISH).setPattern("###.##").setDisplayConversion(op).create();
        assertNotNull(style.getDisplayConversion());
        assertTrue(style.getDisplayConversion() == op);
    }

    @Test
    public void testGetParseConversion() throws Exception{
        MonetaryOperator op = new MonetaryOperator(){
            @Override
            public <T extends MonetaryAmount> T apply(T value){
                return (T) value.divide(2d);
            }
        };
        AmountStyle style = new AmountStyle.Builder(Locale.ENGLISH).setPattern("###.##").setParseConversion(op).create();
        assertNotNull(style.getParseConversion());
        assertTrue(style.getParseConversion() == op);
    }

    @Test
    public void testGetGroupingSizes() throws Exception{
        AmountStyle style = new AmountStyle.Builder(Locale.ENGLISH).setPattern("###.##").setGroupingSizes(1, 2, 3, 4).create();
        assertNotNull(style.getGroupingSizes());
        assertTrue(style.getGroupingSizes()[0] == 1);
        assertTrue(style.getGroupingSizes()[1] == 2);
        assertTrue(style.getGroupingSizes()[2] == 3);
        assertTrue(style.getGroupingSizes()[3] == 4);
    }

    @Test
    public void testToBuilder() throws Exception{
        AmountStyle style = new AmountStyle.Builder(Locale.ENGLISH).setPattern("###.##").setGroupingSizes(1, 2, 3, 4).create();
        AmountStyle.Builder builder = style.toBuilder();
        assertNotNull(builder);
        assertEquals(style, builder.create());
    }

    @Test
    public void testHashCode(){
        MonetaryOperator op = new MonetaryOperator(){
            @Override
            public <T extends MonetaryAmount> T apply(T value){
                return (T) value.multiply(2.0d);
            }
        };
        List<AmountStyle> contexts = new ArrayList<>();
        contexts.add(new AmountStyle.Builder(Locale.ENGLISH).setPattern("###.##").setGroupingSizes(1, 2).setParseConversion(op).create());
        contexts.add(
                new AmountStyle.Builder(Locale.ENGLISH).setPattern("###_##").setGroupingSizes(1, 2, 3, 4).setDisplayConversion(op).create());
        contexts.add(new AmountStyle.Builder(Locale.ENGLISH).setPattern("###,##").setParseConversion(op).setDisplayConversion(op).create());
        contexts.add(new AmountStyle.Builder(Locale.ENGLISH).setPattern("#.##").setParseConversion(op).create());
        contexts.add(new AmountStyle.Builder(Locale.ENGLISH).setPattern("##.##").setDisplayConversion(op).create());
        Set<Integer> hashCodes = new HashSet<>();
        for(AmountStyle ctx : contexts){
            hashCodes.add(ctx.hashCode());
        }
        // Check we have 5 distinct hash codes...
        assertTrue(hashCodes.size() > 2);
    }

    @Test
    public void testEquals(){
        MonetaryOperator op = new MonetaryOperator(){
            @Override
            public <T extends MonetaryAmount> T apply(T value){
                return (T) value.multiply(2.0d);
            }
        };
        List<AmountStyle> contexts = new ArrayList<>();
        contexts.add(new AmountStyle.Builder(Locale.ENGLISH).setPattern("###.##").setGroupingSizes(1, 2).setParseConversion(op).create());
        contexts.add(
                new AmountStyle.Builder(Locale.ENGLISH).setPattern("###_##").setGroupingSizes(1, 2, 3, 4).setDisplayConversion(op).create());
        contexts.add(new AmountStyle.Builder(Locale.ENGLISH).setPattern("###,##").setParseConversion(op).setDisplayConversion(op).create());
        contexts.add(new AmountStyle.Builder(Locale.ENGLISH).setPattern("#.##").setParseConversion(op).create());
        contexts.add(new AmountStyle.Builder(Locale.ENGLISH).setPattern("##.##").setDisplayConversion(op).create());
        Set<AmountStyle> checkContexts = new HashSet<>();
        for(AmountStyle ctx : contexts){
            checkContexts.add(ctx);
            checkContexts.add(ctx);
        }
        // Check we have 5 distinct hash codes...
        assertTrue(checkContexts.size() == 5);
    }

    @Test
    public void testToString() throws Exception{
        AmountStyle style = new AmountStyle.Builder(Locale.ENGLISH).setGroupingSizes(1, 2, 3, 4)
                .setCurrencyStyle(CurrencyStyle.NUMERIC_CODE).setPattern("###").create();
        String toString = style.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("DE"));
        assertTrue(toString.contains("locale"));
        assertTrue(toString.contains("groupSizes"));
        assertTrue(toString.contains("1, 2, 3, 4"));
        assertTrue(toString.contains("###"));
        assertTrue(toString.contains("NUMERIC_CODE"));
        assertTrue(toString.contains("currencyStyle"));
        assertTrue(toString.contains("AmountStyle"));
    }
}
