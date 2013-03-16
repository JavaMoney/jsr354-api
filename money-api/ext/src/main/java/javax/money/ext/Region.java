/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 */
package javax.money.ext;

import java.util.Enumeration;

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
	 * Access the region's identifier. The identifier is unique in combination
	 * with the region type.
	 * 
	 * @return the region's type, never {@code null}.
	 */
	public String getId();

	/**
	 * Get the region's type.
	 * 
	 * @return the region's type, never {@code null}.
	 */
	public RegionType getRegionType();

	boolean contains(Region other);

	/**
	 * access the child regions of this region.
	 * 
	 * @return the child regions, never {@code null}.
	 */
	public Enumeration<Region> getChildRegions();

	/**
	 * Access all direct (non-recursive) child regions of this region, hereby
	 * filtering for the given type only.
	 * 
	 * @param type
	 *            the required type, not {@code null}.
	 */
	public Enumeration<Region> getChildRegions(RegionType type);

	/**
	 * Access the parent region of the give region.
	 * 
	 * @return the parent region, or {@code null} if this region is a root
	 *         region.
	 */
	public Region getParentRegion();

	/**
	 * Access the direct child region of the given type. If multiple parent
	 * regions match, the nearest regions relative to this region is returned.
	 * 
	 * @param type
	 *            the target RegionType
	 * @return the parent region that matches the given type, or {@code null}.
	 */
	public Region getParentRegion(RegionType type);

	/**
	 * Access the direct or indirect parent region of the given type. If
	 * multiple parent regions match, the nearest regions relative to this
	 * region is returned.
	 * 
	 * @param type
	 *            the target RegionType
	 * @param recursive
	 *            if true, all child regions are checked for inclusion
	 *            recursively.
	 * @return the parent region that matches the given type, or {@code null}.
	 */
	public Enumeration<Region> getChildRegions(RegionType type,
			boolean recursive);

	/**
	 * Access a child region (regardless its {@link RegionType}), using its
	 * identifier.
	 * 
	 * @param identifier
	 *            the child region's id.
	 * @return the region that matches the given id, or null.
	 * @throws IllegalArgumentException
	 *             if multiple child regions have the given id.
	 */
	public Region getChildRegion(String identifier);

	/**
	 * Access a child region (regardless its {@link RegionType}), using its
	 * identifier.
	 * 
	 * @param identifier
	 *            the child region's id.
	 * @param recursive
	 *            Flag, to walk down the tree recursively.
	 * @return the region that matches the given id, or null.
	 * @throws IllegalArgumentException
	 *             if multiple child regions have the given id.
	 */
	public Region getChildRegion(String identifier, boolean recursive);

	/**
	 * Access a child region, check only regions of the given {@link RegionType}
	 * ), using its identifier.
	 * 
	 * @param identifier
	 *            the child region's id.
	 * @param type
	 *            the required {@link RegionType}
	 * @return the region that matches the given id, or null.
	 * @throws IllegalArgumentException
	 *             if multiple child regions have the given id.
	 */
	public Region getChildRegion(String identifier, RegionType type);

	/**
	 * Access a child region, check only regions of the given {@link RegionType}
	 * ), using its identifier.
	 * 
	 * @param identifier
	 *            the child region's id.
	 * @param type
	 *            the required {@link RegionType}
	 * @param recursive
	 *            Flag, to walk down the tree recursively.
	 * @return the region that matches the given id, or null.
	 * @return the region that matches the given id, or null.
	 * @throws IllegalArgumentException
	 *             if multiple child regions have the given id.
	 */
	public Region getChildRegion(String identifier, RegionType type,
			boolean recursive);

}
