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

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import org.javamoney.ext.Region;
import org.javamoney.ext.RegionType;

/**
 * A simple implementation of {@link Region}.
 * 
 * @author Anatole Tresch
 */
public final class SimpleRegion implements Region {

	private String code;
	private int numericCode = -1;
	private RegionType regionType = RegionType.UNKNOWN;
	private Collection<String> timezones = Collections.emptySet();
	private Locale locale = Locale.ROOT;

	public SimpleRegion(String code) {
		// TODO check input
		this.code = code;
	}

	// TODO add alternate constructors...

	@Override
	public RegionType getRegionType() {
		return regionType;
	}

	@Override
	public String getRegionCode() {
		return code;
	}

	@Override
	public int getNumericRegionCode() {
		return numericCode;
	}

	@Override
	public Collection<String> getTimezoneIds() {
		return timezones;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

}
