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
