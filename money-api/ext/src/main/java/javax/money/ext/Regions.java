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
     * Get the set of {@link RegionValidity} provider identifiers currently
     * registered. The identifiers are required for accessing
     * {@link RegionValidity} from {@link #RegionValidity(String)}.
     *
     * @return the identifiers of the registered {@link RegionValidity}
     * providers.
     */
    public static Set<String> getRegionValidityProviders() {
        return REGION_SPI.getRegionValidityProviders();
    }

    /**
     * This method allows to determine if a given {@link RegionValidity}
     * provider is currently available.
     *
     * @param provider The {@link RegionValidity} provider id.
     * @return {@code true} if a {@link RegionValidity} provider with the given
     * identifier is registered.
     */
    public static boolean isValidityProviderAvailable(String provider) {
        return REGION_SPI.getRegionValidityProviders().contains(provider);
    }

    /**
     * Access a {@link RegionValidity}, determined by the given provider.
     *
     * @param provider the {@link RegionValidity} provider identifier.
     * @return the {@link RegionValidity} for accessing historic regional data
     * provided by the given provider.
     * @throws IllegalArgumentException If the given provider is not available.
     */
    public static RegionValidity getRegionValidity(String provider) {
        return REGION_SPI.getRegionValidity(provider);
    }

    /**
     * Access all regions available, that have no parent region. It is possible
     * to define different regional hierarchies at the same time, whereas the
     * ids of the root regions must be unique among all root regions
     *
     * @return all {@link Region}s available without a parent, never null.
     */
    public static Collection<RegionTree> getRegionForest() {
        return REGION_SPI.getRegionForest();
    }

    /**
     * Thnis method constructs a RegionTree using the given region as root
     * region.
     *
     * @param region the root region, not {@code null}.
     * @return the region tree, never {@code null}.
     */
    public static RegionTree getRegionTree(Region region) {
        return REGION_SPI.getRegionTree(region);
    }

    /**
     * Access a {@link RegionTree} using the region's hierarchy starting from
     * the given root {@link Region}.
     *
     * @param path the path to be accessed, not {@code null}.
     * @return the {@link RegionTree} starting at the position defined by
     * {@code path}, never {@code null}.
     * @throws IllegalArgumentException if the path can not be resolved.
     */
    public static RegionTree getRegionTree(String path) {
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
        public Set<String> getRegionValidityProviders() {
            return Collections.emptySet();
        }

        @Override
        public RegionValidity getRegionValidity(String provider) {
            throw new IllegalArgumentException("DefaultProvider: Invalid provider: " + provider);
        }

        @Override
        public Collection<RegionTree> getRegionForest() {
            return Collections.emptySet();
        }

        @Override
        public RegionTree getRegionTree(Region region) {
            throw new IllegalArgumentException("DefaultProvider: no such region tree: " + region);
        }
    }
}
