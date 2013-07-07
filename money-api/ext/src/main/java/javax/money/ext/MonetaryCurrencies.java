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

import java.util.ArrayList;
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

/**
 * This is the service component for accessing Java Money Currencies, evaluating
 * currency namespaces, access gistoric currencies and map between currencies.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.9
 */
public final class MonetaryCurrencies {

    private static final Logger LOGGER = Logger.getLogger(MonetaryCurrencies.class.getName());

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
	return currencyUnitProvider.isNamespaceAvailable(namespace);
    }

    /**
     * This method allows to access all name spaces currently defined.
     * "ISO-4217" should be defined in all environments (default).
     * 
     * @return the array of currently defined name space.
     */
    public static Collection<String> getNamespaces() {
	return currencyUnitProvider.getNamespaces();
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
	return currencyUnitProvider.isAvailable(getDefaultNamespace(), code);
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
	return currencyUnitProvider.isAvailable(namespace, code);
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
	return currencyUnitProvider.get(namespace, code);
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
     * This method maps the given {@link CurrencyUnit} to another
     * {@link CurrencyUnit} with the given target namespace.
     * 
     * @param unit
     *            The source unit, never {@code null}.
     * @param targetNamespace
     *            the target namespace, never {@code null}.
     * @return The mapped {@link CurrencyUnit}, or null.
     */
    public static CurrencyUnit map(String targetNamespace, CurrencyUnit currencyUnit) {
	return currencyUnitMapper.map(targetNamespace, currencyUnit, null);
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
    public static CurrencyUnit map(String targetNamespace, long timestamp, CurrencyUnit currencyUnit) {
	return currencyUnitMapper.map(targetNamespace, currencyUnit, timestamp);
    }

    public static Set<String> getValidityProviders() {
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
    public static CurrencyValidity getCurrencyValidity(String provider) {
	throw new UnsupportedOperationException();
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
    public static List<CurrencyUnit> mapAll(String targetNamespace, CurrencyUnit... units) {
	List<CurrencyUnit> resultList = new ArrayList<CurrencyUnit>();
	for (CurrencyUnit currencyUnit : units) {
	    CurrencyUnit result = currencyUnitMapper.map(targetNamespace, currencyUnit, null);
	    if (result == null) {
		throw new IllegalArgumentException("Cannot map curreny " + currencyUnit + " to namespace "
			+ targetNamespace);
	    }
	    resultList.add(result);
	}
	return resultList;
    }

    public static List<CurrencyUnit> mapAll(String targetNamespace, long timestamp, CurrencyUnit... units) {
	List<CurrencyUnit> resultList = new ArrayList<CurrencyUnit>();
	for (CurrencyUnit currencyUnit : units) {
	    CurrencyUnit result = currencyUnitMapper.map(targetNamespace, currencyUnit, timestamp);
	    if (result == null) {
		throw new IllegalArgumentException("Cannot map curreny " + currencyUnit + " to namespace "
			+ targetNamespace);
	    }
	    resultList.add(result);
	}
	return resultList;
    }

    private static CurrencyUnitProviderSpi loadCurrencyUnitProviderSpi() {
	CurrencyUnitProviderSpi spi = null;
	try {
	    // try loading directly from ServiceLoader
	    Iterator<CurrencyUnitProviderSpi> instances = ServiceLoader.load(CurrencyUnitProviderSpi.class).iterator();
	    if (instances.hasNext()) {
		spi = instances.next();
		return spi;
	    }
	} catch (Exception e) {
	    LOGGER.log(Level.INFO, "No CurrencyUnitProvider found, using  default.", e);
	}
	return new DefaultCurrencyUnitProviderSpi();
    }

    private static CurrencyUnitMapperSpi loadCurrencyUnitMapperSpi() {
	CurrencyUnitMapperSpi spi = null;
	try {
	    // try loading directly from ServiceLoader
	    Iterator<CurrencyUnitMapperSpi> instances = ServiceLoader.load(CurrencyUnitMapperSpi.class).iterator();
	    if (instances.hasNext()) {
		spi = instances.next();
		return spi;
	    }
	} catch (Exception e) {
	    LOGGER.log(Level.INFO, "No CurrencyUnitMapperSpi found, using  default.", e);
	}
	return new DefaultCurrencyUnitMapperSpi();
    }

    private static final class DefaultCurrencyUnitProviderSpi implements CurrencyUnitProviderSpi {

	private static final String ISO_NAMESPACE = "ISO-4217";

	@Override
	public String getDefaultNamespace() {
	    return ISO_NAMESPACE;
	}

	@Override
	public boolean isNamespaceAvailable(String namespace) {
	    return false;
	}

	@Override
	public Collection<String> getNamespaces() {
	    return Collections.emptySet();
	}

	@Override
	public Collection<CurrencyUnit> getAll(String namespace) {
	    return Collections.emptySet();
	}

	@Override
	public CurrencyUnit get(String namespace, String code) {
	    throw new UnknownCurrencyException(namespace, code,
		    "No CurrencyUnitProviderSpi registered using ServiceLoader.");
	}

	@Override
	public boolean isAvailable(String namespace, String code) {
	    return false;
	}

    }

    private static final class DefaultCurrencyUnitMapperSpi implements CurrencyUnitMapperSpi {

	@Override
	public CurrencyUnit map(String targetNamespace, CurrencyUnit currencyUnit, Long timestamp) {
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
	 * Access all regions for a given namespace.
	 * 
	 * @param namespace
	 *            the namespace, not null.
	 * @return the regions that belong to the given namespace.
	 */
	public Collection<CurrencyUnit> getAll(String namespace);

	/**
	 * Access a {@link CurrencyUnit} by namespace and code.
	 * 
	 * @param namespace
	 *            the namespace, not null.
	 * @param code
	 *            the code, not null.
	 * @return the {@link CurrencyUnit} found.
	 */
	public CurrencyUnit get(String namespace, String code);

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
	public boolean isNamespaceAvailable(String namespace);

	/**
	 * Access all defined namespaces.
	 * 
	 * @param timestamp
	 *            the target timestamp, when the namespaces should be valid,
	 *            or {@code null} for actual data as of now.
	 * @return a collection, of the namespaces available, never null.
	 */
	public Collection<String> getNamespaces();

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
	public boolean isAvailable(String namespace, String code);

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
	 * @param timestamp
	 *            the target timestamp, may be null.
	 * @return The mapped {@link CurrencyUnit}, or null.
	 */
	public CurrencyUnit map(String targetNamespace, CurrencyUnit currencyUnit, Long timestamp);

    }
}
