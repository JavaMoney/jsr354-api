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

import java.io.IOException;
import java.util.Locale;

import javax.money.format.common.FormatException;
import javax.money.format.common.LocalizationStyle;
import javax.money.format.common.StyleableFormatter;

/**
 * This class implements a {@link StyleableFormatter} based on an ordered and
 * {@link Decoratable} set for {@link FormatterToken} instnaces.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the target type.
 */
public class TokenizedItemFormatter<T> extends AbstractTargeted<T> implements
		StyleableFormatter<T> {

	private FormatterToken<T>[] tokens;

	public TokenizedItemFormatter(Class<T> type, FormatterToken<T>[] tokens) {
		super(type);
		// TODO check compatibility of tokens...
		this.tokens = tokens.clone();
	}

	@Override
	public void print(Appendable appendable, T item, LocalizationStyle style)
			throws IOException {
		for (int i = 0; i < tokens.length; i++) {
			tokens[i].print(appendable, item, style);
		}
	}

	@Override
	public String format(T item, LocalizationStyle style) throws FormatException {
		StringBuilder builder = new StringBuilder();
		try {
			print(builder, item, style);
		} catch (IOException e) {
			throw new FormatException("Error foratting of " + item, e);
		}
		return builder.toString();
	}

}
