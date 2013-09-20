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
import java.util.List;

import javax.money.MonetaryAmount;

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
class MaxCountPredicate<T> implements Predicate<T> {
	/** The minimal number of items that must match, or null. */
	private Integer max;
	/** The current number of items that matched the predicate. */
	private int currentNum;

	private List<Predicate<? super T>> predicates = new ArrayList<Predicate<? super T>>();

	/**
	 * Set the minimal number of items that are required to match the predicate
	 * passed to {@link #apply(MonetaryAmount)}. If less items match the whole
	 * {@link MaxCountPredicate} will not match.
	 * 
	 * @param min
	 *            The minimal number, or {@code null} to remove the condition.
	 * @return this, for chaining.
	 */
	MaxCountPredicate(Integer max,
			Iterable<? extends Predicate<? super T>>... predicates) {
		for (Iterable<? extends Predicate<? super T>> iterable : predicates) {
			for (Predicate<? super T> predicate : iterable) {
				this.predicates.add(predicate);
			}
		}
		this.max = max;
	}

	/**
	 * Method to check if the number of items is less than the minimum number
	 * configured.
	 * 
	 * @return true, if the current number counted is less than the minimum
	 *         number configured.
	 */
	protected boolean checkMaxFailed() {
		if (max != null && currentNum > max.intValue()) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MaxCountPredicate [min=" + max + "]";
	}

	@Override
	public Boolean apply(T value) {
		for (Predicate<? super T> subPredicate : predicates) {
			if (subPredicate.apply(value)) {
				currentNum++;
				if (checkMaxFailed()) {
					return false;
				}
			}
		}
		return checkMaxFailed();
	}

}