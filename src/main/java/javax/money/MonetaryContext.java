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
 * This class models the meta data (mostly the numeric capabilities) of a {@link MonetaryAmount} in a
 * platform independent way. It provides information about
 * <ul>
 * <li>the maximal precision supported (0, for unlimited precision).
 * <li>the minimum scale (&gt;=0)
 * <li>the maximal scale (&gt;= -1, -1 for unlimited scale).
 * <li>the numeric representation class.
 * <li>any other attributes, identified by the attribute type, e.g.
 * {@link java.math.RoundingMode}.
 * </ul>
 * <p>
 * This class is immutable, serializable and thread-safe.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 1.5
 */
public final class MonetaryContext extends AbstractContext implements Serializable {

	private static final long serialVersionUID = 500722564420978659L;

	/**
     * Constant that defines under which key the amount type is stored in the context map.
     */
    static final String AMOUNT_TYPE = "amountType";

    /**
     * Key name for the precision.
     */
    private static final String PRECISION = "precision";

    /**
     * Key name for the fixed scale.
     */
    private static final String FIXED_SCALE = "fixedScale";

    /**
     * Key name for the max scale.
     */
    private static final String MAX_SCALE = "maxScale";

    /**
     * Constructor, used from the Builder.
     *
     * @param builder the corresponding builder, not null.
     */
    MonetaryContext(MonetaryContextBuilder builder) {
        super(builder);
    }

    /**
     * Returns the {@code precision} setting. This value is always non-negative.
     *
     * @return an {@code int} which is the value of the {@code precision}
     * setting
     */
    public int getPrecision() {
        Integer val = getInt(PRECISION);
        if (val == null) {
            return 0;
        }
        return val;
    }

    /**
     * Allows to check if {@code scale == maxScale}.
     *
     * @return {@code true} if {@code scale == maxScale}.
     */
    public boolean isFixedScale() {
        Boolean val = getBoolean(FIXED_SCALE);
        if (val == null) {
            return false;
        }
        return val;
    }

    /**
     * Get the maximal scale supported, always {@code >= -1}. Fixed scaled
     * numbers will have {@code scale==maxScale} for all values. {@code -1}
     * declares the maximal scale to be <i>unlimited</i>.
     *
     * @return the maximal scale supported, always {@code >= -1}
     */
    public int getMaxScale() {
        Integer val = getInt(MAX_SCALE);
        if (val == null) {
            return -1;
        }
        return val;
    }

    /**
     * Get the MonetaryAmount implementation class.
     *
     * @return the implementation class of the containing amount instance, never null.
     * @see MonetaryAmount#getContext()
     */
    public Class<? extends MonetaryAmount> getAmountType() {
        Class<?> clazz = get(AMOUNT_TYPE, Class.class);
        return clazz.asSubclass(MonetaryAmount.class);
    }

    /**
     * This method allows to easily of a new MonetaryContext instance from a given {@link javax.money
     * .MonetaryAmountFactoryQuery}.
     *
     * @param monetaryAmountFactoryQuery the monetary amount factory query, not null.
     * @param amountClass                the targeted implementation type.
     * @return a new corresponding MonetaryContext instance.
     */
    public static MonetaryContext from(MonetaryAmountFactoryQuery monetaryAmountFactoryQuery,
                                       Class<? extends MonetaryAmount> amountClass) {
        return MonetaryContextBuilder.of(amountClass).importContext(monetaryAmountFactoryQuery).build();
    }

    /**
     * Creates a new {@link }MonetaryContext).
     *
     * @param monetaryContext the base context, not null.
     * @param amountClass     the target amount class.
     * @return a new corresponding MonetaryContext instance.
     */
    public static MonetaryContext from(MonetaryContext monetaryContext, Class<? extends MonetaryAmount> amountClass) {
        return MonetaryContextBuilder.of(amountClass).importContext(monetaryContext).build();
    }

    /**
     * Creates a new builder instances, initialized with the data from this one.
     *
     * @return a new {@link javax.money.MonetaryContextBuilder} instance, never null.
     */
    public MonetaryContextBuilder toBuilder() {
        return MonetaryContextBuilder.of(this);
    }

}
