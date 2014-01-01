/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import java.io.IOException;

import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import javax.money.MonetaryQuery;

/**
 * Formats instances of {@code MonetaryAmount} to a {@link String} or an {@link Appendable}.
 * <p>
 * Instances of this class are not required to be thread-safe.
 */
public interface MonetaryAmountFormat extends MonetaryQuery<String> {

	/**
	 * The {@link MonetaryContext} to be applied when a {@link MonetaryAmount} is parsed.
	 * 
	 * @return the {@link MonetaryContext} used, or {@code null}, when the defaults should be used.
	 * @see MonetaryAmount#getMonetaryContext
	 */
	public MonetaryContext getMonetaryContext();

	/**
	 * Formats the given {@link MonetaryAmount} to a String.
	 * 
	 * @param amount
	 *            the amount to format, not {@code null}
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 */
	public String format(MonetaryAmount amount);

	/**
	 * Formats the given {@link MonetaryAmount} to a {@code Appendable}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder}, {@code StringBuffer}
	 * or {@code Writer}. Note that {@code StringBuilder} and {@code StringBuffer} never throw an
	 * {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param amount
	 *            the amount to print, not null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws IOException
	 *             if an IO error occurs, thrown by the {@code appendable}
	 * @throws MonetaryParseException
	 *             if there is a problem while parsing
	 */
	public void print(Appendable appendable, MonetaryAmount amount)
			throws IOException;

	/**
	 * Fully parses the text into an instance of {@link MonetaryAmount}.
	 * <p>
	 * The parse must complete normally and parse the entire text. If the parse completes without
	 * reading the entire length of the text, an exception is thrown. If any other problem occurs
	 * during parsing, an exception is thrown.
	 * <p>
	 * This method uses a mainly delegates to contained {@link AmountStyle}. Additionally when no
	 * currency is on the input stream, the value of {@link #getDefaultCurrency()} is used instead
	 * of.
	 * <p>
	 * Additionally the effective implementation type returned can be determined by the
	 * {@link MonetaryContext} applied to the {@link MonetaryAmountFormat}. This formatter will call
	 * {@link javax.money.MonetaryAmounts#getDefaultAmountType()} and will use the result returned
	 * to access a corresponding {@link javax.money.MonetaryAmountFactory} to create the instance
	 * returned.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @return the parsed value, never {@code null}
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws MonetaryParseException
	 *             if there is a problem while parsing
	 */
	public MonetaryAmount parse(CharSequence text)
			throws MonetaryParseException;

}
