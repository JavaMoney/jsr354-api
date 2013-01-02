/*
 * Copyright (c) 2012-2013,  Credit Suisse (Anatole Tresch), Werner Keil
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
