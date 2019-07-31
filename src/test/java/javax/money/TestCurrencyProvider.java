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
package javax.money;

import javax.money.spi.CurrencyProviderSpi;
import java.util.*;

public final class TestCurrencyProvider implements CurrencyProviderSpi {

    @Override
    public Set<CurrencyUnit> getCurrencies(CurrencyQuery currencyQuery) {
        Set<CurrencyUnit> result = new HashSet<>();
        if (!currencyQuery.getCurrencyCodes().isEmpty()) {
            for (String currencyCode : currencyQuery.getCurrencyCodes()) {
                switch (currencyCode) {
                    case "test1":
                        result.add(new TestCurrency("test1", 1, 2));
                        break;
                    case "test2":
                        result.add(new TestCurrency("test2", 1, 2));
                        break;
                    case "error":
                        throw new IllegalArgumentException("error encountered!");
                    case "invalid":
                        result.add(new TestCurrency("invalid2", 1, 2));
                        break;
                    default:
                        break;
                }
            }
            return result;
        }
        if (!currencyQuery.getCountries().isEmpty()) {
            for (Locale country : currencyQuery.getCountries()) {
                if ("TEST1L".equals(country.getCountry())) {
                    result.add(new TestCurrency("TEST1L", 1, 2));
                } else if (Locale.CHINA.equals(country)) {
                    throw new IllegalArgumentException("CHINA error encountered!");
                } else if (Locale.CHINESE.equals(country)) {
                    result.add(new TestCurrency("invalid2", 1, 2));
                }
            }
            return result;
        }
        return Collections.emptySet();
    }

    @Override
    public boolean isCurrencyAvailable(CurrencyQuery context) {
        Collection<String> currencyCodes = context.getCurrencyCodes();
        for (String currencyCode : currencyCodes) {
            switch (currencyCode) {
                case "test1":
                case "error":
                case "invalid":
                    return true;
            }
        }
        Collection<Locale> countries = context.getCountries();
        for (Locale country : countries) {
            if ("TEST1L".equals(country.getCountry())) {
                return true;
            } else if (Locale.CHINA.equals(country)) {
                return true;
            } else if (Locale.CHINESE.equals(country)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String getProviderName() {
        return "test";
    }


    private static final class TestCurrency implements CurrencyUnit {

        private final String code;
        private final int numCode;
        private final int digits;
        private static final CurrencyContext CONTEXT = CurrencyContextBuilder.of("TestCurrencyProvider").build();

        TestCurrency(String code, int numCode, int digits) {
            this.code = code;
            this.numCode = numCode;
            this.digits = digits;
        }

        @Override
        public String getCurrencyCode() {
            return code;
        }

        @Override
        public int getNumericCode() {
            return numCode;
        }

        @Override
        public int getDefaultFractionDigits() {
            return digits;
        }

        @Override
        public CurrencyContext getContext() {
            return CONTEXT;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(code);
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof TestCurrency) {
                TestCurrency other = (TestCurrency) obj;
                return Objects.equals(code, other.code);
            }
            return false;
        }

        @Override
        public int compareTo(CurrencyUnit o) {
            Objects.requireNonNull(o);
            return getCurrencyCode().compareTo(o.getCurrencyCode());
        }
    }

}
