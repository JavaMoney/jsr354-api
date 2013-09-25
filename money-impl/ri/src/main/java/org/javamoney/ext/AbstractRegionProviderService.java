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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


import org.javamoney.ext.Region;
import org.javamoney.ext.RegionType;
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
public abstract class AbstractRegionProviderService {
	/** The logger used. */
	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractRegionProviderService.class);

	/**
	 * Access all regions for a given {@link RegionType}.
	 * 
	 * @param type
	 *            The region type, not null.
	 * @return the regions found, never null.
	 */
	private Collection<Region> getAll(RegionType type) {
		Set<Region> result = new HashSet<Region>();
		for (RegionProviderSpi prov : getRegionProviderSpis()) {
			Collection<Region> regions = prov.getRegions(type);
			if (regions == null || regions.isEmpty()) {
				LOG.warn("Provider did not provide any regions: "
						+ prov.getClass().getName());
			} else {
				result.addAll(regions);
			}
		}
		return result;
	}

	/**
	 * Access all regions.
	 * 
	 * @return the regions found, never null.
	 */
	private Collection<Region> getAll() {
		Set<Region> result = new HashSet<Region>();
		for (RegionProviderSpi prov : getRegionProviderSpis()) {
			Collection<RegionType> types = prov.getRegionTypes();
			for (RegionType t : types) {
				Collection<Region> regions = prov.getRegions(t);
				if (regions == null || regions.isEmpty()) {
					LOG.warn("Provider did not provide any regions: "
							+ prov.getClass().getName());
				} else {
					result.addAll(regions);
				}
			}
		}
		return result;
	}

	/**
	 * Access a {@link Region} by {@link RegionType} and its numeric id.
	 * 
	 * @param type
	 *            The region's type
	 * @param numericId
	 *            the region's numeric id
	 * @return the region found, never {@code null}
	 * @throws IllegalArgumentException
	 *             , if no such region could be found.
	 */
	public Region getRegion(RegionType type, int numericId) {
		for (RegionProviderSpi prov : getRegionProviderSpis()) {
			Region reg = prov.getRegion(type, numericId);
			if (reg != null) {
				return reg;
			}
		}
		throw new IllegalArgumentException("So such reagion " + type + ':'
				+ numericId);
	}

	/**
	 * Access a {@link Region} by {@link RegionType} and its textual id.
	 * 
	 * @param type
	 *            The region's type
	 * @param code
	 *            the region's textual id
	 * @return the region found, never {@code null}
	 * @throws IllegalArgumentException
	 *             , if no such region could be found.
	 */
	public Region getRegion(RegionType type, String code) {
		for (RegionProviderSpi prov : getRegionProviderSpis()) {
			Region reg = prov.getRegion(type, code);
			if (reg != null) {
				return reg;
			}
		}
		throw new IllegalArgumentException("So such reagion " + type + ':'
				+ code);
	}

	/**
	 * Get all {@link RegionType} instances that are known.
	 * 
	 * @see RegionProviderSpi#getRegionTypes()
	 * @return the {@link RegionType} instances defined by the registered
	 *         {@link RegionProviderSpi} instances.
	 */
	public Set<RegionType> getRegionTypes() {
		Set<RegionType> result = new HashSet<RegionType>();
		for (RegionProviderSpi prov : getRegionProviderSpis()) {
			Collection<RegionType> regionTypes = prov.getRegionTypes();
			if (regionTypes == null || regionTypes.isEmpty()) {
				LOG.warn("Provider did not provide any regions: "
						+ prov.getClass().getName());
			} else {
				result.addAll(regionTypes);
			}
		}
		return result;
	}

	/**
	 * Get all {@link Region} instances of a given {@link RegionType}.
	 * 
	 * @param type
	 *            the region's type
	 * @return all {@link Region} instances with the given type.
	 */
	public Collection<Region> getRegions(RegionType type) {
		Set<Region> result = new HashSet<Region>();
		for (RegionProviderSpi prov : getRegionProviderSpis()) {
			Collection<Region> regions = prov.getRegions(type);
			if (regions == null || regions.isEmpty()) {
				LOG.warn("Provider did not provide any regions: "
						+ prov.getClass().getName());
			} else {
				result.addAll(regions);
			}
		}
		return result;
	}

	/**
	 * Get the region by its locale.
	 * 
	 * @param locale
	 *            The locale
	 * @return the region found, or {@code null}
	 */
	public Region getRegion(Locale locale) {
		for (RegionProviderSpi prov : getRegionProviderSpis()) {
			Region region = prov.getRegion(locale);
			if (region != null) {
				return region;
			}
		}
		return null;
	}

	/**
	 * Access all registered {@link RegionProviderSpi} instances.
	 * 
	 * @return all registered {@link RegionProviderSpi} instances.
	 */
	public Map<Class, RegionProviderSpi> getRegisteredRegionProviderSpis() {
		Map<Class, RegionProviderSpi> regionProviders = new HashMap<Class, RegionProviderSpi>();
		for (RegionProviderSpi prov : getRegionProviderSpis()) {
			regionProviders.put(prov.getClass(), prov);
		}
		return regionProviders;
	}

	/**
	 * Method to return all {@link RegionProviderSpi} instances. This allows to
	 * use different loading mechanisms, depending on the target runtime
	 * environment.
	 * 
	 * @return the {@link RegionProviderSpi} instances loaded.
	 */
	protected abstract Iterable<RegionProviderSpi> getRegionProviderSpis();
}
