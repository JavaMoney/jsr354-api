/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
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

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Builder for queries for accessing {@link javax.money.CurrencyUnit} instances. If not properties are set the
 * query should
 * returns
 * the <i>default</i> currencies. Similarly if no provider is set explicitly the <i>default</i> ISO currencies as
 * returned by {@link java.util.Currency} should be returned.
 * <p>
 * Note this class is NOT thread-safe.
 */
public final class CurrencyQueryBuilder extends AbstractQueryBuilder<CurrencyQueryBuilder, CurrencyQuery> {

    /**
     * Default constructor.
     */
    private CurrencyQueryBuilder() {
    }

    /**
     * Creates a new instance of {@link javax.money.CurrencyQueryBuilder}.
     *
     * @param currencyQuery {@link javax.money.CurrencyQuery} used for initializing this builder.
     */
    private CurrencyQueryBuilder(CurrencyQuery currencyQuery) {
        Objects.requireNonNull(currencyQuery);
        importContext(currencyQuery);
    }

    /**
     * Sets the country for which currencies should be requested.
     *
     * @param countries The ISO countries.
     * @return the query for chaining.
     */
    public CurrencyQueryBuilder setCountries(Locale... countries) {
        return set(CurrencyQuery.KEY_QUERY_COUNTRIES, Arrays.asList(countries));
    }

    /**
     * Sets the currency code, or the regular expression to select codes.
     *
     * @param codes the currency codes or code expressions, not null.
     * @return the query for chaining.
     */
    public CurrencyQueryBuilder setCurrencyCodes(String... codes) {
        return set(CurrencyQuery.KEY_QUERY_CURRENCY_CODES, Arrays.asList(codes));
    }

    /**
     * Set the numeric code. Setting it to -1 search for currencies that have no numeric code.
     *
     * @param codes the numeric codes.
     * @return the query for chaining.
     */
    public CurrencyQueryBuilder setNumericCodes(int... codes) {
        return set(CurrencyQuery.KEY_QUERY_NUMERIC_CODES,
                Arrays.stream(codes).boxed().collect(Collectors.toList()));
    }

    /**
     * Creates a new instance of {@link javax.money.CurrencyQuery}.
     *
     * @return a new {@link javax.money.CurrencyQuery} instance, never null.
     */
    public CurrencyQuery build() {
        return new CurrencyQuery(this);
    }

    /**
     * Creates a new instance of {@link javax.money.CurrencyQueryBuilder}.
     *
     * @return a new {@link javax.money.CurrencyQueryBuilder} instance, never null.
     */
    public static CurrencyQueryBuilder of() {
        return new CurrencyQueryBuilder();
    }

    /**
     * Creates a new instance of {@link javax.money.CurrencyQueryBuilder}.
     *
     * @param currencyQuery {@link javax.money.CurrencyQuery} used for initializing this builder.
     * @return a new {@link javax.money.CurrencyQueryBuilder} instance, never null.
     */
    public static CurrencyQueryBuilder of(CurrencyQuery currencyQuery) {
        return new CurrencyQueryBuilder(currencyQuery);
    }

}