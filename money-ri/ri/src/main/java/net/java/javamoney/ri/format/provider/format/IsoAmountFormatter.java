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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.format.ItemFormatException;
import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;
import javax.money.provider.Monetary;

public class IsoAmountFormatter implements ItemFormatter<MonetaryAmount> {

	private LocalizationStyle style;
	private LocalizationStyle currencyStyle;
	private CurrencyPlacement currencyPlacement = CurrencyPlacement.LEADING;

	public enum CurrencyPlacement {
		LEADING, TRAILING, NONE
	}

	@Override
	public Class<MonetaryAmount> getTargetClass() {
		return MonetaryAmount.class;
	}

	public IsoAmountFormatter(LocalizationStyle style) {
		String currencyRendering = (String) style.getAttribute(
				"currencyRendering", String.class);
		if (currencyRendering == null) {
			currencyRendering = "CODE";
		}
		this.style = style;
	}

	public IsoAmountFormatter(LocalizationStyle amountStyle,
			LocalizationStyle currencyStyle) {
		this.style = amountStyle;
		this.currencyStyle = currencyStyle;
	}

	@Override
	public LocalizationStyle getStyle() {
		return style;
	}

	public CurrencyPlacement getCurrencyPlacement() {
		return currencyPlacement;
	}

	public void setCurrencyPlacement(CurrencyPlacement currencyPlacement) {
		if (currencyPlacement == null) {
			throw new IllegalArgumentException(
					"CurrencyPlacement must not be null.");
		}
		this.currencyPlacement = currencyPlacement;
	}

	@Override
	public String format(MonetaryAmount item)throws ItemFormatException {
		CurrencyUnit currencyUnit = item.getCurrency();
		StringBuilder result = new StringBuilder();

		if (CurrencyUnit.ISO_NAMESPACE.equals(currencyUnit.getNamespace())) {
			String currencyString = "";
			ItemFormatter<CurrencyUnit> cf = Monetary.getItemFormatterFactory()
					.getItemFormatter(CurrencyUnit.class,
							currencyStyle);
			// TODO fix grouping for Cores, Lakhs and similar, possibly define an extension SPI that may be loaded for
			// special currencies only, thus requiring to override the more advanced cases only ;-)
			DecimalFormat df = (DecimalFormat) DecimalFormat
					.getCurrencyInstance(style.getNumberLocale());
			DecimalFormatSymbols syms = df.getDecimalFormatSymbols();
			syms.setCurrencySymbol("");
			df.setDecimalFormatSymbols(syms);
			Number number = item.asType(Number.class);
			String numberString = df.format(number).trim();
			String sep = (String) style.getAttribute("separator", String.class);
			if (sep == null) {
				sep = " ";
			}
			switch (currencyPlacement) {
			case NONE:
				return numberString;
			case LEADING:
				currencyString = cf.format(item.getCurrency());
				return currencyString + sep + numberString;
			case TRAILING:
				currencyString = cf.format(item.getCurrency());
				return numberString + sep + currencyString;
			}
		}
		return result.toString();
	}

	@Override
	public void print(Appendable appendable, MonetaryAmount item)
			throws IOException {
		appendable.append(format(item));
	}

}
