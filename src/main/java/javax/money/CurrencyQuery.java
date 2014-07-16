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
import java.time.temporal.TemporalUnit;
import java.util.*;

/**
 * This class models the attributable context of a {@link CurrencyUnit} instances. It
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
public final class CurrencyQuery  extends AbstractContext implements Serializable{

    public static final CurrencyQuery ANY_QUERY = new CurrencyQueryBuilder().build();

    /**
     * Constructor, used from the Builder.
     * @param builder the corresponding {@link javax.money.CurrencyQuery.CurrencyQueryBuilder}, not null.
     */
    private CurrencyQuery(CurrencyQueryBuilder builder){
        super(builder);
    }

    /**
     * Returns the providers and ordering to be used.
     *
     * @return the ordered providers, never null.
     */
    public Collection<String> getProviders(){
        return getCollection("providers", Collections.emptySet());
    }

    /**
     * Returns the target locales.
     *
     * @return the otarget locales, never null.
     */
    public Collection<Locale> getCountries(){
        return getCollection("countries", Collections.emptySet());
    }

    /**
     * Gets the currency codes, or the regular expression to select codes.
     * @return the query for chaining.
     */
    public Collection<String> getCurrencyCodes(){
        return getCollection("currencyCodes", Collections.emptySet());
    }

    /**
     * Gets the numeric codes. Setting it to -1 search for currencies that have no numeric code.
     * @return the query for chaining.
     */
    public Collection<Integer> getNumericCodes(){
        return getCollection("numericCodes", Collections.emptySet());
    }

    /**
     * Get the current timestamp of the context in UTC milliseconds.  If not set it tries to create an
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
     * Builder for queries for accessing {@link CurrencyUnit} instances. If not properties are set the query should returns
     * the <i>default</i> currencies. Similarly if no provider is set explicitly the <i>default</i> ISO currencies as
     * returned by {@link java.util.Currency} should be returned.
     * <p/>
     * Note this class is NOT thread-safe.
     */
    public static final class CurrencyQueryBuilder extends AbstractContextBuilder<CurrencyQueryBuilder,CurrencyQuery>{
        /**
         * Set the providers to be considered. If not set explicitly the <i>default</i> ISO currencies as
         * returned by {@link java.util.Currency} is used.
         * @param providers the providers to use, not null.
         * @return the query for chaining.
         */
        public CurrencyQueryBuilder setProviders(String... providers){
            return set("providers", providers);
        }

        /**
         * Sets the country for which currencies whould be requested.
         * @param countries The ISO countries.
         * @return the query for chaining.
         */
        public CurrencyQueryBuilder setCountries(Locale... countries){
            return setCollection("countries", Arrays.asList(countries));
        }

        /**
         * Sets the currency code, or the regular expression to select codes.
         * @param codes sthe currency codes or code expressions, not null.
         * @return the query for chaining.
         */
        public CurrencyQueryBuilder setCurrencyCodes(String... codes){
            return setCollection("currencyCodes", Arrays.asList(codes));
        }

        /**
         * Set the numeric code. Setting it to -1 search for currencies that have no numeric code.
         * @param codes the numeric codes.
         * @return the query for chaining.
         */
        public CurrencyQueryBuilder setNumericCodes(int... codes){
            return setCollection("numericCodes", Arrays.asList(codes));
        }

        /**
         * Sets the target timestamp as UTC millisesonds.
         * @param timestamp the target timestamp
         * @return the query for chaining.
         */
        public CurrencyQueryBuilder setTimestampMillis(long timestamp){
            return set("timestamp", timestamp);
        }

        /**
         * Sets the target timestamp as {@link java.time.temporal.TemporalUnit}.
         * @param timestamp the target timestamp
         * @return the query for chaining.
         */
        public CurrencyQueryBuilder setTimestamp(TemporalUnit timestamp){
            return set("timestamp", timestamp, TemporalUnit.class);
        }

        /**
         * Creates a new instance of {@link CurrencyQuery}.
         * @return a new {@link CurrencyQuery} instance.
         */
        public CurrencyQuery build(){
            return new CurrencyQuery(this);
        }

    }

}
