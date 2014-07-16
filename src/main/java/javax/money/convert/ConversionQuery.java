/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 */
package javax.money.convert;

import javax.money.AbstractContext;
import javax.money.CurrencyUnit;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalUnit;
import java.util.*;

/**
 * Query for accessing {@link javax.money.convert.CurrencyConversion} instances. If not properties are set the query
 * should returns the <i>default</i> currencies.<p/>
 * This class is immutable, serializable and thread-safe.
 */
public final class ConversionQuery extends AbstractContext{

    public static final ConversionQuery ANY_CONVERSION = new ConversionQueryBuilder().build();

    /**
     * Constructor, used from the ConversionQueryBuilder.
     *
     * @param builder the corresponding builder, not null.
     */
    private ConversionQuery(ConversionQueryBuilder builder){
        super(builder);
    }

    /**
     * Get the providers to be queried. If set, only the providers listed are queried. By default, or if this
     * method returns an empty list, all providers are queried in random order.
     *
     * @return the providers and provider ordering to be considered, or an empty array, but never null.
     */
    public List<String> getProviders(){
        return getList("providers", Collections.emptyList());
    }

    /**
     * Get the rate types set.
     *
     * @return the rate types set, or an empty array, but never null.
     */
    public Set<RateType> getRateTypes(){
        return getSet("rateTypes", Collections.emptySet());
    }

    /**
     * Get the current target timestamp of the query in UTC milliseconds.  If not set it tries to create an
     * UTC timestamp from #getTimestamp().
     *
     * @return the timestamp in millis, or null.
     */
    public Long getTimestampMillis(){
        Long value = getLong("timestamp", null);
        if(Objects.isNull(value)){
            TemporalAccessor acc = getTimestamp();
            if(Objects.nonNull(acc)){
                return (acc.getLong(ChronoField.INSTANT_SECONDS) * 1000L) + acc.getLong(ChronoField.MILLI_OF_SECOND);
            }
        }
        return value;
    }

    /**
     * Get the current target timestamp of the query. If not set it tries to create an Instant from
     * #getTimestampMillis().
     *
     * @return the current timestamp, or null.
     */
    public TemporalAccessor getTimestamp(){
        TemporalAccessor acc = getAny("timestamp", TemporalAccessor.class, null);
        if(Objects.isNull(acc)){
            Long value = getAny("timestamp", Long.class, null);
            if(Objects.nonNull(value)){
                acc = Instant.ofEpochMilli(value);
            }
        }
        return acc;
    }

    /**
     * Get the base currency. This attribute is optional, when a {@link javax.money.convert.CurrencyConversion}
     * is accessed. It is optional if accessing instances of {@link javax.money.convert.ExchangeRateProvider}. If set
     * it can constraint
     * a {@link javax.money.convert.CurrencyConversion} or {@link javax.money.convert.ExchangeRateProvider} to
     * only support one type of base currency. By default it is not set, hereby determining the base currency by the
     * amount onto which the conversion is applied.
     *
     * @return the base CurrencyUnit, or null.
     */
    public CurrencyUnit getBaseCurrency(){
        return getAny("baseCurrency", CurrencyUnit.class, null);
    }

    /**
     * Get the terminating currency. This attribute is required, when a {@link javax.money.convert.CurrencyConversion}
     * is accessed. It is optional if accessing instances of {@link javax.money.convert.ExchangeRateProvider}.
     *
     * @return the terminating CurrencyUnit, or null.
     */
    public CurrencyUnit getTermCurrency(){
        return getAny("termCurrency", CurrencyUnit.class, null);
    }

    /**
     * Creates a new Builder preinitialized with values from this instance.
     * @return a new Builder, never null.
     */
    public ConversionQueryBuilder toBuilder(){
        return new ConversionQueryBuilder().importContext(this);
    }

    /**
     * Builder class for creating new instances of {@link javax.money.convert.ConversionQuery} adding detailed
     * information about a {@link javax.money.convert.CurrencyConversion} instance.
     * <p>
     * Note this class is NOT thread-safe.
     *
     * @see javax.money.convert.MonetaryConversions#getConversion(ConversionQuery)
     */
    public static final class ConversionQueryBuilder
            extends AbstractContext.AbstractContextBuilder<ConversionQueryBuilder,ConversionQuery>{
        /**
         * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
         * returned by {@link java.util.Currency} is used.
         *
         * @param providers the providers to use, not null.
         * @return the query for chaining.
         */
        public ConversionQueryBuilder setProviders(String... providers){
            return setList("providers", Arrays.asList(providers));
        }

        /**
         * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
         * returned by {@link java.util.Currency} is used.
         *
         * @param providers the providers to use, not null.
         * @return the query for chaining.
         */
        public ConversionQueryBuilder setProviders(List<String> providers){
            return setList("providers", providers);
        }

        /**
         * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
         * returned by {@link java.util.Currency} is used.
         *
         * @param rateTypes the rate types to use, not null.
         * @return the query for chaining.
         */
        public ConversionQueryBuilder setRateTypes(RateType... rateTypes){
            return setSet("rateTypes", new HashSet<>(Arrays.asList(rateTypes)));
        }

        /**
         * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
         * returned by {@link java.util.Currency} is used.
         *
         * @param rateTypes the rate types to use, not null.
         * @return the query for chaining.
         */
        public ConversionQueryBuilder setRateTypes(Set<RateType> rateTypes){
            return setSet("rateTypes", rateTypes);
        }

        /**
         * Sets the target timestamp as UTC millisesonds.
         *
         * @param timestamp the target timestamp
         * @return the query for chaining.
         */
        public ConversionQueryBuilder setTimestampMillis(long timestamp){
            return set("timestamp", timestamp);
        }

        /**
         * Sets the target timestamp as {@link java.time.temporal.TemporalUnit}.
         *
         * @param timestamp the target timestamp
         * @return the query for chaining.
         */
        public ConversionQueryBuilder setTimestamp(TemporalUnit timestamp){
            return set("timestamp", timestamp, TemporalUnit.class);
        }

        /**
         * Sets the target base currency.
         *
         * @param currency the base currency
         * @return the query for chaining.
         */
        public ConversionQueryBuilder setBaseCurrency(CurrencyUnit currency){
            return set("baseCurrency", currency, CurrencyUnit.class);
        }

        /**
         * Sets the target term currency.
         *
         * @param currency the base currency
         * @return the query for chaining.
         */
        public ConversionQueryBuilder setTermCurrency(CurrencyUnit currency){
            return set("termCurrency", currency, CurrencyUnit.class);
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
    }
}

