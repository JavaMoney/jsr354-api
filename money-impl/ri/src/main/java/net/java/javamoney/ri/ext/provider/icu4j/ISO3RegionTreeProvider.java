package net.java.javamoney.ri.ext.provider.icu4j;

import java.util.Locale;
import java.util.Map;

import javax.inject.Singleton;
import javax.money.ext.Region;
import javax.money.ext.RegionTreeNode;
import javax.money.ext.RegionType;
import javax.money.ext.spi.RegionProviderSpi;
import javax.money.ext.spi.RegionTreeProviderSpi;

import net.java.javamoney.ri.ext.BuildableRegionNode;
import net.java.javamoney.ri.ext.BuildableRegionNode.Builder;

@Singleton
public class ISO3RegionTreeProvider implements RegionTreeProviderSpi {

	private BuildableRegionNode regionTree;

	// ISO3/...

	@Override
	public String getTreeId() {
		return "ISO3";
	}

	@Override
	public void init(Map<Class, RegionProviderSpi> providers) {
		Builder treeBuilder = new BuildableRegionNode.Builder(new SimpleRegion("ISO3"));
		ISORegionProvider regionProvider = (ISORegionProvider) providers
				.get(ISORegionProvider.class);
		for (String country : Locale.getISOCountries()) {
			Locale locale = new Locale("", country);
			Region region = regionProvider.getRegion(RegionType.of("ISO"),
					locale.getISO3Country());
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