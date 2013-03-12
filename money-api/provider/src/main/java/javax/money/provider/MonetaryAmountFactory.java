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
package javax.money.provider;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

/**
 * Component for accessing {@link MonetaryAmount} instances. It is provided by
 * the {@link MonetaryAmountProvider} instance.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryAmountFactory {

	/**
	 * This method defines the number class used by this factory instances. The
	 * Monetary singleton allows to use monetary amounts using different numeric
	 * representations.
	 * 
	 * @return The number representation class used by this factory, never null.
	 */
	public Class<?> getNumberClass();

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param number
	 *            The required numeric value, not null.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, Number number);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, byte value);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, short value);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, int value);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, long value);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param major
	 *            The required major decimal number part.
	 * @param minor
	 *            The required minor decimal number part, not negative.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, long major, long minor);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, float value);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, double value);

	/**
	 * Obtains an instance of {@code Money} representing zero.
	 * <p>
	 * For example, {@code zero(USD)} creates the instance {@code USD 0.00}.
	 * 
	 * @param currency
	 *            the currency, not null
	 * @return the instance representing zero, never null
	 */
	public MonetaryAmount zero(CurrencyUnit currency);

}
