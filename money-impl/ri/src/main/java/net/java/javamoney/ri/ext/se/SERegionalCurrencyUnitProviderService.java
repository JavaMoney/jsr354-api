/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial implementation
 *    Wernner Keil - extensions and adaptions.
 */
package net.java.javamoney.ri.ext.se;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import javax.money.CurrencyUnit;

import javax.money.ext.spi.RegionalCurrencyUnitProviderSpi;
import net.java.javamoney.ri.ext.AbstractRegionalCurrencyUnitProviderService;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit}.
 *
 * @author Anatole Tresch
 */
public final class SERegionalCurrencyUnitProviderService extends AbstractRegionalCurrencyUnitProviderService {

    /**
     * Loaded region providers.
     */
    private List<RegionalCurrencyUnitProviderSpi> regionalCurrencyProviders;

    @Override
    protected Iterable<RegionalCurrencyUnitProviderSpi> getRegionalCurrentyUnitProviderSpis() {
        if (regionalCurrencyProviders == null) {
            List<RegionalCurrencyUnitProviderSpi> provs = new ArrayList<RegionalCurrencyUnitProviderSpi>();
            Iterable<RegionalCurrencyUnitProviderSpi> services = ServiceLoader.load(RegionalCurrencyUnitProviderSpi.class);
            for (RegionalCurrencyUnitProviderSpi regionalCurrencyUnitProviderSpi : services) {
                provs.add(regionalCurrencyUnitProviderSpi);
            }
            this.regionalCurrencyProviders = provs;
        }
        return this.regionalCurrencyProviders;
    }
}
