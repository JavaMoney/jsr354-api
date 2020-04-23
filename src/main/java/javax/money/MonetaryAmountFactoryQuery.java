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

import java.io.Serializable;

/**
 * Query to lookup instances of {@link MonetaryAmountFactory}, which are determined by the (minimal)
 * capabilities required by the give use case. By default amount factories can be queried by
 * <ul>
 * <li>The maximal scale needed (THE ADDITIONAL <i>fixed scale</i> allows to define the minimal an maximal scale to
 * be the same).</li>
 * <li>The maximal numeric precision required.</li>
 * <li>the target {@link javax.money.MonetaryAmount} implementation type.</li>
 * <li>any other attributes, currently supported by the current factory and query implementation registered.</li>
 * </ul>
 * <p>This class is thread-safe, final and serializable.</p>
 *
 * @see Monetary#getAmountFactory(MonetaryAmountFactoryQuery)
 * @see MonetaryAmountFactory
 */
public final class MonetaryAmountFactoryQuery extends AbstractQuery implements Serializable {

	private static final long serialVersionUID = -6961037049540444782L;

	/**
     * Key name for the context.
     */
    private static final String KEY_PRECISION = "precision";

    /**
     * Key name for the currency provider.
     */
    private static final String KEY_FIXED_SCALE = "fixedScale";

    /**
     * Key name for the max scale.
     */
    private static final String KEY_MAX_SCALE = "maxScale";

    /**
     * Constructor, used from the {@link MonetaryAmountFactoryQueryBuilder}.
     *
     * @param builder the corresponding builder, not null.
     */
    MonetaryAmountFactoryQuery(MonetaryAmountFactoryQueryBuilder builder) {
        super(builder);
    }

    /**
     * Get the maximal scale to be supported.
     *
     * @return the maximal scale, or null, if this attribute must not be considered.
     */
    public Integer getMaxScale() {
        return getInt(KEY_MAX_SCALE);
    }

    /**
     * Get the maximal precision to be supported.
     *
     * @return the maximal precision, or null, if this attribute must not be considered.
     */
    public Integer getPrecision() {
        return getInt(KEY_PRECISION);
    }

    /**
     * Get the fixed scale flag to be supported. A fixed scale hereby means that the scale is always equal to
     * the max scale (it could never be less).
     *
     * @return the fixed scale flag, or null, if this attribute must not be considered.
     */
    public Boolean isFixedScale() {
        return getBoolean(KEY_FIXED_SCALE);
    }

    /**
     * Creates a new builder instances, initialized with the data from this one.
     *
     * @return a new {@link MonetaryAmountFactoryQueryBuilder} instance, never null.
     */
    public MonetaryAmountFactoryQueryBuilder toBuilder() {
        return MonetaryAmountFactoryQueryBuilder.of(this);
    }

}
