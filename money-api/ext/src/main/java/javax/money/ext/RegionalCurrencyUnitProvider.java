/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.provider.MonetaryExtension;

/**
 * This class models the component defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit} using {@link Region}s. It is provided by the
 * Monetary singleton.
 * 
 * @author Anatole Tresch
 */
public interface RegionalCurrencyUnitProvider extends MonetaryExtension {

	/**
	 * Access all currencies matching a {@link Region}.
	 * 
	 * @param locale
	 *            the target locale, not null.
	 * @return the currencies found, never null.
	 */
	public Set<CurrencyUnit> getAll(Region region);

	/**
	 * Access all currencies matching a {@link Region}, valid at the given
	 * timestamp.
	 * 
	 * @param locale
	 *            the target locale, not null.
	 * @param timestamp
	 *            The target UTC timestamp, or -1 for the current UTC timestamp.
	 * @return the currencies found, never null.
	 */
	public Set<CurrencyUnit> getAll(Region region, long timestamp);

	/**
	 * This method allows to evaluate if a {@link CurrencyUnit} is a legal
	 * tender for a certain region, or country. For example Indian rupee are
	 * accepted also as legal tender in Nepal and Buthan, whereas Nepalese rupee
	 * or Bhutanese ngultrum are not accepted as legal tender in India.
	 * 
	 * @param currency
	 *            The currency to be requested, not null.
	 * @param region
	 *            The region to be requested, not null.
	 * @return true if the currency is accepted as legal tender in the current
	 *         region.
	 */
	public boolean isLegalTCurrencyUnit(CurrencyUnit currency, Region region);

	/**
	 * This method allows to evaluate if a {@link CurrencyUnit} is a legal
	 * tender for a certain region, or country. For example Indian rupee are
	 * accepted also as legal tender in Nepal and Buthan, whereas Nepalese rupee
	 * or Bhutanese ngultrum are not accepted as legal tender in India.
	 * 
	 * @param currency
	 *            The currency to be requested, not null.
	 * @param region
	 *            The region to be requested, not null.
	 * @param timestamp
	 *            the UTC timestamp, or null for the current time.
	 * @return true if the currency is accepted as legal tender in the current
	 *         region.
	 */
	public boolean isLegalTCurrencyUnit(CurrencyUnit currency, Region region,
			Long timestamp);

	/**
	 * This method allows to evaluate the {@link CurrencyUnit} accepted as legal
	 * tenders for a {@link Region}.
	 * 
	 * @param region
	 *            The region to be requested, not null.
	 * @return the {@link CurrencyUnit} to be known as legal tenders for the
	 *         given region, never null.
	 */
	public Set<CurrencyUnit> getLegalCurrencyUnits(Region region);

	/**
	 * This method allows to evaluate the {@link CurrencyUnit} accepted as legal
	 * tenders for a {@link Region}.
	 * 
	 * @param region
	 *            The region to be requested, not null.
	 * @param timestamp
	 *            the UTC timestamp, or null for the current time.
	 * @return the {@link CurrencyUnit} to be known as legal tenders for the
	 *         given region, never null.
	 */
	public Set<CurrencyUnit> getLegalCurrencyUnits(Region region, Long timestamp);

}
