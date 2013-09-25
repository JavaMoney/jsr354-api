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
 * 
 * Contributors: Anatole Tresch - initial implementation
 */
package org.javamoney.format;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


import org.javamoney.format.ItemFormat;
import org.javamoney.format.ItemFormatException;
import org.javamoney.format.LocalizationStyle;
import org.javamoney.format.spi.ItemFormatFactorySpi;
import org.javamoney.format.spi.MonetaryFormatsSingletonSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the base class for implementing the
 * {@link MonetaryFormatsSingletonSpi}.
 * 
 * @author Anatole Tresch
 */
public abstract class AbstractMonetaryFormatsSingletonSpi implements
		MonetaryFormatsSingletonSpi {

	@SuppressWarnings("rawtypes")
	private Map<Class, Set<ItemFormatFactorySpi>> formatMap = new ConcurrentHashMap<Class, Set<ItemFormatFactorySpi>>();
	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractMonetaryFormatsSingletonSpi.class);

	public AbstractMonetaryFormatsSingletonSpi() {
		reload();
	}

	@Override
	public Collection<String> getSupportedStyleIds(Class<?> targetType) {
		Set<String> result = new HashSet<String>();
		Set<ItemFormatFactorySpi> factories = formatMap.get(targetType);
		if (factories == null) {
			return Collections.emptySet();
		}
		for (ItemFormatFactorySpi spi : factories) {
			result.addAll(spi.getSupportedStyleIds());
		}
		return result;
	}

	@Override
	public boolean isSupportedStyle(Class<?> targetType, String styleId) {
		Set<ItemFormatFactorySpi> factories = formatMap.get(targetType);
		if (factories == null) {
			return false;
		}
		Set<String> result = new HashSet<String>();
		for (ItemFormatFactorySpi spi : factories) {
			if (spi.isSupportedStyle(styleId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public <T> ItemFormat<T> getItemFormat(Class<T> targetType,
			LocalizationStyle style) throws ItemFormatException {
		@SuppressWarnings("rawtypes")
		Set<ItemFormatFactorySpi> factories = formatMap.get(targetType);
		if (factories == null) {
			throw new ItemFormatException("No formatter factories loaded for "
					+ targetType.getName());
		}
		for (ItemFormatFactorySpi spi : factories) {
			ItemFormat<T> itemFormatter = spi.getItemFormat(style);
			if (itemFormatter != null) {
				return itemFormatter;
			}
		}
		throw new ItemFormatException("No formatter could be created for "
				+ targetType.getName() + ", " + style);
	}

	/**
	 * Loads and registers the {@link ExposedExtensionType} instances. It also
	 * checks for the types exposed.
	 */
	public void reload() {
		for (ItemFormatFactorySpi t : getItemFormatFactorySpis()) {
			try {
				if (t.getTargetClass() == null) {
					throw new IllegalArgumentException(
							"ItemFormatterFactorySpi of type: "
									+ t.getClass().getName()
									+ " does not define a target type.");
				}
				Set<ItemFormatFactorySpi> spis = this.formatMap.get(t
						.getTargetClass());
				if (spis == null) {
					spis = new HashSet<ItemFormatFactorySpi>();
					this.formatMap.put(t.getTargetClass(), spis);
				}
				spis.add(t);
			} catch (Exception e) {
				LOG.warn("Error loading ItemFormatFactorySpi.", e);
			}
		}
	}

	@Override
	public LocalizationStyle getLocalizationStyle(Class<?> targetType,
			String styleId) {
		LocalizationStyle style = LocalizationStyle.of(targetType, styleId);
		if (style == null) {
			style = new LocalizationStyle.Builder(targetType, styleId).build();
		}
		return style;
	}

	protected abstract Iterable<ItemFormatFactorySpi> getItemFormatFactorySpis();

}
