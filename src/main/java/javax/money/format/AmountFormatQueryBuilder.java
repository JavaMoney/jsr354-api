/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money.format;

import javax.money.AbstractContextBuilder;
import javax.money.MonetaryContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

/**
 * Builder for queries for accessing/configuring {@link javax.money.format.MonetaryAmountFormat} instances.
 * <p>
 * Note this class is NOT thread-safe.
 */
public final class AmountFormatQueryBuilder extends AbstractContextBuilder<AmountFormatQueryBuilder,AmountFormatQuery>{

    private static final String DEFAULT_STYLE_ID = "default";

    /**
     * Creates a new {@link AmountFormatQueryBuilder}.
     *
     * @param style the base {@link AmountFormatContext}, not {@code null}.
     */
    private AmountFormatQueryBuilder(AmountFormatQuery style){
        importContext(style);
    }

    /**
     * Creates a new {@link AmountFormatQueryBuilder}.
     *
     * @param styleId the target styleId {@link String}, not {@code null}.
     */
    private AmountFormatQueryBuilder(String styleId){
        Objects.requireNonNull(styleId, "styleId required.");
        set(AmountFormatQuery.STYLE_ID, styleId);
    }

    /**
     * Creates a new default {@link AmountFormatQueryBuilder} for a formatter based on the
     * locale specific
     * defaults.
     *
     * @param locale the target {@link java.util.Locale}, not {@code null}.
     */
    private AmountFormatQueryBuilder(Locale locale){
        Objects.requireNonNull(locale, "locale required.");
        setLocale(locale);
        set(AmountFormatQuery.STYLE_ID, DEFAULT_STYLE_ID);
        set(locale);
    }

    /**
     * Sets a style's id.
     *
     * @param styleId the styleId, not null.
     * @return the Builder, for chaining.
     */
    public AmountFormatQueryBuilder setStyleId(String styleId){
        return set(AmountFormatQuery.STYLE_ID, styleId);
    }

    /**
     * Sets a Locale to be applied.
     *
     * @param locale the locale, not null.
     * @return the Builder, for chaining.
     */
    public AmountFormatQueryBuilder setLocale(Locale locale){
        return set(locale);
    }

    /**
     * Returns the providers and ordering to be used.
     *
     * @return the ordered providers, never null.
     */
    public AmountFormatQueryBuilder setProviders(Collection<String> providers){
        return setCollection("providers", providers);
    }

    /**
     * Returns the providers and ordering to be used.
     *
     * @return the ordered providers, never null.
     */
    public AmountFormatQueryBuilder setProviders(String... providers){
        return setCollection("providers", Arrays.asList(providers));
    }


    /**
     * Sets the {@link javax.money.MonetaryContext} to be used, when amount's are parsed.
     *
     * @param monetaryContext the monetary context, not {@code null}.
     * @return this builder for chaining.
     */
    public AmountFormatQueryBuilder setMonetaryContext(MonetaryContext monetaryContext){
        Objects.requireNonNull(monetaryContext);
        return set(monetaryContext);
    }

    /**
     * Creates a new {@link javax.money.format.AmountFormatQuery} instance.
     *
     * @return a new {@link javax.money.format.AmountFormatQuery} instance, never null.
     */
    public AmountFormatQuery build(){
        return new AmountFormatQuery(this);
    }

    /**
     * Creates a new {@link AmountFormatQueryBuilder} and initializes it with the values from {@code formatQuery.
     *
     * @param formatQuery the base {@link AmountFormatContext}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatQueryBuilder} instance, never null.
     */
    public static AmountFormatQueryBuilder create(AmountFormatQuery formatQuery){
        return new AmountFormatQueryBuilder(formatQuery);
    }

    /**
     * Creates a new {@link AmountFormatQueryBuilder}.
     *
     * @param styleId the target styleId {@link String}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatQueryBuilder} instance, never null.
     */
    public static AmountFormatQueryBuilder create(String styleId){
        return new AmountFormatQueryBuilder(styleId);
    }

    /**
     * Creates a new default {@link AmountFormatQueryBuilder} for a formatter based on the
     * locale specific
     * defaults.
     *
     * @param locale the target {@link java.util.Locale}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatQueryBuilder} instance, never null.
     */
    public static AmountFormatQueryBuilder create(Locale locale){
        return new AmountFormatQueryBuilder(locale);
    }
}