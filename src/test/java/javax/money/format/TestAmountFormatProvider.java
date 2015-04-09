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
import java.util.*;

import javax.money.MonetaryAmount;
import javax.money.spi.MonetaryAmountFormatProviderSpi;

public class TestAmountFormatProvider implements
        MonetaryAmountFormatProviderSpi {

    private final Set<Locale> testSet = new HashSet<>();

    public TestAmountFormatProvider() {
        testSet.add(Locale.ENGLISH);
    }

    @Override
    public Collection<MonetaryAmountFormat> getAmountFormats(
            AmountFormatQuery formatStyle) {
        if (formatStyle.getProviderNames().contains("foo")) {
            return Collections.emptyList();
        }
        Locale loc = formatStyle.getLocale();
        if (Objects.nonNull(loc) && "BAR".equals(loc.getCountry()) && "foo".equals(loc.getLanguage())) {
            return Collections.emptySet();
        }
        List<MonetaryAmountFormat> result = new ArrayList<>();
        result.add(new TestFormat(formatStyle));
        return result;
    }

    @Override
    public Set<Locale> getAvailableLocales() {
        return Collections.unmodifiableSet(testSet);
    }

    @Override
    public Set<String> getAvailableFormatNames() {
        return Collections.emptySet();
    }

    public static final class TestFormat implements MonetaryAmountFormat {

        private final AmountFormatContext formatStyle;

        TestFormat(AmountFormatQuery formatStyle) {
            Objects.requireNonNull(formatStyle);
            this.formatStyle = AmountFormatContextBuilder.create(formatStyle).build();
        }

        @Override
        public String queryFrom(MonetaryAmount amount) {
            return toString();
        }

        @Override
        public AmountFormatContext getContext() {
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
