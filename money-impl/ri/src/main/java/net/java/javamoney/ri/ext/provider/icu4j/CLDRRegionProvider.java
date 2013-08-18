package net.java.javamoney.ri.ext.provider.icu;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;
import javax.money.ext.Region;
import javax.money.ext.RegionType;
import javax.money.ext.spi.RegionProviderSpi;

@Singleton
public class CLDRRegionProvider implements RegionProviderSpi {

	private Set<RegionType> regionTypes = new HashSet<RegionType>();

	private Map<String, Region> regions = new HashMap<String, Region>();

	// CLDR/world/continents/territories

	public CLDRRegionProvider() {
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
				if(region.getRegionType().equals(RegionType.WORLD)){
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
			if(region.getRegionType().equals(type)){
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