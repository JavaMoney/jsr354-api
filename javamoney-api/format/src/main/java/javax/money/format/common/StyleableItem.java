/*
 * Copyright (c) 2012-2013, Credit Suisse
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.money.format.common;

import java.io.IOException;
import java.util.Locale;

/**
 * This interface is implemented by types that require to be represented as
 * {@link String} objects, e.g. for displaying.
 * 
 * @TODO check if this class can be moved to {@code java.util}.
 * 
 * @author Anatole Tresch
 */
public interface StyleableItem {

	/**
	 * Formats a value to a {@code String}. This method uses a {@link Locale} as
	 * an input parameter.
	 * 
	 * @param locale
	 *            the target {@link Locale}
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public String format(Locale locale);

	/**
	 * Formats a value to a {@code String}. This method uses a
	 * {@link LocalizationStyle} as an input parameter. Styles allows to define
	 * detailed and customized formatting input parameters. This allows to
	 * implement also complex formatting requirements using this interface.
	 * 
	 * @param style
	 *            The {@link LocalizationStyle} to be used.
	 * @return the formatted {@link String}
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public String format(LocalizationStyle style);

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
	 *            the locale to be used.
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
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
	 * @param style
	 *            the {@link LocalizationStyle} to be used.
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void print(Appendable appendable, LocalizationStyle style)
			throws IOException;
}
