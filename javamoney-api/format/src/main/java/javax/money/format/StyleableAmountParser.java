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

import java.util.Locale;

import javax.money.MonetaryAmount;
import javax.money.CurrencyUnit;
import javax.money.format.common.FormatException;
import javax.money.format.common.LocalizationStyle;
import javax.money.format.common.ParseException;
import javax.money.format.common.StyleableParser;

/**
 * Formats instances of money to and from a String.
 * <p>
 * Instances of {@code MoneyFormatter} can be created by
 * {@code MoneyFormatterBuilder}.
 * <p>
 * This class is immutable and thread-safe.
 */
public interface StyleableAmountParser extends StyleableParser<MonetaryAmount> {

	/**
	 * Fully parses a number and combines it with a {@link CurrencyUnit} to an
	 * {@link MonetaryAmount} instance. The amount of parsed decimals can hereby differ
	 * from the correct number of decimal places.
	 * <p>
	 * The parse must complete normally and parse the entire text (currency and
	 * amount). If the parse completes without reading the entire length of the
	 * text, an exception is thrown. If any other problem occurs during parsing,
	 * an exception is thrown.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @param currency
	 *            the target currency to be used (not the text to parse contains
	 *            only a number).
	 * @param style
	 *            The target localization style determining the input format.
	 * @return the parsed monetary value, never null
	 * 
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws FormatException
	 *             if there is a problem while parsing
	 * @throws ArithmeticException
	 *             if the scale of the parsed money exceeds the scale of the
	 *             currency
	 */
	public MonetaryAmount parseNumber(CharSequence text, CurrencyUnit currency,
			LocalizationStyle style) throws ParseException;

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
	 * @param currency
	 *            the target currency to be used (not the text to parse contains
	 *            only a number).
	 * @param locale
	 *            The target locale determining the input format.
	 * @return the parsed monetary value, never null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws FormatException
	 *             if there is a problem while parsing
	 * @throws ArithmeticException
	 *             if the scale of the parsed money exceeds the scale of the
	 *             currency
	 */
	public MonetaryAmount parseNumber(CharSequence text, CurrencyUnit currency,
			Locale locale) throws ParseException;

}
