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
import java.util.Set;

/**
 * This singleton defines access to the exchange and currency conversion logic
 * of JavaMoney.
 * 
 * @author Anatole Tresch
 */
public interface RegionInfo {

    public String getInfoSource();

    public Set<RegionType> getRegionTypes();

    public Region getRegion(RegionType type, String code);

    public Region getRegion(RegionType type, int numericId);

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

    /**
     * Since ids of root regions must be unique a regional tree can be accessed
     * using this id.
     * 
     * @see #getRootRegions()
     * @param numericId
     *            the root region's numeric id
     * @return the root region, or null.
     */
    public Region getRootRegion(int numericId);

    /**
     * Get a region within this region tree by path, using the region's codes
     * 
     * @param path
     *            the region path, e.g. {@code /countries/ISO/DE}.
     * @return the region found, or null.
     */
    public Region getRegion(String path);

    /**
     * Access all regions selected by the given filter.
     * 
     * @param filter
     *            the selecting filter, not null.
     * @return all {@link Region}s available where the filter matches, never
     *         null.
     */
    public Collection<Region> selectRegions(RegionFilter filter);

    /**
     * Access all regions provided by this data source.
     * 
     * @return all {@link Region}s available without a parent, never null.
     */
    public Collection<Region> getAllRegions();

}
