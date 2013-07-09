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
import java.util.Set;

import javax.money.ext.Region;
import javax.money.ext.RegionType;
import javax.money.ext.RegionValidity;
import javax.money.ext.spi.MonetaryRegionsSingletonSpi;

import net.java.javamoney.ri.ext.AbstractRegionProviderService;
import net.java.javamoney.ri.ext.AbstractRegionalCurrencyUnitProviderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link RegionImpl}.
 *
 * @author Anatole Tresch
 */
public class SEMonetaryRegionsSingletonSpi implements MonetaryRegionsSingletonSpi {

    private static final Logger LOG = LoggerFactory
            .getLogger(SEMonetaryRegionsSingletonSpi.class);
    
    /**
     * Loaded region providers.
     */
    private AbstractRegionProviderService regionProviderService = new SERegionProviderService();
    
     /**
     * Loaded currency/region providers.
     */
    private AbstractRegionalCurrencyUnitProviderService regionalCurrencyUnitProviderService = new SERegionalCurrencyUnitProviderService();


    public Region getRegionTree(String id) {
        return regionProviderService.getRegionTree(id);
    }

    @Override
    public Set<String> getRegionValidityProviders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegionValidity getRegionValidity(String provider) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Region getRegionTree(int numericId) {
        return regionProviderService.getRegionTree(numericId);
    }

    @Override
    public Collection<Region> getRegionTrees() {
        return regionProviderService.getRegionTrees();
    }

    @Override
    public Region getRegion(RegionType type, int numericId) {
        return regionProviderService.getRegion(type, numericId);
    }

    @Override
    public Region getRegion(RegionType type, String code) {
        return regionProviderService.getRegion(type, code);
    }

    @Override
    public Set<RegionType> getRegionTypes() {
        return regionProviderService.getRegionTypes();
    }
}
