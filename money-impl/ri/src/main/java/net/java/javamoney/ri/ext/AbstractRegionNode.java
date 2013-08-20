package net.java.javamoney.ri.ext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.money.ext.Region;
import javax.money.ext.RegionFilter;
import javax.money.ext.RegionTreeNode;

public abstract class AbstractRegionNode implements RegionTreeNode {

	private Region region;
	private RegionTreeNode parent;
	private List<RegionTreeNode> childNodes = new ArrayList<RegionTreeNode>();

	
	public AbstractRegionNode() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Region getRegion() {
		return region;
	}

	@Override
	public RegionTreeNode getParent() {
		return parent;
	}

	public void setParent(RegionTreeNode parent) {
		this.parent = parent;
	}

	@Override
	public Collection<RegionTreeNode> getChildren() {
		return Collections.unmodifiableCollection(childNodes);
	}

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

}
