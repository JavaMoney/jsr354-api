/*
 * Copyright (c) 2012, Credit Suisse (Anatole Tresch), Werner Keil
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
 *  * Neither the name of JSR-310 nor the names of its contributors
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

/**
 * Formats instances of number to and from a String.
 * <p>
 * Instances of {@code NumberPrinterParser} can be created by
 * {@code NumberPrinterParserFactory}.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @author Anatole Tresch
 */
public interface PrinterParser<T> {

	/**
	 * Formats a monetary value to a {@code String}.
	 * 
	 * @param money
	 *            the money to print, not null
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public String print(T item, LocalizationStyle style);
	
	/**
	 * Formats a monetary value to a {@code String}.
	 * 
	 * @param money
	 *            the money to print, not null
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws FormatException
	 *             if there is a problem while printing
	 */
	public String print(T item, Locale style);
	
	
	/**
	 * Prints a monetary value to an {@code Appendable} converting any
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
	public void print(Appendable appendable, T item, LocalizationStyle style)
			throws IOException;

	/**
	 * Fully parses the text into a {@code Money} requiring that the parsed
	 * amount has the correct number of decimal places.
	 * <p>
	 * The parse must complete normally and parse the entire text (currency and
	 * amount). If the parse completes without reading the entire length of the
	 * text, an exception is thrown. If any other problem occurs during parsing,
	 * an exception is thrown.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @return the parsed monetary value, never null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws FormatException
	 *             if there is a problem while parsing
	 * @throws ArithmeticException
	 *             if the scale of the parsed money exceeds the scale of the
	 *             currency
	 */
	public T parse(CharSequence text, LocalizationStyle locale)throws ParseException;
	
	/**
	 * Fully parses the text into a {@code Money} requiring that the parsed
	 * amount has the correct number of decimal places.
	 * <p>
	 * The parse must complete normally and parse the entire text (currency and
	 * amount). If the parse completes without reading the entire length of the
	 * text, an exception is thrown. If any other problem occurs during parsing,
	 * an exception is thrown.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @return the parsed monetary value, never null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws FormatException
	 *             if there is a problem while parsing
	 * @throws ArithmeticException
	 *             if the scale of the parsed money exceeds the scale of the
	 *             currency
	 */
	public T parse(CharSequence text, Locale locale)throws ParseException;
	
}
