/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.provider;

import java.util.Enumeration;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.UnknownCurrencyException;

/**
 * This class models the component defined by JSR 354 that provides access
 * for hostoric {@link CurrencyUnit} instances. It is provided by the Monetary singleton.
 * 
 * @author Anatole Tresch
 */
public interface HistoricCurrencyUnitProvider {

	/**
	 * Access the default namespace that this {@link HistoricCurrencyUnitProvider}
	 * instance is using. The default namespace can be changed by setting the
	 * {@code -Djavax.money.defaultCurrencyNamespace} system property. When not
	 * set explicitly {@code ISO-4217} is assummed.
	 * 
	 * @return the default namespace used.
	 */
	public String getDefaultNamespace();
	
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
	public CurrencyUnit get(String code, Long timestamp);


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
	 * Checks if a currency is defined using its name space and code for the
	 * given time period.
	 * 
	 * @param namespace
	 *            The name space, e.g. 'ISO-4217'.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @param start
	 *            The starting UTC timestamp, or {@code null} for the current
	 *            UTC timestamp.
	 * @param end
	 *            The ending UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
	 * @return true, if the currency is defined.
	 */
	public boolean isAvailable(String namespace, String code, Long start,
			Long end);

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
	public boolean isAvailable(String code, Long timestamp);


	/**
	 * Access all currencies available for a given namespace, timestamp.
	 * 
	 * @param namespace
	 *            The target namespace, not null.
	 * @param timestamp
	 *            The target UTC timestamp, or {@code null} for current.
	 * @return the currencies found.
	 */
	public Enumeration<CurrencyUnit> getAll(String namespace, Long timestamp);
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
	public Enumeration<CurrencyUnit> getAll(Locale locale, Long timestamp);

}
