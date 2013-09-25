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
package org.javamoney.ext.spi;

import java.util.Collection;

import javax.money.CurrencyUnit;

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

	/*-- Access of current currencies --*/
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

	/**
	 * Checks if a {@link CurrencyUnit} is defined using its namespace and code.
	 * 
	 * @param code
	 *            The code that identifies the {@link CurrencyUnit}.
	 * @return true, if the currency is defined.
	 */
	public boolean isAvailable(String code);

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
	public CurrencyUnit map(CurrencyUnit currencyUnit,
			String targetNamespace);

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
	public CurrencyUnit map(CurrencyUnit currencyUnit,
			String targetNamespace,
			long timestamp);

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
	public String getNamespace(String code);

}
