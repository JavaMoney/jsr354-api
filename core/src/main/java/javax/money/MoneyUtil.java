/*
 * Copyright (c) 2012-2013,  Credit Suisse (Anatole Tresch), Werner Keil
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

import java.math.RoundingMode;

/**
 * Access point for money related core functionality.
 * 
 * @author Anatole Tresch
 */
public final class MoneyUtil {

	/**
	 * Singleton accessor.
	 */
	private MoneyUtil() {
	}

	/**
	 * Access a currency using its name space and code.
	 * 
	 * @param namespace
	 *            The name space, e.g. 'ISO-4217'.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @return The currency found, never null.
	 * @throws UnknownCurrencyException
	 *             if the required currency is not defined.
	 */
	public static CurrencyUnit getCurrency(String namespace, String code) {
		return null;
	}

	/**
	 * Checks if a currency is defined using its name space and code.
	 * 
	 * @param namespace
	 *            The name space, e.g. 'ISO-4217'.
	 * @param code
	 *            The code that, together with the namespace identifies the
	 *            currency.
	 * @return true, if the currency is defined.
	 */
	public static CurrencyUnit isCurrencyDefined(String namespace, String code) {
		return null;
	}

	/**
	 * This method allows to evaluate, if the given currency name space is
	 * defined. "ISO-4217" should be defined in all environments (default).
	 * 
	 * @param namespace
	 *            the required name space
	 * @return true, if the name space exists.
	 */
	public static boolean isCurrencyNamespaceDefined(String namespace) {
		// TODO Use SPIs to evaluate
		return "ISO-4217".equals(namespace);
	}

	/**
	 * This method allows to access all name spaces currently defined.
	 * "ISO-4217" should be defined in all environments (default).
	 * 
	 * @return the array of currently defined name space.
	 */
	public static String[] getCurrencyNamespaces() {
		// TODO Use SPIs to evaluate
		return new String[] { "ISO-4217" };
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
	public static Amount get(CurrencyUnit currency, Number number) {
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
	public static Amount get(CurrencyUnit currency, byte value) {
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
	public static Amount get(CurrencyUnit currency, short value) {
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
	public static Amount get(CurrencyUnit currency, int value) {
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
	public static Amount get(CurrencyUnit currency, long value) {
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
	public static Amount get(CurrencyUnit currency, long major, long minor) {
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
	public static Amount get(CurrencyUnit currency, float value) {
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
	public static Amount get(CurrencyUnit currency, double value) {
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
	public static Amount zero(CurrencyUnit currency) {
		// TODO Not Implemented yet
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
	public static Amount total(Amount... monies) {
		// TODO Not Implemented yet
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
	public static Amount total(Iterable<Amount> monies) {
		// TODO Not Implemented yet
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
	public static Amount total(CurrencyUnit currency, Amount... monies) {
		// TODO Not Implemented yet
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
	public static Amount total(CurrencyUnit currency, Iterable<Amount> monies) {
		// TODO Not Implemented yet
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
	public static Amount nonNull(Amount amount, CurrencyUnit currency) {
		// TODO Not Implemented yet
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
	public static Rounding getRounding(String id) {
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
	public static boolean isRoundingDefined(String id) {
		return false;
	}

	/**
	 * Allow to access all rounding identifiers currently available.
	 * {@link Rounding} can be accessed by calling {@link #getRounding(String)}.
	 * 
	 * @return the identifiers of all currently defined roundings.
	 */
	public static String[] getRoundingIds() {
		// TODO implement this using registered rounding modes.
		RoundingMode[] roundingItems = RoundingMode.values();
		String[] roundings = new String[roundingItems.length];
		for (int i = 0; i < roundings.length; i++) {
			roundings[i] = roundingItems[i].toString();
		}
		return roundings;
	}
}
