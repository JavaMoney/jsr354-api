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

import javax.money.MonetaryAmount;
import javax.money.format.common.FormatException;
import javax.money.format.common.LocalizationStyle;
import javax.money.format.common.StyleableItemFormatter;

/**
 * Formats instances of {@link MonetaryAmount}, by default the full amount is printed,
 * whereas some method allows also to print the number part without currency.
 * <p>
 * Instances of {@code AmountFormatter} can be created by
 * {@code AmountFormatterBuilder} or by accessing instances from the the
 * {@link MoneyFormat} singleton.
 * 
 * TODO see Formatter, maybe rename to *Printer like suggested by Joda sandbox
 */
public interface StyleableAmountFormatter extends StyleableItemFormatter<MonetaryAmount> {

	/**
	 * Formats a amount's numeric value to a {@code String}, the currency is
	 * omitted.
	 * <p>
	 * This method uses a {@link LocalizationStyle} as an input parameter.
	 * Styles allows to define detailed and customized formatting input
	 * parameters. This allows to implement also complex formatting requirements
	 * using this interface.
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
	public String printNumber(MonetaryAmount amount, LocalizationStyle style);

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
	public String printNumber(MonetaryAmount item, Locale locale);

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
	public void printNumber(Appendable appendable, MonetaryAmount amount, Locale locale)
			throws IOException;

	/**
	 * Formats a amount's numeric value to a {@code Appendable}, the currency is
	 * omitted.
	 * <p>
	 * This method uses a {@link LocalizationStyle} as an input parameter.
	 * Styles allows to define detailed and customized formatting input
	 * parameters. This allows to implement also complex formatting requirements
	 * using this interface.
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
	public void printNumber(Appendable appendable, MonetaryAmount item,
			LocalizationStyle style) throws IOException;

}
