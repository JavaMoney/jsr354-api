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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * This {@link MonetaryFunction} allows to separate instances of
 * {@link MonetaryAmount} by their {@link CurrencyUnit}.
 * 
 * @author Anatole Tresch
 */
public final class SeparateAmounts
		implements
		MonetaryFunction<Iterable<MonetaryAmount>, Map<CurrencyUnit, Collection<MonetaryAmount>>> {

	/**
	 * The shared instance of this class.
	 */
	private static final SeparateAmounts INSTANCE = new SeparateAmounts();

	/**
	 * Access the shared instance of {@link SeparateAmount} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public static SeparateAmounts of() {
		return INSTANCE;
	}

	/**
	 * Private constructor, there is only one instance of this class, accessible
	 * calling {@link #of()}.
	 */
	private SeparateAmounts() {
	}

	/**
	 * Separates the given {@link MonetaryAmount} instances according to their
	 * {@link CurrencyUnit}.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the separated amounts, with {@link CurrencyUnit} as {@link Map}
	 *         key.
	 */
	public static Map<CurrencyUnit, Collection<MonetaryAmount>> from(
			Iterable<MonetaryAmount> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		return SeparateAmounts.of().apply(amounts);
	}

	/**
	 * Separates the given {@link MonetaryAmount} instances according to their
	 * {@link CurrencyUnit}.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the separated amounts, with {@link CurrencyUnit} as {@link Map}
	 *         key.
	 */
	public static Map<CurrencyUnit, Collection<MonetaryAmount>> from(
			MonetaryAmount... amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		return SeparateAmounts.of().apply(Arrays.asList(amounts));
	}

	/**
	 * Separates the given {@link MonetaryAmount} instances according to their
	 * {@link CurrencyUnit}.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the separated amounts, with {@link CurrencyUnit} as {@link Map}
	 *         key.
	 */
	public Map<CurrencyUnit, Collection<MonetaryAmount>> apply(
			Iterable<MonetaryAmount> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		/** the result with all amounts separated by currency. */
		Map<CurrencyUnit, Collection<MonetaryAmount>> result = new HashMap<CurrencyUnit, Collection<MonetaryAmount>>();
		for (MonetaryAmount monetaryAmount : amounts) {
			CurrencyUnit cu = monetaryAmount.getCurrency();
			Collection<MonetaryAmount> target = result.get(cu);
			if (target == null) {
				target = new ArrayList<MonetaryAmount>();
				result.put(cu, target);
			}
			target.add(monetaryAmount);
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
		return "SeparateAmounts [Iterable<MonetaryAmount> -> Map<CurrencyUnit, Collection<MonetaryAmount>>]";
	}

}
