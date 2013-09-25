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
package org.javamoney.ext;

import java.io.IOException;

import org.javamoney.ext.RegionTreeNode;

/**
 * Small utility class for printing out a {@link RegionTreeNode}.
 * 
 * @author Anatole Tresch
 */
public final class RegionPrinter {
	/**
	 * Singleton constructor.
	 */
	private RegionPrinter() {
	}

	/**
	 * Format a {@link RegionTreeNode} to text.
	 * 
	 * @param tree
	 *            the {@link RegionTreeNode} to be formatted
	 * @return the {@link RegionTreeNode} textual representation
	 */
	public static String getAsText(RegionTreeNode tree) {
		return getAsText(tree, "");
	}

	/**
	 * Format a {@link RegionTreeNode} to text.
	 * 
	 * @param tree
	 *            the {@link RegionTreeNode} to be formatted
	 * @param intend
	 *            the initial intend to be used
	 * @return the {@link RegionTreeNode} textual representation
	 */
	public static String getAsText(RegionTreeNode tree, String intend) {
		StringBuilder b = new StringBuilder();
		try {
			printTree(tree, b, intend);
		} catch (IOException e) {
			e.printStackTrace(); // TODO
			b.append("Error: " + e);
		}
		return b.toString();
	}

	/**
	 * Print the whole tree into the given {@link Appendable}.
	 * 
	 * @param tree
	 *            the region tree
	 * @param appendable
	 *            the appendable
	 * @param intend
	 *            the initial intend
	 * @throws IOException
	 *             any exception fomr the appendable
	 */
	public static void printTree(RegionTreeNode tree, Appendable appendable,
			String intend)
			throws IOException {
		appendable.append(intend + tree.toString()).append("\n");
		intend = intend + "  ";
		for (RegionTreeNode region : tree.getChildren()) {
			printTree(region, appendable, intend);
		}
	}

}
