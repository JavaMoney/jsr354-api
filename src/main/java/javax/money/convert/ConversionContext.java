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
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

/**
 * This class models a context for which a {@link ExchangeRate} is valid. It allows to define
 * different settings such as
 * <ul>
 * <li>the required {@link javax.money.convert.RateType}, </li>
 * <li>the required target timestamp</li>
 * <li>the required validity duration</li>
 * <li>additional non standard or extended attributes determined by the implementations participating in the
 * ExchangeRateProvider chain.</li>
 * </ul>
 * This class is immutable, thread-safe and serializable.
 *
 * @author Anatole Tresch
 */
public final class ConversionContext extends AbstractContext{

    private static final long serialVersionUID = 2386546659786888877L;

    /**
     * ConversionContext that queries any conversion available.
     */
    public static final ConversionContext ANY_CONVERSION = new Builder().setRateType(RateType.ANY).build();
    /**
     * ConversionContext quering for any deferred rates.
     */
    public static final ConversionContext DEFERRED_CONVERSION = new Builder().setRateType(RateType.DEFERRED).build();
    /**
     * ConversionContext quering for any historic rates.
     */
    public static final ConversionContext HISTORIC_CONVERSION = new Builder().setRateType(RateType.HISTORIC).build();
    /**
     * ConversionContext quering for real-time rates.
     */
    public static final ConversionContext REALTIME_CONVERSION = new Builder().setRateType(RateType.REALTIME).build();
    /**
     * ConversionContext quering for any other rates.
     */
    public static final ConversionContext OTHER_CONVERSION = new Builder().setRateType(RateType.OTHER).build();

    private static final String PROVIDER = "provider";

    private static final String TIMESTAMP = "timestamp";


    /**
     * Private constructor, used by {@link javax.money.convert.ConversionContext.Builder}.
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
        return get(RateType.class);
    }


    /**
     * Get the provider of this rate. The provider of a rate can have different
     * contexts in different usage scenarios, such as the service type or the
     * stock exchange.
     *
     * @return the provider, or {code null}.
     */
    public String getProvider(){
        return getText("provider");
    }

    /**
     * Get the current timestamp of the ConversionContext in UTC milliseconds.  If not set it tries to create an
     * UTC timestamp from #getTimestamp().
     *
     * @return the timestamp in millis, or null.
     */
    public Long getTimestampMillis(){
        Long value = getLong("timestamp", null);
        if(Objects.isNull(value)){
            TemporalAccessor acc = getTimestamp();
            if(Objects.nonNull(acc)){
                return (acc.getLong(ChronoField.INSTANT_SECONDS) * 1000L) + acc.getLong(ChronoField.MILLI_OF_SECOND);
            }
        }
        return value;
    }

    /**
     * Get the current timestamp. If not set it tries to create an Instant from #getTimestampMillis().
     *
     * @return the current timestamp, or null.
     */
    public TemporalAccessor getTimestamp(){
        TemporalAccessor acc = getAny("timestamp", TemporalAccessor.class, null);
        if(Objects.isNull(acc)){
            Long value = getLong("timestamp", null);
            if(Objects.nonNull(value)){
                acc = Instant.ofEpochMilli(value);
            }
        }
        return acc;
    }

    /**
     * Creates a {@link javax.money.convert.ConversionContext.Builder} initialized with this instance's data.
     *
     * @return a new {@link javax.money.convert.ConversionContext.Builder}, not {@code null}.
     */
    public Builder toBuilder(){
        return new Builder(this);
    }

    /**
     * Simple factory method for {@link ConversionContext}. For more
     * possibilities to initialize a {@link ConversionContext}, please use a
     * {@link javax.money.convert.ConversionContext.Builder},
     *
     * @param provider the provider name, not {@code null}
     * @param rateType the required rate type.
     * @return a new instance of {@link ConversionContext}
     */
    public static ConversionContext of(String provider, RateType rateType){
        Builder b = new Builder();
        b.setRateType(rateType);
        b.setProvider(provider);
        return b.build();
    }

    /**
     * Creates a new ConversionContext for the given  {@link ProviderContext} and the given {@link RateType}.<br/>
     * <i>Note:</i> for adding additional attributes use {@link javax.money.convert.ConversionContext.Builder#Builder
     * (ProviderContext, RateType)}.
     *
     * @param providerContext the provider context, not null.
     * @param rateType        the rate type, not null.
     * @return a corresponding instance of ConversionContext.
     */
    public static ConversionContext from(ProviderContext providerContext, RateType rateType){
        return new Builder(providerContext, rateType).build();
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
     * {@link javax.money.convert.ConversionContext.Builder},
     *
     * @return a new instance of {@link ConversionContext}
     */
    public static ConversionContext of(){
        return ANY_CONVERSION;
    }

    /**
     * Creates a conversion query builder with the context data from this context instance.
     * @return a corresponding conversion query builder instance, never null.
     */
    public ConversionQuery.Builder toQueryBuilder(){
        return new ConversionQuery.Builder().importContext(this).setProviders(getProvider())
                .setRateTypes(getRateType());
    }

    /**
     * Builder class to create {@link ConversionContext} instances. Instances of
     * this class are not thread-safe.
     *
     * @author Anatole Tresch
     */
    public static final class Builder extends AbstractContextBuilder<Builder,ConversionContext>{
        /**
         * Create a new Builder instance without any provider, e.g. for creating
         * new {@link ConversionContext} instances for querying.
         */
        public Builder(){
            set(RateType.ANY);
        }

        /**
         * Create a new Builder, hereby using the given
         * {@link ConversionContext}'s values as defaults. This allows changing
         * an existing {@link ConversionContext} easily.
         *
         * @param context the context, not {@code null}
         */
        public Builder(ConversionContext context){
            importContext(context);
        }

        /**
         * Create a new Builder, hereby using the given
         * {@link ConversionContext}'s values as defaults. This allows changing
         * an existing {@link ConversionContext} easily.
         *
         * @param context  the provider context, not {@code null}
         * @param rateType the rate type, not null.
         */
        public Builder(ProviderContext context, RateType rateType){
            importContext(context);
            setRateType(rateType);
        }

        /**
         * Set the historic value.
         *
         * @param rateType the rate type
         * @return this, for chaining.
         */
        public Builder setRateType(RateType rateType){
            Objects.requireNonNull(rateType);
            set(rateType);
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
            set(PROVIDER, provider);
            return this;
        }

        /**
         * Set the historic value.
         *
         * @param timestamp the rate's timestamp
         * @return this, for chaining.
         */
        public Builder setTimestampMillis(long timestamp){
            set(TIMESTAMP, timestamp);
            return this;
        }

        /**
         * Set the historic value.
         *
         * @param temporalAccessor the rate's timestamp, as TemporalAccessor.
         * @return this, for chaining.
         */
        public Builder setTimestamp(TemporalAccessor temporalAccessor){
            set(TIMESTAMP, temporalAccessor, TemporalAccessor.class);
            return this;
        }

        /**
         * Creates a new {@link ConversionContext} with the data from this
         * Builder instance.
         *
         * @return a new {@link ConversionContext}. never {@code null}.
         */
        public ConversionContext build(){
            return new ConversionContext(this);
        }

    }

}
