package net.java.javamoney.ri.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Locale;

import javax.money.format.BuildableItemFormat;
import javax.money.format.FormatToken;
import javax.money.format.LiteralToken;
import javax.money.format.LocalizationStyle;

import net.java.javamoney.ri.format.tokenformatter.NumberToken;

import org.junit.Test;

public class BuildableItemFormatterTest {

	@Test
	public void testTokenizedFormatterBuilder() {
		new BuildableItemFormat.Builder<Double>(Double.class);
	}

	@Test
	public void testAddTokenFormatTokenOfT() throws IOException {
		BuildableItemFormat.Builder<Double> b = new BuildableItemFormat.Builder<Double>(
				Double.class);
		b.addToken(new LiteralToken<Double>("test- "));
		DecimalFormat df = new DecimalFormat("#0.0#");
		DecimalFormatSymbols syms = df.getDecimalFormatSymbols();
		syms.setDecimalSeparator(':');
		df.setDecimalFormatSymbols(syms);
		b.addToken(new NumberToken<Double>(df).setNumberGroupChars(',', '\'')
				.setNumberGroupSizes(2, 2, 3));
		b.setLocalizationStyle(LocalizationStyle.of(Locale.FRENCH));
		BuildableItemFormat<Double> f = b.build();
		assertNotNull(f);
		assertEquals("test- 12'345'67,89:12", f.format(123456789.123456789d));
	}

	@Test
	public void testAddTokenString() throws IOException {
		BuildableItemFormat.Builder<Double> b = new BuildableItemFormat.Builder<Double>(
				Double.class);
		b.addLiteral("test- ");
		b.addLiteral("BEF+ ");
		DecimalFormat f = new DecimalFormat("#0.0#");
		DecimalFormatSymbols symbols = f.getDecimalFormatSymbols();
		symbols.setDecimalSeparator(':');
		f.setDecimalFormatSymbols(symbols);
		b.addToken(new NumberToken<Double>(f).setNumberGroupChars(',', '\'')
				.setNumberGroupSizes(2, 2, 3));
		b.setLocalizationStyle(LocalizationStyle.of(Locale.FRENCH));
		BuildableItemFormat<Double> sf = b.build();
		assertNotNull(sf);
		assertEquals("test- BEF+ 12'345'67,89:12",
				sf.format(123456789.123456789d));
	}

	@Test
	public void testGetTokens() {
		BuildableItemFormat.Builder<Double> b = new BuildableItemFormat.Builder<Double>(
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
		BuildableItemFormat.Builder<Double> b = new BuildableItemFormat.Builder<Double>(
				Double.class);
		b.addLiteral("1");
		b.addLiteral("2");
		assertEquals(2, b.getTokenCount());
	}

	@Test
	public void testClear() {
		BuildableItemFormat.Builder<Double> b = new BuildableItemFormat.Builder<Double>(
				Double.class);
		b.addLiteral("1");
		b.addLiteral("2");
		assertEquals(2, b.getTokenCount());
		b.clear();
		assertEquals(0, b.getTokenCount());
	}

	@Test
	public void testToFormatterLocalizationStyle() {
		BuildableItemFormat.Builder<Double> b = new BuildableItemFormat.Builder<Double>(
				Double.class);
		b.addToken(new LiteralToken<Double>("test "));
		b.addToken(new NumberToken<Double>());
		b.setLocalizationStyle(LocalizationStyle.of(Locale.CHINESE));
		BuildableItemFormat<Double> f = b.build();
		assertNotNull(f);
		assertEquals("test 123,456,789.123", f.format(123456789.123456789d));
		b.setLocalizationStyle(LocalizationStyle.of(Locale.GERMAN));
		f = b.build();
		assertNotNull(f);
		assertEquals("test 123.456.789,123", f.format(123456789.123456789d));
	}

}
