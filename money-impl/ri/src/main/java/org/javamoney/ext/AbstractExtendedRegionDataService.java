/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial version. Wernner Keil - extensions and
 * adaptions.
 */
package org.javamoney.ext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


import org.javamoney.ext.Region;
import org.javamoney.ext.spi.ExtendedRegionDataProviderSpi;
import org.javamoney.ext.spi.RegionProviderSpi;
import org.javamoney.ext.spi.RegionsSingletonSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class models the an internal service class, that provides the base
 * method used by the {@link RegionsSingletonSpi} implementation. It is extended
 * for different runtime scenarios, hereby allowing the spi implementation
 * loaded using different mechanisms.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public abstract class AbstractExtendedRegionDataService {
	/** The logger used. */
	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractExtendedRegionDataService.class);

	public Collection<Class> getExtendedRegionDataTypes(Region region) {
		Set<Class> dataTypes = new HashSet<Class>();
		for (ExtendedRegionDataProviderSpi prov : getExtendedRegionDataProviderSpis()) {
			dataTypes.addAll(prov.getExtendedRegionDataTypes(region));
		}
		return dataTypes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.Region#getRegionData(java.lang.Class)
	 */
	public <T> T getExtendedRegionData(Region region, Class<T> type) {
		for (ExtendedRegionDataProviderSpi prov : getExtendedRegionDataProviderSpis()) {
			if (prov.getExtendedRegionDataTypes(region).contains(type)) {
				T data = (T) prov.getExtendedRegionData(region, type);
				if (data != null) {
					return data;
				}
			}
		}
		throw new IllegalArgumentException("Unsupported data type for " + this
				+ ", use one of " + getExtendedRegionDataTypes(region));
	}

	/**
	 * Method to return all {@link RegionProviderSpi} instances. This allows to
	 * use different loading mechanisms, depending on the target runtime
	 * environment.
	 * 
	 * @return the {@link RegionProviderSpi} instances loaded.
	 */
	protected abstract Iterable<ExtendedRegionDataProviderSpi> getExtendedRegionDataProviderSpis();
}
