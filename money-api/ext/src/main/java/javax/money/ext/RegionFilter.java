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

/**
 * This interface allows selecting a subset of {@link Region} instances within a
 * parent {@link Region}.
 * 
 * @author Anatole Tresch
 */
// @FunctionalInterface
public interface RegionFilter {

	/**
	 * Filter method used to determine which {@link Region} instances should be
	 * included within a result set returned by
	 * {@link RegionNode#select(RegionFilter)} or
	 * {@link RegionNode#selectParent(RegionFilter)}. This method is called by
	 * the {@link RegionNode} instance for each {@link Region} instance during
	 * tree traversal.
	 * 
	 * @param region
	 *            tHE {@link Region} instance which may be included.
	 * @return {@code true} if the {@link Region} should be included in the
	 *         result.
	 */
	public boolean accept(RegionNode region);

}
