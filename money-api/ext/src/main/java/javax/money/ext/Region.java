/*
 * Copyright (c) 2012-2013, Credit Suisse
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
