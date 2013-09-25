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
package org.javamoney.format.tokens;

import java.util.Locale;
import java.util.ResourceBundle;


import org.javamoney.format.FormatToken;
import org.javamoney.format.ItemParseException;
import org.javamoney.format.LocalizationStyle;
import org.javamoney.format.ParseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link FormatToken} that adds a localizable {@link String}, read by key from
 * a {@link ResourceBundle}..
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The concrete type.
 */
public class TranslatedLiteralToken<T> extends AbstractFormatToken<T> {

	private String bundle;
	private String key;
	private Logger LOG = LoggerFactory.getLogger(TranslatedLiteralToken.class);

	public TranslatedLiteralToken(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Token is required.");
		}
		this.key = key;
	}

	public TranslatedLiteralToken(String key, String bundle) {
		setKey(key);
		setBundle(bundle);
	}

	public TranslatedLiteralToken<T> setKey(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Token is required.");
		}
		this.key = key;
		return this;
	}

	public TranslatedLiteralToken<T> setBundle(String bundle) {
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

	protected String getToken(T item, Locale locale, org.javamoney.format.LocalizationStyle style) {
		return getTokenInternal(locale, style);
	};

	private String getTokenInternal(Locale locale, LocalizationStyle style) {
		if (bundle == null) {
			return String.valueOf(key);
		}
		try {
			ResourceBundle rb = ResourceBundle.getBundle(bundle,
					locale);
			return rb.getString(key);
		} catch (Exception e) {
			return String.valueOf(key);
		}
	}

	@Override
	public void parse(ParseContext context, Locale locale, LocalizationStyle style)
			throws ItemParseException {
		String token = getTokenInternal(locale, style);
		if (!context.consume(token)) {
			LOG.debug("Optional: " + token + " not found at "
						+ context.getInput().toString());
		}
	}
}
