package net.java.javamoney.ri.ext.provider.icu;

import java.util.Locale;
import java.util.Map;

import javax.inject.Singleton;
import javax.money.ext.Region;
import javax.money.ext.RegionNode;
import javax.money.ext.RegionType;
import javax.money.ext.spi.RegionTreeProviderSpi;
import javax.money.ext.spi.RegionProviderSpi;

import net.java.javamoney.ri.ext.BuildableRegionNode;
import net.java.javamoney.ri.ext.BuildableRegionNode.Builder;

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
		Builder treeBuilder = new BuildableRegionNode.Builder(new SimpleRegion("ISO"));
		
		ISORegionProvider regionProvider = (ISORegionProvider) providers.get(ISORegionProvider.class);
		for (String country : Locale.getISOCountries()) {
			Region region = regionProvider.getRegion(RegionType.of("ISO"), country);
			Builder nodeBuilder = new BuildableRegionNode.Builder(region);
			treeBuilder.addChildRegions(nodeBuilder.build());
		}
		regionTree = treeBuilder.build();
	}

	@Override
	public RegionNode getRegionTree() {
		return regionTree;
	}


}