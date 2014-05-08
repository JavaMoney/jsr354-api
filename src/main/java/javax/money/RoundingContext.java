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
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

/**
 * This class models the spec/configuration for a rounding, modelled as {@link javax.money.MonetaryOperator} in a
 * platform independent way. Each RoundingContext instance hereby has a <code>roundingId</code>, which links
 * to the {@link javax.money.spi.RoundingProviderSpi} that must create the according rounding instance ({@link javax
 * .money.MonetaryOperator}). The <i>default</i> </i><code>roundingId</code> is <code>default</code>.<br/>
 * A RoundingContext can take up arbitrary attributes that must be interpreted and validated by a
 * {@link javax.money.spi.RoundingProviderSpi}. By default additionally a <code>CurrencyUnit</code> can be set
 * directly using a explicit method.
 *
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


    public static final RoundingContext DEFAULT_ROUNDING_CONTEXT = new Builder().build();

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
    }

    /**
     * Get the (custom) rounding id.
     *
     * @return the rounding id, or null.
     */
    public String getRoundingId(){
        return getText("roundingId");
    }

    /**
     * Get the target CurrencyUnit-
     *
     * @return the target CurrencyUnit, or null.
     */
    public CurrencyUnit getCurrencyUnit(){
        return getAttribute(CurrencyUnit.class);
    }

    public Long getTimestampMillis(){
        return getLong("timestampMillis");
    }

    public TemporalAccessor getTimestamp(){
        return getNamedAttribute("timestamp", TemporalAccessor.class, null);
    }

    public int getScale(){
        return getInt("scale");
    }

    /**
     * Creates a simple RoundingContext for a named custom rounding.
     * For more complex instances use the corresponding RoundingContext.Builder.
     *
     * @param roundingId the (custom) rounding id, not {@code null}.
     * @return the corresponding RoundingContext.
     */
    public static RoundingContext of(String roundingId){
        return new Builder().setRoundingId(roundingId).build();
    }

    /**
     * Creates a default RoundingContext for a fixed currency rounding.
     * For more complex instances use the corresponding RoundingContext.Builder.
     *
     * @param currencyUnit the currencyUnit, not {@code null}.
     * @return the corresponding RoundingContext.
     */
    public static RoundingContext of(CurrencyUnit currencyUnit){
        return new Builder().setCurrencyUnit(currencyUnit).build();
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
        return true;
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
         * Creates a new {@link Builder}.
         */
        public Builder(){
            setText("roundingId", "default");
        }

        /**
         * Creates a new {@link Builder}.
         *
         * @param roundingId the (custom) rounding id, not {@code null}.
         */
        public Builder setRoundingId(String roundingId){
            Objects.requireNonNull(roundingId);
            setText("roundingId", roundingId);
            return this;
        }

        /**
         * Sets the target scale.
         *
         * @param scale the target scale
         */
        public Builder setScale(int scale){
            setInt("scale", scale);
            return this;
        }

        /**
         * Creates a new {@link Builder}.
         *
         * @param currencyUnit the target {@link javax.money.CurrencyUnit} not null.
         */
        public Builder setCurrencyUnit(CurrencyUnit currencyUnit){
            Objects.requireNonNull(currencyUnit);
            setAttribute(CurrencyUnit.class, currencyUnit, CurrencyUnit.class);
            return this;
        }

        public Builder setTimestampMillis(long timestamp){
            setLong("timestampMillis", timestamp);
            return this;
        }

        public Builder setTimestamp(TemporalAccessor temporalAccessor){
            setAttribute("timestamp", temporalAccessor, TemporalAccessor.class);
            return this;
        }

        /**
         * Creates a new {@link Builder} and uses the given context to
         * initialize this instance.
         *
         * @param context the base {@link javax.money.RoundingContext} to be used.
         */
        public Builder(RoundingContext context){
            super(context);
        }

        /**
         * Builds a new instance of {@link javax.money.RoundingContext}.
         *
         * @return a new instance of {@link javax.money.RoundingContext}, never {@code null}
         * @throws IllegalArgumentException if building of the {@link javax.money.RoundingContext} fails.
         */
        public RoundingContext build(){
            return new RoundingContext(this);
        }

    }

}
