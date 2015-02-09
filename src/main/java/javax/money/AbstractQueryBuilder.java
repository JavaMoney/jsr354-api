/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money;

import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalUnit;
import java.util.*;

/**
 * This abstract class defines the common generic parts of a query. Queries are used to pass complex parameters sets
 * to lookup monetary artifacts, e.g. {@link MonetaryAmountFactory},
 * {@link javax.money.MonetaryRounding},
 * {@link javax.money.CurrencyUnit}, {@link javax.money.convert.ExchangeRateProvider} and {@link javax.money.convert
 * .CurrencyConversion}.
 * <p>
 * Instances of this class are not thread-safe and not serializable.
 */
public abstract class AbstractQueryBuilder<B extends javax.money.AbstractQueryBuilder, C extends AbstractQuery>
        extends AbstractContextBuilder<B, C> {

    /**
     * Initializes the query builder, as a default query builder.
     */
    public AbstractQueryBuilder() {
    }


    /**
     * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
     * returned by {@link java.util.Currency} is used.
     *
     * @param providers the providers to use, not null.
     * @return the query builder for chaining.
     */
    public B setProviderNames(String... providers) {
        return setProviderNames(Arrays.asList(providers));
    }

    /**
     * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
     * returned by {@link java.util.Currency} is used.
     *
     * @param providers the providers to use, not null.
     * @return the query builder for chaining.
     */
    public B setProviderNames(List<String> providers) {
        Objects.requireNonNull(providers);
        return set(AbstractQuery.KEY_QUERY_PROVIDERS, providers);
    }

    /**
     * Set the target timestamp as {@link java.time.temporal.TemporalAccessor}. This allows to select historical
     * roundings that were valid in the past. Its implementation specific, to what extend historical roundings
     * are available. By default if this property is not set always current {@link  javax.money.MonetaryRounding}
     * instances are provided.
     *
     * @param timestamp the target timestamp
     * @return this instance for chaining
     * @see #setTimestampMillis(long)
     */
    public B setTimestamp(TemporalAccessor timestamp) {
        set(AbstractQuery.KEY_QUERY_TIMESTAMP, Objects.requireNonNull(timestamp));
        return (B) this;
    }

    /**
     * Set the providers to be considered. If not set explicitly the <i>default</i> providers and the corresponding
     * default ordering are used.
     *
     * @param providers the providers in order to use, not null.
     * @return the query builder for chaining.
     */
    public B set(List<String> providers) {
        return set(AbstractQuery.KEY_QUERY_PROVIDERS, providers);
    }

    /**
     * Simple override, that sets the provider as provider to use.
     *
     * @param provider the provider, not null.
     * @return the query builder for chaining.
     */
    @Override
    public B setProviderName(String provider) {
        return setProviderNames(provider);
    }

    /**
     * Sets the target timestamp as UTC millisesonds.
     *
     * @param timestamp the target timestamp
     * @return the query builder for chaining.
     */
    public B setTimestampMillis(long timestamp) {
        return set(AbstractQuery.KEY_QUERY_TIMESTAMP, timestamp);
    }

    /**
     * Sets the target timestamp as {@link java.time.temporal.TemporalUnit}.
     *
     * @param timestamp the target timestamp
     * @return the query builder for chaining.
     */
    public B setTimestamp(TemporalUnit timestamp) {
        return set(AbstractQuery.KEY_QUERY_TIMESTAMP, timestamp);
    }

    /**
     * Sets the target implementation type required. This can be used to explicitly acquire a specific
     * implementation
     * type and use a query to configure the instance or factory to be returned.
     *
     * @param type the target implementation type, not null.
     * @return this query builder for chaining.
     */
    public B setTargetType(Class<?> type) {
        Objects.requireNonNull(type);
        set(AbstractQuery.KEY_QUERY_TARGET_TYPE, type);
        return (B) this;
    }

    /**
     * Creates a new {@link AbstractQuery} with the data from this Builder
     * instance.
     *
     * @return a new {@link AbstractQuery}. never {@code null}.
     */
    public abstract C build();

}