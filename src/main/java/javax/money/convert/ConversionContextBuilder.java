/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money.convert;

import javax.money.AbstractContextBuilder;
import java.util.Objects;

/**
 * Builder class to create {@link ConversionContext} instances. Instances of
 * this class are not thread-safe.
 *
 * @author Anatole Tresch
 */
public final class ConversionContextBuilder extends AbstractContextBuilder<ConversionContextBuilder,ConversionContext>{
    //	/** Map key used for the provider attribute. */
    //	private static final String PROVIDER = "provider";
    //    /** Map key used for the timestamp attribute. */
    //	private static final String TIMESTAMP = "timestamp";

    /**
     * Create a new Builder, hereby using the given
     * {@link ConversionContext}'s values as defaults. This allows changing
     * an existing {@link ConversionContext} easily.
     *
     * @param context the context, not {@code null}
     */
    private ConversionContextBuilder(ConversionContext context){
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
    private ConversionContextBuilder(ProviderContext context, RateType rateType){
        importContext(context);
        setRateType(rateType);
    }

    /**
     * Set the historic value.
     *
     * @param rateType the rate type
     * @return this, for chaining.
     */
    public ConversionContextBuilder setRateType(RateType rateType){
        Objects.requireNonNull(rateType);
        set(rateType);
        return this;
    }

    //    /**
    //     * Sets the converion's provider.
    //     *
    //     * @param provider the provider, not null.
    //     * @return this builder, for chaining.
    //     */
    //    public ConversionContextBuilder setProvider(String provider){
    //        Objects.requireNonNull(provider);
    //        set(PROVIDER, provider);
    //        return this;
    //    }
    //
    //    /**
    //     * Set the historic value.
    //     *
    //     * @param timestamp the rate's timestamp
    //     * @return this, for chaining.
    //     */
    //    public ConversionContextBuilder setTimestampMillis(long timestamp){
    //        set(TIMESTAMP, timestamp);
    //        return this;
    //    }
    //
    //    /**
    //     * Set the historic value.
    //     *
    //     * @param temporalAccessor the rate's timestamp, as TemporalAccessor.
    //     * @return this, for chaining.
    //     */
    //    public ConversionContextBuilder setTimestamp(TemporalAccessor temporalAccessor){
    //        set(TIMESTAMP, temporalAccessor, TemporalAccessor.class);
    //        return this;
    //    }

    /**
     * Creates a new {@link ConversionContext} with the data from this
     * Builder instance.
     *
     * @return a new {@link ConversionContext}. never {@code null}.
     */
    public ConversionContext build(){
        return new ConversionContext(this);
    }

    /**
     * Create a new Builder instance without any provider, e.g. for creating
     * new {@link ConversionContext} instances for querying.
     */
    public ConversionContextBuilder(){
        set(RateType.ANY);
    }

    /**
     * Creates a new {@link javax.money.convert.ConversionContextBuilder} instance.
     *
     * @return a new {@link javax.money.convert.ConversionContextBuilder} instance, never null.
     */
    public static ConversionContextBuilder create(ConversionContext conversionContext){
        return new ConversionContextBuilder(conversionContext);
    }

    /**
     * Creates a new {@link javax.money.convert.ConversionContextBuilder} instance.
     *
     * @return a new {@link javax.money.convert.ConversionContextBuilder} instance, never null.
     */
    public static ConversionContextBuilder create(){
        return new ConversionContextBuilder();
    }

    /**
     * Create a new Builder, hereby using the given
     * {@link ConversionContext}'s values as defaults. This allows changing
     * an existing {@link ConversionContext} easily.
     *
     * @param context  the provider context, not {@code null}
     * @param rateType the rate type, not null.
     * @return a new {@link javax.money.convert.ConversionContextBuilder} instance, never null.
     */
    public static ConversionContextBuilder create(ProviderContext context, RateType rateType){
        return new ConversionContextBuilder(context, rateType);
    }

}
