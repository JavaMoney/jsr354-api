package net.java.javamoney.ri.ext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.money.ext.Region;
import javax.money.ext.RegionFilter;
import javax.money.ext.RegionNode;

public abstract class AbstractRegionNode implements RegionNode {

	private Region region;
	private RegionNode parent;
	private List<RegionNode> childNodes = new ArrayList<RegionNode>();

	
	public AbstractRegionNode() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Region getRegion() {
		return region;
	}

	@Override
	public RegionNode getParent() {
		return parent;
	}

	public void setParent(RegionNode parent) {
		this.parent = parent;
	}

	@Override
	public Collection<RegionNode> getChildren() {
		return Collections.unmodifiableCollection(childNodes);
	}

	@Override
	public boolean contains(Region region) {
		for (RegionNode regionNode : childNodes) {
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
	public RegionNode selectParent(RegionFilter filter) {
		RegionNode regionNode = parent;
		while (regionNode != null) {
			if (filter.accept(regionNode)) {
				return regionNode;
			}
			regionNode = regionNode.getParent();
		}
		return null;
	}

	@Override
	public Collection<RegionNode> select(RegionFilter filter) {
		List<RegionNode> result = new ArrayList<RegionNode>();
		for (RegionNode regionNode : childNodes) {
			if (filter.accept(regionNode)) {
				result.add(regionNode);
			}
		}
		return result;
	}

	@Override
	public RegionNode getRegionTree(String path) {
		String[] paths = path.split("/");
		RegionNode current = this;
		for (String pathElem : paths) {
			current = current.getSubRegion(pathElem.trim());
			if (current == null) {
				throw new IllegalArgumentException("path not found: " + path);
			}
		}
		return current;
	}

}
