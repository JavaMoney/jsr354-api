package javax.money.format;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import javax.money.spi.MonetaryAmountFormatProviderSpi;

public class TestAmountFormatProvider implements
		MonetaryAmountFormatProviderSpi {

	@Override
	public MonetaryAmountFormat getFormat(Locale locale,
			FormatStyle formatStyle, MonetaryContext monetaryContext,
			CurrencyUnit defaultCurrency) {
		return new TestFormat(locale, formatStyle, monetaryContext,
				defaultCurrency);
	}

	public static final class TestFormat implements MonetaryAmountFormat {

		private FormatStyle formatStyle = new FormatStyle.Builder(Locale.ENGLISH)
				.build();
		private MonetaryContext monetaryContext;
		private CurrencyUnit defaultCurrency;

		TestFormat(Locale locale,
				FormatStyle formatStyle, MonetaryContext monetaryContext,
				CurrencyUnit defaultCurrency) {
			if (locale != null && formatStyle == null) {
				formatStyle = new FormatStyle.Builder(locale)
						.build();
			}
			this.formatStyle = formatStyle;
			this.monetaryContext = monetaryContext;
			this.defaultCurrency = defaultCurrency;
		}

		@Override
		public String queryFrom(MonetaryAmount<?> amount) {
			return toString();
		}

		@Override
		public FormatStyle getFormatStyle() {
			return formatStyle;
		}

		@Override
		public CurrencyUnit getDefaultCurrency() {
			return defaultCurrency;
		}

		@Override
		public String format(MonetaryAmount<?> amount) {
			return "TestFormat:" + amount.toString();
		}

		@Override
		public void print(Appendable appendable, MonetaryAmount<?> amount)
				throws IOException {
			appendable.append(format(amount));
		}

		@Override
		public MonetaryAmount<?> parse(CharSequence text) throws ParseException {
			throw new UnsupportedOperationException("TestFormat only.");
		}

		@Override
		public MonetaryContext getMonetaryContext() {
			return monetaryContext;
		}

	}

}
