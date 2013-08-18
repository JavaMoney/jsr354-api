/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.java.javamoney.ri.ext;

import net.java.javamoney.ri.ext.AbstractRegionProviderService;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import javax.money.ext.spi.RegionProviderSpi;
import javax.money.ext.spi.RegionTreeProviderSpi;

/**
 *
 * @author Anatole
 */
public class SERegionTreeProviderService extends AbstractRegionTreeProviderService {

    private List<RegionTreeProviderSpi> regionTreeProviderSpis;
    
    @Override
    protected Iterable<RegionTreeProviderSpi> getRegionTreeProviderSpis() {
        if(regionTreeProviderSpis==null){
            List<RegionTreeProviderSpi> load = new ArrayList<RegionTreeProviderSpi>();
            ServiceLoader<RegionTreeProviderSpi> services = ServiceLoader.load(RegionTreeProviderSpi.class);
            for (RegionTreeProviderSpi regionForestProviderSpi : services) {
                load.add(regionForestProviderSpi);
            }
            this.regionTreeProviderSpis = load;
        }
        return regionTreeProviderSpis;
    }
    
}
