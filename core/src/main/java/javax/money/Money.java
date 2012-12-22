/*
 *  Copyright 2012 Credit Suisse (Anatole Tresch)
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
 */
package javax.money;


/**
 * Access point for money related core functionality.
 * 
 * @author Anatole Tresch
 */
public final class Money {

	/**
	 * Singleton accessor.
	 */
	private Money() {
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
     * @param currency  the currency, not null
     * @return the instance representing zero, never null
     */
    public static Money zero(CurrencyUnit currency) {
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
	 * Ensures that a {@code Money} is not {@code null}.
	 * <p>
	 * If the input money is not {@code null}, then it is returned, providing
	 * that the currency matches the specified currency. If the input money is
	 * {@code null}, then zero money in the currency is returned.
	 * 
	 * @param money
	 *            the monetary value to check, may be null
	 * @param currency
	 *            the currency to use, not null
	 * @return the input money or zero in the specified currency, never null
	 * @throws CurrencyMismatchException
	 *             if the input money is non-null and the currencies differ
	 */
	public static Amount nonNull(Amount money, CurrencyUnit currency) {
		// TODO Not Implemented yet
		return null;
	}
}
