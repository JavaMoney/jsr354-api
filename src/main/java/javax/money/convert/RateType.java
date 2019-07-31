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
package javax.money.convert;

/**
 * This enumeration defines the different type of rates that can be provided by {@link javax.money.convert
 * .ExchangeRateProvider} implementations. Hereby the rate provider's {@link javax.money.convert.ProviderContext} can
 * contain
 * additional information about the rates provided. Similarly, when accessing {@link javax.money.convert
 * .ExchangeRateProvider} or {@link javax.money.convert.CurrencyConversion} instances corresponding attributes can
 * be passed within an (optional) {@link javax.money.convert.ConversionContext}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public enum RateType{
    /**
     * Historic rates, e.g. from the day before or older.
     */
    HISTORIC,
    /**
     * Real-time rates should be as adequate as possible, basically not more than a few milliseconds late.
     */
    REALTIME,
    /**
     * Deferred rates are basically also current rates, but have some fixed delay, e.g. 20 minutes.
     */
    DEFERRED,
    /**
     * Any other type of rates. You may use the {@link javax.money.convert.ProviderContext},
     * {@link javax.money.convert.ConversionContext} to define additional details.
     */
    OTHER,
    /**
     * Any type of rates. This can be used, where any type of rate is suitable. This may be feasible if you
     * access a specific chain of rate providers,
     * where the order of providers already implies a prioritization and the rate type in that scenario is not relevant.
     */
    ANY
} 