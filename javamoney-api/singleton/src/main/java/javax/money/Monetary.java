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
package javax.money;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.ExchangeRateType;
import javax.money.format.AmountFormatterFactory;
import javax.money.format.AmountParserFactory;
import javax.money.format.CurrencyFormatterFactory;
import javax.money.format.CurrencyParserFactory;
import javax.money.spi.MonetaryExtension;

/**
 * This is the main accessor component for Java Money.
 * 
 * @author Anatole Tresch
 * 
 */
public final class Monetary {

	private static final Monetary INSTANCE = new Monetary();

	private Class<?> defaultNumberClass;

	private final CurrencyUnitProvider currencyUnitProvider;
	private final Map<Class<?>, MonetaryAmountFactory> monetaryAmountFactories = new HashMap<Class<?>, MonetaryAmountFactory>();
	private final RoundingProvider roundingProvider;
	private final Map<ExchangeRateType, ExchangeRateProvider> exchangeRateProviders = new HashMap<ExchangeRateType, ExchangeRateProvider>();
	private final Map<ExchangeRateType, CurrencyConverter> currencyConverters = new HashMap<ExchangeRateType, CurrencyConverter>();
	private final Map<Class<?>, MonetaryExtension> extensions = new HashMap<Class<?>, MonetaryExtension>();
	private final AmountParserFactory amountParserFactory;
	private final AmountFormatterFactory amountFormatterFactory;
	private final CurrencyParserFactory currencyParserFactory;
	private final CurrencyFormatterFactory currencyFormatterFactory;

	private final ServiceLoader<MonetaryAmountFactory> amountFactoryLoader = ServiceLoader
			.load(MonetaryAmountFactory.class);
	private final ServiceLoader<ExchangeRateProvider> exchangeRateProviderLoader = ServiceLoader
			.load(ExchangeRateProvider.class);
	private final ServiceLoader<CurrencyConverter> currencyConverterLoader = ServiceLoader
			.load(CurrencyConverter.class);
	private final ServiceLoader<MonetaryExtension> extensionsLoader = ServiceLoader
			.load(MonetaryExtension.class);

	/**
	 * Singleton constructor.
	 */
	private Monetary() {
		currencyUnitProvider = loadService(CurrencyUnitProvider.class);
		roundingProvider = loadService(RoundingProvider.class);
		amountParserFactory = loadService(AmountParserFactory.class);
		amountFormatterFactory = loadService(AmountFormatterFactory.class);
		currencyParserFactory = loadService(CurrencyParserFactory.class);
		currencyFormatterFactory = loadService(CurrencyFormatterFactory.class);
		// TODO define how to handle and handle duplicate registrations!
		for (ExchangeRateProvider t : exchangeRateProviderLoader) {
			this.exchangeRateProviders.put(t.getExchangeRateType(), t);
		}
		for (CurrencyConverter t : currencyConverterLoader) {
			this.currencyConverters.put(t.getExchangeRateType(), t);
		}
		for (MonetaryAmountFactory t : amountFactoryLoader) {
			this.monetaryAmountFactories.put(t.getNumberClass(), t);
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

			}
		}
	}

	/**
	 * Initializes the default numeric representation class, used when calling
	 * {@link #getMonetaryAmountFactory()}.
	 */
	private void initDefaultNumberClass() {
		String defaultClassName = System
				.getProperty("javax.money.defaultNumberClass");
		if (defaultClassName != null) {
			try {
				defaultNumberClass = Class.forName(defaultClassName);
				if (!this.monetaryAmountFactories
						.containsKey(defaultNumberClass)) {
					defaultNumberClass = null;
				}
			} catch (ClassNotFoundException e) {
				// TODO log!
				e.printStackTrace();
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
	 * Access the {@link MonetaryAmountFactory} component.
	 * 
	 * @return the {@link MonetaryAmountFactory} component, never {@code null}.
	 */
	public static MonetaryAmountFactory getMonetaryAmountFactory(
			Class<?> numberClass) {
		MonetaryAmountFactory factory = INSTANCE.monetaryAmountFactories
				.get(numberClass);
		if (factory == null) {
			throw new UnsupportedOperationException(
					"No MonetaryAmountFactory for number class '"
							+ numberClass.getName() + "' loaded.");
		}
		return factory;
	}

	/**
	 * Access the {@link MonetaryAmountFactory} default component.
	 * 
	 * @return the {@link MonetaryAmountFactory} component, never {@code null}.
	 */
	public static MonetaryAmountFactory getMonetaryAmountFactory() {
		return getMonetaryAmountFactory(getDefaultNumberClass());
	}

	/**
	 * Get the default number class that is used for the current system. The
	 * default class is determined by the implementation, but can be overridden
	 * by setting a system property:
	 * <p>
	 * <code>-Djavax.money.defaultNumberClass=a.b.c.MyClass</code>.
	 * <p>
	 * It is required that a registered {@link MonetaryAmountFactory} matches
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
			throw new IllegalArgumentException(
					"No ExchangeRateProvider for the required type registered: "
							+ type);
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
			throw new IllegalArgumentException(
					"No CurrencyConverter for the required type registered: "
							+ type);
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
	 * Access the {@link AmountFormatterFactory} component.
	 * 
	 * @return the {@link AmountFormatterFactory} component, never {@code null}.
	 */
	public static AmountFormatterFactory getAmountFormatterFactory() {
		if (INSTANCE.amountFormatterFactory == null) {
			throw new UnsupportedOperationException(
					"No AmountFormatterFactory loaded");
		}
		return INSTANCE.amountFormatterFactory;
	}

	/**
	 * Access the {@link AmountParserFactory} component.
	 * 
	 * @return the {@link AmountParserFactory} component, never {@code null}.
	 */
	public static AmountParserFactory getAmountParserFactory() {
		if (INSTANCE.amountParserFactory == null) {
			throw new UnsupportedOperationException(
					"No AmountParserFactory loaded");
		}
		return INSTANCE.amountParserFactory;
	}

	/**
	 * Access the {@link CurrencyFormatterFactory} component.
	 * 
	 * @return the {@link CurrencyFormatterFactory} component, never
	 *         {@code null}.
	 */
	public static CurrencyFormatterFactory getCurrencyFormatterFactory() {
		if (INSTANCE.currencyFormatterFactory == null) {
			throw new UnsupportedOperationException(
					"No CurrencyFormatterFactory loaded");
		}
		return INSTANCE.currencyFormatterFactory;
	}

	/**
	 * Access the {@link CurrencyParserFactory} component.
	 * 
	 * @return the {@link CurrencyParserFactory} component, never {@code null}.
	 */
	public static CurrencyParserFactory getCurrencyParserFactory() {
		if (INSTANCE.currencyParserFactory == null) {
			throw new UnsupportedOperationException(
					"No CurrencyParserFactory loaded");
		}
		return INSTANCE.currencyParserFactory;
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
