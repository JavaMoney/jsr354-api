/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
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
 *    Anatole Tresch - initial implementation
 */
package net.java.javamoney.ri.format.provider;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.format.ItemFormatException;
import javax.money.format.ItemFormatter;
import javax.money.format.ItemFormatterFactory;
import javax.money.format.LocalizationStyle;
import javax.money.provider.ExposedExtensionType;
import javax.money.provider.Monetary;

import net.java.javamoney.ri.common.AbstractRiComponent;
import net.java.javamoney.ri.format.spi.ItemFormatterFactorySpi;

public class ItemFormatterFactoryImpl extends AbstractRiComponent implements
		ItemFormatterFactory {

	@SuppressWarnings("rawtypes")
	private Map<Class, Set<ItemFormatterFactorySpi>> factoriesMap = new ConcurrentHashMap<Class, Set<ItemFormatterFactorySpi>>();

	public ItemFormatterFactoryImpl() {
		loadExtensions();
	}

	@Override
	public Enumeration<String> getSupportedStyleIds(Class<?> targetType) {
		Set<ItemFormatterFactorySpi> factories = factoriesMap.get(targetType);
		if (factories == null) {
			return Collections.enumeration(Collections.EMPTY_SET);
		}
		Set<String> result = new HashSet<String>();
		for (ItemFormatterFactorySpi spi : factories) {
			Enumeration<String> en = spi.getSupportedStyleIds();
			while (en.hasMoreElements()) {
				result.add(en.nextElement());

			}
		}
		return Collections.enumeration(result);
	}

	@Override
	public boolean isSupportedStyle(Class<?> targetType, String styleId) {
		Set<ItemFormatterFactorySpi> factories = factoriesMap.get(targetType);
		if (factories == null) {
			return false;
		}
		Set<String> result = new HashSet<String>();
		for (ItemFormatterFactorySpi spi : factories) {
			Enumeration<String> en = spi.getSupportedStyleIds();
			while (en.hasMoreElements()) {
				if (styleId.equals(en.nextElement())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public <T> ItemFormatter<T> getItemFormatter(Class<T> targetType,
			LocalizationStyle style) throws ItemFormatException {
		@SuppressWarnings("rawtypes")
		Set<ItemFormatterFactorySpi> factories = factoriesMap.get(targetType);
		if (factories == null) {
			throw new ItemFormatException("No formatter factories loaded for "
					+ targetType.getName());
		}
		for (ItemFormatterFactorySpi<T> spi : factories) {
			ItemFormatter<T> itemFormatter = spi.getItemFormatter(style);
			if (itemFormatter != null) {
				return itemFormatter;
			}
		}
		throw new ItemFormatException("No formatter could be created for "
				+ targetType.getName() + ", " + style);
	}

	@Override
	public <T> ItemFormatter<T> getItemFormatter(Class<T> targetType,
			Locale locale) throws ItemFormatException {
		return getItemFormatter(targetType, LocalizationStyle.of(locale));
	}

	/**
	 * Loads and registers the {@link ExposedExtensionType} instances. It also
	 * checks for the types exposed.
	 */
	private void loadExtensions() {
		for (ItemFormatterFactorySpi<?> t : Monetary.getLoader().getInstances(
				ItemFormatterFactorySpi.class)) {
			try {
				if (t.getTargetClass() == null) {
					throw new IllegalArgumentException(
							"ItemFormatterFactorySpi of type: "
									+ t.getClass().getName()
									+ " does not define a target type.");
				}
				Set<ItemFormatterFactorySpi> spis = this.factoriesMap.get(t
						.getTargetClass());
				if (spis == null) {
					spis = new HashSet<ItemFormatterFactorySpi>();
					this.factoriesMap.put(t.getTargetClass(), spis);
				}
				spis.add(t);
			} catch (Exception e) {
				log.warn("Error loading ExposedExtensionType.", e);
			}
		}
	}
}
