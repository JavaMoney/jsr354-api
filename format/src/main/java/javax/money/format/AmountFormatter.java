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

import javax.money.Amount;

/**
 * Formats instances of {@link Amount}, by default the full amopunt is printed,
 * whereas some method allows also to print the number part without currency.
 * <p>
 * Instances of {@code AmountFormatter} can be created by
 * {@code AmountFormatterBuilder} or by accessing instances from the the
 * {@link MoneyFormat} singleton.
 */
public interface AmountFormatter extends Formatter<Amount> {

	/**
	 * Formats a amount's numeric value to a {@code String}, the currency is
	 * omitted.
	 * 
	 * @param amount
	 *            the amount to print, not null
	 * @param style
	 *            The localization settings to be used.,
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public String printNumber(Amount amount, LocalizationStyle style);

	/**
	 * Formats a amount's numeric value to a {@code String}, the currency is
	 * omitted.
	 * 
	 * @param amount
	 *            the amount to print, not null
	 * @param locale
	 *            The {@link Locale} to be used. This instance is used to create
	 *            a simple {@link LocalizationStyle} using
	 *            {@link LocalizationStyle#of(Locale)}.
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public String printNumber(Amount item, Locale locale);

	/**
	 * Formats a amount's numeric value to a {@code Appendable}, the currency is
	 * omitted.
	 * 
	 * @param appendable
	 *            the appendable to print to, not null
	 * @param amount
	 *            the amount to print, not null
	 * @param locale
	 *            The {@link Locale} to be used. This instance is used to create
	 *            a simple {@link LocalizationStyle} using
	 *            {@link LocalizationStyle#of(Locale)}.
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public void printNumber(Appendable appendable, Amount amount, Locale locale)
			throws IOException;

	/**
	 * Formats a amount's numeric value to a {@code Appendable}, the currency is
	 * omitted.
	 * 
	 * @param appendable
	 *            the appendable to print to, not null
	 * @param amount
	 *            the amount to print, not null
	 * @param style
	 *            The localization settings to be used.
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public void printNumber(Appendable appendable, Amount item,
			LocalizationStyle style) throws IOException;

}
