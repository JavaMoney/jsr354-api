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
package javax.money.format;

import javax.money.AbstractContextBuilder;
import javax.money.MonetaryAmountFactory;
import java.util.Locale;
import java.util.Objects;

/**
 * Builder class for creating new instances of {@link javax.money.format.AmountFormatContext} adding detailed
 * information
 * about a {@link javax.money.format.MonetaryAmountFormat} instance.
 * <p>
 * Note this class is NOT thread-safe.
 *
 * @see MonetaryAmountFormat#getContext()
 */
public final class AmountFormatContextBuilder
        extends AbstractContextBuilder<AmountFormatContextBuilder, AmountFormatContext> {
    /**
     * Map key used for the style identifier attribute.
     */
    private static final String DEFAULT_FORMAT_NAME = "default";

    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param formatQuery the base {@link AmountFormatQuery}, not {@code null}.
     */
    private AmountFormatContextBuilder(AmountFormatQuery formatQuery) {
        importContext(formatQuery);
    }

    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param context the base {@link AmountFormatContext}, not {@code null}.
     */
    private AmountFormatContextBuilder(AmountFormatContext context) {
        Objects.requireNonNull(context);
        importContext(context);
    }

    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param formatName the target formatName {@link String}, not {@code null}.
     */
    private AmountFormatContextBuilder(String formatName) {
        Objects.requireNonNull(formatName, "formatName required.");
        set(AmountFormatContext.KEY_FORMAT_NAME, formatName);
    }

    /**
     * Creates a new default {@link AmountFormatContextBuilder} for a formatter based on the locale specific
     * defaults.
     *
     * @param locale the target {@link java.util.Locale}, not {@code null}.
     */
    private AmountFormatContextBuilder(Locale locale) {
        Objects.requireNonNull(locale, "locale required.");
        setLocale(locale);
        set(AmountFormatContext.KEY_FORMAT_NAME, DEFAULT_FORMAT_NAME);
    }

    /**
     * Sets a format's name.
     *
     * @param formatName the formatName, not null.
     * @return the Builder, for chaining.
     */
    public AmountFormatContextBuilder setFormatName(String formatName) {
        return set(AmountFormatContext.KEY_FORMAT_NAME, formatName);
    }

    /**
     * Sets a Locale to be applied.
     *
     * @param locale the locale, not null.
     * @return the Builder, for chaining.
     */
    public AmountFormatContextBuilder setLocale(Locale locale) {
        return set(locale);
    }

    /**
     * Sets the {@link javax.money.MonetaryContext} to be used, when amount's are parsed.
     *
     * @param monetaryAmountBuilder the monetary amount factory, not {@code null}.
     * @return this builder for chaining.
     */
    public AmountFormatContextBuilder setMonetaryAmountFactory(
            @SuppressWarnings("rawtypes") MonetaryAmountFactory monetaryAmountBuilder) {
        Objects.requireNonNull(monetaryAmountBuilder);
        return set(MonetaryAmountFactory.class, monetaryAmountBuilder);
    }

    /**
     * Creates a new instance of {@link AmountFormatContext} that configures a {@link javax.money.format
     * .MonetaryAmountFormat}.
     *
     * @return a new {@link AmountFormatContext} instance.
     */
    @Override
    public AmountFormatContext build() {
        return new AmountFormatContext(this);
    }

    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param formatQuery the base {@link AmountFormatContext}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatContextBuilder} instance, never null.
     */
    public static AmountFormatContextBuilder create(AmountFormatQuery formatQuery) {
        return new AmountFormatContextBuilder(formatQuery);
    }

    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param context the {@link  AmountFormatContext}
     * @return a new {@link javax.money.format.AmountFormatContextBuilder} instance, never null.
     */
    public static AmountFormatContextBuilder of(AmountFormatContext context) {
        return new AmountFormatContextBuilder(context);
    }

    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param formatName the target formatName {@link String}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatContextBuilder} instance, never null.
     */
    public static AmountFormatContextBuilder of(String formatName) {
        return new AmountFormatContextBuilder(formatName);
    }

    /**
     * Creates a new default {@link AmountFormatContextBuilder} for a formatter based on the locale specific
     * defaults.
     *
     * @param locale the target {@link java.util.Locale}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatContextBuilder} instance, never null.
     */
    public static AmountFormatContextBuilder of(Locale locale) {
        return new AmountFormatContextBuilder(locale);
    }
}