package net.java.javamoney.ri.format.impl;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.format.ItemParser;
import javax.money.format.ItemParserFactory;
import javax.money.format.LocalizationStyle;
import javax.money.format.spi.ItemParserFactorySpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemParserFactoryImpl implements ItemParserFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ItemParserFactoryImpl.class);

	@SuppressWarnings("rawtypes")
	private Map<Class, Set<ItemParserFactorySpi>> factoriesMap = new ConcurrentHashMap<Class, Set<ItemParserFactorySpi>>();

	private final ServiceLoader<ItemParserFactorySpi> parserFactorySpiLoader = ServiceLoader
			.load(ItemParserFactorySpi.class);

	public ItemParserFactoryImpl() {
		loadExtensions();
	}

	@Override
	public <T> ItemParser<T> getItemParser(Class<T> targetType,
			LocalizationStyle style) {
		@SuppressWarnings("rawtypes")
		Set<ItemParserFactorySpi> factories = factoriesMap.get(targetType);
		if (factories == null) {
			throw new IllegalArgumentException(
					"No parser factories loaded for " + targetType.getName());
		}
		for (ItemParserFactorySpi<T> spi : factories) {
			ItemParser<T> itemParser = spi.getItemParser(style);
			if (itemParser != null) {
				return itemParser;
			}
		}
		throw new IllegalArgumentException("No parser could be created for "
				+ targetType.getName() + ", " + style);
	}

	@Override
	public Enumeration<String> getSupportedStyleIds(Class<?> targetType) {
		Set<ItemParserFactorySpi> factories = factoriesMap.get(targetType);
		if (factories == null) {
			return Collections.enumeration(Collections.EMPTY_SET);
		}
		Set<String> result = new HashSet<String>();
		for (ItemParserFactorySpi spi : factories) {
			Enumeration<String> en = spi.getSupportedStyleIds();
			while (en.hasMoreElements()) {
				result.add(en.nextElement());

			}
		}
		return Collections.enumeration(result);
	}

	@Override
	public boolean isSupportedStyle(Class<?> targetType, String styleId) {
		Set<ItemParserFactorySpi> factories = factoriesMap.get(targetType);
		if (factories == null) {
			return false;
		}
		Set<String> result = new HashSet<String>();
		for (ItemParserFactorySpi spi : factories) {
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
	public <T> ItemParser<T> getItemParser(Class<T> targetType, Locale locale) {
		return getItemParser(targetType, LocalizationStyle.of(locale));
	}

	/**
	 * Loads and registers the {@link ItemParserFactorySpi} instances. It also
	 * checks for the types exposed.
	 */
	private void loadExtensions() {
		for (ItemParserFactorySpi<?> t : parserFactorySpiLoader) {
			try {
				if (t.getTargetClass() == null) {
					throw new IllegalArgumentException(
							"ItemFormatterFactorySpi of type: "
									+ t.getClass().getName()
									+ " does not define a target type.");
				}
				Set<ItemParserFactorySpi> spis = this.factoriesMap.get(t
						.getTargetClass());
				if (spis == null) {
					spis = new HashSet<ItemParserFactorySpi>();
					this.factoriesMap.put(t.getTargetClass(), spis);
				}
				spis.add(t);
			} catch (Exception e) {
				LOGGER.warn("Error loading MonetaryExtension.", e);
			}
		}
	}
}
