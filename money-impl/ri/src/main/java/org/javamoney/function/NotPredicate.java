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
package org.javamoney.function;

import org.javamoney.ext.Predicate;


/**
 * This predicate implements the logic {@code or and xor} operations, where
 * {@code OrPredicate(p1,p2) == p1 || p2} or
 * {@code OrPredicate(p1,p2) == (p1 || p2) && !(p1 && p2)}.
 * 
 * @author Anatole Tresch
 */
final class NotPredicate<T> implements Predicate<T> {
	/** The child predicates. */
	private Predicate<? super T> predicate;

	/**
	 * Creates an NOT predicate.
	 * 
	 * @param predicate
	 *            The predicate to be inversed.
	 */
	NotPredicate(Predicate<? super T> predicate) {
		if (predicate == null) {
			throw new IllegalArgumentException("predicate required.");
		}
		this.predicate = predicate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryFunction#apply(java.lang.Object)
	 */
	@Override
	public boolean isPredicateTrue(T value) {
		return !predicate.isPredicateTrue(value);
	}

}