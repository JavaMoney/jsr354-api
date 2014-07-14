/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import javax.money.AbstractContext;
import javax.money.MonetaryContext;
import java.util.*;

/**
 * Query for accessing instances of {@link javax.money.format.MonetaryAmountFormat}. In general it is determined by the
 * implementation, what formats are provided. Nevertheless the following queries must be supported:
 * <ul>
 * <li>Accessing a format based on a Locale, that is also supported by {@link java.text
 * .DecimalFormat#getAvailableLocales()}.</li>
 * </ul>
 * All other formats are optional. For further details about supported formats refer to the implementation's
 * documentation.
 * <p>
 * This class is immutable, thread-safe and serializable.
 */
public final class AmountFormatQuery extends AbstractContext{

    private static final String DEFAULT_STYLE_ID = "default";
    private static final String STYLE_ID = "styleId";

    /**
     * Constructor, used from the Builder.
     *
     * @param builder the corresponding {@link javax.money.format.AmountFormatQuery.AmountFormatQueryBuilder}, not null.
     */
    private AmountFormatQuery(AmountFormatQueryBuilder builder){
        super(builder);
    }

    /**
     * Gets a style's id.
     *
     * @return the styleId, or null.
     */
    public String getStyleId(){
        return getText(STYLE_ID);
    }

    /**
     * Gets a Locale to be applied.
     *
     * @return the style's locale, or null.
     */
    public Locale getLocale(){
        return get(Locale.class);
    }

    /**
     * Gets the {@link javax.money.MonetaryContext} to be used, when amount's are parsed.
     *
     * @return the monetary context, or {@code null}.
     */
    public MonetaryContext getMonetaryContext(){
        return get(MonetaryContext.class);
    }

    /**
     * Returns the providers and ordering to be used.
     *
     * @return the ordered providers, never null.
     */
    public Collection<String> getProviders(){
        return getCollection("providers", Collections.emptySet());
    }

    /**
     * Creates a simple format query based on a singlet Locale, similar to {@link java.text.DecimalFormat#getInstance
     * (java.util.Locale)}.
     *
     * @param locale the target locale, not null.
     */
    public static AmountFormatQuery of(Locale locale){
        return new AmountFormatQueryBuilder(locale).build();
    }

    /**
     * Get a {@link AmountFormatQueryBuilder} preinitialized with this context instance.
     * @return a new preinitialized builder, never null.
     */
    public AmountFormatQueryBuilder toBuilder(){
        return new AmountFormatQueryBuilder(this);
    }


    /**
     * Builder for queries for accessing/configuring {@link javax.money.format.MonetaryAmountFormat} instances.
     * <p>
     * Note this class is NOT thread-safe.
     */
    public static final class AmountFormatQueryBuilder
            extends AbstractContext.AbstractContextBuilder<AmountFormatQueryBuilder,AmountFormatQuery>{

        /**
         * Creates a new {@link AmountFormatQueryBuilder}.
         *
         * @param style the base {@link AmountFormatContext}, not {@code null}.
         */
        public AmountFormatQueryBuilder(AmountFormatQuery style){
            setAll(style);
        }

        /**
         * Creates a new {@link AmountFormatQueryBuilder}.
         *
         * @param styleId the target styleId {@link String}, not {@code null}.
         */
        public AmountFormatQueryBuilder(String styleId){
            Objects.requireNonNull(styleId, "styleId required.");
            set(STYLE_ID, styleId);
        }

        /**
         * Creates a new default {@link AmountFormatQueryBuilder} for a formatter based on the locale specific
         * defaults.
         *
         * @param locale the target {@link java.util.Locale}, not {@code null}.
         */
        public AmountFormatQueryBuilder(Locale locale){
            Objects.requireNonNull(locale, "locale required.");
            setLocale(locale);
            set(STYLE_ID, DEFAULT_STYLE_ID);
            set(locale);
        }

        /**
         * Sets a style's id.
         *
         * @param styleId the styleId, not null.
         * @return the Builder, for chaining.
         */
        public AmountFormatQueryBuilder setStyleId(String styleId){
            return set(STYLE_ID, styleId);
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

        public AmountFormatQuery build(){
            return new AmountFormatQuery(this);
        }

    }
}
