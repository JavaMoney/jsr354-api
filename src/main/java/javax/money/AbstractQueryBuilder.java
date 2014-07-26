/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money;

import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This abstract class defines the common generic parts of a query. Queries are used to pass complex parameters sets
 * to lookup monetary artifacts, e.g. {@link javax.money.MonetaryAmountFactory},
 * {@link javax.money.MonetaryRounding},
 * {@link javax.money.CurrencyUnit}, {@link javax.money.convert.ExchangeRateProvider} and {@link javax.money.convert
 * .CurrencyConversion}.
 * <p>
 * Instances of this class are not thread-safe and not serializable.
 */
public abstract class AbstractQueryBuilder<B extends javax.money.AbstractQueryBuilder, C extends AbstractQuery>
        extends AbstractContextBuilder<B,C>{

    /**
     * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
     * returned by {@link java.util.Currency} is used.
     *
     * @param providers the providers to use, not null.
     * @return the query builder for chaining.
     */
    public B setProviders(String... providers){
        Objects.requireNonNull(providers);
        return setList(AbstractQuery.QUERY_PROVIDERS, Arrays.asList(providers));
    }

    /**
     * Set the providers to be considered. If not set explicitly the <i>default</i> providers and the corresponding
     * default ordering are used.
     *
     * @param providers the providers in order to use, not null.
     * @return the query builder for chaining.
     */
    public B setProviders(List<String> providers){
        return setList("providers", providers);
    }

    /**
     * Sets the target timestamp as UTC millisesonds.
     *
     * @param timestamp the target timestamp
     * @return the query builder for chaining.
     */
    public B setTimestampMillis(long timestamp){
        return set(AbstractQuery.QUERY_TIMESTAMP, timestamp);
    }

    /**
     * Sets the target timestamp as {@link java.time.temporal.TemporalUnit}.
     *
     * @param timestamp the target timestamp
     * @return the query builder for chaining.
     */
    public B setTimestamp(TemporalUnit timestamp){
        return set(AbstractQuery.QUERY_TIMESTAMP, timestamp, TemporalUnit.class);
    }

    /**
     * Sets the target implementation type required. This can be used to explicitly acquire a specific
     * implementation
     * type and use a query to configure the instance or factory to be returned.
     *
     * @param type the target implementation type, not null.
     * @return this query builder for chaining.
     */
    public B setTargetType(Class<?> type){
        Objects.requireNonNull(type);
        set(AbstractQuery.TARGET_TYPE, type, Class.class);
        return (B) this;
    }

}