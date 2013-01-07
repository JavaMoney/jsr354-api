/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial implementation
 *    Wernner Keil - extensions and adaptions.
 */
package javax.money.convert;

import java.util.Calendar;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

/**
 * This class provides singleton access to the exchange conversion logic of
 * JavaMoney.
 * 
 * TODO if this is a final class, then how will it get instances of interfaces?
 * Probably a service (->SPI?)
 * 
 * @author Anatole Tresch
 */
public final class ExchangeRates {

	/**
	 * Private singleton constructor.
	 */
	private ExchangeRates() {

	}

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
			ExchangeRateType type) {
		// TODO implement this
		return false;
	}

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
			ExchangeRateType type, boolean deferred) {
		// TODO implement this
		return false;
	}

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
			ExchangeRateType type, Calendar timestamp) {
		// TODO implement this
		return false;
	}

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
			CurrencyUnit target, boolean deferred, long validityDuration) {
		// TODO implement this
		return null;
	}

	/**
	 * Access a exchange rate using the given currencies. The rate is, by
	 * default, deferred.
	 * 
	 * @param source
	 *            source currency.
	 * @param target
	 *            target currency.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit source, CurrencyUnit target) {
		// TODO implement this
		return null;
	}

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
	public ExchangeRate getExchangeRate(ExchangeRate... exchangeRates) {
		// TODO implement this
		return null;
	}

	/**
	 * Method that converts the source {@link MonetaryAmount} to an
	 * {@link MonetaryAmount} with the given target {@link CurrencyUnit}.<br/>
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
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target) {
		// TODO implement this
		return null;
	}

	/**
	 * Method that converts the source {@link MonetaryAmount} to an
	 * {@link MonetaryAmount} with the given target {@link CurrencyUnit}.
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
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target,
			boolean deferred) {
		// TODO implement this
		return null;
	}

	/**
	 * Method that converts the source {@link MonetaryAmount} to an
	 * {@link MonetaryAmount} with the given target {@link CurrencyUnit}.
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
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target,
			Calendar timestamp) {
		// TODO implement this
		return null;
	}

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
			CurrencyUnit targetCurrency) {
		// TODO implement this
		return amount;
	}

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
			CurrencyUnit target, boolean deferred) {
		// TODO implement this
		return amount;
	}

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
			CurrencyUnit target, Calendar timestamp) {
		// TODO implement this
		return amount;
	}

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
			CurrencyUnit targetCurrency, ExchangeRateType type) {
		// TODO implement this
		return null;
	}

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
			CurrencyUnit targetCurrency, ExchangeRateType type, boolean deferred) {
		// TODO implement this
		return null;
	}

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
			Calendar timestamp) {
		// TODO implement this
		return null;
	}

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
