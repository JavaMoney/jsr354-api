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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * This abstract class models a predicate used for filtering of
 * {@link MonetaryAmount} instances based on arbitrary {@link #acceptedValues}
 * and a {@link #isPredicateTrue(MonetaryAmount, Set)} predicate function.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the input values to
 */
public abstract class AbstractMultiPredicate<T> implements
		MonetaryFunction<T, Boolean> {

	private Set<MonetaryFunction<T, Boolean>> predicates = new HashSet<MonetaryFunction<T, Boolean>>();

	public AbstractMultiPredicate<T> withPredicates(
			MonetaryFunction<T, Boolean>... predicates) {
		withPredicates(Arrays.asList(predicates));
		return this;
	}

	public AbstractMultiPredicate<T> withPredicates(
			Collection<MonetaryFunction<T, Boolean>> predicates) {
		this.predicates.addAll(predicates);
		return this;
	}

	public AbstractMultiPredicate<T> clearPredicates() {
		this.predicates.clear();
		return this;
	}

	@Override
	public Boolean apply(T value) {
		return isPredicateTrue(value, this.predicates);
	}

	protected abstract boolean isPredicateTrue(T value,
			Set<MonetaryFunction<T, Boolean>> predicates);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [predicates=" + predicates + "]";
	}

}
