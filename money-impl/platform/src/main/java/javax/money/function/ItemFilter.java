/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package javax.money.function;

import java.util.ArrayList;
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
final class ItemFilter<T>
		implements
		MonetaryFunction<Predicate<T>, Collection<T>> {

	/** The input {@link MonetaryAmount} instances to be filtered. */
	private Collection<T> input = new ArrayList<T>();

	/**
	 * Creates a new empty {@link ItemFilter}.
	 */
	public ItemFilter(Iterable<? extends T>... values) {
		for (Iterable<? extends T> iterable : values) {
			for (T t : iterable) {
				input.add(t);
			}
		}
	}

	/**
	 * Creates a new {@link ItemFilter}, using the given amounts.
	 * 
	 * @param amounts
	 *            the amounts to be added as input, not null.
	 */
	public ItemFilter(T... values) {
		for (T t : values) {
			input.add(t);
		}
	}

	/**
	 * Allows to add instances of {@link MonetaryAmount} to the input list.
	 * 
	 * @param amounts
	 *            the amounts to be added.
	 * @return this filter instance, for chaining.
	 */
	public ItemFilter<T> add(T... items) {
		if (items == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		for (T item : items) {
			this.input.add(item);
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
	public ItemFilter<T> add(Iterable<T> items) {
		if (items == null) {
			throw new IllegalArgumentException("items required.");
		}
		for (T item : items) {
			this.input.add(item);
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
	public Collection<T> apply(
			final Predicate<T> predicate) {
		final List<T> result = new ArrayList<T>();
		ItemVisitor visitor = new ItemVisitor(this.input);
		visitor.apply(new Predicate<T>() {
			@Override
			public Boolean apply(T value) {
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
