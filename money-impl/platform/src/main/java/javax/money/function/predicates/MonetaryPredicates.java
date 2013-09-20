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

import java.util.Arrays;
import java.util.Collection;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

/**
 * This class provides access to several predicate based functionalities that
 * are useful for monetary manipulations. Hereby this class provides
 * functionalities to create and combine predicates as, well as predicate based
 * filtering and visiting of {@link Iterable} instances.
 * 
 * @author Anatole Tresch
 */
public final class MonetaryPredicates {

	/**
	 * Singleton constructor.
	 */
	private MonetaryPredicates() {
		// singleton constructor
	}

	/**
	 * This method provides a predicate that is constructed by all predicates
	 * passed, where every predicate must evaluate to {@code true}.
	 * 
	 * @param predicates
	 *            The predicates to evaluate.
	 * @return a {@link Predicate} evaluating to true, if every predicate return
	 *         true, or no predicates are passed, never {@code null}.
	 */
	@SafeVarargs
	public static <T> Predicate<T> and(
			Iterable<? extends Predicate<? super T>>... predicates) {
		return new AndPredicate<>(predicates);
	}

	/**
	 * This method provides a predicate that is constructed by all predicates
	 * passed, where every predicate must evaluate to {@code true}.
	 * 
	 * @param predicates
	 *            The predicates to evaluate.
	 * @return a {@link Predicate} evaluating to {@code true}, if every
	 *         predicate return {@code true}, or no predicates are passed, never
	 *         {@code null}.
	 */
	@SafeVarargs
	public static <T> Predicate<T> and(
			Predicate<? super T>... predicates) {
		return new AndPredicate<>(Arrays.asList(predicates));
	}

	/**
	 * This method provides a predicate that is constructed by all predicates
	 * passed, where at least one predicate must evaluate to {@code true}.
	 * 
	 * @param predicates
	 *            The predicates to evaluate.
	 * @return a {@link Predicate} evaluating to {@code true}, if at least one
	 *         predicate return {@code true}, or no predicates are passed, never
	 *         {@code null}.
	 */
	@SafeVarargs
	public static <T> Predicate<T> or(
			Iterable<? extends Predicate<? super T>>... predicates) {
		return new OrPredicate<>(predicates);
	}

	/**
	 * This method provides a predicate that is constructed by all predicates
	 * passed, where at least one predicate must evaluate to {@code true}.
	 * 
	 * @param predicates
	 *            The predicates to evaluate.
	 * @return a {@link Predicate} evaluating to {@code true}, if at least one
	 *         predicate return {@code true}, or no predicates are passed, never
	 *         {@code null}.
	 */
	@SafeVarargs
	public static <T> Predicate<T> or(
			Predicate<? super T>... predicates) {
		return new OrPredicate<>(Arrays.asList(predicates));
	}

	/**
	 * This method provides a predicate that is constructed by all predicates
	 * passed, where at exact one predicate must evaluate to {@code true}.
	 * 
	 * @param predicates
	 *            The predicates to evaluate.
	 * @return a {@link Predicate} evaluating to {@code true}, if exact one
	 *         predicate return {@code true}, or no predicates are passed, never
	 *         {@code null}.
	 */
	@SafeVarargs
	public static <T> Predicate<T> xor(
			Iterable<? extends Predicate<? super T>>... predicates) {
		return new OrPredicate<>(predicates);
	}

	/**
	 * This method provides a predicate that is constructed by all predicates
	 * passed, where at exact one predicate must evaluate to {@code true}.
	 * 
	 * @param predicates
	 *            The predicates to evaluate.
	 * @return a {@link Predicate} evaluating to {@code true}, if exact one
	 *         predicate return {@code true}, or no predicates are passed, never
	 *         {@code null}.
	 */
	@SafeVarargs
	public static <T> Predicate<T> xor(
			Predicate<? super T>... predicates) {
		return new OrPredicate<>(Arrays.asList(predicates));
	}

	/**
	 * This method creates a predicate that is exactly {@code true}, if the
	 * passed predicate returns {@code false} and vice versa.
	 * 
	 * @param predicate
	 *            The predicate to be inversed, not {@code null}.
	 * @return the inversed predicate, never {@code null}.
	 * @throws IllegalArgumentException
	 *             , if predicate is {@code null}.
	 */
	public static <T> Predicate<T> not(Predicate<? super T> predicate) {
		return new NotPredicate<>(predicate);
	}

	/**
	 * This method creates a predicate that is exactly {@code true}, if a given
	 * instance passed to the predicate is in the set of items to be accepted..
	 * 
	 * @param values
	 *            The values to be accepted, not {@code null}.
	 * @return the selecting predicate baed on the values passed, never
	 *         {@code null}.
	 */
	@SafeVarargs
	public static <T> Predicate<T> include(Iterable<? extends T>... values) {
		return new IncludedPredicate<>(values);
	}

	/**
	 * This method creates a predicate that is exactly {@code true}, if a given
	 * instance passed to the predicate is in the set of items to be accepted..
	 * 
	 * @param values
	 *            The values to be accepted, not {@code null}.
	 * @return the selecting predicate baed on the values passed, never
	 *         {@code null}.
	 */
	@SafeVarargs
	public static <T> Predicate<T> include(T... values) {
		return new IncludedPredicate<>(Arrays.asList(values));
	}

	/**
	 * This method creates a predicate that is exactly {@code true}, if a given
	 * instance passed to the predicate is NOT in the set of items to be
	 * accepted..
	 * 
	 * @param values
	 *            The values to be accepted, not {@code null}.
	 * @return the selecting predicate baed on the values passed, never
	 *         {@code null}.
	 */
	@SafeVarargs
	public static <T> Predicate<T> exclude(Iterable<? extends T>... values) {
		return new NotPredicate<>(new IncludedPredicate<>(values));
	}

	/**
	 * This method creates a predicate that is exactly {@code true}, if a given
	 * instance passed to the predicate is NOT in the set of items to be
	 * accepted..
	 * 
	 * @param values
	 *            The values to be accepted, not {@code null}.
	 * @return the selecting predicate baed on the values passed, never
	 *         {@code null}.
	 */
	@SafeVarargs
	public static <T> Predicate<T> exclude(T... values) {
		return new NotPredicate<>(new IncludedPredicate<>(Arrays.asList(values)));
	}

	/**
	 * This method creates a predicate that is exactly {@code true}, if at least
	 * the given number of instances passed to the predicate are matching the
	 * predicate.
	 * 
	 * @param min
	 *            the minimum of items required.
	 * @param predicate
	 *            The predicate to be used for selecting, not {@code null}.
	 * @return the evaluating predicate based on the values passed, never
	 *         {@code null}.
	 */
	public static <T> Predicate<T> min(int min,
			Predicate<? super T> predicate) {
		return new MinCountPredicate<>(min, predicate);
	}

	/**
	 * This method creates a predicate that is exactly {@code true}, if not more
	 * than the given number of instances passed to the predicate are matching
	 * the predicate.
	 * 
	 * @param max
	 *            the maximum of items required.
	 * @param predicate
	 *            The predicate to be used for selecting, not {@code null}.
	 * @return the evaluating predicate based on the values passed, never
	 *         {@code null}.
	 */
	public static <T> Predicate<T> max(int max,
			Predicate<? super T> predicate) {
		return new MaxCountPredicate<>(max, predicate);
	}

	/**
	 * This method allows to select all instances within some {@link Iterable}
	 * instances using an arbitrary {@link Predicate}.
	 * 
	 * @param predicate
	 *            The selecting predicate.
	 * @param items
	 *            The items to be filtered.
	 * @return The items that match the predicate.
	 */
	@SafeVarargs
	public static <T> Collection<T> select(Predicate<T> predicate,
			Iterable<T>... items) {
		return new ItemFilter<>(items).apply(predicate);
	}

	/**
	 * This method allows to select all instances within some {@link Iterable}
	 * instances using an arbitrary {@link Predicate}.
	 * 
	 * @param predicate
	 *            The selecting predicate.
	 * @param items
	 *            The items to be filtered.
	 * @return The items that match the predicate.
	 */
	@SafeVarargs
	public static <T> Collection<T> select(Predicate<T> predicate,
			T... items) {
		return new ItemFilter<>(items).apply(predicate);
	}

	/**
	 * This method allows to count (visit) all instances within some
	 * {@link Iterable} instances that match an arbitrary {@link Predicate}.
	 * 
	 * @param predicate
	 *            The counting predicate.
	 * @param items
	 *            The items to be filtered.
	 * @return The items that match the predicate.
	 */
	@SafeVarargs
	public static <T> int count(Predicate<T> predicate,
			Iterable<T>... items) {
		return new ItemVisitor<>(items).apply(predicate);
	}

	/**
	 * This method allows to count (visit) all instances within some
	 * {@link Iterable} instances that match an arbitrary {@link Predicate}.
	 * 
	 * @param predicate
	 *            The counting predicate.
	 * @param items
	 *            The items to be filtered.
	 * @return The items that match the predicate.
	 */
	@SafeVarargs
	public static <T> int count(Predicate<T> predicate,
			T... items) {
		return new ItemVisitor<>(items).apply(predicate);
	}

	/**
	 * This method allows to create a predicate for {@link MonetaryAmount}s, by
	 * predicating its {@link CurrencyUnit}.
	 * 
	 * @param currencyPredicate
	 *            The {@link CurrencyUnit} {@link Predicate} instance, not {@code null}.
	 * @return The according {@link Predicate} for {@link MonetaryAmount}.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Predicate<? extends MonetaryAmount> createAmountPredicate(
			Predicate<? extends CurrencyUnit> currencyPredicate) {
		return new AmountCurrencyUnitPredicate(currencyPredicate);
	}


}
