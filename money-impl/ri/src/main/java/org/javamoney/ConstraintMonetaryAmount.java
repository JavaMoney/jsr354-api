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
package org.javamoney;

import javax.money.MonetaryAmount;
import javax.money.Money;

import org.javamoney.ext.Predicate;

/**
 * Platform RI: This class decorates an arbitrary {@link MonetaryAmount}
 * instance and ensure no negative values can be created using this instance.
 * Though there are many constraints possible for
 * <p>
 * As required by the {@link MonetaryAmount} interface, this class is
 * <ul>
 * <li>immutable</li>
 * <li>final</li>
 * <li>thread-safe/li>
 * <li>serializable</li>
 * </ul>
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class ConstraintMonetaryAmount {

	/** The shared unsigned predicate. */
	private static final UnsignedPredicate UNSIGED_PREDICATE = new UnsignedPredicate();

	/**
	 * Creates an unsigned {@link MonetaryAmount} based on the given
	 * {@link MonetaryAmount}.
	 * 
	 * @param amount
	 * @return an ansigned instance, that can never be negative.
	 */
	public static MonetaryAmount unsignedAmount(MonetaryAmount amount) {
		return new ConstraintMoney(Money.from(amount), UNSIGED_PREDICATE);
	}

	/**
	 * Creates an predicated {@link MonetaryAmount} based on the given
	 * {@link MonetaryAmount}.
	 * 
	 * @param amount
	 *            The amount to decorated.
	 * @return a predicated instance, that ensures the given predicate is always
	 *         ensured on all operations.
	 */
	public static MonetaryAmount constraintAmount(MonetaryAmount amount,
			Predicate<MonetaryAmount> predicate) {
		return new ConstraintMoney(Money.from(amount), predicate);
	}

	/**
	 * Predicate used for unsigned amounts.
	 * 
	 * @author Anatole Tresch
	 */
	private static final class UnsignedPredicate implements
			Predicate<MonetaryAmount> {
		public boolean isPredicateTrue(MonetaryAmount amount) {
			return Money.from(amount).signum() >= 0;
		}
	}
}
