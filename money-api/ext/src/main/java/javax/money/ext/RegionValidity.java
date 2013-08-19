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

/**
 * This interface models the validity services for currencies, provided by a
 * validity source.
 * 
 * @author Anatole Tresch
 */
public interface RegionValidity {

    /**
     * Access the source which provides data for this service instance.
     * 
     * @return the validity source, not null.
     */
    public String getValiditySource();

    /**
     * Access current validities for the given reference.
     * 
     * @param currencyUnit
     * @param validityReferenceType
     * @return
     */
    public <T> Collection<ValidityInfo<Region>> getValidityInfo(Region region);

    /**
     * Access validities for the given reference, starting from the given
     * timestamp, until now.
     * 
     * @param currencyUnit
     * @param validityReferenceType
     * @param timestamp
     * @return
     */
    public <T> Collection<ValidityInfo<Region>> getValidityInfo(Region region, long timestamp);

    /**
     * Access validities for the given reference, constraint by the given time
     * period.
     * 
     * @param currencyUnit
     * @param validityReferenceType
     * @param from
     * @param to
     * @return
     */
    public <T> Collection<ValidityInfo<Region>> getValidityInfo(Region region,
	    long from, long to);

    /**
     * Access all currencies matching a {@link Region}.
     * 
     * @param locale
     *            the target locale, not null.
     * @return the currencies found, never null.
     */
    public Collection<ValidityInfo<Region>> getValidityInfo(RegionType regionType);

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
    public Collection<ValidityInfo<Region>> getValidityInfo(RegionType regionType, long timestamp);

}