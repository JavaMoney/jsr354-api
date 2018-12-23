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
package javax.money.spi;

import java.util.Set;

import javax.money.*;

/**
 * This SPI allows to extends/override the roundings available for
 * {@link CurrencyUnit}. The JSRs implementation already provides default
 * roundings. By registering instances of this interface using the
 * {@link javax.money.spi.Bootstrap}, the default behaviour can be
 * overridden and extended, e.g. for supporting also special roundings.
 * <p>
 * Implementations of this interface must be
 * <ul>
 * <li>thread-safe
 * <li>not require loading of other resources.
 * </ul>
 * If required, it is possible to implement this interface in a contextual way,
 * e.g. providing different roundings depending on the current EE application
 * context. Though in most cases rounding should be a general concept that does
 * not require contextual handling.
 * 
 * @author Anatole Tresch
 */
public interface RoundingProviderSpi {

    /**
     * Evaluate the rounding that match the given query.
     * @param query the rounding query.
     * @return the matching rounding instance, or {@code null}.
     */
    MonetaryRounding getRounding(RoundingQuery query);

	/**
	 * Access the ids of the roundings defined by this provider.
	 * 
	 * @return the ids of the defined roundings, never {@code null}.
	 */
	Set<String> getRoundingNames();

    /**
     * Get the provider's unique name.
     * @return the provider's unique name, not {@code null}.
     */
    default String getProviderName(){
        return getClass().getSimpleName();
    }


}
