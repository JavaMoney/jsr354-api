package net.java.javamoney.ri.ext.provider.icu;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import javax.money.ext.Region;
import javax.money.ext.RegionType;

import com.ibm.icu.util.TimeZone;

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
		return com.ibm.icu.util.Region.getInstance(getRegionCode()).getNumericCode();
	}

	@Override
	public Collection<String> getTimezoneIds() {
		return Arrays.asList(TimeZone.getAvailableIDs(locale.getCountry()));
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return locale.getDisplayName() + " [ISO: code=" + locale.getCountry() + '/' + locale.getISO3Country() + ", regionType=" + regionType.getId()
				+ "]";
	}

}
