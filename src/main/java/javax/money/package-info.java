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
/**
 * Main package of the Money and Currency API. In more detail:
 * <ul>
 * <li>JSR 354 defines a minimal set of interfaces for interoperability, since
 * concrete usage scenarios do not allow to define an implementation that is
 * capable of covering all aspects identified. Consequently it must be possible
 * that implementations can provide several implementations for monetary
 * amounts.
 *
 * Users should not reference the interfaces, instead the value types should be
 * used.</li>
 * <li>Implementations must provide value types for currencies and amounts,
 * implementing {@link javax.money.CurrencyUnit} and
 * {@link javax.money.MonetaryAmount}.</li>
 * <li>Implementations must also provide a minimal set of roundings, modeled as
 * {@link javax.money.MonetaryRounding}. This should include basic roundings for
 * ISO currencies, roundings defined by {@link java.math.MathContext} or
 * {@link java.math.RoundingMode}.</li>
 * <li>This API must avoid restrictions that prevents its use in different
 * runtime environments, such as EE or ME.</li>
 * <li>Method naming and style for currency modeling should be in alignment
 * with parts of the Java Collection API or {@code java.time} / [JodaMoney].</li>
 * </ul>
 */
package javax.money;

