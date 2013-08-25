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
public class InstancesPredicate<T> implements
		MonetaryFunction<T, Boolean> {

	private Set<T> acceptedValues = new HashSet<T>();
	private boolean reversed = false;

	public InstancesPredicate<T> withValues(T... values) {
		withValues(Arrays.asList(values));
		return this;
	}

	public InstancesPredicate<T> withValues(Collection<T> values) {
		this.acceptedValues.addAll(values);
		return this;
	}

	public InstancesPredicate<T> withoutValues(T... values) {
		withoutValues(Arrays.asList(values));
		return this;
	}

	public InstancesPredicate<T> withoutValues(
			Collection<T> values) {
		this.acceptedValues.removeAll(values);
		return this;
	}

	public InstancesPredicate<T> clearValues() {
		this.acceptedValues.clear();
		return this;
	}

	public boolean isReversed() {
		return reversed;
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}

	@Override
	public Boolean apply(T value) {
		if (isPredicateTrue(value, this.acceptedValues)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	protected boolean isPredicateTrue(T value,
			Set<T> acceptedValues) {
		if (reversed) {
			return !acceptedValues.contains(value);
		}
		else {
			return acceptedValues.contains(value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [reversed=" + reversed
				+ ", acceptedValues=" + acceptedValues + "]";
	}

	public static <T> InstancesPredicate<T> of(T... instances) {
		InstancesPredicate<T> predicate = new InstancesPredicate<T>();
		predicate.withValues(instances);
		return predicate;
	}

	public static <T> InstancesPredicate<T> of(Collection<T> instances) {
		InstancesPredicate<T> predicate = new InstancesPredicate<T>();
		predicate.withValues(instances);
		return predicate;
	}

}
