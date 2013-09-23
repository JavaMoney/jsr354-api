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
import java.util.ServiceLoader;

import org.javamoney.ext.Region;
import org.javamoney.ext.RegionType;

/**
 * Implementation of this interface add additional {@link Region}s to the
 * regions API. Each {@link RegionProviderSpi} may hereby serve several
 * {@link RegionType}s.<br/>
 * It is the responsibility of the registered {@link RegionsSingletonSpi} to
 * load the and manage the instances of {@link RegionProviderSpi}. Depending on
 * the runtime environment, implementations may be loaded using the
 * {@link ServiceLoader}. But also alternate mechanisms are possible, e.g. CDI.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 * 
 * @author Anatole Tresch
 */
public interface RegionProviderSpi {

	/**
	 * Returns all {@link RegionType}s provided by this
	 * {@link RegionProviderSpi} instance, hereby it is possible that several
	 * providers may provide {@link Region}s for the same {@link RegionType}, as
	 * long as they are unique related to its code and numderic id (if defined).
	 * 
	 * @return the {@link RegionType}s for which this provider provides regions,
	 *         never {@code null}.
	 */
	public Collection<RegionType> getRegionTypes();

	/**
	 * Access all regions provided for the given {@link RegionType}.
	 * 
	 * @param type
	 *            The required region type.
	 * @return the regions to be provided, never {@code null}.
	 */
	public Collection<Region> getRegions(RegionType type);

	/**
	 * Access a {@link Region}.
	 * 
	 * @param type
	 *            The required region type.
	 * @param identifier
	 *            The region's id.
	 * @return The corresponding region, or {@code null}.
	 * 
	 */
	public Region getRegion(RegionType type, String identifier);

	/**
	 * Access a {@link Region}.
	 * 
	 * @param numericId
	 *            The region's numeric id.
	 * @param type
	 *            The required region type.
	 * @return The corresponding region, or {@code null}.
	 */
	public Region getRegion(RegionType type, int numericId);

	/**
	 * Access a region using a {@link Locale}.
	 * 
	 * @param locale
	 *            The correspoding country {@link Locale}.
	 * @return the corresponding region, or {@code null}.
	 */
	public Region getRegion(Locale locale);

}
