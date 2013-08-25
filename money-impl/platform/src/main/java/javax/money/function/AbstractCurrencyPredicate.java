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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * Predicate that selects {@link MonetaryAmount} instances depending on their
 * {@link CurrencyUnit}.
 * 
 * @author Anatole Tresch
 */
public abstract class AbstractCurrencyPredicate<T> implements
		MonetaryFunction<T, Boolean> {

	private boolean reversed = false;
	private List<MonetaryFunction<CurrencyUnit, Boolean>> currencyPredicates = new ArrayList<MonetaryFunction<CurrencyUnit, Boolean>>();

	public List<MonetaryFunction<CurrencyUnit, Boolean>> getCurrencySelectors() {
		return Collections.unmodifiableList(currencyPredicates);
	}

	public AbstractCurrencyPredicate<T> withPredicates(
			MonetaryFunction<CurrencyUnit, Boolean>... predicates) {
		withPredicates(Arrays.asList(predicates));
		return this;
	}

	public AbstractCurrencyPredicate<T> withPredicates(
			Collection<MonetaryFunction<CurrencyUnit, Boolean>> predicates) {
		this.currencyPredicates.addAll(predicates);
		return this;
	}

	public AbstractCurrencyPredicate<T> clearPredicates() {
		this.currencyPredicates.clear();
		return this;
	}

	public boolean isReversed() {
		return reversed;
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.function.AbstractValuePredicate#isPredicateTrue(javax.money
	 * .MonetaryAmount, java.util.Set)
	 */
	@Override
	public Boolean apply(T value) {
		for (MonetaryFunction<CurrencyUnit, Boolean> predicate : currencyPredicates) {
			if (!predicate.apply(getCurrencyUnit(value))) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	protected abstract CurrencyUnit getCurrencyUnit(T value);

}