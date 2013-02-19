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

import java.util.Locale;
import java.util.ResourceBundle;

import javax.money.ext.LocalizableRegionType;
import javax.money.ext.RegionType;

/**
 * This class implements a {@link LocalizableRegionType} based on a
 * {@link RegionType} and a {@link ResourceBundle} basename.
 * 
 * @author Anatole Tresch
 * 
 */
public class ResourceBundleLocalizedRegionType implements LocalizableRegionType {

	private final RegionType base;
	private final String basename;

	public ResourceBundleLocalizedRegionType(RegionType base, String basename) {
		if (base == null) {
			throw new IllegalArgumentException("base required.");
		}
		if (basename == null) {
			throw new IllegalArgumentException("basename required.");
		}
		this.base = base;
		this.basename = basename;
	}

	@Override
	public String getId() {
		return base.getId();
	}

	@Override
	public String getDisplayName() {
		ResourceBundle bundle = ResourceBundle.getBundle(basename,
				Locale.getDefault());
		return bundle.getString("RegionType."+getId()+".displayname");
	}

	@Override
	public String getDisplayName(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
		return bundle.getString("RegionType."+getId()+".displayname");
	}

	public String getBasename() {
		return this.basename;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((base == null) ? 0 : base.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		ResourceBundleLocalizedRegionType other = (ResourceBundleLocalizedRegionType) obj;
		if (base == null) {
			if (other.base != null)
				return false;
		} else if (!base.equals(other.base))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResourceBundleLocalizedRegionType [base=" + base
				+ ", basename=" + basename + "]";
	}
	

}
