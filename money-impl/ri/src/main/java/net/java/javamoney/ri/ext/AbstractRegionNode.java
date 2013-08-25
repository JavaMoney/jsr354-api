/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial implementation Werner Keil -
 * extensions and adaptions.
 */
package net.java.javamoney.ri.ext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.money.ext.Region;
import javax.money.ext.RegionFilter;
import javax.money.ext.RegionTreeNode;

/**
 * Abstract base class for a {@link RegionTreeNode}.
 * 
 * @author Anatole Tresch
 */
public abstract class AbstractRegionNode implements RegionTreeNode,
		Comparable<RegionTreeNode> {
	/** Corresponding region instance. */
	private Region region;
	/** THe node's parent node. */
	private RegionTreeNode parent;
	/** The node's child nodes. */
	private List<RegionTreeNode> childNodes = new ArrayList<RegionTreeNode>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.RegionTreeNode#getRegion()
	 */
	@Override
	public Region getRegion() {
		return region;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.RegionTreeNode#getParent()
	 */
	@Override
	public RegionTreeNode getParent() {
		return parent;
	}

	/**
	 * Set the node's region.
	 * 
	 * @param region
	 *            The new region.
	 */
	protected void setRegion(Region region) {
		if (region == null) {
			throw new IllegalArgumentException("region is required.");
		}
		this.region = region;
	}

	/**
	 * Access the child nodes instance (mutable).
	 * 
	 * @return the child nodes list.
	 */
	protected List<RegionTreeNode> getChildNodes() {
		return childNodes;
	}

	/**
	 * Set the node's parent.
	 * 
	 * @param parent
	 *            The new parent.
	 */
	protected void setParent(RegionTreeNode parent) {
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.RegionTreeNode#getChildren()
	 */
	@Override
	public Collection<RegionTreeNode> getChildren() {
		return Collections.unmodifiableCollection(childNodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.RegionTreeNode#contains(javax.money.ext.Region)
	 */
	@Override
	public boolean contains(Region region) {
		for (RegionTreeNode regionNode : childNodes) {
			if (regionNode.getRegion().equals(region)) {
				return true;
			}
			if (regionNode.contains(region)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.ext.RegionTreeNode#selectParent(javax.money.ext.RegionFilter)
	 */
	@Override
	public RegionTreeNode selectParent(RegionFilter filter) {
		RegionTreeNode regionNode = parent;
		while (regionNode != null) {
			if (filter.accept(regionNode)) {
				return regionNode;
			}
			regionNode = regionNode.getParent();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.RegionTreeNode#select(javax.money.ext.RegionFilter)
	 */
	@Override
	public Collection<RegionTreeNode> select(RegionFilter filter) {
		List<RegionTreeNode> result = new ArrayList<RegionTreeNode>();
		for (RegionTreeNode regionNode : childNodes) {
			if (filter.accept(regionNode)) {
				result.add(regionNode);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.RegionTreeNode#getRegionTree(java.lang.String)
	 */
	@Override
	public RegionTreeNode getRegionTree(String path) {
		String[] paths = path.split("/");
		RegionTreeNode current = this;
		for (String pathElem : paths) {
			current = current.getSubRegion(pathElem.trim());
			if (current == null) {
				throw new IllegalArgumentException("path not found: " + path);
			}
		}
		return current;
	}

	@Override
	public int compareTo(RegionTreeNode o) {
		return ((Comparable<Region>) this.region).compareTo(o.getRegion());
	}

	public String getAsText() {
		return getAsText("");
	}

	public String getAsText(String intend) {
		StringBuilder b = new StringBuilder();
		try {
			printTree(b, intend);
		} catch (IOException e) {
			e.printStackTrace(); // TODO
			b.append("Error: " + e);
		}
		return b.toString();
	}

	public void printTree(Appendable b, String intend) throws IOException {
		b.append(intend + toString()).append("\n");
		intend = intend + "  ";
		for (RegionTreeNode region : getChildren()) {
			region.printTree(b, intend);
		}
	}

	@Override
	public RegionTreeNode getSubRegion(String path) {
		String[] paths = path.split("/");
		RegionTreeNode current = this;
		for (String curPath : paths) {
			if (curPath.trim().isEmpty()) {
				continue;
			}
			current = getRegionNode(curPath);
			if (current == null) {
				throw new IllegalArgumentException("Invalid path: " + path);
			}
		}
		return current;
	}

	protected RegionTreeNode getRegionNode(String curPath) {
		for (RegionTreeNode node : this.childNodes) {
			if (node.getRegion().getRegionCode().equals(curPath)) {
				return node;
			}
		}
		return null;
	}

	public Region getRegionByCode(String code) {
		for (RegionTreeNode node : this.childNodes) {
			if (node.getRegion().getRegionCode().equals(code)) {
				return node.getRegion();
			}
		}
		return null;
	}

	public Region getRegionByNumericCode(int code) {
		for (RegionTreeNode node : this.childNodes) {
			if (node.getRegion().getNumericRegionCode() == code) {
				return node.getRegion();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " [region=" + region + ", parent="
				+ parent + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractRegionNode other = (AbstractRegionNode) obj;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		return true;
	}

}
