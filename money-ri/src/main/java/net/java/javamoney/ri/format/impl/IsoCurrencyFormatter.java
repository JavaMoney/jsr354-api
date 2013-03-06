package net.java.javamoney.ri.format.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Currency;

import javax.money.CurrencyUnit;
import javax.money.LocalizableCurrencyUnit;
import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;

public class IsoCurrencyFormatter implements ItemFormatter<CurrencyUnit> {

	public enum RenderedField {
		ID, CODE, SYMBOL, DISPLAYNAME, NUMERICCODE
	}

	private RenderedField renderedField = RenderedField.CODE;
	private LocalizationStyle style;

	@Override
	public Class<CurrencyUnit> getTargetClass() {
		return CurrencyUnit.class;
	}

	public IsoCurrencyFormatter(LocalizationStyle style) {
		String field = style.getId();
		try {
			renderedField = RenderedField.valueOf(field.toLowerCase());
		} catch (Exception e) {
			throw new IllegalArgumentException("style's id must be one of "
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
