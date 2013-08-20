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
import java.util.List;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * This class allows instances of {@link MonetaryAmount} to be filtered by
 * arbitrary predicates.
 * 
 * @author Anatole Tresch
 */
public final class AmountFilter
		implements
		MonetaryFunction<MonetaryFunction<MonetaryAmount, Boolean>, Collection<MonetaryAmount>> {

	/** The input {@link MonetaryAmount} instances to be filtered. */
	private Collection<MonetaryAmount> input = new ArrayList<MonetaryAmount>();

	/**
	 * Creates a new empty {@link AmountFilter}.
	 */
	public AmountFilter() {
	}

	/**
	 * Creates a new {@link AmountFilter}, using the given amounts.
	 * 
	 * @param amounts
	 *            the amounts to be added as input, not null.
	 */
	public AmountFilter(MonetaryAmount... amounts) {
		this();
		add(Arrays.asList(amounts));
	}

	/**
	 * Creates a new instance, using the given amounts.
	 * 
	 * @param amounts
	 *            the amounts to be added as input, not null.
	 */
	public AmountFilter(Iterable<MonetaryAmount> amounts) {
		this();
		add(amounts);
	}

	/**
	 * Allows to add instances of {@link MonetaryAmount} to the input list.
	 * 
	 * @param amounts
	 *            the amounts to be added.
	 * @return this filter instance, for chaining.
	 */
	public AmountFilter add(MonetaryAmount... amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		for (MonetaryAmount monetaryAmount : amounts) {
			this.input.add(monetaryAmount);
		}
		return this;
	}

	/**
	 * Allows to add instances of {@link MonetaryAmount} to the input list.
	 * 
	 * @param amounts
	 *            the amounts to be added.
	 * @return this filter instance, for chaining.
	 */
	public AmountFilter add(Iterable<MonetaryAmount> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		for (MonetaryAmount monetaryAmount : amounts) {
			this.input.add(monetaryAmount);
		}
		return this;
	}

	/**
	 * Visits all amount in the input collection and selects the ones matching
	 * the predicate passed.
	 * 
	 * @param The
	 *            predicate determining what amounts are filtered into the
	 *            result.
	 * @return a collection with all the amounts, for which the predicate
	 *         returned {@code true}.
	 */
	public Collection<MonetaryAmount> apply(
			final MonetaryFunction<MonetaryAmount, Boolean> predicate) {
		final List<MonetaryAmount> result = new ArrayList<MonetaryAmount>();
		AmountVisitor visitor = new AmountVisitor(this.input);
		visitor.apply(new MonetaryFunction<MonetaryAmount, Boolean>() {
			@Override
			public Boolean apply(MonetaryAmount value) {
				if (predicate.apply(value)) {
					result.add(value);
					return true;
				}
				return false;
			}
		});
		return result;
	}

}
