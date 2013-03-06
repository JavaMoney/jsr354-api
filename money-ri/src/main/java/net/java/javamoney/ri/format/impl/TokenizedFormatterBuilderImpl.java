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
package net.java.javamoney.ri.format.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;
import javax.money.provider.spi.MonetaryExtension;

import net.java.javamoney.ri.format.FormatterToken;
import net.java.javamoney.ri.format.TokenizedItemFormatter;
import net.java.javamoney.ri.format.common.AbstractTargeted;
import net.java.javamoney.ri.format.token.Literal;

/**
 * This class implements a {@link FormatterBuilder} based on an ordered and
 * {@link Decoratable} list of {@link FormatterToken} instances.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The target type.
 */
public class TokenizedFormatterBuilderImpl<T> extends AbstractTargeted<T>
		implements TokenizedFormatterBuilder<T>, MonetaryExtension {

	private List<FormatterToken<T>> tokens = new ArrayList<FormatterToken<T>>();
	private LocalizationStyle style;

	public TokenizedFormatterBuilderImpl(Class<T> type) {
		super(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.java.javamoney.ri.format.impl.ITokenizedFormatterBuilder#getStyle()
	 */
	@Override
	public LocalizationStyle getStyle() {
		return style;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.javamoney.ri.format.impl.ITokenizedFormatterBuilder#
	 * setLocalizationStyle(javax.money.format.LocalizationStyle)
	 */
	@Override
	public TokenizedFormatterBuilder<T> setLocalizationStyle(
			LocalizationStyle style) {
		this.style = style;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.java.javamoney.ri.format.impl.ITokenizedFormatterBuilder#addToken
	 * (net.java.javamoney.ri.format.common.FormatterToken)
	 */
	@Override
	public TokenizedFormatterBuilder<T> addToken(FormatterToken<T> token) {
		this.tokens.add(token);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.java.javamoney.ri.format.impl.ITokenizedFormatterBuilder#addToken
	 * (java.lang.String)
	 */
	@Override
	public TokenizedFormatterBuilder<T> addToken(String token) {
		this.tokens.add(new Literal<T>(token));
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.java.javamoney.ri.format.impl.ITokenizedFormatterBuilder#getTokens()
	 */
	@Override
	public Enumeration<FormatterToken<T>> getTokens() {
		return Collections.enumeration(this.tokens);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.java.javamoney.ri.format.impl.ITokenizedFormatterBuilder#getTokenCount
	 * ()
	 */
	@Override
	public int getTokenCount() {
		return this.tokens.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.java.javamoney.ri.format.impl.ITokenizedFormatterBuilder#clear()
	 */
	@Override
	public void clear() {
		this.tokens.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.java.javamoney.ri.format.impl.ITokenizedFormatterBuilder#toItemFormatter
	 * ()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ItemFormatter<T> toItemFormatter() {
		return new TokenizedItemFormatter<>(getTargetClass(), getStyle(),
				tokens.toArray(new FormatterToken[tokens.size()]));
	}

	@Override
	public Class<?> getExposedType() {
		return TokenizedFormatterBuilder.class;
	}

}
