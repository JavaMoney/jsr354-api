/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package javax.money.format;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmounts;
import javax.money.MonetaryContext;
import javax.money.MonetaryQuery;

/**
 * Formats instances of {@code MonetaryAmount} to a {@link String} or an
 * {@link Appendable}.
 * <p>
 * Instances of this class are not required to be thread-safe.
 */
public interface MonetaryAmountFormat extends MonetaryQuery<String> {

	/**
	 * The {@link MonetaryContext} to be applied when a {@link MonetaryAmount} is parsed.
	 * @return the {@link MonetaryContext} used, or {@code null}, when the defaults should be used.
	 * @see MonetaryAmounts#getDefaultMonetaryContext
	 */
	public MonetaryContext getMonetaryContext();

	/**
	 * Get the {@link CurrencyUnit} applied by this {@link MonetaryAmountFormat}
	 * instance, when a {@link MonetaryAmount} is parsed from an input, where no
	 * currency information is available/detectable.
	 * 
	 * @return the default {@link CurrencyUnit} instance uses, or {@code null}.
	 */
	public CurrencyUnit getDefaultCurrency();

	/**
	 * Get the {@link FormatStyle} used by this {@link MonetaryAmountFormat}
	 * instance.
	 * 
	 * @return the {@link MonetaryAmountFormat} instance user, never
	 *         {@code null}.
	 */
	public FormatStyle getFormatStyle();

	/**
	 * Formats a value of {@code T} to a {@code String}. The {@link Locale}
	 * passed defines the overall target {@link Locale}, whereas the
	 * {@link LocalizationStyle} attached with the instances configures, how the
	 * {@link MonetaryFormat} should generally behave. The
	 * {@link LocalizationStyle} allows to configure the formatting and parsing
	 * in arbitrary details. The attributes that are supported are determined by
	 * the according {@link MonetaryFormat} implementation:
	 * <ul>
	 * <li>When the {@link MonetaryFormat} was created using the {@link Builder}
	 * , all the {@link FormatToken}, that model the overall format, and the
	 * {@link ItemFactory}, that is responsible for extracting the final parsing
	 * result, returned from a parsing call, are all possible recipients for
	 * attributes of the configuring {@link LocalizationStyle}.
	 * <li>When the {@link MonetaryFormat} was provided by an instance of
	 * {@link ItemFormatFactorySpi} the {@link MonetaryFormat} returned
	 * determines the capabilities that can be configured.
	 * </ul>
	 * 
	 * So, regardless if an {@link MonetaryFormat} is created using the fluent
	 * style {@link Builder} pattern, or provided as preconfigured
	 * implementation, {@link LocalizationStyle}s allow to configure them both
	 * effectively.
	 * 
	 * @param amount
	 *            the amount to print, not {@code null}
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 */
	public String format(MonetaryAmount<?> amount);

	/**
	 * Prints a item value to an {@code Appendable}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder},
	 * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
	 * and {@code StringBuffer} never throw an {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param item
	 *            the item to print, not null
	 * @param locale
	 *            the main target {@link Locale} to be used, not {@code null}
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs, thrown by the {@code appendable}
	 * @throws MonetaryParseException
	 *             if there is a problem while parsing
	 */
	public void print(Appendable appendable, MonetaryAmount<?> amount)
			throws IOException;

	/**
	 * Fully parses the text into an instance of {@code T}.
	 * <p>
	 * The parse must complete normally and parse the entire text. If the parse
	 * completes without reading the entire length of the text, an exception is
	 * thrown. If any other problem occurs during parsing, an exception is
	 * thrown.
	 * <p>
	 * This method uses a {@link Locale} as an input parameter. Additionally the
	 * {@link ItemFormatException} instance is configured by a
	 * {@link LocalizationStyle}. {@link LocalizationStyle}s allows to configure
	 * formatting input in detail. This allows to implement complex formatting
	 * requirements using this interface.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @param locale
	 *            the main target {@link Locale} to be used, not {@code null}
	 * @return the parsed value, never {@code null}
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws MonetaryParseException
	 *             if there is a problem while parsing
	 */
	public MonetaryAmount<?> parse(CharSequence text)
			throws ParseException;

}
