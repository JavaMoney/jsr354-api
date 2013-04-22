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
package net.java.javamoney.ri.ext.impl;

import java.util.ServiceLoader;

import javax.money.CurrencyUnit;
import javax.money.convert.MonetaryConversions.MonetaryConversionsSpi;
import javax.money.ext.MonetaryCurrencies.CurrencyUnitMapperSpi;

import net.java.javamoney.ri.format.impl.DelegatingMonetaryFormatsSpi;
import net.java.javamoney.ri.spi.ComponentLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class DelegatingCurrencyUnitMapperSpi implements CurrencyUnitMapperSpi {
	
	private CurrencyUnitMapperSpi delegate = loadDelegate();

	private static final Logger LOG = LoggerFactory
			.getLogger(DelegatingMonetaryFormatsSpi.class);

	public DelegatingCurrencyUnitMapperSpi() {
	}

	private CurrencyUnitMapperSpi loadDelegate() {
		CurrencyUnitMapperSpi impl = null;
		try {
			for (ComponentLoader l : ServiceLoader.load(ComponentLoader.class)) {
				try {
					impl = l.getComponent(CurrencyUnitMapperSpi.class);
					if (impl != null) {
						break;
					}
				} catch (Exception e) {
					LOG.warn(
							"Error accessing component from CommponentLoader (ignoring error): "
									+ l, e);
				}
			}
		} catch (Exception e) {
			LOG.warn(
					"No CommponentLoader registered, or registration error (loading ServiceLoader based default) for "
							+ MonetaryConversionsSpi.class.getName(), e);
		}
		if (impl == null) {
			// load default
			impl = new ServiceLoaderCurrencyUnitMapperSpi();
		}
		return impl;
	}

	@Override
	public CurrencyUnit map(String targetNamespace, Long timestamp,
			CurrencyUnit currencyUnit) {
		return this.delegate.map(targetNamespace, timestamp, currencyUnit);
	}

}
