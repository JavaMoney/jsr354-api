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
package javax.money.ext.spi;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;

import javax.money.ext.Region;
import javax.money.ext.RegionTreeNode;
import javax.money.ext.RegionType;
import javax.money.ext.Regions;

/**
 * This is the {@link Regions} spi interface to be registered using
 * {@code ServiceLoader} and that is used by the {@link Regions} singleton as
 * its internal implementation. It is responsible for loading and managing of
 * {@link RegionProviderSpi} and {@link RegionTreeProviderSpi} instances and
 * delegating according calls to the appropriate providers.
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
	 * @return The matching {@link Region}, or {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@link Region} instances matching are ambiguous.
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
	 *             if the {@link Region} instances matching are ambiguous.
	 */
	public Region getRegion(RegionType type, String code);

	/**
	 * Access a region using the corresponding JDK country {@link Locale}.
	 * 
	 * @param locale
	 *            The country {@link Locale}.
	 * @return the corresponding {@link Region}, or {@code null}.
	 */
	public Region getRegion(Locale locale);

	/**
	 * Access all regions provided for a given {@link RegionType}.
	 * 
	 * @param type
	 *            The required {@link RegionType}.
	 * @return the known regions of that type, not {@code null}.
	 */
	public Collection<Region> getRegions(RegionType type);

	/**
	 * Access all {@link RegionType}s defined by this provider.
	 * 
	 * @return the {@link RegionType} instances provided by/used by this
	 *         provider.
	 */
	public Set<RegionType> getRegionTypes();

	/**
	 * Access the defined region trees.
	 * 
	 * @see #getRegionTree(String)
	 * @return the set of defined region trees, accessible calling
	 *         {@link #getRegionTree(String)}.
	 */
	public Set<String> getRegionTreeIds();

	/**
	 * Get the given region tree, for a list of possible items call
	 * {@link #getRegionTreeIds()} beforehand.
	 * 
	 * @see #getRegionTreeIds()
	 * @param id
	 *            The tree identifier
	 * @return the region tree.
	 */
	public RegionTreeNode getRegionTree(String id);
}
