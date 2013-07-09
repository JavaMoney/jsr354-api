/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * 
 * Contributors:
 *    Anatole Tresch - initial version.
 *    Wernner Keil - extensions and adaptions.
 */
package net.java.javamoney.ri.ext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.money.ext.Region;
import javax.money.ext.RegionType;
import javax.money.ext.RegionValidity;

import javax.money.ext.spi.RegionProviderSpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link RegionImpl}.
 *
 * @author Anatole Tresch
 */
public abstract class AbstractRegionProviderService {

    private static final Logger LOG = LoggerFactory
            .getLogger(AbstractRegionProviderService.class);


    /**
     * Access all regions for a given {@link RegionType}.
     *
     * @param type The region type, not null.
     * @return the regions found, never null.
     */
    private Collection<Region> getAll(RegionType type) {
        Set<Region> result = new HashSet<Region>();
        for (RegionProviderSpi prov : getRegionProviderSpis()) {
            Collection<Region> regions = prov.getRegions(type);
            if (regions == null || regions.isEmpty()) {
                LOG.warn("Provider did not provide any regions: "
                        + prov.getClass().getName());
            } else {
                result.addAll(regions);
            }
        }
        return result;
    }

    /**
     * Access all regions.
     *
     * @return the regions found, never null.
     */
    private Collection<Region> getAll() {
        Set<Region> result = new HashSet<Region>();
        for (RegionProviderSpi prov : getRegionProviderSpis()) {
            Collection<Region> regions = prov.getRegions();
            if (regions == null || regions.isEmpty()) {
                LOG.warn("Provider did not provide any regions: "
                        + prov.getClass().getName());
            } else {
                result.addAll(regions);
            }
        }
        return result;
    }

    public Region getRootRegion(String id) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Set<String> getRegionValidityProviders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public RegionValidity getRegionValidity(String provider) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Region getRegionTree(int numericId) {
        for(Region region: getRegionTrees()){
            if(region.getNumericRegionCode() == numericId){
               return region;
            }
        }
        throw new IllegalArgumentException("So such reagion tree " + numericId);
    }

    public Region getRegionTree(String id) {
        for(Region region: getRegionTrees()){
            if(region.getRegionCode().equals(id)){
               return region;
            }
        }
        throw new IllegalArgumentException("So such reagion tree " + id);
    }

    public Collection<Region> getRegionTrees() {
        Collection<Region> regions = getAll();
        Set<Region> result = new HashSet<Region>();
        for (Region region : regions) {
            if (region.getParent() == null) {
                result.add(region);
            }
        }
        return result;
    }

    public Region getRegion(RegionType type, int numericId) {
        for (RegionProviderSpi prov : getRegionProviderSpis()) {
            Region reg = prov.getRegion(numericId, type);
            if (reg != null) {
                return reg;
            }
        }
        throw new IllegalArgumentException("So such reagion " + type + ':'
                + numericId);
    }

    public Region getRegion(RegionType type, String code) {
        for (RegionProviderSpi prov : getRegionProviderSpis()) {
            Region reg = prov.getRegion(code, type);
            if (reg != null) {
                return reg;
            }
        }
        throw new IllegalArgumentException("So such reagion " + type + ':'
                + code);
    }

    public Set<RegionType> getRegionTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected abstract Iterable<RegionProviderSpi> getRegionProviderSpis();
    
}
