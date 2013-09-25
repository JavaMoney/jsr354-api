/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javamoney.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;


import org.javamoney.ext.AbstractExtendedRegionDataService;
import org.javamoney.ext.spi.ExtendedRegionDataProviderSpi;

/**
 *
 * @author Anatole
 */
public class SEExtendedRegionDataService extends AbstractExtendedRegionDataService {

    private List<ExtendedRegionDataProviderSpi> regionDataProviderSpis;
    
    @Override
    protected Iterable<ExtendedRegionDataProviderSpi> getExtendedRegionDataProviderSpis() {
        if(regionDataProviderSpis==null){
            List<ExtendedRegionDataProviderSpi> load = new ArrayList<ExtendedRegionDataProviderSpi>();
            ServiceLoader<ExtendedRegionDataProviderSpi> services = ServiceLoader.load(ExtendedRegionDataProviderSpi.class);
            for (ExtendedRegionDataProviderSpi regionDataProviderSpi: services) {
                load.add(regionDataProviderSpi );
            }
            this.regionDataProviderSpis = load;
        }
        return regionDataProviderSpis;
    }
    
}
