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
package javax.money.function.predicates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;
import javax.money.Predicate;

/**
 * This class allows instances of a type T to be visited with
 * selection of arbitrary predicates.
 * 
 * @author Anatole Tresch
 */
final class ItemVisitor<T> implements
		MonetaryFunction<Predicate<T>, Integer> {
	/** The input instances to be filtered. */
	private Collection<T> input = new ArrayList<T>();

	/**
	 * Creates a new, empty {@link ItemVisitor}.
	 */
	public ItemVisitor() {

	}

	/**
	 * Creates a new {@link ItemVisitor}, using the given amounts.
	 * 
	 * @param amounts
	 *            the amounts to be visited, not null.
	 */
	public ItemVisitor(T... amounts) {
		this();
		add(Arrays.asList(amounts));
	}

	/**
	 * Creates a new {@link SeparateCurrencies}, using the given amounts.
	 * 
	 * @param amounts
	 *            the amounts to be separated, not null.
	 * @return the new instance.
	 */
	public ItemVisitor(Iterable<T>... amounts) {
		this();
		for (Iterable<T> iterable : amounts) {
			add(iterable);
		}
	}

	/**
	 * Adds the given amounts for separation.
	 * 
	 * @param amounts
	 *            The amounts.
	 * @return this instance for building.
	 */
	public ItemVisitor<T> add(T... items) {
		if (items == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		for (T monetaryAmount : items) {
			this.input.add(monetaryAmount);
		}
		return this;
	}

	/**
	 * Method that allows to add amounts to the visitor, that can be visited
	 * later.
	 * 
	 * @param amounts
	 *            The amount to be added.
	 * @return This visitor instance, for chaining.
	 */
	public ItemVisitor<T> add(Iterable<T> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		for (T monetaryAmount : amounts) {
			this.input.add(monetaryAmount);
		}
		return this;
	}

	/**
	 * This method visits all items in the current {@link ItemVisitor}. The
	 * passed {@code predicate} signals by its return value, if it has handled
	 * the current {@link MonetaryAmount} by returning {@code true}.
	 * 
	 * @return the sum of calls to the given predicate that evaluated to
	 *         {@code true}.
	 */
	public Integer apply(Predicate<T> predicate) {
		int visited = 0;
		for (T amt : this.input) {
			if (predicate.apply(amt)) {
				visited++;
			}
		}
		return visited;
	}

}
