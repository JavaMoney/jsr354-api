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

import java.util.Set;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * This predicate implements the logic {@code or and xor} operations, where
 * {@code OrPredicate(p1,p2) == p1 || p2} or
 * {@code OrPredicate(p1,p2) == (p1 || p2) && !(p1 && p2)}.
 * 
 * @author Anatole Tresch
 */
public class OrPredicate extends
		AbstractValuePredicate<MonetaryFunction<MonetaryAmount, Boolean>> {

	/** Flag if {@code xor} is modelled, instead of {@code or} (default). */
	private boolean exclusive = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.function.AbstractValuePredicate#isPredicateTrue(javax.money
	 * .MonetaryAmount, java.util.Set)
	 */
	@Override
	protected boolean isPredicateTrue(MonetaryAmount value,
			Set<MonetaryFunction<MonetaryAmount, Boolean>> acceptedValues) {
		boolean currentValue = false;
		for (MonetaryFunction<MonetaryAmount, Boolean> subPredicate : acceptedValues) {
			if (!subPredicate.apply(value)) {
				if (exclusive) {
					if (currentValue) {
						return false;
					}
				}
				else {
					return true;
				}
			}
		}
		return currentValue;
	}

	/**
	 * Configures this instance to model exlusive OR (XOR) or non exclusive OR
	 * operation.
	 * 
	 * @param exclusiveOR
	 *            set to true to let the precidate model a XOR operation.
	 * @return this for chaining.
	 */
	public OrPredicate withXOR(boolean exclusiveOR) {
		this.exclusive = exclusiveOR;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrPredicate [XOR=" + exclusive + "]";
	}

}