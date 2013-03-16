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
package net.java.javamoney.ri.format.provider.format;

import java.io.IOException;
import java.util.Arrays;
import java.util.Currency;

import javax.money.CurrencyUnit;
import javax.money.LocalizableCurrencyUnit;
import javax.money.format.ItemFormatException;
import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;

public class IsoCurrencyFormatter implements ItemFormatter<CurrencyUnit> {

	public enum RenderedField {
		ID, CODE, SYMBOL, DISPLAYNAME, NUMERICCODE, OMIT
	}

	private RenderedField renderedField = RenderedField.CODE;
	private LocalizationStyle style;

	@Override
	public Class<CurrencyUnit> getTargetClass() {
		return CurrencyUnit.class;
	}

	public IsoCurrencyFormatter(LocalizationStyle style)
			throws ItemFormatException {
		String field = style.getId();
		try {
			renderedField = RenderedField.valueOf(field.toUpperCase());
		} catch (Exception e) {
			throw new ItemFormatException("style's id must be one of "
					+ Arrays.toString(RenderedField.values()));
		}
		this.style = style;
	}

	@Override
	public LocalizationStyle getStyle() {
		return style;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public String format(CurrencyUnit item) {
		// try to check for non localizaed formats
		switch (renderedField) {
		case ID:
			return item.getNamespace() + ':' + item.getCurrencyCode();
		case CODE:
			return item.getCurrencyCode();
		case NUMERICCODE:
			return String.valueOf(item.getNumericCode());
		}
		// check for iso currencies
		if (CurrencyUnit.ISO_NAMESPACE.equals(item.getNamespace())) {
			Currency isoCurrency = Currency.getInstance(item.getCurrencyCode());
			switch (renderedField) {
			case SYMBOL:
				return isoCurrency.getSymbol(this.style.getTranslationLocale());
			case DISPLAYNAME:
				return isoCurrency.getDisplayName(this.style
						.getTranslationLocale());
			}
		} else {
			if (item instanceof LocalizableCurrencyUnit) {
				return formatLocalized((LocalizableCurrencyUnit) item);
			}
		}
		// Overall fallback, return code...
		return item.getCurrencyCode();
	}

	private String formatLocalized(LocalizableCurrencyUnit item) {
		switch (renderedField) {
		case DISPLAYNAME:
			return item.getDisplayName(this.style.getTranslationLocale());
		case SYMBOL:
			return item.getSymbol(this.style.getTranslationLocale());
		default:
			return item.getCurrencyCode();
		}
	}

	@Override
	public void print(Appendable appendable, CurrencyUnit item)
			throws IOException {
		appendable.append(format(item));
	}

}
