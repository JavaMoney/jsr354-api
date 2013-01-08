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
package net.java.javamoney;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;

import javax.money.CurrencyUnit;
import javax.money.Region;
import javax.money.UnknownCurrencyException;
import javax.money.spi.CurrencyUnitProvider;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit}.
 * 
 * @author Anatole Tresch
 */
public final class Currencies extends AbstractSPIComponent {
	/** The singleton instance. */
	private static final Currencies INSTANCE = new Currencies();
	/** The currently loaded providers. */
	private Map<String, List<CurrencyUnitProvider>> currencyProviders;

	/**
	 * Singleton constructor.
	 */
	private Currencies() {
	}

	/**
	 * This method reloads the providers available from the
	 * {@link ServiceLoader}. This adds providers that were not yet visible
	 * before.
	 */
	public void reload() {
		List<CurrencyUnitProvider> providers = getSPIProviders(CurrencyUnitProvider.class);
		for (CurrencyUnitProvider currencyUnitProvider : providers) {
			List<CurrencyUnitProvider> providerList = currencyProviders
					.get(currencyUnitProvider.getNamespace());
			if (providerList == null) {
				providerList = new ArrayList<CurrencyUnitProvider>();
				INSTANCE.currencyProviders.put(
						currencyUnitProvider.getNamespace(), providerList);
			}
			if (!providerList.contains(currencyUnitProvider)) {
				providerList.add(currencyUnitProvider);
			}
		}
	}

	/**
	 * Access a currency using its name space and code. This is a convenience
	 * method for {@link #getCurrency(String, String, Date)}, where {@code null}
	 * is passed for the target date (meaning current date).
	 * 
	 * @param namespace
	 *            The name space, e.g. 'ISO-4217'.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @return The currency found, never null.
	 * @throws UnknownCurrencyException
	 *             if the required currency is not defined.
	 */
	public static CurrencyUnit getCurrency(String namespace, String code) {
		return getCurrency(namespace, code, -1);
	}

	/**
	 * Access a currency using its name space and code.
	 * 
	 * @param namespace
	 *            The name space, e.g. 'ISO-4217'.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @param timestamp
	 *            The target UTC timestamp, or -1 for the current UTC timestamp.
	 * @return The currency found, never null.
	 * @throws UnknownCurrencyException
	 *             if the required currency is not defined.
	 */
	public static CurrencyUnit getCurrency(String namespace, String code,
			long timestamp) {
		List<CurrencyUnitProvider> providers = INSTANCE.currencyProviders
				.get(namespace);
		if (providers == null) {
			throw new IllegalArgumentException("Invalid currency namespace: "
					+ namespace);
		}
		for (CurrencyUnitProvider currencyUnitProvider : providers) {
			try {
				CurrencyUnit currencyUnit = currencyUnitProvider.getCurrency(
						code, -1);
				if (currencyUnit != null) {
					return currencyUnit;
				}
			} catch (Exception e) {
				// log error thrown by currencyUnitProvider
			}
		}
		throw new IllegalArgumentException("No such currency " + namespace
				+ ':' + code);
	}

	/**
	 * Access all currencies currently available.
	 * 
	 * @return the list of currencies available, never null.
	 */
	public static CurrencyUnit[] getCurrencies() {
		return null;
	}

	/**
	 * Access all currencies available for the given timestamp.
	 * 
	 * @param timestamp
	 *            The target UTC timestamp, or -1 for the current UTC timestamp.
	 * @return the list of currencies available, never null.
	 */
	public static CurrencyUnit[] getCurrencies(long timestamp) {
		return null;
	}

	/**
	 * Access all currencies matching a {@link Region}.
	 * 
	 * @param locale
	 *            the target locale, not null.
	 * @return the currencies found, never null.
	 */
	public static CurrencyUnit[] getCurrencies(Region region) {
		return null;
	}

	/**
	 * Access all currencies matching a {@link Region}, valid at the given
	 * timestamp.
	 * 
	 * @param locale
	 *            the target locale, not null.
	 * @param timestamp
	 *            The target UTC timestamp, or -1 for the current UTC timestamp.
	 * @return the currencies found, never null.
	 */
	public static CurrencyUnit[] getCurrencies(Region region, long timestamp) {
		return null;
	}

	/**
	 * Checks if a currency is defined using its name space and code.
	 * 
	 * @param namespace
	 *            The name space, e.g. 'ISO-4217'.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @return true, if the currency is defined.
	 */
	public static boolean isCurrencyAvailable(String namespace, String code) {
		return false;
	}

	/**
	 * Checks if a currency is defined using its name space and code.
	 * 
	 * @param namespace
	 *            The name space, e.g. 'ISO-4217'.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @param timestamp
	 *            The target UTC timestamp, or -1 for the current UTC timestamp.
	 * @return true, if the currency is defined.
	 */
	public static boolean isCurrencyAvailable(String namespace, String code,
			long timestamp) {
		return isCurrencyAvailable(namespace, code, timestamp, -1L);
	}

	/**
	 * Checks if a currency is defined using its name space and code for the
	 * given time period.
	 * 
	 * @param namespace
	 *            The name space, e.g. 'ISO-4217'.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @param start
	 *            The starting UTC timestamp, or -1 for the current UTC
	 *            timestamp.
	 * @param end
	 *            The ending UTC timestamp, or -1 for the current UTC timestamp.
	 * @return true, if the currency is defined.
	 */
	public static boolean isCurrencyAvailable(String namespace, String code,
			long start, long end) {
		return false;
	}

	/**
	 * This method allows to evaluate, if the given currency name space is
	 * defined. "ISO-4217" should be defined in all environments (default).
	 * 
	 * @param namespace
	 *            the required name space
	 * @return true, if the name space exists.
	 */
	public static boolean isCurrencyNamespaceDefined(String namespace) {
		return false;
	}

	/**
	 * This method allows to access all name spaces currently defined.
	 * "ISO-4217" should be defined in all environments (default).
	 * 
	 * @return the array of currently defined name space.
	 */
	public static String[] getCurrencyNamespaces() {
		return null;
	}

	/**
	 * Access all currencies matching a {@link Locale}.
	 * 
	 * @param locale
	 *            the target locale, not null.
	 * @return the currencies found, never null.
	 */
	public static CurrencyUnit[] getCurrencies(Locale locale) {
		return null;
	}

	/**
	 * Access all currencies matching a {@link Locale}, valid at the given
	 * timestamp.
	 * 
	 * @param locale
	 *            the target locale, not null.
	 * @param timestamp
	 *            The target UTC timestamp, or -1 for the current UTC timestamp.
	 * @return the currencies found, never null.
	 */
	public static CurrencyUnit[] getCurrencies(Locale locale, long timestamp) {
		return null;
	}

	// TODO move to RI somehow.
	// /**
	// * Register a {@link CurrencyUnit} programmatically to a {@link Locale}.
	// *
	// * @param currency
	// * the currency
	// * @param locale
	// * the locale
	// */
	// public static void registerCurrency(CurrencyUnit currency, Locale locale)
	// {
	//
	// }
	//
	// /**
	// * Unregister a {@link CurrencyUnit} programmatically from a {@link
	// Locale}.
	// * @param unit
	// */
	// public static void unregisterCurrency(CurrencyUnit unit, Locale locale) {
	//
	// }

}
