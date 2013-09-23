/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Contributors: Anatole Tresch - initial implementation
 */
package org.javamoney.format;

import java.io.IOException;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;

import org.javamoney.format.ItemFormat;
import org.javamoney.format.ItemFormatException;
import org.javamoney.format.ItemParseException;
import org.javamoney.format.LocalizationStyle;

public class IsoCurrencyFormatter implements ItemFormat<CurrencyUnit> {

	public enum RenderedField {

		ID, CODE, SYMBOL, DISPLAYNAME, NUMERICCODE, OMIT
	}

	public enum ParsedField {

		ID, CODE
	}

	private ParsedField[] parsedFields = new ParsedField[] { ParsedField.CODE };
	private RenderedField renderedField = RenderedField.CODE;
	private LocalizationStyle style;

	@Override
	public Class<CurrencyUnit> getTargetClass() {
		return CurrencyUnit.class;
	}

	public IsoCurrencyFormatter(LocalizationStyle style) {
		String field = null;
		if (style != null) {
			field = style.getId();
		}
		if (field == null) {
			field = RenderedField.CODE.toString();
		}
		try {
			renderedField = RenderedField.valueOf(field.toUpperCase());
		} catch (Exception e) {
			throw new ItemFormatException("style's id must be one of "
					+ Arrays.toString(RenderedField.values()));
		}
		String value = null;
		if (style != null) {
			value = (String) style
					.getAttribute("parsedFields", String.class);
		}
		if (value != null) {
			try {
				String[] fields = value.split(",");
				parsedFields = new ParsedField[fields.length];
				for (int i = 0; i < fields.length; i++) {
					parsedFields[i] = ParsedField.valueOf(fields[i]
							.toUpperCase(Locale.ENGLISH));
				}
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"parsedFields must be a comma separated list of "
								+ Arrays.toString(ParsedField.values()));
			}
		}
		this.style = style;
	}

	@Override
	public LocalizationStyle getStyle() {
		return style;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public String format(CurrencyUnit currency, Locale locale) {
		// try to check for non localizaed formats
		switch (renderedField) {
		case CODE:
			return currency.getCurrencyCode();
		case NUMERICCODE:
			return String.valueOf(MoneyCurrency.from(currency).getNumericCode());
		}
		// check for iso currencies
		if (MoneyCurrency.isJavaCurrency(currency.getCurrencyCode())) {
			Currency isoCurrency = Currency.getInstance(currency.getCurrencyCode());
			switch (renderedField) {
			case SYMBOL:
				return isoCurrency.getSymbol(locale);
			case DISPLAYNAME:
				return isoCurrency.getDisplayName(locale);
			}
		}
		// Overall fallback, return code...
		return currency.getCurrencyCode();
	}

	@Override
	public void print(Appendable appendable, CurrencyUnit item, Locale locale)
			throws IOException {
		appendable.append(format(item, locale));
	}

	@Override
	public CurrencyUnit parse(CharSequence text, Locale locale)
			throws ItemParseException {
		// try to check for non localizaed formats
		String currencyCode = null;
		String textString = text.toString();
		for (ParsedField f : parsedFields) {
			switch (f) {
			case CODE:
				return MoneyCurrency.of(text.toString());
			}
		}
		throw new ItemParseException("Currency not parseable: " + text);
	}
}
