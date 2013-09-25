/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package org.javamoney.format;

import java.text.ParsePosition;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Context passed along to each {@link FormatToken} in-line, when parsing an
 * input stream using a {@link ItemFormatBuilder}. It allows to inspect the
 * next tokens, the whole input String, or just the current input substring,
 * based on the current parsing position etc.
 * <p>
 * This class is mutable and intended for use by a single thread. A new instance
 * is created for each parse.
 */
public final class ParseContext<T> {
	/** The current position of parsing. */
	private int index;
	/** The error index position. */
	private int errorIndex = -1;
	/** The full input. */
	private CharSequence originalInput;
	/**
	 * Item factory to determine if the result was successfuylly parsed and to
	 * evaluate the result item.
	 */
	private ItemFactory<T> itemFactory;
	/**
	 * The instances parsed and added to this {@link ParseContext}. This objects
	 * can be used by an according {@code MonetaryFunction<ParseContext,T>} to
	 * create an instance of T.
	 */
	private Map<Object, Object> results = new HashMap<Object, Object>();

	/**
	 * Creates a new {@link ParseContext} with the given input.
	 * 
	 * @param text
	 *            The test to be parsed.
	 */
	public ParseContext(CharSequence text, ItemFactory<T> itemFactory) {
		if (text == null) {
			throw new IllegalArgumentException("test is required");
		}
		if (itemFactory == null) {
			throw new IllegalArgumentException("itemFactory is required");
		}
		this.originalInput = text;
		this.itemFactory = itemFactory;
	}

	/**
	 * Method allows to determine if the item being parsed is available from the
	 * {@link ParseContext}.
	 * 
	 * @return true, if the item is available.
	 */
	public boolean isComplete() {
		return itemFactory.isComplete(this);
	}

	/**
	 * Get the parsed item.
	 * 
	 * @return the item parsed.
	 */
	public T getItem() {
		if (!isComplete()) {
			throw new IllegalStateException("Parsing is not yet complete.");
		}
		T item = this.itemFactory.createItemParsed(this);
		if (item == null) {
			throw new IllegalStateException("Item is not available.");
		}
		return item;
	}

	/**
	 * Consumes the given token. If the current residual text to be parsed
	 * starts with the parsing index is increased by {@code token.size()}.
	 * 
	 * @param token
	 *            The token expected.
	 * @return true, if the token could be consumed and the index was increased
	 *         by {@code token.size()}.
	 */
	public boolean consume(String token) {
		if (getInput().toString().startsWith(token)) {
			index += token.length();
			return true;
		}
		return false;
	}

	/**
	 * Tries to consume one single character.
	 * 
	 * @param c
	 *            the next character being expected.
	 * @return true, if the character matched and the index could be increased
	 *         by one.
	 */
	public boolean consume(char c) {
		if (originalInput.charAt(index) == c) {
			index++;
			return true;
		}
		return false;
	}

	/**
	 * Skips all whitespaces until a non whitespace character is occurring. If
	 * the next character is not whitespace this method does nothing.
	 * 
	 * @see Character#isWhitespace(char)
	 * 
	 * @return the new parse index after skipping any whitespaces.
	 */
	public int skipWhitespace() {
		for (int i = index; i < originalInput.length(); i++) {
			if (Character.isWhitespace(originalInput.charAt(i))) {
				index++;
			} else {
				break;
			}
		}
		return index;
	}

	/**
	 * Gets the error index.
	 * 
	 * @return the error index, negative if no error
	 */
	public int getErrorIndex() {
		return errorIndex;
	}

	/**
	 * Sets the error index.
	 * 
	 * @param index
	 *            the error index
	 */
	public void setErrorIndex(int index) {
		this.errorIndex = index;
	}

	/**
	 * Sets the error index from the current index.
	 */
	public void setError() {
		this.errorIndex = index;
	}

	/**
	 * Gets the current parse position.
	 * 
	 * @return the current parse position within the input.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the residual input text starting from the current parse position.
	 * 
	 * @return the residual input text
	 */
	public CharSequence getInput() {
		return originalInput.subSequence(index, originalInput.length() - 1);
	}

	/**
	 * Gets the full input text.
	 * 
	 * @return the full input.
	 */
	public String getOriginalInput() {
		return originalInput.toString();
	}

	/**
	 * Resets this instance; this will reset the parsing position, the error
	 * index and also all containing results.
	 */
	public void reset() {
		this.index = 0;
		this.errorIndex = -1;
		this.results.clear();
	}

	/**
	 * Add a result to the results of this context.
	 * 
	 * @param key
	 *            The result key
	 * @param value
	 *            The result value
	 */
	public void addParseResult(Object key, Object value) {
		this.results.put(key, value);
	}

	/**
	 * Access all results.
	 * 
	 * @return the unmodifiable map of the results.
	 */
	public Map<Object, Object> getParseResults() {
		return Collections.unmodifiableMap(this.results);
	}

	/**
	 * Checks if the parse has found an error.
	 * 
	 * @return whether a parse error has occurred
	 */
	public boolean isError() {
		return errorIndex >= 0;
	}

	/**
	 * Checks if the text has been fully parsed such that there is no more text
	 * to parse.
	 * 
	 * @return true if fully parsed
	 */
	public boolean isFullyParsed() {
		return index == this.originalInput.length();
	}

	/**
	 * Get a single result from the results stored.
	 * 
	 * @param key
	 *            the result key
	 * @param type
	 *            the result type
	 * @return the result value, casted to T, or null.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getResult(Object key, Class<T> type) {
		return (T) results.get(key);
	}

	/**
	 * This method skips all whitespaces and returns the full text, until
	 * another whitespace area or the end of the input is reached. The method
	 * will not update any index pointers.
	 * 
	 * @return the next token found, or null.
	 */
	public String lookupNextToken() {
		skipWhitespace();
		int start = index;
		for (int end = index; end < originalInput.length(); end++) {
			if (Character.isWhitespace(originalInput.charAt(end))) {
				if (end > start) {
					return originalInput.subSequence(start, end).toString();
				}
				return null;
			}
		}
		return null;
	}

	/**
	 * Converts the indexes to a parse position.
	 * 
	 * @return the parse position, never null
	 */
	public ParsePosition toParsePosition() {
		return new ParsePosition(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParseContext [index=" + index + ", errorIndex=" + errorIndex
				+ ", originalInput='" + originalInput + "', results=" + results
				+ "]";
	}

}
