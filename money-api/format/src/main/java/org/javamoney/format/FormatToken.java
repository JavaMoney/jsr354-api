/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package org.javamoney.format;

import java.io.IOException;
import java.util.Locale;

/**
 * Formats instances of {@code T} to a {@link String} or an {@link Appendable}.
 * Instances of {@link FormatToken} can be added to a {@link ItemFormatBuilder}
 * to assemble a complex input/output {@link ItemFormat} using a programmatic
 * fluent API. Hereby each {@link FormatToken} instance represent a part of the
 * overall formatted String. Similarly when parsing an input by calling
 * {@link ItemFormat#parse(CharSequence, Locale)} each {@link FormatToken} can
 * read and forward the current {@link ParseContext}, or through an error, if
 * the input does not provide a parseable input for the given
 * {@link FormatToken}.
 * <p>
 * Classes implementing this interface are required to be thread-safe and
 * immutable.
 * 
 * @author Anatole Tresch
 */
public interface FormatToken<T> {

	/**
	 * Return a token id. This id is used for assigning attributes from a
	 * configuring {@link LocalizationStyle} the to different tokens loaded
	 * within a {@link ItemFormat} based on several {@link FormatToken}
	 * instances. This allows to use the id as a prefix for the attribute key in
	 * the configuring {@link LocalizationStyle}<br/>
	 * <ul>
	 * <li>to allow to have the same class of tokens multiple times to a
	 * {@link ItemFormatBuilder}, but setting different ids allow to configure
	 * the tokens separately.</li>
	 * <li>to allow to have the same instance of a token to be added multiple
	 * times to a {@link ItemFormatBuilder} and reading the same attribute from
	 * the configuring {@link LocalizationStyle}.</li>
	 * </ul>
	 * In more detail for accessing a key {@code xx}:
	 * <ul>
	 * <li>Lookup the key {@code tokenId.xx}, where {@code tokenId} is the non
	 * null value returned by the {@link FormatToken} to configured, if the
	 * value is not {@code null} use this value.</li>
	 * <li>Lookup the key {@code fqn.xx}, {@code fqn} is the fully qualified
	 * class name of the {@link FormatToken} to configured, if the value is not
	 * {@code null} use this value.</li>
	 * <li>Lookup the key {@code scn.xx}, {@code scn} is the simple class name
	 * of the {@link FormatToken} to configured, if the value is not
	 * {@code null} use this value.</li>
	 * <li>Lookup the key {@code xx}, if the value is not {@code null}, use this
	 * value.</li>
	 * return {@code null} for this configuration entry. The according
	 * FormatToken implementation now must decide, if a default value should be
	 * applied (recommended in most cases) or a {@link ItemFormatException}
	 * should be thrown.</li>
	 * </ul>
	 * 
	 * @return the token id, never {@code null}.
	 */
	public String getTokenId();

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
	 * @param locale
	 *            the {@link Locale} to be used, not null.
	 * @param style
	 *            the {@link LocalizationStyle} to be used.
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void print(Appendable appendable, T item, Locale locale,
			LocalizationStyle style)
			throws IOException;

	/**
	 * Parses an item from the given {@link ParseContext}. Any parsed item can
	 * be added to the {@link ParseContext} using
	 * {@link ParseContext#addParseResult(Object, Object)} as results. At the
	 * end of the parsing process an instance of {@link ItemFactory} is
	 * transferring the results parsed into the target item to be parsed.
	 * 
	 * @param context
	 *            the parse context
	 * @param locale
	 *            the {@link Locale} to be used, not null.
	 * @param style
	 *            the {@link LocalizationStyle} to be used.
	 * @throws ItemParseException
	 *             thrown, if parsing fails.
	 */
	public void parse(ParseContext<T> context, Locale locale,
			LocalizationStyle style)
			throws ItemParseException;

}
