/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.javamoney.cdi.ext;

import java.util.ArrayList;
import java.util.List;


import org.javamoney.cdi.CDIContainer;
import org.javamoney.ext.AbstractExtendedRegionDataService;
import org.javamoney.ext.spi.ExtendedRegionDataProviderSpi;


/**
 * CDI implementation of the {@link AbstractExtendedRegionDataService}.
 * 
 * @author Anatole Tresch
 */
public class CDIExtendedRegionDataService extends
		AbstractExtendedRegionDataService {
	
	/** The cached spi instances. */
	private List<ExtendedRegionDataProviderSpi> regionDataProviderSpis;

	@Override
	protected Iterable<ExtendedRegionDataProviderSpi> getExtendedRegionDataProviderSpis() {
		if (regionDataProviderSpis == null) {
			List<ExtendedRegionDataProviderSpi> load = new ArrayList<ExtendedRegionDataProviderSpi>();
			for (ExtendedRegionDataProviderSpi regionDataProviderSpi : CDIContainer
					.getInstances(ExtendedRegionDataProviderSpi.class)) {
				load.add(regionDataProviderSpi);
			}
			this.regionDataProviderSpis = load;
		}
		return regionDataProviderSpis;
	}

}
