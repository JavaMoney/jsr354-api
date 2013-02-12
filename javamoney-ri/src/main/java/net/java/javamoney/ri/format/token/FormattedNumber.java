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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.money.format.common.LocalizationStyle;

import net.java.javamoney.ri.format.FormatDecorator;
import net.java.javamoney.ri.format.FormatToken;
import net.java.javamoney.ri.format.common.AbstractFormatToken;
import net.java.javamoney.ri.format.common.StringGrouper;

/**
 * {@link FormatToken} which allows to format a {@link Number} type.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The item type.
 */
public class FormattedNumber<T extends Number> extends AbstractFormatToken<T> {

	private StringGrouper numberGroup = new StringGrouper();
	private StringGrouper fractionGroup = new StringGrouper();
	private Character decimalSeparator;
	private boolean groupCharsSet = false;
	private boolean useStyle;
	private boolean signOmited = false;
	private boolean fractionOmited = false;

	public FormattedNumber() {
	}

	public FormattedNumber(char decimcalSep) {
		setDecimalSeparator(decimcalSep);
	}

	public FormattedNumber setUsingtyle(boolean useStyle) {
		this.useStyle = useStyle;
		return this;
	}

	public boolean isUsingStyle() {
		return this.useStyle;
	}

	public boolean isFractionOmited() {
		return this.fractionOmited;
	}

	public FormattedNumber setFractionOmited(boolean fractionOmited) {
		this.fractionOmited = fractionOmited;
		return this;
	}

	public boolean isSignOmited() {
		return this.signOmited;
	}

	public FormattedNumber setSignOmited(boolean signOmited) {
		this.signOmited = signOmited;
		return this;
	}

	public FormattedNumber setDecimalSeparator(char sep) {
		this.decimalSeparator = Character.valueOf(sep);
		return this;
	}

	public FormattedNumber decorate(FormatDecorator<T> decorator) {
		FormatDecorator<T> existing = getDecorator();
		if (decorator == null) {
			setDecorator(null);
		} else {
			if (existing == null) {
				setDecorator(decorator);
			} else {
				existing.setDecorator(decorator);
			}
		}
		return this;
	}

	public Character getDecimalSeparator() {
		return this.decimalSeparator;
	}

	public FormattedNumber setNumberGroupSizes(int... groupSizes) {
		this.numberGroup.setGroupSizes(groupSizes);
		return this;
	}

	public FormattedNumber setNumberGroupChars(char... groupChars) {
		this.numberGroup.setGroupChars(groupChars);
		this.groupCharsSet = true;
		return this;
	}

	public char[] getNumberGroupChars() {
		return this.numberGroup.getGroupChars();
	}

	public FormattedNumber setFractionGroupSizes(int... groupSizes) {
		this.fractionGroup.setGroupSizes(groupSizes);
		return this;
	}

	public int[] getFractionGroupSizes() {
		return this.fractionGroup.getGroupSizes();
	}

	public int[] getNumberGroupSizes() {
		return this.numberGroup.getGroupSizes();
	}

	public FormattedNumber setFractionGroupChars(char... groupChars) {
		this.fractionGroup.setGroupChars(groupChars);
		return this;
	}

	public char[] getFractionGroupChars() {
		return this.fractionGroup.getGroupChars();
	}

	@Override
	protected String getToken(Number item, LocalizationStyle style) {
		Character decimalSep = getDecimalSeparator();
		if (useStyle || this.decimalSeparator == null || !this.groupCharsSet) {
			DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(style
					.getNumberLocale());
			DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();
			if (this.decimalSeparator == null) {
				decimalSep = symbols.getDecimalSeparator();
			}
			if (!this.groupCharsSet) {
				numberGroup.setGroupChars(symbols.getGroupingSeparator());
			}
		}
		DecimalFormat splitDF = null;
		if (!signOmited) {
			splitDF = new DecimalFormat("#0.0#");
		} else {
			splitDF = new DecimalFormat("#0.0#"
					+ DecimalFormatSymbols.getInstance().getPatternSeparator()
					+ "#0.0#");
		}
		String[] parts = splitDF.format(item).split("\\.");
		if (fractionOmited) {
			return numberGroup.group(parts[0]);
		}
		return numberGroup.group(parts[0]) + decimalSep
				+ fractionGroup.group(parts[1]);
	}
}
