/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * Contributors:
 *    Anatole Tresch - initial version.
 */
package javax.money.ext;

import java.util.Enumeration;

/**
 * Regions can be used to segregate or access artifacts (e.g. currencies) either
 * based on geographical, or commercial aspects (e.g. legal units).
 * 
 * @see <a href="http://unstats.un.org/unsd/methods/m49/m49regin.htm">UN M.49:
 *      UN Statistics Division Country or area & region codes</a>
 * 
 * @author Anatole Tresch
 */
public interface Region {

	/**
	 * Access the region's identifier. The identifier is unique in combination
	 * with the region type.
	 * 
	 * @return the region's type, never {@code null}.
	 */
	public String getId();

	/**
	 * Get the region's type.
	 * 
	 * @return the region's type, never {@code null}.
	 */
	public RegionType getRegionType();

	boolean contains(Region other);

	/**
	 * access the child regions of this region.
	 * 
	 * @return the child regions, never {@code null}.
	 */
	public Enumeration<Region> getChildRegions();

	/**
	 * Access all direct (non-recursive) child regions of this region, hereby
	 * filtering for the given type only.
	 * 
	 * @param type
	 *            the required type, not {@code null}.
	 */
	public Enumeration<Region> getChildRegions(RegionType type);

	/**
	 * Access the parent region of the give region.
	 * 
	 * @return the parent region, or {@code null} if this region is a root
	 *         region.
	 */
	public Region getParentRegion();

	/**
	 * Access the direct child region of the given type. If multiple parent
	 * regions match, the nearest regions relative to this region is returned.
	 * 
	 * @param type
	 *            the target RegionType
	 * @return the parent region that matches the given type, or {@code null}.
	 */
	public Region getParentRegion(RegionType type);

	/**
	 * Access the direct or indirect parent region of the given type. If
	 * multiple parent regions match, the nearest regions relative to this
	 * region is returned.
	 * 
	 * @param type
	 *            the target RegionType
	 * @param recursive
	 *            if true, all child regions are checked for inclusion
	 *            recursively.
	 * @return the parent region that matches the given type, or {@code null}.
	 */
	public Enumeration<Region> getChildRegions(RegionType type,
			boolean recursive);

}
