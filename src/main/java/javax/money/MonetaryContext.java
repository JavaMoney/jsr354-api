/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

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
 * <p/>
 * This class is serializable and thread-safe.
 *
 * @author Anatole Tresch
 */
public final class MonetaryContext extends AbstractContext implements Serializable{
    /** Attribute key for amount flavor. */
    private static final String AMOUNT_FLAVOR_KEY = "amountFlavor";
    /** Attribute key for amount type. */
    private static final String AMOUNT_TYPE_KEY = "amountType";
    /** Attribute key for maxScale. */
    private static final String MAX_SCALE_KEY = "maxScale";
    /** Attribute key for fixedScale. */
    private static final String FIXED_SCALE_KEY = "fixedScale";
    /** Attribute key for precision. */
    private static final String PRECISION_KEY = "precision";

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 5579720004786848255L;

    /**
     * A default instance of a MonetaryContext.
     */
    public static final MonetaryContext DEFAULT_CONTEXT = new Builder().build();



    /**
     * The number of digits to be used for an operation. A value of 0 indicates
     * that unlimited precision (as many digits as are required) will be used.
     * Note that leading zeros (in the coefficient of a number) are never
     * significant.
     * <p/>
     * {@code precision} will always be non-negative.
     */
    private final int precision;

    /**
     * The numeric representation type of the amount implementation. In most
     * cases the representation type equals also the numeric implementation type
     * used, whereas this is not required. Though only instances of this
     * representation type can be accessed from from
     * {@link MonetaryAmount#getNumber()}.
     */
    private final Class<? extends MonetaryAmount> amountType;

    /**
     * Flag, if the scale is fixed. Fixed scaled numbers will always have a
     * scale of {@link #maxScale}.
     */
    private final boolean fixedScale;

    /**
     * The maximal scale supported, always >= -1. Fixed scaled numbers a fixed
     * value for {@link #maxScale}. -1 declares the maximal scale to be
     * unlimited.
     */
    private final int maxScale;

    /**
     * Constructs a new {@code MonetaryContext} with the specified precision and
     * rounding mode.
     *
     * @param builder The {@link Builder} with data to be used.
     * @throws IllegalArgumentException if the {@code setPrecision} parameter is less than zero.
     */
    @SuppressWarnings("rawtypes")
    private MonetaryContext(Builder builder){
        super(builder);
        if(builder.precision < 0){
            throw new IllegalArgumentException("precision < 0");
        }
        if(builder.maxScale < -1){
            throw new IllegalArgumentException("maxScale < -1");
        }
        this.precision = builder.precision;
        this.fixedScale = builder.fixedScale;
        this.maxScale = builder.maxScale;
        this.amountType = builder.amountType;
    }

    /**
     * Returns the {@code precision} setting. This value is always non-negative.
     *
     * @return an {@code int} which is the value of the {@code precision}
     * setting
     */
    public int getPrecision(){
        return precision;
    }

    /**
     * Allows to check if {@code minScale == maxScale}.
     *
     * @return {@code true} if {@code minScale == maxScale}.
     */
    public boolean isFixedScale(){
        return fixedScale;
    }

    /**
     * Get the maximal scale supported, always {@code >= -1}. Fixed scaled
     * numbers will have {@code scale==maxScale} for all values. {@code -1}
     * declares the maximal scale to be <i>unlimited</i>.
     *
     * @return the maximal scale supported, always {@code >= -1}
     */
    public int getMaxScale(){
        return maxScale;
    }

    /**
     * Access the amount implementation type for the {@link MonetaryAmount}
     * implementation.
     *
     * @return the amount representation type, never {@code null}.
     */
    public Class<? extends MonetaryAmount> getAmountType(){
        return amountType;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Objects.isNull(attributes)) ? 0 : attributes.hashCode());
        result = prime * result + (fixedScale ? 1231 : 1237);
        result = prime * result + maxScale;
        result = prime * result + (((Objects.isNull(amountType)) ? 0 : amountType.hashCode());
        result = prime * result + precision;
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(Objects.isNull(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        MonetaryContext other = (MonetaryContext) obj;
        if (Objects.isNull(attributes)) {
            if (Objects.nonNull(other.attributes))
                return false;
        }else if(!attributes.equals(other.attributes))
            return false;
        if(fixedScale != other.fixedScale)
            return false;
        if(maxScale != other.maxScale)
            return false;
        if (Objects.isNull(amountType)) {
            if (Objects.nonNull(other.amountType))
                return false;
        }else if(!amountType.equals(other.amountType))
            return false;
        if(precision != other.precision)
            return false;
        return true;
    }

    /**
     * Creates a new {@link MonetaryContext} targeting the the given amount
     * type, using the given {@link MonetaryContext} (so converting it).
     *
     * @param context    the {@link MonetaryContext} to be used.
     * @param amountType the target amount type.
     * @return the {@link MonetaryContext}, not {@code null}.
     */
    public static MonetaryContext from(MonetaryContext context, Class<? extends MonetaryAmount> amountType){
        return new MonetaryContext.Builder(context).setAmountType(amountType).build();
    }

    /**
     * Reconstitute the {@code MonetaryContext} instance from a stream (that is,
     * deserialize it).
     *
     * @param s the stream being read.
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException{
        s.defaultReadObject(); // read in all fields
        // validate possibly bad fields
        if(precision < 0){
            String message = "NumericContext: invalid digits in stream";
            throw new java.io.StreamCorruptedException(message);
        }
    }


    @Override
    public String toString(){
        return "MonetaryContext [amountType=" + amountType + ", precision=" + precision +
                ", maxScale=" + maxScale + ", fixedScale=" + fixedScale + ", attributes=" + attributes + "]";
    }


    /**
     * This class allows to create and create instances of
     * {@link MonetaryContext} using a fluent API.
     * <p/>
     * This class is not serializable and not thread-safe.
     *
     * @author Anatole Tresch
     */
    public static final class Builder extends AbstractBuilder<Builder>{

        /**
         * The maximal precision of the {@link MonetaryContext} built.
         */
        private int precision = 0;
        /**
         * The amount's implementationType type for the {@link MonetaryContext}
         * built.
         */
        private Class<? extends MonetaryAmount> amountType;

        /**
         * Flag that the scale of the {@link MonetaryContext} built is fixed to #maxScale.
         */
        private boolean fixedScale;
        /**
         * Any maximal scale of the {@link MonetaryContext} built.
         */
        private int maxScale = -1;

        /**
         * Creates a new {@link Builder}.
         *
         * @param amountType the numeric representation type, not {@code null}.
         */
        public Builder(Class<? extends MonetaryAmount> amountType){
            Objects.requireNonNull(amountType);
            this.amountType = amountType;
        }

        /**
         * Creates a new {@link Builder}.
         */
        public Builder(){
            this.amountType = MonetaryAmount.class; // unspecified
        }

        /**
         * Creates a new {@link Builder} and uses the given context to
         * initialize this instance.
         *
         * @param context the base {@link MonetaryContext} to be used.
         */
        public Builder(MonetaryContext context){
            super(context);
            this.amountType = context.getAmountType();
            this.maxScale = context.getMaxScale();
            this.fixedScale = context.isFixedScale();
            this.precision = context.getPrecision();
        }

        /**
         * Sets a fixed scale, hereby setting both {@code minScale, maxScale} to
         * {@code fixedScale}.
         *
         * @param amountType the amount type to be used, not {@code null}.
         * @return the {@link Builder}, for chaining.
         */
        public Builder setAmountType(Class<? extends MonetaryAmount> amountType){
            Objects.requireNonNull(amountType);
            this.amountType = amountType;
            return this;
        }

        /**
         * Sets the maximal precision supported.
         *
         * @param precision the maximal precision, aleays {@code >=0}, whereas 0
         *                  declares unlimited precision.
         * @return the {@link Builder}, for chaining.
         */
        public Builder setPrecision(int precision){
            if(precision < 0)
                throw new IllegalArgumentException("precision < -1");
            this.precision = precision;
            return this;
        }

        /**
         * Sets a fixed scale, hereby setting both {@code minScale, maxScale} to
         * {@code fixedScale}.
         *
         * @param fixedScale the min/max scale to be used, which must be {@code >=0}.
         * @return the {@link Builder}, for chaining.
         */
        public Builder setFixedScale(boolean fixedScale){
            this.fixedScale = fixedScale;
            return this;
        }

        /**
         * Sets a maximal scale.
         *
         * @param maxScale the maximal scale to be used, which must be {@code >=-1}.
         *                 {@code -1} means unlimited maximal scale.
         * @return the {@link Builder}, for chaining.
         */
        public Builder setMaxScale(int maxScale){
            if(maxScale < -1)
                throw new IllegalArgumentException("maxScale < -1");
            this.maxScale = maxScale;
            return this;
        }

        /**
         * Builds a new instance of {@link MonetaryContext}.
         *
         * @return a new instance of {@link MonetaryContext}, never {@code null}
         * @throws IllegalArgumentException if building of the {@link MonetaryContext} fails.
         */
        public MonetaryContext build(){
            return new MonetaryContext(this);
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString(){
            return "MonetaryContext.Builder [amountType=" + amountType + ", precision=" + precision + ", maxScale=" +
                    maxScale + ", fixedScale=" + fixedScale + ", attributes=" + attributes + "]";
        }

    }

}
