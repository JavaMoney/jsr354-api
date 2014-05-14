/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import javax.money.spi.MonetaryAmountFormatProviderSpi;
import java.io.IOException;
import java.util.*;

public class TestAmountFormatProvider implements
		MonetaryAmountFormatProviderSpi {

	private Set<Locale> testSet = new HashSet<>();

	public TestAmountFormatProvider() {
		testSet.add(Locale.ENGLISH);
	}

    @Override
    public String getStyleId(){
        return "default";
    }

    @Override
	public MonetaryAmountFormat getAmountFormat(
			AmountFormatContext formatStyle) {
        Locale loc = formatStyle.getAttribute(Locale.class);
        if(loc != null && "BAR".equals(loc.getCountry()) && "foo".equals(loc.getLanguage())){
            return null;
        }
		return new TestFormat(formatStyle);
	}

    @Override
    public Set<Locale> getAvailableLocales(){
        return Collections.unmodifiableSet(testSet);
    }

    public static final class TestFormat implements MonetaryAmountFormat {

		private AmountFormatContext formatStyle;
		private MonetaryContext monetaryContext;
		private CurrencyUnit defaultCurrency;

		TestFormat(AmountFormatContext formatStyle) {
			Objects.requireNonNull(formatStyle);
			this.formatStyle = formatStyle;
		}

		@Override
		public String queryFrom(MonetaryAmount amount) {
			return toString();
		}

		@Override
		public AmountFormatContext getAmountFormatContext() {
			return formatStyle;
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

	}

}
