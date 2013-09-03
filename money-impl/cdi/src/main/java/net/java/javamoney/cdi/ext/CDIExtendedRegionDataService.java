/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package net.java.javamoney.cdi.ext;

import java.util.ArrayList;
import java.util.List;

import javax.money.ext.spi.ExtendedRegionDataProviderSpi;

import net.java.javamoney.cdi.CDIContainer;
import net.java.javamoney.ri.ext.AbstractExtendedRegionDataService;

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
