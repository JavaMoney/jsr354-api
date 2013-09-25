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
package org.javamoney.convert;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

/**
 * This interface defines access to the exchange conversion logic of JavaMoney.
 * It is provided by a {@link ConversionProvider} implementation.
 * <p>
 * Instances fo this interface must be thread-safe, however they are not
 * required to be serializable.
 * 
 * @see ConversionProvider
 * @see MonetaryConversions
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface CurrencyConverter {

	/**
	 * Get the {@link ExchangeRateType} that this provider instance is providing
	 * data for.
	 * 
	 * @return the exchange rate type if this instance.
	 */
	public ExchangeRateType getExchangeRateType();

	/**
	 * Method that converts the source amount to an {@link MonetaryAmount} in
	 * the given target {@link CurrencyUnit}.
	 * 
	 * @param amount
	 *            The amount to be converted.
	 * @param targetCurrency
	 *            The target currency
	 * @return the converted {@code value} as {@code double}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public <T extends MonetaryAmount> T convert(T amount,
			CurrencyUnit targetCurrency);

	/**
	 * Method that converts the source amount to an {@link MonetaryAmount} in
	 * the given target {@link CurrencyUnit}.
	 * 
	 * @param amount
	 *            The amount.
	 * @param term
	 *            The term (target) currency
	 * @param timestamp
	 *            the target time stamp for which the exchange rate is queried,
	 *            or {@code null}, for acquiring a current rate.
	 * @return the converted {@code MonetaryAmount}, never {@code null}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public <T extends MonetaryAmount> T convert(T amount,
			CurrencyUnit term, Long timestamp);

	/**
	 * Access a {@link CurrencyConversion} instance for the corresponding
	 * current {@link ExchangeRate} defined by the parameters passed.<br/>
	 * The returned {@link CurrencyConversion} can be passed to
	 * {@link MonetaryAmount#with(javax.money.MonetaryOperator)} for converting
	 * a {@link MonetaryAmount}.
	 * 
	 * @see CurrencyConversion
	 * @see FixedCurrencyConversion
	 * @param base
	 *            The base currency
	 * @param term
	 *            The terminating currency
	 * @return The according {@link CurrencyConversion}, never {@code null}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public CurrencyConversion getConversion(CurrencyUnit base, CurrencyUnit term);

	/**
	 * Access a {@link CurrencyConversion} instance for the corresponding
	 * current {@link ExchangeRate} defined by the parameters passed.<br/>
	 * The returned {@link CurrencyConversion} can be passed to
	 * {@link MonetaryAmount#with(javax.money.MonetaryOperator)} for converting
	 * a {@link MonetaryAmount}.
	 * 
	 * @see CurrencyConversion
	 * @see FixedCurrencyConversion
	 * @param base
	 *            The base currency
	 * @param term
	 *            The terminating currency
	 * @param targetTimestamp
	 *            for which the conversion is targeted.
	 * @return The according {@link CurrencyConversion}, never {@code null}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public CurrencyConversion getConversion(CurrencyUnit base,
			CurrencyUnit term, long targetTimestamp);

	/**
	 * Access a {@link CurrencyConversion} instance that is bound to the given
	 * terminating {@link CurrencyUnit}. The base {@link CurrencyUnit} is
	 * evaluated from the {@link MonetaryAmount} passed within its
	 * {@link CurrencyConversion#apply(MonetaryAmount)} method. The rate
	 * required for conversion then is accessed lazily from the corresponding
	 * (owning) {@link ConversionProvider}.<br/>
	 * The returned {@link CurrencyConversion} can be passed to
	 * {@link MonetaryAmount#with(javax.money.MonetaryOperator)} for converting
	 * a {@link MonetaryAmount}.
	 * 
	 * @see ConversionProvider
	 * @param term
	 *            The terminating currency
	 * @return The according {@link CurrencyConversion}, never {@code null}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public CurrencyConversion getConversion(CurrencyUnit term);

	/**
	 * Access a {@link CurrencyConversion} instance that is bound to the given
	 * terminating {@link CurrencyUnit}. The base {@link CurrencyUnit} is
	 * evaluated from the {@link MonetaryAmount} passed within its
	 * {@link CurrencyConversion#apply(MonetaryAmount)} method. The rate
	 * required for conversion then is accessed lazily from the corresponding
	 * (owning) {@link ConversionProvider}.<br/>
	 * The returned {@link CurrencyConversion} can be passed to
	 * {@link MonetaryAmount#with(javax.money.MonetaryOperator)} for converting
	 * a {@link MonetaryAmount}.
	 * 
	 * @param term
	 *            The terminating currency
	 * @param targetTimestamp
	 *            for which the conversion is targeted.
	 * @return The according {@link CurrencyConversion}, never {@code null}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public CurrencyConversion getConversion(CurrencyUnit term,
			long targetTimestamp);

}
