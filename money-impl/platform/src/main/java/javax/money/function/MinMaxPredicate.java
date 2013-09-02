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

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * Predicate that select {@link MonetaryAmount} based on their minimum or
 * maximum value.
 * 
 * @author Anatole Tresch
 */
public class MinMaxPredicate implements
		MonetaryFunction<MonetaryAmount, Boolean> {
	
	/** The minimum amount required, or null. */
	private MonetaryAmount minAmount;
	
	/** Flag, if the minimum amount is included into the range to check. */
	private boolean minInclusive;
	
	/** The maximum amount required, or null. */
	private MonetaryAmount maxAmount;
	
	/** Flag, if the maximum amount is included into the range to check. */
	private boolean maxInclusive;

	/**
	 * Set the minimum value required, hereby the minimum value itself is also
	 * included.
	 * 
	 * @param amount
	 *            the minimum value, or {@code null} to remove the condition.
	 * @return this, for chaining.
	 */
	public MinMaxPredicate withMinValue(MonetaryAmount amount) {
		return withMinValue(amount, true);
	}

	/**
	 * Set the minimum value required.
	 * 
	 * @param amount
	 *            the minimum value, or {@code null} to remove the condition.
	 * @param includeValue
	 *            Flag, if the {@code min} value is also included.
	 * @return this, for chaining.
	 */
	public MinMaxPredicate withMinValue(MonetaryAmount amount,
			boolean includeValue) {
		this.minInclusive = includeValue;
		this.minAmount = amount;
		return this;
	}

	/**
	 * Set the maximum value required, hereby the maximum value itself is also
	 * included.
	 * 
	 * @param amount
	 *            the maximum value, or {@code null} to remove the condition.
	 * @return this, for chaining.
	 */
	public MinMaxPredicate withMaxValue(MonetaryAmount amount) {
		return withMaxValue(amount, true);
	}

	/**
	 * Set the maximum value required.
	 * 
	 * @param amount
	 *            the maximum value, or {@code null} to remove the condition.
	 * @param includeValue
	 *            Flag, if the {@code max} value is also included.
	 * @return this, for chaining.
	 */
	public MinMaxPredicate withMaxValue(MonetaryAmount amount,
			boolean includeValue) {
		this.maxInclusive = includeValue;
		this.maxAmount = amount;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryFunction#apply(java.lang.Object)
	 */
	@Override
	public Boolean apply(MonetaryAmount value) {
		if(minAmount!=null){
			if(minInclusive && value.isLessThanOrEqualTo(minAmount)){
				return Boolean.FALSE;
			}
			else if(value.isLessThan(minAmount)){
				return Boolean.FALSE;
			}
		}
		if(maxAmount!=null){
			if(maxInclusive && value.isGreaterThanOrEqualTo(maxAmount)){
				return Boolean.FALSE;
			}
			else if(value.isGreaterThan(maxAmount)){
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MinMaxPredicate [minAmount=" + minAmount + ", minInclusive="
				+ minInclusive + ", maxAmount=" + maxAmount + ", maxInclusive="
				+ maxInclusive + "]";
	}

	
}