/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package javax.money;

import org.testng.annotations.Test;

import java.util.Locale;

import static org.testng.Assert.*;

/**
 * Created by Anatole on 05.03.14.
 */
public class UnknownCurrencyExceptionTest{
    @Test
    public void testGetCurrencyCode() throws Exception{
        UnknownCurrencyException e = new UnknownCurrencyException("GGG");
        assertEquals("GGG", e.getCurrencyCode());
        assertNull(e.getLocale());
        assertTrue(e.toString().contains("GGG"));
        assertTrue(e.toString().contains("UnknownCurrencyException"));
    }

    @Test
    public void testGetLocale() throws Exception{
        UnknownCurrencyException e = new UnknownCurrencyException(Locale.CANADA_FRENCH);
        assertEquals(Locale.CANADA_FRENCH, e.getLocale());
        assertNull(e.getCurrencyCode());
        System.out.println(e);
        assertTrue(e.toString().contains(Locale.CANADA_FRENCH.toString()));
        assertTrue(e.toString().contains("UnknownCurrencyException"));
    }

}
