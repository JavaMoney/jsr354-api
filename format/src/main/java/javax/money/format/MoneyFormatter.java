/*
 *  Copyright 2009-2012 Werner Keil, Stephen Colebourne
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
import java.io.Serializable;
import java.util.Locale;

import javax.money.Monetary;


/**
 * Formats instances of money to and from a String.
 * <p>
 * Instances of {@code MoneyFormatter} can be created by
 * {@code MoneyFormatterBuilder}.
 * <p>
 * This class is immutable and thread-safe.
 */
public final class MoneyFormatter<T> implements Serializable {

    /**
     * Serialization version.
     */
    private static final long serialVersionUID = 1L;

    //-----------------------------------------------------------------------
    /**
     * Constructor, creating a new formatter.
     * 
     * @param locale  the locale to use, not null
     * @param printers  the printers, not null
     * @param parsers  the parsers, not null
     */
    MoneyFormatter(
            Locale locale,
            MoneyPrinter<T>[] printers,
            MoneyParser[] parsers) {
    	// TODO Not Implemented yet
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the locale to use.
     * 
     * @return the locale, never null
     */
    public Locale getLocale() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy of this instance with the specified locale.
     * <p>
     * Changing the locale may change the style of output depending on how the
     * formatter has been configured.
     * 
     * @param locale  the locale, not null
     * @return the new instance, never null
     */
    public MoneyFormatter<T> withLocale(Locale locale) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks whether this formatter can print.
     * <p>
     * If the formatter cannot print, an UnsupportedOperationException will
     * be thrown from the print methods.
     * 
     * @return true if the formatter can print
     */
    public boolean isPrinter() {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Checks whether this formatter can parse.
     * <p>
     * If the formatter cannot parse, an UnsupportedOperationException will
     * be thrown from the print methods.
     * 
     * @return true if the formatter can parse
     */
    public boolean isParser() {
    	// TODO Not Implemented yet
    	return false;
    }

    //-----------------------------------------------------------------------
    /**
     * Prints a monetary value to a {@code String}.
     * 
     * @param moneyProvider  the money to print, not null
     * @return the string printed using the settings of this formatter
     * @throws UnsupportedOperationException if the formatter is unable to print
     * @throws MoneyFormatException if there is a problem while printing
     */
    public String print(Monetary<T> money) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Prints a monetary value to an {@code Appendable} converting
     * any {@code IOException} to a {@code MoneyFormatException}.
     * <p>
     * Example implementations of {@code Appendable} are {@code StringBuilder},
     * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
     * and {@code StringBuffer} never throw an {@code IOException}.
     * 
     * @param appendable  the appendable to add to, not null
     * @param moneyProvider  the money to print, not null
     * @throws UnsupportedOperationException if the formatter is unable to print
     * @throws MoneyFormatException if there is a problem while printing
     */
    public void print(Appendable appendable, Monetary<T> money) {
    	// TODO Not Implemented yet
    }

    /**
     * Prints a monetary value to an {@code Appendable} potentially
     * throwing an {@code IOException}.
     * <p>
     * Example implementations of {@code Appendable} are {@code StringBuilder},
     * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
     * and {@code StringBuffer} never throw an {@code IOException}.
     * 
     * @param appendable  the appendable to add to, not null
     * @param moneyProvider  the money to print, not null
     * @throws UnsupportedOperationException if the formatter is unable to print
     * @throws MoneyFormatException if there is a problem while printing
     * @throws IOException if an IO error occurs
     */
    public void printIO(Appendable appendable, Monetary<T> money) throws IOException {
    	// TODO Not Implemented yet
    }

    /**
     * Fully parses the text into a {@code Money} requiring that the parsed
     * amount has the correct number of decimal places.
     * <p>
     * The parse must complete normally and parse the entire text (currency and amount).
     * If the parse completes without reading the entire length of the text, an exception is thrown.
     * If any other problem occurs during parsing, an exception is thrown.
     * 
     * @param text  the text to parse, not null
     * @return the parsed monetary value, never null
     * @throws UnsupportedOperationException if the formatter is unable to parse
     * @throws MoneyFormatException if there is a problem while parsing
     * @throws ArithmeticException if the scale of the parsed money exceeds the scale of the currency
     */
    public Monetary<T> parseMoney(CharSequence text) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Parses the text extracting monetary information.
     * <p>
     * This method parses the input providing low-level access to the parsing state.
     * The resulting context contains the parsed text, indicator of error, position
     * following the parse and the parsed currency and amount.
     * Together, these provide enough information for higher level APIs to use.
     *
     * @param text  the text to parse, not null
     * @param startIndex  the start index to parse from
     * @return the parsed monetary value, null only if the parse results in an error
     * @throws IndexOutOfBoundsException if the start index is invalid
     * @throws UnsupportedOperationException if this formatter cannot parse
     */
    public MoneyParseContext parse(CharSequence text, int startIndex) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets a string summary of the formatter.
     * 
     * @return a string summarising the formatter, never null
     */
    @Override
    public String toString() {
    	// TODO Not Implemented yet
    	return null;
    }

}
