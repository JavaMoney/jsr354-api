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
 * @author Anatole Tresch
 */
public interface ExchangeRateProvider {

	/**
	 * Get the exchange rate types that this provider instance is providing data
	 * for.
	 * 
	 * @return the {@link ExchangeRateType} delivered by this provider.
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
	 *            the target timestamp for which the exchange rate is queried,
	 *            or {@code null}.
	 * @return true, if such an exchange is currently defined.
	 */
	public boolean isAvailable(CurrencyUnit CurrencyUnit, CurrencyUnit target, Long timestamp);

	/**
	 * Get an {@link ConversionRate} for a given timestamp (including historic
	 * rates).
	 * 
	 * @param sourceCurrency
	 *            The source currency
	 * @param targetCurrency
	 *            The target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried,
	 *            or {@code null}.
	 * @return the matching {@link ConversionRate}, or null.
	 */
	public ExchangeRate get(CurrencyUnit sourceCurrency, CurrencyUnit targetCurrency,
			Long timestamp);

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

}
