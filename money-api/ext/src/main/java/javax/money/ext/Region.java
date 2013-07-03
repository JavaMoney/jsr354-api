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

/**
 * Regions can be used to segregate or access artifacts (e.g. currencies) either
 * based on geographical, or commercial aspects (e.g. legal units).
 * 
 * @see <a href="http://unstats.un.org/unsd/methods/m49/m49regin.htm">UN M.49:
 *      UN Statistics Division Country or area & region codes</a>
 * 
 * @author Anatole Tresch
 */
public interface Region {

    /**
     * Get the region's type.
     * 
     * @return the region's type, never null.
     */
    public RegionType getRegionType();

    /**
     * Access the region's code. The code is unique in combination with the
     * region type.
     * 
     * @return the region's type, never null.
     */
    public String getRegionCode();

    /**
     * Get the region's numeric code. If not defined -1 is returned.
     * 
     * @return the numeric region ode, or -1.
     */
    public int getNumericRegionCode();

    /**
     * Get the direct parent region of this region.
     * 
     * @return the parent region, or null, if this region has no parent (is a
     *         root region).
     */
    public Region getParent();

    /**
     * Access all direct child regions.
     * 
     * @return all direct child regions, never null.
     */
    public Collection<Region> getChildren();

    /**
     * Determines if the given region is contained within this region.
     * 
     * @param region
     *            the region being looked up, null hereby is never contained.
     * @return true if the given region is a direct or indirect child of this
     *         region instance.
     */
    public boolean contains(Region region);

    /**
     * Select the parent region with the given type. This method will navigate
     * up the region tree and select the first parent encountered that has the
     * given region type.
     * 
     * @param type
     *            the required type, not null.
     * @return the region found, or null.
     */
    public Region selectParent(RegionType type);
    
    /**
     * Select a collection of regions selected by the given filter.
     * 
     * @param filter
     *            the region selector, null will return all regions.
     * @return the regions selected.
     */
    public Collection<Region> selectChildren(RegionFilter filter);

    /**
     * Lookup a child region with the given id.
     * 
     * @param code
     *            the child code, not null.
     * @return the child found, or null.
     */
    public Region getChildByCode(String code);

    /**
     * Lookup a child region with the given id.
     * 
     * @param code
     *            the child numeric code.
     * @return the child found, or null.
     */
    public Region getChildByNumericCode(int code);

}
