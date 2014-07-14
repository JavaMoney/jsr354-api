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
 * This class models the numeric capabilities of a {@link MonetaryAmount} in a
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
public final class MonetaryContext extends AbstractContext implements Serializable{

    public static final String AMOUNT_TYPE = "amountType";

    /**
     * Constructor, used from the Builder.
     *
     * @param builder the corresponding builder, not null.
     */
    private MonetaryContext(Builder builder){
        super(builder);
    }

    /**
     * Returns the {@code precision} setting. This value is always non-negative.
     *
     * @return an {@code int} which is the value of the {@code precision}
     * setting
     */
    public int getPrecision(){
        return getInt("precision", 0);
    }

    /**
     * Allows to check if {@code minScale == maxScale}.
     *
     * @return {@code true} if {@code minScale == maxScale}.
     */
    public boolean isFixedScale(){
        return getBoolean("fixedScale", false);
    }

    /**
     * Get the maximal scale supported, always {@code >= -1}. Fixed scaled
     * numbers will have {@code scale==maxScale} for all values. {@code -1}
     * declares the maximal scale to be <i>unlimited</i>.
     *
     * @return the maximal scale supported, always {@code >= -1}
     */
    public int getMaxScale(){
        return getInt("maxScale", 0);
    }

    /**
     * Get the MonetaryAmount implementation class.
     * @return the implementation class of the containing amount instance, never null.
     * @see MonetaryAmount#getMonetaryContext()
     */
    public Class<? extends MonetaryAmount> getAmountType(){
        return getAny(AMOUNT_TYPE, Class.class);
    }

    /**
     * Builder class for creating new instances of {@link javax.money.MonetaryContext} adding detailed information
     * about a {@link javax.money.MonetaryAmount} instance.
     * <p>
     * Note this class is NOT thread-safe.
     *
     * @see MonetaryAmount#getMonetaryContext()
     */
    public static final class Builder extends AbstractContextBuilder<Builder,MonetaryContext>{

        /**
         * Creates a new builder, hereby the target implementation type is required. This can be used to explicitly
         * acquire a specific amount type and additionally configure the amount factory with the attributes in this
         * query.
         *
         * @param amountType the target amount type, not null.
         * @return this builder for chaining.
         */
        public Builder(Class<? extends MonetaryAmount> amountType){
            set(AMOUNT_TYPE, amountType, Class.class);
        }

        /**
         * Apply all entries from the given context,  but keep the amount type.
         * @param context the context to be applied, not null.
         * @return this Builder for chaining.
         */
        public Builder setAll(AbstractContext context){
            Class amountType = context.getAny(AMOUNT_TYPE, Class.class);
            super.setAll(context);
            return set(AMOUNT_TYPE, amountType, Class.class);
        }

        /**
         * Set the maximal scale to be supported.
         *
         * @param maxScale the max scale, >= 0.
         * @return this builder for chaining.
         */
        public Builder setMaxScale(int maxScale){
            return set("maxScale", maxScale);
        }

        /**
         * Set the required precision.
         *
         * @param precision the precision, >= 0, 0 meaning unlimited.
         * @return this builder for chaining.
         */
        public Builder setPrecision(int precision){
            return set("precision", precision);
        }

        /**
         * Set the flag if the scale should fixed.
         *
         * @param fixedScale the fixed scale flag.
         * @return this builder for chaining.
         */
        public Builder setFixedScale(boolean fixedScale){
            return set("fixedScale", fixedScale);
        }

        /**
         * Get the MonetaryAmount implementation class.
         * @return the implementation class of the containing amount instance, never null.
         * @see MonetaryAmount#getMonetaryContext()
         */
        public Builder setAmountType(Class<? extends MonetaryAmount> amountType){
            return set(AMOUNT_TYPE, amountType, Class.class);
        }

        /**
         * Creates a new instance of {@link MonetaryAmountFactoryQuery}.
         *
         * @return a new {@link MonetaryAmountFactoryQuery} instance.
         */
        public MonetaryContext build(){
            return new MonetaryContext(this);
        }

    }
}
