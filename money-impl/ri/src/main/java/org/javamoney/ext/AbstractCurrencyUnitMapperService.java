/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial implementation Werner Keil -
 * extensions and adaptions.
 */
package org.javamoney.ext;

import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

import javax.money.CurrencyUnit;

import org.javamoney.ext.spi.CurrencyUnitMapperSpi;
import org.javamoney.ext.spi.MonetaryCurrenciesSingletonSpi;

/**
 * This class models the an internal service class, that provides the base
 * method used by the {@link MonetaryCurrenciesSingletonSpi} implementation. It
 * is extended for different runtime scenarios, hereby allowing the spi
 * implementation loaded using different mechanisms.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public abstract class AbstractCurrencyUnitMapperService {
	/** Loaded currency mappers. */
	private Set<CurrencyUnitMapperSpi> mappers = new HashSet<CurrencyUnitMapperSpi>();

	/**
	 * COnstructor, also loading the registered spi's.
	 */
	public AbstractCurrencyUnitMapperService() {
		reload();
	}

	/**
	 * This method reloads the providers available from the
	 * {@link ServiceLoader}. This adds providers that were not yet visible
	 * before.
	 */
	@SuppressWarnings("unchecked")
	public void reload() {
		for (CurrencyUnitMapperSpi currencyMappingSPI : getCurrencyUnitMapperSpis()) {
			mappers.add(currencyMappingSPI);
		}
	}

	public CurrencyUnit map(String targetNamespace, CurrencyUnit unit) {
		for (CurrencyUnitMapperSpi prov : mappers) {
			CurrencyUnit mappedUnit = prov.map(unit, targetNamespace, null);
			if (mappedUnit != null) {
				return mappedUnit;
			}
		}
		return null;
	}


	public CurrencyUnit map(CurrencyUnit currencyUnit, String targetNamespace, Long timestamp) {
		if (timestamp == null) {
			return map(targetNamespace, currencyUnit);
		}
		for (CurrencyUnitMapperSpi prov : mappers) {
			CurrencyUnit mappedUnit = prov.map(currencyUnit, targetNamespace,
					timestamp);
			if (mappedUnit != null) {
				return mappedUnit;
			}
		}
		return null;
	}

	protected abstract Iterable<CurrencyUnitMapperSpi> getCurrencyUnitMapperSpis();

}
