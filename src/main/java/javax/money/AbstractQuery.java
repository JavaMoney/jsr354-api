/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package javax.money;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalUnit;
import java.util.*;

/**
 * Represents a general context of data targeting an item of type {@code Q}. Contexts are used to add arbitrary
 * data that cannot be be mapped in a atandard way to the money API, e.g. use case or customer specific
 * extensions os specialities.<p>
 * Superclasses of this class must be final, immutable, serializable and thread-safe.
 */
public abstract class AbstractQuery extends AbstractContext{

    /** Key for storing the target providers to be queried */
    public static final String QUERY_PROVIDERS = "Query.providers";

    /** Key name for the timestamp attribute. */
    public static final String QUERY_TIMESTAMP = "Query.timestamp";

    /** Key name for the target type attribute. */
    public static final String TARGET_TYPE = "Query.targetType";

    /**
     * Constructor, using a builder.
     * @param builder the builder, not null.
     */
    protected AbstractQuery(AbstractContextBuilder builder){
        super(builder);
    }

    /**
     * Returns the providers and their ordering to be considered. This information typically must be interpreted by the
     * singleton SPI implementations, which are backing the singleton accessors.
     * If the list returned is empty, the default provider list,
     * determined by methods like {@code getDefaultProviderNames()} should be used.
     *
     * @return the ordered providers, never null.
     */
    public List<String> getProviders(){
        return getList(QUERY_PROVIDERS, Collections.emptyList());
    }

    /**
     * Gets the target implementation type required. This can be used to explicitly acquire a specific implementation
     * type and use a query to configure the instance or factory to be returned.
     *
     * @return this Builder for chaining.
     */
    public Class<?> getTargetType(){
        return getAny(TARGET_TYPE, Class.class, null);
    }

    /**
     * Get the current timestamp of the context in UTC milliseconds.  If not set it tries to create an
     * UTC timestamp from #getTimestamp().
     *
     * @return the timestamp in millis, or null.
     */
    public Long getTimestampMillis(){
        Long value = getLong(QUERY_TIMESTAMP, null);
        if(Objects.isNull(value)){
            TemporalAccessor acc = getTimestamp();
            if(Objects.nonNull(acc)){
                return (acc.getLong(ChronoField.INSTANT_SECONDS) * 1000L) + acc.getLong(ChronoField.MILLI_OF_SECOND);
            }
        }
        return value;
    }

    /**
     * Get the current timestamp. If not set it tries to create an Instant from #getTimestampMillis().
     *
     * @return the current timestamp, or null.
     */
    public TemporalAccessor getTimestamp(){
        TemporalAccessor acc = getAny(QUERY_TIMESTAMP, TemporalAccessor.class, null);
        if(Objects.isNull(acc)){
            Long value = getLong(QUERY_TIMESTAMP, null);
            if(Objects.nonNull(value)){
                acc = Instant.ofEpochMilli(value);
            }
        }
        return acc;
    }


    /**
     * This interface defines the common generic parts of a query. Queries are used to pass complex parameters sets
     * to lookup monetary artifacts, e.g. {@link MonetaryAmountFactory},
     * {@link MonetaryRounding},
     * {@link CurrencyUnit}, {@link javax.money.convert.ExchangeRateProvider} and {@link javax.money.convert
     * .CurrencyConversion}.
     * <p>
     * Instances of this class are not thread-safe and not serializable.
     */
    public static abstract class AbstractQueryBuilder<B extends AbstractQueryBuilder, C extends AbstractQuery> extends AbstractContextBuilder<B, C>{

        /**
         * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
         * returned by {@link java.util.Currency} is used.
         *
         * @param providers the providers to use, not null.
         * @return the query for chaining.
         */
        public B setProviders(String... providers){
            Objects.requireNonNull(providers);
            return setList(QUERY_PROVIDERS, Arrays.asList(providers));
        }

        /**
         * Sets the target timestamp as UTC millisesonds.
         *
         * @param timestamp the target timestamp
         * @return the query for chaining.
         */
        public B setTimestampMillis(long timestamp){
            return set(QUERY_TIMESTAMP, timestamp);
        }

        /**
         * Sets the target timestamp as {@link java.time.temporal.TemporalUnit}.
         *
         * @param timestamp the target timestamp
         * @return the query for chaining.
         */
        public B setTimestamp(TemporalUnit timestamp){
            return set(QUERY_TIMESTAMP, timestamp, TemporalUnit.class);
        }

        /**
         * Sets the target implementation type required. This can be used to explicitly acquire a specific implementation
         * type and use a query to configure the instance or factory to be returned.
         *
         * @param type the target implementation type, not null.
         * @return this Builder for chaining.
         */
        public B setTargetType(Class<?> type){
            Objects.requireNonNull(type);
            set(TARGET_TYPE, type, Class.class);
            return (B)this;
        }

    }

}
