/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial implementation. Werner Keil -
 * extension and adjustment.
 */
package org.javamoney.ext.provider.icu4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.javamoney.ext.Region;
import org.javamoney.ext.spi.ExtendedRegionDataProviderSpi;

/**
 * Implementation for {@link Region} based on ICU4J's
 * {@link com.ibm.icu.util.Region}.
 * 
 * @author Anatole Tresch
 * 
 */
public class IcuRegionExtRegionDataProvider implements
		ExtendedRegionDataProviderSpi {

	private static final List<Class> REGION_DATATYPES = Arrays
			.asList(new Class[] { IcuRegion.class });

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.Region#getRegionDataTypes()
	 */
	@Override
	public Collection<Class> getExtendedRegionDataTypes(Region region) {
		if (region instanceof IcuRegion) {
			return REGION_DATATYPES;
		}
		return Collections.emptySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.Region#getRegionData(java.lang.Class)
	 */
	@Override
	public <T> T getExtendedRegionData(Region region, Class<T> type) {
		if (IcuRegion.class.equals(type) && region instanceof IcuRegion) {
			return (T) region;
		}
		throw new IllegalArgumentException("Unsupported data type for " + this
				+ ", use one of " + getExtendedRegionDataTypes(region));
	}

}
