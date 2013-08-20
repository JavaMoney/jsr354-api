/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * 
 * Contributors: Anatole Tresch - initial version. Wernner Keil - extensions and
 * adaptions.
 */
package net.java.javamoney.cdi.ext;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;

import javax.money.ext.Region;
import javax.money.ext.RegionTreeNode;
import javax.money.ext.RegionType;
import javax.money.ext.RegionValidity;
import javax.money.ext.spi.RegionsSingletonSpi;

import net.java.javamoney.ri.ext.AbstractRegionProviderService;
import net.java.javamoney.ri.ext.AbstractRegionTreeProviderService;
import net.java.javamoney.ri.ext.AbstractRegionalCurrencyUnitProviderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link RegionImpl}.
 * 
 * @author Anatole Tresch
 */
public class CDIRegionsSingletonSpi implements RegionsSingletonSpi {

	private static final Logger LOG = LoggerFactory
			.getLogger(CDIRegionsSingletonSpi.class);

	/**
	 * Loaded region providers.
	 */
	private AbstractRegionProviderService regionProviderService = new CDIRegionProviderService();

	/**
	 * Loaded region providers.
	 */
	private AbstractRegionTreeProviderService regionTreeProviderService = new CDIRegionTreeProviderService();

	/**
	 * Loaded currency/region providers.
	 */
	private AbstractRegionalCurrencyUnitProviderService regionalCurrencyUnitProviderService = new CDIRegionalCurrencyUnitProviderService();

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

	@Override
	public Region getRegion(Locale locale) {
		return regionProviderService.getRegion(locale);
	}

	@Override
	public Collection<Region> getRegions(RegionType type) {
		return regionProviderService.getRegions(type);
	}

	@Override
	public RegionTreeNode getRegionTree(String treeId) {
		return regionTreeProviderService.getRegionTree(treeId);
	}

	@Override
	public Set<String> getRegionTreeIds() {
		return regionTreeProviderService.getRegionTreeIds();
	}

	@Override
	public RegionValidity getRegionValidity() {
		throw new UnsupportedOperationException(
				"Not yet implemented in JSR 354 RI.");
	}
}
