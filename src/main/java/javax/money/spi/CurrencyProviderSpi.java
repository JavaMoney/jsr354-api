/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2019 Werner Keil, Otavio Santana, Trivadis AG
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
package javax.money.spi;

import javax.money.CurrencyQuery;
import javax.money.CurrencyUnit;
import java.util.Set;

/**
 * SPI (core) to be registered using the {@link javax.money.spi.Bootstrap}, which allows to
 * register/provide additional currencies into the system automatically on
 * startup. The implementation is allowed to be implemented in y contextual way,
 * so depending on the runtime context, different currencies may be available.
 *
 * @author Anatole Tresch
 */
public interface CurrencyProviderSpi{


    /**
     * The unique name of this currency provider instance.
     * @return the unique provider id, never null or empty.
     */
    default String getProviderName(){
        return getClass().getSimpleName();
    }

    /**
     * Checks if a {@link CurrencyUnit} instances matching the given
     * {@link javax.money.CurrencyContext} is available from this provider.
     *
     * @param query the {@link javax.money.CurrencyQuery} containing the parameters determining the query. not null.
     * @return false, if no such unit is provided by this provider.
     */
    default boolean isCurrencyAvailable(CurrencyQuery query){
        return !getCurrencies(query).isEmpty();
    }

    /**
     * Return a {@link CurrencyUnit} instances matching the given
     * {@link javax.money.CurrencyContext}.
     *
     * @param query the {@link javax.money.CurrencyQuery} containing the parameters determining the query. not null.
     * @return the corresponding {@link CurrencyUnit}s matching, never null.
     */
    Set<CurrencyUnit> getCurrencies(CurrencyQuery query);

}
