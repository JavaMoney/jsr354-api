/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.format;

import java.io.IOException;


/**
 * Formats instances of T to a {@link String} or an {@link Appendable}.
 * Instances of this can be added to a {@link ItemFormatBuilder} to assemble a
 * complex input/output format.
 * 
 * @author Anatole Tresch
 */
public interface FormatToken<T> {

	/**
	 * Prints an item value to an {@code Appendable}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder},
	 * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
	 * and {@code StringBuffer} never throw an {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param item
	 *            the item to print, not null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void print(Appendable appendable, T item, LocalizationStyle style)
			throws IOException;

	/**
	 * Parses an item from the given {@link ParseContext}. Any parsed item can
	 * be added to the {@link ParseContext} using
	 * {@link ParseContext#addParseResult(Object, Object)} as results. At the end of
	 * the parsing process an instance of {@link ItemFactory} is transferring
	 * the results parsed into the target item to be parsed.
	 * 
	 * @param context
	 *            the parse context
	 * @throws ItemParseException
	 *             thrown, if parsing fails.
	 */
	public void parse(ParseContext<T> context, LocalizationStyle style)
			throws ItemParseException;

}
