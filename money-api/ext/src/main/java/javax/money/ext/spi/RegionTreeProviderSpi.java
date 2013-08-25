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

import java.util.Map;
import java.util.ServiceLoader;

import javax.money.ext.Region;
import javax.money.ext.RegionTreeNode;

/**
 * Implementation of this interface provide a region tree, identified by
 * {@link #getTreeId()}. A provider hereby can only provide one region tree and
 * {@link #getTreeId()} must be unique within the same context.<br/>
 * It is the responsibility of the registered {@link RegionsSingletonSpi} to
 * load the and manage the instances of {@link RegionTreeProviderSpi}. Depending
 * on the runtime environment, implementatations may be loaded using the
 * {@link ServiceLoader}. But also alternate mechanisms are possible, e.g. CDI.
 * 
 * @author Anatole Tresch
 */
public interface RegionTreeProviderSpi {

	/**
	 * Get the id of the tree provided by this provider.
	 * 
	 * @return
	 */
	public String getTreeId();

	/**
	 * Initialize the {@link RegionTreeProviderSpi} provider.
	 * 
	 * @param regionProviders
	 *            the region providers loaded, to be used for accessing
	 *            {@link Region} entries to be organized in a
	 *            {@link RegionTreeNode} tree structure.
	 */
	public void init(Map<Class, RegionProviderSpi> regionProviders);

	/**
	 * Access the root {@link RegionTreeNode} of the region tree provided.
	 * 
	 * @return the root node, never {@code null}.
	 */
	public RegionTreeNode getRegionTree();

}
