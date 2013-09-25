/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.javamoney.cdi.ext;

import java.util.ArrayList;
import java.util.List;


import org.javamoney.cdi.CDIContainer;
import org.javamoney.ext.AbstractRegionTreeProviderService;
import org.javamoney.ext.spi.RegionTreeProviderSpi;


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
