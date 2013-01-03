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

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.money.Amount;
import javax.money.CurrencyUnit;
import javax.money.AmountAdjuster;

/**
 * Defines the style that the amount of a monetary value will be formatted with.
 * <p>
 * The style contains a number of fields that may be configured based on the
 * locale:
 * <ul>
 * <li>character used for zero, which defined all the numbers from zero to nine
 * <li>character used for positive and negative symbols
 * <li>character used for the decimal point
 * <li>character used for grouping, such as grouping thousands
 * <li>size for each group, such as 3 for thousands
 * <li>whether to group the amount
 * <li>whether to always use a decimal point
 * </ul>
 * <p>
 * The style can be used in three basic ways.
 * <ul>
 * <li>set all the fields manually, resulting in the same amount style for all
 * locales
 * <li>call {@link #localize} manually and optionally adjust to set as required
 * <li>leave the localized fields as {@code null} and let the locale in the
 * formatter to determine the style
 * </ul>
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @author Stephen Colebourne
 * @author ANatole Tresch
 */
public final class MoneyAmountStyle implements Serializable {

	/**
	 * serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A style that uses ASCII digits/negative sign, the decimal point and
	 * groups large amounts in 3's using a comma. Forced decimal point is
	 * disabled.
	 */
	public static final MoneyAmountStyle ASCII_DECIMAL_POINT_GROUP3_COMMA = new MoneyAmountStyle(
			'0', '+', '-', '.', ',', 3, false);
	/**
	 * A style that uses ASCII digits/negative sign, the decimal point and
	 * groups large amounts in 3's using a space. Forced decimal point is
	 * disabled.
	 */
	public static final MoneyAmountStyle ASCII_DECIMAL_POINT_GROUP3_SPACE = new MoneyAmountStyle(
			'0', '+', '-', '.', ' ', 3, false);
	/**
	 * A style that uses ASCII digits/negative sign, the decimal point and no
	 * grouping of large amounts. Forced decimal point is disabled.
	 */
	public static final MoneyAmountStyle ASCII_DECIMAL_POINT_NO_GROUPING = new MoneyAmountStyle(
			'0', '+', '-', '.', ',', 0, false);
	/**
	 * A style that uses ASCII digits/negative sign, the decimal comma and
	 * groups large amounts in 3's using a dot. Forced decimal point is
	 * disabled.
	 */
	public static final MoneyAmountStyle ASCII_DECIMAL_COMMA_GROUP3_DOT = new MoneyAmountStyle(
			'0', '+', '-', ',', '.', 3, false);
	/**
	 * A style that uses ASCII digits/negative sign, the decimal comma and
	 * groups large amounts in 3's using a space. Forced decimal point is
	 * disabled.
	 */
	public static final MoneyAmountStyle ASCII_DECIMAL_COMMA_GROUP3_SPACE = new MoneyAmountStyle(
			'0', '+', '-', ',', ' ', 3, false);
	/**
	 * A style that uses ASCII digits/negative sign, the decimal point and no
	 * grouping of large amounts. Forced decimal point is disabled.
	 */
	public static final MoneyAmountStyle ASCII_DECIMAL_COMMA_NO_GROUPING = new MoneyAmountStyle(
			'0', '+', '-', ',', '.', 0, false);

	/** Defines the grouping sizes. */
	private int[] groupSizes = new int[] { 3 };
	/** Defines the grouping characters. */
	private char[] groupChars;
	/** FLags if a decimal point should be enforced. */
	private boolean decimalPointEnforced = false;
	/** Defines the rounding used to display the amount. */
	private AmountAdjuster rounding;
	/** Defines the character to be shown, when the value is zero. */
	private Character zeroCharacter = null;
	/** defines the decimal point character to be used. */
	private Character decimalPointCharacter;
	/** Defines the character to be used as a sign for positive values. */
	private char[] positiveSign = new char[0];
	/** Defines the text to be used as a sign for negative values. */
	private char[] negativeSign = new char[] { '-' };
	/** Defines the position of the characters to be used as a sign. */
	private SignPlacement placement = SignPlacement.BEFORE;

	/**
	 * Creates a new MoneyAmountStyle instance.
	 * <p>
	 * This creates a localized style using the given locale. Grouping will be
	 * enabled, forced decimal point will be disabled.
	 * 
	 * @param locale
	 *            the locale to use, not null
	 * @return the new instance, never null
	 */
	public MoneyAmountStyle of(Locale locale) {
		// TODO Not Implemented yet
		return null;
	}

	/**
	 * Creates a new MoneyAmountStyle instance.
	 * <p>
	 * This creates a localized style using the specified locale. Grouping will
	 * be enabled, forced decimal point will be disabled.
	 * 
	 * @param locale
	 *            the locale to use, not null
	 * @return the new instance, never null
	 */
	public MoneyAmountStyle() {

	}

	// -----------------------------------------------------------------------
	/**
	 * Constructor, creating a new monetary instance.
	 * 
	 * @param zeroCharacter
	 *            the zero character
	 * @param postiveCharacter
	 *            the positive sign
	 * @param negativeCharacter
	 *            the negative sign
	 * @param decimalPointCharacter
	 *            the decimal point character
	 * @param groupingCharacter
	 *            the grouping character
	 * @param groupingSize
	 *            the grouping size
	 * @param group
	 *            whether to use the group character
	 * @param forceDecimalPoint
	 *            whether to always use the decimal point character
	 */
	public MoneyAmountStyle(int zeroCharacter, int positiveCharacter,
			int negativeCharacter, int decimalPointCharacter,
			int groupingCharacter, int groupingSize, boolean forceDecimalPoint) {
		// TODO Not Implemented yet
	}

	public MoneyAmountStyle(int zeroCharacter, int positiveCharacter,
			int negativeCharacter, int decimalPointCharacter,
			int[] groupingCharacters, int[] groupingSizes,
			boolean forceDecimalPoint) {
		// TODO Not Implemented yet
	}

	public MoneyAmountStyle(int zeroCharacter, int positiveCharacter,
			int negativeCharacter, SignPlacement signPlacement,
			int decimalPointCharacter, int groupingCharacter, int groupingSize,
			boolean forceDecimalPoint) {
		// TODO Not Implemented yet
	}

	public MoneyAmountStyle(int zeroCharacter, int positiveCharacter,
			int negativeCharacter, SignPlacement signPlacement,
			int decimalPointCharacter, int[] groupingCharacters,
			int[] groupingSizes, boolean forceDecimalPoint) {
		// TODO Not Implemented yet
	}

	public MoneyAmountStyle(int zeroCharacter, int positiveCharacter,
			int negativeCharacter, SignPlacement signPlacement,
			int decimalPointCharacter, int groupingCharacter, int groupingSize,
			boolean forceDecimalPoint, AmountAdjuster rounding) {
		// TODO Not Implemented yet
	}

	public MoneyAmountStyle(int zeroCharacter, int positiveCharacter,
			int negativeCharacter, SignPlacement signPlacement,
			int decimalPointCharacter, int[] groupingCharacters,
			int[] groupingSizes, boolean forceDecimalPoint, AmountAdjuster rounding) {
		// TODO Not Implemented yet
	}

	/**
	 * Configures the sign used to format positive amounts.
	 * 
	 * @param sign
	 *            The sign to be used.
	 * @return the instance for chaining, never null
	 */
	public MoneyAmountStyle setPositiveSign(String sign) {
		// TODO
		return this;
	}

	/**
	 * Configures the sign used to format negative amounts.
	 * 
	 * @param sign
	 *            The sign to be used.
	 * @return the instance for chaining, never null
	 */
	public MoneyAmountStyle setNegativeSign(String sign) {
		// TODO
		return this;
	}

	/**
	 * Configures the sign used to format negative amounts.
	 * 
	 * @param sign
	 *            The sign to be used.
	 * @return the instance for chaining, never null
	 */
	public MoneyAmountStyle setNegativeSign(char... sign) {
		// TODO
		return this;
	}

	/**
	 * Returns a copy of this style with the specified sign placement.
	 * 
	 * @param signPlacement
	 *            the signPlacement used
	 * @see SignPlacement
	 * @return the new instance for chaining, never null
	 */
	public MoneyAmountStyle setSignPlacement(SignPlacement placement) {
		// TODO
		return this;
	}

	/**
	 * Defines the sizes of grouping. The first number is used for the first
	 * group size, the second for the next, etc. The last size is used for all
	 * subsequent group sizes.
	 * <p>
	 * <ul>
	 * <li>3 uses 3 for all groupings as a size. Example: 1'234'567.00</li>
	 * <li>2,2,3" uses 2 for the first and second grouping, but 3 for all
	 * subsequent groupings. Example: 123,123,45,67.00</li>
	 * </ul>
	 * <p>
	 * Note that the grouping characters can similarly configured using the
	 * {@link #setGroupingChars(char...)} method.
	 * 
	 * @param groupChars
	 *            the characters to be used for grouping.
	 * @return the new instance for chaining, never null
	 */
	public MoneyAmountStyle setGroupingSizes(int... groupSizes) {
		// TODO Not Implemented yet
		return null;
	}

	/**
	 * Defines which characters should be used for grouping. The first character
	 * is used for the first position, the second for the next position etc. The
	 * last character is used for all subsequent grouping characters.
	 * <p>
	 * <ul>
	 * <li>"'" uses "'" for all groupings as a character. Example: 1'234'567.00</li>
	 * <li>",'" uses "," for the first grouping, but "'" for all subsequent
	 * groupings. Example: 1'234,567.00</li>
	 * </ul>
	 * <p>
	 * Note that the grouping sizes can similarly configured using the
	 * {@link #setGroupingSizes(int[])} method.
	 * 
	 * @param groupChars
	 *            the characters to be used for grouping.
	 * @return the new instance for chaining, never null
	 */
	public MoneyAmountStyle setGroupingChars(char... groupChars) {
		// TODO Not Implemented yet
		return null;
	}

	/**
	 * Evaluates if grouping of the amount is required.
	 * 
	 * @return true, if grouping is required.
	 */
	public boolean isGrouping() {
		return groupSizes != null && groupSizes.length > 0;
	}

	/**
	 * Returns a {@code MoneyAmountStyle} instance configured for the specified
	 * locale.
	 * <p>
	 * This method will return a new instance where each field that was defined
	 * to be localized (by being set to {@code null}) is filled in. If this
	 * instance is fully defined (with all fields non-null), then this method
	 * has no effect. Once this method is called, no method will return null.
	 * <p>
	 * The settings for the locale are pulled from {@link DecimalFormatSymbols}
	 * and {@link DecimalFormat}.
	 * 
	 * @param locale
	 *            the locale to use, not null
	 * @return the new instance for chaining, never null
	 */
	public MoneyAmountStyle setLocale(Locale locale) {
		// TODO Not Implemented yet
		return this;
	}

	/**
	 * Gets the character used for zero, and defining the characters zero to
	 * nine.
	 * <p>
	 * The UTF-8 standard supports a number of different numeric scripts. Each
	 * script has the characters in order from zero to nine. This method returns
	 * the zero character, which therefore also defines one to nine.
	 * 
	 * @return the zero character, null if to be determined by locale
	 */
	public Character getZeroCharacter() {
		// TODO Not Implemented yet
		return null;
	}

	/**
	 * Returns a copy of this style with the specified zero character.
	 * <p>
	 * The UTF-8 standard supports a number of different numeric scripts. Each
	 * script has the characters in order from zero to nine. This method sets
	 * the zero character, which therefore also defines one to nine.
	 * <p>
	 * For English, this is a '0'. Some other scripts use different characters
	 * for the numbers zero to nine.
	 * 
	 * @param zeroCharacter
	 *            the zero character, null if to be determined by locale
	 * @return the new instance for chaining, never null
	 */
	public MoneyAmountStyle setZeroCharacter(Character zeroCharacter) {
		// TODO Not Implemented yet
		return this;
	}

	// -----------------------------------------------------------------------

	/**
	 * Get the sizes of groups created.
	 * <p>
	 * <ul>
	 * <li>3 uses 3 for all groupings as a size. Example: 1'234'567.00</li>
	 * <li>2,2,3" uses 2 for the first and second grouping, but 3 for all
	 * subsequent groupings. Example: 123,123,45,67.00</li>
	 * </ul>
	 * 
	 * @return the groupSizes
	 */
	public final int[] getGroupingSizes() {
		return groupSizes;
	}

	/**
	 * Get the rounding used.
	 * 
	 * @return the rounding
	 */
	public final AmountAdjuster getRounding() {
		return rounding;
	}

	/**
	 * Set the rounding to be used, by default
	 * {@link CurrencyUnit#getDefaultFractionDigits()} is used to determine
	 * rounding.
	 * 
	 * @param rounding
	 *            the rounding to be used.
	 */
	public final MoneyAmountStyle setRounding(AmountAdjuster rounding) {
		this.rounding = rounding;
		return this;
	}

	/**
	 * Gets whether to always use the decimal point, even if there is no
	 * fraction.
	 * 
	 * @return whether to force the decimal point on output
	 */
	public final boolean isDecimalPointEnforced() {
		return decimalPointEnforced;
	}

	/**
	 * Returns a copy of this style with the specified decimal point setting.
	 * 
	 * @param forceDecimalPoint
	 *            true to force the use of the decimal point, false to use it if
	 *            required
	 * @return the new instance for chaining, never null
	 */
	public final MoneyAmountStyle setDecimalPointEnforced(
			boolean decimalPointEnforced) {
		this.decimalPointEnforced = decimalPointEnforced;
		return this;
	}

	/**
	 * Gets the character used for the decimal point.
	 * 
	 * @return the decimal point character, null if to be determined by locale
	 */
	public final Character getDecimalPointCharacter() {
		return decimalPointCharacter;
	}

	/**
	 * @param decimalPointCharacter
	 *            the decimalPointCharacter to set
	 */
	public final MoneyAmountStyle setDecimalPointCharacter(
			Character decimalPointCharacter) {
		this.decimalPointCharacter = decimalPointCharacter;
		return this;
	}

	/**
	 * @return the positiveValue
	 */
	public final char[] getPositiveSign() {
		return positiveSign.clone();
	}

	/**
	 * Configures the sign used to format positive amounts.
	 * 
	 * @param sign
	 *            The sign to be used.
	 * @return the instance for chaining, never null
	 */
	public final MoneyAmountStyle setPositiveSign(char... positiveSign) {
		this.positiveSign = positiveSign;
		return this;
	}

	/**
	 * @return the negativeValue
	 */
	public final char[] getNegativeSign() {
		return negativeSign.clone();
	}

	/**
	 * @return the groupChars
	 */
	public final char[] getGroupingChars() {
		return groupChars;
	}

	/**
	 * @return the placement
	 */
	public final SignPlacement getPlacement() {
		return placement;
	}

	// -----------------------------------------------------------------------
	/**
	 * Compares this style with another.
	 * 
	 * @param other
	 *            the other style, null returns false
	 * @return true if equal
	 */
	@Override
	public boolean equals(Object other) {
		// TODO Not Implemented yet
		return false;
	}

	/**
	 * A suitable hash code.
	 * 
	 * @return a hash code
	 */
	@Override
	public int hashCode() {
		// TODO Not Implemented yet
		return 0;
	}

	// -----------------------------------------------------------------------
	/**
	 * Gets a string summary of the style.
	 * 
	 * @return a string summarising the style, never null
	 */
	@Override
	public String toString() {
		// TODO Not Implemented yet
		return null;
	}

}
