package javax.money.ext;

import java.io.Serializable;

/**
 * Regions can be used to segregate or access artefacts (e.g. currencies) either
 * based on geographical, or commercial aspects (e.g. legal units).
 * 
 * @see <a href="http://unstats.un.org/unsd/methods/m49/m49regin.htm">UN M.49:
 *      UN Statistics Division Country or area & region codes</a>
 * 
 * @author Anatole Tresch
 */
public interface Region extends Serializable, Comparable<Region> {

	/**
	 * Access the region's identifier. The identifier is unique in combination
	 * with the region type.
	 * 
	 * @return the region's type, nrver null.
	 */
	public String getId();

	/**
	 * Get the region's type.
	 * 
	 * @return the region's type, never null.
	 */
	public RegionType getRegionType();

}
