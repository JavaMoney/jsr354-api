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
package javax.money;

import java.util.Objects;

/**
 * Builder class for creating new instances of {@link RoundingContext} adding detailed information
 * about a {@link MonetaryRounding} instance.
 * <p>
 * Note this class is NOT thread-safe.
 *
 * @see javax.money.MonetaryRounding#getRoundingContext() ()
 */
public final class RoundingContextBuilder extends AbstractContextBuilder<RoundingContextBuilder, RoundingContext> {

    /**
     * Creates a new builder.
     *
     * @param provider   the provider name, creating the corresponding {@link MonetaryRounding}
     *                   containing, not null.
     *                   the final {@link RoundingContext} created by this builder, not null.
     * @param roundingId The name of the rounding, not null.
     */
    private RoundingContextBuilder(String provider, String roundingId) {
        Objects.requireNonNull(provider);
        set(RoundingContext.KEY_PROVIDER, provider);
        Objects.requireNonNull(roundingId);
        set(RoundingContext.KEY_ROUNDING_NAME, roundingId);
    }

    /**
     * Creates a new RoundingContextBuilder.
     *
     * @param roundingContext the rounding context, used as a template, not null.
     */
    private RoundingContextBuilder(RoundingContext roundingContext) {
        importContext(roundingContext);
    }

    /**
     * Get the basic {@link CurrencyUnit}, which is based for this rounding type.
     *
     * @return the target CurrencyUnit, or null.
     */
    public RoundingContextBuilder setCurrency(CurrencyUnit currencyUnit) {
        Objects.requireNonNull(currencyUnit);
        return set(CurrencyUnit.class, currencyUnit);
    }

    /**
     * Creates a new instance of {@link javax.money.RoundingContext}.
     *
     * @return a new {@link javax.money.RoundingContext} instance.
     */
    @Override
    public RoundingContext build() {
        return new RoundingContext(this);
    }

    /**
     * Creates a new RoundingContextBuilder.
     *
     * @param provider   the provider name, creating the corresponding {@link MonetaryRounding}
     *                   containing, not null.
     *                   the final {@link RoundingContext} created by this builder, not null.
     * @param roundingId The name of the rounding, not null.
     * @return a new {@link javax.money.MonetaryContextBuilder} instance, never null.
     */
    public static RoundingContextBuilder of(String provider, String roundingId) {
        return new RoundingContextBuilder(provider, roundingId);
    }

    /**
     * Creates a new RoundingContextBuilder.
     *
     * @param roundingContext the rounding context, used as a template, not null.
     * @return a new {@link javax.money.MonetaryContextBuilder} instance, never null.
     */
    public static RoundingContextBuilder of(RoundingContext roundingContext) {
        return new RoundingContextBuilder(roundingContext);
    }

}