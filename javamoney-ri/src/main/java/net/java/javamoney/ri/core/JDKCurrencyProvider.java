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
 *    Anatole Tresch - initial implementation.
 *    Werner Keil - extension and adjustment.
 */
package net.java.javamoney.ri.core;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.spi.CurrencyUnitProviderSpi;

/**
 * Basic implementation of a {@link CurrencyUnitProviderSpi} that provides the
 * ISO 4217 currencies available from the JDK {@link Currency} class.
 * 
 * @author Anatole Tresch
 */
public class JDKCurrencyProvider implements CurrencyUnitProviderSpi {

	public static final String ISO4217_NAMESPACE = "ISO4217";

	private final Map<String, JDKCurrencyAdapter> currencies = new HashMap<String, JDKCurrencyAdapter>();

	public JDKCurrencyProvider() {
		Set<Currency> jdkCurrencies = Currency.getAvailableCurrencies();
		for (Currency currency : jdkCurrencies) {
			this.currencies.put(currency.getCurrencyCode(),
					new JDKCurrencyAdapter(currency));
		}
	}

	public String getNamespace() {
		return ISO4217_NAMESPACE;
	}

	public CurrencyUnit getCurrency(String code, long timestamp) {
		if (timestamp == -1) {
			return this.currencies.get(code);
		}
		return null;
	}

	public CurrencyUnit[] getCurrencies(Locale locale, long timestamp) {
		if (timestamp == -1 &&  locale !=null && locale.getCountry().length()==2) {
			Currency currency = Currency.getInstance(locale);
			if (currency != null) {
				return new CurrencyUnit[] { this.currencies.get(currency
						.getCurrencyCode()) };
			}
		}
		return null;
	}

	public CurrencyUnit[] getCurrencies(long timestamp) {
		if (timestamp == -1) {
			return this.currencies.values().toArray(
					new CurrencyUnit[this.currencies.size()]);
		}
		return null;
	}

	public CurrencyUnit map(CurrencyUnit unit, String targetNamespace) {
		// not supported by the JDK.
		return null;
	}

	public boolean isAvailable(String code, long start, long end) {
		if (start == -1L && end == -1L) {
			return this.currencies.containsKey(code);
		}
		return false;
	}

}
