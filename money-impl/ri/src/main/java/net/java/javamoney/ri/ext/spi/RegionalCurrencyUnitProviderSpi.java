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
package net.java.javamoney.ri.ext.spi;

import java.util.Collection;

import javax.money.CurrencyUnit;
import javax.money.ext.Region;

import net.java.javamoney.ri.ext.provider.RegionalCurrencyUnitProvider;

/**
 * This class models the component defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit} using {@link Region}s. It is provided by the
 * Monetary singleton.
 * 
 * @author Anatole Tresch
 */
public interface RegionalCurrencyUnitProviderSpi {

	/**
	 * Access all currencies matching a {@link Region}.
	 * 
	 * @param locale
	 *            the target locale, not null.
	 * @return the currencies found, or null.
	 */
	public Collection<CurrencyUnit> getAll(Region region);

	/**
	 * Access all currencies matching a {@link Region}, valid at the given
	 * timestamp.
	 * 
	 * @param locale
	 *            the target locale, not null.
	 * @param timestamp
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
	 * @return the currencies found, or null.
	 */
	public Collection<CurrencyUnit> getAll(Region region, Long timestamp);

}
