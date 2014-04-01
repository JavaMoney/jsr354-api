/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.convert;

import javax.money.AbstractContext;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * This class models a context for which a {@link ExchangeRate} is valid. It allows to define
 * different settings such as
 * <ul>
 *     <li>the required {@link javax.money.convert.RateType}, </li>
 *     <li>the required target timestamp</li>
 *     <li>the required validity duration</li>
 *     <li>additional non standard or extended attributes determined by the implementations participating in the ExchangeRateProvider chain.</li>
 * </ul>
 * <p>An instance of this class can be passed
 * <ul>
 * <li>when accessing an ExchangeRate, calling
 * {@link javax.money.convert.ExchangeRateProvider#getExchangeRate(javax.money.CurrencyUnit, javax.money.CurrencyUnit, ConversionContext)} or {@link javax.money.convert
 * .ExchangeRateProvider#getExchangeRate(String, String, ConversionContext)}</li>
 * <li>when accessing a CurrencyConversion, calling {@link javax.money.convert
 * .ExchangeRateProvider#getCurrencyConversion(javax.money.CurrencyUnit, ConversionContext)} or {@link javax.money
 * .convert.ExchangeRateProvider#getCurrencyConversion(String, ConversionContext)}</li>
 * <li>when accessing {@link javax.money.convert.MonetaryConversions#getConversion(javax.money.CurrencyUnit,
 * ConversionContext, String...)} or {@link javax.money.convert.MonetaryConversions#getConversion(String,
 * ConversionContext, String...)}</li>
 * </ul>
 * <p/>
 * <p>If multiple ExchangeRateProvider are participating in a provider chain, the ConversionContext is passed to each
 * provider in the chain. As a consequence the context can have additional parameters that may be read by different
 * rate providers within the chain.
 * </p>
 * Instances of this class are immutable and thread-safe.
 *
 * @author Anatole Tresch
 */
public final class ConversionContext extends AbstractContext{
    /**
     * ConversionContext that queries any conversion available.
     */
    public static final ConversionContext ANY_CONVERSION = new Builder().setRateType(RateType.ANY).create();
    /**
     * ConversionContext quering for any deferred rates.
     */
    public static final ConversionContext DEFERRED_CONVERSION = new Builder().setRateType(RateType.DEFERRED).create();
    /**
     * ConversionContext quering for any historic rates.
     */
    public static final ConversionContext HISTORIC_CONVERSION = new Builder().setRateType(RateType.HISTORIC).create();
    /**
     * ConversionContext quering for real-time rates.
     */
    public static final ConversionContext REALTIME_CONVERSION = new Builder().setRateType(RateType.REALTIME).create();
    /**
     * ConversionContext quering for any other rates.
     */
    public static final ConversionContext OTHER_CONVERSION = new Builder().setRateType(RateType.OTHER).create();

    /**
     * Common context attributes, using this attributes ensures interoperability
     * on property key level. Where possible according type safe methods are
     * also defined on this class.
     *
     * @author Anatole Tresch
     */
    private static enum ConversionAttribute{
        /**
         * The provider serving the conversion data.
         */
        PROVIDER,
        /**
         * The timestamp of a rate, this may be extended by a valid from/to
         * range.
         */
        TIMESTAMP,
        /**
         * The ending range of a rate, when a rate has a constraint validity
         * period.
         */
        VALID_TO,
        /**
         * The type of rate requested.
         */
        RATE_TYPE,
    }

    /**
     * Private constructor, used by {@link Builder}.
     *
     * @param builder the Builder.
     */
    private ConversionContext(Builder builder){
        super(builder);
    }

    /**
     * Get the deferred flag. Exchange rates can be deferred or real.time.
     *
     * @return the deferred flag, or {code null}.
     */
    public RateType getRateType(){
        return getNamedAttribute( ConversionAttribute.RATE_TYPE, RateType.class,RateType.ANY);
    }

    /**
     * Returns the UTC timestamp of this rate.
     *
     * @return The UTC timestamp of the rate, or {@code null}.
     */
    public final Long getTimestamp(){
        return getNamedAttribute(ConversionAttribute.TIMESTAMP, Long.class, null);
    }

    /**
     * Returns the timestamp of this rate.
     *
     * @param type the date/time type required.
     * @return The timestamp of the rate, or {@code null}.
     */
    public final <T> T getTimestamp(Class<T> type){
        return getNamedAttribute(ConversionAttribute.TIMESTAMP, type, null);
    }

    /**
     * Returns the ending UTC timestamp of this rate, or {@code null}.
     *
     * @return The ending UTC timestamp of the rate, or {@code null}.
     */
    public final Long getValidTo(){
        return getNamedAttribute(ConversionAttribute.VALID_TO, Long.class, null);
    }

    /**
     * Returns the ending date/time of this rate.
     *
     * @param type the date/time type required.
     * @return The ending date/time of the rate, or {@code null}.
     */
    public final <T> T getValidTo(Class<T> type){
        return getNamedAttribute(ConversionAttribute.VALID_TO, type, null);
    }

    /**
     * Method that determines if the rate is valid at the given UTC timestamp.
     *
     * @param timestamp the timestamp in milliseconds.
     * @return true, if the rate is valid.
     */
    public boolean isValid(long timestamp){
        Long ts = getTimestamp();
        if(ts != null && ts.longValue() > timestamp){
            return false;
        }
        ts = getValidTo();
        if(ts != null && ts.longValue() < timestamp){
            return false;
        }
        return true;
    }

    /**
     * Get the provider of this rate. The provider of a rate can have different
     * contexts in different usage scenarios, such as the service type or the
     * stock exchange.
     *
     * @return the provider, or {code null}.
     */
    public String getProvider(){
        return getNamedAttribute(ConversionAttribute.PROVIDER, String.class, null);
    }

    /**
     * Creates a {@link Builder} initialized with this instance's data.
     *
     * @return a new {@link Builder}, not {@code null}.
     */
    public Builder toBuilder(){
        return new Builder(this);
    }

    /**
     * Simple factory method for {@link ConversionContext}. For more
     * possibilities to initialize a {@link ConversionContext}, please use a
     * {@link Builder},
     *
     * @param provider the provider name, not {@code null}
     * @param rateType the required rate type.
     * @return a new instance of {@link ConversionContext}
     */
    public static ConversionContext of(String provider, RateType rateType){
        Builder b = new Builder();
        b.setRateType(rateType);
        b.setProvider(provider);
        return b.create();
    }

    /**
     * Creates a {@link ConversionContext} for accessing rates of the given
     * type, without specifying the rate's provider.
     *
     * @param rateType the required rate type.
     * @return a new instance of {@link ConversionContext}
     */
    public static ConversionContext of(RateType rateType){
        switch(rateType){
            default:
            case ANY:
                return ANY_CONVERSION;
            case DEFERRED:
                return DEFERRED_CONVERSION;
            case HISTORIC:
                return HISTORIC_CONVERSION;
            case REALTIME:
                return REALTIME_CONVERSION;
            case OTHER:
                return OTHER_CONVERSION;
        }
    }

    /**
     * Simple factory method for {@link ConversionContext}. For more
     * possibilities to initialize a {@link ConversionContext}, please use a
     * {@link Builder},
     *
     * @return a new instance of {@link ConversionContext}
     */
    public static ConversionContext of(){
        return ANY_CONVERSION;
    }

    /**
     * Simple factory method for a {@link ConversionContext} for accessing historic rates. For more
     * possibilities to initialize a {@link ConversionContext}, please use a
     * {@link Builder},
     *
     * @return a new instance of {@link ConversionContext}
     */
    public static ConversionContext of(long timestamp){
        return new Builder().setTimestamp(timestamp).create();
    }

    /**
     * Converts the given {@link ProviderContext} to an according
     * {@link ConversionContext} using the given {@link RateType}.
     *
     * @param rateType  the {@link RateType}, not null
     * @param timestamp the target timestamp
     * @return a new {@link ConversionContext}
     */
    public static ConversionContext of(String provider, RateType rateType, Long timestamp){
        if(timestamp == null){
            return new ConversionContext.Builder().setProvider(provider).setRateType(rateType).create();
        }
        return new ConversionContext.Builder().setProvider(provider).setRateType(rateType).setTimestamp(timestamp)
                .create();
    }

    /**
     * Builder class to create {@link ConversionContext} instances. Instances of
     * this class are not thread-safe.
     *
     * @author Anatole Tresch
     */
    public static final class Builder extends AbstractBuilder<Builder>{

        /**
         * Create a new Builder instance without any provider, e.g. for creating
         * new {@link ConversionContext} instances for querying.
         */
        public Builder(){
        }

        /**
         * Create a new Builder, hereby using the given
         * {@link ConversionContext}'s values as defaults. This allows changing
         * an existing {@link ConversionContext} easily.
         *
         * @param context the context, not {@code null}
         */
        public Builder(ConversionContext context){
            super(context);
        }

        /**
         * Create a new Builder, hereby using the given
         * {@link ConversionContext}'s values as defaults. This allows changing
         * an existing {@link ConversionContext} easily.
         *
         * @param context the context, not {@code null}
         */
        public Builder(ProviderContext context, RateType rateType){
            super(context);
            setRateType(rateType);
        }

        /**
         * Set the timestamp value.
         *
         * @param timestamp the timestamp value
         * @return this, for chaining.
         */
        public Builder setTimestamp(long timestamp){
            setAttribute(ConversionAttribute.TIMESTAMP, Long.valueOf(timestamp));
            return this;
        }

        /**
         * Set the timestamp value.
         *
         * @param dateTime the timestamp value
         * @return this, for chaining.
         */
        public Builder setTimestamp(Object dateTime){
            setAttribute(ConversionAttribute.TIMESTAMP, dateTime);
            if(dateTime instanceof Date){
                setTimestamp(((Date) dateTime).getTime());
            }else if(dateTime instanceof Calendar){
                setTimestamp(((Calendar) dateTime).getTime());
            }
            return this;
        }

        /**
         * Set the ending period timestamp value.
         *
         * @param timestamp the ending period timestamp value
         * @return this, for chaining.
         */
        public Builder setValidTo(long timestamp){
            setAttribute(ConversionAttribute.VALID_TO, Long.valueOf(timestamp));
            return this;
        }

        /**
         * Set the ending period timestamp value.
         *
         * @param dateTime the ending period dateTime value
         * @return this, for chaining.
         */
        public Builder setValidTo(Object dateTime){
            setAttribute(ConversionAttribute.VALID_TO, dateTime);
            if(dateTime instanceof Date){
                setValidTo(((Date) dateTime).getTime());
            }else if(dateTime instanceof Calendar){
                setValidTo(((Calendar) dateTime).getTime());
            }
            return this;
        }

        /**
         * Set the historic value.
         *
         * @param rateType the rate type
         * @return this, for chaining.
         */
        public Builder setRateType(RateType rateType){
            Objects.requireNonNull(rateType);
            setAttribute(ConversionAttribute.RATE_TYPE, rateType);
            return this;
        }

        /**
         * Sets the converion's provider.
         *
         * @param provider the provider, not null.
         * @return this builder, for chaining.
         */
        public Builder setProvider(String provider){
            Objects.requireNonNull(provider);
            setAttribute(ConversionAttribute.PROVIDER, provider);
            return this;
        }

        /**
         * Creates a new {@link ConversionContext} with the data from this
         * Builder instance.
         *
         * @return a new {@link ConversionContext}. never {@code null}.
         */
        public ConversionContext create(){
            return new ConversionContext(this);
        }

    }

}
