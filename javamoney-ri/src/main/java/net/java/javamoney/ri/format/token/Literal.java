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
package net.java.javamoney.ri.format.token;

import javax.money.format.common.LocalizationStyle;

import net.java.javamoney.ri.format.common.AbstractFormatToken;
import net.java.javamoney.ri.format.common.FormatToken;

/**
 * {@link FormatToken} which adds an arbitrary literal constant value to the
 * output.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The item type.
 */
public class Literal<T> extends AbstractFormatToken<T> {

	private String token;

	public Literal(String token) {
		if (token == null) {
			throw new IllegalArgumentException("Token is required.");
		}
		this.token = token;
	}

	@Override
	protected String getToken(T item, LocalizationStyle style) {
		return this.token;
	}

}
