/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Builder for queries for accessing {@link javax.money.CurrencyUnit} instances. If not properties are set the
 * query should
 * returns
 * the <i>default</i> currencies. Similarly if no provider is set explicitly the <i>default</i> ISO currencies as
 * returned by {@link java.util.Currency} should be returned.
 * <p>
 * Note this class is NOT thread-safe.
 */
public final class CurrencyQueryBuilder extends AbstractQueryBuilder<CurrencyQueryBuilder, CurrencyQuery> {

    /**
     * Default constructor.
     */
    private CurrencyQueryBuilder() {
    }

    /**
     * Creates a new instance of {@link javax.money.CurrencyQueryBuilder}.
     *
     * @param currencyQuery {@link javax.money.CurrencyQuery} used for initializing this builder.
     */
    private CurrencyQueryBuilder(CurrencyQuery currencyQuery) {
        Objects.requireNonNull(currencyQuery);
        importContext(currencyQuery);
    }

    /**
     * Sets the country for which currencies should be requested.
     *
     * @param countries The ISO countries.
     * @return the query for chaining.
     */
    public CurrencyQueryBuilder setCountries(Locale... countries) {
        return set(CurrencyQuery.KEY_QUERY_COUNTRIES, Arrays.asList(countries));
    }

    /**
     * Sets the currency code, or the regular expression to select codes.
     *
     * @param codes the currency codes or code expressions, not null.
     * @return the query for chaining.
     */
    public CurrencyQueryBuilder setCurrencyCodes(String... codes) {
        return set(CurrencyQuery.KEY_QUERY_CURRENCY_CODES, Arrays.asList(codes));
    }

    /**
     * Set the numeric code. Setting it to -1 search for currencies that have no numeric code.
     *
     * @param codes the numeric codes.
     * @return the query for chaining.
     */
    public CurrencyQueryBuilder setNumericCodes(int... codes) {
        return set(CurrencyQuery.KEY_QUERY_NUMERIC_CODES,
                Arrays.stream(codes).boxed().collect(Collectors.toList()));
    }

    /**
     * Creates a new instance of {@link javax.money.CurrencyQuery}.
     *
     * @return a new {@link javax.money.CurrencyQuery} instance, never null.
     */
    public CurrencyQuery build() {
        return new CurrencyQuery(this);
    }

    /**
     * Creates a new instance of {@link javax.money.CurrencyQueryBuilder}.
     *
     * @return a new {@link javax.money.CurrencyQueryBuilder} instance, never null.
     */
    public static CurrencyQueryBuilder of() {
        return new CurrencyQueryBuilder();
    }

    /**
     * Creates a new instance of {@link javax.money.CurrencyQueryBuilder}.
     *
     * @param currencyQuery {@link javax.money.CurrencyQuery} used for initializing this builder.
     * @return a new {@link javax.money.CurrencyQueryBuilder} instance, never null.
     */
    public static CurrencyQueryBuilder of(CurrencyQuery currencyQuery) {
        return new CurrencyQueryBuilder(currencyQuery);
    }

}