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
package net.java.javamoney.ri.ext.spi;

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
public interface RegionProviderSpi {

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
