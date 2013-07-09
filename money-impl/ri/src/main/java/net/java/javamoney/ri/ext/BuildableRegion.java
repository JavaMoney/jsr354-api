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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.money.ext.Region;
import javax.money.ext.RegionFilter;
import javax.money.ext.RegionType;


/**
 * Regions can be used to segregate or access artifacts (e.g. currencies) either
 * based on geographical, or commercial aspects (e.g. legal units).
 * 
 * @see <a href="http://unstats.un.org/unsd/methods/m49/m49regin.htm">UN M.49:
 *      UN Statistics Division Country or area & region codes</a>
 * 
 * @author Anatole Tresch
 */
public class BuildableRegion implements Region, Serializable, Comparable<Region> {

    /**
     * serialID.
     */
    private static final long serialVersionUID = -8957470024522944264L;

    /** The region code of a region, unique within a region type. */
    private String regionCode;
    /**
     * The optional numeric code of a region, unique within a region type, if
     * defined.
     */
    private int numericCode;

    /** The region's type. */
    private RegionType regionType;

    /**
     * The parent region, or {@code null}.
     */
    private BuildableRegion parent;

    private Set<Region> childRegions = new HashSet<>();

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
    public BuildableRegion(String id, RegionType regionType) {
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
    public BuildableRegion(String id, RegionType regionType, BuildableRegion parent) {
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
    public BuildableRegion(String id, RegionType regionType, BuildableRegion parent,
	    Collection<BuildableRegion> childRegions) {
	if (id == null) {
	    throw new IllegalArgumentException("id is required.");
	}
	if (regionType == null) {
	    throw new IllegalArgumentException("regionType is required.");
	}
	this.regionCode = id;
	this.regionType = regionType;
	this.parent = parent;
	if (childRegions != null) {
	    this.childRegions.addAll(childRegions);
	}
    }

    /**
     * Access the region's code. The code is unique in combination with the
     * region type.
     * 
     * @return the region's type, never null.
     */
    public String getRegionCode() {
	return this.regionCode;
    }

    /**
     * Get the region's numeric code. If not defined -1 is returned.
     * 
     * @return the numeric region ode, or -1.
     */
    public int getNumericRegionCode() {
	return this.numericCode;
    }

    /**
     * Get the region's type.
     * 
     * @return the region's type, never null.
     */
    public RegionType getRegionType() {
	return this.regionType;
    }

    @Override
    public Region getRegionByPath(String path) {
	throw new UnsupportedOperationException("Not yet implemented.");
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Region o) {
	int compare = ((Comparable<RegionType>) this.regionType).compareTo(o.getRegionType());
	if (compare == 0) {
	    compare = this.regionCode.compareTo(o.getRegionCode());
	}
	return compare;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((regionCode == null) ? 0 : regionCode.hashCode());
	result = prime * result + ((regionType == null) ? 0 : regionType.hashCode());
	return result;
    }

    /*
     * (non-Javadoc)
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
	BuildableRegion other = (BuildableRegion) obj;
	if (regionCode == null) {
	    if (other.regionCode != null)
		return false;
	} else if (!regionCode.equals(other.regionCode))
	    return false;
	if (regionType != other.regionType)
	    return false;
	return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Region [regionType=" + regionType + ", regionCode=" + regionCode + ", numericCode=" + numericCode + "]";
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

    public Collection<Region> select(RegionFilter filter) {
	return Collections.unmodifiableCollection(childRegions);
    }

    public Region getParent() {
	return this.parent;
    }

    public Region selectParent(RegionFilter filter) {
	Region parent = this.parent;
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

	/** The unique id of a region. */
	private String id;

	/** The region's type. */
	private RegionType regionType;

	/**
	 * The parent region, or {@code null}.
	 */
	private BuildableRegion parent;

	private Set<BuildableRegion> childRegions = new HashSet<BuildableRegion>();

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
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(BuildableRegion o) {
	    int compare = ((Comparable<RegionType>) this.regionType).compareTo(o.getRegionType());
	    if (compare == 0) {
		compare = this.id.compareTo(o.getRegionCode());
	    }
	    return compare;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    result = prime * result + ((regionType == null) ? 0 : regionType.hashCode());
	    return result;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.ext.Region#getChildRegions()
	 */
	public Set<BuildableRegion> getChildRegions() {
	    return childRegions;
	}

	public BuildableRegion getParentRegion() {
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

	public Builder addChildRegions(BuildableRegion... regions) {
	    this.childRegions.addAll(Arrays.asList(regions));
	    return this;
	}

	public Builder removeChildRegions(BuildableRegion... regions) {
	    this.childRegions.removeAll(Arrays.asList(regions));
	    return this;
	}

	public void clearChildren() {
	    this.childRegions.clear();
	}

	public Builder setParentRegion(BuildableRegion parent) {
	    this.parent = parent;
	    return this;
	}

	public boolean isBuildable() {
	    return this.id != null && this.regionType != null;
	}

	public BuildableRegion build() {
	    return new BuildableRegion(this.id, this.regionType, this.parent, this.childRegions);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	    return "RegionBuilderImpl [id=" + id + ", regionType=" + regionType + ", parent=" + parent
		    + ", childRegions=" + childRegions + "]";
	}

    }

}
