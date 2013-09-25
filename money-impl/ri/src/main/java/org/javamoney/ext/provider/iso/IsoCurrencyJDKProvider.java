/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial implementation. Werner Keil -
 * extension and adjustment.
 */
package org.javamoney.ext.provider.iso;

import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;

import org.javamoney.ext.spi.CurrencyUnitProviderSpi;


/**
 * Basic implementation of a {@link CurrencyUnitProviderSpi} that provides the
 * ISO 4217 currencies available from the JDK {@link Currency} class.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
@Singleton
public class IsoCurrencyJDKProvider implements CurrencyUnitProviderSpi {

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

    public CurrencyUnit get(String code) {
    	return this.currencies.get(code);
    }

//    public Collection<CurrencyUnit> getCurrencies(Locale locale) {
//	if (locale != null && locale.getCountry().length() == 2) {
//	    Currency currency = Currency.getInstance(locale);
//	    if (currency != null) {
//		List<CurrencyUnit> result = new ArrayList<CurrencyUnit>();
//		result.add(this.currencies.get(currency.getCurrencyCode()));
//		return result;
//	    }
//	}
//	return null;
//    }

    public Collection<CurrencyUnit> getAll() {
    	return Collections.unmodifiableCollection(this.currencies.values());
    }

    public boolean isAvailable(String code) {
    	return this.currencies.containsKey(code);
    }

}
