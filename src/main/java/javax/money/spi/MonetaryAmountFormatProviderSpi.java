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
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryAmountFormat.Builder;

/**
 * SPI providing {@link MonetaryAmountFormat} instances and the according
 * {@link Builder} instances for dynamic format.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryAmountFormatProviderSpi {
	/**
	 * Access a {@link MonetaryAmountFormat} for the given {@link Locale} and
	 * {@link CurrencyUnit}.
	 * 
	 * @param locale
	 *            the target {@link Locale}, not {@code null}.
	 * @param currency
	 *            the target {@link CurrencyUnit}, not {@code null}.
	 * @return the target {@link MonetaryAmountFormat}, never {@code null}.
	 */
	public MonetaryAmountFormat getFormat(Locale locale, CurrencyUnit currency);

	/**
	 * Access a {@link MonetaryAmountFormat.Builder} for the given
	 * {@link Locale}.
	 * 
	 * @param locale
	 *            the target {@link Locale}, not {@code null}.
	 * @return the target {@link MonetaryAmountFormat.Builder}, never {@code null}.
	 */
	public Builder getFormatBuilder(Locale locale);

}
