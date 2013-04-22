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
 */
package net.java.javamoney.ri.format.impl;

import java.util.Collection;
import java.util.ServiceLoader;

import javax.money.format.ItemFormat;
import javax.money.format.ItemFormatException;
import javax.money.format.LocalizationStyle;
import javax.money.format.MonetaryFormats.MonetaryFormatsSpi;

import net.java.javamoney.ri.spi.ComponentLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelegatingMonetaryFormatsSpi implements MonetaryFormatsSpi {

	private MonetaryFormatsSpi delegate = loadDelegate();

	private static final Logger LOG = LoggerFactory
			.getLogger(DelegatingMonetaryFormatsSpi.class);

	public DelegatingMonetaryFormatsSpi() {
	}

	private MonetaryFormatsSpi loadDelegate() {
		MonetaryFormatsSpi impl = null;
		try {
			for (ComponentLoader l : ServiceLoader.load(ComponentLoader.class)) {
				try {
					impl = l.getComponent(MonetaryFormatsSpi.class);
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
							+ MonetaryFormatsSpi.class.getName(), e);
		}
		if (impl == null) {
			// load default
			impl = new ServiceLoaderMonetaryFormatsSpi();
		}
		return impl;
	}

	@Override
	public Collection<String> getSupportedStyleIds(Class<?> targetType) {
		return this.delegate.getSupportedStyleIds(targetType);
	}

	@Override
	public boolean isSupportedStyle(Class<?> targetType, String styleId) {
		return this.delegate.isSupportedStyle(targetType, styleId);
	}

	@Override
	public <T> ItemFormat<T> getItemFormat(Class<T> targetType,
			LocalizationStyle style) throws ItemFormatException {
		return this.delegate.getItemFormat(targetType, style);
	}

}
