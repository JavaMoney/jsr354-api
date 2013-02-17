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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.money.format.common.LocalizationStyle;
import javax.money.format.common.StyleableFormatter;
import javax.money.format.common.StyledFormatter;
import javax.money.format.common.FormatterBuilder;

import net.java.javamoney.ri.format.token.Literal;

/**
 * This class implements a {@link FormatterBuilder} based on an ordered
 * and {@link Decoratable} list of {@link FormatterToken} instances.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The target type.
 */
public class TokenizedFormatterBuilder<T> extends AbstractTargeted<T> implements
		FormatterBuilder<T> {

	private List<FormatterToken<T>> tokens = new ArrayList<FormatterToken<T>>();

	public TokenizedFormatterBuilder(Class<T> type) {
		super(type);
	}

	public TokenizedFormatterBuilder<T> addToken(FormatterToken<T> token) {
		this.tokens.add(token);
		return this;
	}

	public TokenizedFormatterBuilder<T> addToken(String token) {
		this.tokens.add(new Literal<T>(token));
		return this;
	}

	public Enumeration<FormatterToken<T>> getTokens() {
		return Collections.enumeration(this.tokens);
	}

	public int getTokenCount() {
		return this.tokens.size();
	}

	public void clear() {
		this.tokens.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public StyleableFormatter<T> toStyleableFormatter() {
		return new TokenizedItemFormatter<>(getTargetClass(),
				tokens.toArray(new FormatterToken[tokens.size()]));
	}

	@Override
	public StyledFormatter<T> toFormatter(LocalizationStyle style) {
		return new StyledFormatterAdapter<>(getTargetClass(),
				toStyleableFormatter(), style);
	}

}
