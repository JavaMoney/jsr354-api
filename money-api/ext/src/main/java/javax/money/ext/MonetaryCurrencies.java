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
package javax.money.ext;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.CurrencyUnit;
import javax.money.UnknownCurrencyException;
import javax.money.ext.spi.CurrencyUnitProviderSpi;
import javax.money.ext.spi.MonetaryCurrenciesSingletonSpi;

/**
 * This is the service component for accessing Java Money Currencies, evaluating
 * currency namespaces, access historic currencies and map between currencies.
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
	 * Access the default namespace that this {@link CurrencyUnitProviderSpi}
	 * instance is using. The default namespace can be changed by adding a file
	 * META-INF/java-money.properties with the following entry
	 * {@code defaultCurrencyNamespace=myNamespace}. When not set explicitly
	 * {@code ISO-4217} is assummed.
	 * 
	 * @return the default namespace used.
	 */
	public static String getDefaultNamespace() {
		return CURRENCIES_SPI.getDefaultNamespace();
	}

	/**
	 * This method allows to evaluate, if the given currency name space is
	 * defined. "ISO-4217" should be defined in all environments (default).
	 * 
	 * @param namespace
	 *            the required name space
	 * @return true, if the name space exists.
	 */
	public static boolean isNamespaceAvailable(String namespace) {
		return CURRENCIES_SPI.isNamespaceAvailable(namespace);
	}

	/**
	 * This method allows to access all name spaces currently defined.
	 * "ISO-4217" should be defined in all environments (default).
	 * 
	 * @return the array of currently defined name space.
	 */
	public static Collection<String> getNamespaces() {
		return CURRENCIES_SPI.getNamespaces();
	}

	/*-- Access of current currencies --*/
	/**
	 * Checks if a currency is defined using its name space and code. This is a
	 * convenience method for {@link #getCurrency(String, String)}, where as
	 * namespace the default namespace is assumed.
	 * 
	 * @see #getDefaultNamespace()
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @return true, if the currency is defined.
	 */
	public static boolean isAvailable(String code) {
		return CURRENCIES_SPI.isAvailable(code);
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
	public static boolean isAvailable(String namespace, String code) {
		return CURRENCIES_SPI.isAvailable(namespace, code);
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
	public static CurrencyUnit get(String namespace, String code) {
		return CURRENCIES_SPI.get(namespace, code);
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
	 * @return The currency found, never null.
	 * @throws UnknownCurrencyException
	 *             if the required currency is not defined.
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
	 *            The target namespace, not null.
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
	 * @return The mapped {@link CurrencyUnit}, or null.
	 */
	public static CurrencyUnit map(String targetNamespace,
			CurrencyUnit currencyUnit) {
		return CURRENCIES_SPI.map(targetNamespace, currencyUnit);
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
	 * @return The mapped {@link CurrencyUnit}, or null.
	 */
	public static CurrencyUnit map(String targetNamespace, long timestamp,
			CurrencyUnit currencyUnit) {
		return CURRENCIES_SPI.map(targetNamespace, timestamp, currencyUnit);
	}

	public static Set<String> getValidityProviders() {
		return CURRENCIES_SPI.getValidityProviders();
	}

	/**
	 * Access an instance of the CurrencyValidity for the required validity
	 * source.
	 * 
	 * @param provider
	 *            the validity provider.
	 * @return
	 */
	public static CurrencyValidity getCurrencyValidity(String provider) {
		return CURRENCIES_SPI.getCurrencyValidity(provider);
	}

	/**
	 * This method maps the given {@link CurrencyUnit} instances to another
	 * {@link CurrencyUnit} instances with the given target namespace.
	 * 
	 * @param units
	 *            The source units, never {@code null}.
	 * @param targetNamespace
	 *            the target namespace, never {@code null}.
	 * @return The mapped {@link CurrencyUnit} instances (same array length). If
	 *         a unit could not be mapped, the according array element will be
	 *         {@code null}.
	 */
	public static List<CurrencyUnit> mapAll(String targetNamespace,
			CurrencyUnit... units) {
		return CURRENCIES_SPI.mapAll(targetNamespace, units);
	}

	public static List<CurrencyUnit> mapAll(String targetNamespace,
			long timestamp, CurrencyUnit... units) {
		return CURRENCIES_SPI.mapAll(targetNamespace, timestamp, units);
	}

	/**
	 * 
	 * @author Anatole
	 */
	private static final class DefaultMonetaryCurrenciesSpi implements
			MonetaryCurrenciesSingletonSpi {

		private static final String ERROR_MESSAGE = "No "
				+ MonetaryCurrenciesSingletonSpi.class.getName()
				+ " registered.";

		/**
		 * Access the default namespace that this
		 * {@link CurrencyUnitProviderSpi} instance is using. The default
		 * namespace can be changed by adding a file
		 * META-INF/java-money.properties with the following entry
		 * {@code defaultCurrencyNamespace=myNamespace}. When not set explicitly
		 * {@code ISO-4217} is assummed.
		 * 
		 * @return the default namespace used.
		 */
		public String getDefaultNamespace() {
			throw new UnsupportedOperationException(ERROR_MESSAGE);
		}

		/**
		 * This method allows to evaluate, if the given currency name space is
		 * defined. "ISO-4217" should be defined in all environments (default).
		 * 
		 * @param namespace
		 *            the required name space
		 * @return true, if the name space exists.
		 */
		public boolean isNamespaceAvailable(String namespace) {
			return false;
		}

		/**
		 * This method allows to access all name spaces currently defined.
		 * "ISO-4217" should be defined in all environments (default).
		 * 
		 * @return the array of currently defined name space.
		 */
		public Collection<String> getNamespaces() {
			return Collections.emptySet();
		}

		/*-- Access of current currencies --*/
		/**
		 * Checks if a currency is defined using its name space and code. This
		 * is a convenience method for {@link #getCurrency(String, String)},
		 * where as namespace the default namespace is assumed.
		 * 
		 * @see #getDefaultNamespace()
		 * @param code
		 *            The code that, together with the namespace identifies the
		 *            currency.
		 * @return true, if the currency is defined.
		 */
		public boolean isAvailable(String code) {
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
		 * @return true, if the currency is defined.
		 */
		public boolean isAvailable(String namespace, String code) {
			return false;
		}

		/**
		 * Access a currency using its name space and code. This is a
		 * convenience method for {@link #getCurrency(String, String, Date)},
		 * where {@code null} is passed for the target date (meaning current
		 * date).
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
		public CurrencyUnit get(String namespace, String code) {
			throw new UnknownCurrencyException(namespace, code);
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
		 * @return The currency found, never null.
		 * @throws UnknownCurrencyException
		 *             if the required currency is not defined.
		 */
		public CurrencyUnit get(String code) {
			throw new UnknownCurrencyException(getDefaultNamespace(), code);
		}

		/**
		 * This method maps the given {@link CurrencyUnit} to another
		 * {@link CurrencyUnit} with the given target namespace.
		 * 
		 * @param unit
		 *            The source unit, never {@code null}.
		 * @param targetNamespace
		 *            the target namespace, never {@code null}.
		 * @return The mapped {@link CurrencyUnit}, or null.
		 */
		public CurrencyUnit map(String targetNamespace,
				CurrencyUnit currencyUnit) {
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
		 * @return The mapped {@link CurrencyUnit}, or null.
		 */
		public CurrencyUnit map(String targetNamespace, long timestamp,
				CurrencyUnit currencyUnit) {
			return null;
		}

		public Set<String> getValidityProviders() {
			return Collections.emptySet();
		}

		/**
		 * Access an instance of the CurrencyValidity for the required validity
		 * source.
		 * 
		 * @param provider
		 *            the validity provider.
		 * @return
		 */
		public CurrencyValidity getCurrencyValidity(String provider) {
			throw new UnsupportedOperationException(ERROR_MESSAGE);
		}

		/**
		 * This method maps the given {@link CurrencyUnit} instances to another
		 * {@link CurrencyUnit} instances with the given target namespace.
		 * 
		 * @param units
		 *            The source units, never {@code null}.
		 * @param targetNamespace
		 *            the target namespace, never {@code null}.
		 * @return The mapped {@link CurrencyUnit} instances (same array
		 *         length). If a unit could not be mapped, a
		 *         IllegalArgumentException is thrown.
		 */
		public List<CurrencyUnit> mapAll(String targetNamespace,
				CurrencyUnit... units) {
			// List<CurrencyUnit> resultList = new ArrayList<CurrencyUnit>();
			// for (CurrencyUnit currencyUnit : units) {
			// CurrencyUnit result = currencyUnitMapper.map(targetNamespace,
			// currencyUnit, null);
			// if (result == null) {
			// throw new IllegalArgumentException("Cannot map curreny " +
			// currencyUnit + " to namespace "
			// + targetNamespace);
			// }
			// resultList.add(result);
			// }
			// return resultList;
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}

		public List<CurrencyUnit> mapAll(String targetNamespace,
				long timestamp, CurrencyUnit... units) {
			// List<CurrencyUnit> resultList = new ArrayList<CurrencyUnit>();
			// for (CurrencyUnit currencyUnit : units) {
			// CurrencyUnit result = currencyUnitMapper.map(targetNamespace,
			// currencyUnit, timestamp);
			// if (result == null) {
			// throw new IllegalArgumentException("Cannot map curreny " +
			// currencyUnit + " to namespace "
			// + targetNamespace);
			// }
			// resultList.add(result);
			// }
			// return resultList;
			throw new IllegalArgumentException(ERROR_MESSAGE);
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

	}
}
