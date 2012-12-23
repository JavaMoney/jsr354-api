/*
 *  Copyright 2012 Credit Suisse (Anatole Tresch)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package javax.money.format;

import java.io.IOException;
import java.util.Locale;

import javax.money.CurrencyUnit;

/**
 * Formats instances of {@link CurrencyUnit} to and from a String.
 */
public interface CurrencyFormatter extends Formatter<CurrencyUnit> {

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
	public String getSymbol(CurrencyUnit currency, Locale locale); // formatSymbol ?

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
	public String getSymbol(CurrencyUnit currency, LocalizationStyle style); // formatSymbol ?

	/**
	 * Formats a currency value to a {@code String}.
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
	public String getName(CurrencyUnit currency, Locale locale); // formatName ?

	/**
	 * Formats a currency's name to a {@code String}.
	 * 
	 * @param currency
	 *            's the currency to print, not null
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public String getName(CurrencyUnit currency, LocalizationStyle style); // formatName ?

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
