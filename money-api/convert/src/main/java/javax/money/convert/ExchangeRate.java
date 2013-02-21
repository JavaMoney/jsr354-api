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

import javax.money.CurrencyUnit;

/**
 * This interface models a exchange rate between two currencies.
 * 
 * @author Werner Keil
 * @author Anatole Tresch
 * @version 0.2.2
 */
public interface ExchangeRate {

	/**
	 * Access the type of exchange rate.
	 * 
	 * @return the type of this rate, never null.
	 */
	public ExchangeRateType getExchangeRateType();

	/**
	 * Get the source currency.
	 * 
	 * @return the source currency.
	 */
	public CurrencyUnit getSource();

	/**
	 * Get the target currency.
	 * 
	 * @return the target currency.
	 */
	public CurrencyUnit getTarget();

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
	public Long getTimestamp();

	/**
	 * Get the data validity timestamp of this rate in milliseconds. This can be
	 * useful, when a rate in a system only should be used within some specified
	 * time.
	 * 
	 * @return the duration of validity in milliseconds, or {@code null} if no
	 *         validity constraints apply.
	 */
	public Long getValidUntil();

	/**
	 * Allows to check if a rate is still valid according to its data validity
	 * timestamp.
	 * 
	 * @see #getValidUntil()
	 * @return true, if the rate is valid for use.
	 */
	public boolean isValid();

	/**
	 * Get the location of this quote.
	 * 
	 * @return the stock exchange or location.
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
	 *         <code>new ConversionRate[]{this}</code>.
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
	public boolean isIdentity();


	/**
	 * Access additional attributes of this currency instance. This allows to
	 * add additional codes or extended information by SPI providers. For
	 * instance there are ISO currency codes existing that may represented by
	 * different country specific currencies. The detailed country can be added
	 * as an attribute here.
	 * 
	 * @param key
	 *            The attribute's key, never null.
	 * @return the according attribute value, or null.
	 */
	public <T> T getAttribute(String key, Class<T> type);

	/**
	 * Access the extended attributes defined.
	 * 
	 * @return the attribute key available, never null.
	 */
	public Enumeration<String> getAttributeKeys();

	/**
	 * Access the type of an attribute.
	 * 
	 * @param key
	 *            The attribute key
	 * @return the attribute's value class, or null.
	 */
	public Class<?> getAttributeType(String key);

	/**
	 * The method reverses the exchange rate to a rate mapping from target to
	 * source. Hereby the factor must be (by default) recalculated as
	 * {@code 1/oldFactor}.
	 * 
	 * @return the reversed exchange rate.
	 * @throws UnsupportedOperationException
	 *             if the rate is not reversible.
	 */
	public ExchangeRate reverse();
}
