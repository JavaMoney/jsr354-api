/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This abstract class defines the common generic parts of a query. Queries are used to pass complex parameters sets
 * to lookup monetary artifacts, e.g. {@link MonetaryAmountFactory},
 * {@link javax.money.MonetaryRounding},
 * {@link javax.money.CurrencyUnit}, {@link javax.money.convert.ExchangeRateProvider} and {@link javax.money.convert
 * .CurrencyConversion}.
 * <p>
 * Instances of this class are not thread-safe and not serializable.
 */
@SuppressWarnings("rawtypes")
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
     * Sets the target implementation type required. This can be used to explicitly acquire a specific
     * implementation
     * type and use a query to configure the instance or factory to be returned.
     *
     * @param type the target implementation type, not null.
     * @return this query builder for chaining.
     */
    @SuppressWarnings("unchecked")
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
    @Override
    public abstract C build();

}