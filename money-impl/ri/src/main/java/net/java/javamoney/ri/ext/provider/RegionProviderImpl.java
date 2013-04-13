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
package net.java.javamoney.ri.ext.provider;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import javax.inject.Singleton;
import javax.money.ext.Region;
import javax.money.ext.RegionProvider;
import javax.money.ext.RegionType;

import net.java.javamoney.ri.common.AbstractRiComponent;
import net.java.javamoney.ri.ext.spi.RegionProviderSpi;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link RegionImpl}.
 * 
 * @author Anatole Tresch
 */
@Singleton
public final class RegionProviderImpl extends AbstractRiComponent implements
		RegionProvider {
	/** Singleton instance. */
	private static final RegionProviderImpl INSTANCE = new RegionProviderImpl();
	/** Loaded region providers. */
	private List<RegionProviderSpi> regionProviders;

	public RegionProviderImpl() {
		reload();
	}

	/**
	 * This method defined that this implementation is exposed as
	 * {@link RegionProvider}.
	 * 
	 * @return {@link RegionProvider}.class
	 */
	public Class<RegionProvider> getExposedType() {
		return RegionProvider.class;
	}


	/**
	 * This method reloads the providers available from the
	 * {@link ServiceLoader}. This adds providers that were not yet visible
	 * before.
	 */
	@SuppressWarnings("unchecked")
	public void reload() {
		regionProviders = getSPIProviders(RegionProviderSpi.class);
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
		for (RegionProviderSpi prov : INSTANCE.regionProviders) {
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
	public Region[] getAll(RegionType type) {
		Set<Region> result = new HashSet<Region>();
		for (RegionProviderSpi prov : INSTANCE.regionProviders) {
			Region[] regions = prov.getRegions(type);
			if (regions == null || regions.length == 0) {
				log.warn("Provider did not provide any regions: "
						+ prov.getClass().getName());
			} else {
				result.addAll(Arrays.asList(regions));
			}
		}
		return result.toArray(new Region[result.size()]);
	}

	/**
	 * Access all regions.
	 * 
	 * @return the regions found, never null.
	 */
	public Region[] getAll() {
		Set<Region> result = new HashSet<Region>();
		for (RegionProviderSpi prov : INSTANCE.regionProviders) {
			Region[] regions = prov.getRegions();
			if (regions == null) {
				log.warn("Provider did not provide any regions: "
						+ prov.getClass().getName());
			} else {
				result.addAll(Arrays.asList(regions));
			}
		}
		return result.toArray(new Region[result.size()]);
	}

	/**
	 * Access all region that have no parent.
	 * 
	 * @return the regions found, never null.
	 */
	@Override
	public Collection<Region> getRootRegions() {
		Region[] regions = getAll();
		Set<Region> result = new HashSet<Region>();
		for (Region region : regions) {
			if (region.getParent() == null) {
				result.add(region);
			}
		}
		return result;
	}


	@Override
	public Region getRootRegion(String id) {
		throw new UnsupportedOperationException("Not implemented");
	}

}
