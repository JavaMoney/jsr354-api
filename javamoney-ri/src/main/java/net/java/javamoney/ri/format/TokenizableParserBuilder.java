package net.java.javamoney.ri.format;

import javax.money.format.common.StyledParserBuilder;

public interface TokenizableParserBuilder<T> extends StyledParserBuilder<T> {

	/**
	 * Adds a parsing part representing by the given {@link ParseToken}.
	 * 
	 * @param token
	 *            the {@link ParseToken}, never null.
	 */
	public void addToken(ParseToken<T> token);

}
