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

import javax.money.provider.MonetaryExtension;

/**
 * This class models the component defined by JSR 354 that provides accessors
 * for {@link Region}. It is provided by the Money singleton.
 * 
 * @author Anatole Tresch
 */
public interface RegionProvider extends MonetaryExtension {

	/**
	 * Access all types of regions available.
	 * 
	 * @return all {@link RegionType}s available, never null.
	 */
	public RegionType[] getRegionTypes();

	/**
	 * Access all regions available, that have no parent region.
	 * 
	 * @return all {@link Region}s available without a parent, never null.
	 */
	public Region[] getRootRegions();

	/**
	 * Access a region.
	 * 
	 * @param identifier
	 *            The region's id, not null.
	 * @param type
	 *            The region type, not null.
	 * @return the region instance.
	 * @throws IllegalArgumentException
	 *             if the region does not exist.
	 */
	public Region get(String identifier, RegionType type);

	/**
	 * Access all regions for a given {@link RegionType}.
	 * 
	 * @param type
	 *            The region type, not null.
	 * @return the regions found, never null.
	 */
	public Region[] getAll(RegionType type);

	/**
	 * Access all regions.
	 * 
	 * @return the regions found, never null.
	 */
	public Region[] getAll();

}
