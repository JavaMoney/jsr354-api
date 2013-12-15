/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import java.io.IOException;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import javax.money.spi.MonetaryAmountFormatProviderSpi;

public class TestAmountFormatProvider implements
		MonetaryAmountFormatProviderSpi {

	@Override
	public MonetaryAmountFormat getFormat(Locale locale,
			MonetaryContext monetaryContext,
			CurrencyUnit defaultCurrency) {
		return new TestFormat(locale, null, monetaryContext,
				defaultCurrency);
	}

	@Override
	public MonetaryAmountFormat getFormat(
			AmountStyle formatStyle, MonetaryContext monetaryContext,
			CurrencyUnit defaultCurrency) {
		return new TestFormat(null, formatStyle, monetaryContext,
				defaultCurrency);
	}

	public static final class TestFormat implements MonetaryAmountFormat {

		private AmountStyle formatStyle = new AmountStyle.Builder(
				Locale.ENGLISH)
				.build();
		private MonetaryContext monetaryContext;
		private CurrencyUnit defaultCurrency;

		TestFormat(Locale locale,
				AmountStyle formatStyle, MonetaryContext<?> monetaryContext,
				CurrencyUnit defaultCurrency) {
			if (locale != null && formatStyle == null) {
				formatStyle = new AmountStyle.Builder(locale)
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
		public AmountStyle getAmountStyle() {
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
		public MonetaryAmount<?> parse(CharSequence text)
				throws MonetaryParseException {
			throw new UnsupportedOperationException("TestFormat only.");
		}

		@Override
		public MonetaryContext getMonetaryContext() {
			return monetaryContext;
		}

	}

}
