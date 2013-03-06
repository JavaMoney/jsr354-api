/*
 * Copyright (c) 2012-2013, Credit Suisse
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.money.provider;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.ExchangeRateType;
import javax.money.convert.spi.CurrencyConverterDefaultFactorySpi;
import javax.money.convert.spi.ExchangeRateProviderDefaultFactorySpi;
import javax.money.format.ItemFormatterFactory;
import javax.money.format.ItemParserFactory;
import javax.money.provider.spi.MonetaryExtension;

/**
 * This is the main accessor component for Java Money.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 */
public final class Monetary {
	private static final Logger LOGGER = Logger.getLogger(Monetary.class
			.getName());
	private static final Monetary INSTANCE = new Monetary();

	private Class<?> defaultNumberClass;

	private final CurrencyUnitProvider currencyUnitProvider;
	private final Map<Class<?>, MonetaryAmountProvider> monetaryAmountProviders = new HashMap<Class<?>, MonetaryAmountProvider>();
	private final RoundingProvider roundingProvider;
	private final ExchangeRateProviderDefaultFactorySpi exchangeRateProviderDefaultFactorySpi;
	private final Map<ExchangeRateType, ExchangeRateProvider> exchangeRateProviders = new HashMap<ExchangeRateType, ExchangeRateProvider>();
	private final CurrencyConverterDefaultFactorySpi currencyConverterDefaultFactorySpi;
	private final Map<ExchangeRateType, CurrencyConverter> currencyConverters = new HashMap<ExchangeRateType, CurrencyConverter>();
	private final Map<Class<?>, MonetaryExtension> extensions = new HashMap<Class<?>, MonetaryExtension>();
	private final ItemParserFactory itemParserFactory;
	private final ItemFormatterFactory itemFormatterFactory;

	private final ServiceLoader<MonetaryAmountProvider> amountFactoryLoader = ServiceLoader
			.load(MonetaryAmountProvider.class);
	private final ServiceLoader<CurrencyConverter> currencyConverterLoader = ServiceLoader
			.load(CurrencyConverter.class);
	private final ServiceLoader<ExchangeRateProvider> exchangeRateProviderLoader = ServiceLoader
			.load(ExchangeRateProvider.class);
	private final ServiceLoader<MonetaryExtension> extensionsLoader = ServiceLoader
			.load(MonetaryExtension.class);

	/**
	 * Singleton constructor.
	 */
	private Monetary() {
		currencyUnitProvider = loadService(CurrencyUnitProvider.class);
		roundingProvider = loadService(RoundingProvider.class);
		itemFormatterFactory = loadService(ItemFormatterFactory.class);
		itemParserFactory = loadService(ItemParserFactory.class);
		exchangeRateProviderDefaultFactorySpi = loadService(ExchangeRateProviderDefaultFactorySpi.class);
		currencyConverterDefaultFactorySpi = loadService(CurrencyConverterDefaultFactorySpi.class);
		// TODO define how to handle and handle duplicate registrations!
		for (ExchangeRateProvider t : exchangeRateProviderLoader) {
			this.exchangeRateProviders.put(t.getExchangeRateType(), t);
		}
		for (CurrencyConverter t : currencyConverterLoader) {
			this.currencyConverters.put(t.getExchangeRateType(), t);
		}
		for (MonetaryAmountProvider t : amountFactoryLoader) {
			this.monetaryAmountProviders.put(t.getNumberClass(), t);
		}
		loadExtensions();
		initDefaultNumberClass();
	}

	/**
	 * Loads and registers the {@link MonetaryExtension} instances. It also
	 * checks for the types exposed.
	 */
	private void loadExtensions() {
		for (MonetaryExtension t : extensionsLoader) {
			try {
				if (t.getExposedType() == null) {
					throw new IllegalArgumentException(
							"Monetary extension of type: "
									+ t.getClass().getName()
									+ " does not expose a type.");
				}
				if (!t.getExposedType().isAssignableFrom(t.getClass())) {
					throw new IllegalArgumentException(
							"Monetary extension of type: "
									+ t.getClass().getName()
									+ " does not implement exposed type: "
									+ t.getExposedType().getName());
				}
				this.extensions.put(t.getExposedType(), t);
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Error loading MonetaryExtension.", e);
			}
		}
	}

	/**
	 * Initializes the default numeric representation class, used when calling
	 * {@link #getMonetaryAmountProvider()}.
	 */
	private void initDefaultNumberClass() {
		String defaultClassName = System
				.getProperty("javax.money.defaultNumberClass");
		if (defaultClassName != null) {
			try {
				defaultNumberClass = Class.forName(defaultClassName);
				if (!this.monetaryAmountProviders
						.containsKey(defaultNumberClass)) {
					defaultNumberClass = null;
				}
			} catch (ClassNotFoundException e) {
				LOGGER.log(Level.FINE, "Class not found", e);
			}
		}
		if (defaultNumberClass == null) {
			defaultNumberClass = BigDecimal.class;
		}
	}

	/**
	 * Loads a service from the {@link ServiceLoader} that should be unique.
	 * This is typically the case for API services.
	 * 
	 * @param serviceType
	 *            THe target service interface type.
	 * @return The single instance found, or null.
	 * @throws IllegalStateException
	 *             if multiple service implementations are registered.
	 */
	private <T> T loadService(Class<T> serviceType) {
		ServiceLoader<T> loader = ServiceLoader.load(serviceType);
		T instance = null;
		for (T t : loader) {
			if (instance == null) {
				instance = t;
			} else {
				throw new IllegalStateException(
						"javaxmoney: Implementation of " + serviceType
								+ " is ambigous.");
			}
		}
		return instance;
	}

	/**
	 * Access the {@link MonetaryAmountFactorySpi} component.
	 * 
	 * @return the {@link MonetaryAmountFactorySpi} component, never
	 *         {@code null}.
	 */
	public static MonetaryAmountProvider getMonetaryAmountProvider(
			Class<?> numberClass) {
		MonetaryAmountProvider factory = INSTANCE.monetaryAmountProviders
				.get(numberClass);
		if (factory == null) {
			throw new UnsupportedOperationException(
					"No MonetaryAmountFactorySpi for number class '"
							+ numberClass.getName() + "' loaded.");
		}
		return factory;
	}

	/**
	 * Access the {@link MonetaryAmountFactorySpi} default component.
	 * 
	 * @return the {@link MonetaryAmountFactorySpi} component, never
	 *         {@code null}.
	 */
	public static MonetaryAmountProvider getMonetaryAmountProvider() {
		return getMonetaryAmountProvider(getDefaultNumberClass());
	}

	/**
	 * Get the default number class that is used for the current system. The
	 * default class is determined by the implementation, but can be overridden
	 * by setting a system property:
	 * <p>
	 * <code>-Djavax.money.defaultNumberClass=a.b.c.MyClass</code>.
	 * <p>
	 * It is required that a registered {@link MonetaryAmountFactorySpi} matches
	 * the default class configured, if not a warning is written and the
	 * implementation's default is used.
	 * 
	 * @return the default number class, never null.
	 */
	public static Class<?> getDefaultNumberClass() {
		return INSTANCE.defaultNumberClass;
	}

	/**
	 * Access the {@link CurrencyUnitProvider} component.
	 * 
	 * @return the {@link CurrencyUnitProvider} component, never {@code null}.
	 */
	public static CurrencyUnitProvider getCurrencyUnitProvider() {
		if (INSTANCE.currencyUnitProvider == null) {
			throw new UnsupportedOperationException(
					"No CurrencyUnitProvider loaded");
		}
		return INSTANCE.currencyUnitProvider;
	}

	/**
	 * Access the {@link ExchangeRateProvider} component.
	 * 
	 * @param type
	 *            the target {@link ExchangeRateType}.
	 * @return the {@link ExchangeRateProvider} component, never {@code null}.
	 * @throws IllegalArgumentException
	 *             if no such provider is registered.
	 */
	public static ExchangeRateProvider getExchangeRateProvider(
			ExchangeRateType type) {
		ExchangeRateProvider prov = INSTANCE.exchangeRateProviders.get(type);
		if (prov == null) {
			ExchangeRateProviderDefaultFactorySpi provFactory = INSTANCE.exchangeRateProviderDefaultFactorySpi;
			if (provFactory != null) {
				prov = provFactory.createExchangeRateProvider(type);
			}
			if (prov == null) {
				throw new IllegalArgumentException(
						"No ExchangeRateProvider for the required type registered: "
								+ type);
			} else {
				INSTANCE.exchangeRateProviders.put(type, prov);
			}
		}
		return prov;
	}

	/**
	 * Access the {@link ExchangeRateProvider} component.
	 * 
	 * @param type
	 *            the target {@link ExchangeRateType}.
	 * @return the {@link ExchangeRateProvider} component, never {@code null}.
	 * @throws IllegalArgumentException
	 *             if no such provider is registered.
	 */
	public static CurrencyConverter getCurrencyConverter(ExchangeRateType type) {
		CurrencyConverter prov = INSTANCE.currencyConverters.get(type);
		if (prov == null) {
			CurrencyConverterDefaultFactorySpi provFactory = INSTANCE.currencyConverterDefaultFactorySpi;
			if (provFactory != null) {
				prov = provFactory.createCurrencyConverter(type);
			}
			if (prov == null) {
				throw new IllegalArgumentException(
						"No CurrencyConverters for the required type registered: "
								+ type);
			} else {
				INSTANCE.currencyConverters.put(type, prov);
			}
		}
		return prov;
	}

	/**
	 * Access the defined {@link ExchangeRateType} currently registered.
	 * 
	 * @see #getCurrencyConverter(ExchangeRateType)
	 * @see #getExchangeRateProvider(ExchangeRateType)
	 * @return The exchange rate types allow to access a
	 *         {@link CurrencyConverter} or an {@link ExchangeRateProvider}.
	 */
	public static Enumeration<ExchangeRateType> getSupportedExchangeRateTypes() {
		return Collections.enumeration(INSTANCE.exchangeRateProviders.keySet());
	}

	/**
	 * Access the {@link ItemFormatterFactory} component.
	 * 
	 * @return the {@link ItemFormatterFactory} component, never {@code null}.
	 */
	public static ItemFormatterFactory getItemFormatterFactory() {
		if (INSTANCE.itemFormatterFactory == null) {
			throw new UnsupportedOperationException(
					"No ItemFormatterFactory loaded");
		}
		return INSTANCE.itemFormatterFactory;
	}

	/**
	 * Access the {@link ItemParserFactory} component.
	 * 
	 * @return the {@link ItemParserFactory} component, never {@code null}.
	 */
	public static ItemParserFactory getItemParserFactory() {
		if (INSTANCE.itemParserFactory == null) {
			throw new UnsupportedOperationException(
					"No ItemParserFactory loaded");
		}
		return INSTANCE.itemParserFactory;
	}

	/**
	 * Access the {@link RoundingProvider} component.
	 * 
	 * @return the {@link RoundingProvider} component, never {@code null}.
	 */
	public static RoundingProvider getRoundingProvider() {
		if (INSTANCE.roundingProvider == null) {
			throw new UnsupportedOperationException(
					"No RoundingProvider loaded");
		}
		return INSTANCE.roundingProvider;
	}

	/**
	 * Access a monetary extension by type.
	 * 
	 * @param extensionType
	 * @return The corresponding extension reference, never null.
	 * @throws IllegalArgumentException
	 *             if the required extension is not loaded, or does not expose
	 *             the required interface.
	 */
	public static <T> T getExtension(Class<T> extensionType) {
		@SuppressWarnings("unchecked")
		T ext = (T) INSTANCE.extensions.get(extensionType);
		if (ext == null) {
			throw new IllegalArgumentException(
					"Unsupported monetary extension: " + extensionType);
		}
		return ext;
	}

	/**
	 * Allows to check for the availability of an extension.
	 * 
	 * @param type
	 *            The exposed extension type.
	 * @return true, if such an extension type is loaded and registered.
	 */
	public static boolean isExtensionAvailable(Class<?> type) {
		return INSTANCE.extensions.containsKey(type);
	}

	/**
	 * Provides the list of exposed extension APIs currently registered.
	 * 
	 * @see MonetaryExtension#getExposedType()
	 * @return the enumeration containing the types of extensions loaded, never
	 *         null.
	 */
	public static Enumeration<Class<?>> getLoadedExtensions() {
		return Collections.enumeration(INSTANCE.extensions.keySet());
	}
}
