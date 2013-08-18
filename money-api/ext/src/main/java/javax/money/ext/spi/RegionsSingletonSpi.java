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
import javax.money.ext.RegionNode;
import javax.money.ext.RegionType;
import javax.money.ext.RegionValidity;

/**
 * This is the region spi interface to be registered using {@code ServiceLoader}
 * and that is used by the {@link MonetaryRegions} singleton.
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
	 * Access a region using a {@link Locale}.
	 * 
	 * @param locale
	 *            The required locale.
	 * @return the corresponding region, or null.
	 */
	public Region getRegion(Locale locale);

	/**
	 * Access all regions provided for a given {@link RegionType}.
	 * 
	 * @param type
	 *            The required region type.
	 * @return the known regions of that type, not null.
	 */
	public Collection<Region> getRegions(RegionType type);

	/**
	 * Access all region types defined by this provider.
	 * 
	 * @return the {@link RegionType} instances provided by/used by this
	 *         provider.
	 */
	public Set<RegionType> getRegionTypes();

	/**
	 * Get a {@link RegionValidity} service.
	 * 
	 * @return
	 */
	public RegionValidity getRegionValidity();

	/**
	 * Access the defined region trees.
	 * 
	 * @see #getRegionTree(String)
	 * @return the set of defined region trees, accessible calling
	 *         {@link #getRegionTree(String)}.
	 */
	public Set<String> getRegionTreeIds();

	/**
	 * Get the given region tree, for a list call {@link #getRegionTreeIds()}
	 * beforehand.
	 * 
	 * @see #getRegionTreeIds()
	 * @param id
	 *            The tree name
	 * @return the region tree.
	 */
	public RegionNode getRegionTree(String id);
}
