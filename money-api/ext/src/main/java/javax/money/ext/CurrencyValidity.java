/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package javax.money.ext;

import java.util.Collection;

import javax.money.CurrencyUnit;

/**
 * This interface models the validity services for currencies, provided by a
 * validity source.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyValidity {

    /**
     * Access the source which provides data for this service instance.
     * 
     * @return the validity source, not null.
     */
    public String getValiditySource();

//    /**
//     * Access current validities for the given reference.
//     * 
//     * @param currencyUnit
//     * @param validityReferenceType
//     * @return
//     */
//    public <T> Collection<ReferencedValidityInfo<CurrencyUnit, Region>> getValidityInfo(Region region);
//
//    /**
//     * Access validities for the given reference, starting from the given
//     * timestamp, until now.
//     * 
//     * @param currencyUnit
//     * @param validityReferenceType
//     * @param timestamp
//     * @return
//     */
//    public <T> Collection<ReferencedValidityInfo<CurrencyUnit, Region>> getValidityInfo(Region region, long timestamp);
//
//    /**
//     * Access validities for the given reference, constraint by the given time
//     * period.
//     * 
//     * @param currencyUnit
//     * @param validityReferenceType
//     * @param from
//     * @param to
//     * @return
//     */
//    public <T> Collection<ReferencedValidityInfo<CurrencyUnit, Region>> getValidityInfo(Region region,
//	    long from, long to);

    /**
     * Access all currencies matching a {@link Region}.
     * 
     * @param locale
     *            the target locale, not null.
     * @return the currencies found, never null.
     */
    public Collection<RelatedValidityInfo<CurrencyUnit, Region>> getValidityInfo(Region region);

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
    public Collection<RelatedValidityInfo<CurrencyUnit, Region>> getValidityInfo(Region region, long timestamp);

    
    /**
     * Access all current currencies for a given namespace.
     * 
     * @param namespace
     *            The target namespace, not null.
     * @return the currencies found.
     */
    public Collection<RelatedValidityInfo<CurrencyUnit, Region>> getValidityInfo(Region region, String namespace);

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
    public boolean isLegalCurrencyUnit(CurrencyUnit currency, Region region);

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
    public boolean isLegalCurrencyUnit(CurrencyUnit currency, Region region, long timestamp);

    /**
     * This method allows to evaluate the {@link CurrencyUnit} accepted as legal
     * tenders for a {@link Region}.
     * 
     * @param region
     *            The region to be requested, not null.
     * @return the {@link CurrencyUnit} to be known as legal tenders for the
     *         given region, never null.
     */
    public Collection<CurrencyUnit> getLegalCurrencyUnits(Region ref);

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
    public <R> Collection<CurrencyUnit> getLegalCurrencyUnits(Region region, long timestamp);

    /**
     * Access all currencies available for a given namespace, timestamp.
     * 
     * @param namespace
     *            The target namespace, not null.
     * @param timestamp
     *            The target UTC timestamp, or {@code null} for current.
     * @return the currencies found.
     */
    public Collection<CurrencyUnit> getValidityInfo(Region region, long timestamp, String namespace);

}