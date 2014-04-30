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
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;


/**
 * The {@link AmountFormatContext} defines how a {@link javax.money.MonetaryAmount} should be formatted and
 * is used within a {@link MonetaryAmountFormat}.
 *
 * @author Anatole Tresch
 * @see MonetaryAmountFormat
 */
public final class AmountFormatContext extends AbstractContext implements Serializable{
    /**
     * Style id for default formats.
     */
    public static final String DEFAULT_STYLE_ID = "default";

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -7744853434156071725L;

    /**
     * Constructor.
     *
     * @param builder the {@link Builder} providing the data required.
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
        return getText("styleId");
    }

    /**
     * Access the context's Locale.
     * @return the Locale, or null.
     */
    public Locale getLocale(){
        return getAttribute(Locale.class);
    }

    /**
     * Access the style's {@link javax.money.MonetaryContext}.
     *
     * @return the {@link javax.money.MonetaryContext}, never {@code null}.
     */
    public MonetaryContext getMonetaryContext(){
        return getAttribute(MonetaryContext.class);
    }

    /**
     * Creates a Builder initialized with the settings of this format instance.
     *
     * @return a new Builder instance, never {@code null}.
     */
    public Builder toBuilder(){
        return new Builder(this);
    }

    /**
     * Creates a default instance baed on the given locale settings.
     *
     * @param locale the target locale, not mull.
     * @return a new default context instance.
     */
    public static AmountFormatContext of(Locale locale){
        return new Builder(locale).create();
    }

    /**
     * Builder for creating a new {@link AmountFormatContext}.
     *
     * @author Anatole Tresch
     */
    public static final class Builder extends AbstractBuilder<Builder>{

        /**
         * Creates a new {@link Builder}.
         *
         * @param style the base {@link AmountFormatContext}, not {@code null}.
         */
        public Builder(AmountFormatContext style){
            this.attributes.putAll(style.attributes);
        }


        /**
         * Creates a new {@link Builder}.
         *
         * @param styleId the target styleId {@link String}, not {@code null}.
         */
        public Builder(String styleId){
            Objects.requireNonNull(styleId, "styleId required.");
            setAttribute("styleId", styleId);
        }

        /**
         * Creates a new default {@link Builder} for a formatter based on the locale specific defaults.
         *
         * @param locale the target {@link java.util.Locale}, not {@code null}.
         */
        public Builder(Locale locale){
            Objects.requireNonNull(locale, "locale required.");
            setLocale(locale);
            setAttribute("styleId", DEFAULT_STYLE_ID);
            setObject(locale);
        }

        /**
         * Sets a Locale to be applied.
         * @param locale the locale, not null.
         * @return the Builder, for chaining.
         */
        public Builder setLocale(Locale locale){
            setObject(locale);
            return this;
        }

        /**
         * Sets the {@link javax.money.MonetaryContext} to be used, when amount's are parsed.
         *
         * @param monetaryContext the monetary context, not {@code null}.
         * @return this builder for chaining.
         */
        public Builder setMonetaryContext(MonetaryContext monetaryContext){
            Objects.requireNonNull(monetaryContext);
            setObject(monetaryContext);
            return this;
        }

        /**
         * Creates a new {@link AmountFormatContext}.
         *
         * @return a new {@link AmountFormatContext} instance, never {@code null}.
         * @throws IllegalStateException if no {@link AmountFormatContext} could be created.
         */
        public AmountFormatContext create(){
            return new AmountFormatContext(this);
        }

    }

}
