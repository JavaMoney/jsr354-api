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
package net.java.javamoney.ri.core.spi;

import java.util.Locale;
import java.util.ServiceLoader;

import javax.money.CurrencyUnit;

/**
 * Implementation of this interface define the currencies supported in the
 * system. Each provider implementation hereby may be responsible for exactly
 * one name space. For multiple name spaces being supported several providers
 * must be registered.
 * <p>
 * Registration is done using the {@link ServiceLoader} features.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyUnitProviderSpi {

	/**
	 * The currency namespace provided by this instance.
	 * 
	 * @return the namespace this instance is responsible for, never null.
	 */
	public String getNamespace();

	/**
	 * This method is called by the specification, to evaluate/access a currency
	 * instance with the given names space.
	 * 
	 * @param code
	 *            The code of the currency required. This code with the name
	 *            space uniquely identify a currency instance.
	 * @param timestamp
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
	 * @return The currency unit to used, or null. Hereby the implementation
	 *         should return immutable and final instances of
	 *         {@link CurrencyUnit}. It is the responsibility of the
	 *         implementation to implement caching of currency instances
	 *         (recommended).
	 */
	public CurrencyUnit getCurrency(String code, Long timestamp);

	/**
	 * Get the available currencies for the given {@link Locale}.
	 * 
	 * @param locale
	 *            the target {@link Locale}
	 * @param timestamp
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
	 * @return the currencies found, or null.
	 */
	public CurrencyUnit[] getCurrencies(Locale locale, Long timestamp);

	/**
	 * Get the currencies available.
	 * 
	 * @param timestamp
	 *            The target UTC timestamp, or {@code null} for the current UTC
	 *            timestamp.
	 * @return the currencies found, or null.
	 */
	public CurrencyUnit[] getCurrencies(Long timestamp);

	/**
	 * Method that allows to check if a currency is available for a given time
	 * range.
	 * 
	 * @param code
	 *            the required code within this namespace, never null.
	 * @param start
	 *            the start UTC timestamp, or {@code null}
	 * @param end
	 *            the end UTC timestamp, or {@code null}
	 * @return true, if the code is defined.
	 */
	public boolean isAvailable(String code, Long start, Long end);

}
