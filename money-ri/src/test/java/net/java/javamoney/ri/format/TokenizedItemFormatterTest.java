package net.java.javamoney.ri.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Enumeration;
import java.util.Locale;

import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;

import net.java.javamoney.ri.format.tokenformatter.FormattedNumber;
import net.java.javamoney.ri.format.tokenformatter.FormatterToken;
import net.java.javamoney.ri.format.tokenformatter.Literal;
import net.java.javamoney.ri.format.tokenformatter.TokenizedItemFormatter;

import org.junit.Test;

public class TokenizedItemFormatterTest {

	@Test
	public void testTokenizedFormatterBuilder() {
		new TokenizedItemFormatter.Builder<Double>(Double.class);
	}

	@Test
	public void testAddTokenFormatTokenOfT() throws IOException {
		TokenizedItemFormatter.Builder<Double> b = new TokenizedItemFormatter.Builder<Double>(
				Double.class);
		b.addToken(new Literal<Double>("test- "));
		DecimalFormat df = new DecimalFormat("#0.0#");
		DecimalFormatSymbols syms = df.getDecimalFormatSymbols();
		syms.setDecimalSeparator(':');
		df.setDecimalFormatSymbols(syms);
		b.addToken(new FormattedNumber<Double>(df).setNumberGroupChars(',',
				'\'').setNumberGroupSizes(2, 2, 3));
		b.setLocalizationStyle(LocalizationStyle.of(Locale.FRENCH));
		ItemFormatter<Double> f = b.build();
		assertNotNull(f);
		assertEquals("test- 12'345'67,89:12", f.format(123456789.123456789d));
	}

	@Test
	public void testAddTokenString() throws IOException {
		TokenizedItemFormatter.Builder<Double> b = new TokenizedItemFormatter.Builder<Double>(
				Double.class);
		b.addToken("test- ");
		b.addToken("BEF+ ");
		DecimalFormat f = new DecimalFormat("#0.0#");
		DecimalFormatSymbols symbols = f.getDecimalFormatSymbols();
		symbols.setDecimalSeparator(':');
		f.setDecimalFormatSymbols(symbols);
		b.addToken(new FormattedNumber<Double>(f)
				.setNumberGroupChars(',', '\'').setNumberGroupSizes(2, 2, 3));
		b.setLocalizationStyle(LocalizationStyle.of(Locale.FRENCH));
		ItemFormatter<Double> sf = b.build();
		assertNotNull(sf);
		assertEquals("test- BEF+ 12'345'67,89:12",
				sf.format(123456789.123456789d));
	}

	@Test
	public void testGetTokens() {
		TokenizedItemFormatter.Builder<Double> b = new TokenizedItemFormatter.Builder<Double>(
				Double.class);
		b.addToken("1");
		b.addToken("2");
		b.addToken("3");
		Enumeration<FormatterToken<Double>> tokens = b.getTokens();
		int size = 0;
		while (tokens.hasMoreElements()) {
			FormatterToken<?> token = (FormatterToken<?>) tokens.nextElement();
			assertNotNull(token);
			assertTrue(token instanceof Literal<?>);
			size++;
		}
		assertEquals(3, size);
	}

	@Test
	public void testGetTokenCount() {
		TokenizedItemFormatter.Builder<Double> b = new TokenizedItemFormatter.Builder<Double>(
				Double.class);
		b.addToken("1");
		b.addToken("2");
		assertEquals(2, b.getTokenCount());
	}

	@Test
	public void testClear() {
		TokenizedItemFormatter.Builder<Double> b = new TokenizedItemFormatter.Builder<Double>(
				Double.class);
		b.addToken("1");
		b.addToken("2");
		assertEquals(2, b.getTokenCount());
		b.clear();
		assertEquals(0, b.getTokenCount());
	}

	@Test
	public void testToFormatterLocalizationStyle() {
		TokenizedItemFormatter.Builder<Double> b = new TokenizedItemFormatter.Builder<Double>(
				Double.class);
		b.addToken(new Literal<Double>("test "));
		b.addToken(new FormattedNumber<Double>());
		b.setLocalizationStyle(LocalizationStyle.of(Locale.CHINESE));
		ItemFormatter<Double> f = b.build();
		assertNotNull(f);
		assertEquals("test 123,456,789.123", f.format(123456789.123456789d));
		b.setLocalizationStyle(LocalizationStyle.of(Locale.GERMAN));
		f = b.build();
		assertNotNull(f);
		assertEquals("test 123.456.789,123", f.format(123456789.123456789d));
	}

}
