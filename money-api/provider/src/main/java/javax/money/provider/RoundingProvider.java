/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * Contributors:
 *    Anatole Tresch - initial version.
 */
package javax.money.provider;

import java.util.Enumeration;

import javax.money.CurrencyUnit;
import javax.money.Rounding;

/**
 * This instance provides default {@link Rounding}, e.g. for ISO currencies.
 * 
 * @author Anatole Tresch
 */
public interface RoundingProvider {

	/**
	 * Access the {@link Rounding} by id.
	 * 
	 * @param name
	 *            the rounding's id. not null.
	 * @return the {@link Rounding}. If no explicit {@link Rounding} is defined,
	 *         {@code null} is returned.
	 */
	public Rounding getRounding(String name);

	/**
	 * Access the available rounding ids.
	 * 
	 * @return the ids available, never {@code null}.
	 */
	public Enumeration<String> getRoundingIds();

	/**
	 * Allows to determine if a named {@link Rounding} is available.
	 * 
	 * @param id
	 *            the {@link Rounding}'s identifier.
	 * @return true, if such a {@link Rounding} is defined.
	 */
	public boolean isRoundingDefined(String id);

	/**
	 * Access the {@link Rounding} for a given {@link CurrencyUnit}.
	 * 
	 * @param currency
	 *            the currency instance. not null.
	 * @return the {@link Rounding}. If no explicit {@link Rounding} is defined,
	 *         it should be created/registered based on
	 *         {@link CurrencyUnit#getDefaultFractionDigits()}.
	 */
	public Rounding getRounding(CurrencyUnit currency);

	/**
	 * Access the {@link Rounding} for a given {@link CurrencyUnit} and
	 * timestamp.
	 * 
	 * @param currency
	 *            the currency instance. not null.
	 * @param timestamp
	 *            the target timestamp for the {@link Rounding}, or {@code null}
	 *            for the current UTC time.
	 * @return the {@link Rounding}. If no explicit {@link Rounding} is defined,
	 *         it should be created/registered based on
	 *         {@link CurrencyUnit#getDefaultFractionDigits()}.
	 */
	public Rounding getRounding(CurrencyUnit currency, Long timestamp);

}
