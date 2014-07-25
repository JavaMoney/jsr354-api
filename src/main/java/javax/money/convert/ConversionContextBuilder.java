package javax.money.convert;

import java.time.temporal.TemporalAccessor;
import java.util.Objects;

import javax.money.AbstractContextBuilder;

/**
 * Builder class to create {@link ConversionContext} instances. Instances of
 * this class are not thread-safe.
 *
 * @author Anatole Tresch
 */
public class ConversionContextBuilder extends AbstractContextBuilder<ConversionContextBuilder,ConversionContext> {
	
	private static final String PROVIDER = "provider";

	private static final String TIMESTAMP = "timestamp";

    /**
     * Create a new Builder, hereby using the given
     * {@link ConversionContext}'s values as defaults. This allows changing
     * an existing {@link ConversionContext} easily.
     *
     * @param context the context, not {@code null}
     */
    public ConversionContextBuilder(ConversionContext context){
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
    public ConversionContextBuilder(ProviderContext context, RateType rateType){
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

    /**
     * Sets the converion's provider.
     *
     * @param provider the provider, not null.
     * @return this builder, for chaining.
     */
    public ConversionContextBuilder setProvider(String provider){
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
    public ConversionContextBuilder setTimestampMillis(long timestamp){
        set(TIMESTAMP, timestamp);
        return this;
    }

    /**
     * Set the historic value.
     *
     * @param temporalAccessor the rate's timestamp, as TemporalAccessor.
     * @return this, for chaining.
     */
    public ConversionContextBuilder setTimestamp(TemporalAccessor temporalAccessor){
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

    /**
     * Create a new Builder instance without any provider, e.g. for creating
     * new {@link ConversionContext} instances for querying.
     */
    public ConversionContextBuilder() {
    	set(RateType.ANY);
    }
  
    public static ConversionContextBuilder create() {
    	return new ConversionContextBuilder();
    }
}
