/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.javamoney.cdi.ext;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.UnknownCurrencyException;

import org.javamoney.ext.spi.MonetaryCurrenciesSingletonSpi;

public class CDIMonetaryCurrenciesSingletonSpi implements
		MonetaryCurrenciesSingletonSpi {

	/**
	 * System property used to redefine the default namespace for
	 * {@link CurrencyUnit} instances.
	 */
	private static final String DEFAULT_NAMESPACE_PROP = "javax.money.defaultCurrencyNamespace";
	/**
	 * The default namespace used.
	 */
	private CDICurrencyUnitProviderService currencyUnitProvider = new CDICurrencyUnitProviderService();
	private CDICurrencyUnitMapperService currencyUnitMapper = new CDICurrencyUnitMapperService();

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
		return currencyUnitProvider.isAvailable(code);
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
	public CurrencyUnit get(String code) {
		return currencyUnitProvider.get(code);
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
	public CurrencyUnit map(CurrencyUnit currencyUnit, String targetNamespace) {
		return currencyUnitMapper.map(currencyUnit, targetNamespace, null);
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
	public CurrencyUnit map(CurrencyUnit currencyUnit, String targetNamespace,
			long timestamp) {
		return currencyUnitMapper.map(currencyUnit, targetNamespace, timestamp);
	}

	public Set<String> getCurrencyValidityProviders() {
		return Collections.emptySet();
	}

	@Override
	public Collection<CurrencyUnit> getAll(String namespace) {
		return currencyUnitProvider.getAll(namespace);
	}

	@Override
	public String getNamespace(String code) {
		return currencyUnitProvider.getNamespace(code);
	}
}