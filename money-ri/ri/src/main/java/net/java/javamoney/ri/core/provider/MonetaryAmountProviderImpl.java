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
 *    Anatole Tresch - initial implementation.
 */
package net.java.javamoney.ri.core.provider;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;
import javax.money.provider.Monetary;
import javax.money.provider.MonetaryAmountFactory;
import javax.money.provider.MonetaryAmountProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MonetaryAmountProviderImpl implements MonetaryAmountProvider {

	private Class<?> defaultNumberClass;

	private Map<Class<?>, MonetaryAmountFactory> factories = new ConcurrentHashMap<Class<?>, MonetaryAmountFactory>();

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Monetary.class);

	@SuppressWarnings("unchecked")
	public MonetaryAmountProviderImpl() {
		for (MonetaryAmountFactory fact : Monetary.getLoader()
				.getInstances(MonetaryAmountFactory.class)) {
			this.factories.put(fact.getNumberClass(), fact);
		}
		initDefaultNumberClass();
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
			} catch (ClassNotFoundException e) {
				LOGGER.error("Failed to initialize default number class to "
						+ defaultClassName, e);
			}
		}
		if (defaultNumberClass == null) {
			defaultNumberClass = BigDecimal.class;
		}
	}

	@Override
	public Enumeration<Class<?>> getSupportedNumberClasses() {
		return Collections.enumeration(factories.keySet());
	}

	@Override
	public boolean isNumberClassSupported(Class<?> numberClass) {
		return factories.containsKey(numberClass);
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
	@Override
	public Class<?> getDefaultNumberClass() {
		return defaultNumberClass;
	}

	@Override
	public MonetaryAmountFactory getMonetaryAmountFactory(Class<?> numberClass) {
		MonetaryAmountFactory fact = this.factories.get(numberClass);
		if (fact == null) {
			throw new IllegalArgumentException("Unsupported number type: "
					+ numberClass);
		}
		return fact;
	}

	@Override
	public MonetaryAmountFactory getMonetaryAmountFactory() {
		return getMonetaryAmountFactory(getDefaultNumberClass());
	}

}
