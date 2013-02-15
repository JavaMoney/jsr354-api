/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial version.
 */
package net.java.javamoney.ri.ext;

import java.util.Map;

import javax.money.CurrencyUnit;
import javax.money.ext.CompoundItem;
import javax.money.ext.CompoundItemBuilder;

import net.java.javamoney.ri.ext.common.AbstractCompoundItem;

/**
 * Defines a {@link CompoundItem} containing {@link CurrencyUnit} instances.
 * This is very useful for provding multiple currencies, e.g. provided by
 * different data providers, for different time periods, regions or other
 * classification types.
 * 
 * @see CompoundItem
 * @author Anatole Tresch
 */
public final class CompoundCurrencyUnit extends
		AbstractCompoundItem<CurrencyUnit> {

	/**
	 * Creates a new (empty) compound item of the given {@code type}.
	 * 
	 * @param type
	 *            The item^s target type, not {@code null}.
	 */
	public CompoundCurrencyUnit(String type) {
		super(type);
	}

	/**
	 * Creates a new (empty) compound item of the given {@code type} and
	 * {@code items}.
	 * 
	 * @param type
	 *            The item^s target type, not {@code null}.
	 * @param items
	 *            The items to be included int the instance.
	 */
	public CompoundCurrencyUnit(String type, Map<Object, CurrencyUnit> items) {
		super(type, items);
	}

	/**
	 * @param type
	 *            The item^s target type, not {@code null}.
	 * @param items
	 *            The items to be included int the instance.
	 * @param leadingItem
	 *            The leading item to be set.
	 */
	public CompoundCurrencyUnit(String type, Map<Object, CurrencyUnit> items,
			CurrencyUnit leadingItem) {
		super(type, items, leadingItem);
	}

	/**
	 * Creates a new {@link CompoundItemBuilder} based on this instance.
	 * 
	 * @return a new {@link CompoundItemBuilder}, never null.
	 */
	@Override
	public CompoundCurrencyUnitBuilder toBuilder() {
		return new CompoundCurrencyUnitBuilder(this);
	}

	/**
	 * Factory method to creates a new {@link CompoundItemBuilder}.
	 * 
	 * @return a new {@link CompoundItemBuilder}, never null.
	 */
	public static CompoundCurrencyUnitBuilder getBuilder(String type) {
		return new CompoundCurrencyUnitBuilder(type);
	}
}
