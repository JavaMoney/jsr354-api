package javax.money;

import java.io.Serializable;

/**
 * Regions can be used to segregate or access artefacts (e.g. currencies) either
 * based on geographical, or commercial aspects (e.g. legal units).
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
	 * Creates a region. Regions should only be accessed using the accessor
	 * method on {@link MoneyUtil}. TODO link method here...
	 * 
	 * @param id
	 *            the region's id, not null.
	 * @param type
	 *            the region's type, not null.
	 */
	public Region(String id, RegionType regionType) {
		this.id = id;
		this.regionType = regionType;
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
		int compare = this.regionType.compareTo(o.regionType);
		if (compare == 0) {
			compare = this.id.compareTo(o.id);
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

}
