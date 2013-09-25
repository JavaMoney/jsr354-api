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
package org.javamoney.ext.spi;

import java.util.Collection;

import org.javamoney.ext.Region;

/**
 * Implementation of this interface provide arbitrary additional data for a
 * region.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 * 
 * @author Anatole Tresch
 */
public interface ExtendedRegionDataProviderSpi {

	/**
	 * Get the extended data types, that can be accessed from this
	 * {@link Region} by calling {@link #getRegionData(Class)}.
	 * 
	 * @param region
	 *            the region for which addition data is requested.
	 * @return the collection of supported region data, may be {@code empty} but
	 *         never {@code null}.
	 */
	public Collection<Class> getExtendedRegionDataTypes(Region region);

	/**
	 * Access the additional region data, using its type.
	 * <p>
	 * Note different to the API this method does never throw an
	 * {@link IllegalArgumentException} when the required type is not supported,
	 * but simply should return {@code null}.
	 * 
	 * @param region
	 *            the region for which addition data is requested.
	 * @param type
	 *            The region data type, not {@code null}.
	 * @return the corresponding data item, or {@code null} if the type passed
	 *         is not supported.
	 */
	public <T> T getExtendedRegionData(Region region, Class<T> type);

}
