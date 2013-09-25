/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package org.javamoney.format;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

/**
 * {@link FormatToken} which adds an arbitrary literal constant value to the
 * output.
 * <p>
 * This class is thread safe, immutable and serializable.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The item type.
 */
public final class LiteralToken<R> implements FormatToken<R>, Serializable {

	/**
	 * The literal part.
	 */
	private String token;
	/** The token's id, or null. */
	private String tokenId;

	/**
	 * Constructor.
	 * 
	 * @param token
	 *            The literal token part.
	 */
	public LiteralToken(String token) {
		if (token == null) {
			throw new IllegalArgumentException("Token is required.");
		}
		this.token = token;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.format.FormatToken#getTokenId()
	 */
	@Override
	public String getTokenId() {
		return tokenId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.format.FormatToken#parse(javax.money.format.ParseContext,
	 * java.util.Locale, javax.money.format.LocalizationStyle)
	 */
	@Override
	public void parse(ParseContext<R> context, Locale locale,
			LocalizationStyle style)
			throws ItemParseException {
		if (!context.consume(token)) {
			throw new ItemParseException("Expected '" + token + "' in "
					+ context.getInput().toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.format.FormatToken#print(java.lang.Appendable,
	 * java.lang.Object, java.util.Locale, javax.money.format.LocalizationStyle)
	 */
	@Override
	public void print(Appendable appendable, R item, Locale locale,
			LocalizationStyle style)
			throws IOException {
		appendable.append(this.token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LiteralToken [token=" + token + "]";
	}

}
