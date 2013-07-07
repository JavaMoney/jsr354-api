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
import java.util.Collections;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This singleton defines access to the region logic of JavaMoney.
 * 
 * @author Anatole Tresch
 */
public final class MonetaryRegions {

    /**
     * The spi currently active, use {@link ServiceLoader} to register an
     * implementation.
     */
    private static final MonetaryRegionsSpi MONETARY_REGION_SPI = loadMonetaryRegionSpi();

    /**
     * Private singleton constructor.
     */
    private MonetaryRegions() {
    }

    /**
     * Access a {@link Region} using the region path, which allows access of a
     * {@link Region} from the tree, e.g. {@code WORLD/EUROPE/GERMANY} or
     * {@code STANDARDS/ISO/GER}.
     * 
     * @param path
     *            the path to be accessed, not {@code null}.
     * @return the {@link Region} found, or {@code null}.
     */
    public Region getRegionByPath(String path) {
	throw new UnsupportedOperationException("Not yet implemented.");
    }

    /**
     * Get the set of {@link RegionValidity} provider identifiers currently
     * registered. The identifiers are required for accessing
     * {@link RegionValidity} from {@link #RegionValidity(String)}.
     * 
     * @return the identifiers of the registered {@link RegionValidity}
     *         providers.
     */
    public static Set<String> getRegionValidityProviders() {
	return MONETARY_REGION_SPI.getRegionValidityProviders();
    }

    /**
     * This method allows to determine if a given {@link RegionValidity}
     * provider is currently available.
     * 
     * @param provider
     *            The {@link RegionValidity} provider id.
     * @return {@code true} if a {@link RegionValidity} provider with the given
     *         identifier is registered.
     */
    public static boolean isValidityProviderAvailable(String provider) {
	return MONETARY_REGION_SPI.getRegionValidityProviders().contains(provider);
    }

    /**
     * Access a {@link RegionValidity}, determined by the given provider.
     * 
     * @param provider
     *            the {@link RegionValidity} provider identifier.
     * @return the {@link RegionValidity} for accessing historic regional data
     *         provided by the given provider.
     * @throws IllegalArgumentException
     *             If the given provider is not available.
     */
    public static RegionValidity getRegionValidity(String provider) {
	return MONETARY_REGION_SPI.getRegionValidity(provider);
    }

    /**
     * Access the currently available {@link RegionType} instances.
     * 
     * @return the currently available {@link RegionType} instances, never null.
     */
    public Set<RegionType> getRegionTypes() {
	return MONETARY_REGION_SPI.getRegionTypes();
    }

    /**
     * Access a region by region type and the region code.
     * 
     * @param type
     *            the region type, not null.
     * @param code
     *            the region code, not null.
     * @return the region found, or null.
     */
    public Region getRegion(RegionType type, String code) {
	return MONETARY_REGION_SPI.getRegion(type, code);
    }

    /**
     * Access a region by region type and the numeric region code.
     * 
     * @param type
     *            the region type, not null.
     * @param code
     *            the numeric region code, not null.
     * @return the region found, or null.
     */
    public Region getRegion(RegionType type, int code) {
	return MONETARY_REGION_SPI.getRegion(type, code);
    }

    /**
     * Access all regions available, that have no parent region. It is possible
     * to define different regional hierarchies at the same time, whereas the
     * ids of the root regions must be unique among all root regions
     * 
     * @return all {@link Region}s available without a parent, never null.
     */
    public Collection<Region> getRegionTrees() {
	return MONETARY_REGION_SPI.getRegionTrees();
    }

    /**
     * Since ids of root regions must be unique a regional tree can be accessed
     * using this id.
     * 
     * @see #getRootRegions()
     * @param id
     *            the root region's id
     * @return the root region, or null.
     */
    public Region getRegionTree(String id) {
	return MONETARY_REGION_SPI.getRegionTree(id);
    }

    /**
     * Since ids of root regions must be unique a regional tree can be accessed
     * using this id.
     * 
     * @see #getRootRegions()
     * @param numericId
     *            the root region's numeric id
     * @return the root region, or null.
     */
    public Region getRegionTree(int numericId) {
	return MONETARY_REGION_SPI.getRegionTree(numericId);
    }

    /**
     * This is the region spi interface to be registered that is used by the
     * {@link MonetaryRegions} singleton.
     * 
     * @author Anatole Tresch
     */
    public static interface MonetaryRegionsSpi {

	/**
	 * Get a set of {@link RegionValidity} provider identifiers registered.
	 * 
	 * @return the {@link RegionValidity} identifiers of the registered
	 *         region providers, not {@code null}, but may be empty.
	 */
	public Set<String> getRegionValidityProviders();

	/**
	 * Access a root region by its numeric id. <i>Note:</i> The numeric id
	 * may not be defined by a region, in this case access the region using
	 * its {@code code}.
	 * 
	 * @see #getRegion(RegionType, String)
	 * @param numericId
	 *            the numeric region id
	 * @return The matching {@link Region}, or {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@link Region} instances matching are ambiguous.
	 */
	public Region getRegionTree(int numericId);

	/**
	 * Access a root region by its code.
	 * 
	 * @see #getRegion(RegionType, int)
	 * @param numericId
	 *            the numeric region id
	 * @return The matching {@link Region}, or {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@link Region} instances matching are ambiguous.
	 */
	public Region getRegionTree(String id);

	/**
	 * Get all root {@link Region} instances that are the starting points of
	 * the several region trees.
	 * 
	 * @return The root region of the different region trees.
	 */
	public Collection<Region> getRegionTrees();

	/**
	 * Access a {@link Region} by its {@link RegionType} and its numeric id.
	 * <i>Note:</i> The numeric id may not be defined by a region, in this
	 * case access the region using its {@code code}.
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
	 * Access all region types defined by this provider.
	 * 
	 * @return the {@link RegionType} instances provided by/used by this
	 *         provider.
	 */
	public Set<RegionType> getRegionTypes();

	/**
	 * Get a {@link RegionValidity} from the given provider.
	 * 
	 * @param provider
	 * @return
	 */
	public RegionValidity getRegionValidity(String provider);

    }

    /**
     * Method that loads the {@link MonetaryConversionSpi} on class loading.
     * 
     * @return the instance ot be registered into the shared variable.
     */
    private static MonetaryRegionsSpi loadMonetaryRegionSpi() {
	try {
	    // try loading directly from ServiceLoader
	    Iterator<MonetaryRegionsSpi> instances = ServiceLoader.load(MonetaryRegionsSpi.class).iterator();
	    if (instances.hasNext()) {
		return instances.next();
	    }
	} catch (Throwable e) {
	    Logger.getLogger(MonetaryRegionsSpi.class.getName()).log(Level.INFO,
		    "No MonetaryRegionSpi registered, using  default.", e);
	}
	return new DefaultMonetaryRegionsSpi();
    }

    /**
     * This class represents the default implementation of
     * {@link MonetaryConversionSpi} used always when no alternative is
     * registered within the {@link ServiceLoader}.
     * 
     * @author Anatole Tresch
     * 
     */
    private final static class DefaultMonetaryRegionsSpi implements MonetaryRegionsSpi {

	@Override
	public Set<String> getRegionValidityProviders() {
	    return Collections.emptySet();
	}

	@Override
	public Collection<Region> getRegionTrees() {
	    return Collections.emptySet();
	}

	@Override
	public Region getRegion(RegionType type, int numericId) {
	    return null;
	}

	@Override
	public Region getRegion(RegionType type, String code) {
	    return null;
	}

	@Override
	public Set<RegionType> getRegionTypes() {
	    return Collections.emptySet();
	}

	@Override
	public RegionValidity getRegionValidity(String provider) {
	    throw new IllegalArgumentException("DefaultProvider: Invalid provider: " + provider);
	}

	@Override
	public Region getRegionTree(int numericId) {
	    throw new IllegalArgumentException("DefaultProvider: no such region: " + numericId);
	}

	@Override
	public Region getRegionTree(String id) {
	    throw new IllegalArgumentException("DefaultProvider: no such region: " + id);
	}

    }

}
