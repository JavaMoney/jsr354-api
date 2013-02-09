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
package javax.money;

import java.util.ServiceLoader;

import javax.money.util.AttributeProvider;

/**
 * * A unit of currency.
 * <p>
 * * This class represents a unit of currency such as the British Pound, Euro or
 * US Dollar, BitCoins or other.
 * <p>
 * * The set of loaded currencies is provided by an instances of
 * {@link CurrencyUnitProvider}. The providers used are registered using the
 * {@link ServiceLoader} feature.
 * 
 * @author Werner Keil
 * @author Stephen Colebourne
 * @author Anatole Tresch
 */
public interface CurrencyUnit extends AttributeProvider {

	/**
	 * The predefined namespace for ISO 4217 currencies.
	 */
	public static final String ISO_NAMESPACE = "ISO-4217";

	/**
	 * Defines the name space for the currency code. If the CurrencyUnit is an
	 * instance of <type>java.util.Currency</type> this method returns
	 * 'ISO-4217', whereas for other currency schemes, e.g. virtual currencies
	 * or internal legacy currencies different values are possible.
	 * 
	 * @return the name space of the currency, never null.
	 */
	public String getNamespace();

	/**
	 * Gets the currency code, the effective code depends on the currency and
	 * the name space. It is possible that the two currency may have the same
	 * code, but different name spaces.
	 * <p>
	 * Each currency is uniquely identified within its name space by this code.
	 * 
	 * @return the currency code. Instances of <type>java.util.Currency</type>
	 *         return the three letter ISO-4217 or equivalent currency code,
	 *         never null.
	 */
	public String getCurrencyCode();

	/**
	 * Gets a numeric currency code. within the ISO-4217 name space, this equals
	 * to the ISO numeric code. In other currency name spaces this number may be
	 * different, or even undefined (-1).
	 * <p>
	 * The numeric code is an optional alternative to the standard currency
	 * code.
	 * 
	 * @return the numeric currency code
	 */
	public int getNumericCode();

	/**
	 * Gets the number of fractional digits typically used by this currency.
	 * <p>
	 * Different currencies have different numbers of fractional digits by
	 * default. * For example, 'GBP' has 2 fractional digits, but 'JPY' has
	 * zero. * Pseudo-currencies are indicated by -1. *
	 * <p>
	 * This method matches the API of <type>java.util.Currency</type> The
	 * alternative method {@link #getDecimalPlaces()} may be more useful.
	 * 
	 * @return the fractional digits, from 0 to 9 (normally 0, 2 or 3), or -1
	 *         for pseudo-currencies
	 * 
	 */
	public int getDefaultFractionDigits();

	/**
	 * Gets the increment used for rounding, along with
	 * {@link #getDefaultFractionDigits()} rounding is defined.
	 * 
	 * @return
	 * 
	 *         public double getRoundingIncrement();
	 */

	/**
	 * Checks if this is a virtual currency, such as BitCoins or Linden Dollars.
	 * 
	 * @return true if this is a virtual currency.
	 */
	public boolean isVirtual();

	/**
	 * Get the timestamp from when this currency instance is valid from.<br/>
	 * This is useful for historic currencies.
	 * 
	 * @return the UTC timestamp from where this instance is valid. If not
	 *         defined {@code null} should be returned.
	 */
	public Long getValidFrom();

	/**
	 * Get the timestamp until when this currency instance is valid from.<br/>
	 * This is useful for historic currencies.
	 * 
	 * @return the UTC timestamp until when this instance is valid. If not
	 *         defined {@code null} should be returned.
	 */
	public Long getValidUntil();

}
