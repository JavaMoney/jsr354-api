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

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Defines the style that the amount of a monetary value will be formatted with.
 * <p>
 * The style contains a number of fields that may be configured based on the locale:
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
 * <li>set all the fields manually, resulting in the same amount style for all locales
 * <li>call {@link #localize} manually and optionally adjust to set as required
 * <li>leave the localized fields as {@code null} and let the locale in the
 *  formatter to determine the style
 * </ul>
 * <p>
 * This class is immutable and thread-safe.
 * 
 * FIXME this should use enum if possible
 */
public final class MoneyAmountStyle implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * A style that uses ASCII digits/negative sign, the decimal point
     * and groups large amounts in 3's using a comma.
     * Forced decimal point is disabled.
     */
    public static final MoneyAmountStyle ASCII_DECIMAL_POINT_GROUP3_COMMA =
        new MoneyAmountStyle('0', '+', '-', '.', ',', 3, true, false);
    /**
     * A style that uses ASCII digits/negative sign, the decimal point
     * and groups large amounts in 3's using a space.
     * Forced decimal point is disabled.
     */
    public static final MoneyAmountStyle ASCII_DECIMAL_POINT_GROUP3_SPACE =
        new MoneyAmountStyle('0', '+', '-', '.', ' ', 3, true, false);
    /**
     * A style that uses ASCII digits/negative sign, the decimal point
     * and no grouping of large amounts.
     * Forced decimal point is disabled.
     */
    public static final MoneyAmountStyle ASCII_DECIMAL_POINT_NO_GROUPING =
        new MoneyAmountStyle('0', '+', '-', '.', ',', 3, false, false);
    /**
     * A style that uses ASCII digits/negative sign, the decimal comma
     * and groups large amounts in 3's using a dot.
     * Forced decimal point is disabled.
     */
    public static final MoneyAmountStyle ASCII_DECIMAL_COMMA_GROUP3_DOT =
        new MoneyAmountStyle('0', '+', '-', ',', '.', 3, true, false);
    /**
     * A style that uses ASCII digits/negative sign, the decimal comma
     * and groups large amounts in 3's using a space.
     * Forced decimal point is disabled.
     */
    public static final MoneyAmountStyle ASCII_DECIMAL_COMMA_GROUP3_SPACE =
        new MoneyAmountStyle('0', '+', '-', ',', ' ', 3, true, false);
    /**
     * A style that uses ASCII digits/negative sign, the decimal point
     * and no grouping of large amounts.
     * Forced decimal point is disabled.
     */
    public static final MoneyAmountStyle ASCII_DECIMAL_COMMA_NO_GROUPING =
        new MoneyAmountStyle('0', '+', '-', ',', '.', 3, false, false);
    /**
     * A style that will be filled in with localized values using the locale of the formatter.
     * Grouping is enabled. Forced decimal point is disabled.
     */
    public static final MoneyAmountStyle LOCALIZED_GROUPING =
        new MoneyAmountStyle(-1, -1, -1, -1, -1, -1, true, false);
    /**
     * A style that will be filled in with localized values using the locale of the formatter.
     * Grouping is disabled. Forced decimal point is disabled.
     */
    public static final MoneyAmountStyle LOCALIZED_NO_GROUPING =
        new MoneyAmountStyle(-1, -1, -1, -1, -1, -1, false, false);
    
    //-----------------------------------------------------------------------
    /**
     * Gets a localized style.
     * <p>
     * This creates a localized style for the specified locale.
     * Grouping will be enabled, forced decimal point will be disabled.
     *
     * @param locale  the locale to use, not null
     * @return the new instance, never null
     */
    public static MoneyAmountStyle of(Locale locale) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor, creating a new monetary instance.
     * 
     * @param zeroCharacter  the zero character
     * @param postiveCharacter  the positive sign
     * @param negativeCharacter  the negative sign
     * @param decimalPointCharacter  the decimal point character
     * @param groupingCharacter  the grouping character
     * @param groupingSize  the grouping size
     * @param group  whether to use the group character
     * @param forceDecimalPoint  whether to always use the decimal point character
     */
    private MoneyAmountStyle(
                int zeroCharacter,
                int positiveCharacter, int negativeCharacter,
                int decimalPointCharacter, int groupingCharacter,
                int groupingSize, boolean group, boolean forceDecimalPoint) {
    	// TODO Not Implemented yet
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a {@code MoneyAmountStyle} instance configured for the specified locale.
     * <p>
     * This method will return a new instance where each field that was defined
     * to be localized (by being set to {@code null}) is filled in.
     * If this instance is fully defined (with all fields non-null), then this
     * method has no effect. Once this method is called, no method will return null.
     * <p>
     * The settings for the locale are pulled from {@link DecimalFormatSymbols} and
     * {@link DecimalFormat}.
     * 
     * @param locale  the locale to use, not null
     * @return the new instance for chaining, never null
     */
    public MoneyAmountStyle localize(Locale locale) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the character used for zero, and defining the characters zero to nine.
     * <p>
     * The UTF-8 standard supports a number of different numeric scripts.
     * Each script has the characters in order from zero to nine.
     * This method returns the zero character, which therefore also defines one to nine.
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
     * The UTF-8 standard supports a number of different numeric scripts.
     * Each script has the characters in order from zero to nine.
     * This method sets the zero character, which therefore also defines one to nine.
     * <p>
     * For English, this is a '0'. Some other scripts use different characters
     * for the numbers zero to nine.
     * 
     * @param zeroCharacter  the zero character, null if to be determined by locale
     * @return the new instance for chaining, never null
     */
    public MoneyAmountStyle withZeroCharacter(Character zeroCharacter) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the character used for the positive sign character.
     * <p>
     * The standard ASCII symbol is '+'.
     * 
     * @return the format for positive amounts, null if to be determined by locale
     */
    public Character getPositiveSignCharacter() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy of this style with the specified positive sign character.
     * <p>
     * The standard ASCII symbol is '+'.
     * 
     * @param positiveCharacter  the positive character, null if to be determined by locale
     * @return the new instance for chaining, never null
     */
    public MoneyAmountStyle withPositiveSignCharacter(Character positiveCharacter) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the character used for the negative sign character.
     * <p>
     * The standard ASCII symbol is '-'.
     * 
     * @return the format for negative amounts, null if to be determined by locale
     */
    public Character getNegativeSignCharacter() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy of this style with the specified negative sign character.
     * <p>
     * The standard ASCII symbol is '-'.
     * 
     * @param negativeCharacter  the negative character, null if to be determined by locale
     * @return the new instance for chaining, never null
     */
    public MoneyAmountStyle withNegativeSignCharacter(Character negativeCharacter) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the character used for the decimal point.
     * 
     * @return the decimal point character, null if to be determined by locale
     */
    public Character getDecimalPointCharacter() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy of this style with the specified decimal point character.
     * <p>
     * For English, this is a dot.
     * 
     * @param decimalPointCharacter  the decimal point character, null if to be determined by locale
     * @return the new instance for chaining, never null
     */
    public MoneyAmountStyle withDecimalPointCharacter(Character decimalPointCharacter) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the character used to separate groups, typically thousands.
     * 
     * @return the grouping character, null if to be determined by locale
     */
    public Character getGroupingCharacter() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy of this style with the specified grouping character.
     * <p>
     * For English, this is a comma.
     * 
     * @param groupingCharacter  the grouping character, null if to be determined by locale
     * @return the new instance for chaining, never null
     */
    public MoneyAmountStyle withGroupingCharacter(Character groupingCharacter) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the size of each group, typically 3 for thousands.
     * 
     * @return the size of each group, null if to be determined by locale
     */
    public Integer getGroupingSize() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy of this style with the specified grouping size.
     * 
     * @param groupingSize  the size of each group, such as 3 for thousands,
     *          not zero or negative, null if to be determined by locale
     * @return the new instance for chaining, never null
     * @throws IllegalArgumentException if the grouping size is zero or less
     */
    public MoneyAmountStyle withGroupingSize(Integer groupingSize) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets whether to use the grouping separator, typically for thousands.
     * 
     * @return whether to use the grouping separator
     */
    public boolean isGrouping() {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Returns a copy of this style with the specified grouping setting.
     * 
     * @param grouping  true to use the grouping separator, false to not use it
     * @return the new instance for chaining, never null
     */
    public MoneyAmountStyle withGrouping(boolean grouping) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets whether to always use the decimal point, even if there is no fraction.
     * 
     * @return whether to force the decimal point on output
     */
    public boolean isForcedDecimalPoint() {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Returns a copy of this style with the specified decimal point setting.
     * 
     * @param forceDecimalPoint  true to force the use of the decimal point, false to use it if required
     * @return the new instance for chaining, never null
     */
    public MoneyAmountStyle withForcedDecimalPoint(boolean forceDecimalPoint) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this style with another.
     * 
     * @param other  the other style, null returns false
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

    //-----------------------------------------------------------------------
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
