package net.java.javamoney.ri.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.money.format.common.LocalizationStyle;
import javax.money.format.common.StyleableFormatter;
import javax.money.format.common.StyledFormatter;
import javax.money.format.common.StyledFormatterBuilder;

import net.java.javamoney.ri.format.common.AbstractTargeted;
import net.java.javamoney.ri.format.token.Literal;

public class TokenizedFormatterBuilder<T> extends AbstractTargeted<T> implements
		StyledFormatterBuilder<T> {

	private List<FormatToken<T>> tokens = new ArrayList<FormatToken<T>>();

	public TokenizedFormatterBuilder(Class<T> type) {
		super(type);
	}

	public TokenizedFormatterBuilder<T> addToken(FormatToken<T> token) {
		this.tokens.add(token);
		return this;
	}

	public TokenizedFormatterBuilder<T> addToken(String token) {
		this.tokens.add(new Literal<T>(token));
		return this;
	}

	public Enumeration<FormatToken<T>> getTokens() {
		return Collections.enumeration(this.tokens);
	}

	public int getTokenCount() {
		return this.tokens.size();
	}

	public void clear() {
		this.tokens.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public StyleableFormatter<T> toStyleableFormatter() {
		return new TokenizedItemFormatter<>(getTargetClass(),
				tokens.toArray(new FormatToken[tokens.size()]));
	}

	@Override
	public StyledFormatter<T> toFormatter(Locale locale) {
		return toFormatter(LocalizationStyle.of(locale));
	}

	@SuppressWarnings("unchecked")
	@Override
	public StyledFormatter<T> toFormatter(LocalizationStyle style) {
		return new StyledFormatterAdapter<>(getTargetClass(),
				toStyleableFormatter(), style);
	}

}
