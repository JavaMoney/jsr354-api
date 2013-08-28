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

import java.io.IOException;
import java.util.Collection;

/**
 * Regions can be used to segregate or access artifacts (e.g. currencies) either
 * based on geographical, or commercial aspects (e.g. legal units).<br/>
 * According root instances (trees) can be accessed by calling
 * {@link Regions#getRegionTree(String)}. By default, ISO countries accessible
 * with 2-digit or 3-digit codes must be provided by default.
 * 
 * @see <a href="http://unstats.un.org/unsd/methods/m49/m49regin.htm">UN M.49:
 *      UN Statistics Division Country or area & region codes</a>
 * 
 * @author Anatole Tresch
 */
public interface RegionTreeNode {

	/**
	 * Get the corresponding region.
	 * 
	 * @return the region, never null.
	 */
	public Region getRegion();

	/**
	 * Get the direct parent region of this region.
	 * 
	 * @return the parent region, or null, if this region has no parent (is a
	 *         root region).
	 */
	public RegionTreeNode getParent();

	/**
	 * Access all direct child regions.
	 * 
	 * @return all direct child regions, never null.
	 */
	public Collection<RegionTreeNode> getChildren();

	/**
	 * Determines if the given region is contained within this region tree.
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
	public RegionTreeNode selectParent(RegionFilter filter);

	/**
	 * Select a collection of regions selected by the given filter.
	 * 
	 * @param filter
	 *            the region selector, null will return all regions.
	 * @return the regions selected.
	 */
	public Collection<RegionTreeNode> select(RegionFilter filter);

	/**
	 * Access a {@link Region} using the region path, which allows access of a
	 * {@link Region} from the tree, e.g. {@code WORLD/EUROPE/GERMANY} or
	 * {@code STANDARDS/ISO/GER}.
	 * 
	 * @param path
	 *            the path to be accessed, not {@code null}.
	 * @return the {@link Region} found, or {@code null}.
	 */
	public RegionTreeNode getRegionTree(String path);

	/**
	 * Access a direct child of the {@link Region}.
	 * 
	 * @param path
	 *            the path, e.g. a/b/c, never {@code null}.
	 * @return The {@link RegionTreeNode} found.
	 * @throws IllegalArgumentException
	 *             , if the path is not matching!
	 */
	public RegionTreeNode getSubRegion(String path);

	/**
	 * Get a String representation of the region tree.
	 * 
	 * @return the according tree as text.
	 */
	public String getAsText();

	/**
	 * Get a String representation of the region tree.
	 * 
	 * @param intend
	 *            THe intend to use for formatting
	 * @return the according tree as text.
	 */
	public String getAsText(String intend);

	/**
	 * Print the tree to the given {@link Appendable}, using the given intend.
	 * 
	 * @param b
	 *            The {@link Appendable} to use.
	 * @param intend
	 *            THe intend to use for formatting
	 * @throws IOException
	 */
	public void printTree(Appendable b, String intend) throws IOException;

}
