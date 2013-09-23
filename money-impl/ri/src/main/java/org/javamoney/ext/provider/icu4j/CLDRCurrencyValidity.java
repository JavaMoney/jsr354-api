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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.money.CurrencyUnit;

import org.javamoney.ext.MonetaryCurrencies;
import org.javamoney.ext.Region;
import org.javamoney.ext.RelatedValidityInfo;
import org.javamoney.ext.RelatedValidityQuery;
import org.javamoney.ext.ValidityType;
import org.javamoney.ext.provider.icu4j.res.CLDRSupplementalData;
import org.javamoney.ext.provider.icu4j.res.CLDRSupplementalData.Currency4Region;
import org.javamoney.ext.provider.icu4j.res.CLDRSupplementalData.CurrencyRegionRecord;
import org.javamoney.ext.spi.RelatedValidityProviderSpi;


/**
 * This class provides {@link RelatedValidityInfo} based on the CLDC
 * SupplementalData. It covers the relationship between ISO currencies and
 * territories (countries).
 * 
 * @author Anatole Tresch
 */
public class CLDRCurrencyValidity implements RelatedValidityProviderSpi {
	/**
	 * The validity types supported, current
	 * {@code ValidityType.EXISTENCE, ValidityType.of("legal")}.
	 */
	private static final Set<ValidityType> FLAVORS = Collections
			.unmodifiableSet(new HashSet<ValidityType>(
					Arrays.asList(new ValidityType[] { ValidityType.EXISTENCE,
							ValidityType.of("legal") })));
	/**
	 * The item types supported, currently {@link CurrencyUnit}.
	 */
	@SuppressWarnings("rawtypes")
	private static final Set<Class> ITEM_TYPES = Collections
			.unmodifiableSet(new HashSet<Class>(
					Arrays.asList(new Class[] { CurrencyUnit.class })));
	/**
	 * The related types supported, currently {@link Region}.
	 */
	@SuppressWarnings("rawtypes")
	private static final Set<Class> RELATED_ITEM_TYPES = Collections
			.unmodifiableSet(new HashSet<Class>(
					Arrays.asList(new Class[] { Region.class })));

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.spi.RelatedValidityProviderSpi#getProviderId()
	 */
	@Override
	public String getProviderId() {
		return "CLDR";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.spi.RelatedValidityProviderSpi#getValidityTypes()
	 */
	@Override
	public Set<ValidityType> getValidityTypes(Class itemType, Class relatedType) {
		if (itemType.equals(CurrencyUnit.class)
				&& relatedType.equals(Region.class)) {
			return FLAVORS;
		}
		return Collections.emptySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.spi.RelatedValidityProviderSpi#getItemTypes()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Set<Class> getItemTypes() {
		return ITEM_TYPES;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.ext.spi.RelatedValidityProviderSpi#getRelatedItemTypes(java
	 * .lang.Class)
	 */
	@Override
	public Set<Class> getRelatedItemTypes(Class itemType) {
		if (itemType == null) {
			throw new IllegalArgumentException("itemType required.");
		}
		if (!ITEM_TYPES.contains(itemType)) {
			throw new IllegalArgumentException("Invalid item type for " + this
					+ ": " + itemType);
		}
		// Only one item type (CurrencyUnit) is supported, so just return our
		// static list...
		return RELATED_ITEM_TYPES;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.ext.spi.RelatedValidityProviderSpi#getRelatedValidityInfo
	 * (javax.money.ext.RelatedValidityQuery)
	 */
	@Override
	public <T, R> Collection<RelatedValidityInfo<T, R>> getRelatedValidityInfo(
			RelatedValidityQuery<T, R> query) {
		if (!query.getItemType().equals(CurrencyUnit.class)
				|| !(query.getRelatedToType().equals((Region.class)))) {
			return Collections.emptySet();
		}
		if (!this.FLAVORS.contains(query.getValidityType())) {
			return Collections.emptySet();
		}
		return getRelatedValidityInfoInternal(
				(CurrencyUnit) query.getItem(),
				query);
	}

	/**
	 * Method that finally collects, filters and evaluates the
	 * {@link Currency4Region} instances returned by
	 * {@link CLDRSupplementalData} to return the appropriate result for
	 * {@code query}.
	 * 
	 * @param currency
	 *            The acquired currency
	 * @param query
	 *            The {@link RelatedValidityQuery} instance
	 * @return the {@link RelatedValidityInfo} instances matching the
	 *         {@code query}.
	 */
	@SuppressWarnings("unchecked")
	protected <T, R> Collection<RelatedValidityInfo<T, R>> getRelatedValidityInfoInternal(
			CurrencyUnit currency, RelatedValidityQuery<T, R> query) {
		Collection<Currency4Region> regionData = CLDRSupplementalData
				.getInstance()
				.getAllCurrencyData();
		regionData = filterRequestedRegions(regionData, query);
		List<RelatedValidityInfo<T, R>> result = new ArrayList<RelatedValidityInfo<T, R>>();
		for (Currency4Region currencyData : regionData) {
			for (CurrencyRegionRecord data : currencyData.getEntries()) {
				if (query.getValidityType().equals(ValidityType.of("legal")) &&
						!data.isLegalTender()) {
					continue;
				}
				if (currency != null
						&& !(data.getCurrencyCode().equals(
								currency.getCurrencyCode()))) {
					continue;
				}
				for (String tzName : currencyData.getRegion().getTimezoneIds()) {
					TimeZone tz = TimeZone.getTimeZone(tzName);
					if (tz.getID().equals("GMT")) {
						// the timezone was not recognized by the JDK, ignore
						// it!
						// TODO think about a more intelligent variant here...
						continue;
					}
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
					RelatedValidityInfo<T, R> vi = new RelatedValidityInfo<T, R>(
							(T) MonetaryCurrencies.get(data.getCurrencyCode()),
							(R) currencyData.getRegion(),
							query.getValidityType(),
							"CLDR", fromCal, toCal, tzName, data);
					result.add(vi);
				}
			}
		}
		result = filterRequestedTimeRange(result, query);
		return result;
	}

	/**
	 * Filter out the items which do not match the query's RelatedTo predicate
	 * (if any).
	 * 
	 * @param regionData
	 *            the CLDR data items.
	 * @param query
	 *            the query.
	 * @return the filered collection for further processing.
	 */
	private <T, R> Collection<Currency4Region> filterRequestedRegions(
			Collection<Currency4Region> regionData,
			RelatedValidityQuery<T, R> query) {
		if (query.getRelatedToPredicate() == null) {
			return regionData;
		}
		List<Currency4Region> filtered = new ArrayList<Currency4Region>();
		for (Currency4Region currency4Region : regionData) {
			Region region = currency4Region.getRegion();
			if (region == null) {
				continue;
			}
			if (query.getRelatedToPredicate().isPredicateTrue(
					(R) region)) {
				filtered.add(currency4Region);
			}
		}
		return filtered;
	}

	/**
	 * Filter out the items which do not match the query's RelatedTo predicate
	 * (if any).
	 * 
	 * @param validities
	 *            the validities prepared so far.
	 * @param query
	 *            the query.
	 * @return the filtered collection matching the required time range.
	 */
	private <T, R> List<RelatedValidityInfo<T, R>> filterRequestedTimeRange(
			List<RelatedValidityInfo<T, R>> validities,
			RelatedValidityQuery<T, R> query) {
		if (query.isTimeUnbounded()) {
			return validities;
		}
		List<RelatedValidityInfo<T, R>> filtered = new ArrayList<RelatedValidityInfo<T, R>>();
		for (RelatedValidityInfo<T, R> info : validities) {
			Long from = query.getFrom();
			if (from != null && info.getFromTimeInMillis() != null
					&& from > info.getFromTimeInMillis()) {
				continue;
			}
			Long to = query.getTo();
			if (to != null && info.getToTimeInMillis() != null
					&& to < info.getToTimeInMillis()) {
				continue;
			}
			filtered.add(info);
		}
		return filtered;
	}

}
