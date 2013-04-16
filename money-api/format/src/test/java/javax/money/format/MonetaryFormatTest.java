/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Locale;

import org.junit.Test;

/**
 * Tests class for {@link MonetaryFormat}.
 * 
 * @author Anatole Tresch
 * 
 */
public class MonetaryFormatTest {

	/**
	 * Test method for
	 * {@link javax.money.format.MonetaryFormat#getSupportedStyleIds(java.lang.Class)}
	 * .
	 */
	@Test
	public void testGetSupportedStyleIds() {
		Collection<String> ids = MonetaryFormat
				.getSupportedStyleIds(String.class);
		assertNotNull(ids);
		assertTrue(ids.size() == 1);
		assertTrue(ids.contains("String"));
	}

	/**
	 * Test method for
	 * {@link javax.money.format.MonetaryFormat#isSupportedStyle(java.lang.Class, java.lang.String)}
	 * .
	 */
	@Test
	public void testIsSupportedStyle() {
		assertTrue(MonetaryFormat.isSupportedStyle(String.class, "String"));
		assertTrue(MonetaryFormat.isSupportedStyle(Integer.class, "Integer"));
		assertFalse(MonetaryFormat.isSupportedStyle(Integer.class, "String"));
		assertFalse(MonetaryFormat.isSupportedStyle(Double.class, "Integer"));

	}

	/**
	 * Test method for
	 * {@link javax.money.format.MonetaryFormat#getItemFormat(java.lang.Class, javax.money.format.LocalizationStyle)}
	 * .
	 * 
	 * @throws ItemParseException
	 */
	@Test
	public void testGetItemFormatterClassOfTLocalizationStyle()
			throws ItemParseException {
		ItemFormat<String> formatter = MonetaryFormat.getItemFormat(
				String.class, LocalizationStyle.of(Locale.ENGLISH));
		assertEquals("testest", formatter.format("testest"));
		assertEquals("gugus", formatter.format("gugus"));
		assertEquals(LocalizationStyle.of(Locale.ENGLISH), formatter.getStyle());
		assertEquals(String.class, formatter.getTargetClass());
		assertEquals("", formatter.parse("testest"));
		assertEquals("", formatter.parse("gugus"));
	}

	/**
	 * Test method for
	 * {@link javax.money.format.MonetaryFormat#getItemFormat(java.lang.Class, java.util.Locale)}
	 * .
	 * 
	 * @throws ItemParseException
	 */
	@Test
	public void testGetItemFormatterClassOfTLocale() throws ItemParseException {
		ItemFormat<String> formatter = MonetaryFormat.getItemFormat(
				String.class, Locale.ENGLISH);
		assertEquals("testest", formatter.format("testest"));
		assertEquals("gugus", formatter.format("gugus"));
		assertEquals(LocalizationStyle.of(Locale.ENGLISH), formatter.getStyle());
		assertEquals(String.class, formatter.getTargetClass());
		assertEquals("", formatter.parse("testest"));
		assertEquals("", formatter.parse("gugus"));
	}

}
