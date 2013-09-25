/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package org.javamoney.ext;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.CurrencyUnit;

import org.javamoney.ext.spi.MonetaryCurrenciesSingletonSpi;

/**
 * This is the service component for accessing Java Money Currencies, evaluating
 * currency namespaces, access historic currencies and map between currencies.
 * <p>
 * This class is thread-safe. However it delegates all calls to the registered
 * {@link MonetaryCurrenciesSingletonSpi} SPI (using the {@link ServiceLoader}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.9.1
 */
public final class MonetaryCurrencies {

	private static final MonetaryCurrenciesSingletonSpi CURRENCIES_SPI = loadMonetaryCurrenciesSpi();

	/**
	 * Singleton constructor.
	 */
	private MonetaryCurrencies() {
	}

	/**
	 * Method that loads the {@link MonetaryConversionsSpi} on class loading.
	 * 
	 * @return the instance or be registered into the shared variable.
	 */
	private static MonetaryCurrenciesSingletonSpi loadMonetaryCurrenciesSpi() {
		try {
			// try loading directly from ServiceLoader
			Iterator<MonetaryCurrenciesSingletonSpi> instances = ServiceLoader
					.load(
							MonetaryCurrenciesSingletonSpi.class).iterator();
			MonetaryCurrenciesSingletonSpi spiLoaded = null;
			if (instances.hasNext()) {
				spiLoaded = instances.next();
				if (instances.hasNext()) {
					throw new IllegalStateException(
							"Ambigous reference to spi (only "
									+ "one can be registered: "
									+ MonetaryCurrenciesSingletonSpi.class
											.getName());
				}
				return spiLoaded;
			}
		} catch (Throwable e) {
			Logger.getLogger(MonetaryCurrenciesSingletonSpi.class.getName())
					.log(
							Level.INFO,
							"No MonetaryConversionSpi registered, using  default.",
							e);
		}
		return new DefaultMonetaryCurrenciesSpi();
	}

	/**
	 * This method allows to evaluate, if the given currency namespace is
	 * defined. "ISO-4217" should be defined in all environments (default).
	 * 
	 * @param namespace
	 *            the required namespace
	 * @return {@code true}, if the namespace exists.
	 */
	public static boolean isNamespaceAvailable(String namespace) {
		return CURRENCIES_SPI.isNamespaceAvailable(namespace);
	}

	/**
	 * This method allows to access all namespaces currently defined. "ISO-4217"
	 * should be defined in all environments (default).
	 * 
	 * @return the array of currently defined namespaces.
	 */
	public static Collection<String> getNamespaces() {
		return CURRENCIES_SPI.getNamespaces();
	}

	/*-- Access of current currencies --*/
	/**
	 * Checks if a currency is defined using its namespace and code. This is a
	 * convenience method for {@link #getCurrency(String, String)}, where as
	 * namespace the default namespace is assumed.
	 * 
	 * @see #getDefaultNamespace()
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @return {@code true}, if the currency is defined.
	 */
	public static boolean isAvailable(String code) {
		return CURRENCIES_SPI.isAvailable(code);
	}

	/**
	 * Evaluates the currency namespace of a currency code.
	 * 
	 * @param namespace
	 *            The currency namespace, e.g. 'ISO-4217'.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @return {@code true}, if the currency is defined.
	 */
	public static String getNamespace(String code) {
		return CURRENCIES_SPI.getNamespace(code);
	}

	/**
	 * Access a currency using its code.
	 * 
	 * @param code
	 *            The code that identifies the currency.
	 * @return The {@link CurrencyUnit} found, never {@code null}.
	 * @throws UnknownCurrencyException
	 *             if the required {@link CurrencyUnit} is not defined.
	 */
	public static CurrencyUnit get(String code) {
		return CURRENCIES_SPI.get(code);
	}

	/**
	 * Access all currencies for a given namespace.
	 * 
	 * @see #getNamespaces()
	 * @see #getDefaultNamespace()
	 * @param namespace
	 *            The target namespace, not {@code null}.
	 * @return The currencies found, never null.
	 * @throws UnknownCurrencyException
	 *             if the required namespace is not defined.
	 */
	public static Collection<CurrencyUnit> getAll(String namespace) {
		return CURRENCIES_SPI.getAll(namespace);
	}

	/**
	 * This method maps the given {@link CurrencyUnit} to another
	 * {@link CurrencyUnit} with the given target namespace.
	 * 
	 * @param unit
	 *            The source unit, never {@code null}.
	 * @param targetNamespace
	 *            the target namespace, never {@code null}.
	 * @return The mapped {@link CurrencyUnit}, or {@code null}.
	 */
	public static CurrencyUnit map(CurrencyUnit currencyUnit,
			String targetNamespace) {
		return CURRENCIES_SPI.map(currencyUnit, targetNamespace);
	}

	/**
	 * This method maps the given {@link CurrencyUnit} to another
	 * {@link CurrencyUnit} with the given target namespace.
	 * 
	 * @param unit
	 *            The source unit, never {@code null}.
	 * @param targetNamespace
	 *            the target namespace, never {@code null}.
	 * @param timestamp
	 *            the target timestamp
	 * @return The mapped {@link CurrencyUnit}, or {@code null}.
	 */
	public static CurrencyUnit map(CurrencyUnit currencyUnit,
			String targetNamespace, long timestamp) {
		return CURRENCIES_SPI.map(currencyUnit, targetNamespace, timestamp);
	}

	/**
	 * Default implementation of {@link MonetaryCurrenciesSingletonSpi}, active
	 * if no instance of {@link MonetaryCurrenciesSingletonSpi} was registered
	 * using the {@link ServiceLoader}.
	 * 
	 * @author Anatole Tresch
	 */
	private static final class DefaultMonetaryCurrenciesSpi implements
			MonetaryCurrenciesSingletonSpi {
		/** Error message for unsupported operations. */
		private static final String ERROR_MESSAGE = "No "
				+ MonetaryCurrenciesSingletonSpi.class.getName()
				+ " registered.";

		/**
		 * This method allows to evaluate, if the given currency namespace is
		 * defined. {@code "ISO-4217"} should be defined in all environments
		 * (default).
		 * 
		 * @param namespace
		 *            the required namespace
		 * @return {@code true}, if the namespace exists.
		 */
		@Override
		public boolean isNamespaceAvailable(String namespace) {
			return false;
		}

		/**
		 * This method allows to access all namespaces currently defined.
		 * "ISO-4217" should be defined in all environments (default).
		 * 
		 * @return the collection of currently defined namespace, never
		 *         {@code null}.
		 */
		@Override
		public Collection<String> getNamespaces() {
			return Collections.emptySet();
		}

		/*-- Access of current currencies --*/
		/**
		 * Checks if a currency is defined using its namespace and code. This is
		 * a convenience method for {@link #getCurrency(String, String)}, where
		 * as namespace the default namespace is assumed.
		 * 
		 * @see #getDefaultNamespace()
		 * @param code
		 *            The code that, together with the namespace identifies the
		 *            currency.
		 * @return {@code true}, if the currency is defined.
		 */
		@Override
		public boolean isAvailable(String code) {
			return false;
		}

		/**
		 * Access a currency using its code. This is a convenience method for
		 * {@link #getCurrency(String, String)}, where as namespace the default
		 * namespace is assumed.
		 * 
		 * @see #getDefaultNamespace()
		 * @param code
		 *            The code that, together with the namespace identifies the
		 *            currency.
		 * @return The currency found, never {@code null}.
		 * @throws UnknownCurrencyException
		 *             if the required currency is not defined.
		 */
		@Override
		public CurrencyUnit get(String code) {
			throw new IllegalArgumentException("Unknown currency: " + code);
		}

		/**
		 * This method maps the given {@link CurrencyUnit} to another
		 * {@link CurrencyUnit} with the given target namespace.
		 * 
		 * @param unit
		 *            The source unit, never {@code null}.
		 * @param currencyCode
		 *            the target currencyCode, never {@code null}.
		 * @return The mapped {@link CurrencyUnit}, or {@code null}.
		 */
		@Override
		public CurrencyUnit map(CurrencyUnit currencyUnit,
				String currencyCode) {
			return null;
		}

		/**
		 * This method maps the given {@link CurrencyUnit} to another
		 * {@link CurrencyUnit} with the given target namespace.
		 * 
		 * @param unit
		 *            The source unit, never {@code null}.
		 * @param targetNamespace
		 *            the target namespace, never {@code null}.
		 * @return The mapped {@link CurrencyUnit}, or {@code null}.
		 */
		@Override
		public CurrencyUnit map(CurrencyUnit currencyUnit,
				String currencyCode, long timestamp) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.spi.MonetaryCurrenciesSingletonSpi#getAll(java.lang
		 * .String)
		 */
		@Override
		public Collection<CurrencyUnit> getAll(String namespace) {
			return Collections.emptySet();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.spi.MonetaryCurrenciesSingletonSpi#getNamespace(java
		 * .lang.String)
		 */
		@Override
		public String getNamespace(String code) {
			throw new IllegalArgumentException("Unknown currency: " + code);
		}

	}
}
