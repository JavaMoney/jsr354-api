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
     * @param type The {@link RegionType}
     * @param numericId The numeric id.
     * @return The matching {@link Region}, or {@code null}.
     * @throws IllegalArgumentException if the {@link Region} instances matching
     * are ambiguous.
     */
    public Region getRegion(RegionType type, int numericId);

    /**
     * Access a {@link Region} by its {@link RegionType} and its code.
     *
     * @param type The {@link RegionType}
     * @param numericId The numeric id.
     * @return The matching {@link Region}, or {@code null}.
     * @throws IllegalArgumentException if the {@link Region} instances matching
     * are ambiguous.
     */
    public Region getRegion(RegionType type, String code);

    /**
     * Access all region types defined by this provider.
     *
     * @return the {@link RegionType} instances provided by/used by this
     * provider.
     */
    public Set<RegionType> getRegionTypes();

    /**
     * Access a root region by its numeric id. <i>Note:</i> The numeric id may
     * not be defined by a region, in this case access the region using its
     * {@code code}.
     *
     * @see #getRegion(RegionType, String)
     * @param region the region from where the graph should start
     * @return The matching {@link RegionNode}, or {@code null}.
     * @throws IllegalArgumentException if the {@link Region} instances matching
     * are ambiguous.
     */
    public RegionNode getRegionNode(Region region);

    /**
     * Get all root {@link Region} instances that are the starting points of the
     * several region trees.
     *
     * @return The root region of the different region trees.
     */
    public Collection<RegionNode> getRegionForest();

//    /**
//     * Access a {@link Region} by its {@link RegionType} and its numeric id.
//     * <i>Note:</i> The numeric id may not be defined by a region, in this case
//     * access the region using its {@code code}.
//     *
//     * @see #getRegion(RegionType, String)
//     * @param type The {@link RegionType}
//     * @param numericId The numeric id.
//     * @return The matching {@link Region}, or {@code null}.
//     * @throws IllegalArgumentException if the {@link Region} instances matching
//     * are ambiguous.
//     */
//    public RegionNode getRegionTree(RegionType type, int numericId);
//
//    /**
//     * Access a {@link Region} by its {@link RegionType} and its code.
//     *
//     * @param type The {@link RegionType}
//     * @param numericId The numeric id.
//     * @return The matching {@link Region}, or {@code null}.
//     * @throws IllegalArgumentException if the {@link Region} instances matching
//     * are ambiguous.
//     */
//    public RegionNode getRegionTree(RegionType type, String code);

     /**
     * Get a {@link RegionValidity} service.
     *
     * @return
     */
    public RegionValidity getRegionValidity();
}
