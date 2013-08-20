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
 * This class allows to calculate the average of some {@link MonetaryAmount}
 * instances, all of the same currency.
 * 
 * @author Anatole Tresch
 */
public final class Average implements
		MonetaryFunction<Iterable<? extends MonetaryAmount>, MonetaryAmount> {

	/**
	 * The shared instance of this class.
	 */
	private static final Average INSTANCE = new Average();

	/**
	 * Private constructor, there is only one instance of this class, accessible
	 * calling {@link #of()}.
	 */
	private Average() {
	}

	/**
	 * Access the shared instance of {@link Average} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public static final Average of() {
		return INSTANCE;
	}

	/**
	 * Evaluates the average of the given amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the average.
	 */
	public static MonetaryAmount from(Iterable<? extends MonetaryAmount> amounts) {
		return Average.of().apply(amounts);
	}

	/**
	 * Evaluates the average of the given amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the average.
	 */
	public static MonetaryAmount from(MonetaryAmount... amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		return Average.of().apply(Arrays.asList(amounts));
	}

	/**
	 * Evaluates the average of the given amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the average.
	 */
	public MonetaryAmount apply(Iterable<? extends MonetaryAmount> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		MonetaryAmount total = null;
		int itemNumber = 0;

		for (MonetaryAmount amount : amounts) {
			itemNumber++;
			if (total == null) {
				total = amount;
			} else {
				total = total.add(amount);
			}
		}
		if (total == null) {
			throw new IllegalArgumentException("No amounts to totalize.");
		}
		return total.divide(itemNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Average [Iterable<MonetaryAmount> -> MonetaryAmount]";
	}

}
