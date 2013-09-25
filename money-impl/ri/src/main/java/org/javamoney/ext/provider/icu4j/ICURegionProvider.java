/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial implementation. Werner Keil -
 * extension and adjustment.
 */
package org.javamoney.ext.provider.icu4j;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import org.javamoney.ext.Region;
import org.javamoney.ext.RegionType;
import org.javamoney.ext.spi.RegionProviderSpi;

/**
 * RegionProvider implementation based on the data returned by ICU4J.
 * 
 * @author Anatole Tresch
 */
@Singleton
public class ICURegionProvider implements RegionProviderSpi {

	private Set<RegionType> regionTypes = new HashSet<RegionType>();

	private Map<String, Region> regions = new HashMap<String, Region>();

	// CLDR/world/continents/territories

	public ICURegionProvider() {
		reload();
	}

	private void reload() {
		Region world = null;
		regionTypes.clear();
		for (com.ibm.icu.util.Region.RegionType rt : com.ibm.icu.util.Region.RegionType
				.values()) {
			this.regionTypes.add(RegionType.of(rt.name()));
			Set<com.ibm.icu.util.Region> icuRegions = com.ibm.icu.util.Region
					.getAvailable(com.ibm.icu.util.Region.RegionType
							.valueOf(rt.name()));
			for (com.ibm.icu.util.Region icuRegion : icuRegions) {
				RegionType type = RegionType.of(icuRegion.getType().name());
				regionTypes.add(type);
				Region region = new IcuRegion(icuRegion, type);
				regions.put(region.getRegionCode(), region);
				if (region.getRegionType().equals(RegionType.WORLD)) {
					world = region;
				}
			}
		}
		Set<com.ibm.icu.util.Region> icuRegions = com.ibm.icu.util.Region
				.getAvailable(com.ibm.icu.util.Region.RegionType.WORLD);
	}

	@Override
	public Collection<RegionType> getRegionTypes() {
		return Collections.unmodifiableSet(regionTypes);
	}

	@Override
	public Region getRegion(RegionType type, String code) {
		com.ibm.icu.util.Region icuRegion = com.ibm.icu.util.Region
				.getInstance(code);
		if (icuRegion != null) {
			Region region = this.regions.get(code);
			if (region.getRegionType().equals(type)) {
				return region;
			}
		}
		return null;
	}

	@Override
	public Collection<Region> getRegions(RegionType type) {
		Set<Region> result = new HashSet<Region>();
		for (Region region : regions.values()) {
			if (region.getRegionType().equals(type)) {
				result.add(region);
			}
		}
		return result;
	}

	@Override
	public Region getRegion(RegionType type, int numericId) {
		for (Region region : regions.values()) {
			if (region.getRegionType().equals(type)
					&& region.getNumericRegionCode() == numericId) {
				return region;
			}
		}
		return null;
	}

	@Override
	public Region getRegion(Locale locale) {
		return regions.get(locale.getCountry());
	}

}