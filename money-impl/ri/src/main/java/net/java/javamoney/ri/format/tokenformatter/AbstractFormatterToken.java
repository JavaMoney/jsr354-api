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
package net.java.javamoney.ri.format.tokenformatter;

import java.io.IOException;

import javax.money.format.ItemParseException;
import javax.money.format.LocalizationStyle;

import net.java.javamoney.ri.format.common.ParseContext;
import net.java.javamoney.ri.format.tokenparser.ParserToken;

/**
 * Base class when implementing a {@link FormatterToken}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The target type.
 */
public abstract class AbstractFormatterToken<T> implements FormatterToken<T> {

	protected abstract String getToken(T item, LocalizationStyle style);

	@Override
	public String format(T item, LocalizationStyle style) {
		String token = getToken(item, style);
		if (token == null) {
			throw new IllegalStateException("Token may not be null.");
		}
		return token;
	}

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
