/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * Contributors:
 *    Anatole Tresch - initial version.
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
	 * Returns the UTC timestamp defining from what date/time this rate is
	 * valid.
	 * 
	 * @return The UTC timestamp of the rate, defining valid from, or
	 *         {@code null}.
	 */
	public Long getValidFrom();

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
	 * Get the provider of this rate. The provider of a rate can have different
	 * contexts in different usage scenarios, such as the service type or the
	 * stock exchange.
	 * 
	 * @return the provider, or {code null}.
	 */
	public String getProvider();

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

}
