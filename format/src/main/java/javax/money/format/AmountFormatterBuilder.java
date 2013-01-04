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

/**
 * Provides the ability to build a formatter for amount values.
 * <p>
 * This class is mutable and intended for use by a single thread. A new instance
 * should be created for each use. The formatters produced by the builder are
 * immutable and thread-safe.
 */
public interface AmountFormatterBuilder {

	/**
	 * This sets the {@link LocalizationStyle} to be used, if none is explicitly
	 * passed as parameters to the methods called.
	 * <p>
	 * A {@link LocalizationStyle} allows to define detailed and customized
	 * formatting input parameters. This allows to implement also complex
	 * formatting requirements using this interface.
	 * 
	 * @param style
	 *            the style to be used by default by this builder.
	 */
	public void setLocalization(LocalizationStyle style);

	/**
	 * This sets the {@link Locale} to be used, if none is explicitly defined.
	 * The locale is wrapped into an according instance of
	 * {@link LocalizationStyle} by using {@link LocalizationStyle#of(Locale)}.
	 * 
	 * @param locale
	 *            the target locale
	 */
	public void setLocale(Locale locale);

	/**
	 * Appends the amount to the builder using the default format. The default
	 * format equals to the format that is used by the {@link AmountFormatter}
	 * instance returned for the current default {@link Locale}.
	 * 
	 * @return this, for chaining, never null
	 */
	public AmountFormatterBuilder appendAmount();

	/**
	 * Appends the amount to the builder using the given {@link Locale}. The
	 * format equals to the format that is used by the {@link AmountFormatter}
	 * instance returned for the given {@link Locale}.
	 * 
	 * @param locale
	 *            the required {@link Locale}. This locale is ued to create an
	 *            according {@link LocalizationStyle}, by calling
	 *            {@link LocalizationStyle#of(Locale)}.
	 * @return this, for chaining, never null
	 */
	public AmountFormatterBuilder appendAmountLocalized();

	/**
	 * Appends the amount to the builder using the specified amount style.
	 * <p>
	 * The amount is the value itself, such as '12.34'.
	 * <p>
	 * The amount style allows the formatting of the number to be controlled in
	 * detail. See {@link MoneyAmountStyle} for more details.
	 * 
	 * @param style
	 *            the required style to use, not null.
	 * @return this, for chaining, never null
	 */
	public AmountFormatterBuilder appendAmount(MoneyAmountStyle style);

	// -----------------------------------------------------------------------
	/**
	 * Appends the currency code to the builder, e.g. the three letter ISO code,
	 * such as 'GBP'.
	 * 
	 * @return this, for chaining, never null
	 */
	public AmountFormatterBuilder appendCurrencyCode();

	/**
	 * Appends the currency code to the builder, e.g. an ISO numeric code, such
	 * as '826'. If the numeric is not defined for the given currency, nothing
	 * will be printed..
	 * 
	 * @return this, for chaining, never null
	 */
	public AmountFormatterBuilder appendCurrencyNumericCode();

	/**
	 * Appends the localized currency symbol to the builder.
	 * <p>
	 * The localized currency symbol is the symbol as chosen by the
	 * {@link LocalizationStyle} of the formatter.
	 * <p>
	 * Symbols cannot be parsed.
	 * 
	 * @return this, for chaining, never null
	 */
	public AmountFormatterBuilder appendCurrencySymbolLocalized();

	/**
	 * Appends a literal to the builder.
	 * 
	 * @param literal
	 *            the literal to append, null or empty ignored
	 * @return this, for chaining, never null
	 */
	public AmountFormatterBuilder appendLiteral(CharSequence literal);

	// -----------------------------------------------------------------------
	/**
	 * Appends the output from the specified formatter to this builder.
	 * <p>
	 * If the specified formatter cannot print, then the the output of this
	 * builder will be unable to print. If the specified formatter cannot parse,
	 * then the output of this builder will be unable to parse.
	 * 
	 * @param formatter
	 *            the formatter to append, not null
	 * @return this for chaining, never null
	 */
	public AmountFormatterBuilder append(Formatter<MonetaryAmount> formatter);

	// -----------------------------------------------------------------------
	/**
	 * Builds the formatter from the builder using the default locale.
	 * <p>
	 * Once the builder is in the correct state it must be converted to a
	 * {@code AmountFormatter} to be used. Calling this method does not change
	 * the state of this instance, so it can still be used.
	 * <p>
	 * This method uses the specified locale within the returned formatter. It
	 * can be changed by calling {@link AmountFormatter#toFormatter(Locale)} or
	 * {@link AmountFormatter#toFormatter(LocalizationStyle)}.
	 * 
	 * @return the formatter built from this builder, never null
	 */
	public AmountFormatter toFormatter();

	/**
	 * Builds the formatter from the builder setting the locale.
	 * <p>
	 * Once the builder is in the correct state it must be converted to a
	 * {@code AmountFormatter} to be used. Calling this method does not change
	 * the state of this instance, so it can still be used.
	 * <p>
	 * This method uses the specified locale. FOr extended style parameters use
	 * {@link AmountFormatter#toFormatter(LocalizationStyle)} instead of.
	 * 
	 * @param locale
	 *            the locale for the formatter, not null
	 * @return the formatter built from this builder, never null
	 */
	public AmountFormatter toFormatter(Locale locale);

	/**
	 * Builds the formatter from the builder setting the locale.
	 * <p>
	 * Once the builder is in the correct state it must be converted to a
	 * {@code AmountFormatter} to be used. Calling this method does not change
	 * the state of this instance, so it can still be used.
	 * 
	 * @param style
	 *            the style for the formatter, not null
	 * @return the formatter built from this builder, never null
	 */
	public AmountFormatter toFormatter(LocalizationStyle style);
}
