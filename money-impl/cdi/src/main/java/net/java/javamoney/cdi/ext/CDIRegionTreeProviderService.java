/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package net.java.javamoney.cdi.ext;

import java.util.ArrayList;
import java.util.List;

import javax.money.ext.spi.RegionTreeProviderSpi;

import net.java.javamoney.cdi.CDIContainer;
import net.java.javamoney.ri.ext.AbstractRegionTreeProviderService;

/**
 * 
 * @author Anatole
 */
public class CDIRegionTreeProviderService extends
		AbstractRegionTreeProviderService {

	private List<RegionTreeProviderSpi> regionTreeProviderSpis;

	@Override
	protected Iterable<RegionTreeProviderSpi> getRegionTreeProviderSpis() {
		if (regionTreeProviderSpis == null) {
			List<RegionTreeProviderSpi> load = new ArrayList<RegionTreeProviderSpi>();
			for (RegionTreeProviderSpi regionTreeProviderSpi : CDIContainer
					.getInstances(RegionTreeProviderSpi.class)) {
				load.add(regionTreeProviderSpi);
			}
			this.regionTreeProviderSpis = load;
		}
		return regionTreeProviderSpis;
	}

}
