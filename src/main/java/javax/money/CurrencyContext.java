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
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

/**
 * This class models the attributable context of a {@link javax.money.CurrencyUnit} instances. It
 * provides information about
 * <ul>
 * <li>the provider that provided the instance
 * <li>the target timestamp / temporal unit
 * <li>any other attributes, identified by the attribute type, e.g. regions, tenants etc.
 * </ul>
 * <p>
 * This class is immutable, serializable and thread-safe.
 *
 * @author Anatole Tresch
 */
public final class CurrencyContext extends AbstractContext implements Serializable{

    /**
     * Key name for the currency provider.
     */
    private static final String PROVIDER = "provider";
    /**
     * Key name for the timestamp attribute.
     */
    private static final String TIMESTAMP = "timestamp";

    /**
     * Constructor, used from the {@link javax.money.CurrencyContext.Builder}.
     * @param builder the corresponding builder, not null.
     */
    private CurrencyContext(Builder builder){
        super(builder);
    }

    /**
     * Returns the {@code precision} setting. This value is always non-negative.
     *
     * @return an {@code int} which is the value of the {@code precision}
     * setting
     */
    public String getProvider(){
        return getText(PROVIDER, null);
    }

    /**
     * Get the current timestamp of the context in UTC milliseconds.  If not set it tries to create an
     * UTC timestamp from #getTimestamp().
     *
     * @return the timestamp in millis, or null.
     */
    public Long getTimestampMillis(){
        Long value = getLong(TIMESTAMP, null);
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
        TemporalAccessor acc = getAny(TIMESTAMP, TemporalAccessor.class, null);
        if(Objects.isNull(acc)){
            Long value = getLong(TIMESTAMP, null);
            if(Objects.nonNull(value)){
                acc = Instant.ofEpochMilli(value);
            }
        }
        return acc;
    }


    /**
     * Builder class for creating new instances of {@link javax.money.CurrencyContext} adding detailed information
     * about a {@link javax.money.CurrencyUnit} instance.
     * <p/>
     * Note this class is NOT thread-safe.
     *
     * @see CurrencyUnit#getCurrencyContext()
     */
    public static final class Builder extends AbstractContextBuilder<Builder,CurrencyContext>{

        /**
         * Creates a new builder.
         *
         * @param provider the provider name, creating the corresponding {@link javax.money.CurrencyUnit} containing
         *                 the final {@link javax.money.CurrencyContext} created by this builder, not null.
         */
        public Builder(String provider){
            Objects.requireNonNull(provider);
            set("provider", provider);
        }

        /**
         * Sets the currency's timestamp, using UTC milliseconds.
         *
         * @param timestamp the timestamp.
         * @return the builder for chaining
         */
        public Builder setTimestamp(long timestamp){
            set("timestamp", timestamp);
            return this;
        }

        /**
         * Sets the currency's timestamp.
         *
         * @param timestamp the timestamp, not null.
         * @return the builder for chaining
         */
        public Builder setTimestamp(TemporalAccessor timestamp){
            set("timestamp", timestamp, TemporalAccessor.class);
            return this;
        }

        /**
         * Creates a new instance of {@link CurrencyContext}.
         *
         * @return a new {@link CurrencyContext} instance.
         */
        @Override
        public CurrencyContext build(){
            return new CurrencyContext(this);
        }

    }

}
