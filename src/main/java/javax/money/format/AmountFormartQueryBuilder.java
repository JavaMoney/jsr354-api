package javax.money.format;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

import javax.money.AbstractContextBuilder;
import javax.money.MonetaryContext;

/**
 * Builder for queries for accessing/configuring {@link javax.money.format.MonetaryAmountFormat} instances.
 * <p>
 * Note this class is NOT thread-safe.
 */
public final class AmountFormartQueryBuilder extends AbstractContextBuilder<AmountFormartQueryBuilder,AmountFormatQuery>{

	private static final String DEFAULT_STYLE_ID = "default";
	
    /**
     * Creates a new {@link javax.money.format.AmountFormatQuery.AmountFormartQueryBuilder}.
     *
     * @param style the base {@link AmountFormatContext}, not {@code null}.
     */
    public AmountFormartQueryBuilder(AmountFormatQuery style){
        importContext(style);
    }

    /**
     * Creates a new {@link javax.money.format.AmountFormatQuery.AmountFormartQueryBuilder}.
     *
     * @param styleId the target styleId {@link String}, not {@code null}.
     */
    public AmountFormartQueryBuilder(String styleId){
        Objects.requireNonNull(styleId, "styleId required.");
        set(AmountFormatQuery.STYLE_ID, styleId);
    }

    /**
     * Creates a new default {@link javax.money.format.AmountFormatQuery.AmountFormartQueryBuilder} for a formatter based on the
     * locale specific
     * defaults.
     *
     * @param locale the target {@link java.util.Locale}, not {@code null}.
     */
    public AmountFormartQueryBuilder(Locale locale){
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
    public AmountFormartQueryBuilder setStyleId(String styleId){
        return set(AmountFormatQuery.STYLE_ID, styleId);
    }

    /**
     * Sets a Locale to be applied.
     *
     * @param locale the locale, not null.
     * @return the Builder, for chaining.
     */
    public AmountFormartQueryBuilder setLocale(Locale locale){
        return set(locale);
    }

    /**
     * Returns the providers and ordering to be used.
     *
     * @return the ordered providers, never null.
     */
    public AmountFormartQueryBuilder setProviders(Collection<String> providers){
        return setCollection("providers", providers);
    }

    /**
     * Returns the providers and ordering to be used.
     *
     * @return the ordered providers, never null.
     */
    public AmountFormartQueryBuilder setProviders(String... providers){
        return setCollection("providers", Arrays.asList(providers));
    }


    /**
     * Sets the {@link javax.money.MonetaryContext} to be used, when amount's are parsed.
     *
     * @param monetaryContext the monetary context, not {@code null}.
     * @return this builder for chaining.
     */
    public AmountFormartQueryBuilder setMonetaryContext(MonetaryContext monetaryContext){
        Objects.requireNonNull(monetaryContext);
        return set(monetaryContext);
    }

    public AmountFormatQuery build(){
        return new AmountFormatQuery(this);
    }
}