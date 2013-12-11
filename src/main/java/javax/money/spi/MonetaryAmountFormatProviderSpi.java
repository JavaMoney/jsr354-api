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
package javax.money.spi;

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import javax.money.format.FormatStyle;
import javax.money.format.MonetaryAmountFormat;

/**
 * SPI providing {@link MonetaryAmountFormat} instances.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryAmountFormatProviderSpi {

	/**
	 * Create a new {@link MonetaryAmountFormat} for the given input.
	 * 
	 * @param locale
	 *            The {@link Locale} to be used for determining the
	 *            {@link FormatStyle} to be used.
	 * @param monetaryContext
	 *            The {@link MonetaryContext}, to be required for creation of
	 *            new {@link MonetaryAmount} instances during parsing.
	 * @param defaultCurrency
	 *            The {@link CurrencyUnit} to be set, if a
	 *            {@link MonetaryAmount} is parsed from an input, without
	 *            currency information.
	 * @return An according {@link MonetaryAmountFormat} instance, or
	 *         {@code null}, which delegates the request to subsequent
	 *         {@link MonetaryAmountFormatProviderSpi} instances registered.
	 */
	public MonetaryAmountFormat getFormat(Locale locale,
			MonetaryContext monetaryContext,
			CurrencyUnit defaultCurrency);

	/**
	 * Create a new {@link MonetaryAmountFormat} for the given input.
	 * 
	 * @param formatStyle
	 *            The {@link FormatStyle} to be used.
	 * @param monetaryContext
	 *            The {@link MonetaryContext}, to be required for creation of
	 *            new {@link MonetaryAmount} instances during parsing.
	 * @param defaultCurrency
	 *            The {@link CurrencyUnit} to be set, if a
	 *            {@link MonetaryAmount} is parsed from an input, without
	 *            currency information.
	 * @return An according {@link MonetaryAmountFormat} instance, or
	 *         {@code null}, which delegates the request to subsequent
	 *         {@link MonetaryAmountFormatProviderSpi} instances registered.
	 */
	public MonetaryAmountFormat getFormat(
			FormatStyle formatStyle,
			MonetaryContext monetaryContext,
			CurrencyUnit defaultCurrency);

}
