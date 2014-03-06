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

import javax.money.MonetaryException;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Anatole on 05.03.14.
 */
public class AmountFormatSymbolsTest{
    @Test
    public void testOf() throws Exception{
        assertNotNull(AmountFormatSymbols.of(Locale.ENGLISH));
    }

    @Test(expected = MonetaryException.class)
    public void testOf_Invalid(){
        AmountFormatSymbols.of(Locale.CHINESE);
    }

    @Test
    public void testGetAvailableLocales() throws Exception{
        Set<Locale> locales = AmountFormatSymbols.getAvailableLocales();
        assertNotNull(locales);
        assertTrue(locales.size() == 1);
    }

    @Test
    public void testGetLocale() throws Exception{
        assertEquals(AmountFormatSymbols.of(Locale.ENGLISH).getLocale(),Locale.ENGLISH);
    }

    @Test
    public void testGetZeroDigit() throws Exception{
        AmountFormatSymbols syms = new AmountFormatSymbols.Builder(Locale.ENGLISH).setZeroDigit('-').create();
        assertTrue('-' == syms.getZeroDigit());
    }

    @Test
    public void testGetGroupingSeparators() throws Exception{
        AmountFormatSymbols syms = new AmountFormatSymbols.Builder(Locale.ENGLISH).setGroupingSeparator('a','b','c').create();
        assertNotNull(syms.getGroupingSeparators());
        assertTrue(syms.getGroupingSeparators().length==3);
        assertTrue('a' == syms.getGroupingSeparators()[0]);
        assertTrue('b' == syms.getGroupingSeparators()[1]);
        assertTrue('c' == syms.getGroupingSeparators()[2]);
    }

    @Test
    public void testGetDecimalSeparator() throws Exception{
        AmountFormatSymbols syms = new AmountFormatSymbols.Builder(Locale.ENGLISH).setDecimalSeparator('d').create();
        assertTrue('d' == syms.getDecimalSeparator());
    }

    @Test
    public void testGetDigit() throws Exception{
        AmountFormatSymbols syms = new AmountFormatSymbols.Builder(Locale.ENGLISH).setDigit('D').create();
        assertTrue('D' == syms.getDigit());
    }

    @Test
    public void testGetPatternSeparator() throws Exception{
        AmountFormatSymbols syms = new AmountFormatSymbols.Builder(Locale.ENGLISH).setPatternSeparator(':').create();
        assertTrue(':' == syms.getPatternSeparator());
    }

    @Test
    public void testGetInfinity() throws Exception{
        AmountFormatSymbols syms = new AmountFormatSymbols.Builder(Locale.ENGLISH).setInfinity("unbound").create();
        assertTrue("unbound".equals(syms.getInfinity()));
    }

    @Test
    public void testGetMinusSign() throws Exception{
        AmountFormatSymbols syms = new AmountFormatSymbols.Builder(Locale.ENGLISH).setMinusSign('=').create();
        assertTrue('=' == syms.getMinusSign());
    }

    @Test
    public void testGetExponentSeparator() throws Exception{
        AmountFormatSymbols syms = new AmountFormatSymbols.Builder(Locale.ENGLISH).setExponentialSeparator("exp").create();
        assertEquals("exp", syms.getExponentSeparator());
    }

    @Test
    public void testToBuilder() throws Exception{
        AmountFormatSymbols syms = new AmountFormatSymbols.Builder(Locale.ENGLISH).setMinusSign('=').create();
        assertEquals(syms.getLocale(), syms.toBuilder().create().getLocale());
        assertEquals(syms.getMinusSign(), syms.toBuilder().create().getMinusSign());
        assertEquals(syms.toString(), syms.toBuilder().create().toString());
    }

    @Test
    public void testGetFormatSymbols(){
        AmountFormatSymbols symbols = AmountFormatSymbols.of(Locale.ENGLISH);
        assertNotNull(symbols);
        assertEquals(symbols.getExponentSeparator(), "test");
    }


}
