package net.java.javamoney.ri.ext.provider.icu4j;

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
			if (locale.equals(region.getLocale()) && region instanceof ISOCountry){
				return region;
			}
		}
		return null;
	}
}