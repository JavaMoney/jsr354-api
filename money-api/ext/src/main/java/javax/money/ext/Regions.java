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

import javax.money.ext.spi.RegionsSingletonSpi;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public Set<RegionType> getRegionTypes() {
        return REGION_SPI.getRegionTypes();
    }

    /**
     * Access a region by region type and the region code.
     *
     * @param type the region type, not null.
     * @param code the region code, not null.
     * @return the region found, or null.
     */
    public Region getRegion(RegionType type, String code) {
        return REGION_SPI.getRegion(type, code);
    }

    /**
     * Access a region by region type and the numeric region code.
     *
     * @param type the region type, not null.
     * @param code the numeric region code, not null.
     * @return the region found, or null.
     */
    public Region getRegion(RegionType type, int code) {
        return REGION_SPI.getRegion(type, code);
    }
    
    /**
     * Access a region using a {@link Locale}.
     *
     * @param locale The required locale.
     * @return the corresponding region, or null.
     */
    public Region getRegion(Locale locale){
        return REGION_SPI.getRegion(locale);
    }
    
    /**
     * Access all regions of a given region type.
     *
     * @param type the region type, not null.
     * @return the region found, or null.
     */
    public Collection<Region> getRegions(RegionType type) {
        return REGION_SPI.getRegions(type);
    }

    /**
     * Access the {@link RegionValidity} service.
     *
     * @return the {@link RegionValidity} for accessing historic regional data
     * provided by the registered validity providers.
     * @throws IllegalArgumentException If the given provider is not available.
     */
    public static RegionValidity getRegionValidity() {
        return REGION_SPI.getRegionValidity();
    }

    /**
     * Access all regions available, that have no parent region. It is possible
     * to define different regional hierarchies at the same time, whereas the
     * ids of the root regions must be unique among all root regions
     *
     * @return all {@link Region}s available without a parent, never null.
     */
    public static Collection<RegionNode> getRegionForest() {
        return REGION_SPI.getRegionForest();
    }

    /**
     * Thnis method constructs a RegionNode using the given region as root
     * region.
     *
     * @param region the root region, not {@code null}.
     * @return the region tree, never {@code null}.
     */
    public static RegionNode getRegionNode(Region region) {
        return REGION_SPI.getRegionNode(region);
    }

    /**
     * Access a {@link RegionNode} using the region's hierarchy starting from
     * the given root {@link Region}.
     *
     * @param path the path to be accessed, not {@code null}.
     * @return the {@link RegionNode} starting at the position defined by
     * {@code path}, never {@code null}.
     * @throws IllegalArgumentException if the path can not be resolved.
     */
    public static RegionNode getRegionNode(String path) {
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
            Iterator<RegionsSingletonSpi> instances = ServiceLoader.load(RegionsSingletonSpi.class).iterator();
            RegionsSingletonSpi spiLoaded = null;
            if (instances.hasNext()) {
                spiLoaded = instances.next();
                if (instances.hasNext()) {
                    throw new IllegalStateException("Ambigous reference to spi (only "
                            + "one can be registered: " + RegionsSingletonSpi.class.getName());
                }
                return spiLoaded;
            }
        } catch (Throwable e) {
            Logger.getLogger(RegionsSingletonSpi.class.getName()).log(Level.INFO,
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
    private final static class DefaultMonetaryRegionsSpi implements RegionsSingletonSpi {

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
        public Collection<RegionNode> getRegionForest() {
            return Collections.emptySet();
        }

        @Override
        public RegionNode getRegionNode(Region region) {
            throw new IllegalArgumentException("DefaultProvider: no such region tree: " + region);
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
