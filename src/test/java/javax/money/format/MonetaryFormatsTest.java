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

import javax.money.MonetaryException;
import java.util.Locale;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Tests for {@link MonetaryFormats}.
 *
 * @author Anatole Tresch
 */
public class MonetaryFormatsTest{

    @Test
    public void testGetAmountFormatLocale(){
        MonetaryAmountFormat fmt = MonetaryFormats.getAmountFormat(Locale.ENGLISH);
        assertNotNull(fmt);
        assertEquals(fmt.getClass(), TestAmountFormatProvider.TestFormat.class);
    }

    @Test(expectedExceptions = MonetaryException.class)
    public void testGetAmountFormatLocale_Invalid(){
        MonetaryFormats.getAmountFormat(new Locale("foo", "bar"));
    }

    @Test
    public void testGetAmountFormatStyle(){
        AmountFormatQuery s = AmountFormatQuery.of(Locale.ENGLISH);
        MonetaryAmountFormat fmt = MonetaryFormats.getAmountFormat(s);
        assertNotNull(fmt);
        assertEquals(fmt.getClass(), TestAmountFormatProvider.TestFormat.class);
        AmountFormatContext ctx = new AmountFormatContext.Builder(Locale.ENGLISH).importContext(s).build();
        assertEquals(ctx, fmt.getAmountFormatContext());
    }

    @Test
    public void testGetAvailableLocales(){
        Set<Locale> locales = MonetaryFormats.getAvailableLocales();
        assertNotNull(locales);
        assertTrue(locales.size() == 1);
        assertTrue(locales.contains(Locale.ENGLISH));
    }

}
