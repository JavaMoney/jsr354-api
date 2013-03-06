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
package net.java.javamoney.ri.format;

import java.io.IOException;

import javax.money.format.ItemFormatException;
import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;

import net.java.javamoney.ri.format.common.AbstractTargeted;

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
		ItemFormatter<T> {

	private FormatterToken<T>[] tokens;
	private LocalizationStyle style;

	public TokenizedItemFormatter(Class<T> type, LocalizationStyle style,
			FormatterToken<T>[] tokens) {
		super(type);
		if (style == null) {
			throw new IllegalArgumentException("Style must not be null.");
		}
		this.style = style;
		// TODO check compatibility of tokens...
		this.tokens = tokens.clone();
	}

	@Override
	public void print(Appendable appendable, T item) throws IOException {
		for (int i = 0; i < tokens.length; i++) {
			tokens[i].print(appendable, item, style);
		}
	}

	@Override
	public String format(T item) throws ItemFormatException {
		StringBuilder builder = new StringBuilder();
		try {
			print(builder, item);
		} catch (IOException e) {
			throw new ItemFormatException("Error foratting of " + item, e);
		}
		return builder.toString();
	}

	@Override
	public LocalizationStyle getStyle() {
		return this.style;
	}

}
