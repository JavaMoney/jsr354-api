/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Regions can be used to segregate or access artifacts (e.g. currencies) either
 * based on geographical, or commercial aspects (e.g. legal units).
 * 
 * @see <a href="http://unstats.un.org/unsd/methods/m49/m49regin.htm">UN M.49:
 *      UN Statistics Division Country or area & region codes</a>
 * 
 * @author Anatole Tresch
 */
public class Region implements Serializable, Comparable<Region> {

	/**
	 * serialID.
	 */
	private static final long serialVersionUID = -8957470024522944264L;

	/** The unique id of a region. */
	private String id;

	/** The region's type. */
	private RegionType regionType;

	/**
	 * The parent region, or {@code null}.
	 */
	private Region parent;

	private Set<Region> childRegions = new HashSet<Region>();

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
	public Region(String id, RegionType regionType) {
		this(id, regionType, null, null);
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
	public Region(String id, RegionType regionType, Region parent) {
		this(id, regionType, parent, null);
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
	public Region(String id, RegionType regionType, Region parent,
			Collection<Region> childRegions) {
		if (id == null) {
			throw new IllegalArgumentException("id is required.");
		}
		if (regionType == null) {
			throw new IllegalArgumentException("regionType is required.");
		}
		this.id = id;
		this.regionType = regionType;
		this.parent = parent;
		if (childRegions != null) {
			this.childRegions.addAll(childRegions);
		}
	}

	/**
	 * Access the region's identifier. The identifier is unique in combination
	 * with the region type.
	 * 
	 * @return the region's type, nrver null.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Get the region's type.
	 * 
	 * @return the region's type, never null.
	 */
	public RegionType getRegionType() {
		return this.regionType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Region o) {
		int compare = ((Comparable<RegionType>) this.regionType).compareTo(o
				.getRegionType());
		if (compare == 0) {
			compare = this.id.compareTo(o.getId());
		}
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((regionType == null) ? 0 : regionType.hashCode());
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
		Region other = (Region) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (regionType != other.regionType)
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
		return "Region [regionType=" + regionType + ", id=" + id + "]";
	}

	public boolean contains(Region region) {
		if (this.childRegions.contains(region)) {
			return true;
		}
		for (Region current : childRegions) {
			if (current.contains(region)) {
				return true;
			}
		}
		return false;
	}

	public Collection<Region> getChildren() {
		return Collections.unmodifiableCollection(childRegions);
	}

	public Region getParent() {
		return this.parent;
	}

	public Region getParent(RegionType type) {
		Region parent = this.parent;
		while (parent != null) {
			if (parent.getRegionType().equals(type)) {
				return parent;
			}
			parent = parent.getParent();
		}
		return null;
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

		/** The unique id of a region. */
		private String id;

		/** The region's type. */
		private RegionType regionType;

		/**
		 * The parent region, or {@code null}.
		 */
		private Region parent;

		private Set<Region> childRegions = new HashSet<Region>();

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
		public Builder(String id, RegionType regionType) {
			this.id = id;
			this.regionType = regionType;
		}

		/**
		 * Access the region's identifier. The identifier is unique in
		 * combination with the region type.
		 * 
		 * @return the region's type, nrver null.
		 */
		public String getId() {
			return this.id;
		}

		/**
		 * Get the region's type.
		 * 
		 * @return the region's type, never null.
		 */
		public RegionType getRegionType() {
			return this.regionType;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		public int compareTo(Region o) {
			int compare = ((Comparable<RegionType>) this.regionType)
					.compareTo(o.getRegionType());
			if (compare == 0) {
				compare = this.id.compareTo(o.getId());
			}
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
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result
					+ ((regionType == null) ? 0 : regionType.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.ext.Region#getChildRegions()
		 */
		public Set<Region> getChildRegions() {
			return childRegions;
		}

		public Region getParentRegion() {
			return this.parent;
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}

		public Builder setRegionType(RegionType type) {
			if (type == null) {
				throw new IllegalArgumentException("Type may not be null.");
			}
			this.regionType = type;
			return this;
		}

		public Builder addChildRegions(Region... regions) {
			this.childRegions.addAll(Arrays.asList(regions));
			return this;
		}

		public Builder removeChildRegions(Region... regions) {
			this.childRegions.removeAll(Arrays.asList(regions));
			return this;
		}

		public void clearChildren() {
			this.childRegions.clear();
		}

		public Builder setParentRegion(Region parent) {
			this.parent = parent;
			return this;
		}

		public boolean isBuildable() {
			return this.id != null && this.regionType != null;
		}

		public Region build() {
			return new Region(this.id, this.regionType, this.parent,
					this.childRegions);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "RegionBuilderImpl [id=" + id + ", regionType=" + regionType
					+ ", parent=" + parent + ", childRegions=" + childRegions
					+ "]";
		}

	}

	public Region getChild(String identifier) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public Region lookupRegion(RegionType type, String id2) {
	    throw new UnsupportedOperationException("Not yet implemented");
	}

}
