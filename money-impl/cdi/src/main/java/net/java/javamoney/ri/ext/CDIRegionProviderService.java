/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.java.javamoney.ri.ext;

import net.java.javamoney.ri.ext.AbstractRegionProviderService;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import javax.money.ext.spi.CurrencyUnitProviderSpi;
import javax.money.ext.spi.RegionProviderSpi;
import net.java.javamoney.cdi.CDIContainer;

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
