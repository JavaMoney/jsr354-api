/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.io.Serializable;

/**
 * This class models the meta data (mostly the numeric capabilities) of a {@link MonetaryAmount} in a
 * platform independent way. It provides information about
 * <ul>
 * <li>the maximal precision supported (0, for unlimited precision).
 * <li>the minimum scale (>=0)
 * <li>the maximal scale (>= -1, -1 for unlimited scale).
 * <li>the numeric representation class.
 * <li>any other attributes, identified by the attribute type, e.g.
 * {@link java.math.RoundingMode}.
 * </ul>
 * <p>
 * This class is immutable, serializable and thread-safe.
 *
 * @author Anatole Tresch
 */
public final class MonetaryContext extends AbstractContext implements Serializable {

	private static final long serialVersionUID = 500722564420978659L;

	/**
     * Constant that defines under which key the amount type is stored in the context map.
     */
    static final String AMOUNT_TYPE = "amountType";

    /**
     * Key name for the context.
     */
    private static final String PRECISION = "precision";

    /**
     * Key name for the currency provider.
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
     * Allows to check if {@code minScale == maxScale}.
     *
     * @return {@code true} if {@code minScale == maxScale}.
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
