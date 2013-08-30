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
package net.java.javamoney.ri.ext;

import javax.money.ext.RegionFilter;
import javax.money.ext.RegionTreeNode;

/**
 * This models a subtree within a {@link RegionTreeNode}, that is transparently
 * modeled/provided by another subtree within the global region trees. This can
 * be useful to fill up the possible child values with regions already existing.
 * E.g. A sales representative's organization may contain several countries,
 * defined in detail by ISO, so instead of copying the ISO values they can be
 * linked to the corresponding ISO area within the global region forest.<br/>
 * Since sometimes not all linked values atr applicable an additional
 * {@link RegionFilter} can be applied.
 * 
 * @author Anatole Tresch
 */
public interface LinkedRegionNode extends RegionTreeNode {

	/**
	 * This method returns the effective tree path, starting with a current root
	 * region, to which this node links. This allows to link an arbitrary region
	 * subgraph as child node of the given tree.
	 * 
	 * @see #getLinkedFilter()
	 * @return the path to the subgraph linkded, or {@code null}.
	 */
	public String getLinkedPath();

	/**
	 * This filter allows to filter out children, from the subgraph linked in.
	 * 
	 * @see #getLinkedPath()
	 * @return the filter used, or {@code null}.
	 */
	public RegionFilter getLinkedFilter();
}
