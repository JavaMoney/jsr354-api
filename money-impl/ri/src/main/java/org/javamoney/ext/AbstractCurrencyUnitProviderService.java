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
package org.javamoney.ext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.CurrencyUnit;
import javax.money.UnknownCurrencyException;

import org.javamoney.ext.spi.CurrencyUnitProviderSpi;
import org.javamoney.ext.spi.MonetaryCurrenciesSingletonSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class models the an internal service class, that provides the base
 * method used by the {@link MonetaryCurrenciesSingletonSpi} implementation. It
 * is extended for different runtime scenarios, hereby allowing the spi
 * implementation loaded using different mechanisms.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public abstract class AbstractCurrencyUnitProviderService {
	/** Loaded currency providers. */
	private final Map<String, List<CurrencyUnitProviderSpi>> currencyProviders = new ConcurrentHashMap<String, List<CurrencyUnitProviderSpi>>();

	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Constructor, also loading the registered spi's.
	 */
	public AbstractCurrencyUnitProviderService() {
		try {
			reload();
		} catch (Exception e) {
			log.error(
					"Error loading providers.", e);
		}
	}

	/**
	 * This method reloads the providers available from the
	 * {@link ServiceLoader}. This adds providers that were not yet visible
	 * before.
	 */
	public void reload() {
		for (CurrencyUnitProviderSpi currencyProviderSPI : getCurrencyUnitProviderSpis()) {
			try {
				List<CurrencyUnitProviderSpi> provList = this.currencyProviders
						.get(currencyProviderSPI.getNamespace());
				if (provList == null) {
					provList = new ArrayList<CurrencyUnitProviderSpi>();
					this.currencyProviders.put(
							currencyProviderSPI.getNamespace(),
							provList);
				}
				provList.add(currencyProviderSPI);
			} catch (Exception e) {
				log.error("Error loading CurrencyUnitProviderSpi: "
						+ currencyProviderSPI, e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnitProvider#get(java.lang.String,
	 * java.lang.String, long)
	 */
	public CurrencyUnit get(String code) {
		for (List<CurrencyUnitProviderSpi> provList : currencyProviders
				.values()) {
			for (CurrencyUnitProviderSpi prov : provList) {
				CurrencyUnit currency = prov.get(code);
				if (currency != null) {
					return currency;
				}
			}
		}
		throw new UnknownCurrencyException(code);
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
		List<CurrencyUnitProviderSpi> provList = currencyProviders
				.get(namespace);
		if (provList == null) {
			return null;
		}
		for (CurrencyUnitProviderSpi prov : provList) {
			Collection<CurrencyUnit> currencies = prov.getAll();
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
		for (List<CurrencyUnitProviderSpi> provList : currencyProviders
				.values()) {
			for (CurrencyUnitProviderSpi prov : provList) {
				Collection<CurrencyUnit> currencies = prov.getAll();
				if (currencies != null) {
					result.addAll(currencies);
				}
			}
		}
		return result;
	}

	public boolean isAvailable(String namespace, String code) {
		List<CurrencyUnitProviderSpi> provList = currencyProviders
				.get(namespace);
		if (provList == null) {
			return false;
		}
		for (CurrencyUnitProviderSpi prov : provList) {
			if (prov.isAvailable(code)) {
				return true;
			}
		}
		return false;
	}

	public boolean isAvailable(String code) {
		for (List<CurrencyUnitProviderSpi> provList : currencyProviders
				.values()) {
			for (CurrencyUnitProviderSpi prov : provList) {
				if (prov.isAvailable(code)) {
					return true;
				}
			}
		}
		return false;
	}

	public String getNamespace(String code) {
		for (List<CurrencyUnitProviderSpi> provList : currencyProviders
				.values()) {
			for (CurrencyUnitProviderSpi prov : provList) {
				if (prov.isAvailable(code)) {
					return prov.getNamespace();
				}
			}
		}
		return null;
	}

	public boolean isNamespaceAvailable(String namespace) {
		return this.currencyProviders.containsKey(namespace);
	}

	public Collection<String> getNamespaces() {
		return this.currencyProviders.keySet();
	}

	protected abstract Iterable<CurrencyUnitProviderSpi> getCurrencyUnitProviderSpis();

}
