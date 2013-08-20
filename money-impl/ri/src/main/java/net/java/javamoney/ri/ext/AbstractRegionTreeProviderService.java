/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial version. Wernner Keil - extensions and
 * adaptions.
 */
package net.java.javamoney.ri.ext;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.money.ext.Region;
import javax.money.ext.RegionTreeNode;
import javax.money.ext.spi.RegionProviderSpi;
import javax.money.ext.spi.RegionTreeProviderSpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link RegionImpl}.
 * 
 * @author Anatole Tresch
 */
public abstract class AbstractRegionTreeProviderService {

	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractRegionTreeProviderService.class);

	/**
	 * Get the id of the forest provided by this provider.
	 * 
	 * @return
	 */
	public Set<String> getRegionTreeIds() {
		Set<String> result = new HashSet<String>();
		for (RegionTreeProviderSpi prov : getRegionTreeProviderSpis()) {
			String forestId = prov.getTreeId();
			if (forestId == null || forestId.isEmpty()) {
				LOG.warn("Provider did not provide a valid forestId: "
						+ prov.getClass().getName());
			} else {
				result.add(forestId);
			}
		}
		return result;
	}

	/**
	 * Initialize the {@link RegionTreeProviderSpi} provider.
	 * 
	 * @param regionProviders
	 *            the region providers loaded, to be used for accessing
	 *            {@link Region} entries.
	 */
	public void init(Map<Class, RegionProviderSpi> regionProviders) {
		for (RegionTreeProviderSpi prov : getRegionTreeProviderSpis()) {
			try {
				prov.init(regionProviders);
			} catch (Exception e) {
				LOG.error("Error initializing RegionProviderSpi: "
						+ prov.getClass().getName(), e);
			}
		}
	}

	/**
	 * Access a set of Region instances that are defined to be graph root
	 * regions, which are identifiable entry points into the region graph.
	 * 
	 * @return the root graph {@link Region}s defined by this spi, not null.
	 */
	public RegionTreeNode getRegionTree(String treeId) {
		for (RegionTreeProviderSpi prov : getRegionTreeProviderSpis()) {
			try {
				if (treeId.equals(prov.getTreeId())) {
					RegionTreeNode node = prov.getRegionTree();
					if (node == null) {
						LOG.error("Error accessing RegionTree: " + treeId
								+ " from " + prov.getClass().getName()
								+ ": provider returned null.");
						return null;
					}
					return node;
				}
			} catch (Exception e) {
				LOG.error("Error initializing RegionTreeProviderSpi: "
						+ prov.getClass().getName(), e);
			}
		}
		return null;
	}

	protected abstract Iterable<RegionTreeProviderSpi> getRegionTreeProviderSpis();

}
