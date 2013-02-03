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
 *    Wernner Keil - extensions and adaptions.
 */
package net.java.javamoney.ri.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.CurrencyUnitProvider;
import javax.money.UnknownCurrencyException;
import javax.money.ext.RegionType;
import javax.money.spi.CurrencyUnitMappingSpi;
import javax.money.spi.CurrencyUnitProviderSpi;

import net.java.javamoney.ri.AbstractSpiComponent;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit}.
 * 
 * @author Anatole Tresch
 */
public final class CurrencyUnitProviderImpl extends AbstractSpiComponent
		implements CurrencyUnitProvider {
	/** Singleton instance. */
	private static final CurrencyUnitProviderImpl INSTANCE = new CurrencyUnitProviderImpl();
	/** Loaded currency providers. */
	private Map<String, List<CurrencyUnitProviderSpi>> currencyProviders = new HashMap<String, List<CurrencyUnitProviderSpi>>();
	/** Loaded currency mappers. */
	private Set<CurrencyUnitMappingSpi> mappers = new HashSet<CurrencyUnitMappingSpi>();

	/**
	 * This method reloads the providers available from the
	 * {@link ServiceLoader}. This adds providers that were not yet visible
	 * before.
	 */
	public void reload() {
		List<CurrencyUnitProviderSpi> loadedList = getSPIProviders(CurrencyUnitProviderSpi.class);
		for (CurrencyUnitProviderSpi currencyProviderSPI : loadedList) {
			List<CurrencyUnitProviderSpi> provList = this.currencyProviders
					.get(currencyProviderSPI.getNamespace());
			if (provList == null) {
				provList = new ArrayList<CurrencyUnitProviderSpi>();
				this.currencyProviders.put(currencyProviderSPI.getNamespace(),
						provList);
			}
			provList.add(currencyProviderSPI);
		}
		List<CurrencyUnitMappingSpi> loadedMapperList = getSPIProviders(CurrencyUnitMappingSpi.class);
		for (CurrencyUnitMappingSpi currencyMappingSPI : loadedMapperList) {
			mappers.add(currencyMappingSPI);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnitProvider#get(java.lang.String,
	 * java.lang.String, long)
	 */
	public CurrencyUnit get(String namespace, String code, long timestamp) {
		List<CurrencyUnitProviderSpi> provList = INSTANCE.currencyProviders
				.get(namespace);
		if (provList == null) {
			return null;
		}
		for (CurrencyUnitProviderSpi prov : provList) {
			CurrencyUnit currency = prov.getCurrency(code, timestamp);
			if (currency != null) {
				return currency;
			}
		}
		throw new UnknownCurrencyException("So such currency " + namespace
				+ ':' + code);
	}

	/**
	 * Access all regions for a given {@link RegionType}.
	 * 
	 * @param type
	 *            The region type, not null.
	 * @return the regions found, never null.
	 */
	public CurrencyUnit[] getAll(String namespace) {
		return getAll(namespace, -1);
	}

	public CurrencyUnit[] getAll(String namespace, long timestamp) {
		Set<CurrencyUnit> result = new HashSet<CurrencyUnit>();
		List<CurrencyUnitProviderSpi> provList = INSTANCE.currencyProviders
				.get(namespace);
		if (provList == null) {
			return null;
		}
		for (CurrencyUnitProviderSpi prov : provList) {
			CurrencyUnit[] currencies = prov.getCurrencies(-1);
			if (currencies != null) {
				result.addAll(Arrays.asList(currencies));
			}
		}
		return result.toArray(new CurrencyUnit[result.size()]);
	}

	/**
	 * Access all regions.
	 * 
	 * @return the regions found, never null.
	 */
	public CurrencyUnit[] getAll() {
		return getAll(-1);
	}

	public CurrencyUnit[] getAll(long timestamp) {
		Set<CurrencyUnit> result = new HashSet<CurrencyUnit>();
		for (List<CurrencyUnitProviderSpi> provList : INSTANCE.currencyProviders
				.values()) {
			for (CurrencyUnitProviderSpi prov : provList) {
				CurrencyUnit[] currencies = prov.getCurrencies(timestamp);
				if (currencies != null) {
					result.addAll(Arrays.asList(currencies));
				}
			}
		}
		return result.toArray(new CurrencyUnit[result.size()]);
	}

	public CurrencyUnit get(String namespace, String code) {
		return get(namespace, code, -1L);
	}

	public boolean isAvailable(String namespace, String code) {
		return isAvailable(namespace, code, -1L, -1L);
	}

	public boolean isAvailable(String namespace, String code, long timestamp) {
		return isAvailable(namespace, code, timestamp, -1L);
	}

	public boolean isAvailable(String namespace, String code, long start,
			long end) {
		List<CurrencyUnitProviderSpi> provList = INSTANCE.currencyProviders
				.get(namespace);
		if (provList == null) {
			return false;
		}
		for (CurrencyUnitProviderSpi prov : provList) {
			if (prov.isAvailable(code, start, end)) {
				return true;
			}
		}
		return false;
	}

	public boolean isNamespaceAvailable(String namespace) {
		return this.currencyProviders.containsKey(namespace);
	}

	public String[] getNamespaces() {
		return this.currencyProviders.keySet().toArray(
				new String[this.currencyProviders.size()]);
	}

	public CurrencyUnit[] getAll(Locale locale) {
		return getAll(locale, -1);
	}

	public CurrencyUnit[] getAll(Locale locale, long timestamp) {
		Set<CurrencyUnit> result = new HashSet<CurrencyUnit>();
		for (List<CurrencyUnitProviderSpi> provList : INSTANCE.currencyProviders
				.values()) {
			for (CurrencyUnitProviderSpi prov : provList) {
				CurrencyUnit[] currencies = prov.getCurrencies(locale,
						timestamp);
				if (currencies != null) {
					result.addAll(Arrays.asList(currencies));
				}
			}
		}
		return result.toArray(new CurrencyUnit[result.size()]);
	}

	public CurrencyUnit map(CurrencyUnit unit, String targetNamespace) {
		List<CurrencyUnitProviderSpi> provList = INSTANCE.currencyProviders
				.get(unit.getNamespace());
		if (provList == null) {
			return null;
		}
		for (CurrencyUnitMappingSpi prov : mappers) {
			CurrencyUnit mappedUnit = prov.map(unit, targetNamespace);
			if (mappedUnit != null) {
				return mappedUnit;
			}
		}
		return null;
	}

	public CurrencyUnit[] mapAll(CurrencyUnit[] units, String targetNamespace) {
		CurrencyUnit[] result = new CurrencyUnit[units.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = map(units[i], targetNamespace);
		}
		return result;
	}

}
