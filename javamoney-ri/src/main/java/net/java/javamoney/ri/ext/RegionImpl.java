/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial implementation
 */
package net.java.javamoney.ri.ext;

import javax.money.Monetary;
import javax.money.ext.Region;
import javax.money.ext.RegionType;

/**
 * Regions can be used to segregate or access artefacts (e.g. currencies) either
 * based on geographical, or commercial aspects (e.g. legal units).
 * 
 * @see <a
 *      href="http://unstats.un.org/unsd/methods/m49/m49regin.htm">UN M.49: UN Statistics Division
Country or area & region codes</a>
 * 
 * @author Anatole Tresch
 */
public class RegionImpl implements Region {
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
	 * method on {@link Monetary}. TODO link method here...
	 * 
	 * @param id
	 *            the region's id, not null.
	 * @param type
	 *            the region's type, not null.
	 */
	public RegionImpl(String id, RegionType regionType) {
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
		int compare = this.regionType.compareTo(o.getRegionType());
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
		RegionImpl other = (RegionImpl) obj;
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
