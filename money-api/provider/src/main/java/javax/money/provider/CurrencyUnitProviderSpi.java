/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.provider;

import java.util.Collection;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.UnknownCurrencyException;

/**
 * This class models the component defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit}. It is provided by the Monetary singleton.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyUnitProviderSpi {

	/**
	 * Access the default namespace that this {@link CurrencyUnitProviderSpi}
	 * instance is using. The default namespace can be changed by adding the
	 * follwing entry into {@codeMETA-INF/java-money.properties} :
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
	 *            the target timestamp, when the namespace should be valid, or
	 *            {@code null} for actual data as of now.
	 * @return true, if the namepsace is available.
	 */
	public boolean isNamespaceAvailable(String namespace, Long timestamp);

	/**
	 * Access all defined namespaces.
	 * 
	 * @param timestamp
	 *            the target timestamp, when the namespaces should be valid, or
	 *            {@code null} for actual data as of now.
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
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
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
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
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
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
	 * @return the currencies found, never null.
	 */
	public Collection<CurrencyUnit> getAll(Locale locale, Long timestamp);

}
