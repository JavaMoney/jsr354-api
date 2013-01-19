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
import javax.money.util.AttributeProvider;

/**
 * This interface models a exchange rate between two currencies.
 * 
 * @author Werner Keil
 * @author Anatole Tresch
 * @version 0.2.2
 */
public interface ExchangeRate extends AttributeProvider, Comparable<ExchangeRate> {

	/**
	 * Get the source currency.
	 * 
	 * @return the source currency.
	 */
	public CurrencyUnit getSourceCurrency();

	/**
	 * Get the target currency.
	 * 
	 * @return the target currency.
	 */
	public CurrencyUnit getTargetCurrency();

	/**
	 * Get the exchange factor converted to the given target type. This allows
	 * to use different exchange rate implementations.
	 * 
	 * @param targetClass
	 *            The target type required.
	 * @return the exchange factor, as instance of T.
	 */
	public <T> T getFactorAsType(Class<T> targetClass);

	/**
	 * Access the rate's factor.
	 * 
	 * @return the factor for this exchange rate.
	 */
	public Number getFactor();

	/**
	 * Returns the UTC timestamp of the rate.
	 * 
	 * @return The UTC timestamp of the rate.
	 */
	public long getTimestamp();

	/**
	 * Get the location of this quote. TODO model this as an object?
	 * 
	 * @return the stock exchange name, or location.
	 */
	public String getLocation();

	/**
	 * Get the name of the data provider, that provided this rate.
	 * 
	 * @return the name of the data provider.
	 */
	public String getDataProvider();

	/**
	 * Access the chain of exchange rates.
	 * 
	 * @return the chain of rates, in case of a derived rate, this may be
	 *         several instances. For a direct exchange rate, this equals to
	 *         <code>new ExchangeRate[]{this}</code>.
	 */
	public ExchangeRate[] getExchangeRateChain();

	/**
	 * Allows to evaluate if this exchange rate is a derived exchange rate.
	 * Derived exchange rates are defined by an ordered list of subconversions
	 * with intermediate steps, whereas a direct conversion is possible in one
	 * steps.
	 * 
	 * @return true, if the exchange rate is derived.
	 */
	public boolean isDerived();

	/**
	 * Access the type of exchange rate.
	 * 
	 * @return the type of this rate, never null.
	 */
	public ExchangeRateType getExchangeRateType();

}
