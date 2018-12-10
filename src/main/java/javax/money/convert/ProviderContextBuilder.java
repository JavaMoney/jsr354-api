/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
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
package javax.money.convert;

import javax.money.AbstractContextBuilder;
import java.util.*;

/**
 * Builder class to of {@link ProviderContext} instances. Instances of
 * this class are not thread-safe.
 *
 * @author Anatole Tresch
 */
public final class ProviderContextBuilder extends AbstractContextBuilder<ProviderContextBuilder, ProviderContext> {

    /**
     * Create a new Builder instance.
     *
     * @param provider  the provider name, not {@code null}.
     * @param rateTypes the rate types, not null and not empty.
     */
    private ProviderContextBuilder(String provider, RateType rateType, RateType... rateTypes) {
        Objects.requireNonNull(rateType, "At least one RateType is required.");
        Objects.requireNonNull(rateTypes);
        setProviderName(provider);
        Set<RateType> rts = new HashSet<>();
        rts.add(rateType);
        Collections.addAll(rts, rateTypes);
        set(ProviderContext.KEY_RATE_TYPES, rts);
    }

    /**
     * Create a new Builder instance.
     *
     * @param provider  the provider name, not {@code null}.
     * @param rateTypes the rate types, not null and not empty.
     */
    private ProviderContextBuilder(String provider, Collection<RateType> rateTypes) {
        Objects.requireNonNull(rateTypes);
        if (rateTypes.isEmpty()) {
            throw new IllegalArgumentException("At least one RateType is required.");
        }
        setProviderName(provider);
        Set<RateType> rts = new HashSet<>();
        rts.addAll(rateTypes);
        set("rateTypes", rts);
    }

    /**
     * Create a new Builder, hereby using the given {@link ProviderContext}
     * 's values as defaults. This allows changing an existing
     * {@link ProviderContext} easily.
     *
     * @param context the context, not {@code null}
     */
    private ProviderContextBuilder(ProviderContext context) {
        importContext(context);
        Set<RateType> rts = new HashSet<>();
        rts.addAll(context.getRateTypes());
        set(ProviderContext.KEY_RATE_TYPES, rts);
    }

    /**
     * Set the rate types.
     *
     * @param rateTypes the rate types, not null and not empty.
     * @return this, for chaining.
     * @throws IllegalArgumentException when not at least one {@link RateType} is provided.
     */
    public ProviderContextBuilder setRateTypes(RateType... rateTypes) {
        return setRateTypes(Arrays.asList(rateTypes));
    }

    /**
     * Set the rate types.
     *
     * @param rateTypes the rate types, not null and not empty.
     * @return this, for chaining.
     * @throws IllegalArgumentException when not at least one {@link RateType} is provided.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ProviderContextBuilder setRateTypes(Collection<RateType> rateTypes) {
        Objects.requireNonNull(rateTypes);
        if (rateTypes.isEmpty()) {
            throw new IllegalArgumentException("At least one RateType is required.");
        }
        Set rtSet = new HashSet<>();
        rtSet.addAll(rateTypes);
        set(ProviderContext.KEY_RATE_TYPES, rtSet);
        return this;
    }

    /**
     * Creates a new {@link ProviderContext} with the data from this Builder
     * instance.
     *
     * @return a new {@link ProviderContext}. never {@code null}.
     */
    public ProviderContext build() {
        return new ProviderContext(this);
    }

    /**
     * Create a new ProviderContextBuilder, hereby using the given {@link ProviderContext}
     * 's values as defaults. This allows changing an existing
     * {@link ProviderContext} easily.
     *
     * @param context the context, not {@code null}
     * @return a new {@link javax.money.convert.ProviderContextBuilder} instance, never null.
     */
    public static ProviderContextBuilder create(ProviderContext context) {
        return new ProviderContextBuilder(context);
    }

    /**
     * Create a new ProviderContextBuilder instance.
     *
     * @param provider  the provider name, not {@code null}.
     * @param rateTypes the rate types, not null and not empty.
     * @return a new {@link javax.money.convert.ProviderContextBuilder} instance, never null.
     */
    public static ProviderContextBuilder of(String provider, RateType rateType, RateType... rateTypes) {
        return new ProviderContextBuilder(provider, rateType, rateTypes);
    }

    /**
     * Create a new ProviderContextBuilder instance.
     *
     * @param provider  the provider name, not {@code null}.
     * @param rateTypes the rate types, not null and not empty.
     * @return a new {@link javax.money.convert.ProviderContextBuilder} instance, never null.
     */
    public static ProviderContextBuilder of(String provider, Collection<RateType> rateTypes) {
        return new ProviderContextBuilder(provider, rateTypes);
    }

}