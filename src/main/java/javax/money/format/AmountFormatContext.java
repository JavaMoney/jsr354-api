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
     * The target {@link String} style identifier.
     */
    private String styleId;
    /**
     * The target {@link javax.money.MonetaryContext} to be used, when amount's are parsed with this instance.
     */
    private MonetaryContext monetaryContext;


    /**
     * Constructor.
     *
     * @param builder the {@link Builder} providing the data required.
     */
    private AmountFormatContext(Builder builder){
        super(builder);
        Objects.requireNonNull(builder.styleId, "styleId required.");
        this.styleId = builder.styleId;
    }


    /**
     * Access the style's {@link Locale}.
     *
     * @return the {@link Locale}, never {@code null}.
     */
    public String getStyleId(){
        return styleId;
    }

    /**
     * Access the style's {@link javax.money.MonetaryContext}.
     *
     * @return the {@link javax.money.MonetaryContext}, never {@code null}.
     */
    public MonetaryContext getMonetaryContext(){
        return monetaryContext;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((styleId == null) ? 0 : styleId.hashCode());
        result = prime * result + ((monetaryContext == null) ? 0 : monetaryContext.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        AmountFormatContext other = (AmountFormatContext) obj;
        if(!super.equals(obj)){
            return false;
        }
        if(styleId == null){
            if(other.styleId != null){
                return false;
            }
        }else if(!styleId.equals(other.styleId)){
            return false;
        }
        if(monetaryContext == null){
            if(other.monetaryContext != null){
                return false;
            }
        }else if(!monetaryContext.equals(other.monetaryContext)){
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        return "AmountFormatContext [styleId=" + styleId + ", monetaryContext=" + monetaryContext + ", " +
                super.toString() + "]";
    }

    /**
     * Builder for creating a new {@link AmountFormatContext}.
     *
     * @author Anatole Tresch
     */
    public static final class Builder extends AbstractBuilder<Builder>{

        /**
         * The target {@link Locale} to be used.
         */
        private String styleId;
        /**
         * By default use the default MonetaryContext.
         */
        private MonetaryContext monetaryContext = MonetaryContext.DEFAULT_CONTEXT;

        /**
         * Creates a new {@link Builder}.
         *
         * @param style the base {@link AmountFormatContext}, not {@code null}.
         */
        public Builder(AmountFormatContext style){
            Objects.requireNonNull(style, "style required.");
            this.styleId = style.styleId;
            this.attributes.putAll(style.attributes);
        }


        /**
         * Creates a new {@link Builder}.
         *
         * @param styleId the target styleId {@link String}, not {@code null}.
         */
        public Builder(String styleId){
            Objects.requireNonNull(styleId, "styleId required.");
            this.styleId = styleId;
        }

        /**
         * Creates a new default {@link Builder} for a formatter based on the locale specific defaults.
         *
         * @param locale the target {@link java.util.Locale}, not {@code null}.
         */
        public Builder(Locale locale){
            Objects.requireNonNull(locale, "locale required.");
            this.styleId = DEFAULT_STYLE_ID;
            setAttribute(locale);
        }

        /**
         * Sets the {@link javax.money.MonetaryContext} to be used, when amount's are parsed.
         *
         * @param monetaryContext the monetary context, not {@code null}.
         * @return this builder for chaining.
         */
        public Builder setMonetaryContext(MonetaryContext monetaryContext){
            Objects.requireNonNull(monetaryContext);
            this.monetaryContext = monetaryContext;
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

        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString(){
            return "FormatStyle.Builder [styleId=" + styleId + ", monetaryContext=" + monetaryContext + "]";
        }

    }

}
