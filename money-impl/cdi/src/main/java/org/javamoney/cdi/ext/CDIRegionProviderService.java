/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javamoney.cdi.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.javamoney.cdi.CDIContainer;
import org.javamoney.ext.AbstractRegionProviderService;
import org.javamoney.ext.spi.CurrencyUnitProviderSpi;
import org.javamoney.ext.spi.RegionProviderSpi;


/**
 *
 * @author Anatole
 */
public class CDIRegionProviderService extends AbstractRegionProviderService {

    private List<RegionProviderSpi> regionProviderSpis;
    
    @Override
    protected Iterable<RegionProviderSpi> getRegionProviderSpis() {
        if(regionProviderSpis==null){
            List<RegionProviderSpi> load = new ArrayList<RegionProviderSpi>();
            for (RegionProviderSpi regionProviderSpi : CDIContainer.getInstances(RegionProviderSpi.class)) {
                load.add(regionProviderSpi);
            }
            this.regionProviderSpis = load;
        }
        return regionProviderSpis;
    }
    
}
