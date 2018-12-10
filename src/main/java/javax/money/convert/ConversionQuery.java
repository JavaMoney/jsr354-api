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
package javax.money.convert;

import javax.money.AbstractQuery;
import javax.money.CurrencySupplier;
import javax.money.CurrencyUnit;
import java.util.*;

/**
 * Query for accessing {@link javax.money.convert.CurrencyConversion} instances. If not properties are set the query
 * should returns the <i>default</i> currencies.
 * <p>
 * This class is immutable, serializable and thread-safe.
 * </p>
 */
public final class ConversionQuery extends AbstractQuery implements CurrencySupplier {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -9147265628185601586L;


    /**
     * THe key used for the base currency attribute.
     */
    static final String KEY_BASE_CURRENCY = "Query.baseCurrency";

    /**
     * THe key used for the term currency attribute.
     */
    static final String KEY_TERM_CURRENCY = "Query.termCurrency";

    /**
     * THe key used for the rate types attribute.
     */
    static final String KEY_RATE_TYPES = "Query.rateTypes";

    /**
     * Constructor, used from the ConversionQueryBuilder.
     *
     * @param builder the corresponding builder, not null.
     */
    ConversionQuery(ConversionQueryBuilder builder) {
        super(builder);
    }

    /**
     * Get the rate types set.
     *
     * @return the rate types set, or an empty array, but never null.
     */
    @SuppressWarnings("unchecked")
    public Set<RateType> getRateTypes() {
        Set<RateType> result = get(KEY_RATE_TYPES, Set.class);
        if (result == null) {
            return Collections.emptySet();
        }
        return result;
    }

    /**
     * Get the base currency. This attribute is optional, when a {@link javax.money.convert.CurrencyConversion}
     * is accessed. It is optional if accessing instances of {@link javax.money.convert.ExchangeRateProvider}. If set
     * it can constraint
     * a {@link javax.money.convert.CurrencyConversion} or {@link javax.money.convert.ExchangeRateProvider} to
     * only support one type of base currency. By default it is not set, hereby determining the base currency by the
     * amount onto which the conversion is applied.
     *
     * @return the base CurrencyUnit, or null.
     */
    public CurrencyUnit getBaseCurrency() {
        return get(KEY_BASE_CURRENCY, CurrencyUnit.class);
    }

    /**
     * Get the terminating currency. This attribute is required, when a {@link javax.money.convert.CurrencyConversion}
     * is accessed. It is optional if accessing instances of {@link javax.money.convert.ExchangeRateProvider}.
     *
     * @return the terminating CurrencyUnit, or null.
     */
    public CurrencyUnit getCurrency() {
        return get(KEY_TERM_CURRENCY, CurrencyUnit.class);
    }

    /**
     * Creates a new Builder preinitialized with values from this instance.
     *
     * @return a new Builder, never null.
     */
    public ConversionQueryBuilder toBuilder() {
        return ConversionQueryBuilder.of(this);
    }

}