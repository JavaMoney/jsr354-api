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
 *    Wernner Keil - extensions and adaptions.
 */
package net.java.javamoney;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import javax.money.Region;
import javax.money.RegionType;
import javax.money.spi.RegionProvider;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link Region}.
 * 
 * @author Anatole Tresch
 */
public final class Regions extends AbstractSPIComponent {
	/** Singleton instance. */
	private static final Regions INSTANCE = new Regions();
	/** Loaded region providers. */
	private List<RegionProvider> regionProviders;

	/**
	 * Singleton constructor.
	 */
	private Regions() {
		try {
			reload();
		} catch (Exception e) {
			// TODO log excetion!
		}
	}

	/**
	 * This method reloads the providers available from the
	 * {@link ServiceLoader}. This adds providers that were not yet visible
	 * before.
	 */
	public void reload() {
		regionProviders = getSPIProviders(RegionProvider.class);
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
	public static Region getRegion(String identifier, RegionType type) {
		for (RegionProvider prov : INSTANCE.regionProviders) {
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
	public static Region[] getRegions(RegionType type) {
		Set<Region> result = new HashSet<Region>();
		for (RegionProvider prov : INSTANCE.regionProviders) {
			Region[] regions = prov.getRegions(type);
			if (regions == null) {
				// TODO Log warning
			}
			result.addAll(Arrays.asList(regions));
		}
		return result.toArray(new Region[result.size()]);
	}

	/**
	 * Access all regions.
	 * 
	 * @return the regions found, never null.
	 */
	public static Region[] getRegions() {
		Set<Region> result = new HashSet<Region>();
		for (RegionType type : RegionType.values()) {
			Region[] regions = getRegions(type);
			result.addAll(Arrays.asList(regions));
		}
		return result.toArray(new Region[result.size()]);
	}

}
