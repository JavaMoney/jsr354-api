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

/**
 *
 * @author Anatole
 */
public class SERegionProviderService extends AbstractRegionProviderService {

    private List<RegionProviderSpi> regionProviderSpis;
    
    @Override
    protected Iterable<RegionProviderSpi> getRegionProviderSpis() {
        if(regionProviderSpis==null){
            List<RegionProviderSpi> load = new ArrayList<RegionProviderSpi>();
            ServiceLoader<RegionProviderSpi> services = ServiceLoader.load(RegionProviderSpi.class);
            for (RegionProviderSpi regionProviderSpi : services) {
                load.add(regionProviderSpi);
            }
            this.regionProviderSpis = load;
        }
        return regionProviderSpis;
    }
    
}
