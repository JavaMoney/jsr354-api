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
package net.java.javamoney.ri.format.tokens;

import java.io.IOException;

import javax.money.format.FormatToken;
import javax.money.format.LocalizationStyle;

/**
 * Base class when implementing a {@link FormatToken}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The target type.
 */
public abstract class AbstractFormatToken<T> implements FormatToken<T> {

	protected abstract String getToken(T item, LocalizationStyle style);

	@Override
	public void print(Appendable appendable, T item, LocalizationStyle style)
			throws IOException {
		String token = adjustPreformatted(getToken(adjustValue(item), style));
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
