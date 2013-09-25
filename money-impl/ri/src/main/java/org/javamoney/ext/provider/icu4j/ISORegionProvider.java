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
 * Region Tree provider that provides all ISO countries, defined by
 * {@link Locale#getISOCountries()} using their 2-letter ISO country code under
 * {@code ISO}.
 * 
 * @author Anatole Tresch
 */
@Singleton
public class ISORegionProvider implements RegionProviderSpi {

	private Set<RegionType> regionTypes = new HashSet<RegionType>();

	private Map<String, Region> regions = new HashMap<String, Region>();

	public ISORegionProvider() {
		reload();
	}

	private void reload() {
		regionTypes.clear();
		RegionType rt = RegionType.of("ISO");
		regionTypes.add(rt);
		for (String country : Locale.getISOCountries()) {
			Locale locale = new Locale("", country);
			ISOCountry region = new ISOCountry(locale, rt);
			regions.put(region.getRegionCode(), region);
			ISO3Country region3 = new ISO3Country(locale, rt);
			regions.put(region.getISO3Code(), region3);
		}
	}

	@Override
	public Collection<RegionType> getRegionTypes() {
		return Collections.unmodifiableSet(regionTypes);
	}

	@Override
	public Region getRegion(RegionType type, String code) {
		if (!"ISO".equals(type.getId())) {
			return null;
		}
		return this.regions.get(code);
	}

	@Override
	public Collection<Region> getRegions(RegionType type) {
		if (!"ISO".equals(type.getId())) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableCollection(regions.values());
	}

	@Override
	public Region getRegion(RegionType type, int numericId) {
		for (Region region : regions.values()) {
			if (region.getRegionType().equals(type)
					&& region.getNumericRegionCode() == numericId
					&& region instanceof ISOCountry) {
				return region;
			}
		}
		return null;
	}

	@Override
	public Region getRegion(Locale locale) {
		for (Region region : regions.values()) {
			if (locale.equals(region.getLocale())
					&& region instanceof ISOCountry) {
				return region;
			}
		}
		return null;
	}
}