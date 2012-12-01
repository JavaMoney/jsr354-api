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

import java.text.ParsePosition;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;


/**
 * Context used when parsing money.
 * <p>
 * This class is mutable and intended for use by a single thread.
 * A new instance is created for each parse.
 */
public final class MoneyParseContext<T> {

	//-----------------------------------------------------------------------
    /**
     * Gets the locale.
     * 
     * @return the locale, not null
     */
    public Locale getLocale() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Sets the locale.
     * 
     * @param locale  the locale, not null
     */
    public void setLocale(Locale locale) {
    	// TODO Not Implemented yet
    }

    /**
     * Gets the text being parsed.
     * 
     * @return the text being parsed, never null
     */
    public CharSequence getText() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Sets the text.
     * 
     * @param text  the text being parsed, not null
     */
    public void setText(CharSequence text) {
    	// TODO Not Implemented yet
    }

    /**
     * Gets the length of the text being parsed.
     * 
     * @return the length of the text being parsed
     */
    public int getTextLength() {
    	// TODO Not Implemented yet
    	return 0;
    }

    /**
     * Gets a substring of the text being parsed.
     * 
     * @param start  the start index
     * @param end  the end index
     * @return the substring, not null
     */
    public String getTextSubstring(int start, int end) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the current parse position index.
     * 
     * @return the current parse position index
     */
    public int getIndex() {
    	// TODO Not Implemented yet
    	return 0;
    }

    /**
     * Sets the current parse position index.
     * 
     * @param index  the current parse position index
     */
    public void setIndex(int index) {
    	// TODO Not Implemented yet
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the error index.
     * 
     * @return the error index, negative if no error
     */
    public int getErrorIndex() {
    	// TODO Not Implemented yet
    	return 0;
    }

    /**
     * Sets the error index.
     * 
     * @param index  the error index
     */
    public void setErrorIndex(int index) {
    	// TODO Not Implemented yet
    }

    /**
     * Sets the error index from the current index.
     */
    public void setError() {
    	// TODO Not Implemented yet
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the parsed currency.
     * 
     * @return the parsed currency, null if not parsed yet
     */
    public CurrencyUnit getCurrency() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Sets the parsed currency.
     * 
     * @param currency  the parsed currency, may be null
     */
    public void setCurrency(CurrencyUnit currency) {
    	// TODO Not Implemented yet
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the parsed amount.
     * 
     * @return the parsed amount, null if not parsed yet
     */
    public T getAmount() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Sets the parsed currency.
     * 
     * @param amount  the parsed amount, may be null
     */
    public void setAmount(T amount) {
    	// TODO Not Implemented yet
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the parse has found an error.
     * 
     * @return whether a parse error has occurred
     */
    public boolean isError() {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Checks if the text has been fully parsed such that there is no more text to parse.
     * 
     * @return true if fully parsed
     */
    public boolean isFullyParsed() {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Checks if the context contains a currency and amount suitable for creating
     * a monetary value.
     * 
     * @return true if able to create a monetary value
     */
    public boolean isComplete() {
    	// TODO Not Implemented yet
    	return false;
    }

    //-----------------------------------------------------------------------
    /**
     * Converts the indexes to a parse position.
     * 
     * @return the parse position, never null
     */
    public ParsePosition toParsePosition() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Converts the context to a {@code BigMoney}.
     * 
     * @return the monetary value, never null
     * @throws MoneyFormatException if either the currency or amount is missing
     */
    public Monetary<T> toMoney() {
    	// TODO Not Implemented yet
    	return null;
    }

}
