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

import java.util.Locale;
import java.util.ResourceBundle;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.format.ItemFormatter;
import javax.money.format.ItemParseException;
import javax.money.format.LocalizationStyle;
import javax.money.provider.Monetary;

import net.java.javamoney.ri.format.common.ParseContext;

/**
 * {@link FormatterToken} that adds a localizable {@link String}, read by key
 * from a {@link ResourceBundle}..
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The concrete type.
 */
public class CurrencyToken<T extends MonetaryAmount> extends AbstractFormatterToken<T> {

	public static enum DisplayType {
		NAMESPACE, FULLCODE, CODE, NAME, NUMERIC_CODE, SYMBOL
	}

	private DisplayType displayType = DisplayType.CODE;
	private LocalizationStyle style;

	public CurrencyToken() {
	}

	public CurrencyToken(LocalizationStyle style) {
		setLocalizationStyle(style);
	}

	public CurrencyToken(Locale locale) {
		setLocalizationStyle(LocalizationStyle.of(locale));
	}

	public CurrencyToken<?> setLocalizationStyle(LocalizationStyle style) {
		this.style = style;
		return this;
	}

	public LocalizationStyle getLocalizationStyle() {
		return this.style;
	}

	public CurrencyToken<T> setDisplayType(DisplayType displayType) {
		if (displayType == null) {
			throw new IllegalArgumentException("Display type null.");
		}
		this.displayType = displayType;
		return this;
	}

	protected String getToken(T item,
			javax.money.format.LocalizationStyle targetStyle) {
		CurrencyUnit unit = item.getCurrency();
		LocalizationStyle styleUsed = this.style;
		if (styleUsed == null) {
			styleUsed = targetStyle;
		}
		switch (displayType) {
		case CODE:
			return unit.getCurrencyCode();
		case NAMESPACE:
			return unit.getNamespace();
		case NUMERIC_CODE:
			return String.valueOf(unit.getNumericCode());
		case NAME:
			ItemFormatter<CurrencyUnit> cf1 = Monetary.getItemFormatterFactory().getItemFormatter(
					CurrencyUnit.class, new LocalizationStyle.Builder(styleUsed).setAttribute("renderField", "displayName").build());
			return cf1.format(unit);
		case SYMBOL:
			ItemFormatter<CurrencyUnit> cf2 = Monetary.getItemFormatterFactory().getItemFormatter(
					CurrencyUnit.class, new LocalizationStyle.Builder(styleUsed).setAttribute("renderField", "symbol").build());
			return cf2.format(unit);
		case FULLCODE:
		default:
			return unit.getNamespace() + ':' + unit.getCurrencyCode();
		}
	}
}
