/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
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
 *
 * Contributors:
 *    Anatole Tresch - initial implementation
 */
package net.java.javamoney.ri.format.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.money.format.ItemParseException;
import javax.money.format.LocalizationStyle;

import net.java.javamoney.ri.format.ParserToken;

/**
 * This class contains the parsing context for parsing a value. Each ParserToken
 * may adapt the given instance as required.
 * 
 * @see ParserToken
 * 
 * @author Anatole Tresch
 */
public final class ParseContext {

	private int position;
	private CharSequence text;
	private int errorPosition;
	private ItemParseException error;
	private Map<Object, Object> attributes = new HashMap<Object, Object>();
	private LocalizationStyle style;

	public ParseContext(CharSequence text, LocalizationStyle style) {
		if (text == null) {
			throw new IllegalArgumentException("Test must not be null.");
		}
		this.text = text;
		if (style == null) {
			throw new IllegalArgumentException("Style must not be null.");
		}
		this.style = style;
	}

	public int getPosition() {
		return this.position;
	}

	public void setPosition(int position) {
		if (position < 0 || position >= this.text.length()) {
			throw new IllegalArgumentException("Invalid position: " + position);
		}
		this.position = position;
	}

	public CharSequence getText() {
		return this.text;
	}

	public CharSequence getCurrentText() {
		return this.text.subSequence(position, this.text.length());
	}

	public CharSequence lookup(int numbers) {
		if ((numbers + position) >= text.length()) {
			return text.subSequence(position, text.length());
		}
		return text.subSequence(position, position + numbers);
	}

	public boolean consume(CharSequence expected) {
		CharSequence next = lookup(expected.length());
		if (next.length() != expected.length()) {
			return false;
		}
		if (next.toString().equals(expected.toString())) {
			position += expected.length();
			return true;
		}
		return false;
	}

	public int getErrorPosition() {
		return this.errorPosition;
	}

	public ItemParseException getError() {
		return this.error;
	}

	public void setError(int position, ItemParseException error) {
		this.position = position;
		if (error == null) {
			throw new IllegalArgumentException(
					"You must define a correct error.");
		}
		this.error = error;
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key, Class<T> type) {
		return (T) this.attributes.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Class<T> type) {
		return (T) this.attributes.get(type);
	}

	public void setAttribute(Object key, Object value) {
		this.attributes.put(key, value);
	}

	public Object removeAttribute(Object key) {
		return this.attributes.remove(key);
	}

	public Map<Object, Object> getAttributes() {
		return Collections.unmodifiableMap(this.attributes);
	}

	public void clearAttributes() {
		this.attributes.clear();
	}

	public LocalizationStyle getLocalizationStyle() {
		return this.style;
	}

	public String getNextToken() {
		return getNextToken(" \n\r\t");
	}

	public String getNextToken(String separators) {
		StringTokenizer tokenizer = new StringTokenizer(getCurrentText().toString(),
				separators, false);
		return tokenizer.nextToken();
	}
}
