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
 * Query to search for {@link javax.money.MonetaryAmountFactory} instances.
 */
public final class MonetaryAmountFactoryQuery extends AbstractContext implements Serializable{

    /**
     * Constructor, used from the {@link javax.money.MonetaryAmountFactoryQuery.MonetaryAmountFactoryQueryBuilder}.
     * @param builder the corresponding builder, not null.
     */
    private MonetaryAmountFactoryQuery(MonetaryAmountFactoryQueryBuilder builder){
        super(builder);
    }

    /**
     * Get the maximal scale to be supported.
     *
     * @return the maximal scale, or null, if this attribute must not be considered.
     */
    public Integer getMaxScale(){
        return getInt("maxScale", null);
    }

    /**
     * Get the maximal precision to be supported.
     *
     * @return the maximal precision, or null, if this attribute must not be considered.
     */
    public Integer getPrecision(){
        return getInt("precision", null);
    }

    /**
     * Get the maximal precision to be supported.
     *
     * @return the maximal precision, or null, if this attribute must not be considered.
     */
    public Class<? extends MonetaryAmount> getTargetType(){
        return getAny("targetType", Class.class, null);
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
     * <p/>
     * Note this class is NOT thread-safe.
     */
    public static final class MonetaryAmountFactoryQueryBuilder
            extends AbstractContextBuilder<MonetaryAmountFactoryQueryBuilder,MonetaryAmountFactoryQuery>{

        /**
         * Set the maximal scale to be supported.
         *
         * @param maxScale the max scale, >= 0.
         * @return this builder for chaining.
         */
        public MonetaryAmountFactoryQueryBuilder setMaxScale(int maxScale){
            return set("maxScale", maxScale);
        }

        /**
         * Set the required precision.
         *
         * @param precision the precision, >= 0, 0 meaning unlimited.
         * @return this builder for chaining.
         */
        public MonetaryAmountFactoryQueryBuilder setPrecision(int precision){
            return set("precision", precision);
        }

        /**
         * Set the flag if the scale should fixed.
         *
         * @param fixedScale the fixed scale flag.
         * @return this builder for chaining.
         */
        public MonetaryAmountFactoryQueryBuilder setFixedScale(boolean fixedScale){
            return set("fixedScale", fixedScale);
        }

        /**
         * Sets the target implementation type required. This can be used to explicitly acquire a specific amount
         * type and additionally configure the amount factory with the attributes in this query.
         *
         * @param amountType the target amount type, not null.
         * @return this builder for chaining.
         */
        public MonetaryAmountFactoryQueryBuilder setTargetType(Class<? extends MonetaryAmount> amountType){
            set("targetType", amountType, Class.class);
            return this;
        }

        /**
         * Creates a new instance of {@link MonetaryAmountFactoryQuery}.
         *
         * @return a new {@link MonetaryAmountFactoryQuery} instance.
         */
        public MonetaryAmountFactoryQuery build(){
            return new MonetaryAmountFactoryQuery(this);
        }

    }

}
