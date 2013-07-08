/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.money.ext.spi;

import java.util.Collection;
import java.util.Set;
import javax.money.ext.Region;
import javax.money.ext.RegionType;
import javax.money.ext.RegionValidity;

/**
 * This is the region spi interface to be registered that is used by the
 * {@link MonetaryRegions} singleton.
 *
 * @author Anatole Tresch
 */
public interface MonetaryRegionsSingletonSpi {

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
