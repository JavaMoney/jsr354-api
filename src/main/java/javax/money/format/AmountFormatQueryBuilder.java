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
package javax.money.format;

import javax.money.AbstractQueryBuilder;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryAmountFactoryQuery;
import java.util.Locale;
import java.util.Objects;

/**
 * Builder for queries for accessing/configuring {@link javax.money.format.MonetaryAmountFormat} instances.
 * <p>
 * Note this class is NOT thread-safe.
 */
public final class AmountFormatQueryBuilder extends AbstractQueryBuilder<AmountFormatQueryBuilder, AmountFormatQuery> {
    /**
     * The default format name used.
     */
    private static final String DEFAULT_FORMAT_NAME = "default";

    /**
     * Creates a new {@link AmountFormatQueryBuilder}.
     *
     * @param formatQuery the base {@link AmountFormatQuery}, not {@code null}.
     */
    private AmountFormatQueryBuilder(AmountFormatQuery formatQuery) {
        importContext(formatQuery);
    }

    /**
     * Creates a new {@link AmountFormatQueryBuilder}.
     *
     * @param formatName the target format's name {@link String}, not {@code null}.
     */
    private AmountFormatQueryBuilder(String formatName) {
        Objects.requireNonNull(formatName, "formatName required.");
        set(AmountFormatQuery.KEY_QUERY_FORMAT_NAME, formatName);
    }

    /**
     * Creates a new default {@link AmountFormatQueryBuilder} for a formatter based on the
     * locale specific
     * defaults.
     *
     * @param locale the target {@link java.util.Locale}, not {@code null}.
     */
    private AmountFormatQueryBuilder(Locale locale) {
        Objects.requireNonNull(locale, "locale required.");
        setLocale(locale);
        set(AmountFormatQuery.KEY_QUERY_FORMAT_NAME, DEFAULT_FORMAT_NAME);
    }

    /**
     * Sets a style's id.
     *
     * @param formatName the format's name, not null.
     * @return the Builder, for chaining.
     */
    public AmountFormatQueryBuilder setFormatName(String formatName) {
        return set(AmountFormatQuery.KEY_QUERY_FORMAT_NAME, formatName);
    }

    /**
     * Sets a Locale to be applied.
     *
     * @param locale the locale, not null.
     * @return the Builder, for chaining.
     */
    public AmountFormatQueryBuilder setLocale(Locale locale) {
        return set(locale);
    }

    /**
     * Sets the {@link javax.money.MonetaryContext} to be used, when amount's are parsed.
     *
     * @param monetaryQuery the monetary query, not {@code null}.
     * @return this builder for chaining.
     */
    public AmountFormatQueryBuilder setMonetaryQuery(MonetaryAmountFactoryQuery monetaryQuery) {
        Objects.requireNonNull(monetaryQuery);
        return set(monetaryQuery);
    }

    /**
     * Sets the {@link javax.money.MonetaryAmountFactory} to be used to of amounts during parsing.
     *
     * @param monetaryFactory the {@link javax.money.MonetaryAmountFactory} to be used, not null.
     * @return this builder for chaining.
     */
    public AmountFormatQueryBuilder setMonetaryAmountFactory(MonetaryAmountFactory<?> monetaryFactory) {
        Objects.requireNonNull(monetaryFactory);
        return set(MonetaryAmountFactory.class, monetaryFactory);
    }

    /**
     * Creates a new {@link javax.money.format.AmountFormatQuery} instance.
     *
     * @return a new {@link javax.money.format.AmountFormatQuery} instance, never null.
     */
    public AmountFormatQuery build() {
        return new AmountFormatQuery(this);
    }

    /**
     * Creates a new {@link AmountFormatQueryBuilder} and initializes it with the values from {@code formatQuery}.
     *
     * @param formatQuery the base {@link AmountFormatContext}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatQueryBuilder} instance, never null.
     */
    public static AmountFormatQueryBuilder of(AmountFormatQuery formatQuery) {
        return new AmountFormatQueryBuilder(formatQuery);
    }

    /**
     * Creates a new {@link AmountFormatQueryBuilder}.
     *
     * @param formatName the target format's name {@link String}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatQueryBuilder} instance, never null.
     */
    public static AmountFormatQueryBuilder of(String formatName) {
        return new AmountFormatQueryBuilder(formatName);
    }

    /**
     * Creates a new default {@link AmountFormatQueryBuilder} for a formatter based on the
     * locale specific
     * defaults.
     *
     * @param locale the target {@link java.util.Locale}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatQueryBuilder} instance, never null.
     */
    public static AmountFormatQueryBuilder of(Locale locale) {
        return new AmountFormatQueryBuilder(locale);
    }


}
