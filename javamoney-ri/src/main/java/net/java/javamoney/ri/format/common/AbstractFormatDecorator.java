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

import javax.money.format.common.LocalizationStyle;


/**
 * Base class when implementing a {@link FormatDecorator}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The target type.
 */
public abstract class AbstractFormatDecorator<T> implements FormatDecorator<T> {

	private FormatDecorator<T> formatDecorator;
	private ParseDecorator<T> parseDecorator;

	@Override
	public String decorateFormat(T item, String formattedString,
			LocalizationStyle style) {
		String token = decorateInternal(item, formattedString, style);
		if (token == null) {
			throw new IllegalStateException("Token may not be null.");
		}
		if (this.formatDecorator != null) {
			return this.formatDecorator.decorateFormat(item, token, style);
		}
		return token;
	}

	protected abstract String decorateInternal(T item, String formattedString,
			LocalizationStyle style);

	public void setFormatDecorator(FormatDecorator<T> decorator) {
		this.formatDecorator = decorator;
	}

	public FormatDecorator<T> getFormatDecorator() {
		return this.formatDecorator;
	}
	
	public void setParseDecorator(ParseDecorator<T> decorator) {
		this.parseDecorator = decorator;
	}

	public ParseDecorator<T> getParseDecorator() {
		return this.parseDecorator;
	}

}