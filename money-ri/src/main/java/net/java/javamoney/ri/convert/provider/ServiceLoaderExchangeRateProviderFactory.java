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

import java.util.ServiceLoader;

import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.ExchangeRateType;
import javax.money.convert.spi.ExchangeRateProviderFactorySpi;

import net.java.javamoney.ri.convert.spi.ExchangeRateProviderSpi;

/**
 * This class provides the default implementation for the
 * {@link ServiceLoaderExchangeRateProviderFactory}, which always returns instances of
 * {@link ServiceLoaderExchangeRateProvider}, which are relying on the
 * {@link ServiceLoader} to load according {@link ExchangeRateProviderSpi}
 * instances.
 * 
 * @author Anatole Tresch
 * 
 */
public class ServiceLoaderExchangeRateProviderFactory implements
		ExchangeRateProviderFactorySpi {

	@Override
	public ExchangeRateProvider createExchangeRateProvider(ExchangeRateType type) {
		return new ServiceLoaderExchangeRateProvider(type);
	}

}
