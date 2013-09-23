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
import java.util.Locale;

import org.javamoney.ext.Region;
import org.javamoney.ext.RegionType;

import com.ibm.icu.util.TimeZone;

/**
 * Implementation of a {@link Region} based on {@link Locale}, using the ISO
 * 2-letter code as Region code.
 * 
 * @author Anatole Tresch
 */
public class ISOCountry implements Region {

	private Locale locale;
	private RegionType regionType;

	public ISOCountry(Locale locale, RegionType rt) {
		this.locale = locale;
		this.regionType = rt;
	}

	@Override
	public RegionType getRegionType() {
		return regionType;
	}

	@Override
	public String getRegionCode() {
		return locale.getCountry();
	}

	public String getISO3Code() {
		return locale.getISO3Country();
	}

	@Override
	public int getNumericRegionCode() {
		return com.ibm.icu.util.Region.getInstance(getRegionCode())
				.getNumericCode();
	}

	@Override
	public Collection<String> getTimezoneIds() {
		return Arrays.asList(TimeZone.getAvailableIDs(locale.getCountry()));
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return locale.getDisplayName() + " [ISO: code=" + locale.getCountry()
				+ '/' + locale.getISO3Country() + ", regionType="
				+ regionType.getId()
				+ "]";
	}

}
