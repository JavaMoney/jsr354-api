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
import java.util.ServiceLoader;

import javax.money.ext.Region;
import javax.money.ext.RegionNode;
import javax.money.ext.RegionType;

/**
 * Implementation of this interface define the regions supported in the system.
 * Each provider may hereby serve several region types.
 *
 * @author Anatole Tresch
 */
public interface RegionProviderSpi {

    /**
     * Returns all {@link RegionType}s defined by this {@link RegionProviderSpi}
     * instance.
     *
     * @return the {@link RegionType}s to be defined.
     */
    public Collection<RegionType> getRegionTypes();

    /**
     * Access all regions provided for {@link RegionType} by this region
     * provider.
     *
     * @param type The required region type.
     * @return the regions to be added, not null.
     */
    public Collection<Region> getRegions(RegionType type);

    /**
     * Access a region.
     *
     * @param identifier The region's id.
     * @param type The required region type.
     * @return The corresponding region, or null.
     */
    public Region getRegion(RegionType type, String identifier);

    /**
     * Access a region.
     *
     * @param numericId The region's numeric id.
     * @param type The required region type.
     * @return The corresponding region, or null.
     */
    public Region getRegion(RegionType type, int numericId);
    
    /**
     * Access a region using a {@link Locale}.
     *
     * @param locale The required locale.
     * @return the corresponding region, or null.
     */
    public Region getRegion(Locale locale);
    
}
