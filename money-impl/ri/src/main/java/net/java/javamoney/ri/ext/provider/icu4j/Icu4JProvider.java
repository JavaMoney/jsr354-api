package net.java.javamoney.ri.ext.provider.icu4j;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.money.ext.Region;
import javax.money.ext.RegionType;

import net.java.javamoney.ri.ext.BuildableRegion;
import net.java.javamoney.ri.ext.spi.RegionProviderSpi;

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
    public Region getRegion(String code, RegionType type) {
	Set<com.ibm.icu.util.Region> icuRegions = com.ibm.icu.util.Region
		.getAvailable(com.ibm.icu.util.Region.RegionType.valueOf(type.getId()));
	for (com.ibm.icu.util.Region icuRegion : icuRegions) {
	    Region region = new BuildableRegion(icuRegion.toString(), RegionType.of(icuRegion.getType().name()));
	     // TODO add region...
	}
	return null;
    }

    @Override
    public Collection<Region> getRegions(RegionType type) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<Region> getRegions() {
	// TODO Auto-generated method stub
	return null;
    }

}
