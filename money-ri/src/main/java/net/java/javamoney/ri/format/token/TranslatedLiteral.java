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
package net.java.javamoney.ri.format.token;

import java.util.ResourceBundle;

import javax.money.format.ItemParseException;
import javax.money.format.LocalizationStyle;

import net.java.javamoney.ri.format.FormatterToken;
import net.java.javamoney.ri.format.common.ParseContext;

/**
 * {@link FormatterToken} that adds a localizable {@link String}, read by key from
 * a {@link ResourceBundle}..
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The concrete type.
 */
public class TranslatedLiteral<T> extends AbstractToken<T> {

	private String bundle;
	private String key;

	public TranslatedLiteral(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Token is required.");
		}
		this.key = key;
	}

	public TranslatedLiteral(String key, String bundle) {
		setKey(key);
		setBundle(bundle);
	}

	public TranslatedLiteral<T> setKey(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Token is required.");
		}
		this.key = key;
		return this;
	}

	public TranslatedLiteral<T> setBundle(String bundle) {
		if (bundle == null) {
			throw new IllegalArgumentException("Bundle is required.");
		}
		this.bundle = bundle;
		return this;
	}

	public String getBundle() {
		return this.bundle;
	}

	public String getKey() {
		return this.key;
	}

	protected String getToken(T item,
			javax.money.format.LocalizationStyle style) {
		return getTokenInternal(style);
	};
	
	private String getTokenInternal(LocalizationStyle style) {
		if (bundle == null) {
			return String.valueOf(key);
		}
		try {
			ResourceBundle rb = ResourceBundle.getBundle(bundle,
					style.getTranslationLocale());
			return rb.getString(key);
		} catch (Exception e) {
			return String.valueOf(key);
		}
	}

	@Override
	public void parse(ParseContext context) throws ItemParseException {
		javax.money.format.LocalizationStyle style = context.getLocalizationStyle();
		String token = getTokenInternal(style);
		if(!context.consume(token)){
			if(!isOptional()){
				throw new ItemParseException("Expected: " + token, context.getCurrentText().toString(), -1);
			}
		}
	}
}
