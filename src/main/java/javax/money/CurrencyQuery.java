/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.money;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

/**
 * This class models a query for accessing instances of {@link CurrencyUnit}. It
 * provides information such as
 * <ul>
 * <li>the providers that may provide {@link CurrencyUnit} instances
 * <li>any other attributes, identified by the attribute type, e.g. regions, tenants,
 *     a target timestamp / temporal unit, when the {@link CurrencyUnit} instances should be valid, etc.
 * </ul>
 * The effective attributes supported are only determined by the implementations of {@link javax.money.spi
 * .CurrencyProviderSpi}.
 * <p>
 * This class is immutable, serializable and thread-safe.
 *
 * @author Anatole Tresch
 */
@SuppressWarnings("unchecked")
public final class CurrencyQuery extends AbstractQuery implements Serializable {

	private static final long serialVersionUID = -5117949582074719190L;

	/**
     * Key for storing a countries to be queried.
     */
    static final String KEY_QUERY_COUNTRIES = "Query.countries";

    /**
     * Key for storing a target literal currency codes to be queried.
     */
    static final String KEY_QUERY_CURRENCY_CODES = "Query.currencyCodes";

    /**
     * Key for storing a target numeric currency codes to be queried.
     */
    static final String KEY_QUERY_NUMERIC_CODES = "Query.numericCodes";

    /**
     * Constructor, used from the Builder.
     *
     * @param builder the corresponding {@link javax.money.CurrencyQueryBuilder}, not null.
     */
    CurrencyQuery(CurrencyQueryBuilder builder) {
        super(builder);
    }

    /**
     * Returns the target locales.
     *
     * @return the target locales, never null.
     */
    public Collection<Locale> getCountries() {
        Collection<Locale> result = get(KEY_QUERY_COUNTRIES, Collection.class);
        if (result == null) {
            return Collections.emptySet();
        }
        return result;
    }

    /**
     * Gets the currency codes, or the regular expression to select codes.
     *
     * @return the target currency codes or the regular expression, never null.
     */
    public Collection<String> getCurrencyCodes() {
        Collection<String> result = get(KEY_QUERY_CURRENCY_CODES, Collection.class);
        if (result == null) {
            return Collections.emptySet();
        }
        return result;
    }

    /**
     * Gets the numeric codes. Setting it to -1 search for currencies that have no numeric code.
     *
     * @return the target numeric codes, never null.
     */
    public Collection<Integer> getNumericCodes() {
        Collection<Integer> result = get(KEY_QUERY_NUMERIC_CODES, Collection.class);
        if (result == null) {
            return Collections.emptySet();
        }
        return result;
    }

    /**
     * Creates a new builder instances, initialized with the data from this one.
     *
     * @return a new {@link MonetaryAmountFactoryQueryBuilder} instance, never null.
     */
    public CurrencyQueryBuilder toBuilder() {
        return CurrencyQueryBuilder.of(this);
    }

}
