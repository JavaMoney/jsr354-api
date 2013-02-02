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
package javax.money.ext.spi;

import java.util.ServiceLoader;

import javax.money.ext.Region;
import javax.money.ext.RegionType;

/**
 * Implementation of this interface define the regions supported in the system.
 * Each provider may hereby serve several region types.
 * <p>
 * Registration is done using the {@link ServiceLoader} features.
 * 
 * @author Anatole Tresch
 */
public interface RegionProviderSPI {

	/**
	 * Returns all {@link RegionType}s defined by this {@link RegionProviderSpi}
	 * instance.
	 * 
	 * @return the {@link RegionType}s to be defined.
	 */
	public RegionType[] getRegionTypes();

	/**
	 * Access a region.
	 * 
	 * @param identifier
	 *            The region's id.
	 * @param type
	 *            The required region type.
	 * @return The corresponding region, or null.
	 */
	public Region getRegion(String identifier, RegionType type);

	/**
	 * Access all regions provided for {@link RegionType} by this region
	 * provider.
	 * 
	 * @param type
	 *            The required region type.
	 * @return the regions to be added, not null.
	 */
	public Region[] getRegions(RegionType type);

	/**
	 * Access all regions provided by this region provider.
	 * 
	 * @return the regions to be added, not null.
	 */
	public Region[] getRegions();

}
