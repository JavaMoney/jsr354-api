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
 * 
 * Contributors: Anatole Tresch - initial implementation Werner Keil -
 * extensions and adaptions.
 */
package net.java.javamoney.ri.ext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.money.CurrencyUnit;
import javax.money.UnknownCurrencyException;
import javax.money.ext.spi.CurrencyUnitProviderSpi;
import javax.money.ext.spi.MonetaryCurrenciesSingletonSpi;

public class SEMonetaryCurrenciesSingletonSpi implements
		MonetaryCurrenciesSingletonSpi {

	/**
	 * System property used to redefine the default namespace for
	 * {@link CurrencyUnit} instances.
	 */
	private static final String DEFAULT_NAMESPACE_PROP = "javax.money.defaultCurrencyNamespace";
	/**
	 * The default namespace used.
	 */
	private String defaultNamespace = "ISO-4217";
	private SECurrencyUnitProviderService currencyUnitProvider = new SECurrencyUnitProviderService();
	private SECurrencyUnitMapperService currencyUnitMapper = new SECurrencyUnitMapperService();

	public SEMonetaryCurrenciesSingletonSpi() {
		String ns = System.getProperty(DEFAULT_NAMESPACE_PROP);
		if (ns != null) {
			defaultNamespace = ns;
		}
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
	public String getDefaultNamespace() {
		return defaultNamespace;
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
		return currencyUnitProvider.isNamespaceAvailable(namespace);
	}

	/**
	 * This method allows to access all name spaces currently defined.
	 * "ISO-4217" should be defined in all environments (default).
	 * 
	 * @return the array of currently defined name space.
	 */
	public Collection<String> getNamespaces() {
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
	public boolean isAvailable(String code) {
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
	public boolean isAvailable(String namespace, String code) {
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
	public CurrencyUnit get(String namespace, String code) {
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
	public CurrencyUnit get(String code) {
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
	public CurrencyUnit map(String targetNamespace, CurrencyUnit currencyUnit) {
		return currencyUnitMapper.map(targetNamespace, null, currencyUnit);
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
	public List<CurrencyUnit> mapAll(String targetNamespace,
			CurrencyUnit... units) {
		List<CurrencyUnit> resultList = new ArrayList<CurrencyUnit>();
		for (CurrencyUnit currencyUnit : units) {
			CurrencyUnit result = currencyUnitMapper.map(targetNamespace, null,
					currencyUnit);
			if (result == null) {
				throw new IllegalArgumentException("Cannot map curreny "
						+ currencyUnit + " to namespace "
						+ targetNamespace);
			}
			resultList.add(result);
		}
		return resultList;
	}

	public List<CurrencyUnit> mapAll(String targetNamespace, long timestamp,
			CurrencyUnit... units) {
		List<CurrencyUnit> resultList = new ArrayList<CurrencyUnit>();
		for (CurrencyUnit currencyUnit : units) {
			CurrencyUnit result = currencyUnitMapper.map(targetNamespace,
					timestamp, currencyUnit);
			if (result == null) {
				throw new IllegalArgumentException("Cannot map curreny "
						+ currencyUnit + " to namespace "
						+ targetNamespace);
			}
			resultList.add(result);
		}
		return resultList;
	}

	@Override
	public Collection<CurrencyUnit> getAll(String namespace) {
		return currencyUnitProvider.getAll(namespace);
	}
}