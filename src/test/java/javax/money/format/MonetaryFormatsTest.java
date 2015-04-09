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
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Tests for {@link MonetaryFormats}.
 *
 * @author Anatole Tresch
 */
public class MonetaryFormatsTest {

    @Test
    public void testGetAmountFormatLocale() {
        MonetaryAmountFormat fmt = MonetaryFormats.getAmountFormat(Locale.ENGLISH);
        assertNotNull(fmt);
        assertEquals(fmt.getClass(), TestAmountFormatProvider.TestFormat.class);
    }

    @Test(expectedExceptions = MonetaryException.class)
    public void testGetAmountFormatLocale_Invalid() {
        MonetaryFormats.getAmountFormat(new Locale("foo", "bar"));
    }

    @Test
    public void testGetAvailableLocales() {
        Set<Locale> locales = MonetaryFormats.getAvailableLocales();
        assertNotNull(locales);
        assertTrue(locales.size() == 1);
        assertTrue(locales.contains(Locale.ENGLISH));
    }

    @Test
    public void testIsAvailable_Locale_Providers() {
        assertTrue(MonetaryFormats.isAvailable(Locale.ENGLISH));
        assertTrue(MonetaryFormats.isAvailable(Locale.ENGLISH, "Test"));
        assertTrue(MonetaryFormats.isAvailable(Locale.CANADA));
        assertFalse(MonetaryFormats.isAvailable(Locale.ENGLISH, "foo"));
        assertFalse(MonetaryFormats.isAvailable(Locale.CANADA, "foo"));
    }

    @Test
    public void testIsAvailable_Query() {
        assertTrue(MonetaryFormats.isAvailable(AmountFormatQueryBuilder.of("Test")
                .setProviderName("TestAmountFormatProvider").build()));
        assertTrue(MonetaryFormats.isAvailable(AmountFormatQueryBuilder.of("Test")
                .build()));
        assertFalse(MonetaryFormats.isAvailable(AmountFormatQueryBuilder.of("Test")
                .setProviderName("foo").build()));
    }

    @Test
    public void testGetAmountFormats_Query() {
        Collection<MonetaryAmountFormat> formats1 = MonetaryFormats.getAmountFormats(AmountFormatQueryBuilder.of("Test")
                .setProviderName("TestAmountFormatProvider").build());
        assertNotNull(formats1);
        assertEquals(formats1.size(), 1);
        Collection<MonetaryAmountFormat> formats2 = MonetaryFormats.getAmountFormats(AmountFormatQueryBuilder.of("Test")
                .build());
        assertNotNull(formats2);
        assertEquals(formats2.size(), 1);
        Collection<MonetaryAmountFormat> formats3 = MonetaryFormats.getAmountFormats(AmountFormatQueryBuilder.of("Test")
                .setProviderName("foo").build());
        assertNotNull(formats3);
        assertEquals(formats3.size(), 0);
    }

    @Test
    public void testGeAmountFormat_Name_Providers() {
        MonetaryAmountFormat f = MonetaryFormats.getAmountFormat("Test", "TestAmountFormatProvider");
        assertNotNull(f);
        f = MonetaryFormats.getAmountFormat("Test");
        assertNotNull(f);
        try {
            f = MonetaryFormats.getAmountFormat("Test", "foo");
            assertNotNull(f);
        } catch (MonetaryException e) {
            // OK
        }
    }

    @Test
    public void testGetProviderNames() {
        Collection<String> provs = MonetaryFormats.getFormatProviderNames();
        assertNotNull(provs);
        assertTrue(provs.contains("TestAmountFormatProvider"));
    }

    @Test
    public void testGetDefaultProviderChain() {
        List<String> provs = MonetaryFormats.getDefaultFormatProviderChain();
        assertNotNull(provs);
        assertTrue(provs.contains("TestAmountFormatProvider"));
    }
}
