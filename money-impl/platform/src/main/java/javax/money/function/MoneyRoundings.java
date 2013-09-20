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
 */
package javax.money.function;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.ServiceLoader;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;
import javax.money.MonetaryOperator;
import javax.money.spi.RoundingProviderSpi;

import org.slf4j.LoggerFactory;

/**
 * This class models the accessor for rounding instances, modeled by
 * {@link MonetaryOperator}.
 * <p>
 * This class is thread-safe.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MoneyRoundings {
	/**
	 * An adaptive rounding instance that transparently looks up the correct
	 * rounding.
	 */
	private static final MonetaryOperator DEFAULT_ROUNDING = new DefaultCurrencyRounding();
	/**
	 * The internal fallback provider, if no registered
	 * {@link RoundingProviderSpi} could return a rounding.
	 */
	private static DefaultRoundingProvider defaultProvider = new DefaultRoundingProvider();
	/** Currently loaded SPIs. */
	private static ServiceLoader<RoundingProviderSpi> providerSpis = loadSpis();

	/**
	 * Private singleton constructor.
	 */
	private MoneyRoundings() {
		// Singleton
	}

	private static ServiceLoader<RoundingProviderSpi> loadSpis() {
		try {
			return ServiceLoader.load(RoundingProviderSpi.class);
		} catch (Exception e) {
			LoggerFactory.getLogger(MoneyRoundings.class).error(
					"Error loading RoundingProviderSpi instances.", e);
			return null;
		}
	}

	/**
	 * Creates a rounding that can be added as {@link MonetaryFunction} to
	 * chained calculations. The instance will lookup the concrete
	 * {@link MonetaryOperator} instance from the {@link MoneyRoundings} based
	 * on the input {@link MonetaryAmount}'s {@link CurrencyUnit}.
	 * 
	 * @return the (shared) default rounding instance.
	 */
	public MonetaryOperator getRounding() {
		return DEFAULT_ROUNDING;
	}

	/**
	 * Creates an rounding instance.
	 * 
	 * @param mathContext
	 *            The {@link MathContext} to be used, not {@code null}.
	 */
	public static MonetaryOperator getRounding(int scale,
			RoundingMode roundingMode) {
		return new DefaultRounding(scale, roundingMode);
	}

	/**
	 * Creates an {@link MonetaryOperator} for rounding {@link MonetaryAmount}
	 * instances given a currency.
	 * 
	 * @param currency
	 *            The currency, which determines the required precision. As
	 *            {@link RoundingMode}, by default, {@link RoundingMode#HALF_UP}
	 *            is sued.
	 * @return a new instance {@link MonetaryOperator} implementing the
	 *         rounding, never {@code null}.
	 */
	public static MonetaryOperator getRounding(CurrencyUnit currency) {
		MonetaryOperator op = null;
		for (RoundingProviderSpi prov : providerSpis) {
			try {
				op = prov.getRounding(currency);
			} catch (Exception e) {
				LoggerFactory.getLogger(MoneyRoundings.class).error(
						"Error loading RoundingProviderSpi from ptovider: "
								+ prov, e);
			}
		}
		if (op == null) {
			op = defaultProvider.getRounding(currency);
		}
		return op;
	}

	/**
	 * Creates an {@link MonetaryOperator} for rounding {@link MonetaryAmount}
	 * instances given a currency.
	 * 
	 * @param currency
	 *            The currency, which determines the required precision. As
	 *            {@link RoundingMode}, by default, {@link RoundingMode#HALF_UP}
	 *            is sued.
	 * @return a new instance {@link MonetaryOperator} implementing the
	 *         rounding, never {@code null}.
	 */
	public static MonetaryOperator getCashRounding(CurrencyUnit currency) {
		MonetaryOperator op = null;
		for (RoundingProviderSpi prov : providerSpis) {
			try {
				op = prov.getCashRounding(currency);
				if (op != null) {
					break;
				}
			} catch (Exception e) {
				LoggerFactory.getLogger(MoneyRoundings.class).error(
						"Error loading RoundingProviderSpi from ptovider: "
								+ prov, e);
			}
		}
		if (op == null) {
			op = defaultProvider.getCashRounding(currency);
		}
		return op;
	}

	/**
	 * Creates an {@link MonetaryOperator} for rounding {@link MonetaryAmount}
	 * instances given a currency, hereby the rounding must be valid for the
	 * given timestamp.
	 * 
	 * @param currency
	 *            The currency, which determines the required precision. As
	 *            {@link RoundingMode}, by default, {@link RoundingMode#HALF_UP}
	 *            is used.
	 * @param timestamp
	 *            the UTC timestamp.
	 * @return a new instance {@link MonetaryOperator} implementing the
	 *         rounding, or {@code null}.
	 */
	public static MonetaryOperator getRounding(CurrencyUnit currency,
			long timestamp) {
		MonetaryOperator op = null;
		for (RoundingProviderSpi prov : providerSpis) {
			try {
				op = prov.getRounding(currency, timestamp);
				if (op != null) {
					break;
				}
			} catch (Exception e) {
				LoggerFactory.getLogger(MoneyRoundings.class).error(
						"Error loading RoundingProviderSpi from ptovider: "
								+ prov, e);
			}
		}
		if (op == null) {
			op = defaultProvider.getRounding(currency, timestamp);
		}
		return op;
	}

	/**
	 * Creates an {@link MonetaryOperator} for rounding {@link MonetaryAmount}
	 * instances given a currency, hereby the rounding must be valid for the
	 * given timestamp.
	 * 
	 * @param currency
	 *            The currency, which determines the required precision. As
	 *            {@link RoundingMode}, by default, {@link RoundingMode#HALF_UP}
	 *            is sued.
	 * @param timestamp
	 *            the UTC timestamp.
	 * @return a new instance {@link MonetaryOperator} implementing the
	 *         rounding, or {@code null}.
	 */
	public static MonetaryOperator getCashRounding(CurrencyUnit currency,
			long timestamp) {
		MonetaryOperator op = null;
		for (RoundingProviderSpi prov : providerSpis) {
			try {
				op = prov.getCashRounding(currency, timestamp);
				if (op != null) {
					break;
				}
			} catch (Exception e) {
				LoggerFactory.getLogger(MoneyRoundings.class).error(
						"Error loading RoundingProviderSpi from ptovider: "
								+ prov, e);
			}
		}
		if (op == null) {
			op = defaultProvider.getCashRounding(currency, timestamp);
		}
		return op;
	}

	/**
	 * Platform RI: Default Rounding that rounds a {@link MonetaryAmount} based
	 * on tis {@link Currency}.
	 * 
	 * @author Anatole Tresch
	 */
	private static final class DefaultCurrencyRounding implements
			MonetaryOperator {

		@Override
		public MonetaryAmount apply(MonetaryAmount amount) {
			MonetaryOperator r = MoneyRoundings.getRounding(amount
					.getCurrency());
			return r.apply(amount);
		}

	}

	private static final class DefaultRoundingProvider implements
			RoundingProviderSpi {

		@Override
		public MonetaryOperator getRounding(CurrencyUnit currency) {
			return new DefaultRounding(currency);
		}

		@Override
		public MonetaryOperator getRounding(CurrencyUnit currency,
				long timestamp) {
			return null;
		}

		@Override
		public MonetaryOperator getCashRounding(CurrencyUnit currency) {
			return new DefaultCashRounding(currency);
		}

		@Override
		public MonetaryOperator getCashRounding(CurrencyUnit currency,
				long timestamp) {
			return null;
		}

	}
}
