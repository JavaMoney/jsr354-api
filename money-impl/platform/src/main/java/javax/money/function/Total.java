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
 */
package javax.money.function;

import java.util.Arrays;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * This class allows to calculate the total of some {@link MonetaryAmount}
 * instances.
 * 
 * @author Anatole Tresch
 */
public final class Total implements
		MonetaryFunction<Iterable<MonetaryAmount>, MonetaryAmount> {

	/**
	 * The shared instance of this class.
	 */
	private static final Total INSTANCE = new Total();

	/**
	 * Private constructor, there is only one instance of this class, accessible
	 * calling {@link #of()}.
	 */
	private Total() {
	}

	/**
	 * Access the shared instance of {@link Total} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public static Total of() {
		return INSTANCE;
	}

	/**
	 * Evaluates the total sum of the given amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the total sum.
	 */
	public static MonetaryAmount from(Iterable<MonetaryAmount> amounts) {
		return Total.of().apply(amounts);
	}

	/**
	 * Evaluates the total sum of the given amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the total sum.
	 */
	public static MonetaryAmount from(MonetaryAmount... amounts) {
		return Total.of().apply(Arrays.asList(amounts));
	}

	/**
	 * Evaluates the total sum of the given amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the total sum.
	 */
	public MonetaryAmount apply(Iterable<MonetaryAmount> amounts) {
		MonetaryAmount result = null;
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		for (MonetaryAmount amount : amounts) {
			if (result == null) {
				result = amount;
			} else {
				result = result.add(amount);
			}
		}
		if (result == null) {
			throw new IllegalArgumentException("amounts is empty.");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Total [Iterable<MonetaryAmount> -> MonetaryAmount]";
	}

}
