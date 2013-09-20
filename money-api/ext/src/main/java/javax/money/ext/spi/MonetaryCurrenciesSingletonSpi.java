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
package javax.money.ext.spi;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.money.CurrencyUnit;
import javax.money.UnknownCurrencyException;

/**
 * This interface must be implemented and registered using the
 * {@code ServiceLoader}. It implements the functionality provided by the
 * {@code MonetaryCurrencies} singleton and is responsible for loading and
 * managing of {@link CurrencyUnitProviderSpi} and {@link CurrencyUnitMapperSpi}
 * instances and delegating according calls to the appropriate providers.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryCurrenciesSingletonSpi {

	/**
	 * Access the default namespace that this
	 * {@link MonetaryCurrenciesSingletonSpi} instance is using. The default
	 * namespace can be changed by adding a file
	 * {@code META-INF/java-money.properties} with the following entry
	 * {@code defaultCurrencyNamespace=myNamespace}. When not set explicitly
	 * {@code "ISO-4217"} is assumed.
	 * 
	 * @return the default namespace used.
	 */
	public String getDefaultNamespace();

	/**
	 * This method allows to evaluate, if the given currency namespace is
	 * defined. {@code "ISO-4217"} should be defined in all environments
	 * (default).
	 * 
	 * @param namespace
	 *            the required namespace
	 * @return {@code true}, if the namespace exists.
	 */
	public boolean isNamespaceAvailable(String namespace);

	/**
	 * This method allows to access all namespaces currently defined.
	 * {@code "ISO-4217"} should be defined in all environments (default).
	 * 
	 * @return the array of currently defined namespace.
	 */
	public Collection<String> getNamespaces();

	/*-- Access of current currencies --*/
	/**
	 * Checks if a {@link CurrencyUnit} is defined using its namespace and code.
	 * This is a convenience method for {@link #getCurrency(String, String)},
	 * where as the default namespace is assumed.
	 * 
	 * @see #getDefaultNamespace()
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @return {@code true}, if the currency is defined.
	 */
	public boolean isAvailable(String code);

	/**
	 * Checks if a {@link CurrencyUnit} is defined using its namespace and code.
	 * 
	 * @param namespace
	 *            The namespace, e.g. {@code "ISO-4217"}.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            {@link CurrencyUnit}.
	 * @return true, if the currency is defined.
	 */
	public boolean isAvailable(String namespace, String code);

	/**
	 * Access a currency using its namespace and code. This is a convenience
	 * method for {@link #getCurrency(String, String, Date)}, where {@code null}
	 * is passed for the target date (meaning current date).
	 * 
	 * @param namespace
	 *            The namespace, e.g. {@code "ISO-4217"}.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @return The {@link CurrencyUnit} found, never {@code null}.
	 * @throws UnknownCurrencyException
	 *             if the required currency is not defined.
	 */
	public CurrencyUnit get(String namespace, String code);

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
	public CurrencyUnit get(String code);

	/**
	 * This method maps the given {@link CurrencyUnit} to another
	 * {@link CurrencyUnit} with the given target namespace.
	 * 
	 * @param currencyUnit
	 *            The source {@link CurrencyUnit}, never {@code null}.
	 * @param targetNamespace
	 *            the target namespace, never {@code null}.
	 * @return The mapped {@link CurrencyUnit}, or {@code null}.
	 */
	public CurrencyUnit map(CurrencyUnit currencyUnit, String targetNamespace);

	/**
	 * This method maps the given {@link CurrencyUnit} to another
	 * {@link CurrencyUnit} with the given target namespace.
	 * 
	 * @param currencyUnit
	 *            The source unit, never {@code null}.
	 * @param targetNamespace
	 *            the target namespace, never {@code null}.
	 * @param timestamp
	 *            The target UTC timestamp.
	 * @return The mapped {@link CurrencyUnit}, or {@code null}.
	 */
	public CurrencyUnit map(CurrencyUnit currencyUnit, String targetNamespace,
			long timestamp);

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
	public List<CurrencyUnit> mapAll(String targetNamespace,
			CurrencyUnit... units);

	/**
	 * This method maps the given {@link CurrencyUnit} instances to another
	 * {@link CurrencyUnit} instances with the given target namespace.
	 * 
	 * @param targetNamespace
	 *            the target namespace, never {@code null}.
	 * @param timestamp
	 *            The target UTC timestamp.
	 * @param units
	 *            The source units, never {@code null}.
	 * 
	 * @return The mapped {@link CurrencyUnit} instances (same array length). If
	 *         a unit could not be mapped, the according array element will be
	 *         {@code null}.
	 */
	public List<CurrencyUnit> mapAll(String targetNamespace, long timestamp,
			CurrencyUnit... units);

	/**
	 * Access all currencies for a given namespace.
	 * 
	 * @see #getNamespaces()
	 * @see #getDefaultNamespace()
	 * @param namespace
	 *            The target namespace, not {@code null}.
	 * @return The currencies found, never {@code null}.
	 * @throws UnknownCurrencyException
	 *             if the required namespace is not defined.
	 */
	public Collection<CurrencyUnit> getAll(String namespace);

}
