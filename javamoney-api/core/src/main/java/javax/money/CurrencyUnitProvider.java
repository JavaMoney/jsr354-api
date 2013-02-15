/*
 * Copyright (c) 2012-2013, Credit Suisse
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.money;

import java.util.Date;
import java.util.Locale;

/**
 * This class models the component defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit}. It is provided by the Monetary singleton.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyUnitProvider {

	/**
	 * Access the default namespace that this {@link CurrencyUnitProvider}
	 * instance is using. The default namespace can be changed by setting the
	 * {@code -Djavax.money.defaultCurrencyNamespace} system property. When not
	 * set explicitly {@code ISO-4217} is assummed.
	 * 
	 * @return the default namespace used.
	 */
	public String getDefaultNamespace();

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
	 * @return The currency found, never null.
	 * @throws UnknownCurrencyException
	 *             if the required currency is not defined.
	 */
	public CurrencyUnit get(String code);

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
	 * Access all currencies currently available.
	 * 
	 * @return the list of currencies available, never null.
	 */
	public CurrencyUnit[] getAll();

	/**
	 * Access all currencies available for the given UTC timestamp.
	 * 
	 * @param timestamp
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
	 * @return the list of currencies available, never null.
	 */
	public CurrencyUnit[] getAll(Long timstamp);

	/**
	 * Access all currencies available for a given namespace, timestamp.
	 * 
	 * @param namespace
	 *            The target namespace, not null.
	 * @param timestamp
	 *            The target UTC timestamp, or {@code null} for current.
	 * @return the currencies found.
	 */
	public CurrencyUnit[] getAll(String namespace, Long timestamp);

	/**
	 * Access all current currencies for a given namespace.
	 * 
	 * @param namespace
	 *            The target namespace, not null.
	 * @return the currencies found.
	 */
	public CurrencyUnit[] getAll(String namespace);

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
	 * Checks if a currency is defined using its name space and code. This is a convenience method for
	 * {@link #getCurrency(String, String)}, where as namespace the default
	 * namespace is assumed.
	 * 
	 * @see #getDefaultNamespace()
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @return true, if the currency is defined.
	 */
	public boolean isAvailable(String code);

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
	 * Checks if a currency is defined using its name space and code for the
	 * given time period. This is a convenience method for
	 * {@link #getCurrency(String, String)}, where as namespace the default
	 * namespace is assumed.
	 * 
	 * @see #getDefaultNamespace()
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
	public boolean isAvailable(String code, Long start, Long end);

	/**
	 * This method allows to evaluate, if the given currency name space is
	 * defined. "ISO-4217" should be defined in all environments (default).
	 * 
	 * @param namespace
	 *            the required name space
	 * @return true, if the name space exists.
	 */
	public boolean isNamespaceAvailable(String namespace);

	/**
	 * This method allows to access all name spaces currently defined.
	 * "ISO-4217" should be defined in all environments (default).
	 * 
	 * @return the array of currently defined name space.
	 */
	public String[] getNamespaces();

	/**
	 * Access all currencies matching a {@link Locale}.
	 * 
	 * @param locale
	 *            the target locale, not null.
	 * @return the currencies found, never null.
	 */
	public CurrencyUnit[] getAll(Locale locale);

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
	public CurrencyUnit[] getAll(Locale locale, Long timestamp);

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
	public CurrencyUnit map(CurrencyUnit unit, String targetNamespace);

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
	public CurrencyUnit[] mapAll(CurrencyUnit[] units, String targetNamespace);

}
