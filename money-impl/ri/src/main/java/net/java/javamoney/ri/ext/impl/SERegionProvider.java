/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * Contributors:
 *    Anatole Tresch - initial version.
 *    Wernner Keil - extensions and adaptions.
 */
package net.java.javamoney.ri.ext.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import javax.inject.Singleton;
import javax.money.ext.Region;
import javax.money.ext.RegionType;

import net.java.javamoney.ri.ext.spi.RegionProviderSpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link RegionImpl}.
 * 
 * @author Anatole Tresch
 */
public final class SERegionProvider {
	/** Loaded region providers. */
	private List<RegionProviderSpi> regionProviders = new ArrayList<RegionProviderSpi>();

	private static final Logger LOG = LoggerFactory
			.getLogger(SERegionProvider.class);

	public SERegionProvider() {
		reload();
	}

	/**
	 * This method reloads the providers available from the
	 * {@link ServiceLoader}. This adds providers that were not yet visible
	 * before.
	 */
	@SuppressWarnings("unchecked")
	public void reload() {
		regionProviders.clear();
		for(RegionProviderSpi prov: ServiceLoader.load(RegionProviderSpi.class)){
			regionProviders.add(prov);
		}
	}

	/**
	 * Access a region.
	 * 
	 * @param identifier
	 *            The region's id, not null.
	 * @param type
	 *            The region type, not null.
	 * @return the region instance.
	 * @throws IllegalArgumentException
	 *             if the region does not exist.
	 */
	public Region get(String identifier, RegionType type) {
		for (RegionProviderSpi prov : regionProviders) {
			Region reg = prov.getRegion(identifier, type);
			if (reg != null) {
				return reg;
			}
		}
		throw new IllegalArgumentException("So such reagion " + type + ':'
				+ identifier);
	}

	/**
	 * Access all regions for a given {@link RegionType}.
	 * 
	 * @param type
	 *            The region type, not null.
	 * @return the regions found, never null.
	 */
	public Collection<Region> getAll(RegionType type) {
		Set<Region> result = new HashSet<Region>();
		for (RegionProviderSpi prov : regionProviders) {
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
	public Collection<Region> getAll() {
		Set<Region> result = new HashSet<Region>();
		for (RegionProviderSpi prov : regionProviders) {
			Collection<Region> regions = prov.getRegions();
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
	 * Access all region that have no parent.
	 * 
	 * @return the regions found, never null.
	 */
	public Collection<Region> getRootRegions() {
		Collection<Region> regions = getAll();
		Set<Region> result = new HashSet<Region>();
		for (Region region : regions) {
			if (region.getParent() == null) {
				result.add(region);
			}
		}
		return result;
	}

	public Region getRootRegion(String id) {
		throw new UnsupportedOperationException("Not implemented");
	}

}
