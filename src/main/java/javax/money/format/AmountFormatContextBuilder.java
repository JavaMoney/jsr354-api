package javax.money.format;

import java.util.Locale;
import java.util.Objects;

import javax.money.AbstractContextBuilder;
import javax.money.MonetaryAmountFactory;

/**
 * Builder class for creating new instances of {@link javax.money.format.AmountFormatContext} adding detailed information
 * about a {@link javax.money.format.MonetaryAmountFormat} instance.
 * <p/>
 * Note this class is NOT thread-safe.
 *
 * @see MonetaryAmountFormat#getAmountFormatContext()
 */
public final class AmountFormatContextBuilder extends AbstractContextBuilder<AmountFormatContextBuilder,AmountFormatContext>{

	private static final String DEFAULT_STYLE_ID = "default";
	
    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param style the base {@link AmountFormatContext}, not {@code null}.
     */
    public AmountFormatContextBuilder(AmountFormatQuery style){
        importContext(style);
    }

    /**
     * Creates a new {@link AmountFormatContextBuilder}.
     *
     * @param styleId the target styleId {@link String}, not {@code null}.
     */
    public AmountFormatContextBuilder(String styleId){
        Objects.requireNonNull(styleId, "styleId required.");
        set(AmountFormatContext.STYLE_ID, styleId);
    }

    /**
     * Creates a new default {@link AmountFormatContextBuilder} for a formatter based on the locale specific
     * defaults.
     *
     * @param locale the target {@link java.util.Locale}, not {@code null}.
     */
    public AmountFormatContextBuilder(Locale locale){
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
    public AmountFormatContextBuilder setMonetaryAmountFactory(@SuppressWarnings("rawtypes") MonetaryAmountFactory monetaryAmountFactory){
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