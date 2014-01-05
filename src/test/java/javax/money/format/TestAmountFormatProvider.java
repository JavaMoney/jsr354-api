/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import javax.money.spi.MonetaryAmountFormatProviderSpi;

public class TestAmountFormatProvider implements
		MonetaryAmountFormatProviderSpi {

	private Set<Locale> testSet = new HashSet<Locale>();

	public TestAmountFormatProvider() {
		testSet.add(Locale.ENGLISH);
	}

	@Override
	public MonetaryAmountFormat getAmountFormat(
			AmountStyle formatStyle) {
		return new TestFormat(formatStyle);
	}

	public static final class TestFormat implements MonetaryAmountFormat {

		private AmountStyle formatStyle;
		private MonetaryContext monetaryContext;
		private CurrencyUnit defaultCurrency;

		TestFormat(AmountStyle formatStyle) {
			Objects.requireNonNull(formatStyle);
			this.formatStyle = formatStyle;
		}

		@Override
		public String queryFrom(MonetaryAmount amount) {
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
		public String format(MonetaryAmount amount) {
			return "TestFormat:" + amount.toString();
		}

		@Override
		public void print(Appendable appendable, MonetaryAmount amount)
				throws IOException {
			appendable.append(format(amount));
		}

		@Override
		public MonetaryAmount parse(CharSequence text)
				throws MonetaryParseException {
			throw new UnsupportedOperationException("TestFormat only.");
		}

		@Override
		public MonetaryContext getMonetaryContext() {
			return monetaryContext;
		}

		@Override
		public void setDefaultCurrency(CurrencyUnit currency) {
			this.defaultCurrency = currency;
		}

		@Override
		public void setAmountStyle(AmountStyle style) {
			this.formatStyle = style;
		}

		@Override
		public void setMonetaryContext(MonetaryContext context) {
			this.monetaryContext = context;
		}

	}

}
