package net.java.javamoney.ri.ext.provider.icu4j;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.inject.Singleton;
import javax.money.ext.Region;
import javax.money.ext.RegionType;
import javax.money.ext.spi.RegionProviderSpi;

import net.java.javamoney.ri.ext.DefaultRegion;

@Singleton
public class Icu4JProvider implements RegionProviderSpi {

    private Set<RegionType> regionTypes = new HashSet<RegionType>();

    public Icu4JProvider() {
	for (com.ibm.icu.util.Region.RegionType rt : com.ibm.icu.util.Region.RegionType.values()) {
	    this.regionTypes.add(RegionType.of(rt.name()));
	}
    }

    @Override
    public Collection<RegionType> getRegionTypes() {
	return Collections.unmodifiableSet(regionTypes);
    }

    @Override
    public Region getRegion(RegionType type, String code) {
	Set<com.ibm.icu.util.Region> icuRegions = com.ibm.icu.util.Region
		.getAvailable(com.ibm.icu.util.Region.RegionType.valueOf(type.getId()));
	for (com.ibm.icu.util.Region icuRegion : icuRegions) {
	    if(icuRegion.toString().equals(code)){
		Region region = new DefaultRegion(icuRegion.toString(), RegionType.of(icuRegion.getType().name()));
		// TODO add region...
		return region;
	    }
	}
	return null;
    }

    @Override
    public Collection<Region> getRegions(RegionType type) {
	Set<com.ibm.icu.util.Region> icuRegions = com.ibm.icu.util.Region
		.getAvailable(com.ibm.icu.util.Region.RegionType.valueOf(type.getId()));
	Set<Region> result = new HashSet<Region>();
	for (com.ibm.icu.util.Region icuRegion : icuRegions) {
	    Region region = new DefaultRegion(icuRegion.toString(), RegionType.of(icuRegion.getType().name()));
	    // TODO add region...
	    result.add(region);
	}
	return result;
    }


    @Override
    public Region getRegion(RegionType type, int numericId) {
	Set<com.ibm.icu.util.Region> icuRegions = com.ibm.icu.util.Region
		.getAvailable(com.ibm.icu.util.Region.RegionType.valueOf(type.getId()));
	for (com.ibm.icu.util.Region icuRegion : icuRegions) {
	    if(icuRegion.getNumericCode() == numericId){
		Region region = new DefaultRegion(icuRegion.toString(), RegionType.of(icuRegion.getType().name()));
		// TODO add region...
		return region;
	    }
	}
	return null;
    }

    @Override
    public Region getRegion(Locale locale) {
	return null;
    }

   

}
