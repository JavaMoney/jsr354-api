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
package net.java.javamoney.ri.ext;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.money.ext.Region;
import javax.money.ext.RegionFilter;
import javax.money.ext.RegionNode;

/**
 * Regions can be used to segregate or access artifacts (e.g. currencies) either
 * based on geographical, or commercial aspects (e.g. legal units).
 * 
 * @see <a href="http://unstats.un.org/unsd/methods/m49/m49regin.htm">UN M.49:
 *      UN Statistics Division Country or area & region codes</a>
 * 
 * @author Anatole Tresch
 */
public class BuildableRegionNode implements RegionNode, Serializable,
		Comparable<RegionNode> {

	/**
	 * serialID.
	 */
	private static final long serialVersionUID = -8957470024522944264L;

	private Region region;

	/**
	 * The parent region, or {@code null}.
	 */
	private RegionNode parent;

	private Set<RegionNode> childRegions = new HashSet<>();

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
		this(region, null, null);
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
	public BuildableRegionNode(Region region, RegionNode parent) {
		this(region, parent, null);
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
	public BuildableRegionNode(Region region, RegionNode parent,
			Collection<RegionNode> childRegions) {
		if (region == null) {
			throw new IllegalArgumentException("region is required.");
		}
		this.region = region;
		this.parent = parent;
		if (childRegions != null) {
			this.childRegions.addAll(childRegions);
			for (RegionNode regionNode : childRegions) {
				if (regionNode instanceof BuildableRegionNode) {
					((BuildableRegionNode) regionNode).parent = this;
				}
			}
		}
	}

	/**
	 * Access the region.
	 * 
	 * @return the node's region, never null.
	 */
	public Region getRegion() {
		return this.region;
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
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuildableRegionNode other = (BuildableRegionNode) obj;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BuildableRegionNode [region=" + region + "]";
	}

	public String getAsText() {
		return getAsText("");
	}

	public String getAsText(String intend) {
		StringBuilder b = new StringBuilder();
		try {
			printTree(b, intend);
		} catch (IOException e) {
			e.printStackTrace(); // TODO
			b.append("Error: " + e);
		}
		return b.toString();
	}

	public void printTree(Appendable b, String intend) throws IOException {
		b.append(intend + toString()).append("\n");
		intend = intend + "  ";
		for (RegionNode region : getChildren()) {
			region.printTree(b, intend);
		}
	}

	public boolean contains(Region region) {
		for (RegionNode current : childRegions) {
			if (current.getRegion().equals(region)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public RegionNode getSubRegion(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<RegionNode> getChildren() {
		return Collections.unmodifiableCollection(childRegions);
	}

	public Collection<RegionNode> select(RegionFilter filter) {
		return Collections.unmodifiableCollection(childRegions);
	}

	public RegionNode getParent() {
		return this.parent;
	}

	public RegionNode selectParent(RegionFilter filter) {
		RegionNode parent = this.parent;
		while (parent != null) {
			if (filter.accept(parent)) {
				return parent;
			}
			parent = parent.getParent();
		}
		return null;
	}

	public Region getRegionByCode(String code) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public Region getRegionByNumericCode(int code) {
		throw new UnsupportedOperationException("Not yet implemented");
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
		private RegionNode parent;

		private Set<RegionNode> childRegions = new HashSet<RegionNode>();

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

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.ext.Region#getChildRegions()
		 */
		public Set<RegionNode> getChildRegions() {
			return childRegions;
		}

		public RegionNode getParentRegion() {
			return this.parent;
		}

		public Builder setRegion(Region region) {
			if (region == null) {
				throw new IllegalArgumentException("Region may not be null.");
			}
			this.region = region;
			return this;
		}

		public Builder addChildRegions(RegionNode... regions) {
			this.childRegions.addAll(Arrays.asList(regions));
			return this;
		}

		public Builder removeChildRegions(RegionNode... regions) {
			this.childRegions.removeAll(Arrays.asList(regions));
			return this;
		}

		public void clearChildren() {
			this.childRegions.clear();
		}

		public Builder setParentRegion(RegionNode parent) {
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

	@Override
	public RegionNode getRegionTree(String path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int compareTo(RegionNode o) {
		return ((Comparable<Region>) this.region).compareTo(o.getRegion());
	}

}
