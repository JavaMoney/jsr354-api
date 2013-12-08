/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package javax.money;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.spi.CurrencyProviderSpi;

/**
 * Factory singleton for {@link CurrencyUnit} instances as provided by the
 * different registered {@link CurrencyProviderSpi} instances.
 * <p>
 * This class is thread safe.
 * 
 * @version 0.8
 * @author Anatole Tresch
 */
public final class MonetaryCurrencies {

	/** Currently loaded {@link CurrencyProviderSpi} instances. */
	private static Collection<CurrencyProviderSpi> currencyProviderSpis = loadCurrencyProviderSpis();

	/**
	 * Internal shared cache of {@link CustomCurrency} instances, registered
	 * using {@link Builder} instances.
	 */
	private static final Map<String, CurrencyUnit> REGISTERED = new ConcurrentHashMap<String, CurrencyUnit>();


	/**
	 * Required for deserialization only.
	 */
	private MonetaryCurrencies() {
	}

	/**
	 * Access a new instance based on the currency code. Currencies are
	 * available as provided by {@link CurrencyProviderSpi} instances registered
	 * with the {@link ServiceLoader}.
	 * 
	 * @param currencyCode
	 *            the ISO currency code, not {@code null}.
	 * @return the corresponding {@link CurrencyUnit} instance.
	 * @throws IllegalArgumentException
	 *             if no such currency exists.
	 */
	public static CurrencyUnit getCurrency(String currencyCode) {
		CurrencyUnit cu = null;
		for (CurrencyProviderSpi spi : currencyProviderSpis) {
			try {
				cu = spi.getCurrencyUnit(currencyCode);
				if (cu != null) {
					if (!currencyCode.equals(cu.getCurrencyCode())) {
						throw new IllegalStateException(
								"Provider("
										+ spi.getClass().getName()
										+ ") returned an invalid CurrencyUnit for '"
										+ currencyCode + "': "
										+ cu.getCurrencyCode());
					}
					return cu;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryCurrencies.class.getName()).log(
						Level.SEVERE,
						"Error loading Currency '" + currencyCode
								+ "' from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		if (cu == null) {
			cu = REGISTERED.get(currencyCode);
		}
		if (cu == null) {
			throw new IllegalArgumentException("No such currency: "
					+ currencyCode);
		}
		return cu;
	}

	/**
	 * Access a new instance based on the currency code. Currencies are
	 * available as provided by {@link CurrencyProviderSpi} instances registered
	 * with the {@link ServiceLoader}.
	 * 
	 * @param currencyCode
	 *            the ISO currency code, not {@code null}.
	 * @param timestamp
	 *            the UTC timestamp of the time, when the {@link CurrencyUnit}
	 *            should be valid.
	 * @return the corresponding {@link CurrencyUnit} instance.
	 * @throws IllegalArgumentException
	 *             if no such currency exists.
	 */
	public static CurrencyUnit getCurrency(String currencyCode, long timestamp) {
		CurrencyUnit cu = null;
		for (CurrencyProviderSpi spi : currencyProviderSpis) {
			try {
				cu = spi.getCurrencyUnit(currencyCode, timestamp);
				if (cu != null) {
					if (!currencyCode.equals(cu.getCurrencyCode())) {
						throw new IllegalStateException(
								"Provider("
										+ spi.getClass().getName()
										+ ") returned an invalid CurrencyUnit for '"
										+ currencyCode + "': "
										+ cu.getCurrencyCode());
					}
					return cu;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryCurrencies.class.getName()).log(
						Level.SEVERE,
						"Error loading Currency '" + currencyCode
								+ "'/ts=" + timestamp + " from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new IllegalArgumentException("No such currency: "
				+ currencyCode);
	}

	/**
	 * Access a new instance based on the {@link Locale}. Currencies are
	 * available as provided by {@link CurrencyProviderSpi} instances registered
	 * with the {@link ServiceLoader}.
	 * 
	 * @param currencyCode
	 *            the ISO currency code, not {@code null}.
	 * @return the corresponding {@link CurrencyUnit} instance.
	 * @throws IllegalArgumentException
	 *             if no such currency exists.
	 */
	public static CurrencyUnit getCurrency(Locale locale) {
		CurrencyUnit cu = null;
		for (CurrencyProviderSpi spi : currencyProviderSpis) {
			try {
				cu = spi.getCurrencyUnit(locale);
				if (cu != null) {
					return cu;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryCurrencies.class.getName()).log(
						Level.SEVERE,
						"Error loading Currency for Locale '" + locale
								+ " from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new IllegalArgumentException("No such currency: "
				+ locale);
	}

	/**
	 * Access a new instance based on the {@link Locale}. Currencies are
	 * available as provided by {@link CurrencyProviderSpi} instances registered
	 * with the {@link ServiceLoader}.
	 * 
	 * @param currencyCode
	 *            the ISO currency code, not {@code null}.
	 * @param timestamp
	 *            the UTC timestamp of the time, when the {@link CurrencyUnit}
	 *            should be valid.
	 * @return the corresponding {@link CurrencyUnit} instance.
	 * @throws IllegalArgumentException
	 *             if no such currency exists.
	 */
	public static CurrencyUnit getCurrency(Locale locale, long timestamp) {
		CurrencyUnit cu = null;
		for (CurrencyProviderSpi spi : currencyProviderSpis) {
			try {
				cu = spi.getCurrencyUnit(locale, timestamp);
				if (cu != null) {
					return cu;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryCurrencies.class.getName()).log(
						Level.SEVERE,
						"Error loading Currency for Locale '" + locale
								+ "'/ts=" + timestamp + " from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new IllegalArgumentException("No such currency: "
				+ locale);
	}

	/**
	 * Allows to check if a {@link Currencies} instance is defined, i.e.
	 * accessible from {@link Currencies#of(String)}.
	 * 
	 * @param code
	 *            the currency code, not {@code null}.
	 * @return {@code true} if {@link Currencies#of(String)} would return a
	 *         result for the given code.
	 */
	public static boolean isCurrencyAvailable(String code) {
		try {
			getCurrency(code);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Allows to check if a {@link Currencies} instance is defined, i.e.
	 * accessible from {@link Currencies#of(String)}.
	 * 
	 * @param code
	 *            the currency code, not {@code null}.
	 * @param timestamp
	 *            the UTC timestamp of the time, when the {@link CurrencyUnit}
	 *            should be valid.
	 * @return {@code true} if {@link Currencies#of(String)} would return a
	 *         result for the given code.
	 */
	public static boolean isCurrencyAvailable(String code, long timestamp) {
		try {
			getCurrency(code, timestamp);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Allows to check if a {@link Currencies} instance is defined, i.e.
	 * accessible from {@link Currencies#of(String)}.
	 * 
	 * @param code
	 *            the currency code, not {@code null}.
	 * @return {@code true} if {@link Currencies#of(String)} would return a
	 *         result for the given code.
	 */
	public static boolean isCurrencyAvailable(Locale locale) {
		try {
			getCurrency(locale);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Allows to check if a {@link Currencies} instance is defined, i.e.
	 * accessible from {@link Currencies#of(String)}.
	 * 
	 * @param code
	 *            the currency code, not {@code null}.
	 * @param timestamp
	 *            the UTC timestamp of the time, when the {@link CurrencyUnit}
	 *            should be valid.
	 * @return {@code true} if {@link Currencies#of(String)} would return a
	 *         result for the given code.
	 */
	public static boolean isCurrencyAvailable(Locale locale, long timestamp) {
		try {
			getCurrency(locale, timestamp);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// private methods

	/**
	 * Loader method, executed on startup once.
	 * 
	 * @return the {@link CurrencyProviderSpi} loaded.
	 */
	private static Collection<CurrencyProviderSpi> loadCurrencyProviderSpis() {
		List<CurrencyProviderSpi> spis = new ArrayList<CurrencyProviderSpi>();
		try {
			for (CurrencyProviderSpi spi : ServiceLoader
					.load(CurrencyProviderSpi.class)) {
				spis.add(spi);
			}
		} catch (Exception e) {
			Logger.getLogger(MonetaryCurrencies.class.getName()).log(
					Level.SEVERE,
					"Error loading CurrencyProviderSpi instances.", e);
			return null;
		}
		Collections.sort(spis, new PriorityComparator());
		return spis;
	}

	
	private static final class PriorityComparator implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) {
			Class class1 = o1.getClass();
			Class class2 = o2.getClass();
			// TODO
			return 0;
		}
	}

}
