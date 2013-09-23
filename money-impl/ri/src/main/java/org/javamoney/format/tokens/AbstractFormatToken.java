/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Contributors: Anatole Tresch - initial implementation
 */
package org.javamoney.format.tokens;

import java.io.IOException;
import java.util.Locale;

import org.javamoney.format.FormatToken;
import org.javamoney.format.LocalizationStyle;

/**
 * Base class when implementing a {@link FormatToken}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The target type.
 */
public abstract class AbstractFormatToken<T> implements FormatToken<T> {

	protected abstract String getToken(T item, Locale locale,
			LocalizationStyle style);

	@Override
	public String getTokenId() {
		return getClass().getSimpleName();
	}

	/**
	 * Access a configured value as defined in {@link FormatToken#getTokenId()}.
	 * 
	 * @see #getTokenId()
	 * @param style
	 *            the {@link LocalizationStyle}
	 * @return the parameter value, or the given defaultValue.
	 */
	protected <T> T getParamterValue(String key, LocalizationStyle style,
			Class<T> type,
			T defaultValue) {
		String accessKey = getTokenId() + '.' + key;
		T value = style.getAttribute(accessKey, type);
		if (value == null) {
			accessKey = getClass().getName() + '.' + key;
			value = style.getAttribute(accessKey, type);
		}
		if (value == null) {
			accessKey = getClass().getSimpleName() + '.' + key;
			value = style.getAttribute(accessKey, type);
		}
		if (value == null) {
			value = style.getAttribute(key, type);
		}
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	@Override
	public void print(Appendable appendable, T item, Locale locale,
			LocalizationStyle style)
			throws IOException {
		String token = adjustPreformatted(getToken(adjustValue(item), locale,
				style));
		if (token == null) {
			throw new IllegalStateException("Token may not be null.");
		}
		appendable.append(token);
	}

	protected T adjustValue(T item) {
		return item;
	}

	protected String adjustPreformatted(String preformattedValue) {
		return preformattedValue;
	}

}
