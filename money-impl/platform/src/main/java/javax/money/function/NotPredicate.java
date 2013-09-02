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

import javax.money.MonetaryFunction;

/**
 * This predicate inverses any other predicate, so
 * {@code NotPredicate(p) == !(p)}.
 * 
 * @author Anatole Tresch
 */
public class NotPredicate<T> extends
		AbstractMultiPredicate<T> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.function.AbstractValuePredicate#isPredicateTrue(javax.money
	 * .MonetaryAmount, java.util.Set)
	 */
	@Override
	protected boolean isPredicateTrue(T value,
			Set<MonetaryFunction<T, Boolean>> acceptedValues) {
		for (MonetaryFunction<T, Boolean> subPredicate : acceptedValues) {
			if (subPredicate.apply(value)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NotPredicate";
	}

	@Override
	public NotPredicate<T> clearPredicates() {
		return (NotPredicate<T>) super.clearPredicates();
	}

	@Override
	public NotPredicate<T> withPredicates(
			Collection<MonetaryFunction<T, Boolean>> predicates) {
		return (NotPredicate<T>) super.withPredicates(predicates);
	}

	@Override
	public NotPredicate<T> withPredicates(
			MonetaryFunction<T, Boolean>... predicates) {
		return (NotPredicate<T>) super.withPredicates(predicates);
	}
}