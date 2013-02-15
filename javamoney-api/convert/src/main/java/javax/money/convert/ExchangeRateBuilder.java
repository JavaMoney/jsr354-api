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
 * This interface defines a builder to create {@link ConversionRate} instances.
 * 
 * @author Anatole Tresch
 */
public interface ExchangeRateBuilder {

	/**
	 * Access an attribute.
	 * 
	 * @param key
	 *            The attribute's key
	 * @param type
	 *            The attribtue's type
	 * @return The attribute's value, or {@code null}
	 */
	public <T> T getAttribute(String key, Class<T> type);

	/**
	 * Sets an attribute.
	 * 
	 * @param key
	 *            The attribute's key, not {@code null}.
	 * @param value
	 *            The attribtue's value, not {@code null}.
	 * @return The builder instance, never {@code null}.
	 */
	public ExchangeRateBuilder setAttribute(String key, Object value);

	/**
	 * Removes all attributes currently defined.
	 */
	public void clearAttributes();

	/**
	 * Get the exchange rate types that this provider instance is providing data
	 * for.
	 * 
	 * @return the {@link ConversionType} instances available.
	 */
	public ExchangeRateBuilder setConversionType(
			ConversionType<CurrencyUnit, CurrencyUnit> type);

	/**
	 * Access the {@link ConversionType} of the rate to be created.
	 * 
	 * @return
	 */
	public ConversionType<CurrencyUnit, CurrencyUnit> getConversionType();

	/**
	 * Set the source item.
	 * 
	 * @param currency
	 *            the source item
	 * @return the builder instance
	 */
	public ExchangeRateBuilder setSource(CurrencyUnit currency);

	/**
	 * Sets the rate's timestamp.
	 * 
	 * @param timestamp
	 *            The timestamp.
	 * @return This builder instance.
	 */
	public ExchangeRateBuilder setTimestamp(Long timestamp);

	/**
	 * Get the timestamp used for the {@link ConversionRate} created.
	 * 
	 * @return
	 */
	public Long getTimestamp();

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
	public ExchangeRateBuilder setExchangeRateChain(
			ExchangeRate... exchangeRates);

	/**
	 * Get the exchange rate chain, for a chained rate.
	 * 
	 * @return the rate chain, may also be an empty array, but never
	 *         {@code null}.
	 */
	public ExchangeRate[] getExchangeRateChain();

	/**
	 * Defines the exchange factor with the target currency as leading instance.
	 * So the {@code number} passed defines the factor between target and source
	 * currencies. The builder will map this factor, so it is appropriate for
	 * mapping the source currency to the target currency.
	 * 
	 * @param number
	 *            The factor, never null.
	 * @return the builder instance.
	 */
	public ExchangeRateBuilder setSourceLeadingFactor(Number factor);

	/**
	 * Defines the exchange factor with the target currency as leading instance.
	 * So the {@code number} passed defines the factor between target and source
	 * currencies. The builder will map this factor, so it is appropriate for
	 * mapping the source currency to the target currency.
	 * 
	 * @param number
	 *            The factor, never null.
	 * @return the builder instance.
	 */
	public ExchangeRateBuilder setTargetLeadingFactor(Number factor);

	/**
	 * Set the location.
	 * 
	 * @param location
	 *            the location
	 * @return the builder instance
	 */
	public ExchangeRateBuilder setLocation(String location);

	/**
	 * Get the location of the rate to be built.
	 * 
	 * @return the location, or null.
	 */
	public String getLocation();

	/**
	 * Set the data provider.
	 * 
	 * @param dataProvider
	 *            the data provider
	 * @return the builder instance
	 */
	public ExchangeRateBuilder setDataProvider(String dataProvider);

	/**
	 * Builds a new instance of {@link ConversionRate} using the attributes
	 * defines within this builder instance. The method {@link #isBuildeable()}
	 * allows to check if a rate can be build.
	 * 
	 * @see #isBuildeable()
	 * @return the created {@link ConversionRate}, never null.
	 * @throws IllegalStateException
	 *             if the builder can not create a valid {@link ConversionRate}
	 *             instance, e.g. beacause required properties are not defined.
	 */
	public ExchangeRate build();

	/**
	 * Get the source item of the rate to be built.
	 * 
	 * @return the source item
	 */
	public CurrencyUnit getSource();

	/**
	 * Set the target item.
	 * 
	 * @param currency
	 *            the target item.
	 * @return the builder instance
	 */
	public ExchangeRateBuilder setTarget(CurrencyUnit currency);

	/**
	 * Get the target item of the rate to be built.
	 * 
	 * @return the target item.
	 */
	public CurrencyUnit getTarget();

	/**
	 * Access the factor for the rate to be bhuild.
	 * 
	 * @return the factor used for building, or null, if not defined.
	 */
	public Number getFactor();

	/**
	 * Get the data provider of the rate to be built.
	 * 
	 * @return the data provider, or null.
	 */
	public String getDataProvider();

	/**
	 * This method allows to query if an {@link ConversionRate} can be build
	 * using this builder.
	 * 
	 * @return true, if the builder is able to build an {@link ConversionRate}.
	 */
	public boolean isBuildeable();

}
