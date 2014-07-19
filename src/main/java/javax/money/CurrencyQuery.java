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
import java.util.*;

/**
 * This class models a query for accessing instances of {@link CurrencyUnit}. It
 * provides information such as
 * <ul>
 * <li>the providers that may provide {@link CurrencyUnit} instances
 * <li>a target timestamp / temporal unit, when the {@link CurrencyUnit} instances should be valid
 * <li>any other attributes, identified by the attribute type, e.g. regions, tenants etc.
 * </ul>
 * The effective attributes supported are only determined by the implementations of {@link javax.money.spi
 * .CurrencyProviderSpi}.
 * <p>
 * This class is immutable, serializable and thread-safe.
 *
 * @author Anatole Tresch
 */
public final class CurrencyQuery extends AbstractQuery implements Serializable{

    /** Key for storing a countries to be queried. */
    protected static final String COUNTRIES = "Query.countries";

    /** Key for storing a target literal currency codes to be queried. */
    protected static final String CURRENCY_CODES = "Query.currencyCodes";

    /** Key for storing a target numeric currency codes to be queried. */
    protected static final String NUMERIC_CODES = "Query.numericCodes";

    /**
     * An instance, which basically modely a query that allows any type of currencies to be returned.
     */
    public static final CurrencyQuery ANY_QUERY = new Builder().build();

    /**
     * Constructor, used from the Builder.
     *
     * @param builder the corresponding {@link javax.money.CurrencyQuery.Builder}, not null.
     */
    private CurrencyQuery(Builder builder){
        super(builder);
    }

    /**
     * Returns the target locales.
     *
     * @return the otarget locales, never null.
     */
    public Collection<Locale> getCountries(){
        return getCollection(COUNTRIES, Collections.emptySet());
    }

    /**
     * Gets the currency codes, or the regular expression to select codes.
     *
     * @return the query for chaining.
     */
    public Collection<String> getCurrencyCodes(){
        return getCollection(CURRENCY_CODES, Collections.emptySet());
    }

    /**
     * Gets the numeric codes. Setting it to -1 search for currencies that have no numeric code.
     *
     * @return the query for chaining.
     */
    public Collection<Integer> getNumericCodes(){
        return getCollection(NUMERIC_CODES, Collections.emptySet());
    }



    /**
     * Builder for queries for accessing {@link CurrencyUnit} instances. If not properties are set the query should
     * returns
     * the <i>default</i> currencies. Similarly if no provider is set explicitly the <i>default</i> ISO currencies as
     * returned by {@link java.util.Currency} should be returned.
     * <p>
     * Note this class is NOT thread-safe.
     */
    public static final class Builder extends AbstractQueryBuilder<Builder,CurrencyQuery>{

        /**
         * Sets the country for which currencies whould be requested.
         *
         * @param countries The ISO countries.
         * @return the query for chaining.
         */
        public Builder setCountries(Locale... countries){
            return setCollection(COUNTRIES, Arrays.asList(countries));
        }

        /**
         * Sets the currency code, or the regular expression to select codes.
         *
         * @param codes sthe currency codes or code expressions, not null.
         * @return the query for chaining.
         */
        public Builder setCurrencyCodes(String... codes){
            return setCollection(CURRENCY_CODES, Arrays.asList(codes));
        }

        /**
         * Set the numeric code. Setting it to -1 search for currencies that have no numeric code.
         *
         * @param codes the numeric codes.
         * @return the query for chaining.
         */
        public Builder setNumericCodes(int... codes){
            return setCollection(NUMERIC_CODES, Arrays.asList(codes));
        }

        /**
         * Creates a new instance of {@link CurrencyQuery}.
         *
         * @return a new {@link CurrencyQuery} instance.
         */
        public CurrencyQuery build(){
            return new CurrencyQuery(this);
        }

    }

}
