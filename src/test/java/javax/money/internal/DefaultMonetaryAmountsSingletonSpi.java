/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2020 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.money.internal;

import javax.money.DummyAmount;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryException;
import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryAmountFactoryProviderSpi;
import javax.money.spi.MonetaryAmountsSingletonSpi;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default implementation of MonetaryAmountsSpi for testing only.
 */
public class DefaultMonetaryAmountsSingletonSpi implements MonetaryAmountsSingletonSpi{

	private final Map<Class<? extends MonetaryAmount>, MonetaryAmountFactoryProviderSpi<?>> factories = new ConcurrentHashMap<>();

	public DefaultMonetaryAmountsSingletonSpi() {
		for (MonetaryAmountFactoryProviderSpi<?> f : Bootstrap
				.getServices(MonetaryAmountFactoryProviderSpi.class)) {
			factories.put(f.getAmountType(), f);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends MonetaryAmount> MonetaryAmountFactory<T> getAmountFactory(
			Class<T> amountType) {
		MonetaryAmountFactoryProviderSpi<T> f = MonetaryAmountFactoryProviderSpi.class
				.cast(factories.get(amountType));
		if (Objects.nonNull(f)) {
			return f.createMonetaryAmountFactory();
		}
		throw new MonetaryException(
				"No matching MonetaryAmountFactory found, type="
						+ amountType.getName());
	}

	@Override
	public Set<Class<? extends MonetaryAmount>> getAmountTypes() {
		return factories.keySet();
	}


	@Override
	public Class<? extends MonetaryAmount> getDefaultAmountType() {
		return DummyAmount.class;
	}

}
