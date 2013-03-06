package net.java.javamoney.ri.format.impl;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.format.ItemFormatter;
import javax.money.format.ItemFormatterFactory;
import javax.money.format.LocalizationStyle;
import javax.money.format.spi.ItemFormatterFactorySpi;
import javax.money.provider.spi.MonetaryExtension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultItemFormatterFactory implements ItemFormatterFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DefaultItemFormatterFactory.class);

	@SuppressWarnings("rawtypes")
	private Map<Class, Set<ItemFormatterFactorySpi>> factoriesMap = new ConcurrentHashMap<Class, Set<ItemFormatterFactorySpi>>();

	private final ServiceLoader<ItemFormatterFactorySpi> formatterFactorySpiLoader = ServiceLoader
			.load(ItemFormatterFactorySpi.class);

	public DefaultItemFormatterFactory() {
		loadExtensions();
	}

	@Override
	public <T> ItemFormatter<T> getItemFormatter(Class<T> targetType,
			LocalizationStyle style) {
		@SuppressWarnings("rawtypes")
		Set<ItemFormatterFactorySpi> factories = factoriesMap.get(targetType);
		if (factories == null) {
			throw new IllegalArgumentException(
					"No formatter factories loaded for " + targetType.getName());
		}
		for (ItemFormatterFactorySpi<T> spi : factories) {
			ItemFormatter<T> itemFormatter = spi.getItemFormatter(style);
			if (itemFormatter != null) {
				return itemFormatter;
			}
		}
		throw new IllegalArgumentException("No formatter could be created for "
				+ targetType.getName() + ", " + style);
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
			Locale locale) {
		return getItemFormatter(targetType, LocalizationStyle.of(locale));
	}

	/**
	 * Loads and registers the {@link MonetaryExtension} instances. It also
	 * checks for the types exposed.
	 */
	private void loadExtensions() {
		for (ItemFormatterFactorySpi<?> t : formatterFactorySpiLoader) {
			try {
				if (t.getTargetClass() != null) {
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
			} catch (Exception e) {
				LOGGER.warn("Error loading MonetaryExtension.", e);
			}
		}
	}
}
