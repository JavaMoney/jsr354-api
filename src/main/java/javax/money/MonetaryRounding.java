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

/**
 * Interface representing a monetary rounding. Rounding must not necessarily represent roundings in a pure arithmetical sense. Basically a rounding
 * can scale an amount arbitrarily.
 * <h3>Implementation specification</h3>
 * Implementations of this interface must be
 * <ul>
 *     <li>final</li>
 *     <li>immutable</li>
 *     <li>thread-safe</li>
 * </ul>
 * Implementations of this interface should be
 * <ul>
 *     <li>serializable</li>
 * </ul>
 */
public interface MonetaryRounding extends MonetaryOperator{

    /**
     * Access the rounding's context, which gives more details of the rounding instances such as
     * <ul>
     *     <li>The rounding's name (required)</li>
     *     <li>Its provider (required)</li>
     *     <li>Its base currency</li>
     *     <li>its scale and rounding mode</li>
     *     <li>any other attributes useful to describe the rounding</li>
     * </ul>
     * @return the rounding's context, never null.
     */
    RoundingContext getRoundingContext();

}
