/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.format;

import java.util.Enumeration;

/**
 * This interface models a builder for a {@link TokenizedItemFormatter}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The target type to be formatted.
 */
public interface TokenizedItemFormatterBuilder<T> {

	/**
	 * Adds a {@link FormatToken} to the formatting process chain.
	 * 
	 * @param token
	 *            the token to be added, not {@code null}.
	 * @return the builder instance.
	 */
	public TokenizedItemFormatterBuilder<T> addToken(FormatToken<T> token);

	/**
	 * Adds a literal to the output format.
	 * 
	 * @param token
	 *            The literal token
	 * @return the builder instance.
	 */
	public TokenizedItemFormatterBuilder<T> addToken(String token);

	/**
	 * Set the {@link LocalizationStyle} to be used for configuring the
	 * {@link ItemFormatter} to be build.
	 * 
	 * @param style
	 *            the {@link LocalizationStyle} to be used
	 * @return the builder instance
	 */
	public TokenizedItemFormatterBuilder<T> setLocalizationStyle(
			LocalizationStyle style);

	/**
	 * Access the {@link LocalizationStyle} to be used for configuring the
	 * {@link ItemFormatter} to be build.
	 * 
	 * @return
	 */
	public LocalizationStyle getLocalizationStyle();

	/**
	 * Access all tokens currently defined.
	 * 
	 * @return Return the tokens currently defined, never {@code null}.
	 */
	public Enumeration<FormatToken<T>> getTokens();

	/**
	 * Access the number of tokens currently defined.
	 * 
	 * @return the number of tokens defined.
	 */
	public int getTokenCount();

	/**
	 * Reset all the current tokesn in the builder's list.
	 */
	public void clear();

	/**
	 * Method to determ ine if the current builder can build a new instance of
	 * {@link ItemFormatter}.
	 * 
	 * @return true if the builder can build a new instance of
	 *         {@link ItemFormatter}.
	 */
	public boolean isBuildable();

	/**
	 * Create a new instance of {@link ItemFormatter}
	 * 
	 * @return a new instance of {@link ItemFormatter}
	 * @throws IllegalStateException
	 *             If the builder can not build.
	 */
	public abstract ItemFormatter<T> build();

}