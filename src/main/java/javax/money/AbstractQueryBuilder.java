/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2020 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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