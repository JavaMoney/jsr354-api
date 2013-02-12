package net.java.javamoney.ri.format.common;

import javax.money.format.common.StyledFormatterBuilder;

public interface TokenizableFormatterBuilder<T> extends StyledFormatterBuilder<T> {

	/**
	 * Adds a format part representing by the given {@link AbstractToken}.
	 * @param token the {@link AbstractToken}, never null.
	 */
	public void addToken(FormatToken<T> token);
	
}
