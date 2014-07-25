package javax.money.convert;

import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.money.AbstractContextBuilder;
import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;

/**
 * Builder class for creating new instances of {@link javax.money.convert.ConversionQuery} adding detailed
 * information about a {@link javax.money.convert.CurrencyConversion} instance.
 * <p>
 * Note this class is NOT thread-safe.
 *
 * @see javax.money.convert.MonetaryConversions#getConversion(ConversionQuery)
 */
public class ConversiontQueryBuilder extends AbstractContextBuilder<ConversiontQueryBuilder,ConversionQuery>{
    /**
     * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
     * returned by {@link java.util.Currency} is used.
     *
     * @param providers the providers to use, not null.
     * @return the query for chaining.
     */
    public ConversiontQueryBuilder setProviders(String... providers){
        return setList("providers", Arrays.asList(providers));
    }

    /**
     * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
     * returned by {@link java.util.Currency} is used.
     *
     * @param providers the providers to use, not null.
     * @return the query for chaining.
     */
    public ConversiontQueryBuilder setProviders(List<String> providers){
        return setList("providers", providers);
    }

    /**
     * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
     * returned by {@link java.util.Currency} is used.
     *
     * @param rateTypes the rate types to use, not null.
     * @return the query for chaining.
     */
    public ConversiontQueryBuilder setRateTypes(RateType... rateTypes){
        return setSet("rateTypes", new HashSet<>(Arrays.asList(rateTypes)));
    }

    /**
     * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
     * returned by {@link java.util.Currency} is used.
     *
     * @param rateTypes the rate types to use, not null.
     * @return the query for chaining.
     */
    public ConversiontQueryBuilder setRateTypes(Set<RateType> rateTypes){
        return setSet("rateTypes", rateTypes);
    }

    /**
     * Sets the target timestamp as UTC millisesonds.
     *
     * @param timestamp the target timestamp
     * @return the query for chaining.
     */
    public ConversiontQueryBuilder setTimestampMillis(long timestamp){
        return set("timestamp", timestamp);
    }

    /**
     * Sets the target timestamp as {@link java.time.temporal.TemporalUnit}.
     *
     * @param timestamp the target timestamp
     * @return the query for chaining.
     */
    public ConversiontQueryBuilder setTimestamp(TemporalUnit timestamp){
        return set("timestamp", timestamp, TemporalUnit.class);
    }

    /**
     * Sets the base currency.
     *
     * @param currency the base currency
     * @return the query for chaining.
     */
    public ConversiontQueryBuilder setBaseCurrency(CurrencyUnit currency){
        return set("baseCurrency", currency, CurrencyUnit.class);
    }

    /**
     * Sets the base currency.
     *
     * @param currencyCode the currency code, resolvable through {@link javax.money
     * .MonetaryCurrencies#getCurrency(String, String...)}, not null.
     * @return the query for chaining.
     */
    public ConversiontQueryBuilder setBaseCurrency(String currencyCode){
        return setBaseCurrency(MonetaryCurrencies.getCurrency(currencyCode));
    }

    /**
     * Sets the term currency.
     *
     * @param currency the base currency
     * @return the query for chaining.
     */
    public ConversiontQueryBuilder setTermCurrency(CurrencyUnit currency){
        return set("termCurrency", currency, CurrencyUnit.class);
    }

    /**
     * Sets the term currency.
     *
     * @param currencyCode the currency code, resolvable through {@link javax.money
     * .MonetaryCurrencies#getCurrency(String, String...)}, not null.
     * @return the query for chaining.
     */
    public ConversiontQueryBuilder setTermCurrency(String currencyCode){
        return setTermCurrency(MonetaryCurrencies.getCurrency(currencyCode));
    }
    
    /**
     * Creates a new instance of {@link ConversionQuery}.
     *
     * @return a new {@link ConversionQuery} instance.
     */
    @Override
    public ConversionQuery build(){
        return new ConversionQuery(this);
    }
    
    public ConversiontQueryBuilder(){}
    
	public static ConversiontQueryBuilder create() {
		return new ConversiontQueryBuilder();
	}
}
