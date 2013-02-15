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

import javax.money.format.common.LocalizationStyle;
import javax.money.format.common.StyleableFormatter;
import javax.money.format.common.StyledFormatter;

/**
 * This class is used to implement a {@link StyledFormatter} based on a
 * {@link StyleableFormatter} instance.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The target type.
 */
public class StyledFormatterAdapter<T> extends AbstractTargeted<T> implements
		StyledFormatter<T> {

	private StyleableFormatter<T> baseFormatter;
	private LocalizationStyle style;

	public StyledFormatterAdapter(Class<T> type,
			StyleableFormatter<T> baseFormatter, LocalizationStyle style) {
		super(type);
		if (baseFormatter == null) {
			throw new IllegalArgumentException(
					"BaseFormatter must not be null.");
		}
		if (style == null) {
			throw new IllegalArgumentException(
					"LocalizationStyle must not be null.");
		}
		this.baseFormatter = baseFormatter;
		this.style = style;
		this.style.setImmutable();
	}

	@Override
	public LocalizationStyle getStyle() {
		return this.style;
	}

	@Override
	public String format(T item) {
		StringBuilder builder = new StringBuilder();
		try {
			print(builder, item);
			return builder.toString();
		} catch (IOException e) {
			throw new IllegalStateException("Error during formatting.", e);
		}

	}

	@Override
	public void print(Appendable appendable, T item) throws IOException {
		this.baseFormatter.print(appendable, item, this.style);
	}

}
