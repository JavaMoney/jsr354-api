package net.java.javamoney.ri.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Enumeration;
import java.util.Locale;

import javax.money.format.FormatToken;
import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;
import javax.money.format.TokenizedItemFormatterBuilder;

import net.java.javamoney.ri.format.tokenformatter.FormattedNumber;
import net.java.javamoney.ri.format.tokenformatter.Literal;
import net.java.javamoney.ri.format.tokenformatter.DefaultTokenizedItemFormatter;

import org.junit.Test;

public class TokenizedItemFormatterTest {

	@Test
	public void testTokenizedFormatterBuilder() {
		new DefaultTokenizedItemFormatter.Builder<Double>(Double.class);
	}

	@Test
	public void testAddTokenFormatTokenOfT() throws IOException {
		DefaultTokenizedItemFormatter.Builder<Double> b = new DefaultTokenizedItemFormatter.Builder<Double>(
				Double.class);
		b.addToken(new Literal<Double>("test- "));
		DecimalFormat df = new DecimalFormat("#0.0#");
		DecimalFormatSymbols syms = df.getDecimalFormatSymbols();
		syms.setDecimalSeparator(':');
		df.setDecimalFormatSymbols(syms);
		b.addToken(new FormattedNumber<Double>(df).setNumberGroupChars(',',
				'\'').setNumberGroupSizes(2, 2, 3));
		b.setLocalizationStyle(LocalizationStyle.valueOf(Locale.FRENCH));
		ItemFormatter<Double> f = b.build();
		assertNotNull(f);
		assertEquals("test- 12'345'67,89:12", f.format(123456789.123456789d));
	}

	@Test
	public void testAddTokenString() throws IOException {
		DefaultTokenizedItemFormatter.Builder<Double> b = new DefaultTokenizedItemFormatter.Builder<Double>(
				Double.class);
		b.addLiteral("test- ");
		b.addLiteral("BEF+ ");
		DecimalFormat f = new DecimalFormat("#0.0#");
		DecimalFormatSymbols symbols = f.getDecimalFormatSymbols();
		symbols.setDecimalSeparator(':');
		f.setDecimalFormatSymbols(symbols);
		b.addToken(new FormattedNumber<Double>(f)
				.setNumberGroupChars(',', '\'').setNumberGroupSizes(2, 2, 3));
		b.setLocalizationStyle(LocalizationStyle.valueOf(Locale.FRENCH));
		ItemFormatter<Double> sf = b.build();
		assertNotNull(sf);
		assertEquals("test- BEF+ 12'345'67,89:12",
				sf.format(123456789.123456789d));
	}

	@Test
	public void testGetTokens() {
		TokenizedItemFormatterBuilder<Double> b = new DefaultTokenizedItemFormatter.Builder<Double>(
				Double.class);
		b.addLiteral("1");
		b.addLiteral("2");
		b.addLiteral("3");
		Enumeration<FormatToken<Double>> tokens = b.getTokens();
		int size = 0;
		while (tokens.hasMoreElements()) {
			FormatToken<?> token = (FormatToken<?>) tokens.nextElement();
			assertNotNull(token);
			assertTrue(token instanceof Literal<?>);
			size++;
		}
		assertEquals(3, size);
	}

	@Test
	public void testGetTokenCount() {
		TokenizedItemFormatterBuilder<Double> b = new DefaultTokenizedItemFormatter.Builder<Double>(
				Double.class);
		b.addLiteral("1");
		b.addLiteral("2");
		assertEquals(2, b.getTokenCount());
	}

	@Test
	public void testClear() {
		TokenizedItemFormatterBuilder<Double> b = new DefaultTokenizedItemFormatter.Builder<Double>(
				Double.class);
		b.addLiteral("1");
		b.addLiteral("2");
		assertEquals(2, b.getTokenCount());
		b.clear();
		assertEquals(0, b.getTokenCount());
	}

	@Test
	public void testToFormatterLocalizationStyle() {
		DefaultTokenizedItemFormatter.Builder<Double> b = new DefaultTokenizedItemFormatter.Builder<Double>(
				Double.class);
		b.addToken(new Literal<Double>("test "));
		b.addToken(new FormattedNumber<Double>());
		b.setLocalizationStyle(LocalizationStyle.valueOf(Locale.CHINESE));
		ItemFormatter<Double> f = b.build();
		assertNotNull(f);
		assertEquals("test 123,456,789.123", f.format(123456789.123456789d));
		b.setLocalizationStyle(LocalizationStyle.valueOf(Locale.GERMAN));
		f = b.build();
		assertNotNull(f);
		assertEquals("test 123.456.789,123", f.format(123456789.123456789d));
	}

}
