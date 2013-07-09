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
 *    Werner Keil - extensions and adaptions.
 */
package net.java.javamoney.ri.ext;

import net.java.javamoney.ri.ext.AbstractCurrencyUnitProviderService;
import java.util.ServiceLoader;

import javax.money.CurrencyUnit;
import javax.money.ext.spi.CurrencyUnitProviderSpi;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit}.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class SECurrencyUnitProviderService extends AbstractCurrencyUnitProviderService {

    @Override
    protected Iterable<CurrencyUnitProviderSpi> getCurrencyUnitProviderSpis() {
        return ServiceLoader.load(CurrencyUnitProviderSpi.class);
    }
}
