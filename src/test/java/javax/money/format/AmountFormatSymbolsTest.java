package javax.money.format;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.Set;

import org.junit.Test;

public class AmountFormatSymbolsTest {

	@Test
	public void testGetFormatSymbols() {
		AmountFormatSymbols symbols = AmountFormatSymbols
				.of(Locale.ENGLISH);
		assertNotNull(symbols);
		assertEquals(symbols.getExponentSeparator(), "test");
	}

	@Test
	public void testGetAvailableLocales() {
		Set<Locale> locales = AmountFormatSymbols
				.getAvailableLocales();
		assertNotNull(locales);
		assertTrue(locales.size() == 1);
	}
}
