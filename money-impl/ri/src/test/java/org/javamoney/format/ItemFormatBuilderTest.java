package org.javamoney.format;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Locale;



import org.javamoney.format.FormatToken;
import org.javamoney.format.ItemFormat;
import org.javamoney.format.ItemFormatBuilder;
import org.javamoney.format.LiteralToken;
import org.javamoney.format.LocalizationStyle;
import org.javamoney.format.tokens.NumberToken;
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
		b.append(new LiteralToken<Number>("test- "));
		DecimalFormat df = new DecimalFormat("#0.0#");
		DecimalFormatSymbols syms = df.getDecimalFormatSymbols();
		syms.setDecimalSeparator(':');
		df.setDecimalFormatSymbols(syms);
		b.append(new NumberToken(df).setNumberGroupChars(',', '\'')
				.setNumberGroupSizes(2, 2, 3));
		b.withStyle(new LocalizationStyle.Builder(Number.class).build());
		ItemFormat<Number> f = b.build();
		assertNotNull(f);
		assertEquals("test- 12'345'67,89:12", f.format(123456789.123456789d, Locale.FRENCH));
	}

	@Test
	public void testAddTokenString() throws IOException {
		ItemFormatBuilder<Number> b = new ItemFormatBuilder<Number>(
				Number.class);
		b.append("test- ");
		b.append("BEF+ ");
		DecimalFormat f = new DecimalFormat("#0.0#");
		DecimalFormatSymbols symbols = f.getDecimalFormatSymbols();
		symbols.setDecimalSeparator(':');
		f.setDecimalFormatSymbols(symbols);
		b.append(new NumberToken(f).setNumberGroupChars(',', '\'')
				.setNumberGroupSizes(2, 2, 3));
		b.withStyle(new LocalizationStyle.Builder(Number.class).build());
		ItemFormat<Number> sf = b.build();
		assertNotNull(sf);
		assertEquals("test- BEF+ 12'345'67,89:12",
				sf.format(123456789.123456789d, Locale.FRENCH));
	}

	@Test
	public void testGetTokens() {
		ItemFormatBuilder<Double> b = new ItemFormatBuilder<Double>(
				Double.class);
		b.append("1");
		b.append("2");
		b.append("3");
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
		b.append("1");
		b.append("2");
		assertEquals(2, b.getTokenCount());
	}

	@Test
	public void testClear() {
		ItemFormatBuilder<Double> b = new ItemFormatBuilder<Double>(
				Double.class);
		b.append("1");
		b.append("2");
		assertEquals(2, b.getTokenCount());
		b.clear();
		assertEquals(0, b.getTokenCount());
	}

	@Test
	public void testToFormatterLocalizationStyle() {
		ItemFormatBuilder<Number> b = new ItemFormatBuilder<Number>(
				Number.class);
		b.append(new LiteralToken<Number>("test "));
		b.append(new NumberToken());
		b.withStyle(new LocalizationStyle.Builder(Number.class).build());
		ItemFormat<Number> f = b.build();
		assertNotNull(f);
		assertEquals("test 123,456,789.123", f.format(123456789.123456789d, Locale.CHINESE));
		b.withStyle(new LocalizationStyle.Builder(Number.class).build());
		f = b.build();
		assertNotNull(f);
		assertEquals("test 123.456.789,123", f.format(123456789.123456789d,Locale.GERMAN));
	}

}
