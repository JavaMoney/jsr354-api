/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package org.javamoney.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Locale;

import org.javamoney.format.ItemFormat;
import org.javamoney.format.ItemFormatException;
import org.javamoney.format.ItemParseException;
import org.javamoney.format.LocalizationStyle;
import org.javamoney.format.MonetaryFormats;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests class for {@link MonetaryFormats}.
 * 
 * @author Anatole Tresch
 * 
 */
public class MonetaryFormatsTest {

	/**
	 * Test method for
	 * {@link org.javamoney.format.MonetaryFormats#getSupportedStyleIds(java.lang.Class)}
	 * .
	 */
	@Test
	public void testGetSupportedStyleIds() {
		Collection<String> ids = MonetaryFormats
				.getSupportedStyleIds(String.class);
		assertNotNull(ids);
		assertTrue(ids.size() == 0);
		//assertTrue(ids.contains("String"));
	}

	/**
	 * Test method for
	 * {@link org.javamoney.format.MonetaryFormats#isSupportedStyle(java.lang.Class, java.lang.String)}
	 * .
	 */
	@Test
	@Ignore
	public void testIsSupportedStyle() {
		assertTrue(MonetaryFormats.isSupportedStyle(String.class, "String"));
		assertTrue(MonetaryFormats.isSupportedStyle(Integer.class, "Integer"));
		assertFalse(MonetaryFormats.isSupportedStyle(Integer.class, "String"));
		assertFalse(MonetaryFormats.isSupportedStyle(Double.class, "Integer"));

	}

	/**
	 * Test method for
	 * {@link org.javamoney.format.MonetaryFormats#getItemFormat(java.lang.Class, org.javamoney.format.LocalizationStyle)}
	 * .
	 * 
	 * @throws ItemParseException
	 */
	@Test(expected = ItemFormatException.class)
	public void testGetItemFormatterClassOfTLocalizationStyle()
			throws ItemParseException {
		ItemFormat<String> formatter = MonetaryFormats.getItemFormat(
				String.class, LocalizationStyle.of(String.class));
		assertEquals("testest", formatter.format("testest", Locale.ENGLISH));
		assertEquals("gugus", formatter.format("gugus", Locale.ENGLISH));
		assertEquals(LocalizationStyle.of(String.class), formatter.getStyle());
		assertEquals(String.class, formatter.getTargetClass());
		assertEquals("", formatter.parse("testest", Locale.ENGLISH));
		assertEquals("", formatter.parse("gugus", Locale.ENGLISH));
	}

	/**
	 * Test method for
	 * {@link org.javamoney.format.MonetaryFormats#getItemFormat(java.lang.Class, java.util.Locale)}
	 * .
	 * 
	 * @throws ItemParseException
	 */
	@Test
	@Ignore
	public void testGetItemFormatterClassOfTLocale() throws ItemParseException {
		ItemFormat<String> formatter = MonetaryFormats.getItemFormat(
				String.class, LocalizationStyle.of(String.class));
		assertEquals("testest", formatter.format("testest", Locale.ENGLISH));
		assertEquals("gugus", formatter.format("gugus", Locale.ENGLISH));
		assertEquals(String.class, formatter.getTargetClass());
		assertEquals("", formatter.parse("testest", Locale.ENGLISH));
		assertEquals("", formatter.parse("gugus", Locale.ENGLISH));
	}

}
