/*
 *  Copyright 2012 Credit Suisse (Anatole Tresch)
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
 */
package javax.money.convert;

import javax.money.CurrencyUnit;

/**
 * This class provides singleton access to the exchange conversion logic of
 * JavaMoney.
 * 
 * @author Anatole Tresch
 */
public final class MoneyExchange {
	/**
	 * Private singleton constructor.
	 */
	private MoneyExchange() {

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
	public static ExchangeRate getExchangeRate(CurrencyUnit source,
			CurrencyUnit target, boolean deferred, long validdityDuration) {
		// TODO
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
	public static ExchangeRate getExchangeRate(CurrencyUnit source,
			CurrencyUnit target) {
		// TODO
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
	public static ExchangeRate getExchangeRate(ExchangeRate... exchangeRates) {
		// TODO
		return null;
	}

	/**
	 * Access a {@link CurrencyConverter} for the given currencies.
	 * 
	 * @param src
	 *            the source currency
	 * @param tgt
	 *            the target currency
	 * @return a instance of a currency converter.
	 */
	public CurrencyConverter getConverter(CurrencyUnit src, CurrencyUnit tgt) {
		return null;
	}
}
