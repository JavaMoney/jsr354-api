/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

import java.util.Collection;

import javax.money.provider.MonetaryExtension;

/**
 * This class models the component defined by JSR 354 that provides accessors
 * for {@link Region}. It is provided by the Money singleton.
 * 
 * @author Anatole Tresch
 */
public interface RegionProvider extends MonetaryExtension {

	/**
	 * Access all regions available, that have no parent region. It is possible
	 * to define different regional hierarchies at the same time, whereas the
	 * ids of the root regions must be unique among all root regions
	 * 
	 * @return all {@link Region}s available without a parent, never null.
	 */
	public Collection<Region> getRootRegions();

	/**
	 * Since ids of root regions must be unique a regional tree can be accessed
	 * using this id.
	 * 
	 * @see #getRootRegions()
	 * @param id
	 *            the root region's id
	 * @return the root region, or null.
	 */
	public Region getRootRegion(String id);

}
