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

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Predicate;

/**
 * This abstract class models a predicate used for filtering of
 * {@link MonetaryAmount} instances based on an arbitrary {@link CurrencyUnit}
 * predicate. I.e. all amounts of a given CurrencyUnit can be filtered by
 * calling:
 * 
 * <pre>
 *   Collection<MonetaryAmount> mixedAmounts = ...;
 *   Collection<MonetaryAmount> amountsInCHF = MonetaryPredicates.filter(
 *   	MonetaryPredicates.createAmountPredicate(
 *   	    MonetaryPredicates.includes(MoneyCurrency.of("CHF")));
 *   );
 * </pre>
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the input values to
 */
class AmountCurrencyUnitPredicate<T extends MonetaryAmount> implements
		Predicate<T> {

	private Predicate<CurrencyUnit> currencyPredicate;

	public AmountCurrencyUnitPredicate(
			Predicate<CurrencyUnit> currencyPredicate) {
		if (currencyPredicate == null) {
			throw new IllegalArgumentException("currencyPredicate required.");
		}
		this.currencyPredicate = currencyPredicate;
	}

	@Override
	public Boolean apply(MonetaryAmount value) {
		return currencyPredicate.apply(value.getCurrency());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AmountCurrencyUnitPredicate [currencyPredicate="
				+ currencyPredicate + "]";
	}

}
