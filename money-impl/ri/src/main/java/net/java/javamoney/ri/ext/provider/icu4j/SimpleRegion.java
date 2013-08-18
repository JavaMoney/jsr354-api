package net.java.javamoney.ri.ext.provider.icu4j;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import javax.money.ext.Region;
import javax.money.ext.RegionType;

public final class SimpleRegion implements Region{

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
