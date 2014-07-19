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
import javax.money.MonetaryAmountFactory;
import java.util.Locale;
import java.util.Objects;


/**
 * The {@link AmountFormatContext} provides details about a {@link javax.money.format.MonetaryAmountFormat}.
 * @see MonetaryAmountFormat#getAmountFormatContext()
 * @author Anatole Tresch
 */
public final class AmountFormatContext extends AbstractContext{

    private static final String DEFAULT_STYLE_ID = "default";
    private static final String STYLE_ID = "styleId";

    /**
     * Creates a new instance of {@link javax.money.format.AmountFormatContext}.
     *
     * @param builder the corresponding builder.
     */
    private AmountFormatContext(Builder builder){
        super(builder);
    }

    /**
     * Access the style's {@link Locale}.
     *
     * @return the {@link Locale}, never {@code null}.
     */
    public String getStyleId(){
        return getText(STYLE_ID);
    }

    /**
     * Access the context's Locale.
     *
     * @return the Locale, or null.
     */
    public Locale getLocale(){
        return get(Locale.class);
    }

    /**
     * Access the format's {@link javax.money.MonetaryAmountFactory} that is used to create new amounts during
     * parsing. If not set explicitly, the default {@link javax.money.MonetaryAmountFactory} is used.
     *
     * @return the {@link javax.money.MonetaryAmountFactory}, never {@code null}.
     */
    public MonetaryAmountFactory<?> getParseFactory(){
        return get(MonetaryAmountFactory.class);
    }

    /**
     * Builder class for creating new instances of {@link javax.money.format.AmountFormatContext} adding detailed information
     * about a {@link javax.money.format.MonetaryAmountFormat} instance.
     * <p/>
     * Note this class is NOT thread-safe.
     *
     * @see MonetaryAmountFormat#getAmountFormatContext()
     */
    public static final class Builder extends AbstractContext.AbstractContextBuilder<Builder,AmountFormatContext>{

        /**
         * Creates a new {@link Builder}.
         *
         * @param style the base {@link AmountFormatContext}, not {@code null}.
         */
        public Builder(AmountFormatQuery style){
            importContext(style);
        }

        /**
         * Creates a new {@link Builder}.
         *
         * @param styleId the target styleId {@link String}, not {@code null}.
         */
        public Builder(String styleId){
            Objects.requireNonNull(styleId, "styleId required.");
            set(STYLE_ID, styleId);
        }

        /**
         * Creates a new default {@link Builder} for a formatter based on the locale specific
         * defaults.
         *
         * @param locale the target {@link java.util.Locale}, not {@code null}.
         */
        public Builder(Locale locale){
            Objects.requireNonNull(locale, "locale required.");
            setLocale(locale);
            set(STYLE_ID, DEFAULT_STYLE_ID);
        }

        /**
         * Sets a style's id.
         *
         * @param styleId the styleId, not null.
         * @return the Builder, for chaining.
         */
        public Builder setStyleId(String styleId){
            return set(STYLE_ID, styleId);
        }

        /**
         * Sets a Locale to be applied.
         *
         * @param locale the locale, not null.
         * @return the Builder, for chaining.
         */
        public Builder setLocale(Locale locale){
            return set(locale);
        }

        /**
         * Sets the {@link javax.money.MonetaryContext} to be used, when amount's are parsed.
         *
         * @param monetaryAmountFactory the monetary amount factory, not {@code null}.
         * @return this builder for chaining.
         */
        public Builder setMonetaryAmountFactory(MonetaryAmountFactory monetaryAmountFactory){
            Objects.requireNonNull(monetaryAmountFactory);
            return set(monetaryAmountFactory, MonetaryAmountFactory.class);
        }

        /**
         * Creates a new instance of {@link AmountFormatContext} that configures a {@link javax.money.format
         * .MonetaryAmountFormat}.
         *
         * @return a new {@link AmountFormatContext} instance.
         */
        @Override
        public AmountFormatContext build(){
            return new AmountFormatContext(this);
        }

    }
}


