/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.convert;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

/**
 * This interface defines access to the exchange conversion logic of JavaMoney.
 * It is provided by the Money singleton. It is provided by the Money singleton.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyConverter {

	/**
	 * Get the exchange rate type that this provider instance is providing data
	 * for.
	 * 
	 * @return the {@link ExchangeRateType} if this instance.
	 */
	public ExchangeRateType getExchangeRateType();

	/**
	 * Method that converts the source {@code double} amount in source
	 * {@link CurrencyUnit} to an {@link MonetaryAmount} with the given target
	 * {@link CurrencyUnit}.
	 * 
	 * @param amount
	 *            The amount.
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried,
	 *            or {@code null}.
	 * @return the converted {@code value} as {@code double}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target);

	/**
	 * Method that converts the source {@code double} amount in source
	 * {@link CurrencyUnit} to an {@link MonetaryAmount} with the given target
	 * {@link CurrencyUnit}.
	 * 
	 * @param amount
	 *            The amount.
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried,
	 *            or {@code null}.
	 * @return the converted {@code value} as {@code double}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target,
			Long timestamp);

	/**
	 * Access a {@link FixedCurrencyConversion} instance for the corresponding
	 * current {@link ExchangeRate} defined by the parameters passed.
	 * 
	 * @param base
	 *            The base currency
	 * @param term
	 *            The terminating currency
	 * @return The according {@link FixedCurrencyConversion}, never null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public CurrencyConversion getConversion(CurrencyUnit base,
			CurrencyUnit term);

	/**
	 * Access a {@link FixedCurrencyConversion} instance for the corresponding
	 * current {@link ExchangeRate} defined by the parameters passed.
	 * 
	 * @param base
	 *            The base currency
	 * @param term
	 *            The terminating currency
	 * @param targetTimestamp
	 *            for which the conversion is targeted.
	 * @return The according {@link FixedCurrencyConversion}, never null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public CurrencyConversion getConversion(CurrencyUnit base,
			CurrencyUnit term, Long targetTimestamp);

	/**
	 * Access a {@link CurrencyConversion} instance that is bound to the given
	 * terminating {@link CurrencyUnit}. The base {@link CurrencyUnit} is
	 * evaluated from the {@link MonetaryAmount} passed within its
	 * {@link CurrencyConversion#apply(MonetaryAmount)} method.
	 * 
	 * @param term
	 *            The terminating currency
	 * @return The according {@link FixedCurrencyConversion}, never null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public CurrencyConversion getConversion(CurrencyUnit term);

	/**
	 * Access a {@link CurrencyConversion} instance that is bound to the given
	 * terminating {@link CurrencyUnit}. The base {@link CurrencyUnit} is
	 * evaluated from the {@link MonetaryAmount} passed within its
	 * {@link CurrencyConversion#apply(MonetaryAmount)} method.
	 * 
	 * @param term
	 *            The terminating currency
	 *            
	 * @return The according {@link FixedCurrencyConversion}, never null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public CurrencyConversion getConversion(CurrencyUnit term,
			Long targetTimestamp);

}
