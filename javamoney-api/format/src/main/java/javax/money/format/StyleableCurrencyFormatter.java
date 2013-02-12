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
package javax.money.format;

import java.io.IOException;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.format.common.FormatException;
import javax.money.format.common.LocalizationStyle;
import javax.money.format.common.StyleableFormatter;

/**
 * Formats instances of {@link CurrencyUnit} to and from a String. TODO see
 * Formatter, maybe rename to *Printer like suggested by Joda sandbox
 */
public interface StyleableCurrencyFormatter extends
		StyleableFormatter<CurrencyUnit> {

	/**
	 * Formats a currency's symbol value to a {@code String}.
	 * 
	 * @param currency
	 *            the currency to print, not null
	 * @param locale
	 *            The target {@link Locale}.
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public String getSymbol(CurrencyUnit currency, Locale locale); // formatSymbol
																	// ?

	/**
	 * Formats a currency's symbol value to a {@code String}.
	 * 
	 * @param currency
	 *            the currency to print, not null
	 * @param style
	 *            The target {@link LocaliazationStyle}.
	 * @param locale
	 *            the target {@link Locale}.
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public String getSymbol(CurrencyUnit currency, LocalizationStyle style); // formatSymbol
																				// ?

	/**
	 * Formats a currency's display name to a {@code String}.
	 * 
	 * @param currency
	 *            the currency to print, not null
	 * @param locale
	 *            The target {@link Locale}.
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public String getDisplayName(CurrencyUnit currency, Locale locale);

	/**
	 * Formats a currency's display name to a {@code String}.
	 * 
	 * @param currency
	 *            's the currency to print, not null
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public String getDisplayName(CurrencyUnit currency, LocalizationStyle style); // formatName
																					// ?

	/**
	 * Formats a currency minor unit name to a {@code String}.
	 * 
	 * @param currency
	 *            the currency to print, not null
	 * @param locale
	 *            The target {@link Locale}.
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public String getMinorUnitName(CurrencyUnit currency, Locale locale);

	/**
	 * Formats a currency's minor unit name to a {@code String}.
	 * 
	 * @param currency
	 *            's the currency to print, not null
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public String getMinorUnitName(CurrencyUnit currency,
			LocalizationStyle style); // formatName ?

	/**
	 * Prints an currency's symbol value to an {@code Appendable} converting any
	 * {@code IOException} to a {@code MoneyFormatException}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder},
	 * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
	 * and {@code StringBuffer} never throw an {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param currency
	 *            the currency to print, not null
	 * @param locale
	 *            The target locale.
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void printSymbol(Appendable appendable, CurrencyUnit currency,
			Locale locale) throws IOException;

	/**
	 * Prints a currency's symbol value to an {@code Appendable} converting any
	 * {@code IOException} to a {@code MoneyFormatException}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder},
	 * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
	 * and {@code StringBuffer} never throw an {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param moneyProvider
	 *            the money to print, not null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void printSymbol(Appendable appendable, CurrencyUnit currency,
			LocalizationStyle style) throws IOException;

	/**
	 * Prints a currency's name to an {@code Appendable} converting any
	 * {@code IOException} to a {@code MoneyFormatException}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder},
	 * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
	 * and {@code StringBuffer} never throw an {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param moneyProvider
	 *            the money to print, not null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void printName(Appendable appendable, CurrencyUnit currency,
			Locale locale) throws IOException;

	/**
	 * Prints a currency's name to an {@code Appendable} converting any
	 * {@code IOException} to a {@code MoneyFormatException}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder},
	 * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
	 * and {@code StringBuffer} never throw an {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param currency
	 *            the currency to print, not null
	 * @param LocalizationSettings
	 *            The style to be used.
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void printName(Appendable appendable, CurrencyUnit currency,
			LocalizationStyle style) throws IOException;

}
