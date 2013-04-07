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
package net.java.javamoney.ri.core.provider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.provider.CurrencyUnitMapper;
import javax.money.provider.Monetary;

import net.java.javamoney.ri.common.AbstractRiComponent;
import net.java.javamoney.ri.core.spi.CurrencyUnitMappingSpi;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
@Singleton
public class CurrencyUnitMapperImpl extends AbstractRiComponent implements
		CurrencyUnitMapper {
	/** Loaded currency mappers. */
	private Set<CurrencyUnitMappingSpi> mappers = new HashSet<CurrencyUnitMappingSpi>();

	/**
	 * COnstructor, also loading the registered spi's.
	 */
	public CurrencyUnitMapperImpl() {
		reload();
	}

	/**
	 * This method reloads the providers available from the
	 * {@link ServiceLoader}. This adds providers that were not yet visible
	 * before.
	 */
	@SuppressWarnings("unchecked")
	public void reload() {
		List<CurrencyUnitMappingSpi> loadedMapperList = Monetary.getLoader()
				.getComponents(CurrencyUnitMappingSpi.class);
		for (CurrencyUnitMappingSpi currencyMappingSPI : loadedMapperList) {
			mappers.add(currencyMappingSPI);
		}
	}

	public CurrencyUnit map(String targetNamespace, CurrencyUnit unit) {
		for (CurrencyUnitMappingSpi prov : mappers) {
			CurrencyUnit mappedUnit = prov.map(unit, targetNamespace, null);
			if (mappedUnit != null) {
				return mappedUnit;
			}
		}
		return null;
	}

	public List<CurrencyUnit> mapAll(String targetNamespace,
			CurrencyUnit... units) {
		List<CurrencyUnit> result = new ArrayList<CurrencyUnit>();
		for (CurrencyUnit unit : units) {
			result.add(map(targetNamespace, unit));
		}
		return result;
	}

	@Override
	public CurrencyUnit map(String targetNamespace, Long timestamp,
			CurrencyUnit currencyUnit) {
		if (timestamp == null) {
			return map(targetNamespace, currencyUnit);
		}
		for (CurrencyUnitMappingSpi prov : mappers) {
			CurrencyUnit mappedUnit = prov.map(currencyUnit, targetNamespace, timestamp);
			if (mappedUnit != null) {
				return mappedUnit;
			}
		}
		return null;
	}

	@Override
	public List<CurrencyUnit> mapAll(String targetNamespace, Long timestamp,
			CurrencyUnit... units) {
		List<CurrencyUnit> result = new ArrayList<CurrencyUnit>();
		for (CurrencyUnit unit : units) {
			result.add(map(targetNamespace, timestamp, unit));
		}
		return result;
	}

}
