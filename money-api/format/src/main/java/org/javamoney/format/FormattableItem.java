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
 * This interface is implemented by types that require to be represented as
 * {@link String} objects, e.g. for displaying.
 * <p>
 * Classes implementing this interface must be thread-safe and should be
 * immutable.
 * 
 * @author Anatole Tresch
 */
public interface FormattableItem {

	/**
	 * Formats a value to a {@code String}. This method uses a
	 * {@link LocalizationStyle} as an input parameter. Styles allows to define
	 * detailed and customized formatting input parameters. This allows to
	 * implement also complex formatting requirements using this interface.
	 * 
	 * @param locale
	 *            The {@link Locale} to be used.
	 * @return the formatted {@link String}
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 */
	public String format(Locale locale);

	/**
	 * Formats a value to a {@code String}. This method uses a
	 * {@link LocalizationStyle} as an input parameter. Styles allows to define
	 * detailed and customized formatting input parameters. This allows to
	 * implement also complex formatting requirements using this interface.
	 * 
	 * @param locale
	 *            The {@link Locale} to be used.
	 * @param style
	 *            the {@link LocalizationStyle} to be attached to this
	 *            {@link ItemFormat}, which also contains the target
	 *            {@link Locale} instances to be used, as well as other
	 *            attributes configuring this instance.
	 * @return the formatted {@link String}
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 */
	public String format(Locale locale, LocalizationStyle style);

	/**
	 * Prints a item value to an {@code Appendable}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder},
	 * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
	 * and {@code StringBuffer} never throw an {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param style
	 *            the {@link Locale} to be used.
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void print(Appendable appendable, Locale locale) throws IOException;

	/**
	 * Prints a item value to an {@code Appendable}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder},
	 * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
	 * and {@code StringBuffer} never throw an {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param locale
	 *            The {@link Locale} to be used.
	 * @param style
	 *            the {@link LocalizationStyle} to be attached to this
	 *            {@link ItemFormat}, which also contains the target
	 *            {@link Locale} instances to be used, as well as other
	 *            attributes configuring this instance.
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void print(Appendable appendable, Locale locale,
			LocalizationStyle style)
			throws IOException;
}
