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
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import javax.money.convert.ExchangeRateProvider;
import javax.money.format.AmountFormatterFactory;
import javax.money.format.AmountParserFactory;
import javax.money.format.CurrencyFormatterFactory;
import javax.money.format.CurrencyParserFactory;

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
	private final RegionProvider regionProvider;
	private final Map<Class<?>, MonetaryAmountFactory> monetaryAmountFactories = new HashMap<Class<?>, MonetaryAmountFactory>();
	private final RoundingProvider roundingProvider;
	private final ExchangeRateProvider exchangeRateProvider;
	private final AmountParserFactory amountParserFactory;
	private final AmountFormatterFactory amountFormatterFactory;
	private final CurrencyParserFactory currencyParserFactory;
	private final CurrencyFormatterFactory currencyFormatterFactory;

	private final ServiceLoader<MonetaryAmountFactory> amountFactoryLoader = ServiceLoader
			.load(MonetaryAmountFactory.class);

	/**
	 * Singleton constructor.
	 */
	private Monetary() {
		currencyUnitProvider = loadService(CurrencyUnitProvider.class);
		regionProvider = loadService(RegionProvider.class);
		roundingProvider = loadService(RoundingProvider.class);
		amountParserFactory = loadService(AmountParserFactory.class);
		amountFormatterFactory = loadService(AmountFormatterFactory.class);
		currencyParserFactory = loadService(CurrencyParserFactory.class);
		currencyFormatterFactory = loadService(CurrencyFormatterFactory.class);
		exchangeRateProvider = loadService(ExchangeRateProvider.class);
		for (MonetaryAmountFactory t : amountFactoryLoader) {
			this.monetaryAmountFactories.put(t.getNumberClass(), t);
		}
		initDefaultNumberClass();
	}

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
	 * Access the {@link RegionProvider} component.
	 * 
	 * @return the {@link RegionProvider} component, never {@code null}.
	 */
	public static RegionProvider getRegionProvider() {
		if (INSTANCE.regionProvider == null) {
			throw new UnsupportedOperationException("No RegionProvider loaded");
		}
		return INSTANCE.regionProvider;
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
	 * @return the {@link ExchangeRateProvider} component, never {@code null}.
	 */
	public static ExchangeRateProvider getExchangeRateProvider() {
		if (INSTANCE.exchangeRateProvider == null) {
			throw new UnsupportedOperationException(
					"No ExchangeRateProvider loaded");
		}
		return INSTANCE.exchangeRateProvider;
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
}
