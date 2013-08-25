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
package net.java.javamoney.ri.ext.provider.icu4j;

import java.util.Map;

import javax.inject.Singleton;
import javax.money.ext.Region;
import javax.money.ext.RegionTreeNode;
import javax.money.ext.RegionType;
import javax.money.ext.spi.RegionProviderSpi;
import javax.money.ext.spi.RegionTreeProviderSpi;

import net.java.javamoney.ri.ext.BuildableRegionNode;
import net.java.javamoney.ri.ext.BuildableRegionNode.Builder;

/**
 * Region tree provider implementation registering under {@code CLDR} which maps
 * the region tree provided by ICU4J.
 * 
 * @author Anatole Tresch
 */
@Singleton
public class ICURegionTreeProvider implements RegionTreeProviderSpi {

	private BuildableRegionNode regionTree;

	// CLDR/world/...

	@Override
	public String getTreeId() {
		return "CLDR";
	}

	@Override
	public void init(Map<Class, RegionProviderSpi> providers) {
		ICURegionProvider regionProvider = (ICURegionProvider) providers
				.get(ICURegionProvider.class);
		com.ibm.icu.util.Region icuWorld = com.ibm.icu.util.Region
				.getAvailable(com.ibm.icu.util.Region.RegionType.WORLD)
				.iterator().next();
		Region root = regionProvider.getRegion(RegionType.WORLD,
				icuWorld.toString());
		Builder treeBuilder = new BuildableRegionNode.Builder(root);
		populateRegionNode(regionProvider, treeBuilder);
		regionTree = treeBuilder.build();
	}

	private void populateRegionNode(ICURegionProvider regionProvider,
			BuildableRegionNode.Builder regionNode) {
		for (com.ibm.icu.util.Region rt : ((IcuRegion) regionNode.getRegion())
				.getIcuRegion().getContainedRegions()) {
			RegionType type = RegionType.of(rt.getType().name());
			Region region = regionProvider.getRegion(type,
					rt.toString());
			Builder nodeBuilder = new BuildableRegionNode.Builder(region);
			populateRegionNode(regionProvider, nodeBuilder);
			regionNode.addChildRegions(nodeBuilder.build());
		}
	}

	@Override
	public RegionTreeNode getRegionTree() {
		return regionTree;
	}

}