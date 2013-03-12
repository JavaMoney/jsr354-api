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

import javax.money.format.ItemParseException;
import javax.money.format.LocalizationStyle;

import net.java.javamoney.ri.format.common.ParseContext;

/**
 * {@link FormatDecorator} that allows to replace the representation of a zero
 * {@link Number} with an arbitrary literal value.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The concrete {@link Number} type.
 */
public class ZeroValueDecorator<T extends Number> extends
		AbstractFormatterToken<T> {

	private String zeroValue;
	private FormatterToken<T> decorated;

	public ZeroValueDecorator(FormatterToken<T> decorated) {
		if (decorated == null) {
			throw new IllegalArgumentException("decorated is required.");
		}
		this.decorated = decorated;
	}

	public ZeroValueDecorator<T> setZeroValue(String value) {
		this.zeroValue = value;
		return this;
	}

	public String getZeroValue() {
		return zeroValue;
	}

	@Override
	protected String getToken(T item, LocalizationStyle style) {
		if (item.doubleValue() == 0.0d || item.doubleValue() == -0.0d) {
			return zeroValue;
		}
		return this.decorated.format(item, style);
	}


}
