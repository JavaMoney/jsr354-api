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
module javax.money {
    exports javax.money;
    exports javax.money.convert;
    exports javax.money.format;
    exports javax.money.spi;
    requires java.base;
    requires java.logging;
    uses javax.money.spi.CurrencyProviderSpi;
    uses javax.money.spi.MonetaryAmountFactoryProviderSpi;
    uses javax.money.spi.MonetaryAmountFormatProviderSpi;
    uses javax.money.spi.MonetaryAmountsSingletonQuerySpi;
    uses javax.money.spi.MonetaryAmountsSingletonSpi;
    uses javax.money.spi.MonetaryConversionsSingletonSpi;
    uses javax.money.spi.MonetaryCurrenciesSingletonSpi;
    uses javax.money.spi.MonetaryFormatsSingletonSpi;
    uses javax.money.spi.MonetaryRoundingsSingletonSpi;
    uses javax.money.spi.RoundingProviderSpi;
    uses javax.money.spi.ServiceProvider;
    uses javax.money.convert.ExchangeRateProvider;
}