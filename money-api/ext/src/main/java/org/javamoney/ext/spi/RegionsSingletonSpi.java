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
import java.util.Locale;
import java.util.Set;

import org.javamoney.ext.Region;
import org.javamoney.ext.RegionTreeNode;
import org.javamoney.ext.RegionType;
import org.javamoney.ext.Regions;

/**
 * This is the {@link Regions} spi interface to be registered using
 * {@code ServiceLoader} and that is used by the {@link Regions} singleton as
 * its internal implementation. It is responsible for loading and managing of
 * {@link RegionProviderSpi} and {@link RegionTreeProviderSpi} instances and
 * delegating according calls to the appropriate providers.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 * 
 * @author Anatole Tresch
 */
public interface RegionsSingletonSpi {

	/**
	 * Access a {@link Region} by its {@link RegionType} and its numeric id.
	 * <i>Note:</i> The numeric id may not be defined by a region, in this case
	 * access the region using its {@code code}.
	 * 
	 * @see #getRegion(RegionType, String)
	 * @param type
	 *            The {@link RegionType}
	 * @param numericId
	 *            The numeric id.
	 * @return The matching {@link Region}..
	 * @throws IllegalArgumentException
	 *             if the {@link Region} instances matching are ambiguous, or no
	 *             such {@link Region} could be provided by any of the
	 *             registered {@link RegionProviderSpi} instances.
	 */
	public Region getRegion(RegionType type, int numericId);

	/**
	 * Access a {@link Region} by its {@link RegionType} and its code.
	 * 
	 * @param type
	 *            The {@link RegionType}
	 * @param numericId
	 *            The numeric id.
	 * @return The matching {@link Region}, or {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@link Region} instances matching are ambiguous, or no
	 *             such {@link Region} could be provided by any of the
	 *             registered {@link RegionProviderSpi} instances.
	 */
	public Region getRegion(RegionType type, String code);

	/**
	 * Access a region using the corresponding JDK country {@link Locale}.
	 * 
	 * @param locale
	 *            The country {@link Locale}.
	 * @return the corresponding {@link Region}, if resolved by any of the
	 *         registered {@link RegionProviderSpi} instances, or {@code null}.
	 */
	public Region getRegion(Locale locale);

	/**
	 * Access all regions provided for a given {@link RegionType}.
	 * 
	 * @param type
	 *            The required {@link RegionType}.
	 * @return the known regions of that type as returned by the registered
	 *         {@link RegionProviderSpi} instances, never {@code null}.
	 */
	public Collection<Region> getRegions(RegionType type);

	/**
	 * Get the extended data types, that can be accessed from this
	 * {@link Region} by calling {@link #getRegionData(Class)}.
	 * 
	 * @param region
	 *            the region for which addition data is requested, not
	 *            {@code null}.
	 * @return the collection of supported region data as returned by the
	 *         registered {@link ExtendedRegionDataProviderSpi} instances, may
	 *         be {@code empty} but never {@code null}.
	 */
	public Collection<Class> getExtendedRegionDataTypes(Region region);

	/**
	 * Access the additional region data, using its type.
	 * 
	 * @param region
	 *            the region for which addition data is requested.
	 * @param type
	 *            The region data type, not {@code null}.
	 * @return the corresponding data item.
	 * @throws IllegalArgumentException
	 *             if the type passed is not supported (none of the
	 *             {@link ExtendedRegionDataProviderSpi} instances registered
	 *             could provide it). See {@link #getRegionDataTypes()}.
	 */
	public <T> T getExtendedRegionData(Region region, Class<T> type);

	/**
	 * Access all {@link RegionType}s defined by the registered
	 * {@link RegionProviderSpi} instances.
	 * 
	 * @return the {@link RegionType} instances provided by/used by this
	 *         provider, never {@code null}.
	 */
	public Set<RegionType> getRegionTypes();

	/**
	 * Access the defined region trees, defined by the registered
	 * {@link RegionTreeProviderSpi} instances.
	 * 
	 * @see #getRegionTree(String)
	 * @return the set of defined region trees, accessible calling
	 *         {@link #getRegionTree(String)}, never {@code null}.
	 */
	public Set<String> getRegionTreeIds();

	/**
	 * Get the given region tree, for a list of possible items call
	 * {@link #getRegionTreeIds()} beforehand.
	 * 
	 * @see #getRegionTreeIds()
	 * @param id
	 *            The tree identifier
	 * @return the region tree, never {@code null}.
	 * @throws IllegalArgumentException
	 *             If no such tree was provided by any of the registered
	 *             {@link RegionTreeProviderSpi} instances.
	 */
	public RegionTreeNode getRegionTree(String id);
}
