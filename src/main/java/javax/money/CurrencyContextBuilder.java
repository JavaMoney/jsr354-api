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
 * Builder class for creating new instances of {@link CurrencyContext} adding detailed information
 * about a {@link CurrencyUnit} instance. Typically the
 * contexts are created and assigned by the classes that implement the {@link javax.money.spi.CurrencyProviderSpi}.
 * The according implementation classes should document, which attributes are available.
 * <p>
 * Note this class is NOT thread-safe.
 *
 * @see javax.money.CurrencyUnit#getContext()
 */
public final class CurrencyContextBuilder extends AbstractContextBuilder<CurrencyContextBuilder, CurrencyContext> {

    /**
     * Creates a new builder.
     *
     * @param provider the provider name, creating the corresponding {@link CurrencyUnit} containing
     *                 the final {@link CurrencyContext} created by this builder, not null.
     */
    private CurrencyContextBuilder(String provider) {
        Objects.requireNonNull(provider);
        setProviderName(provider);
    }

    /**
     * Creates a new builder.
     *
     * @param context the {@link javax.money.CurrencyContext} to be used for initializing this builder.
     */
    private CurrencyContextBuilder(CurrencyContext context) {
        Objects.requireNonNull(context);
        importContext(context);
    }

    /**
     * Creates a new instance of {@link javax.money.CurrencyContext}.
     *
     * @return a new {@link javax.money.CurrencyContext} instance.
     */
    @Override
    public CurrencyContext build() {
        return new CurrencyContext(this);
    }

    /**
     * Creates a new builder.
     *
     * @param provider the provider name, creating the corresponding {@link CurrencyUnit} containing
     *                 the final {@link CurrencyContext} created by this builder, not null.
     * @return a new {@link javax.money.CurrencyContextBuilder} instance, never null.
     */
    public static CurrencyContextBuilder of(String provider) {
        return new CurrencyContextBuilder(provider);
    }

    /**
     * Creates a new builder.
     *
     * @param context the {@link javax.money.CurrencyContext} to be used for initializing this builder.
     * @return a new {@link javax.money.CurrencyContextBuilder} instance, never null.
     */
    public static CurrencyContextBuilder of(CurrencyContext context) {
        return new CurrencyContextBuilder(context);
    }

}