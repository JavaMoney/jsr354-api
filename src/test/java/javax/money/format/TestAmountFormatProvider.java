/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2019 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
