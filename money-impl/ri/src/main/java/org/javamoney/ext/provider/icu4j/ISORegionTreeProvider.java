/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial implementation. Werner Keil -
 * extension and adjustment.
 */
package org.javamoney.ext.provider.icu4j;

import java.util.Locale;
import java.util.Map;

import javax.inject.Singleton;

import org.javamoney.ext.BuildableRegionNode;
import org.javamoney.ext.Region;
import org.javamoney.ext.RegionTreeNode;
import org.javamoney.ext.RegionType;
import org.javamoney.ext.BuildableRegionNode.Builder;
import org.javamoney.ext.spi.RegionProviderSpi;
import org.javamoney.ext.spi.RegionTreeProviderSpi;


/**
 * Region Tree provider that provides all ISO countries, defined by
 * {@link Locale#getISOCountries()} using their 2-letter ISO country code under
 * {@code ISO}.
 * 
 * @author Anatole Tresch
 */
@Singleton
public class ISORegionTreeProvider implements RegionTreeProviderSpi {

	private BuildableRegionNode regionTree;

	// ISO/...

	@Override
	public String getTreeId() {
		return "ISO";
	}

	@Override
	public void init(Map<Class, RegionProviderSpi> providers) {
		Builder treeBuilder = new BuildableRegionNode.Builder(new SimpleRegion(
				"ISO"));

		ISORegionProvider regionProvider = (ISORegionProvider) providers
				.get(ISORegionProvider.class);
		for (String country : Locale.getISOCountries()) {
			Region region = regionProvider.getRegion(RegionType.of("ISO"),
					country);
			Builder nodeBuilder = new BuildableRegionNode.Builder(region);
			treeBuilder.addChildRegions(nodeBuilder.build());
		}
		regionTree = treeBuilder.build();
	}

	@Override
	public RegionTreeNode getRegionTree() {
		return regionTree;
	}

}