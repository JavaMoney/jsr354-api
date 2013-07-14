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

}
