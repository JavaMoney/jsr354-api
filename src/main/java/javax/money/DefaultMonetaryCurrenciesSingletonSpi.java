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

import javax.money.spi.Bootstrap;
import javax.money.spi.CurrencyProviderSpi;
import javax.money.spi.MonetaryCurrenciesSingletonSpi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory singleton for {@link CurrencyUnit} instances as provided by the
 * different registered {@link javax.money.spi.CurrencyProviderSpi} instances.
 * <p/>
 * This class is thread safe.
 *
 * @author Anatole Tresch
 * @version 0.8
 */
final class DefaultMonetaryCurrenciesSingletonSpi implements MonetaryCurrenciesSingletonSpi {

    @Override
    public Set<CurrencyUnit> getCurrencies(CurrencyQuery query) {
        Set<CurrencyUnit> result = new HashSet<>();
        for (CurrencyProviderSpi spi : Bootstrap.getServices(CurrencyProviderSpi.class)) {
            try {
                result.addAll(spi.getCurrencies(query));
            } catch (Exception e) {
                Logger.getLogger(DefaultMonetaryCurrenciesSingletonSpi.class.getName())
                        .log(Level.SEVERE, "Error loading currency provider names for " + spi.getClass().getName(),
                                e);
            }
        }
        return result;
    }

    /**
     * This default implementation simply returns all providers defined in arbitrary order.
     *
     * @return the default provider chain, never null.
     */
    @Override
    public List<String> getDefaultProviderChain() {
        List<String> list = new ArrayList<>(getProviderNames());
        Collections.sort(list);
        return list;
    }

    /**
     * Get the names of the currently loaded providers.
     *
     * @return the names of the currently loaded providers, never null.
     */
    @Override
    public Set<String> getProviderNames() {
        Set<String> result = new HashSet<>();
        for (CurrencyProviderSpi spi : Bootstrap.getServices(CurrencyProviderSpi.class)) {
            try {
                result.add(spi.getProviderName());
            } catch (Exception e) {
                Logger.getLogger(DefaultMonetaryCurrenciesSingletonSpi.class.getName())
                        .log(Level.SEVERE, "Error loading currency provider names for " + spi.getClass().getName(),
                                e);
            }
        }
        return result;
    }

}
