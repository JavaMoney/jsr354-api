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
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.CurrencyUnit;
import javax.money.ext.CurrencyValidity;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public abstract class AbstractCurrencyUnitValidityService {
	/** Loaded currency providers. */
	private final Map<String, List<CurrencyValidity>> currencyValidities = new ConcurrentHashMap<String, List<CurrencyValidity>>();

	/**
	 * Constructor, also loading the registered spi's.
	 */
	public AbstractCurrencyUnitValidityService() {
		reload();
	}

	public Set<String> getCurrencyValidityProviders() {
		return currencyValidities.keySet();
	}

	public CurrencyValidity getCurrencyValidity(String type) {
		List<CurrencyValidity> list = currencyValidities.get(type);
		if (list.size() > 0) {
			return list.get(0); // TODO support multiple...
		}
		return null;
	}

	/**
	 * This method reloads the providers available from the
	 * {@link ServiceLoader}. This adds providers that were not yet visible
	 * before.
	 */
	public void reload() {
		for (CurrencyValidity currencyValidity : getCurrencyValidities()) {
			List<CurrencyValidity> provList = this.currencyValidities
					.get(currencyValidity.getValiditySource());
			if (provList == null) {
				provList = new ArrayList<CurrencyValidity>();
				this.currencyValidities.put(
						currencyValidity.getValiditySource(),
						provList);
			}
			provList.add(currencyValidity);
		}
	}

	protected abstract Iterable<CurrencyValidity> getCurrencyValidities();

}
