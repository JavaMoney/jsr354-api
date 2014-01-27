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
package javax.money.convert;

import javax.money.CurrencyUnit;

/**
 * This interface defines access to the exchange conversion logic of JavaMoney.
 * It is provided by the {@link MonetaryConversions} singleton. Hereby a
 * instance of this class must only provide conversion data for exact one
 * exchange rate type, defined by {@link #getExchangeRateType()}.
 * <p>
 * Implementations of this interface are required to be thread save.
 * <p>
 * Implementations of this class must neither be immutable nor serializable.
 * 
 * @author Anatole Tresch
 */
public interface ExchangeRateProvider {

	/**
	 * Access the {@link ConversionContext} for this
	 * {@link ExchangeRateProvider}. Each instance of
	 * {@link ExchangeRateProvider} provides conversion data for exact one
	 * {@link ConversionContext} .
	 * 
	 * @return the exchange rate type, never {@code null}.
	 */
	ConversionContext getConversionContext();

	/**
	 * Checks if an {@link ExchangeRate} between two {@link CurrencyUnit} is
	 * available from this provider. This method should check, if a given rate
	 * is <i>currently</i> defined. It should be the same as
	 * {@code isAvailable(base, term, System.currentTimeMillis())}.
	 * 
	 * @param base
	 *            the base {@link CurrencyUnit}
	 * @param term
	 *            the term {@link CurrencyUnit}
	 * @return {@code true}, if such an {@link ExchangeRate} is currently
	 *         defined.
	 */
	boolean isAvailable(CurrencyUnit base, CurrencyUnit term);

	/**
	 * Checks if an {@link ExchangeRate} between two {@link CurrencyUnit} is
	 * available from this provider. This method should check, if a given rate
	 * is <i>currently</i> defined. It should be the same as
	 * {@code isAvailable(base, term, System.currentTimeMillis())}.
	 * 
	 * @param base
	 *            the base {@link CurrencyUnit}
	 * @param term
	 *            the term {@link CurrencyUnit}
	 * @param conversionContext
	 *            the required {@link ConversionContext}, not {@code null}
	 * @return {@code true}, if such an {@link ExchangeRate} is currently
	 *         defined.
	 */
	boolean isAvailable(CurrencyUnit base, CurrencyUnit term,
			ConversionContext conversionContext);

	/**
	 * Access a {@link ExchangeRate} using the given currencies. The
	 * {@link ExchangeRate} may be, depending on the data provider, eal-time or
	 * deferred. This method should return the rate that is <i>currently</i>
	 * valid. It should be the same as
	 * {@code getExchangeRate(base, term, System.currentTimeMillis())}.
	 * 
	 * @param base
	 *            base {@link CurrencyUnit}, not {@code null}
	 * @param term
	 *            term {@link CurrencyUnit}, not {@code null}
	 * @return the matching {@link ExchangeRate}.
	 * @throws CurrencyConversionException
	 *             If no such rate is available.
	 */
	ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term);

	/**
	 * Access a {@link ExchangeRate} using the given currencies. The
	 * {@link ExchangeRate} may be, depending on the data provider, eal-time or
	 * deferred. This method should return the rate that is <i>currently</i>
	 * valid. It should be the same as
	 * {@code getExchangeRate(base, term, System.currentTimeMillis())}.
	 * 
	 * @param base
	 *            base {@link CurrencyUnit}, not {@code null}
	 * @param term
	 *            term {@link CurrencyUnit}, not {@code null}
	 * @param conversionContext
	 *            the required {@link ConversionContext}, not {@code null}
	 * @return the matching {@link ExchangeRate}.
	 * @throws CurrencyConversionException
	 *             If no such rate is available.
	 */
	ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term,
			ConversionContext conversionContext);

	/**
	 * The method reverses the {@link ExchangeRate} to a rate mapping from term
	 * to base {@link CurrencyUnit}. Hereby the factor must <b>not</b> be
	 * recalculated as {@code 1/oldFactor}, since typically reverse rates are
	 * not symmetric in most cases.
	 * 
	 * @return the matching reversed {@link ExchangeRate}, or {@code null}, if
	 *         the rate cannot be reversed.
	 */
	ExchangeRate getReversed(ExchangeRate rate);

	/**
	 * Access a {@link CurrencyConverter} that can be applied as a
	 * {@link MonetaryOperator} to an amount.
	 * 
	 * @param term
	 *            term {@link CurrencyUnit}, not {@code null}
	 * @return a new instance of a corresponding {@link CurrencyConverter},
	 *         never {@code null}.
	 */
	CurrencyConversion getCurrencyConversion(CurrencyUnit term);

	/**
	 * Access a {@link CurrencyConverter} that can be applied as a
	 * {@link MonetaryOperator} to an amount.
	 * 
	 * @param term
	 *            term {@link CurrencyUnit}, not {@code null}
	 * @param conversionContext
	 *            the required {@link ConversionContext}, not {@code null}
	 * @return a new instance of a corresponding {@link CurrencyConverter},
	 *         never {@code null}.
	 */
	CurrencyConversion getCurrencyConversion(CurrencyUnit term,
			ConversionContext conversionContext);
}
