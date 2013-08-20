package net.java.javamoney.ri.ext.provider.icu4j;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.money.CurrencyUnit;
import javax.money.ext.CurrencyValidity;
import javax.money.ext.MonetaryCurrencies;
import javax.money.ext.Region;
import javax.money.ext.RelatedValidityInfo;

import net.java.javamoney.ri.ext.provider.icu4j.res.CLDRSupplementalData;
import net.java.javamoney.ri.ext.provider.icu4j.res.CLDRSupplementalData.Currency4Region;
import net.java.javamoney.ri.ext.provider.icu4j.res.CLDRSupplementalData.CurrencyRegionRecord;

public class CLDRCurrencyValidity implements CurrencyValidity {

	@Override
	public String getValiditySource() {
		return "CLDR";
	}

	@Override
	public Collection<RelatedValidityInfo<CurrencyUnit, Region>> getValidityInfo(
			Region region) {
		Currency4Region currencyData = CLDRSupplementalData.getInstance()
				.getCurrencyData(region.getRegionCode());
		List<RelatedValidityInfo<CurrencyUnit, Region>> result = new ArrayList<RelatedValidityInfo<CurrencyUnit, Region>>();
		for (CurrencyRegionRecord data : currencyData.getEntries()) {
			for (String tzName : region.getTimezoneIds()) {
				TimeZone tz = TimeZone.getTimeZone(tzName);
				int[] from = data.getFromYMD();
				Calendar fromCal = null;
				Calendar toCal = null;
				if (from != null) {
					fromCal = new GregorianCalendar(tz);
					fromCal.clear();
					fromCal.setTimeZone(tz);
					fromCal.set(from[0], from[1], from[2]);
				}
				int[] to = data.getToYMD();
				if (to != null) {
					toCal = new GregorianCalendar(tz);
					toCal.clear();
					toCal.setTimeZone(tz);
					toCal.set(to[0], to[1], to[2]);
				}
				RelatedValidityInfo<CurrencyUnit, Region> vi = new RelatedValidityInfo<CurrencyUnit, Region>(
						MonetaryCurrencies.get(data.getCurrencyCode()), region,
						"CLDR", fromCal, toCal, tzName);
				vi.setUserData(data);
				result.add(vi);
			}
		}
		return result;
	}

	@Override
	public Collection<RelatedValidityInfo<CurrencyUnit, Region>> getValidityInfo(
			Region region, long timestamp) {
		List<RelatedValidityInfo<CurrencyUnit, Region>> result = new ArrayList<RelatedValidityInfo<CurrencyUnit, Region>>();
		Collection<RelatedValidityInfo<CurrencyUnit, Region>> all = getValidityInfo(region);
		for (RelatedValidityInfo<CurrencyUnit, Region> vi : all) {
			if (vi.getFromTimeInMillis() != null
					&& vi.getFromTimeInMillis() > timestamp) {
				continue;
			}
			if (vi.getToTimeInMillis() != null
					&& vi.getToTimeInMillis() < timestamp) {
				continue;
			}
			result.add(vi);
		}
		return result;
	}

	@Override
	public boolean isLegalCurrencyUnit(CurrencyUnit currency, Region region) {
		return getLegalCurrencyUnits(region).contains(currency);
	}

	@Override
	public boolean isLegalCurrencyUnit(CurrencyUnit currency, Region region,
			long timestamp) {
		return getLegalCurrencyUnits(region, timestamp).contains(currency);
	}

	@Override
	public Collection<CurrencyUnit> getLegalCurrencyUnits(Region region) {
		Set<CurrencyUnit> result = new HashSet<CurrencyUnit>();
		Collection<RelatedValidityInfo<CurrencyUnit, Region>> all = getValidityInfo(region);
		for (RelatedValidityInfo<CurrencyUnit, Region> vi : all) {
			CurrencyRegionRecord rec = vi.getUserData();
			if (!(rec.isLegalTender())) {
				result.add(vi.getItem());
			}
		}
		return result;
	}

	@Override
	public <R> Collection<CurrencyUnit> getLegalCurrencyUnits(Region region,
			long timestamp) {
		Set<CurrencyUnit> result = new HashSet<CurrencyUnit>();
		Collection<RelatedValidityInfo<CurrencyUnit, Region>> all = getValidityInfo(
				region, timestamp);
		for (RelatedValidityInfo<CurrencyUnit, Region> vi : all) {
			CurrencyRegionRecord rec = vi.getUserData();
			if (!(rec.isLegalTender())) {
				result.add(vi.getItem());
			}
		}
		return result;
	}

}
