/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Query for accessing instances of {@link javax.money.MonetaryRounding}. In general it is determined by the
 * implementation, what roundings are provided. Nevertheless the following queries must be supported:
 * <ul>
 * <li>Accessing roundings using rounding names and/or regular expressions.</li>
 * <li>Accessing mathematical rounding by setting a scale and (optionally) a {@link java.math.MathContext} as
 * additinal generic attribute.</li>
 * <li>Accessing default roundings for a {@link javax.money.CurrencyUnit}. This method should return the most
 * appropriate rounding for a currency. If no
 * currency specific rounding is available, it should return a rounding with {@code scale==currency
 * .getDefaultFractionUnits(), java.math.RoundingMode = RoundingMode.HALF_EVEN}.</li>
 * </ul>
 * All other roundings including cash rounding and historical roundings are optional.
 * <p/>
 * This class is immutable, thread-safe and serializable.
 */
public final class RoundingQuery extends AbstractContext{

    /**
     * Constructor, used from the {@link javax.money.RoundingQuery.Builder}.
     *
     * @param builder the corresponding builder, not null.
     */
    private RoundingQuery(Builder builder){
        super(builder);
    }

    /**
     * Gets the target rounding name. This method allows to
     * access the {@link javax.money.MonetaryRounding} instances by passing a name, which most of the time
     * identifies a certain rounding instance. Each entry is first matched as name on equality. If no instance
     * with such a name exists, the value passed is interpreted as a regular
     * expression to lookup roundings.
     *
     * @return the rounding id  or null.
     */
    public Collection<String> getRoundingNames(){
        return getCollection("roundingNames", Collections.emptySet());
    }

    /**
     * Get the providers to be queried. If set, only the providers listed are queried. By default, or if this
     * method returns an empty or null array, all providers are queried in random order.
     *
     * @return the providers and provider ordering to be considered, or an empty array, but never null.
     */
    public Collection<String> getProviders(){
        return getCollection("providers", Collections.emptySet());
    }

    /**
     * Gets the target scale. This allows to define the scale required. If not specified as additional
     * attribute, by default, RoundingMode.HALF_EVEN is used hereby.
     *
     * @return the target scale or null.
     */
    public Integer getScale(){
        return getInt("scale", null);
    }

    /**
     * Sets the target CurrencyUnit. Typically this determines all other properties,
     * such as scale and the concrete rounding algorithm. With
     * rounding names, depending on the implementation, additinoal subselections are possible. Similarly
     * additional attributes can be used to select special rounding instances, e.g. for cash rounding.
     *
     * @return the CurrencyUnit, or null.
     */
    public CurrencyUnit getCurrencyUnit(){
        return get(CurrencyUnit.class, (CurrencyUnit) null);
    }

    /**
     * Get the current target timestamp of the query in UTC milliseconds.  If not set it tries to create an
     * UTC timestamp from #getTimestamp(). This allows to select historical roundings that were valid in the
     * past. Its implementation specific, to what extend historical roundings are available. By default if this
     * property is not set always current {@link  javax.money.MonetaryRounding} instances are provided.
     *
     * @return the timestamp in millis, or null.
     */
    public Long getTimestampMillis(){
        Long value = getAny("timestamp", Long.class, null);
        if(Objects.isNull(value)){
            TemporalAccessor acc = getTimestamp();
            if(Objects.nonNull(acc)){
                return (acc.getLong(ChronoField.INSTANT_SECONDS) * 1000L) + acc.getLong(ChronoField.MILLI_OF_SECOND);
            }
        }
        return value;
    }

    /**
     * Get the current target timestamp of the query. If not set it tries to create an Instant from
     * #getTimestampMillis(). This allows to select historical roundings that were valid in the
     * past. Its implementation specific, to what extend historical roundings are available. By default if this
     * property is not set always current {@link  javax.money.MonetaryRounding} instances are provided.
     *
     * @return the current timestamp, or null.
     */
    public TemporalAccessor getTimestamp(){
        TemporalAccessor acc = getAny("timestamp", TemporalAccessor.class, null);
        if(Objects.isNull(acc)){
            Long value = getAny("timestamp", Long.class, null);
            if(Objects.nonNull(value)){
                acc = Instant.ofEpochMilli(value);
            }
        }
        return acc;
    }

    /**
     * Builder used to construct new instances of {@link }RoundingQuery}.
     * <p>
     * Note this class is NOT thread-safe.
     */
    public static final class Builder extends AbstractContextBuilder<Builder,RoundingQuery>{

        /**
         * Sets the rounding names of the {@link javax.money.MonetaryRounding} instances. This method allows to
         * access the {@link javax.money.MonetaryRounding} instances by passing a name, which most of the time
         * identifies a certain rounding instance. Each entry is first matched as name on equality. If no instance
         * with such a name exists, the value passed is interpreted as a regular
         * expression to lookup roundings.
         *
         * @param roundingNames the (custom) rounding name expressions, not {@code null}.
         * @return this instance for chaining
         */
        public Builder setRoundingNames(String... roundingNames){
            Objects.requireNonNull(roundingNames);
            setCollection("roundingNames", Arrays.asList(roundingNames));
            return this;
        }

        /**
         * Set the providers and the provider ordering. If set, only the providers listed are queried in the given
         * order.
         * By default, all providers are queried as defined by the default providers configured in {@code javamoney
         * .properties}.
         *
         * @return the providers and provider ordering to be considered, or an empty array, but never null.
         */
        public Builder setProviders(String... providers){
            Objects.requireNonNull(providers);
            return setCollection("providers", Arrays.asList(providers));
        }

        /**
         * Sets the target scale. This allows to define the scale required. If not specified as additional
         * attribute, by default, RoundingMode.HALF_EVEN is used hereby.
         *
         * @param scale the target scale, >0.
         * @return this instance for chaining
         */
        public Builder setScale(int scale){
            set("scale", scale);
            return this;
        }

        /**
         * Sets the target {@link javax.money.CurrencyUnit}, which defines a rounding targeting a concrete {@link
         * javax.money.CurrencyUnit}.
         * Typically this determines all other properties, such as scale and the concrete rounding algorithm. With
         * rounding names, depending on the implementation, additinoal subselections are possible. Similarly
         * additional attributes can be used to select special rounding instances, e.g. for cash rounding.
         *
         * @param currencyUnit the target {@link javax.money.CurrencyUnit} not null.
         * @return this instance for chaining
         */
        public Builder setCurrencyUnit(CurrencyUnit currencyUnit){
            Objects.requireNonNull(currencyUnit);
            set(CurrencyUnit.class, currencyUnit, CurrencyUnit.class);
            return this;
        }

        /**
         * Set the target timestamp in UTC millis. This allows to select historical roundings that were valid in the
         * past. Its implementation specific, to what extend historical roundings are available. By default if this
         * property is not set always current {@link  javax.money.MonetaryRounding} instances are provided.
         *
         * @param timestamp the target timestamp
         * @return this instance for chaining
         * @see #setTimestamp(java.time.temporal.TemporalAccessor)
         */
        public Builder setTimestampMillis(long timestamp){
            set("timestamp", timestamp);
            return this;
        }

        /**
         * Set the target timestamp as {@link java.time.temporal.TemporalAccessor}. This allows to select historical
         * roundings that were valid in the past. Its implementation specific, to what extend historical roundings
         * are available. By default if this property is not set always current {@link  javax.money.MonetaryRounding}
         * instances are provided.
         *
         * @param timestamp the target timestamp
         * @return this instance for chaining
         * @see #setTimestampMillis(long)
         */
        public Builder setTimestamp(TemporalAccessor timestamp){
            set("timestamp", timestamp, TemporalAccessor.class);
            return this;
        }

        /**
         * Creates a new instance of {@link RoundingQuery}.
         *
         * @return a new {@link RoundingQuery} instance.
         */
        public RoundingQuery build(){
            return new RoundingQuery(this);
        }

    }

}
