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

import java.util.Collection;
import java.util.Set;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * This predicate allows filtering of {@link MonetaryAmount} instances using a
 * predicate and hereby counts the items that are matching. Additionally this
 * predicate can be configured using {@link #withMinMatching(Integer)} to ensure
 * that a minimal number of items matches the criterias, or with
 * {@link #withMaxMatching(Integer)} to ensure that not more than the specified
 * maximum number of items are matching the predicate.<br/>
 * Note that instances of this class are NOT thread-safe.
 * 
 * @author Anatole Tresch
 */
public class CountPredicate<T> extends AbstractMultiPredicate<T> {
	/** The minimal number of items that must match, or null. */
	private Integer min;
	/** The maximal number of items that must match, or null. */
	private Integer max;
	/** The current number of items that matched the predicate. */
	private int currentNum;

	/**
	 * Set the minimal number of items that are required to match the predicate
	 * passed to {@link #apply(MonetaryAmount)}. If less items match the whole
	 * {@link CountPredicate} will not match.
	 * 
	 * @param min
	 *            The minimal number, or {@code null} to remove the condition.
	 * @return this, for chaining.
	 */
	public CountPredicate<T> withMinMatching(Integer min) {
		this.min = min;
		return this;
	}

	/**
	 * Set the maximal number of items that are allowed to match the predicate
	 * passed to {@link #apply(MonetaryAmount)}. If more items match the whole
	 * {@link CountPredicate} will not match.
	 * 
	 * @param max
	 *            The maximal number, or {@code null} to remove the condition.
	 * @return this, for chaining.
	 */
	public CountPredicate<T> withMaxMatching(Integer max) {
		this.max = max;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.function.AbstractValuePredicate#isPredicateTrue(javax.money
	 * .MonetaryAmount, java.util.Set)
	 */
	@Override
	protected boolean isPredicateTrue(T value,
			Set<MonetaryFunction<T, Boolean>> predicates) {
		for (MonetaryFunction<T, Boolean> subPredicate : predicates) {
			if (subPredicate.apply(value)) {
				currentNum++;
				if (checkMaxFailed()) {
					return false;
				}
			}
		}
		return checkMinFailed();
	}

	/**
	 * Resets the {@link #currentNum} to 0. Maybe overridden to allow counts
	 * when using the predicate several times.
	 */
	protected void resetCurrentNum() {
		currentNum = 0;
	}

	/**
	 * Method to check if the number of items exceeds the maximum number
	 * configured.
	 * 
	 * @return true, if the current number counted exceeds the maximum number
	 *         configured.
	 */
	protected boolean checkMaxFailed() {
		if (max != null && currentNum > max.intValue()) {
			return true;
		}
		return false;
	}

	/**
	 * Method to check if the number of items is less than the minimum number
	 * configured.
	 * 
	 * @return true, if the current number counted is less than the minimum
	 *         number configured.
	 */
	protected boolean checkMinFailed() {
		if (min != null && currentNum < min.intValue()) {
			return true;
		}
		return false;
	}
	
	@Override
	public CountPredicate<T> clearPredicates() {
		return (CountPredicate<T>) super.clearPredicates();
	}

	@Override
	public CountPredicate<T> withPredicates(
			Collection<MonetaryFunction<T, Boolean>> predicates) {
		return (CountPredicate<T>) super.withPredicates(predicates);
	}

	@Override
	public CountPredicate<T> withPredicates(
			MonetaryFunction<T, Boolean>... predicates) {
		return (CountPredicate<T>) super.withPredicates(predicates);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CountPredicate [min=" + min + ", max=" + max + "]";
	}

}