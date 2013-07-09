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
package net.java.javamoney.ri.convert;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.convert.ConversionProvider;
import javax.money.convert.ExchangeRateType;
import javax.money.convert.spi.MonetaryConversionsSingletonSpi;

public class SEMonetaryConversionsSingletonSpi implements MonetaryConversionsSingletonSpi {

	private Map<ExchangeRateType, ConversionProvider> conversionProviders = new ConcurrentHashMap<ExchangeRateType, ConversionProvider>();

	public SEMonetaryConversionsSingletonSpi() {
		reload();
	}

	public void reload() {
		for (ConversionProvider prov : ServiceLoader.load(ConversionProvider.class)) {
			this.conversionProviders.put(prov.getExchangeRateType(), prov);
		}
	}

	@Override
	public ConversionProvider getConversionProvider(ExchangeRateType type) {
		ConversionProvider prov = this.conversionProviders.get(type);
		if (prov == null) {
			throw new IllegalArgumentException("Unsupported ExchangeRateType: "
					+ type);
		}
		return prov;
	}

	@Override
	public Collection<ExchangeRateType> getSupportedExchangeRateTypes() {
		return Collections.unmodifiableCollection(conversionProviders.keySet());
	}

	@Override
	public boolean isSupportedExchangeRateType(ExchangeRateType type) {
		return conversionProviders.containsKey(type);
	}

}
