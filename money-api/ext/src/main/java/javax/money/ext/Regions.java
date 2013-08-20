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
import java.util.Locale;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.ext.spi.RegionsSingletonSpi;

/**
 * This singleton defines access to the region logic of JavaMoney.
 * 
 * @author Anatole Tresch
 */
public final class Regions {

	/**
	 * The spi currently active, use {@link ServiceLoader} to register an
	 * implementation.
	 */
	private static final RegionsSingletonSpi REGION_SPI = loadMonetaryRegionSpi();

	/**
	 * Private singleton constructor.
	 */
	private Regions() {
	}

	/**
	 * Access the currently available {@link RegionType} instances.
	 * 
	 * @return the currently available {@link RegionType} instances, never null.
	 */
	public static Set<RegionType> getRegionTypes() {
		return REGION_SPI.getRegionTypes();
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
	public static Region getRegion(RegionType type, String code) {
		return REGION_SPI.getRegion(type, code);
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
	public static Region getRegion(RegionType type, int code) {
		return REGION_SPI.getRegion(type, code);
	}

	/**
	 * Access a region using a {@link Locale}.
	 * 
	 * @param locale
	 *            The required locale.
	 * @return the corresponding region, or null.
	 */
	public static Region getRegion(Locale locale) {
		return REGION_SPI.getRegion(locale);
	}

	/**
	 * Access all regions of a given region type.
	 * 
	 * @param type
	 *            the region type, not null.
	 * @return the region found, or null.
	 */
	public static Collection<Region> getRegions(RegionType type) {
		return REGION_SPI.getRegions(type);
	}

	/**
	 * Access the {@link RegionValidity} service.
	 * 
	 * @return the {@link RegionValidity} for accessing historic regional data
	 *         provided by the registered validity providers.
	 * @throws IllegalArgumentException
	 *             If the given provider is not available.
	 */
	public static RegionValidity getRegionValidity() {
		return REGION_SPI.getRegionValidity();
	}

	/**
	 * Access the defined region trees.
	 * 
	 * @see #getRegionTree(String)
	 * @return the set of defined region trees, accessible calling
	 *         {@link #getRegionTree(String)}.
	 */
	public static Set<String> getRegionTreeIds() {
		return REGION_SPI.getRegionTreeIds();
	}

	/**
	 * Get the given region tree, for a list call {@link #getRegionTreeIds()}
	 * beforehand.
	 * 
	 * @see #getRegionTreeIds()
	 * @param id
	 *            The tree name
	 * @return the region tree.
	 * @throws IllegalArgumentException
	 *             if no such region tree is available.
	 */
	public static RegionTreeNode getRegionTree(String treeId) {
		return REGION_SPI.getRegionTree(treeId);
	}

	/**
	 * Access a {@link RegionTreeNode} using the region's hierarchy starting from
	 * the given root {@link Region}.
	 * 
	 * @param path
	 *            the path to be accessed, not {@code null}.
	 * @return the {@link RegionTreeNode} starting at the position defined by
	 *         {@code path}, never {@code null}.
	 * @throws IllegalArgumentException
	 *             if the path can not be resolved.
	 */
	public static RegionTreeNode getRegionNode(String path) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	/**
	 * Method that loads the {@link MonetaryConversionSpi} on class loading.
	 * 
	 * @return the instance ot be registered into the shared variable.
	 */
	private static RegionsSingletonSpi loadMonetaryRegionSpi() {
		try {
			// try loading directly from ServiceLoader
			Iterator<RegionsSingletonSpi> instances = ServiceLoader.load(
					RegionsSingletonSpi.class).iterator();
			RegionsSingletonSpi spiLoaded = null;
			if (instances.hasNext()) {
				spiLoaded = instances.next();
				if (instances.hasNext()) {
					throw new IllegalStateException(
							"Ambigous reference to spi (only "
									+ "one can be registered: "
									+ RegionsSingletonSpi.class.getName());
				}
				return spiLoaded;
			}
		} catch (Throwable e) {
			Logger.getLogger(RegionsSingletonSpi.class.getName()).log(
					Level.INFO,
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
	private final static class DefaultMonetaryRegionsSpi implements
			RegionsSingletonSpi {

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
		public RegionValidity getRegionValidity() {
			throw new IllegalArgumentException("DefaultProvider:.");
		}

		@Override
		public Set<String> getRegionTreeIds() {
			return Collections.emptySet();
		}

		@Override
		public RegionTreeNode getRegionTree(String treeId) {
			throw new IllegalArgumentException(
					"DefaultProvider: no such region tree: " + treeId);
		}

		@Override
		public Region getRegion(Locale locale) {
			return null;
		}

		@Override
		public Collection<Region> getRegions(RegionType type) {
			return Collections.emptySet();
		}
	}
}
