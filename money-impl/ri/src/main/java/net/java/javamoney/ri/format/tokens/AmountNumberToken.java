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
package net.java.javamoney.ri.format.tokens;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.util.Locale;

import javax.money.MonetaryAmount;
import javax.money.format.FormatToken;
import javax.money.format.ItemParseException;
import javax.money.format.LocalizationStyle;
import javax.money.format.ParseContext;


/**
 * {@link FormatToken} which allows to format a {@link Number} type.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The item type.
 */
public class AmountNumberToken<T extends MonetaryAmount> extends
		AbstractFormatToken<T> {

	private static final char[] EMPTY_CHAR_ARRAY = new char[0];
	private static final int[] EMPTY_INT_ARRAY = new int[0];
	private DecimalFormat format;
	private StringGrouper numberGroup;

	// private StringGrouper fractionGroup;

	public AmountNumberToken() {
	}

	public AmountNumberToken(DecimalFormat format) {
		if (format == null) {
			throw new IllegalArgumentException("Format is required.");
		}
		this.format = (DecimalFormat) format.clone();
	}

	public AmountNumberToken<T> setNumberGroupSizes(int... groupSizes) {
		if (this.numberGroup == null) {
			this.numberGroup = new StringGrouper();
		}
		this.numberGroup.setGroupSizes(groupSizes);
		return this;
	}

	public AmountNumberToken<T> setNumberGroupChars(char... groupChars) {
		if (this.numberGroup == null) {
			this.numberGroup = new StringGrouper();
		}
		this.numberGroup.setGroupChars(groupChars);
		return this;
	}

	public char[] getNumberGroupChars() {
		if (this.numberGroup == null) {
			return EMPTY_CHAR_ARRAY;
		}
		return this.numberGroup.getGroupChars();
	}

	public int[] getNumberGroupSizes() {
		if (this.numberGroup == null) {
			return EMPTY_INT_ARRAY;
		}
		return this.numberGroup.getGroupSizes();
	}

	public AmountNumberToken<T> setPattern(String pattern) {
		if (this.format == null) {
			this.format = new DecimalFormat(pattern);
		} else {
			this.format.applyPattern(pattern);
		}
		return this;
	}

	public AmountNumberToken<T> setDecimalFormat(DecimalFormat format) {
		this.format = format;
		return this;
	}

	public DecimalFormat getDecimalFormat() {
		return this.format;
	}

	public DecimalFormatSymbols getSymbols() {
		if (this.format != null) {
			return this.format.getDecimalFormatSymbols();
		}
		return null;
	}

	public AmountNumberToken<T> setSymbols(DecimalFormatSymbols symbols) {
		if (this.format == null) {
			this.format = (DecimalFormat) DecimalFormat.getInstance();
			this.format.setDecimalFormatSymbols(symbols);
		} else {
			this.format.setDecimalFormatSymbols(symbols);
		}
		return this;
	}

	protected DecimalFormat getNumberFormat(Locale locale, LocalizationStyle style) {
		DecimalFormat formatUsed = this.format;
		if (formatUsed == null) {
			formatUsed = (DecimalFormat) DecimalFormat.getInstance(locale);
		}
		if (this.numberGroup != null) { // this.fractionGroup!=null ||
			formatUsed.setGroupingUsed(false);
		}
		return formatUsed;
	}

	@Override
	protected String getToken(T item, Locale locale, LocalizationStyle style) {
		DecimalFormat format = getNumberFormat(locale, style);
		if (this.numberGroup == null) { // || this.fractionGroup==null
			return format.format(item);
		}
		String preformattedValue = format.format(item);
		String[] numberParts = splitNumberParts(item, format, style,
				preformattedValue);
		if (numberParts.length != 2) {
			return preformattedValue;
		}
		return numberGroup.group(numberParts[0])
				+ format.getDecimalFormatSymbols().getDecimalSeparator()
				+ numberParts[1];
	}

	private String[] splitNumberParts(T item, DecimalFormat format,
			LocalizationStyle style, String preformattedValue) {
		return preformattedValue.split(String.valueOf(format
				.getDecimalFormatSymbols().getDecimalSeparator()));
	}

	@Override
	public void parse(ParseContext context, Locale locale, LocalizationStyle style) throws ItemParseException {
		DecimalFormat format = getNumberFormat(locale, style);
		ParsePosition pos = new ParsePosition(0);
		Number number = format.parse(context.getInput().toString(), pos);
		context.addParseResult(Number.class, number);
	}
}
