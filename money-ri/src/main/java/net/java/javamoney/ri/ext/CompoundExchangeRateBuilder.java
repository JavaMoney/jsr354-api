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

import javax.money.convert.ExchangeRate;
import javax.money.ext.CompoundItem;
import javax.money.ext.CompoundItemBuilder;

import net.java.javamoney.ri.ext.common.AbstractCompoundItemBuilder;

/**
 * Defines a {@link CompoundItemBuilder} for creating
 * {@link CompoundExchangeRate} instances.
 * 
 * @author Anatole Tresch
 */
public final class CompoundExchangeRateBuilder extends
		AbstractCompoundItemBuilder<ExchangeRate> {

	/**
	 * Creates a new builder instance.
	 * 
	 * @param type
	 *            the type of the {@link CompoundItem} to be created by the
	 *            builder.
	 */
	public CompoundExchangeRateBuilder(String type) {
		super(type);
	}

	/**
	 * Creates a new builder instance.
	 * 
	 * @param baseItem
	 *            Use the items, leading item and type from the given
	 *            {@code baseItem}.
	 */
	public CompoundExchangeRateBuilder(CompoundExchangeRate baseItem) {
		super(baseItem);
	}

	/**
	 * Creates a new instance of a {@link CompoundItem} using the properties set
	 * within this builder instace.
	 */
	@Override
	public CompoundExchangeRate toCompoundItem() {
		return new CompoundExchangeRate(getType(), getAll(), getLeadingItem());
	}

}
