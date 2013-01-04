/*
 * Copyright (c) 2012-2013, Credit Suisse
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.money.convert;

import java.util.Calendar;

import javax.money.MonetaryAmount;
import javax.money.CurrencyUnit;

/**
 * This class provides singleton access to the exchange conversion logic of
 * JavaMoney.
 * 
 * @author Anatole Tresch
 */
public interface ExchangeSupport {

	/**
	 * Checks if an exchange of a currency is defined.
	 * 
	 * @param source
	 *            the source currency
	 * @param target
	 *            the target currency
	 * @return true, if such an exchange is currently defined.
	 */
	public boolean isExchangeDefined(CurrencyUnit src, CurrencyUnit target,
			ExchangeRateType type);

	/**
	 * Checks if an exchange of a currency is defined.
	 * 
	 * @param source
	 *            the source currency
	 * @param target
	 *            the target currency
	 * @param deferred
	 *            if the required exchange rate may be deferred, or a real time
	 *            rate is required.
	 * @return true, if such an exchange is currently defined.
	 */
	public boolean isExchangeDefined(CurrencyUnit src, CurrencyUnit target,
			ExchangeRateType type, boolean deferred);

	/**
	 * Checks if an exchange of a currency is defined.
	 * 
	 * @param source
	 *            the source currency
	 * @param target
	 *            the target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried.
	 * @return true, if such an exchange is currently defined.
	 */
	public boolean isExchangeDefined(CurrencyUnit src, CurrencyUnit target,
			ExchangeRateType type, Calendar timestamp);

	/**
	 * Access a exchange rate using the given currencies.
	 * 
	 * @param source
	 *            source currency.
	 * @param target
	 *            target currency.
	 * @param deferred
	 *            if the exchange rate is a deferred.
	 * @param validityDuration
	 *            duration how long this rate is considered valid.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit source,
			CurrencyUnit target, boolean deferred, long validityDuration);

	/**
	 * Access a exchange rate using the given currencies. The rate is, by
	 * default, deferred.
	 * 
	 * @param source
	 *            source currency.
	 * @param target
	 *            target currency.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit source, CurrencyUnit target);

	/**
	 * Creates a {@link ExchangeRateType#DERIVED} exchange rate using the given
	 * chain of rates.
	 * 
	 * @param exchangeRates
	 *            the chain of rates that define a derived exchange rate from
	 *            the source currency of the first item in the chain to the
	 *            target currency in the last item of the chain. In between
	 *            every target currency must match the source currency of the
	 *            next rate within the chain.
	 * @throws IllegalArgumentException
	 *             if the chain passed is inconsistent.
	 */
	public ExchangeRate getExchangeRate(ExchangeRate... exchangeRates);

	/**
	 * Method that converts the source {@link MonetaryAmount} to an {@link MonetaryAmount} with
	 * the given target {@link CurrencyUnit}.<br/>
	 * By default this method should use a real time conversion, but may also
	 * fall back to deferred data.
	 * 
	 * @param amount
	 *            The source amount
	 * @param target
	 *            The target currency
	 * @return The converted amount, never null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target);

	/**
	 * Method that converts the source {@link MonetaryAmount} to an {@link MonetaryAmount} with
	 * the given target {@link CurrencyUnit}.
	 * 
	 * @param amount
	 *            The source amount
	 * @param target
	 *            The target currency
	 * @param deferred
	 *            if the required exchange rate may be deferred, or a real time
	 *            rate is required.
	 * @return The converted amount, never null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target, boolean deferred);

	/**
	 * Method that converts the source {@link MonetaryAmount} to an {@link MonetaryAmount} with
	 * the given target {@link CurrencyUnit}.
	 * 
	 * @param amount
	 *            The source amount
	 * @param target
	 *            The target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried.
	 * @return The converted amount, never null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target, Calendar timestamp);

	/**
	 * Method that converts the source {@code double} amount in source
	 * {@link CurrencyUnit} to an {@link MonetaryAmount} with the given target
	 * {@link CurrencyUnit}.<br/>
	 * By default this method should use a real time conversion, but may also
	 * fall back to deferred data.
	 * 
	 * @param amount
	 *            The amount.
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @return the converted {@code value} as {@code double}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public double convert(double amount, CurrencyUnit sourceCurrency,
			CurrencyUnit targetCurrency);

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
	 * @param deferred
	 *            if the required exchange rate may be deferred, or a real time
	 *            rate is required.
	 * @return the converted {@code value} as {@code double}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public double convert(double amount, CurrencyUnit source,
			CurrencyUnit target, boolean deferred);

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
	 *            the target timestamp for which the exchange rate is queried.
	 * @return the converted {@code value} as {@code double}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public double convert(double amount, CurrencyUnit source,
			CurrencyUnit target, Calendar timestamp);

	/**
	 * Get an {@link ExchangeRate} for a given combination of currencies.<br/>
	 * By default this method should use a real time conversion, but may also
	 * fall back to deferred data.
	 * 
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @param timestamp
	 *            the target timestamp for which the identity check is queried.
	 * @return the matching {@link ExchangeRate}, or null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit sourceCurrency,
			CurrencyUnit targetCurrency, ExchangeRateType type);

	/**
	 * Get an {@link ExchangeRate} for a given combination of currencies.
	 * 
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @param deferred
	 *            if the required exchange rate may be deferred, or a real time
	 *            rate is required.
	 * @return the matching {@link ExchangeRate}, or null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit sourceCurrency,
			CurrencyUnit targetCurrency, ExchangeRateType type, boolean deferred);

	/**
	 * Get an {@link ExchangeRate} for a given timestamp (including historic
	 * rates).
	 * 
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried.
	 * @return the matching {@link ExchangeRate}, or null.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit sourceCurrency,
			CurrencyUnit targetCurrency, ExchangeRateType type,
			Calendar timestamp);

	// TODO Use case, background?
	// /**
	// * Checks if a conversion is linear.
	// *
	// * @param sourceCurrency
	// * The source currency
	// * @param targetCurrency
	// * The target currency
	// * @param deferred
	// * if the required exchange rate may be deferred, or a real time
	// * rate is required.
	// * @return true, if the conversion is linear.
	// * @throws CurrencyConversionException
	// * if conversion failed, or the required data is not available.
	// */
	// public boolean isLinear(CurrencyUnit sourceCurrency,
	// CurrencyUnit targetCurrency, boolean deferred);
	//
	// /**
	// * Checks if a conversion is linear.
	// *
	// * @param sourceCurrency
	// * The source currency
	// * @param targetCurrency
	// * The target currency
	// * @param timestamp
	// * the target timestamp for which the exchange rate is queried.
	// * @return true, if the conversion is linear.
	// * @throws CurrencyConversionException
	// * if conversion failed, or the required data is not available.
	// */
	// public boolean isLinear(CurrencyUnit sourceCurrency,
	// CurrencyUnit targetCurrency, Calendar timestamp);
	// /**
	// * Checks if a conversion is an identity.
	// *
	// * @param sourceCurrency
	// * The source currency
	// * @param targetCurrency
	// * The target currency
	// * @param timestamp
	// * the target timestamp for which the identity check is queried.
	// * @return true, if the conversion is linear.
	// * @throws CurrencyConversionException
	// * if conversion failed, or the required data is not available.
	// */
	// public boolean isIdentity(CurrencyUnit sourceCurrency,
	// CurrencyUnit targetCurrency, Calendar timestamp);
	//
	// /**
	// * Checks if a conversion is an identity.
	// *
	// * @param sourceCurrency
	// * The source currency
	// * @param targetCurrency
	// * The target currency
	// * @param deferred
	// * if the required exchange rate may be deferred, or a real time
	// * rate is required.
	// * @return true, if the conversion is linear.
	// * @throws CurrencyConversionException
	// * if conversion failed, or the required data is not available.
	// */
	// public boolean isIdentity(CurrencyUnit sourceCurrency,
	// CurrencyUnit targetCurrency, boolean deferred);

}
