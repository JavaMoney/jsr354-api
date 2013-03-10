package net.java.javamoney.ri.format.impl.parse;

import java.util.Arrays;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.format.ItemParseException;
import javax.money.format.ItemParser;
import javax.money.format.LocalizationStyle;

import net.java.javamoney.ri.core.MoneyCurrency;

public class IsoCurrencyParser implements ItemParser<CurrencyUnit> {

	public enum ParsedField {
		ID, CODE
	}

	private ParsedField[] parsedFields = new ParsedField[] { ParsedField.CODE };
	private LocalizationStyle style;

	@Override
	public Class<CurrencyUnit> getTargetClass() {
		return CurrencyUnit.class;
	}

	public IsoCurrencyParser(LocalizationStyle style) {
		String value = (String) style
				.getAttribute("parsedFields", String.class);
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

	@Override
	public CurrencyUnit parse(CharSequence text) throws ItemParseException {
		// try to check for non localizaed formats
		String namespace = this.style.getAttribute("namespace", String.class);
		String currencyCode = null;
		String textString = text.toString();
		for (ParsedField f : parsedFields) {
			switch (f) {
			case ID:
				int index = textString.indexOf(':');
				if (index <= 0) {
					throw new ItemParseException(
							"Currency ID format required namespace:code.",
							text, 0);
				}
				namespace = textString.substring(0, index);
				currencyCode = textString.substring(index + 1);
				return MoneyCurrency.getInstance(namespace, currencyCode);
			case CODE:
				if (namespace == null) {
					throw new ItemParseException(
							"Currency CODE format requires namespace attribute.",
							text, 0);
				}
				return MoneyCurrency.getInstance(namespace, text.toString());
			}
		}
		throw new ItemParseException("Currency not parseable.", text, 0);
	}
}
