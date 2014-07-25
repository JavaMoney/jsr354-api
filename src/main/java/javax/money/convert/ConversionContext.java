/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.convert;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

import javax.money.AbstractContext;

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
    public static final ConversionContext ANY_CONVERSION = ConvertionContextBuilder.create().setRateType(RateType.ANY).build();
    /**
     * ConversionContext quering for any deferred rates.
     */
    public static final ConversionContext DEFERRED_CONVERSION = ConvertionContextBuilder.create().setRateType(RateType.DEFERRED).build();
    /**
     * ConversionContext quering for any historic rates.
     */
    public static final ConversionContext HISTORIC_CONVERSION = ConvertionContextBuilder.create().setRateType(RateType.HISTORIC).build();
    /**
     * ConversionContext quering for real-time rates.
     */
    public static final ConversionContext REALTIME_CONVERSION = ConvertionContextBuilder.create().setRateType(RateType.REALTIME).build();
    /**
     * ConversionContext quering for any other rates.
     */
    public static final ConversionContext OTHER_CONVERSION = ConvertionContextBuilder.create().setRateType(RateType.OTHER).build();

    /**
     * Private constructor, used by {@link javax.money.convert.ConversionContext.ConvertionContextBuilder}.
     *
     * @param builder the Builder.
     */
    ConversionContext(ConvertionContextBuilder builder){
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
     * Creates a {@link javax.money.convert.ConversionContext.ConvertionContextBuilder} initialized with this instance's data.
     *
     * @return a new {@link javax.money.convert.ConversionContext.ConvertionContextBuilder}, not {@code null}.
     */
    public ConvertionContextBuilder toBuilder(){
        return new ConvertionContextBuilder(this);
    }

    /**
     * Simple factory method for {@link ConversionContext}. For more
     * possibilities to initialize a {@link ConversionContext}, please use a
     * {@link javax.money.convert.ConversionContext.ConvertionContextBuilder},
     *
     * @param provider the provider name, not {@code null}
     * @param rateType the required rate type.
     * @return a new instance of {@link ConversionContext}
     */
    public static ConversionContext of(String provider, RateType rateType){
        ConvertionContextBuilder b = ConvertionContextBuilder.create();
        b.setRateType(rateType);
        b.setProvider(provider);
        return b.build();
    }

    /**
     * Creates a new ConversionContext for the given  {@link ProviderContext} and the given {@link RateType}.<br/>
     * <i>Note:</i> for adding additional attributes use {@link javax.money.convert.ConversionContext.ConvertionContextBuilder#Builder
     * (ProviderContext, RateType)}.
     *
     * @param providerContext the provider context, not null.
     * @param rateType        the rate type, not null.
     * @return a corresponding instance of ConversionContext.
     */
    public static ConversionContext from(ProviderContext providerContext, RateType rateType){
        return new ConvertionContextBuilder(providerContext, rateType).build();
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
     * {@link javax.money.convert.ConversionContext.ConvertionContextBuilder},
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
    public ConversiontQueryBuilder toQueryBuilder(){
        return ConversiontQueryBuilder.create().importContext(this).setProviders(getProvider())
                .setRateTypes(getRateType());
    }

}
