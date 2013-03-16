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
 */
package net.java.javamoney.ri.core.provider;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;
import javax.money.convert.ConversionProvider;
import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.ExchangeRateType;
import javax.money.provider.Monetary;

import net.java.javamoney.ri.common.AbstractRiComponent;
import net.java.javamoney.ri.convert.provider.DefaultCurrencyConverter;
import net.java.javamoney.ri.convert.spi.CurrencyConverterFactorySpi;

@Singleton
public class ConversionProviderImpl extends AbstractRiComponent implements
		ConversionProvider {

	private Map<ExchangeRateType, CurrencyConverter> currencyConverters = new ConcurrentHashMap<ExchangeRateType, CurrencyConverter>();
	private Map<ExchangeRateType, ExchangeRateProvider> exchangeRateProviders = new ConcurrentHashMap<ExchangeRateType, ExchangeRateProvider>();
	private CurrencyConverterFactorySpi converterFactory;

	@SuppressWarnings("unchecked")
	public ConversionProviderImpl() {
		try {
			converterFactory = Monetary.getLoader().getInstance(
					CurrencyConverterFactorySpi.class);
		} catch (Exception e) {
			log.debug("No CurrencyConverterFactorySpi implementation found, using default CurrencyConversion.");
		}
		reload();
	}

	@SuppressWarnings("unchecked")
	public void reload() {
		for (ExchangeRateProvider prov : Monetary.getLoader().getInstances(
				ExchangeRateProvider.class)) {
			this.exchangeRateProviders.put(prov.getExchangeRateType(), prov);
		}
	}

	@Override
	public CurrencyConverter getCurrencyConverter(ExchangeRateType type) {
		CurrencyConverter converter = this.currencyConverters.get(type);
		if (converter == null) {
			if (isSupportedExchangeRateType(type)) {
				if (converterFactory != null) {
					converter = converterFactory.createCurrencyConverter(type);
				}
			}
			if (converter == null) {
				converter = new DefaultCurrencyConverter(type);
			}
		}
		return converter;
	}

	@Override
	public ExchangeRateProvider getExchangeRateProvider(ExchangeRateType type) {
		ExchangeRateProvider prov = this.exchangeRateProviders.get(type);
		if (prov == null) {
			throw new IllegalArgumentException("Unsupported ExchangeRateType: "
					+ type);
		}
		return prov;
	}

	@Override
	public Enumeration<ExchangeRateType> getSupportedExchangeRateTypes() {
		return Collections.enumeration(exchangeRateProviders.keySet());
	}

	@Override
	public boolean isSupportedExchangeRateType(ExchangeRateType type) {
		return exchangeRateProviders.containsKey(type);
	}

}
