/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2020 Werner Keil, Otavio Santana, Trivadis AG
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
package javax.money.convert;

import javax.money.AbstractQueryBuilder;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Builder class for creating new instances of {@link javax.money.convert.ConversionQuery} adding detailed
 * information about a {@link javax.money.convert.CurrencyConversion} instance.
 * <p>
 * Note this class is NOT thread-safe.
 *
 * @see javax.money.convert.MonetaryConversions#getConversion(ConversionQuery)
 */
public final class ConversionQueryBuilder extends AbstractQueryBuilder<ConversionQueryBuilder, ConversionQuery> {

    private ConversionQueryBuilder() {
    }

    /**
     * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
     * returned by {@link java.util.Currency} is used.
     *
     * @param rateTypes the rate types to use, not null.
     * @return the query for chaining.
     */
    public ConversionQueryBuilder setRateTypes(RateType... rateTypes) {
        return set(ConversionQuery.KEY_RATE_TYPES, new HashSet<>(Arrays.asList(rateTypes)));
    }

    /**
     * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
     * returned by {@link java.util.Currency} is used.
     *
     * @param rateTypes the rate types to use, not null.
     * @return the query for chaining.
     */
    public ConversionQueryBuilder setRateTypes(Set<RateType> rateTypes) {
        return set(ConversionQuery.KEY_RATE_TYPES, rateTypes);
    }

    /**
     * Sets the base currency.
     *
     * @param currency the base currency
     * @return the query for chaining.
     */
    public ConversionQueryBuilder setBaseCurrency(CurrencyUnit currency) {
        return set(ConversionQuery.KEY_BASE_CURRENCY, currency);
    }

    /**
     * Sets the base currency.
     *
     * @param currencyCode the currency code, resolvable through {@link javax.money
     *                     .MonetaryCurrencies#getCurrency(String, String...)}, not null.
     * @return the query for chaining.
     */
    public ConversionQueryBuilder setBaseCurrency(String currencyCode) {
        return setBaseCurrency(Monetary.getCurrency(currencyCode));
    }

    /**
     * Sets the term currency.
     *
     * @param currency the base currency
     * @return the query for chaining.
     */
    public ConversionQueryBuilder setTermCurrency(CurrencyUnit currency) {
        return set(ConversionQuery.KEY_TERM_CURRENCY, currency);
    }

    /**
     * Sets the term currency.
     *
     * @param currencyCode the currency code, resolvable through {@link javax.money
     *                     .MonetaryCurrencies#getCurrency(String, String...)}, not null.
     * @return the query for chaining.
     */
    public ConversionQueryBuilder setTermCurrency(String currencyCode) {
        return setTermCurrency(Monetary.getCurrency(currencyCode));
    }

    /**
     * Creates a new instance of {@link ConversionQuery}.
     *
     * @return a new {@link ConversionQuery} instance.
     */
    @Override
    public ConversionQuery build() {
        return new ConversionQuery(this);
    }

    /**
     * Creates a new {@link javax.money.convert.ConversionQueryBuilder} instance.
     *
     * @return a new {@link javax.money.convert.ConversionQueryBuilder} instance, never null.
     */
    public static ConversionQueryBuilder of() {
        return new ConversionQueryBuilder();
    }

    /**
     * Creates a new {@link javax.money.convert.ConversionQueryBuilder} instance.
     *
     * @param query the {@link javax.money.convert.ConversionQuery} instance to be used as a template.
     * @return a new {@link javax.money.convert.ConversionQueryBuilder} instance, never null.
     */
    public static ConversionQueryBuilder of(ConversionQuery query) {
        return new ConversionQueryBuilder().importContext(query);
    }

}
