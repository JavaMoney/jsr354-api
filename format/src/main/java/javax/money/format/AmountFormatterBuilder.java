/*
 *  Copyright 2009-2011 Stephen Colebourne
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

import java.util.Locale;

import javax.money.Amount;

/**
 * Provides the ability to build a formatter for amount values.
 * <p>
 * This class is mutable and intended for use by a single thread. A new instance
 * should be created for each use. The formatters produced by the builder are
 * immutable and thread-safe.
 */
public interface AmountFormatterBuilder {

	/**
	 * This sets the {@link LocalizationStyle} to be used, if none is eplicitly
	 * defined.
	 * 
	 * @param style
	 *            the target style
	 */
	public void setLocalization(LocalizationStyle style);

	/**
	 * This sets the {@link Locale} to be used, if none is eplicitly defined.
	 * The locale is copied into an according instance of
	 * {@link LocalizationStyle} by using {@link LocalizationStyle#of(Locale)}.
	 * 
	 * @param lo0cale
	 *            the target locale
	 */
	public void setLocale(Locale locale);

	// -----------------------------------------------------------------------
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
	public AmountFormatterBuilder append(Formatter<Amount> formatter);

	// -----------------------------------------------------------------------
	/**
	 * Builds the formatter from the builder using the default locale.
	 * <p>
	 * Once the builder is in the correct state it must be converted to a
	 * {@code AmountFormatter} to be used. Calling this method does not change
	 * the state of this instance, so it can still be used.
	 * <p>
	 * This method uses the default locale within the returned formatter. It can
	 * be changed by calling {@link AmountFormatter#withLocale(Locale)}.
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
	 * This method uses the specified locale within the returned formatter. It
	 * can be changed by calling {@link AmountFormatter#withLocale(Locale)}.
	 * 
	 * @param locale
	 *            the initial locale for the formatter, not null
	 * @return the formatter built from this builder, never null
	 */
	public AmountFormatter toFormatter(Locale locale);

	/**
	 * Builds the formatter from the builder setting the locale.
	 * <p>
	 * Once the builder is in the correct state it must be converted to a
	 * {@code AmountFormatter} to be used. Calling this method does not change
	 * the state of this instance, so it can still be used.
	 * <p>
	 * This method uses the specified locale within the returned formatter. It
	 * can be changed by calling {@link AmountFormatter#withLocale(Locale)}.
	 * 
	 * @param locale
	 *            the initial locale for the formatter, not null
	 * @return the formatter built from this builder, never null
	 */
	public AmountFormatter toFormatter(LocalizationStyle style);
}
