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

import javax.money.CurrencyUnit;

/**
 * This interface defines access to the exchange conversion logic of JavaMoney.
 * It is provided by the Money singleton. It is provided by the Money singleton.
 * 
 * @author Anatole Tresch TODO should this probably be called CurrencyConverter?
 *         The main action is convert() Getters for ExchangeRate might remain
 *         here, separated from convert() methods in a *Converter interface...?
 */
public interface ExchangeRateProvider {

	/**
	 * Get the exchange rate type that this provider instance is providing data
	 * for.
	 * 
	 * @return the {@link ExchangeRateType} if this instance.
	 */
	public ExchangeRateType getExchangeRateType();

	/**
	 * Checks if an exchange of a currency is defined.
	 * 
	 * @param source
	 *            the source currency
	 * @param target
	 *            the target currency
	 * @return true, if such an exchange is currently defined.
	 */
	public boolean isAvailable(CurrencyUnit src, CurrencyUnit target);

	/**
	 * Checks if an exchange of a currency is defined.
	 * 
	 * @param source
	 *            the source currency
	 * @param target
	 *            the target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried, or {@code null}.
	 * @return true, if such an exchange is currently defined.
	 */
	public boolean isAvailable(CurrencyUnit src, CurrencyUnit target,
			Long timestamp);

	/**
	 * Get an {@link ExchangeRate} for a given timestamp (including historic
	 * rates).
	 * 
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried, or {@code null}.
	 * @return the matching {@link ExchangeRate}, or null.
	 */
	public ExchangeRate get(CurrencyUnit sourceCurrency,
			CurrencyUnit targetCurrency, Long timestamp);

	/**
	 * Access a exchange rate using the given currencies. The rate may be,
	 * depending on the data provider, be real-time or deferred.
	 * 
	 * @param source
	 *            source currency.
	 * @param target
	 *            target currency.
	 */
	public ExchangeRate get(CurrencyUnit source, CurrencyUnit target);

	/**
	 * Creates a derived {@link ExchangeRate} using the given chain of rates.
	 * <p>
	 * The method must validate that each target {@link CurrencyUnit} matches
	 * the {@link CurrencyUnit} of the next {@link ExchangeRate} instance.
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
	public ExchangeRate get(ExchangeRate... exchangeRates);

	/**
	 * Checks if a conversion is linear.
	 * 
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @param deferred
	 *            if the required exchange rate may be deferred, or a real time
	 *            rate is required.
	 * @return true, if the conversion is linear.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public boolean isLinear(CurrencyUnit sourceCurrency,
			CurrencyUnit targetCurrency);

	/**
	 * Checks if a conversion is linear.
	 * 
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried, or{@code null}
	 * @return true, if the conversion is linear.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public boolean isLinear(CurrencyUnit sourceCurrency,
			CurrencyUnit targetCurrency, Long timestamp);

	/**
	 * Checks if a conversion is an identity.
	 * 
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @return true, if the conversion is linear.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public boolean isIdentity(CurrencyUnit sourceCurrency,
			CurrencyUnit targetCurrency);

	/**
	 * Checks if a conversion is an identity.
	 * 
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @param timestamp
	 *            the target timestamp for which the identity check is queried, or {@code null}.
	 * @return true, if the conversion is linear.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public boolean isIdentity(CurrencyUnit sourceCurrency,
			CurrencyUnit targetCurrency, Long timestamp);

}
