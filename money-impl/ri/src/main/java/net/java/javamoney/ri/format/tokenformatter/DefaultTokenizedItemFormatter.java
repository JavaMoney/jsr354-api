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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.money.format.FormatToken;
import javax.money.format.ItemFormatException;
import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;
import javax.money.format.TokenizedItemFormatter;
import javax.money.format.TokenizedItemFormatterBuilder;

import net.java.javamoney.ri.format.common.AbstractTargeted;

/**
 * This class implements a {@link StyleableFormatter} based on an ordered and
 * {@link Decoratable} set for {@link FormatToken} instances.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the target type.
 */
public class DefaultTokenizedItemFormatter<T> extends AbstractTargeted<T>
		implements TokenizedItemFormatter<T> {

	private FormatToken<T>[] tokens;
	private LocalizationStyle style;

	public DefaultTokenizedItemFormatter(Class<T> type,
			LocalizationStyle style, FormatToken<T>... tokens) {
		super(type);
		if (style == null) {
			throw new IllegalArgumentException("Style must not be null.");
		}
		this.style = style;
		// TODO check compatibility of tokens...
		this.tokens = tokens.clone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.java.javamoney.ri.format.tokenformatter.TokenizedItemFormatter#print(
	 * java.lang.Appendable, T)
	 */
	@Override
	public void print(Appendable appendable, T item) throws IOException {
		for (int i = 0; i < tokens.length; i++) {
			tokens[i].print(appendable, item, style);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.java.javamoney.ri.format.tokenformatter.TokenizedItemFormatter#format
	 * (T)
	 */
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

	/**
	 * Access all the token used for building up this format.
	 * 
	 * @return the token used by this formatter, never {@code null}.
	 */
	public Enumeration<FormatToken<T>> getTokens() {
		return Collections.enumeration(Arrays.asList(this.tokens));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.java.javamoney.ri.format.tokenformatter.TokenizedItemFormatter#getStyle
	 * ()
	 */
	@Override
	public LocalizationStyle getStyle() {
		return this.style;
	}

	/**
	 * This class implements a {@link FormatterBuilder} based on an ordered and
	 * {@link Decoratable} list of {@link FormatToken} instances.
	 * 
	 * @author Anatole Tresch
	 * 
	 * @param <T>
	 *            The target type.
	 */
	public static class Builder<T> extends AbstractTargeted<T> implements
			TokenizedItemFormatterBuilder<T> {

		private List<FormatToken<T>> tokens = new ArrayList<FormatToken<T>>();
		private LocalizationStyle style;

		public Builder(Class<T> type) {
			super(type);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.provider.ITokenizedFormatterBuilder#
		 * getStyle()
		 */
		public LocalizationStyle getLocalizationStyle() {
			return style;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.provider.ITokenizedFormatterBuilder#
		 * setLocalizationStyle(javax.money.format.LocalizationStyle)
		 */
		public TokenizedItemFormatterBuilder<T> setLocalizationStyle(
				LocalizationStyle style) {
			this.style = style;
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.provider.ITokenizedFormatterBuilder#
		 * addToken (net.java.javamoney.ri.format.common.FormatterToken)
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.tokenformatter.TokenizedItemFormatterBuilder
		 * #addToken(javax.money.format.FormatToken)
		 */
		@Override
		public TokenizedItemFormatterBuilder<T> addToken(FormatToken<T> token) {
			this.tokens.add(token);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.provider.ITokenizedFormatterBuilder#
		 * addToken (java.lang.String)
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.tokenformatter.TokenizedItemFormatterBuilder
		 * #addToken(java.lang.String)
		 */
		@Override
		public TokenizedItemFormatterBuilder<T> addToken(String token) {
			this.tokens.add(new Literal<T>(token));
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.provider.ITokenizedFormatterBuilder#
		 * getTokens()
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.tokenformatter.TokenizedItemFormatterBuilder
		 * #getTokens()
		 */
		@Override
		public Enumeration<FormatToken<T>> getTokens() {
			return Collections.enumeration(this.tokens);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.provider.ITokenizedFormatterBuilder#
		 * getTokenCount ()
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.tokenformatter.TokenizedItemFormatterBuilder
		 * #getTokenCount()
		 */
		@Override
		public int getTokenCount() {
			return this.tokens.size();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.provider.ITokenizedFormatterBuilder#
		 * clear()
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.tokenformatter.TokenizedItemFormatterBuilder
		 * #clear()
		 */
		@Override
		public void clear() {
			this.tokens.clear();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.format.TokenizedItemFormatterBuilder#isBuildable()
		 */
		@Override
		public boolean isBuildable() {
			return !this.tokens.isEmpty();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.provider.ITokenizedFormatterBuilder#
		 * toItemFormatter ()
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.java.javamoney.ri.format.tokenformatter.TokenizedItemFormatterBuilder
		 * #build()
		 */
		@Override
		@SuppressWarnings("unchecked")
		public ItemFormatter<T> build() {
			return new DefaultTokenizedItemFormatter<>(getTargetClass(),
					getLocalizationStyle(),
					tokens.toArray(new FormatToken[tokens.size()]));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "DefaultTokenizedItemFormatter.Builder [style=" + style
					+ ", tokens=" + tokens + "]";
		}

	}

}
