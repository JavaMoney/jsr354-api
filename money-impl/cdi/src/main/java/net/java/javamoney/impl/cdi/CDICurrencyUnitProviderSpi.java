/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial implementation
 *    Werner Keil - extensions and adaptions.
 */
package net.java.javamoney.impl.cdi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.inject.Instance;
import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;
import javax.money.UnknownCurrencyException;
import javax.money.ext.MonetaryCurrencies.CurrencyUnitProviderSpi;
import javax.money.ext.RegionType;

import net.java.javamoney.ri.ext.spi.CurrencyUnitMappingSpi;
import net.java.javamoney.ri.ext.spi.CurrencyUnitProviderComponentSpi;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class CDICurrencyUnitProviderSpi implements CurrencyUnitProviderSpi {
	/**
	 * System property used to redefine the default namespace for
	 * {@link CurrencyUnit} instances.
	 */
	private static final String DEFAULT_NAMESPACE_PROP = "javax.money.defaultCurrencyNamespace";
	/** Loaded currency providers. */
	private Map<String, List<CurrencyUnitProviderComponentSpi>> currencyProviders = new ConcurrentHashMap<String, List<CurrencyUnitProviderComponentSpi>>();
	/** Loaded currency mappers. */
	private Instance<CurrencyUnitMappingSpi> mappers;
	/** The default namespace used. */
	private String defaultNamespace = MoneyCurrency.ISO_NAMESPACE;

	/**
	 * COnstructor, also loading the registered spi's.
	 */
	public CDICurrencyUnitProviderSpi() {
		String ns = System.getProperty(DEFAULT_NAMESPACE_PROP);
		if (ns != null) {
			this.defaultNamespace = ns;
		}
		for (CurrencyUnitProviderComponentSpi currencyProviderSPI : CDIContainer
				.getInstances(CurrencyUnitProviderComponentSpi.class)) {
			List<CurrencyUnitProviderComponentSpi> provList = this.currencyProviders
					.get(currencyProviderSPI.getNamespace());
			if (provList == null) {
				provList = new ArrayList<CurrencyUnitProviderComponentSpi>();
				this.currencyProviders.put(currencyProviderSPI.getNamespace(),
						provList);
			}
			provList.add(currencyProviderSPI);
		}
		mappers = CDIContainer.getInstances(CurrencyUnitMappingSpi.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnitProvider#get(java.lang.String,
	 * java.lang.String, long)
	 */
	public CurrencyUnit get(String namespace, String code) {
		List<CurrencyUnitProviderComponentSpi> provList = currencyProviders
				.get(namespace);
		if (provList == null) {
			return null;
		}
		for (CurrencyUnitProviderComponentSpi prov : provList) {
			CurrencyUnit currency = prov.getCurrency(code);
			if (currency != null) {
				return currency;
			}
		}
		throw new UnknownCurrencyException(namespace, code);
	}

	/**
	 * Access all regions for a given {@link RegionType}.
	 * 
	 * @param type
	 *            The region type, not null.
	 * @return the regions found, never null.
	 */
	public Collection<CurrencyUnit> getAll(String namespace) {
		Set<CurrencyUnit> result = new HashSet<CurrencyUnit>();
		List<CurrencyUnitProviderComponentSpi> provList = currencyProviders
				.get(namespace);
		if (provList == null) {
			return null;
		}
		for (CurrencyUnitProviderComponentSpi prov : provList) {
			Collection<CurrencyUnit> currencies = prov.getCurrencies();
			if (currencies != null) {
				result.addAll(currencies);
			}
		}
		return result;
	}

	/**
	 * Access all regions.
	 * 
	 * @return the regions found, never null.
	 */
	public Collection<CurrencyUnit> getAll() {
		Set<CurrencyUnit> result = new HashSet<CurrencyUnit>();
		for (List<CurrencyUnitProviderComponentSpi> provList : currencyProviders
				.values()) {
			for (CurrencyUnitProviderComponentSpi prov : provList) {
			    	Collection<CurrencyUnit> currencies = prov.getCurrencies();
				if (currencies != null) {
					result.addAll(currencies);
				}
			}
		}
		return result;
	}

	public boolean isAvailable(String namespace, String code) {
		List<CurrencyUnitProviderComponentSpi> provList = currencyProviders
				.get(namespace);
		if (provList == null) {
			return false;
		}
		for (CurrencyUnitProviderComponentSpi prov : provList) {
			if (prov.isAvailable(code)) {
				return true;
			}
		}
		return false;
	}

	public boolean isNamespaceAvailable(String namespace) {
		return this.currencyProviders.containsKey(namespace);
	}

	public Collection<String> getNamespaces() {
		return this.currencyProviders.keySet();
	}

	public Set<CurrencyUnit> getAll(Locale locale) {
		Set<CurrencyUnit> result = new HashSet<CurrencyUnit>();
		for (List<CurrencyUnitProviderComponentSpi> provList : currencyProviders
				.values()) {
			for (CurrencyUnitProviderComponentSpi prov : provList) {
			    	Collection<CurrencyUnit> currencies = prov.getCurrencies(locale);
				if (currencies != null) {
					result.addAll(currencies);
				}
			}
		}
		return result;
	}

	@Override
	public String getDefaultNamespace() {
		return this.defaultNamespace;
	}

}
