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
package javax.money.convert.spi;

import javax.money.CurrencyUnit;
import javax.money.convert.ConversionType;
import javax.money.convert.ExchangeRate;

/**
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author Anatole Tresch
 * @version 0.1.1
 */
public interface ExchangeRateProviderSpi {

	/**
	 * Get the exchange rate type that this provider instance is providing data
	 * for.
	 * 
	 * @return the {@link ConversionType} supported, never null.
	 */
	public ConversionType<CurrencyUnit, CurrencyUnit> getConversionType();

	/**
	 * Get an exchange rate for the given parameters.
	 * 
	 * @param source
	 *            the source currency.
	 * @param target
	 *            the target currency.
	 * @param type
	 *            Allows to determine the kind of rate to returned. {@code null}
	 *            means any type.
	 * @param timestamp
	 *            the required target UTC timestamp for the rate, or
	 *            {@code null} for the latest available.
	 * @return the according exchange rate, or null.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit source, CurrencyUnit target, Long timestamp);

}
