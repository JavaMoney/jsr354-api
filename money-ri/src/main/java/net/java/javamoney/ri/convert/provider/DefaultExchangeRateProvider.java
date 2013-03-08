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
package net.java.javamoney.ri.convert.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.money.CurrencyUnit;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.ExchangeRateType;
import javax.money.convert.spi.ExchangeRateProviderSpi;

public class DefaultExchangeRateProvider implements ExchangeRateProvider {

	private final ExchangeRateType rateType;
	private final List<ExchangeRateProviderSpi> exchangeRateProviderSpis = new ArrayList<ExchangeRateProviderSpi>();
	
	private final ServiceLoader<ExchangeRateProviderSpi> exchangeRateProviderSpiLoader = ServiceLoader
			.load(ExchangeRateProviderSpi.class);
	
	public DefaultExchangeRateProvider(ExchangeRateType rateType) {
		if(rateType==null){
			throw new IllegalArgumentException("ExchangeRateType required.");
		}
		this.rateType = rateType;
		loadProviders();
	}

	private void loadProviders() {
		for (ExchangeRateProviderSpi t : exchangeRateProviderSpiLoader) {
			if(t.getExchangeRateType().equals(rateType)){
				this.exchangeRateProviderSpis.add(t);
			}
		}
	}

	@Override
	public ExchangeRateType getExchangeRateType() {
		return rateType;
	}

	@Override
	public boolean isAvailable(CurrencyUnit src, CurrencyUnit target) {
		for (ExchangeRateProviderSpi rateProviderSpi : exchangeRateProviderSpis) {
			if(rateProviderSpi.getExchangeRate(src, target, null) != null){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isAvailable(CurrencyUnit src, CurrencyUnit target,
			Long timestamp) {
		for (ExchangeRateProviderSpi rateProviderSpi : exchangeRateProviderSpis) {
			if(rateProviderSpi.getExchangeRate(src, target, timestamp) != null){
				return true;
			}
		}
		return false;
	}

	@Override
	public ExchangeRate get(CurrencyUnit source,
			CurrencyUnit target, Long timestamp) {
		for (ExchangeRateProviderSpi rateProviderSpi : exchangeRateProviderSpis) {
			ExchangeRate rate = rateProviderSpi.getExchangeRate(source, target, timestamp);
			if(rate != null){
				return rate;
			}
		}
		return null;
	}

	@Override
	public ExchangeRate get(CurrencyUnit source, CurrencyUnit target) {
		for (ExchangeRateProviderSpi rateProviderSpi : exchangeRateProviderSpis) {
			ExchangeRate rate = rateProviderSpi.getExchangeRate(source, target, null);
			if(rate != null){
				return rate;
			}
		}
		return null;
	}

}
