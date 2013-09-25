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
import java.util.List;
import java.util.Locale;

import org.javamoney.ext.Displayable;
import org.javamoney.ext.Region;
import org.javamoney.ext.RegionType;

import com.ibm.icu.util.TimeZone;

/**
 * Implementation for {@link Region} based on ICU4J's
 * {@link com.ibm.icu.util.Region}.
 * 
 * @author Anatole Tresch
 * 
 */
public class IcuRegion implements Region, Displayable {

	private com.ibm.icu.util.Region icuRegion;
	private RegionType regionType;
	private Locale countryLocale;
	private static final List<Class> REGION_DATATYPES = Arrays
			.asList(new Class[] { IcuRegion.class });

	public IcuRegion(com.ibm.icu.util.Region icuRegion, RegionType regionType) {
		if (icuRegion == null) {
			throw new IllegalArgumentException("icuRegion required.");
		}
		this.icuRegion = icuRegion;
		this.regionType = regionType;
		this.countryLocale = new Locale("", icuRegion.toString());
	}

	@Override
	public RegionType getRegionType() {
		return regionType;
	}

	@Override
	public String getRegionCode() {
		// returns region id!
		return icuRegion.toString();
	}

	@Override
	public int getNumericRegionCode() {
		return icuRegion.getNumericCode();
	}

	@Override
	public Collection<String> getTimezoneIds() {
		String[] timezones = TimeZone.getAvailableIDs(getRegionCode());
		return Arrays.asList(timezones);
	}

	@Override
	public String getDisplayName(Locale locale) {
		String name = this.countryLocale.getDisplayCountry(locale);
		if (name == null) {
			name = this.getRegionCode();
		}
		return name;
	}

	public com.ibm.icu.util.Region getIcuRegion() {
		return this.icuRegion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String name = getDisplayName(Locale.getDefault());
		if (name == null) {
			name = "IcuRegion";
		}
		return name + " [code: " + icuRegion.toString() + ", type: "
				+ regionType.getId() + "]";
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
		result = prime * result
				+ ((icuRegion == null) ? 0 : icuRegion.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		IcuRegion other = (IcuRegion) obj;
		if (icuRegion == null) {
			if (other.icuRegion != null) {
				return false;
			}
		} else if (!icuRegion.equals(other.icuRegion)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.Region#getLocale()
	 */
	@Override
	public Locale getLocale() {
		return this.countryLocale;
	}


}
