/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
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
 * Contributors:
 *    Anatole Tresch - initial implementation
 *    Wernner Keil - extensions and adaptions.
 */
package javax.money;

public final class MonetaryAmounts {

	/**
	 * Singleton constructor.
	 */
	private MonetaryAmounts() {
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param number
	 *            The required numeric value, not null.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, Number number) {
		// TODO implement this
		return null;
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, byte value) {
		// TODO implement this
		return null;
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, short value) {
		// TODO implement this
		return null;
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, int value) {
		// TODO implement this
		return null;
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, long value) {
		// TODO implement this
		return null;
	}

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
	public MonetaryAmount get(CurrencyUnit currency, long major, long minor) {
		// TODO implement this
		return null;
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, float value) {
		// TODO implement this
		return null;
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, double value) {
		// TODO implement this
		return null;
	}

	/**
	 * Obtains an instance of {@code Money} representing zero.
	 * <p>
	 * For example, {@code zero(USD)} creates the instance {@code USD 0.00}.
	 * 
	 * @param currency
	 *            the currency, not null
	 * @return the instance representing zero, never null
	 */
	public MonetaryAmount zero(CurrencyUnit currency) {
		// TODO implement this
		return null;
	}

	/**
	 * Obtains an instance of {@code Money} as the total value an array.
	 * <p>
	 * The array must contain at least one monetary value. Subsequent amounts
	 * are added as though using {@link #plus(Money)}. All amounts must be in
	 * the same currency.
	 * 
	 * @param monies
	 *            the monetary values to total, not empty, no null elements, not
	 *            null
	 * @return the total, never null
	 * @throws IllegalArgumentException
	 *             if the array is empty
	 * @throws CurrencyMismatchException
	 *             if the currencies differ
	 */
	public MonetaryAmount total(MonetaryAmount... monies) {
		// TODO implement this
		return null;
	}

	/**
	 * Obtains an instance of {@code Money} as the total value a collection.
	 * <p>
	 * The iterable must provide at least one monetary value. Subsequent amounts
	 * are added as though using {@link #plus(Money)}. All amounts must be in
	 * the same currency.
	 * 
	 * @param monies
	 *            the monetary values to total, not empty, no null elements, not
	 *            null
	 * @return the total, never null
	 * @throws IllegalArgumentException
	 *             if the iterable is empty
	 * @throws CurrencyMismatchException
	 *             if the currencies differ
	 */
	public MonetaryAmount total(Iterable<MonetaryAmount> monies) {
		// TODO implement this
		return null;
	}

	/**
	 * Obtains an instance of {@code Money} as the total value a possibly empty
	 * array.
	 * <p>
	 * The amounts are added as though using {@link #plus(Money)} starting from
	 * zero in the specified currency. All amounts must be in the same currency.
	 * 
	 * @param currency
	 *            the currency to total in, not null
	 * @param monies
	 *            the monetary values to total, no null elements, not null
	 * @return the total, never null
	 * @throws CurrencyMismatchException
	 *             if the currencies differ
	 */
	public MonetaryAmount total(CurrencyUnit currency, MonetaryAmount... monies) {
		// TODO implement this
		return null;
	}

	/**
	 * Obtains an instance of {@code Money} as the total value a possibly empty
	 * collection.
	 * <p>
	 * The amounts are added as though using {@link #plus(Money)} starting from
	 * zero in the specified currency. All amounts must be in the same currency.
	 * 
	 * @param currency
	 *            the currency to total in, not null
	 * @param monies
	 *            the monetary values to total, no null elements, not null
	 * @return the total, never null
	 * @throws CurrencyMismatchException
	 *             if the currencies differ
	 */
	public MonetaryAmount total(CurrencyUnit currency,
			Iterable<MonetaryAmount> monies) {
		// TODO implement this
		return null;
	}

	/**
	 * Ensures that an {@code Amount} is not {@code null}.
	 * <p>
	 * If the input money is not {@code null}, then it is returned, providing
	 * that the currency matches the specified currency. If the input amount is
	 * {@code null}, then a zero amount in the currency is returned.
	 * 
	 * @param amonut
	 *            the amount to check, may be null
	 * @param currency
	 *            the currency to use, not null
	 * @return the input amount or zero in the specified currency, never null
	 * @throws CurrencyMismatchException
	 *             if the input money is non-null and the currencies differ
	 */
	public MonetaryAmount nonNull(MonetaryAmount amount, CurrencyUnit currency) {
		// TODO implement this
		return null;
	}

	/**
	 * Access a {@link Rounding} using its identifier.
	 * 
	 * @param id
	 *            The id that identifies the rounding.
	 * @return The currency found, never null.
	 * @throws IllegalArgumentException
	 *             if the required rounding is not defined.
	 */
	public Rounding getRounding(String id) {
		// TODO implement this
		return null;
	}

	/**
	 * Checks if a rounding is defined using its identifier.
	 * 
	 * @param id
	 *            The rounding id, e.g. 'HALF-UP'.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @return true, if the currency is defined.
	 */
	public boolean isRoundingDefined(String id) {
		// TODO implement this
		return false;
	}

	/**
	 * Access the {@link Rounding} for a given {@link CurrencyUnit}.
	 * 
	 * @param currency
	 *            the currency instance. not null.
	 * @return the {@link Rounding}. If no explicit {@link Rounding} is defined,
	 *         it should be created/registered based on
	 *         {@link CurrencyUnit#getDefaultFractionDigits()}.
	 */
	public Rounding getRounding(CurrencyUnit currency) {
		// TODO implement this
		return null;
	}

	/**
	 * Access the {@link Rounding} for a given {@link CurrencyUnit} and
	 * timestamp.
	 * 
	 * @param currency
	 *            the currency instance. not null.
	 * @param timestamp
	 *            the target timestamp for the {@link Rounding}, or -1 for the
	 *            current UTC time.
	 * @return the {@link Rounding}. If no explicit {@link Rounding} is defined,
	 *         it should be created/registered based on
	 *         {@link CurrencyUnit#getDefaultFractionDigits()}.
	 */
	public Rounding getRounding(CurrencyUnit currency, long timestamp) {
		// TODO implement this
		return null;
	}

	/**
	 * Allow to access all rounding identifiers currently available.
	 * {@link Rounding} can be accessed by calling {@link #getRounding(String)}.
	 * 
	 * @return the identifiers of all currently defined roundings.
	 */
	public String[] getRoundingIds() {
		// TODO implement this
		return null;
	}

	// TODO Should we manage general AmountAdjusters for access and caching ?
}
