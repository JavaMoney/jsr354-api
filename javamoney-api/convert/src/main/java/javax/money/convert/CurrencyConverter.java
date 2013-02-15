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
	 * @return the {@link ConversionType} if this instance.
	 */
	public ConversionType<CurrencyUnit,CurrencyUnit> getConversionType();

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
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target);

	/**
	 * Method that converts the source {@link MonetaryAmount} to an
	 * {@link MonetaryAmount} with the given target {@link CurrencyUnit}.
	 * 
	 * @param amount
	 *            The source amount
	 * @param target
	 *            The target currency
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried,
	 *            or {@code null}.
	 * @return The converted amount, never null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target,
			Long timestamp);

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
	public MonetaryAmount convert(Number amount, CurrencyUnit sourceCurrency,
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
	 * @param timestamp
	 *            the target timestamp for which the exchange rate is queried,
	 *            or {@code null}.
	 * @return the converted {@code value} as {@code double}.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public MonetaryAmount convert(Number amount, CurrencyUnit source,
			CurrencyUnit target, Long timestamp);

}
