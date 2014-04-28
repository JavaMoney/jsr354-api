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
import java.util.Objects;

/**
 * This class models the spec/configuration for a rounding, modelled as {@link javax.money.MonetaryOperator} in a
 * platform independent way. Each RoundingContext instance has at least one of the following attributes set:
 * <ul>
 * <li>the target CurrencyUnit.
 * <li>the (custom) rounding id
 * </ul>
 * It is possible that a RoundingContext also has both attributes set. Additionally is is possible to provide
 * additional attribute to configure the rounding as defined by the registered {@link javax.money.spi
 * .RoundingProviderSpi}
 * instance that is creating the final rounding operator instance, e.g.
 * {@link java.math.RoundingMode}.
 * <ul>
 * <p/>
 * This class is serializable and thread-safe.
 *
 * @author Anatole Tresch
 */
public final class RoundingContext extends AbstractContext implements Serializable{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 5579720004786848255L;

    /**
     * The name of the {@link javax.money.RoundingContext} built.
     */
    private String roundingId;

    /**
     * The target {@link javax.money.CurrencyUnit} of the {@link javax.money.RoundingContext} built.
     */
    private CurrencyUnit currencyUnit;

    /**
     * Constructs a new {@code MonetaryContext} with the specified precision and
     * rounding mode.
     *
     * @param builder The {@link Builder} with data to be used.
     * @throws IllegalArgumentException if the {@code setPrecision} parameter is less than zero.
     */
    @SuppressWarnings("rawtypes")
    private RoundingContext(Builder builder){
        super(builder);
        this.currencyUnit = builder.currencyUnit;
        this.roundingId = builder.roundingId;
    }

    /**
     * Get the (custom) rounding id.
     *
     * @return the rounding id, or null.
     */
    public String getRoundingId(){
        return this.roundingId;
    }

    /**
     * Get the target CurrencyUnit-
     *
     * @return the target CurrencyUnit, or null.
     */
    public CurrencyUnit getCurrencyUnit(){
        return this.currencyUnit;
    }

    /**
     * Creates a simple RoundingContext for a named custom rounding.
     * For more complex instances use the corresponding RoundingContext.Builder.
     *
     * @param roundingId the (custom) rounding id, not {@code null}.
     * @return the corresponding RoundingContext.
     */
    public static RoundingContext of(String roundingId){
        return new Builder(roundingId).create();
    }

    /**
     * Creates a simple RoundingContext for a default currency rounding.
     * For more complex instances use the corresponding RoundingContext.Builder.
     *
     * @param currencyUnit the currencyUnit, not {@code null}.
     * @return the corresponding RoundingContext.
     */
    public static RoundingContext of(CurrencyUnit currencyUnit){
        return new Builder(currencyUnit).create();
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
        result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
        result = prime * result + ((currencyUnit == null) ? 0 : currencyUnit.hashCode());
        result = prime * result + ((roundingId == null) ? 0 : roundingId.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        RoundingContext other = (RoundingContext) obj;
        if(attributes == null){
            if(other.attributes != null){
                return false;
            }
        }else if(!attributes.equals(other.attributes)){
            return false;
        }
        if(roundingId == null){
            if(other.roundingId != null){
                return false;
            }
        }else if(!roundingId.equals(other.roundingId)){
            return false;
        }
        if(currencyUnit == null){
            if(other.currencyUnit != null){
                return false;
            }
        }else if(!currencyUnit.equals(other.currencyUnit)){
            return false;
        }
        return true;
    }


    @Override
    public String toString(){
        if(currencyUnit == null){
            return "RoundingContext [roundingId=" + roundingId + ", attributes=" + attributes + "]";
        }else if(roundingId == null){
            return "RoundingContext [currencyUnit=" + currencyUnit + ", attributes=" + attributes + "]";
        }else{
            return "RoundingContext [roundingId=" + roundingId + ", currencyUnit=" + currencyUnit + ", attributes=" +
                    attributes + "]";
        }
    }


    /**
     * This class allows to create and create instances of
     * {@link javax.money.RoundingContext} using a fluent API.
     * <p/>
     * This class is not serializable and not thread-safe.
     *
     * @author Anatole Tresch
     */
    public static final class Builder extends AbstractBuilder<Builder>{

        /**
         * The name of the {@link javax.money.RoundingContext} built.
         */
        private String roundingId;

        /**
         * The target {@link javax.money.CurrencyUnit} of the {@link javax.money.RoundingContext} built.
         */
        private CurrencyUnit currencyUnit;

        /**
         * Creates a new {@link Builder}.
         *
         * @param roundingId the (custom) rounding id, not {@code null}.
         */
        public Builder(String roundingId){
            Objects.requireNonNull(roundingId);
            this.roundingId = roundingId;
        }

        /**
         * Creates a new {@link Builder}.
         *
         * @param currencyUnit the target {@link javax.money.CurrencyUnit} not null.
         */
        public Builder(CurrencyUnit currencyUnit){
            Objects.requireNonNull(currencyUnit);
            this.currencyUnit = currencyUnit;
        }

        /**
         * Creates a new {@link Builder} and uses the given context to
         * initialize this instance.
         *
         * @param context the base {@link javax.money.RoundingContext} to be used.
         */
        public Builder(RoundingContext context){
            super(context);
            this.currencyUnit = context.currencyUnit;
            this.roundingId = context.roundingId;
        }

        /**
         * Builds a new instance of {@link javax.money.RoundingContext}.
         *
         * @return a new instance of {@link javax.money.RoundingContext}, never {@code null}
         * @throws IllegalArgumentException if building of the {@link javax.money.RoundingContext} fails.
         */
        public RoundingContext create(){
            return new RoundingContext(this);
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString(){
            if(currencyUnit == null){
                return "RoundingContext.Builder [roundingId=" + roundingId + ", attributes=" + attributes + "]";
            }else if(roundingId == null){
                return "RoundingContext.Builder [currencyUnit=" + currencyUnit + ", attributes=" + attributes + "]";
            }else{
                return "RoundingContext.Builder [roundingId=" + roundingId + ", currencyUnit=" + currencyUnit +
                        ", attributes=" + attributes + "]";
            }
        }

    }

}
