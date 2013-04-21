/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.CurrencyUnit;
import javax.money.UnknownCurrencyException;

/**
 * This is the service component for accessing Java Money Currencies, evaluating
 * currency namespaces, access gistoric currencies and map between currencies.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.9
 */
public final class MonetaryCurrencies {

	private static final Logger LOGGER = Logger
			.getLogger(MonetaryCurrencies.class.getName());

	private static CurrencyUnitProviderSpi currencyUnitProvider = loadCurrencyUnitProviderSpi();

	private static CurrencyUnitMapperSpi currencyUnitMapper = loadCurrencyUnitMapperSpi();

	/**
	 * Singleton constructor.
	 */
	private MonetaryCurrencies() {
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
		return currencyUnitProvider.getDefaultNamespace();
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
		return currencyUnitProvider.isNamespaceAvailable(namespace, null);
	}

	/**
	 * This method allows to access all name spaces currently defined.
	 * "ISO-4217" should be defined in all environments (default).
	 * 
	 * @return the array of currently defined name space.
	 */
	public static Collection<String> getNamespaces() {
		return currencyUnitProvider.getNamespaces(null);
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
		return isAvailable(getDefaultNamespace(), code, null);
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
		return isAvailable(namespace, code, null);
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
		return get(namespace, code, null);
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
		return get(getDefaultNamespace(), code);
	}

	/**
	 * Access all current currencies for a given namespace.
	 * 
	 * @param namespace
	 *            The target namespace, not null.
	 * @return the currencies found.
	 */
	public static Collection<CurrencyUnit> getAll(String namespace) {
		if (!isNamespaceAvailable(namespace)) {
			throw new IllegalArgumentException("Invalid namespace: "
					+ namespace);
		}
		return currencyUnitProvider.getAll(namespace, null);
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
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
	 * @return The currency found, never null.
	 * @throws UnknownCurrencyException
	 *             if the required currency is not defined.
	 */
	public static CurrencyUnit get(String namespace, String code, Long timestamp) {
		return currencyUnitProvider.get(namespace, code, timestamp);
	}

	/**
	 * Access a currency using its name space and code. This is a convenience
	 * method for {@link #getCurrency(String, String)}, where as namespace the
	 * default namespace is assumed.
	 * 
	 * @see #getDefaultNamespace()
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @param timestamp
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
	 * @return The currency found, never null.
	 * @throws UnknownCurrencyException
	 *             if the required currency is not defined.
	 */
	public static CurrencyUnit get(String code, Long timestamp) {
		return currencyUnitProvider.get(getDefaultNamespace(), code, timestamp);
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
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
	 * @return true, if the currency is defined.
	 */
	public static boolean isAvailable(String namespace, String code,
			Long timestamp) {
		return currencyUnitProvider.isAvailable(namespace, code, timestamp);
	}

	/**
	 * Checks if a currency is defined using its name space and code. This is a
	 * convenience method for {@link #getCurrency(String, String)}, where as
	 * namespace the default namespace is assumed.
	 * 
	 * @see #getDefaultNamespace()
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @param timestamp
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
	 * @return true, if the currency is defined.
	 */
	public static boolean isAvailable(String code, Long timestamp) {
		return isAvailable(getDefaultNamespace(), code, timestamp);
	}

	/**
	 * Access all currencies available for a given namespace, timestamp.
	 * 
	 * @param namespace
	 *            The target namespace, not null.
	 * @param timestamp
	 *            The target UTC timestamp, or {@code null} for current.
	 * @return the currencies found.
	 */
	public Collection<CurrencyUnit> getAll(String namespace, Long timestamp) {
		return currencyUnitProvider.getAll(namespace, timestamp);
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
		return currencyUnitMapper.map(targetNamespace, null, currencyUnit);
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
		List<CurrencyUnit> resultList = new ArrayList<CurrencyUnit>();
		for (CurrencyUnit currencyUnit : units) {
			CurrencyUnit result = currencyUnitMapper.map(targetNamespace, null,
					currencyUnit);
			if (result == null) {
				throw new IllegalArgumentException("Cannot map curreny "
						+ currencyUnit + " to namespace " + targetNamespace);
			}
			resultList.add(result);
		}
		return resultList;
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
	public static CurrencyUnit map(String targetNamespace, Long timestamp,
			CurrencyUnit currencyUnit) {
		return currencyUnitMapper.map(targetNamespace, timestamp, currencyUnit);
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
			Long timestamp, CurrencyUnit... units) {
		List<CurrencyUnit> resultList = new ArrayList<CurrencyUnit>();
		for (CurrencyUnit currencyUnit : units) {
			CurrencyUnit result = currencyUnitMapper.map(targetNamespace,
					timestamp, currencyUnit);
			if (result == null) {
				throw new IllegalArgumentException("Cannot map curreny "
						+ currencyUnit + " to namespace " + targetNamespace);
			}
			resultList.add(result);
		}
		return resultList;
	}

	private static CurrencyUnitProviderSpi loadCurrencyUnitProviderSpi() {
		CurrencyUnitProviderSpi spi = null;
		try {
			// try loading directly from ServiceLoader
			Iterator<CurrencyUnitProviderSpi> instances = ServiceLoader.load(
					CurrencyUnitProviderSpi.class).iterator();
			if (instances.hasNext()) {
				spi = instances.next();
				return spi;
			}
		} catch (Exception e) {
			LOGGER.log(Level.INFO,
					"No CurrencyUnitProvider found, using  default.", e);
		}
		return new DefaultCurrencyUnitProviderSpi();
	}

	private static CurrencyUnitMapperSpi loadCurrencyUnitMapperSpi() {
		CurrencyUnitMapperSpi spi = null;
		try {
			// try loading directly from ServiceLoader
			Iterator<CurrencyUnitMapperSpi> instances = ServiceLoader.load(
					CurrencyUnitMapperSpi.class).iterator();
			if (instances.hasNext()) {
				spi = instances.next();
				return spi;
			}
		} catch (Exception e) {
			LOGGER.log(Level.INFO,
					"No CurrencyUnitMapperSpi found, using  default.", e);
		}
		return new DefaultCurrencyUnitMapperSpi();
	}

	private static final class DefaultCurrencyUnitProviderSpi implements
			CurrencyUnitProviderSpi {

		private static final String ISO_NAMESPACE = "ISO-4217";

		@Override
		public String getDefaultNamespace() {
			return ISO_NAMESPACE;
		}

		@Override
		public boolean isNamespaceAvailable(String namespace, Long timestamp) {
			return false;
		}

		@Override
		public Collection<String> getNamespaces(Long timestamp) {
			return Collections.emptySet();
		}

		@Override
		public CurrencyUnit get(String namespace, String code, Long timestamp) {
			throw new UnknownCurrencyException(namespace, code,
					"No CurrencyUnitProviderSpi registered using ServiceLoader.");
		}

		@Override
		public boolean isAvailable(String namespace, String code, Long timestamp) {
			return false;
		}

		@Override
		public Collection<CurrencyUnit> getAll(String namespace, Long timestamp) {
			return Collections.emptySet();
		}

		@Override
		public Collection<CurrencyUnit> getAll(Locale locale, Long timestamp) {
			return Collections.emptySet();
		}
	}

	private static final class DefaultCurrencyUnitMapperSpi implements
			CurrencyUnitMapperSpi {

		@Override
		public CurrencyUnit map(String targetNamespace, Long timestamp,
				CurrencyUnit currencyUnit) {
			return null;
		}

	}

	/**
	 * This class models the component defined by JSR 354 that provides
	 * accessors for {@link CurrencyUnit}. It is provided by the Monetary
	 * singleton.
	 * 
	 * @author Anatole Tresch
	 */
	public interface CurrencyUnitProviderSpi {

		/**
		 * Access the default namespace that this
		 * {@link CurrencyUnitProviderSpi} instance is using. The default
		 * namespace can be changed by adding the follwing entry into
		 * {@codeMETA-INF/java-money.properties} :
		 * {@code defaultCurrencyNamespace=myNamespace}. When not set explicitly
		 * {@code ISO-4217} is assumed.
		 * 
		 * @return the default namespace used.
		 */
		public String getDefaultNamespace();

		/**
		 * This method allows to check if a given name space is available.
		 * 
		 * @param namespace
		 *            the namespace id.
		 * @param timestamp
		 *            the target timestamp, when the namespace should be valid,
		 *            or {@code null} for actual data as of now.
		 * @return true, if the namepsace is available.
		 */
		public boolean isNamespaceAvailable(String namespace, Long timestamp);

		/**
		 * Access all defined namespaces.
		 * 
		 * @param timestamp
		 *            the target timestamp, when the namespaces should be valid,
		 *            or {@code null} for actual data as of now.
		 * @return a collection, of the namespaces available, never null.
		 */
		public Collection<String> getNamespaces(Long timestamp);

		/**
		 * Access a currency using its name space and code.
		 * 
		 * @param namespace
		 *            The name space, e.g. 'ISO-4217'.
		 * @param code
		 *            The code that, together with the namespace identifies the
		 *            currency.
		 * @param timestamp
		 *            The target UTC timestamp, or {@code null} for the current
		 *            UTC timestamp.
		 * @return The currency found, never null.
		 * @throws UnknownCurrencyException
		 *             if the required currency is not defined.
		 */
		public CurrencyUnit get(String namespace, String code, Long timestamp);

		/**
		 * Checks if a currency is defined using its name space and code.
		 * 
		 * @param namespace
		 *            The name space, e.g. 'ISO-4217'.
		 * @param code
		 *            The code that, together with the namespace identifies the
		 *            currency.
		 * @param timestamp
		 *            The target UTC timestamp, or {@code null} for the current
		 *            UTC timestamp.
		 * @return true, if the currency is defined.
		 */
		public boolean isAvailable(String namespace, String code, Long timestamp);

		/**
		 * Access all currencies available for a given namespace, timestamp.
		 * 
		 * @param namespace
		 *            The target namespace, not null.
		 * @param timestamp
		 *            The target UTC timestamp, or {@code null} for current.
		 * @return the currencies found.
		 */
		public Collection<CurrencyUnit> getAll(String namespace, Long timestamp);

		/**
		 * Access all currencies matching a {@link Locale}, valid at the given
		 * timestamp.
		 * 
		 * @param locale
		 *            the target locale, not null.
		 * @param timestamp
		 *            The target UTC timestamp, or {@code null} for the current
		 *            UTC timestamp.
		 * @return the currencies found, never null.
		 */
		public Collection<CurrencyUnit> getAll(Locale locale, Long timestamp);

	}

	/**
	 * This class models the component defined by JSR 354 that provides
	 * accessors for {@link CurrencyUnit}. It is provided by the Monetary
	 * singleton.
	 * 
	 * @author Anatole Tresch
	 */
	public interface CurrencyUnitMapperSpi {

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
		public CurrencyUnit map(String targetNamespace, Long timestamp,
				CurrencyUnit currencyUnit);

	}
}
