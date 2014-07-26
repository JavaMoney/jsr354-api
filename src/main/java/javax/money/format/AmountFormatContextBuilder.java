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
 * @see MonetaryAmountFormat#getAmountFormatContext()
 */
public final class AmountFormatContextBuilder
        extends AbstractContextBuilder<AmountFormatContextBuilder,AmountFormatContext>{
    /**
     * Map key used for the style identifier attribute.
     */
    private static final String DEFAULT_STYLE_ID = "default";

    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param style the base {@link AmountFormatQuery}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatContextBuilder} instance, never null.
     */
    private AmountFormatContextBuilder(AmountFormatQuery style){
        importContext(style);
    }

    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param context the base {@link AmountFormatContext}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatContextBuilder} instance, never null.
     */
    private AmountFormatContextBuilder(AmountFormatContext context){
        Objects.requireNonNull(context);
        importContext(context);
    }

    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param styleId the target styleId {@link String}, not {@code null}.
     */
    private AmountFormatContextBuilder(String styleId){
        Objects.requireNonNull(styleId, "styleId required.");
        set(AmountFormatContext.STYLE_ID, styleId);
    }

    /**
     * Creates a new default {@link AmountFormatContextBuilder} for a formatter based on the locale specific
     * defaults.
     *
     * @param locale the target {@link java.util.Locale}, not {@code null}.
     */
    private AmountFormatContextBuilder(Locale locale){
        Objects.requireNonNull(locale, "locale required.");
        setLocale(locale);
        set(AmountFormatContext.STYLE_ID, DEFAULT_STYLE_ID);
    }

    /**
     * Sets a style's id.
     *
     * @param styleId the styleId, not null.
     * @return the Builder, for chaining.
     */
    public AmountFormatContextBuilder setStyleId(String styleId){
        return set(AmountFormatContext.STYLE_ID, styleId);
    }

    /**
     * Sets a Locale to be applied.
     *
     * @param locale the locale, not null.
     * @return the Builder, for chaining.
     */
    public AmountFormatContextBuilder setLocale(Locale locale){
        return set(locale);
    }

    /**
     * Sets the {@link javax.money.MonetaryContext} to be used, when amount's are parsed.
     *
     * @param monetaryAmountFactory the monetary amount factory, not {@code null}.
     * @return this builder for chaining.
     */
    public AmountFormatContextBuilder setMonetaryAmountFactory(
            @SuppressWarnings("rawtypes") MonetaryAmountFactory monetaryAmountFactory){
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

    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param style the base {@link AmountFormatContext}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatContextBuilder} instance, never null.
     */
    public static AmountFormatContextBuilder create(AmountFormatQuery style){
        return new AmountFormatContextBuilder(style);
    }

    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param context  the {@link  AmountFormatContext}
     * @return a new {@link javax.money.format.AmountFormatContextBuilder} instance, never null.
     */
    public static AmountFormatContextBuilder create(AmountFormatContext context){
        return new AmountFormatContextBuilder(context);
    }

    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param styleId the target styleId {@link String}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatContextBuilder} instance, never null.
     */
    public static AmountFormatContextBuilder create(String styleId){
        return new AmountFormatContextBuilder(styleId);
    }

    /**
     * Creates a new default {@link AmountFormatContextBuilder} for a formatter based on the locale specific
     * defaults.
     *
     * @param locale the target {@link java.util.Locale}, not {@code null}.
     * @return a new {@link javax.money.format.AmountFormatContextBuilder} instance, never null.
     */
    public static AmountFormatContextBuilder create(Locale locale){
        return new AmountFormatContextBuilder(locale);
    }
}