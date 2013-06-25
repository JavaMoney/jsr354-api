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
import java.util.Locale;
import java.util.ServiceLoader;

import javax.money.CurrencyUnit;

/**
 * Implementation of this interface define the currencies supported in the
 * system. Each provider implementation hereby may be responsible for exactly
 * one name space. For multiple name spaces being supported several providers
 * must be registered.
 * <p>
 * Registration is done using the {@link ServiceLoader} features.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyUnitProviderComponentSpi {

	/**
	 * The currency namespace provided by this instance.
	 * 
	 * @return the namespace this instance is responsible for, never null.
	 */
	public String getNamespace();

	/**
	 * This method is called by the specification, to evaluate/access a currency
	 * instance with the given names space.
	 * 
	 * @param code
	 *            The code of the currency required. This code with the name
	 *            space uniquely identify a currency instance.
	 * @return The currency unit to used, or null. Hereby the implementation
	 *         should return immutable and final instances of
	 *         {@link CurrencyUnit}. It is the responsibility of the
	 *         implementation to implement caching of currency instances
	 *         (recommended).
	 */
	public CurrencyUnit getCurrency(String code);

	/**
	 * Get the available currencies for the given {@link Locale}.
	 * 
	 * @param locale
	 *            the target {@link Locale}
	 * @param timestamp
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
	 * @return the currencies found, or null.
	 */
	public Collection<CurrencyUnit> getCurrencies(Locale locale);

	/**
	 * Get the currencies available.
	 * 
	 * @param timestamp
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
	 * @return the currencies found, or null.
	 */
	public Collection<CurrencyUnit> getCurrencies();

	/**
	 * Method that allows to check if a currency is available for a given time
	 * range.
	 * 
	 * @param code
	 *            the required code within this namespace, never null.
	 * @return true, if the code is defined.
	 */
	public boolean isAvailable(String code);

}
