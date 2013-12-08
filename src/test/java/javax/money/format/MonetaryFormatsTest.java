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
						MonetaryAmounts.getDefaultMonetaryContext());
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryAmounts.getDefaultMonetaryContext(),
				fmt.getMonetaryContext());
	}

	@Test
	public void testGetAmountFormatLocaleMonetaryContextCurrencyUnit() {
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(Locale.ENGLISH,
						MonetaryAmounts.getDefaultMonetaryContext(),
						MonetaryCurrencies.getCurrency("test1"));
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryAmounts.getDefaultMonetaryContext(),
				fmt.getMonetaryContext());
		assertEquals(MonetaryCurrencies.getCurrency("test1"),
				fmt.getDefaultCurrency());
	}

	// //////////////

	@Test
	public void testGetAmountFormatStyle() {
		FormatStyle s = new FormatStyle.Builder(Locale.GERMAN).build();
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(s);
		assertNotNull(fmt);
		assertEquals(fmt.getClass(), TestAmountFormatProvider.TestFormat.class);
		assertEquals(s, fmt.getFormatStyle());
	}

	@Test
	public void testGetAmountFormatFormatStyleCurrencyUnit() {
		FormatStyle s = new FormatStyle.Builder(Locale.GERMAN).build();
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(s, MonetaryCurrencies.getCurrency("test1"));
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryCurrencies.getCurrency("test1"),
				fmt.getDefaultCurrency());
		assertEquals(s, fmt.getFormatStyle());
	}

	@Test
	public void testGetAmountFormatFormatStyleMonetaryContext() {
		FormatStyle s = new FormatStyle.Builder(Locale.GERMAN).build();
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(s, MonetaryAmounts.getDefaultMonetaryContext());
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryAmounts.getDefaultMonetaryContext(),
				fmt.getMonetaryContext());
		assertEquals(s, fmt.getFormatStyle());
	}

	@Test
	public void testGetAmountFormatFormatStyleMonetaryContextCurrencyUnit() {
		FormatStyle s = new FormatStyle.Builder(Locale.GERMAN).build();
		MonetaryAmountFormat fmt = MonetaryFormats
				.getAmountFormat(s,
						MonetaryAmounts.getDefaultMonetaryContext(),
						MonetaryCurrencies.getCurrency("test1"));
		assertNotNull(fmt);
		assertEquals(TestAmountFormatProvider.TestFormat.class, fmt.getClass());
		assertEquals(MonetaryAmounts.getDefaultMonetaryContext(),
				fmt.getMonetaryContext());
		assertEquals(MonetaryCurrencies.getCurrency("test1"),
				fmt.getDefaultCurrency());
		assertEquals(s, fmt.getFormatStyle());
	}

}
