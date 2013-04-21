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
package net.java.javamoney.ri.ext.impl;

import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;
import javax.money.ext.MonetaryCurrencies.CurrencyUnitProviderSpi;

import net.java.javamoney.ri.ext.spi.CurrencyUnitProviderComponentSpi;

/**
 * Basic implementation of a {@link CurrencyUnitProviderSpi} that provides the
 * ISO 4217 currencies available from the JDK {@link Currency} class.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
@Singleton
public class IsoCurrencyJDKProvider implements CurrencyUnitProviderComponentSpi {

	private final Map<String, CurrencyUnit> currencies = new ConcurrentHashMap<String, CurrencyUnit>();

	public IsoCurrencyJDKProvider() {
		Set<Currency> jdkCurrencies = Currency.getAvailableCurrencies();
		for (Currency jdkCurrency : jdkCurrencies) {
			CurrencyUnit currency = MoneyCurrency.of(jdkCurrency);
			this.currencies.put(currency.getCurrencyCode(), currency);
		}
	}

	public String getNamespace() {
		return MoneyCurrency.ISO_NAMESPACE;
	}

	public CurrencyUnit getCurrency(String code, Long timestamp) {
		if (timestamp == null) {
			return this.currencies.get(code);
		}
		return null;
	}

	public CurrencyUnit[] getCurrencies(Locale locale, Long timestamp) {
		if (timestamp == null && locale != null
				&& locale.getCountry().length() == 2) {
			Currency currency = Currency.getInstance(locale);
			if (currency != null) {
				return new CurrencyUnit[] { this.currencies.get(currency
						.getCurrencyCode()) };
			}
		}
		return null;
	}

	public CurrencyUnit[] getCurrencies(Long timestamp) {
		if (timestamp == null) {
			return this.currencies.values().toArray(
					new CurrencyUnit[this.currencies.size()]);
		}
		return null;
	}

	public boolean isAvailable(String code, Long start, Long end) {
		if (start == null && end == null) {
			return this.currencies.containsKey(code);
		}
		return false;
	}

}
