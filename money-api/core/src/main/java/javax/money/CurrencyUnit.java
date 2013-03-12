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
package javax.money;

import java.util.Enumeration;

/**
 * * A unit of currency.
 * <p>
 * * This class represents a unit of currency such as the British Pound, Euro,
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
public interface CurrencyUnit {

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
	 * <p>
	 * This method matches the API of <type>java.util.Currency</type>.
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
	 * <p>
	 * This method matches the API of <type>java.util.Currency</type>.
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
	 * This method matches the API of <type>java.util.Currency</type>.
	 * 
	 * @return the fractional digits, from 0 to 9 (normally 0, 2 or 3), or -1
	 *         for pseudo-currencies.
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
	 * Checks if this is a currency that has a legal tender.
	 * 
	 * @return true if this currency has a legal tender.
	 */
	public boolean isLegalTender();

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
