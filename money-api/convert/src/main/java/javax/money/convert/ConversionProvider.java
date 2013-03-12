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

import java.util.Enumeration;

/**
 * This interface defines access to the exchange and currency conversion logic
 * of JavaMoney.
 * 
 * @author Anatole Tresch
 */
public interface ConversionProvider {

	/**
	 * Access an instance of {@link CurrencyConverter} for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param type
	 *            the exchange rate type that this provider instance is
	 *            providing data for, not {@code null}.
	 * 
	 * @return the {@link ExchangeRateType} if this instance.
	 */
	public CurrencyConverter getCurrencyConverter(ExchangeRateType type);

	/**
	 * Access an instance of {@link ExchangeRateProvider} for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param type
	 *            the exchange rate type that this provider instance is
	 *            providing data for, not {@code null}.
	 * 
	 * @return the {@link ExchangeRateType} if this instance.
	 */
	public ExchangeRateProvider getExchangeRateProvider(ExchangeRateType type);

	/**
	 * Return all supported {@link ExchangeRateType} instances for which
	 * {@link ExchangeRateProvider} instances can be obtained.
	 * 
	 * @return all supported {@link ExchangeRateType} instances, never
	 *         {@code null}.
	 */
	public Enumeration<ExchangeRateType> getSupportedExchangeRateTypes();

	/**
	 * CHecks if a {@link ExchangeRateProvider} can be accessed for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param type
	 *            the required {@link ExchangeRateType}, not {@code null}.
	 * @return true, if a {@link ExchangeRateProvider} for this
	 *         {@link ExchangeRateType} can be obtained from this
	 *         {@link ConversionProvider} instance.
	 */
	public boolean isSupportedExchangeRateType(ExchangeRateType type);

}
