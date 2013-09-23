/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial implementation
 */
package org.javamoney.ext;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.javamoney.ext.Region;
import org.javamoney.ext.RegionTreeNode;

/**
 * {@link Region}s can be used to segregate or access artifacts (e.g.
 * CurrencyUnits) either based on geographical, or commercial aspects (e.g.
 * legal units). This implementation provides a class with an according
 * {@link Builder} for creation.
 * 
 * @see <a href="http://unstats.un.org/unsd/methods/m49/m49regin.htm">UN M.49:
 *      UN Statistics Division Country or area & region codes</a>
 * 
 * @author Anatole Tresch
 */
public class BuildableRegionNode extends AbstractRegionNode implements
		RegionTreeNode, Serializable,
		Comparable<RegionTreeNode> {

	/**
	 * serialID.
	 */
	private static final long serialVersionUID = -8957470024522944264L;

	/**
	 * Creates a region. Regions should only be accessed using the accessor
	 * method {@link Monetary#getExtension(Class)}, passing
	 * {@link RegionProvider} as type.
	 * 
	 * @param id
	 *            the region's id, not null.
	 * @param type
	 *            the region's type, not null.
	 */
	public BuildableRegionNode(Region region) {
		setRegion(region);
	}

	/**
	 * Creates a region. Regions should only be accessed using the accessor
	 * method {@link Monetary#getExtension(Class)}, passing
	 * {@link RegionProvider} as type.
	 * 
	 * @param id
	 *            the region's id, not null.
	 * @param type
	 *            the region's type, not null.
	 */
	public BuildableRegionNode(Region region, RegionTreeNode parent) {
		setRegion(region);
		setParent(parent);
	}

	/**
	 * Creates a region. Regions should only be accessed using the accessor
	 * method {@link Monetary#getExtension(Class)}, passing
	 * {@link RegionProvider} as type.
	 * 
	 * @param id
	 *            the region's id, not null.
	 * @param type
	 *            the region's type, not null.
	 */
	public BuildableRegionNode(Region region, RegionTreeNode parent,
			Collection<RegionTreeNode> childRegions) {
		setRegion(region);
		setParent(parent);
		if (childRegions != null) {
			getChildNodes().addAll(childRegions);
			for (RegionTreeNode regionNode : childRegions) {
				if (regionNode instanceof BuildableRegionNode) {
					((BuildableRegionNode) regionNode).setParent(this);
				}
			}
		}
	}

	/**
	 * Regions can be used to segregate or access artifacts (e.g. currencies)
	 * either based on geographical, or commercial aspects (e.g. legal units).
	 * 
	 * @see <a href="http://unstats.un.org/unsd/methods/m49/m49regin.htm">UN
	 *      M.49: UN Statistics Division Country or area & region codes</a>
	 * 
	 * @author Anatole Tresch
	 */
	public static class Builder {

		/** The region's type. */
		private Region region;

		/**
		 * The parent region, or {@code null}.
		 */
		private RegionTreeNode parent;

		private Set<RegionTreeNode> childRegions = new HashSet<RegionTreeNode>();

		/**
		 * Creates a {@link RegionBuilder}. Regions should only be accessed
		 * using the accessor method {@link Monetary#getExtension(Class)},
		 * passing {@link RegionProvider} as type.
		 * 
		 * @param regionCode
		 *            the region's id, not null.
		 * @param type
		 *            the region's type, not null.
		 */
		public Builder() {
		}

		/**
		 * Creates a {@link RegionBuilder}. Regions should only be accessed
		 * using the accessor method {@link Monetary#getExtension(Class)},
		 * passing {@link RegionProvider} as type.
		 * 
		 * @param id
		 *            the region's id, not null.
		 * @param type
		 *            the region's type, not null.
		 */
		public Builder(Region region) {
			this.region = region;
		}

		/**
		 * Access the region.
		 * 
		 * @return the region, never null.
		 */
		public Region getRegion() {
			return this.region;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		public int compareTo(BuildableRegionNode o) {
			int compare = ((Comparable<Region>) this.region).compareTo(o
					.getRegion());
			return compare;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((region == null) ? 0 : region.hashCode());
			return result;
		}

		public Set<RegionTreeNode> getChildRegions() {
			return childRegions;
		}

		public RegionTreeNode getParentRegion() {
			return this.parent;
		}

		public Builder setRegion(Region region) {
			if (region == null) {
				throw new IllegalArgumentException("Region may not be null.");
			}
			this.region = region;
			return this;
		}

		public Builder addChildRegions(RegionTreeNode... regions) {
			this.childRegions.addAll(Arrays.asList(regions));
			return this;
		}

		public Builder removeChildRegions(RegionTreeNode... regions) {
			this.childRegions.removeAll(Arrays.asList(regions));
			return this;
		}

		public void clearChildren() {
			this.childRegions.clear();
		}

		public Builder setParentRegion(RegionTreeNode parent) {
			this.parent = parent;
			return this;
		}

		public boolean isBuildable() {
			return this.region != null;
		}

		public BuildableRegionNode build() {
			return new BuildableRegionNode(this.region, this.parent,
					this.childRegions);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "RegionBuilderImpl [region=" + region + ", parent=" + parent
					+ ", childRegions=" + childRegions
					+ "]";
		}

	}

}
