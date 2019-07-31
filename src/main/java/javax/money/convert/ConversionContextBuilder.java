/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2019 Werner Keil, Otavio Santana, Trivadis AG
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
import java.util.Objects;

/**
 * Builder class to of {@link ConversionContext} instances. Instances of
 * this class are not thread-safe.
 *
 * @author Anatole Tresch
 */
public final class ConversionContextBuilder extends AbstractContextBuilder<ConversionContextBuilder, ConversionContext> {

    /**
     * Create a new Builder, hereby using the given
     * {@link ConversionContext}'s values as defaults. This allows changing
     * an existing {@link ConversionContext} easily.
     *
     * @param context the context, not {@code null}
     */
    private ConversionContextBuilder(ConversionContext context) {
        importContext(context);
    }

    /**
     * Create a new Builder, hereby using the given
     * {@link ConversionContext}'s values as defaults. This allows changing
     * an existing {@link ConversionContext} easily.
     *
     * @param context  the provider context, not {@code null}
     * @param rateType the rate type, not null.
     */
    private ConversionContextBuilder(ProviderContext context, RateType rateType) {
        importContext(context);
        setRateType(rateType);
    }

    /**
     * Set the historic value.
     *
     * @param rateType the rate type
     * @return this, for chaining.
     */
    public ConversionContextBuilder setRateType(RateType rateType) {
        Objects.requireNonNull(rateType);
        set(rateType);
        return this;
    }

    /**
     * Creates a new {@link ConversionContext} with the data from this
     * Builder instance.
     *
     * @return a new {@link ConversionContext}. never {@code null}.
     */
    public ConversionContext build() {
        return new ConversionContext(this);
    }

    /**
     * Create a new Builder instance without any provider, e.g. for creating
     * new {@link ConversionContext} instances for querying.
     */
    public ConversionContextBuilder() {
        set(RateType.ANY);
    }

    /**
     * Creates a new {@link javax.money.convert.ConversionContextBuilder} instance.
     * @param conversionContext the conversion context to be used to initialize the new builder instance, not null.
     * @return a new {@link javax.money.convert.ConversionContextBuilder} instance, never null.
     */
    public static ConversionContextBuilder of(ConversionContext conversionContext) {
        return new ConversionContextBuilder(conversionContext);
    }

    /**
     * Creates a new {@link javax.money.convert.ConversionContextBuilder} instance.
     *
     * @return a new {@link javax.money.convert.ConversionContextBuilder} instance, never null.
     */
    public static ConversionContextBuilder of() {
        return new ConversionContextBuilder();
    }

    /**
     * Create a new Builder, hereby using the given
     * {@link ConversionContext}'s values as defaults. This allows changing
     * an existing {@link ConversionContext} easily.
     *
     * @param context  the provider context, not {@code null}
     * @param rateType the rate type, not null.
     * @return a new {@link javax.money.convert.ConversionContextBuilder} instance, never null.
     */
    public static ConversionContextBuilder create(ProviderContext context, RateType rateType) {
        return new ConversionContextBuilder(context, rateType);
    }

}
