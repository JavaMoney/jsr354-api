/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * Contributors:
 *    Anatole Tresch - initial version.
 */
package javax.money.provider.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.format.ItemFormatter;
import javax.money.format.ItemFormatterFactory;
import javax.money.format.LocalizationStyle;

/**
 * Empty pseudo implementation for testing only.
 * 
 * @author Anatole Tresch
 * 
 */
public class TestItemFormatterFactory implements ItemFormatterFactory {

	@Override
	public Enumeration<String> getSupportedStyleIds(Class<?> type) {
		return Collections.enumeration(Arrays
				.asList(new String[] { LocalizationStyle.DEFAULT_ID }));
	}

	@Override
	public boolean isSupportedStyle(Class<?> type, String styleId) {
		return LocalizationStyle.DEFAULT_ID.equals(styleId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ItemFormatter<T> getItemFormatter(Class<T> type,
			LocalizationStyle style) {
		return null;
	}

	@Override
	public <T> ItemFormatter<T> getItemFormatter(Class<T> targetType,
			Locale locale) {
		return getItemFormatter(targetType, LocalizationStyle.of(locale));
	}

}
