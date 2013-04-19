package net.java.javamoney.ri.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Locale;

import javax.money.format.FormatToken;
import javax.money.format.ItemFormat;
import javax.money.format.ItemFormatBuilder;
import javax.money.format.LiteralToken;
import javax.money.format.LocalizationStyle;

import net.java.javamoney.ri.format.tokens.NumberToken;

import org.junit.Test;

public class ItemFormatBuilderTest {

	@Test
	public void testTokenizedFormatterBuilder() {
		new ItemFormatBuilder<Double>(Double.class);
	}

	@Test
	public void testAddTokenFormatTokenOfT() throws IOException {
		ItemFormatBuilder<Number> b = new ItemFormatBuilder<Number>(
				Number.class);
		b.addToken(new LiteralToken<Number>("test- "));
		DecimalFormat df = new DecimalFormat("#0.0#");
		DecimalFormatSymbols syms = df.getDecimalFormatSymbols();
		syms.setDecimalSeparator(':');
		df.setDecimalFormatSymbols(syms);
		b.addToken(new NumberToken(df).setNumberGroupChars(',', '\'')
				.setNumberGroupSizes(2, 2, 3));
		b.setLocalizationStyle(LocalizationStyle.of(Locale.FRENCH));
		ItemFormat<Number> f = b.build();
		assertNotNull(f);
		assertEquals("test- 12'345'67,89:12", f.format(123456789.123456789d));
	}

	@Test
	public void testAddTokenString() throws IOException {
		ItemFormatBuilder<Number> b = new ItemFormatBuilder<Number>(
				Number.class);
		b.addLiteral("test- ");
		b.addLiteral("BEF+ ");
		DecimalFormat f = new DecimalFormat("#0.0#");
		DecimalFormatSymbols symbols = f.getDecimalFormatSymbols();
		symbols.setDecimalSeparator(':');
		f.setDecimalFormatSymbols(symbols);
		b.addToken(new NumberToken(f).setNumberGroupChars(',', '\'')
				.setNumberGroupSizes(2, 2, 3));
		b.setLocalizationStyle(LocalizationStyle.of(Locale.FRENCH));
		ItemFormat<Number> sf = b.build();
		assertNotNull(sf);
		assertEquals("test- BEF+ 12'345'67,89:12",
				sf.format(123456789.123456789d));
	}

	@Test
	public void testGetTokens() {
		ItemFormatBuilder<Double> b = new ItemFormatBuilder<Double>(
				Double.class);
		b.addLiteral("1");
		b.addLiteral("2");
		b.addLiteral("3");
		Collection<FormatToken<Double>> tokens = b.getTokens();
		int size = 0;
		for(FormatToken<?> token: tokens){
			assertNotNull(token);
			assertTrue(token instanceof LiteralToken<?>);
			size++;
		}
		assertEquals(3, size);
	}

	@Test
	public void testGetTokenCount() {
		ItemFormatBuilder<Double> b = new ItemFormatBuilder<Double>(
				Double.class);
		b.addLiteral("1");
		b.addLiteral("2");
		assertEquals(2, b.getTokenCount());
	}

	@Test
	public void testClear() {
		ItemFormatBuilder<Double> b = new ItemFormatBuilder<Double>(
				Double.class);
		b.addLiteral("1");
		b.addLiteral("2");
		assertEquals(2, b.getTokenCount());
		b.clear();
		assertEquals(0, b.getTokenCount());
	}

	@Test
	public void testToFormatterLocalizationStyle() {
		ItemFormatBuilder<Number> b = new ItemFormatBuilder<Number>(
				Number.class);
		b.addToken(new LiteralToken<Number>("test "));
		b.addToken(new NumberToken());
		b.setLocalizationStyle(LocalizationStyle.of(Locale.CHINESE));
		ItemFormat<Number> f = b.build();
		assertNotNull(f);
		assertEquals("test 123,456,789.123", f.format(123456789.123456789d));
		b.setLocalizationStyle(LocalizationStyle.of(Locale.GERMAN));
		f = b.build();
		assertNotNull(f);
		assertEquals("test 123.456.789,123", f.format(123456789.123456789d));
	}

}
