package net.java.javamoney.ri.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Enumeration;
import java.util.Locale;

import javax.money.format.common.LocalizationStyle;
import javax.money.format.common.StyleableFormatter;
import javax.money.format.common.StyledFormatter;

import net.java.javamoney.ri.format.token.FormattedNumber;
import net.java.javamoney.ri.format.token.Literal;
import net.java.javamoney.ri.format.token.PositiveSign;

import org.junit.Test;

public class TokenizedFormatterBuilderTest {

	@Test
	public void testTokenizedFormatterBuilder() {
		TokenizedFormatterBuilder b = new TokenizedFormatterBuilder(
				Double.class);
	}

	@Test
	public void testAddTokenFormatTokenOfT() {
		TokenizedFormatterBuilder b = new TokenizedFormatterBuilder(
				Double.class);
		b.addToken(new Literal<Double>("test- "));
		b.addToken(new FormattedNumber<Double>().setDecimalSeparator(':')
				.setNumberGroupChars(',', '\'').setNumberGroupSizes(2, 2, 3));
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
		TokenizedFormatterBuilder b = new TokenizedFormatterBuilder(
				Double.class);
		b.addToken("test- ");
		b.addToken(new FormattedNumber<Double>().setDecimalSeparator(':')
				.setNumberGroupChars(',', '\'').setNumberGroupSizes(2, 2, 3)
				.decorate(new PositiveSign().setBeforeSign("BEF+")));
		StyledFormatter<Double> f = b.toFormatter(Locale.FRENCH);
		assertNotNull(f);
		assertEquals("test- BEF+12'345'67,89:12", f.print(123456789.123456789d));
		StyleableFormatter<Double> sf = b.toStyleableFormatter();
		assertNotNull(sf);
		assertEquals("test- BEF+12'345'67,89:12",
				sf.print(123456789.123456789d, Locale.FRENCH));
	}

	@Test
	public void testGetTokens() {
		TokenizedFormatterBuilder b = new TokenizedFormatterBuilder(
				Double.class);
		b.addToken("1");
		b.addToken("2");
		b.addToken("3");
		Enumeration<FormatToken> tokens = b.getTokens();
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
		TokenizedFormatterBuilder b = new TokenizedFormatterBuilder(
				Double.class);
		b.addToken("1");
		b.addToken("2");
		assertEquals(2, b.getTokenCount());
	}

	@Test
	public void testClear() {
		TokenizedFormatterBuilder b = new TokenizedFormatterBuilder(
				Double.class);
		b.addToken("1");
		b.addToken("2");
		assertEquals(2, b.getTokenCount());
		b.clear();
		assertEquals(0, b.getTokenCount());
	}

	@Test
	public void testToStyleableFormatter() {
		TokenizedFormatterBuilder b = new TokenizedFormatterBuilder(
				Double.class);
		b.addToken(new Literal<Double>("test"));
		StyleableFormatter<Double> sf = b.toStyleableFormatter();
		assertNotNull(sf);
		assertEquals("test", sf.print(123456789.123456789d, Locale.CHINESE));
	}

	@Test
	public void testToFormatterLocale() {
		TokenizedFormatterBuilder b = new TokenizedFormatterBuilder(
				Double.class);
		b.addToken(new Literal<Double>("test "));
		b.addToken(new FormattedNumber<>().setUsingtyle(true));
		StyledFormatter<Double> f = b.toFormatter(Locale.CHINESE);
		assertNotNull(f);
		assertEquals("test 123,456,789.12", f.print(123456789.123456789d));
		f = b.toFormatter(Locale.GERMAN);
		assertNotNull(f);
		assertEquals("test 123.456.789,12", f.print(123456789.123456789d));
	}

	@Test
	public void testToFormatterLocalizationStyle() {
		TokenizedFormatterBuilder b = new TokenizedFormatterBuilder(
				Double.class);
		b.addToken(new Literal<Double>("test "));
		b.addToken(new FormattedNumber<>().setUsingtyle(true));
		StyledFormatter<Double> f = b.toFormatter(LocalizationStyle
				.of(Locale.CHINESE));
		assertNotNull(f);
		assertEquals("test 123,456,789.12", f.print(123456789.123456789d));
		f = b.toFormatter(LocalizationStyle.of(Locale.GERMAN));
		assertNotNull(f);
		assertEquals("test 123.456.789,12", f.print(123456789.123456789d));
	}

}
