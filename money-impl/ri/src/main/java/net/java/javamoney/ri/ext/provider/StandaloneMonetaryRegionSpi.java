package net.java.javamoney.ri.ext.provider;

import java.util.Collection;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.ext.MonetaryRegions.MonetaryRegionsSpi;
import javax.money.ext.Region;

public class StandaloneMonetaryRegionSpi implements MonetaryRegionsSpi {

	private RegionProvider regionsProvider = new RegionProvider();
	private RegionalCurrencyUnitProvider regionalCurrencyProvider = new RegionalCurrencyUnitProvider();
	
	@Override
	public Collection<Region> getRootRegions() {
		return regionsProvider.getRootRegions();
	}

	@Override
	public Region getRootRegion(String id) {
		return regionsProvider.getRootRegion(id);
	}

	@Override
	public Set<CurrencyUnit> getAll(Region region, Long timestamp) {
		return regionalCurrencyProvider.getAll(region, timestamp);
	}

	@Override
	public boolean isLegalCurrencyUnit(CurrencyUnit currency, Region region,
			Long timestamp) {
		return regionalCurrencyProvider.isLegalTCurrencyUnit(currency, region, timestamp);
	}

	@Override
	public Set<CurrencyUnit> getLegalCurrencyUnits(Region region, Long timestamp) {
		return regionalCurrencyProvider.getLegalCurrencyUnits(region, timestamp);
	}

}
