/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.money.MonetaryAmounts;
import javax.money.MonetaryCurrencies;
import javax.money.MonetaryException;

import org.junit.Test;

public class MonetaryFormatsTest {

	// @Test
	// public void testGetFormatSymbols() {
	// AmountFormatSymbols symbols = MonetaryFormats
	// .getAmountFormatSymbols(Locale.ENGLISH);
	// assertNotNull(symbols);
	// assertEquals(symbols.getExponentSeparator(), "test");
	// }
	//
	// @Test(expected = MonetaryException.class)
	// public void testGetFormatSymbolsNotPresent() {
	// MonetaryFormats
	// .getAmountFormatSymbols(Locale.GERMAN);
	// }
	//
	// @Test
	// public void testGetAmountFormatNames() {
	// Set<String> names = MonetaryFormats.getAmountFormatNames();
	// assertNotNull(names);
	// assertTrue(names.contains("test"));
	// }
	//
	// @Test
	// public void testGetAmountFormatStringLocale() {
	// MonetaryAmountFormat fmt = MonetaryFormats.getAmountFormat("test",
	// Locale.ENGLISH);
	// assertNotNull(fmt);
	// assertEquals(fmt.getClass(), TestAmountFormatProvider.TestFormat.class);
	// }
	//
	// @Test(expected = MonetaryException.class)
	// public void
	// publicNotPresent() {
	// MonetaryFormats.getAmountFormat("foo", Locale.ENGLISH);
	// }

	@Test
	public void testGetAmountFormatLocale() {
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(Locale.ENGLISH);
		assertNotNull(fmt);
		assertEquals(fmt.getClass(), TestAmountFormatProvider.TestFormat.class);
	}

	@Test
	public void testGetAmountFormatLocaleCurrencyUnit() {
		MonetaryAmountFormat fmt = new MonetaryFormats.Builder(Locale.ENGLISH)
				.setDefaultCurrency(MonetaryCurrencies.getCurrency("test1"))
				.create();
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		// assertEquals(MonetaryCurrencies.getCurrency("test1"),
		// fmt.getDefaultCurrency());
	}

	@Test
	public void testGetAmountFormatLocaleMonetaryContext() {
		MonetaryAmountFormat fmt = new MonetaryFormats.Builder(Locale.ENGLISH)
				.set(MonetaryAmounts.getDefaultAmountFactory()
						.getDefaultMonetaryContext()).create();
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryAmounts.getDefaultAmountFactory()
				.getDefaultMonetaryContext(),
				fmt.getMonetaryContext());
	}

	@Test
	public void testGetAmountFormatLocaleMonetaryContextCurrencyUnit() {
		MonetaryAmountFormat fmt = new MonetaryFormats.Builder(Locale.ENGLISH)
				.set(MonetaryAmounts.getDefaultAmountFactory()
						.getDefaultMonetaryContext())
				.setDefaultCurrency(MonetaryCurrencies.getCurrency("test1"))
				.create();
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryAmounts.getDefaultAmountFactory()
				.getDefaultMonetaryContext(),
				fmt.getMonetaryContext());
		// assertEquals(MonetaryCurrencies.getCurrency("test1"),
		// fmt.getDefaultCurrency());
	}

	// //////////////

	@Test
	public void testGetAmountFormatStyle() {
		MonetaryAmountFormat fmt = new MonetaryFormats.Builder(Locale.ENGLISH)
				.create();
		assertNotNull(fmt);
		assertEquals(fmt.getClass(), TestAmountFormatProvider.TestFormat.class);
		// assertEquals(s, fmt.getAmountStyle());
	}

	@Test
	public void testGetAmountFormatFormatStyleCurrencyUnit() {
		MonetaryAmountFormat fmt = new MonetaryFormats.Builder(Locale.ENGLISH)
				.setDefaultCurrency(
						MonetaryCurrencies.getCurrency("test1")).create();
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		// assertEquals(MonetaryCurrencies.getCurrency("test1"),
		// fmt.getDefaultCurrency());
		// assertEquals(s, fmt.getAmountStyle());
	}

	@Test
	public void testGetAmountFormatFormatStyleMonetaryContext() {
		AmountStyle s = AmountStyle.getInstance(Locale.ENGLISH);
		MonetaryAmountFormat fmt = new MonetaryFormats.Builder(Locale.ENGLISH)
				.set(
						MonetaryAmounts.getDefaultAmountFactory()
								.getDefaultMonetaryContext()).create();
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryAmounts.getDefaultAmountFactory()
				.getDefaultMonetaryContext(),
				fmt.getMonetaryContext());
		// assertEquals(s, fmt.getAmountStyle());
	}

	@Test
	public void testGetAmountFormatFormatStyleMonetaryContextCurrencyUnit() {
		MonetaryAmountFormat fmt = new MonetaryFormats.Builder(Locale.ENGLISH)
				.set(MonetaryAmounts.getDefaultAmountFactory()
						.getDefaultMonetaryContext()).setDefaultCurrency(
						MonetaryCurrencies.getCurrency("test1")).create();
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryAmounts.getDefaultAmountFactory()
				.getDefaultMonetaryContext(),
				fmt.getMonetaryContext());
		// assertEquals(MonetaryCurrencies.getCurrency("test1"),
		// fmt.getDefaultCurrency());
		// assertEquals(s, fmt.getAmountStyle());
	}

}
