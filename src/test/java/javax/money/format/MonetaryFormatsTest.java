/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import javax.money.MonetaryAmounts;
import javax.money.MonetaryCurrencies;

import org.junit.Test;

public class MonetaryFormatsTest {

	@Test
	public void testGetAmountFormatLocale() {
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(Locale.ENGLISH);
		assertNotNull(fmt);
		assertEquals(fmt.getClass(), TestAmountFormatProvider.TestFormat.class);
	}

	@Test
	public void testGetAmountFormatLocaleCurrencyUnit() {
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(Locale.ENGLISH,
						MonetaryCurrencies.getCurrency("test1"));
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryCurrencies.getCurrency("test1"),
				fmt.getDefaultCurrency());
	}

	@Test
	public void testGetAmountFormatLocaleMonetaryContext() {
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(Locale.ENGLISH,
						MonetaryAmounts.getDefaultAmountFactory().getDefaultMonetaryContext());
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryAmounts.getDefaultAmountFactory().getDefaultMonetaryContext(),
				fmt.getMonetaryContext());
	}

	@Test
	public void testGetAmountFormatLocaleMonetaryContextCurrencyUnit() {
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(Locale.ENGLISH,
						MonetaryAmounts.getDefaultAmountFactory().getDefaultMonetaryContext(),
						MonetaryCurrencies.getCurrency("test1"));
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryAmounts.getDefaultAmountFactory().getDefaultMonetaryContext(),
				fmt.getMonetaryContext());
		assertEquals(MonetaryCurrencies.getCurrency("test1"),
				fmt.getDefaultCurrency());
	}

	// //////////////

	@Test
	public void testGetAmountFormatStyle() {
		AmountStyle s = new AmountStyle.Builder(Locale.GERMAN).build();
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(s);
		assertNotNull(fmt);
		assertEquals(fmt.getClass(), TestAmountFormatProvider.TestFormat.class);
		assertEquals(s, fmt.getAmountStyle());
	}

	@Test
	public void testGetAmountFormatFormatStyleCurrencyUnit() {
		AmountStyle s = new AmountStyle.Builder(Locale.GERMAN).build();
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(s, MonetaryCurrencies.getCurrency("test1"));
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryCurrencies.getCurrency("test1"),
				fmt.getDefaultCurrency());
		assertEquals(s, fmt.getAmountStyle());
	}

	@Test
	public void testGetAmountFormatFormatStyleMonetaryContext() {
		AmountStyle s = new AmountStyle.Builder(Locale.GERMAN).build();
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(s, MonetaryAmounts.getDefaultAmountFactory().getDefaultMonetaryContext());
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryAmounts.getDefaultAmountFactory().getDefaultMonetaryContext(),
				fmt.getMonetaryContext());
		assertEquals(s, fmt.getAmountStyle());
	}

	@Test
	public void testGetAmountFormatFormatStyleMonetaryContextCurrencyUnit() {
		AmountStyle s = new AmountStyle.Builder(Locale.GERMAN).build();
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(s,
						MonetaryAmounts.getDefaultAmountFactory().getDefaultMonetaryContext(),
						MonetaryCurrencies.getCurrency("test1"));
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryAmounts.getDefaultAmountFactory().getDefaultMonetaryContext(),
				fmt.getMonetaryContext());
		assertEquals(MonetaryCurrencies.getCurrency("test1"),
				fmt.getDefaultCurrency());
		assertEquals(s, fmt.getAmountStyle());
	}

}
