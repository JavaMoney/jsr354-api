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
package javax.money;

import java.io.Serializable;

/**
 * This class models the spec/configuration for a rounding, modeled as {@link javax.money.MonetaryRounding} in a
 * platform independent way. Each RoundingContext instance hereby has a <code>roundingId</code>, which links
 * to the {@link javax.money.spi.RoundingProviderSpi} that must of the according rounding instance. The
 * <i>default</i> <code>roundingId</code> is <code>default</code>.
 * <p>
 * A RoundingContext can take up arbitrary attributes that must be documented by the
 * {@link javax.money.spi.RoundingProviderSpi} implementations.
 * <p>
 * Examples for such additional attributes are
 * {@link java.math.RoundingMode}, {@link java.math.MathContext}, additional regional information,
 * e.g. if a given rounding is targeting cash rounding.
 * <p>
 * This class is immutable, serializable, thread-safe.
 *
 * @author Anatole Tresch
 */
public final class RoundingContext extends AbstractContext implements Serializable, CurrencySupplier {

    private static final long serialVersionUID = -1879443887564347935L;

    /**
     * Attribute key used for the rounding name.
     */
    static final String KEY_ROUNDING_NAME = "roundingName";

    /**
     * Constructor, used from the {@link javax.money.RoundingContextBuilder}.
     *
     * @param builder the corresponding builder, not null.
     */
    RoundingContext(RoundingContextBuilder builder) {
        super(builder);
    }

    /**
     * Get the (custom) rounding id.
     *
     * @return the rounding id, or null.
     */
    public String getRoundingName() {
        return getText(KEY_ROUNDING_NAME);
    }

    /**
     * Get the basic {@link javax.money.CurrencyUnit}, which is based for this rounding type.
     *
     * @return the target CurrencyUnit, or null.
     */
    public CurrencyUnit getCurrency() {
        return get(CurrencyUnit.class);
    }

    /**
     * Allows to convert a instance into the corresponding {@link javax.money.CurrencyContextBuilder}, which allows
     * to change the values and of another {@link javax.money.CurrencyContext} instance.
     *
     * @return a new Builder instance, preinitialized with the values from this instance.
     */
    public RoundingContextBuilder toBuilder() {
        return RoundingContextBuilder.of(this);
    }

}
