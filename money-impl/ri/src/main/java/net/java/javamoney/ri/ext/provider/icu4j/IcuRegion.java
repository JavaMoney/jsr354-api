package net.java.javamoney.ri.ext.provider.icu4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import javax.money.Displayable;
import javax.money.ext.Region;
import javax.money.ext.RegionType;

import net.java.javamoney.ri.ext.provider.iso.IsoCountryMappingProvider;

import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.UResourceBundle;

public class IcuRegion implements Region, Displayable {

	private com.ibm.icu.util.Region icuRegion;
	private RegionType regionType;
	private Locale countryLocale;

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
		if(name==null){
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
		if(name==null){
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

	@Override
	public Locale getLocale() {
		return this.countryLocale;
	}

	
}
