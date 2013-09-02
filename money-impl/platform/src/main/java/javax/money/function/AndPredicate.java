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

public class AndPredicate<T> extends AbstractMultiPredicate<T> {

	@Override
	protected boolean isPredicateTrue(T value,
			Set<MonetaryFunction<T, Boolean>> acceptedValues) {
		for (MonetaryFunction<T, Boolean> subPredicate : acceptedValues) {
			if (!subPredicate.apply(value)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public AndPredicate<T> clearPredicates() {
		return (AndPredicate<T>) super.clearPredicates();
	}

	@Override
	public AndPredicate<T> withPredicates(
			Collection<MonetaryFunction<T, Boolean>> predicates) {
		return (AndPredicate<T>) super.withPredicates(predicates);
	}

	@Override
	public AndPredicate<T> withPredicates(
			MonetaryFunction<T, Boolean>... predicates) {
		return (AndPredicate<T>) super.withPredicates(predicates);
	}

}