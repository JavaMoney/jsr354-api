package net.java.javamoney.ri.format.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Enumeration;
import java.util.Locale;

import javax.money.format.common.LocalizationStyle;
import javax.money.format.common.StyleableFormatter;
import javax.money.format.common.StyledFormatter;

import net.java.javamoney.ri.format.common.FormatToken;
import net.java.javamoney.ri.format.common.TokenizedFormatterBuilder;
import net.java.javamoney.ri.format.token.FormattedNumber;
import net.java.javamoney.ri.format.token.Literal;
import net.java.javamoney.ri.format.token.PositiveSign;

import org.junit.Test;

public class TokenizedFormatterBuilderTest {

	@Test
	public void testTokenizedFormatterBuilder() {
		new TokenizedFormatterBuilder<Double>(Double.class);
	}

	@Test
	public void testAddTokenFormatTokenOfT() {
		TokenizedFormatterBuilder<Double> b = new TokenizedFormatterBuilder<Double>(
				Double.class);
		b.addToken(new Literal<Double>("test- "));
		DecimalFormat df = new DecimalFormat("#0.0#");
		DecimalFormatSymbols syms = df.getDecimalFormatSymbols();
		syms.setDecimalSeparator(':');
		df.setDecimalFormatSymbols(syms);
		b.addToken(new FormattedNumber<Double>(df).setNumberGroupChars(',', '\'')
				.setNumberGroupSizes(2, 2, 3));
		StyledFormatter<Double> f = b.toFormatter(Locale.FRENCH);
		assertNotNull(f);
		assertEquals("test- 12'345'67,89:12", f.print(123456789.123456789d));
		StyleableFormatter<Double> sf = b.toStyleableFormatter();
		assertNotNull(sf);
		assertEquals("test- 12'345'67,89:12",
				sf.print(123456789.123456789d, Locale.FRENCH));
	}

	@Test
	public void testAddTokenString() {
		TokenizedFormatterBuilder<Double> b = new TokenizedFormatterBuilder<Double>(
				Double.class);
		b.addToken("test- ");
		DecimalFormat f = new DecimalFormat("#0.0#");
		DecimalFormatSymbols symbols = f.getDecimalFormatSymbols();
		symbols.setDecimalSeparator(':');
		f.setDecimalFormatSymbols(symbols);
		b.addToken(new FormattedNumber<Double>(f)
				.setNumberGroupChars(',', '\'').setNumberGroupSizes(2, 2, 3)
				.decorate(new PositiveSign<Double>().setBeforeSign("BEF+")));
		StyledFormatter<Double> sf = b.toFormatter(Locale.FRENCH);
		assertNotNull(sf);
		assertEquals("test- BEF+12'345'67,89:12",
				sf.print(123456789.123456789d));
		StyleableFormatter<Double> stf = b.toStyleableFormatter();
		assertNotNull(stf);
		assertEquals("test- BEF+12'345'67,89:12",
				stf.print(123456789.123456789d, Locale.FRENCH));
	}

	@Test
	public void testGetTokens() {
		TokenizedFormatterBuilder<Double> b = new TokenizedFormatterBuilder<Double>(
				Double.class);
		b.addToken("1");
		b.addToken("2");
		b.addToken("3");
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
		TokenizedFormatterBuilder<Double> b = new TokenizedFormatterBuilder<Double>(
				Double.class);
		b.addToken("1");
		b.addToken("2");
		assertEquals(2, b.getTokenCount());
	}

	@Test
	public void testClear() {
		TokenizedFormatterBuilder<Double> b = new TokenizedFormatterBuilder<Double>(
				Double.class);
		b.addToken("1");
		b.addToken("2");
		assertEquals(2, b.getTokenCount());
		b.clear();
		assertEquals(0, b.getTokenCount());
	}

	@Test
	public void testToStyleableFormatter() {
		TokenizedFormatterBuilder<Double> b = new TokenizedFormatterBuilder<Double>(
				Double.class);
		b.addToken(new Literal<Double>("test"));
		StyleableFormatter<Double> sf = b.toStyleableFormatter();
		assertNotNull(sf);
		assertEquals("test", sf.print(123456789.123456789d, Locale.CHINESE));
	}

	@Test
	public void testToFormatterLocale() {
		TokenizedFormatterBuilder<Double> b = new TokenizedFormatterBuilder<Double>(
				Double.class);
		b.addToken(new Literal<Double>("test "));
		b.addToken(new FormattedNumber<Double>());
		StyledFormatter<Double> f = b.toFormatter(Locale.CHINESE);
		assertNotNull(f);
		assertEquals("test 123,456,789.123", f.print(123456789.123456789d));
		f = b.toFormatter(Locale.GERMAN);
		assertNotNull(f);
		assertEquals("test 123.456.789,123", f.print(123456789.123456789d));
		f = b.toFormatter(Locale.CANADA);
		assertNotNull(f);
		assertEquals("test 123,456,789.123", f.print(123456789.123456789d));
		f = b.toFormatter(Locale.JAPAN);
		assertNotNull(f);
		assertEquals("test 123,456,789.123", f.print(123456789.123456789d));
	}

	@Test
	public void testToFormatterLocalizationStyle() {
		TokenizedFormatterBuilder<Double> b = new TokenizedFormatterBuilder<Double>(
				Double.class);
		b.addToken(new Literal<Double>("test "));
		b.addToken(new FormattedNumber<Double>());
		StyledFormatter<Double> f = b.toFormatter(LocalizationStyle
				.of(Locale.CHINESE));
		assertNotNull(f);
		assertEquals("test 123,456,789.123", f.print(123456789.123456789d));
		f = b.toFormatter(LocalizationStyle.of(Locale.GERMAN));
		assertNotNull(f);
		assertEquals("test 123.456.789,123", f.print(123456789.123456789d));
	}

}
