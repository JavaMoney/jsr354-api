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
 * Query to lookup indtsnvrd of {@link javax.money.MonetaryAmountFactory}, which are determined by the (minimal)
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
 * @see MonetaryAmounts#getAmountFactory(MonetaryAmountFactoryQuery)
 * @see javax.money.MonetaryAmountFactory
 */
public final class MonetaryAmountFactoryQuery extends AbstractQuery implements Serializable{

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
     * Constructor, used from the {@link javax.money.MonetaryAmountFactoryQuery.Builder}.
     *
     * @param builder the corresponding builder, not null.
     */
    private MonetaryAmountFactoryQuery(Builder builder){
        super(builder);
    }

    /**
     * Get the maximal scale to be supported.
     *
     * @return the maximal scale, or null, if this attribute must not be considered.
     */
    public Integer getMaxScale(){
        return getInt(MAX_SCALE, null);
    }

    /**
     * Get the maximal precision to be supported.
     *
     * @return the maximal precision, or null, if this attribute must not be considered.
     */
    public Integer getPrecision(){
        return getInt(PRECISION, null);
    }

    /**
     * Get the fixed scale flag to be supported. A fixed scale hereby means that the scale is always equal to
     * the max scale (it could never be less).
     *
     * @return the fixed scale flag, or null, if this attribute must not be considered.
     */
    public Boolean getFixedScale(){
        return getBoolean("fixedScale", null);
    }


    /**
     * Builder class for creating new instances of {@link javax.money.MonetaryAmountFactoryQuery} that can be passed
     * to access {@link javax.money.MonetaryAmountFactory} instances using a possible complex query.
     * <p>
     * Note this class is NOT thread-safe.
     *
     * @see MonetaryAmounts#getAmountFactory(MonetaryAmountFactoryQuery)
     * @see javax.money.MonetaryAmountFactory
     */
    public static final class Builder extends AbstractQueryBuilder<Builder,MonetaryAmountFactoryQuery>{

        /**
         * Sets the maximal scale to be supported.
         *
         * @param maxScale the max scale, >= 0.
         * @return this Builder for chaining.
         */
        public Builder setMaxScale(int maxScale){
            return set("maxScale", maxScale);
        }

        /**
         * Sets the required precision, the value 0 models unlimited precision.
         *
         * @param precision the precision, >= 0, 0 meaning unlimited.
         * @return this Builder for chaining.
         */
        public Builder setPrecision(int precision){
            return set("precision", precision);
        }

        /**
         * Sets the flag if the scale should fixed, meaning minimal scale and maximal scale are always equally sized.
         *
         * @param fixedScale the fixed scale flag.
         * @return this Builder for chaining.
         */
        public Builder setFixedScale(boolean fixedScale){
            return set("fixedScale", fixedScale);
        }

        /**
         * Creates a new instance of {@link MonetaryAmountFactoryQuery} based on the values of this Builder. Note that
         * the Builder supports creation of several Builder instances from the a common Builder instance. But be aware
         * that the keys and values contained are themself not recursively cloned (deep-copy).
         *
         * @return a new {@link MonetaryAmountFactoryQuery} instance.
         */
        public MonetaryAmountFactoryQuery build(){
            return new MonetaryAmountFactoryQuery(this);
        }

    }

}
