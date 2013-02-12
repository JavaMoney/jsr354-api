package net.java.javamoney.ri.format.common;

import java.io.IOException;
import java.util.Locale;

import javax.money.format.common.FormatException;
import javax.money.format.common.LocalizationStyle;
import javax.money.format.common.StyleableFormatter;


public class TokenizedItemFormatter<T> extends AbstractTargeted<T> implements
		StyleableFormatter<T> {

	private FormatToken<T>[] tokens;

	public TokenizedItemFormatter(Class<T> type, FormatToken<T>[] tokens) {
		super(type);
		// TODO check compatibility of tokens...
		this.tokens = tokens.clone();
	}

	@Override
	public void print(Appendable appendable, T item, Locale locale)
			throws IOException {
		print(appendable, item, LocalizationStyle.of(locale));
	}

	@Override
	public String print(T item, Locale locale) throws FormatException {
		return print(item, LocalizationStyle.of(locale));
	}

	@Override
	public void print(Appendable appendable, T item, LocalizationStyle style)
			throws IOException {
		for (int i = 0; i < tokens.length; i++) {
			tokens[i].print(appendable, item, style);
		}
	}

	@Override
	public String print(T item, LocalizationStyle style) throws FormatException {
		StringBuilder builder = new StringBuilder();
		try {
			print(builder, item, style);
		} catch (IOException e) {
			throw new FormatException("Error foratting of " + item, e);
		}
		return builder.toString();
	}

}
