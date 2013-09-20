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

import javax.money.MonetaryAmount;
import javax.money.Predicate;

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
class MinCountPredicate<T> implements Predicate<T> {
	/** The minimal number of items that must match, or null. */
	private Integer min;
	/** The current number of items that matched the predicate. */
	private int currentNum;

	private Predicate<? super T> predicate;

	/**
	 * Set the minimal number of items that are required to match the predicate
	 * passed to {@link #apply(MonetaryAmount)}. If less items match the whole
	 * {@link MinCountPredicate} will not match.
	 * 
	 * @param min
	 *            The minimal number, or {@code null} to remove the condition.
	 * @return this, for chaining.
	 */
	MinCountPredicate(int min, Predicate<? super T> predicate) {
		this.predicate = predicate;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CountPredicate [min=" + min + "]";
	}

	@Override
	public Boolean apply(T value) {
		if (predicate != null && predicate.apply(value)) {
			currentNum++;
			if (checkMinFailed()) {
				return false;
			}
		}
		return checkMinFailed();
	}

}