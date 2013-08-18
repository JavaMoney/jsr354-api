package net.java.javamoney.ri.ext.provider.icu4j;

import java.util.Map;

import javax.inject.Singleton;
import javax.money.ext.Region;
import javax.money.ext.RegionNode;
import javax.money.ext.RegionType;
import javax.money.ext.spi.RegionProviderSpi;
import javax.money.ext.spi.RegionTreeProviderSpi;

import net.java.javamoney.ri.ext.BuildableRegionNode;
import net.java.javamoney.ri.ext.BuildableRegionNode.Builder;

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
		com.ibm.icu.util.Region icuWorld = com.ibm.icu.util.Region.getAvailable(com.ibm.icu.util.Region.RegionType.WORLD).iterator().next();
		Region root = regionProvider.getRegion(RegionType.WORLD, icuWorld.toString());
		Builder treeBuilder = new BuildableRegionNode.Builder(root);
		populateRegionNode(regionProvider, treeBuilder);
		regionTree = treeBuilder.build();
	}

	private void populateRegionNode(ICURegionProvider regionProvider,
			BuildableRegionNode.Builder regionNode) {
		for (com.ibm.icu.util.Region rt : ((IcuRegion)regionNode.getRegion()).getIcuRegion().getContainedRegions()) {
			RegionType type = RegionType.of(rt.getType().name());
			Region region = regionProvider.getRegion(type,
					rt.toString());
			Builder nodeBuilder = new BuildableRegionNode.Builder(region);
			populateRegionNode(regionProvider, nodeBuilder);
			regionNode.addChildRegions(nodeBuilder.build());
		}
	}

	@Override
	public RegionNode getRegionTree() {
		return regionTree;
	}

}