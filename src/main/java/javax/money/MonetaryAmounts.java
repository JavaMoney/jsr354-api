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
package javax.money;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.bootstrap.Bootstrap;
import javax.money.spi.CurrencyProviderSpi;
import javax.money.spi.MonetaryAmountProviderSpi;

/**
 * Factory singleton for {@link MonetaryAmount} instances as provided by the
 * different registered {@link MonetaryAmountProviderSpi} instances.
 * <p>
 * This singleton allows to get {@link MonetaryAmount} instances depending on
 * the precision and scale requirements. If not defined a default
 * {@link MonetaryContext} is used, which can also be reconfigured by adding a
 * file {@code /javamoney.properties} to the classpath, with the following
 * content:
 * 
 * <pre>
 * # Default MathContext for Money
 * #-------------------------------
 * # Custom MonetaryContext, overrides default entries from 
 * # org.javamoney.moneta.Money.monetaryContext
 * # RoundingMode hereby is optional (default = HALF_EVEN)
 * Money.defaults.precision=256
 * Money.defaults.scale=-1
 * Money.attributes.java.math.RoundingMode=RoundingMode.HALF_EVEN
 * </pre>
 * 
 * @version 0.6.1
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryAmounts {

	/**
	 * The default {@link MonetaryContext} applied, if not set explicitly on
	 * creation.
	 */
	private static final MonetaryContext DEFAULT_MONETARY_CONTEXT = initDefaultMathContext();

	/**
	 * Required for deserialization only.
	 */
	private MonetaryAmounts() {
	}

	/**
	 * Creates a new instance of {@link MonetaryAmount}, using the default
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @return a {@code MonetaryAmount} combining the numeric value and currency
	 *         unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 */
	public static MonetaryAmount<?> getAmount(CurrencyUnit currency, long number) {
		MonetaryAmount<?> amt = null;
		for (MonetaryAmountProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountProviderSpi.class)) {
			try {
				amt = spi.getAmount(currency, number, DEFAULT_MONETARY_CONTEXT);
				if (amt != null) {
					return amt;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryAmounts.class.getName()).log(
						Level.SEVERE,
						"Error loading MonetaryAmount "
								+ amt + "(" + DEFAULT_MONETARY_CONTEXT
								+ ") from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new MonetaryException(
				"Unsupported MonetaryAmount requested: "
						+ amt + "(" + DEFAULT_MONETARY_CONTEXT + ")");
	}

	/**
	 * Creates a new instance of {@link MonetaryAmount}, using the default
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @return a {@code MonetaryAmount} combining the numeric value and currency
	 *         unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 */
	public static MonetaryAmount<?> getAmount(CurrencyUnit currency,
			double number) {
		MonetaryAmount<?> amt = null;
		for (MonetaryAmountProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountProviderSpi.class)) {
			try {
				amt = spi.getAmount(currency, number, DEFAULT_MONETARY_CONTEXT);
				if (amt != null) {
					return amt;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryAmounts.class.getName()).log(
						Level.SEVERE,
						"Error loading MonetaryAmount<?> "
								+ currency.getCurrencyCode() + " " + number
								+ "(" + DEFAULT_MONETARY_CONTEXT
								+ ") from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new MonetaryException(
				"Unsupported MonetaryAmount<?> type requested: "
						+ currency.getCurrencyCode() + " " + number
						+ "(" + DEFAULT_MONETARY_CONTEXT
						+ ")");
	}

	/**
	 * Creates a new instance of {@link MonetaryAmount}, using the default
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @return a {@code MonetaryAmount} combining the numeric value and currency
	 *         unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 */
	public static MonetaryAmount<?> getAmount(CurrencyUnit currency,
			Number number) {
		MonetaryAmount<?> amt = null;
		for (MonetaryAmountProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountProviderSpi.class)) {
			try {
				amt = spi.getAmount(currency, number, DEFAULT_MONETARY_CONTEXT);
				if (amt != null) {
					return amt;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryAmounts.class.getName()).log(
						Level.SEVERE,
						"Error loading MonetaryAmount<?> "
								+ currency.getCurrencyCode() + " " + number
								+ "(" + DEFAULT_MONETARY_CONTEXT
								+ ") from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new MonetaryException(
				"Unsupported MonetaryAmount<?> type requested: "
						+ currency.getCurrencyCode() + " " + number
						+ "(" + DEFAULT_MONETARY_CONTEXT
						+ ")");
	}

	/**
	 * Creates a new instance of {@link MonetaryAmounts}, using the default
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currencyCode
	 *            currency code, not {@code null}.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 */
	public static MonetaryAmount<?> getAmount(String currencyCode, long number) {
		MonetaryAmount<?> amt = null;
		for (MonetaryAmountProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountProviderSpi.class)) {
			try {
				amt = spi.getAmount(
						MonetaryCurrencies.getCurrency(currencyCode), number,
						DEFAULT_MONETARY_CONTEXT);
				if (amt != null) {
					return amt;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryAmounts.class.getName()).log(
						Level.SEVERE,
						"Error loading MonetaryAmount<?> "
								+ currencyCode + " " + number
								+ "(" + DEFAULT_MONETARY_CONTEXT
								+ ") from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new MonetaryException(
				"Unsupported MonetaryAmount<?> type requested: "
						+ currencyCode + " " + number
						+ "(" + DEFAULT_MONETARY_CONTEXT
						+ ")");
	}

	/**
	 * Creates a new instance of {@link MonetaryAmounts}, using the default
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currencyCode
	 *            currency code, not {@code null}.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 */
	public static MonetaryAmount<?> getAmount(String currencyCode, double number) {
		MonetaryAmount<?> amount = null;
		for (MonetaryAmountProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountProviderSpi.class)) {
			try {
				amount = spi.getAmount(
						MonetaryCurrencies.getCurrency(currencyCode), number,
						DEFAULT_MONETARY_CONTEXT);
				if (amount != null) {
					return amount;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryAmounts.class.getName()).log(
						Level.SEVERE,
						"Error loading MonetaryAmount<?> "
								+ currencyCode + " " + number
								+ "(" + DEFAULT_MONETARY_CONTEXT
								+ ") from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new MonetaryException(
				"Unsupported MonetaryAmount<?> type requested: "
						+ currencyCode + " " + number
						+ "(" + DEFAULT_MONETARY_CONTEXT
						+ ")");
	}

	/**
	 * Creates a new instance of {@link MonetaryAmounts}, using the default
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currencyCode
	 *            currency code, not {@code null}.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 */
	public static MonetaryAmount<?> getAmount(String currencyCode, Number number) {
		MonetaryAmount<?> amt = null;
		for (MonetaryAmountProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountProviderSpi.class)) {
			try {
				amt = spi.getAmount(
						MonetaryCurrencies.getCurrency(currencyCode), number,
						DEFAULT_MONETARY_CONTEXT);
				if (amt != null) {
					return amt;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryAmounts.class.getName()).log(
						Level.SEVERE,
						"Error loading MonetaryAmount<?> "
								+ currencyCode + " " + number
								+ "(" + DEFAULT_MONETARY_CONTEXT
								+ ") from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new MonetaryException(
				"Unsupported MonetaryAmount<?> type requested: "
						+ currencyCode + " " + number
						+ "(" + DEFAULT_MONETARY_CONTEXT
						+ ")");
	}

	/**
	 * Creates a new instance of {@link MonetaryAmounts}, using the default
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @param context
	 *            the {@link MonetaryContext} required.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 */
	public static MonetaryAmount<?> getAmount(String currency, long number,
			MonetaryContext context) {
		MonetaryAmount<?> amt = null;
		for (MonetaryAmountProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountProviderSpi.class)) {
			try {
				amt = spi.getAmount(MonetaryCurrencies.getCurrency(currency),
						number, DEFAULT_MONETARY_CONTEXT);
				if (amt != null) {
					return amt;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryAmounts.class.getName()).log(
						Level.SEVERE,
						"Error loading MonetaryAmount<?> "
								+ currency + " " + number
								+ "(" + DEFAULT_MONETARY_CONTEXT
								+ ") from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new MonetaryException(
				"Unsupported MonetaryAmount<?> type requested: "
						+ currency + " " + number
						+ "(" + DEFAULT_MONETARY_CONTEXT
						+ ")");
	}

	/**
	 * Creates a new instance of {@link MonetaryAmounts}, using the default
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currencyCode
	 *            currency unit, not {@code null}.
	 * @param context
	 *            the {@link MonetaryContext} required.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 */
	public static MonetaryAmount<?> getAmount(String currencyCode,
			double number,
			MonetaryContext context) {
		MonetaryAmount<?> amt = null;
		for (MonetaryAmountProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountProviderSpi.class)) {
			try {
				amt = spi.getAmount(
						MonetaryCurrencies.getCurrency(currencyCode), number,
						DEFAULT_MONETARY_CONTEXT);
				if (amt != null) {
					return amt;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryAmounts.class.getName()).log(
						Level.SEVERE,
						"Error loading MonetaryAmount<?> "
								+ currencyCode + " " + number
								+ "(" + DEFAULT_MONETARY_CONTEXT
								+ ") from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new MonetaryException(
				"Unsupported MonetaryAmount<?> type requested: "
						+ currencyCode + " " + number
						+ "(" + DEFAULT_MONETARY_CONTEXT
						+ ")");
	}

	/**
	 * Creates a new instance of {@link MonetaryAmounts}, using the default
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currencyCode
	 *            currency unit, not {@code null}.
	 * @param context
	 *            the {@link MonetaryContext} required.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 */
	public static MonetaryAmount<?> getAmount(String currencyCode,
			Number number,
			MonetaryContext monetaryContext) {
		MonetaryAmount<?> amt = null;
		for (MonetaryAmountProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountProviderSpi.class)) {
			try {
				amt = spi.getAmount(
						MonetaryCurrencies.getCurrency(currencyCode), number,
						DEFAULT_MONETARY_CONTEXT);
				if (amt != null) {
					return amt;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryAmounts.class.getName()).log(
						Level.SEVERE,
						"Error loading MonetaryAmount<?> "
								+ currencyCode + " " + number
								+ "(" + DEFAULT_MONETARY_CONTEXT
								+ ") from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new MonetaryException(
				"Unsupported MonetaryAmount requested: "
						+ amt + "(" + monetaryContext + ")");
	}

	/**
	 * Creates a new instance of {@link MonetaryAmounts}, using an explicit
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, if {@code null} the
	 *            default {@link MonetaryContext} is used.
	 * @return a {@code Money} instance based on the monetary context with the
	 *         given numeric value, currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the
	 *             {@link MonetaryContext} used.
	 */
	public static MonetaryAmount<?> getAmount(CurrencyUnit currency,
			long number,
			MonetaryContext monetaryContext) {
		MonetaryAmount<?> amt = null;
		for (MonetaryAmountProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountProviderSpi.class)) {
			try {
				amt = spi.getAmount(currency, number, monetaryContext);
				if (amt != null) {
					return amt;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryAmounts.class.getName()).log(
						Level.SEVERE,
						"Error loading MonetaryAmount<?> "
								+ currency.getCurrencyCode() + " " + number
								+ "(" + DEFAULT_MONETARY_CONTEXT
								+ ") from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new MonetaryException(
				"Unsupported MonetaryAmount requested: "
						+ amt + "(" + monetaryContext + ")");
	}

	/**
	 * Creates a new instance of {@link MonetaryAmounts}, using an explicit
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, if {@code null} the
	 *            default {@link MonetaryContext} is used.
	 * @return a {@code Money} instance based on the monetary context with the
	 *         given numeric value, currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the
	 *             {@link MonetaryContext} used.
	 */
	public static MonetaryAmount<?> getAmount(CurrencyUnit currency,
			double number,
			MonetaryContext monetaryContext) {
		MonetaryAmount<?> amt = null;
		for (MonetaryAmountProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountProviderSpi.class)) {
			try {
				amt = spi.getAmount(currency, number, monetaryContext);
				if (amt != null) {
					return amt;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryAmounts.class.getName()).log(
						Level.SEVERE,
						"Error loading MonetaryAmount "
								+ amt + "(" + monetaryContext
								+ ") from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new MonetaryException(
				"Unsupported MonetaryAmount requested: "
						+ amt + "(" + monetaryContext + ")");
	}

	/**
	 * Creates a new instance of {@link MonetaryAmounts}, using an explicit
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, if {@code null} the
	 *            default {@link MonetaryContext} is used.
	 * @return a {@code Money} instance based on the monetary context with the
	 *         given numeric value, currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the
	 *             {@link MonetaryContext} used.
	 */
	public static MonetaryAmount<?> getAmount(CurrencyUnit currency,
			Number number,
			MonetaryContext monetaryContext) {
		MonetaryAmount<?> amt = null;
		for (MonetaryAmountProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountProviderSpi.class)) {
			try {
				amt = spi.getAmount(currency, number, monetaryContext);
				if (amt != null) {
					return amt;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryAmounts.class.getName()).log(
						Level.SEVERE,
						"Error loading MonetaryAmount<?> "
								+ currency.getCurrencyCode() + " " + number
								+ "(" + monetaryContext
								+ ") from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new MonetaryException(
				"Unsupported MonetaryAmount<?> type requested: "
						+ currency.getCurrencyCode() + " " + number
						+ "(" + monetaryContext
						+ ")");
	}

/**
	 * Factory method creating a zero instance with the given {@code currency);
	 * @param currency 
	 * 			the target {@link CurrencyUnit} of the {@link MonetaryAmount} being created, not {@code null}.
	 * @return a new Money instance of zero, with a default {@link MonetaryContext}.
	 */
	public static MonetaryAmount<?> getAmountZero(CurrencyUnit currency) {
		return getAmount(currency, BigDecimal.ZERO, DEFAULT_MONETARY_CONTEXT);
	}

/**
	 * Factory method creating a zero instance with the given {@code currencyCode);
	 * @param currencyCode
	 * 			the currency code to determine the {@link CurrencyUnit} of the {@link MonetaryAmount} being created.
	 * @return a new Money instance of zero, with a default {@link MonetaryContext}.
	 */
	public static MonetaryAmount<?> getAmountZero(String currencyCode) {
		return getAmount(MonetaryCurrencies.getCurrency(currencyCode),
				BigDecimal.ZERO,
				DEFAULT_MONETARY_CONTEXT);
	}

/**
	 * Factory method creating a zero instance with the given {@code currency);
	 * @param currency 
	 * 			the target currency of the amount being created, not {@code null}.
	 * @return a new Money instance of zero, with a default {@link MonetaryContext}.
	 */
	public static MonetaryAmount<?> getAmountZero(CurrencyUnit currency,
			MonetaryContext monetaryContext) {
		return getAmount(currency, BigDecimal.ZERO, monetaryContext);
	}

/**
	 * Factory method creating a zero instance with the given {@code currency);
	 * @param currencyCode
	 * 			the target currency code to determine the {@link CurrencyUnit} of the {@link MonetaryAmount} being created.
	 * @return a new {@link MonetaryAmount} instance of zero, with a default {@link MonetaryContext}.
	 */
	public static MonetaryAmount<?> getAmountZero(String currencyCode,
			MonetaryContext monetaryContext) {
		return getAmount(MonetaryCurrencies.getCurrency(currencyCode),
				BigDecimal.ZERO,
				monetaryContext);
	}

	/**
	 * Returns the default {@link MonetaryContext} used, when no
	 * {@link MonetaryContext} is provided.
	 * 
	 * @return the default {@link MonetaryContext}, never {@code null}.
	 */
	public static MonetaryContext getDefaultMonetaryContext() {
		return DEFAULT_MONETARY_CONTEXT;
	}

	/**
	 * Converts (if necessary) the given {@link MonetaryAmount} to a new
	 * {@link MonetaryAmount} instance, hereby supporting the
	 * {@link MonetaryContext} given.
	 * 
	 * @param amt
	 *            the amount to be converted, if necessary.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, if {@code null} the
	 *            default {@link MonetaryContext} is used.
	 * @return an according Money instance.
	 */
	public static MonetaryAmount<?> getAmountFrom(MonetaryAmount<?> amt,
			MonetaryContext monetaryContext) {
		for (MonetaryAmountProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountProviderSpi.class)) {
			try {
				amt = spi.getAmountFrom(amt, monetaryContext);
				if (amt != null) {
					return amt;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryAmounts.class.getName()).log(
						Level.SEVERE,
						"Error loading MonetaryAmount "
								+ amt + "(" + monetaryContext
								+ ") from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new MonetaryException(
				"Unsupported MonetaryAmount requested: "
						+ amt + "(" + monetaryContext + ")");
	}

	// private methods

	/**
	 * Loader method, executed on startup once.
	 * 
	 * @return the {@link CurrencyProviderSpi} loaded.
	 */
	private static Collection<MonetaryAmountProviderSpi> loadMonetaryAmountProviderSpis() {
		List<MonetaryAmountProviderSpi> spis = new ArrayList<MonetaryAmountProviderSpi>();
		try {
			for (MonetaryAmountProviderSpi spi : ServiceLoader
					.load(MonetaryAmountProviderSpi.class)) {
				spis.add(spi);
			}
		} catch (Exception e) {
			Logger.getLogger(MonetaryAmounts.class.getName()).log(
					Level.SEVERE,
					"Error loading CurrencyProviderSpi instances.", e);
			return null;
		}
		Collections.sort(spis, new PriorityComparator());
		return spis;
	}

	/**
	 * Evaluates the default {@link MonetaryContext} to be used for the
	 * {@link MonetaryAmounts} singleton. The default {@link MonetaryContext}
	 * can be configured by adding a file {@code /javamoney.properties} from the
	 * classpath with the following content:
	 * 
	 * <pre>
	 * # Default MathContext for Money
	 * #-------------------------------
	 * # Custom MathContext, overrides entries from org.javamoney.moneta.Money.mathContext
	 * # RoundingMode hereby is optional (default = HALF_EVEN)
	 * Money.defaults.precision=256
	 * Money.defaults.scale=-1
	 * Money.defaults.type=java.math.BigDecimal
	 * Money.attributes.java.math.RoundingMode=RoundingMode.HALF_EVEN
	 * </pre>
	 * 
	 * @TODO implement the concept as outlined above...
	 * @return default MonetaryContext, never {@code null}.
	 */
	private static MonetaryContext initDefaultMathContext() {
		InputStream is = null;
		try {
			Properties props = new Properties();
			URL url = MonetaryAmounts.class
					.getResource("/javamoney.properties");
			if (url != null) {
				is = url
						.openStream();
				props.load(is);
				String value = props
						.getProperty("org.javamoney.moneta.Money.defaults.precision");
				if (value != null) {
					int prec = Integer.parseInt(value);
					value = props
							.getProperty("org.javamoney.moneta.Money.defaults.roundingMode");
					RoundingMode rm = value != null ? RoundingMode
							.valueOf(value
									.toUpperCase(Locale.ENGLISH))
							: RoundingMode.HALF_UP;
					MonetaryContext mc = new MonetaryContext.Builder(
							BigDecimal.class).setPrecision(prec)
							.setAttribute(rm).build();
					Logger.getLogger(MonetaryAmounts.class.getName()).info(
							"Using custom MathContext: precision=" + prec
									+ ", roundingMode=" + rm);
					return mc;
				}
				else {
					MonetaryContext.Builder builder = new MonetaryContext.Builder(
							BigDecimal.class);
					value = props
							.getProperty("org.javamoney.moneta.Money.defaults.mathContext");
					if (value != null) {
						switch (value.toUpperCase(Locale.ENGLISH)) {
						case "DECIMAL32":
							Logger.getLogger(MonetaryAmounts.class.getName())
									.info(
											"Using MathContext.DECIMAL32");
							builder.setAttribute(MathContext.DECIMAL32);
							break;
						case "DECIMAL64":
							Logger.getLogger(MonetaryAmounts.class.getName())
									.info(
											"Using MathContext.DECIMAL64");
							builder.setAttribute(MathContext.DECIMAL64);
							break;
						case "DECIMAL128":
							Logger.getLogger(MonetaryAmounts.class.getName())
									.info(
											"Using MathContext.DECIMAL128");
							builder.setAttribute(MathContext.DECIMAL128);
							break;
						case "UNLIMITED":
							Logger.getLogger(MonetaryAmounts.class.getName())
									.info(
											"Using MathContext.UNLIMITED");
							builder.setAttribute(MathContext.UNLIMITED);
							break;
						}
					}
					return builder.build();
				}
			}
			MonetaryContext.Builder builder = new MonetaryContext.Builder(
					BigDecimal.class);
			Logger.getLogger(MonetaryAmounts.class.getName()).info(
					"Using default MathContext.DECIMAL64");
			builder.setAttribute(MathContext.DECIMAL64);
			return builder.build();
		} catch (Exception e) {
			Logger.getLogger(MonetaryAmounts.class.getName())
					.log(Level.SEVERE,
							"Error evaluating default NumericContext, using default (NumericContext.NUM64).",
							e);
			return new MonetaryContext.Builder(
					BigDecimal.class).setAttribute(MathContext.DECIMAL64)
					.build();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Logger.getLogger(MonetaryAmounts.class.getName())
							.log(Level.WARNING,
									"Error closing InputStream after evaluating default NumericContext.",
									e);
				}
			}
		}
	}

	private static final class PriorityComparator implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) {
			Class class1 = o1.getClass();
			Class class2 = o2.getClass();
			// TODO
			return 0;
		}
	}

}