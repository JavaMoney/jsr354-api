package net.java.javamoney.ri.ext.provider;

import java.util.Collection;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.ext.MonetaryRegions.MonetaryRegionsSpi;
import javax.money.ext.Region;

import net.java.javamoney.ri.ext.impl.SERegionProvider;
import net.java.javamoney.ri.ext.impl.RegionalCurrencyUnitProvider;

public class StandaloneMonetaryRegionSpi implements MonetaryRegionsSpi {

	private SERegionProvider regionsProvider = new SERegionProvider();
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
		return regionalCurrencyProvider.isLegalCurrencyUnit(currency, region, timestamp);
	}

	@Override
	public Set<CurrencyUnit> getLegalCurrencyUnits(Region region, Long timestamp) {
		return regionalCurrencyProvider.getLegalCurrencyUnits(region, timestamp);
	}

}
