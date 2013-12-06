/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.spi.CurrencyProviderSpi;

/**
 * Default implementation of a {@link CurrencyUnit} based on the using the JDK's
 * {@link Currencies}, but also extendible using a {@link Builder} instance, or
 * by registering {@link CurrencyProviderSpi} using the {@link ServiceLoader}.
 * 
 * @version 0.8
 * @author Anatole Tresch
 */
public final class Currencies {

	/** Currently loaded SPIs. */
	private static Collection<CurrencyProviderSpi> providerSpis = loadSpis();

	/**
	 * Internal shared cache of {@link CustomCurrency} instances, registered
	 * using {@link Builder} instances.
	 */
	private static final Map<String, CurrencyUnit> REGISTERED = new ConcurrentHashMap<String, CurrencyUnit>();

	/**
	 * Loader method, executed on startup once.
	 * 
	 * @return the {@link CurrencyProviderSpi} loaded.
	 */
	private static Collection<CurrencyProviderSpi> loadSpis() {
		List<CurrencyProviderSpi> spis = new ArrayList<CurrencyProviderSpi>();
		try {
			for (CurrencyProviderSpi spi : ServiceLoader
					.load(CurrencyProviderSpi.class)) {
				spis.add(spi);
			}
		} catch (Exception e) {
			Logger.getLogger(Currencies.class.getName()).log(
					Level.SEVERE,
					"Error loading CurrencyProviderSpi instances.", e);
			return null;
		}
		return spis;
	}

	/**
	 * Private singleton constructor.
	 */
	private Currencies() {
	}

	/**
	 * Access a new instance based on the currency code. Currencies are
	 * available as provided by {@link CurrencyProviderSpi} instances registered
	 * with the {@link ServiceLoader}.
	 * 
	 * @param currencyCode
	 *            the ISO currency code, not {@code null}.
	 * @return the corresponding {@link CurrencyUnit} instance.
	 * @throws IllegalArgumentException
	 *             if no such currency exists.
	 */
	public static CurrencyUnit of(String currencyCode) {
		CurrencyUnit cu = null;
		for (CurrencyProviderSpi spi : providerSpis) {
			try {
				cu = spi.getCurrencyUnit(currencyCode);
				if (cu != null) {
					if (!currencyCode.equals(cu.getCurrencyCode())) {
						throw new IllegalStateException(
								"Provider("
										+ spi.getClass().getName()
										+ ") returned an invalid CurrencyUnit for '"
										+ currencyCode + "': "
										+ cu.getCurrencyCode());
					}
					return cu;
				}
			} catch (Exception e) {
				Logger.getLogger(Currencies.class.getName()).log(
						Level.SEVERE,
						"Error loading Currency '" + currencyCode
								+ "' from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		cu = REGISTERED.get(currencyCode);
		if (cu == null) {
			throw new IllegalArgumentException("No such currency: "
					+ currencyCode);
		}
		return cu;
	}

	/**
	 * Access a new instance based on the currency code. Currencies are
	 * available as provided by {@link CurrencyProviderSpi} instances registered
	 * with the {@link ServiceLoader}.
	 * 
	 * @param currencyCode
	 *            the ISO currency code, not {@code null}.
	 * @param timestamp
	 *            the UTC timestamp of the time, when the {@link CurrencyUnit}
	 *            should be valid.
	 * @return the corresponding {@link CurrencyUnit} instance.
	 * @throws IllegalArgumentException
	 *             if no such currency exists.
	 */
	public static CurrencyUnit of(String currencyCode, long timestamp) {
		CurrencyUnit cu = null;
		for (CurrencyProviderSpi spi : providerSpis) {
			try {
				cu = spi.getCurrencyUnit(currencyCode, timestamp);
				if (cu != null) {
					if (!currencyCode.equals(cu.getCurrencyCode())) {
						throw new IllegalStateException(
								"Provider("
										+ spi.getClass().getName()
										+ ") returned an invalid CurrencyUnit for '"
										+ currencyCode + "': "
										+ cu.getCurrencyCode());
					}
					return cu;
				}
			} catch (Exception e) {
				Logger.getLogger(Currencies.class.getName()).log(
						Level.SEVERE,
						"Error loading Currency '" + currencyCode
								+ "'/ts=" + timestamp + " from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new IllegalArgumentException("No such currency: "
				+ currencyCode);
	}

	/**
	 * Access a new instance based on the {@link Locale}. Currencies are
	 * available as provided by {@link CurrencyProviderSpi} instances registered
	 * with the {@link ServiceLoader}.
	 * 
	 * @param currencyCode
	 *            the ISO currency code, not {@code null}.
	 * @return the corresponding {@link CurrencyUnit} instance.
	 * @throws IllegalArgumentException
	 *             if no such currency exists.
	 */
	public static CurrencyUnit of(Locale locale) {
		CurrencyUnit cu = null;
		for (CurrencyProviderSpi spi : providerSpis) {
			try {
				cu = spi.getCurrencyUnit(locale);
				if (cu != null) {
					return cu;
				}
			} catch (Exception e) {
				Logger.getLogger(Currencies.class.getName()).log(
						Level.SEVERE,
						"Error loading Currency for Locale '" + locale
								+ " from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new IllegalArgumentException("No such currency: "
				+ locale);
	}

	/**
	 * Access a new instance based on the {@link Locale}. Currencies are
	 * available as provided by {@link CurrencyProviderSpi} instances registered
	 * with the {@link ServiceLoader}.
	 * 
	 * @param currencyCode
	 *            the ISO currency code, not {@code null}.
	 * @param timestamp
	 *            the UTC timestamp of the time, when the {@link CurrencyUnit}
	 *            should be valid.
	 * @return the corresponding {@link CurrencyUnit} instance.
	 * @throws IllegalArgumentException
	 *             if no such currency exists.
	 */
	public static CurrencyUnit of(Locale locale, long timestamp) {
		CurrencyUnit cu = null;
		for (CurrencyProviderSpi spi : providerSpis) {
			try {
				cu = spi.getCurrencyUnit(locale, timestamp);
				if (cu != null) {
					return cu;
				}
			} catch (Exception e) {
				Logger.getLogger(Currencies.class.getName()).log(
						Level.SEVERE,
						"Error loading Currency for Locale '" + locale
								+ "'/ts=" + timestamp + " from provider: "
								+ spi.getClass().getName(), e);
			}
		}
		throw new IllegalArgumentException("No such currency: "
				+ locale);
	}

	/**
	 * Allows to check if a {@link Currencies} instance is defined, i.e.
	 * accessible from {@link Currencies#of(String)}.
	 * 
	 * @param code
	 *            the currency code, not {@code null}.
	 * @return {@code true} if {@link Currencies#of(String)} would return a
	 *         result for the given code.
	 */
	public static boolean isAvailable(String code) {
		try {
			of(code);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Allows to check if a {@link Currencies} instance is defined, i.e.
	 * accessible from {@link Currencies#of(String)}.
	 * 
	 * @param code
	 *            the currency code, not {@code null}.
	 * @param timestamp
	 *            the UTC timestamp of the time, when the {@link CurrencyUnit}
	 *            should be valid.
	 * @return {@code true} if {@link Currencies#of(String)} would return a
	 *         result for the given code.
	 */
	public static boolean isAvailable(String code, long timestamp) {
		try {
			of(code, timestamp);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Allows to check if a {@link Currencies} instance is defined, i.e.
	 * accessible from {@link Currencies#of(String)}.
	 * 
	 * @param code
	 *            the currency code, not {@code null}.
	 * @return {@code true} if {@link Currencies#of(String)} would return a
	 *         result for the given code.
	 */
	public static boolean isAvailable(Locale locale) {
		try {
			of(locale);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Allows to check if a {@link Currencies} instance is defined, i.e.
	 * accessible from {@link Currencies#of(String)}.
	 * 
	 * @param code
	 *            the currency code, not {@code null}.
	 * @param timestamp
	 *            the UTC timestamp of the time, when the {@link CurrencyUnit}
	 *            should be valid.
	 * @return {@code true} if {@link Currencies#of(String)} would return a
	 *         result for the given code.
	 */
	public static boolean isAvailable(Locale locale, long timestamp) {
		try {
			of(locale, timestamp);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Builder class that supports building and registering instances of
	 * {@link Currencies} programmatically.
	 * 
	 * @author Anatole Tresch
	 */
	public static final class Builder {
		/** Currency code for the currency built. */
		private String currencyCode;
		/** Numeric code, or -1. */
		private int numericCode = -1;
		/** Default fraction digits, or -1. */
		private int defaultFractionDigits = -1;

		/**
		 * Creates a new {@link Builder}.
		 */
		public Builder() {
		}

		/**
		 * Set the currency code.
		 * 
		 * @see CurrencyUnit#getCurrencyCode()
		 * @param currencyCode
		 *            the currency code, not {@code null}
		 * @return the builder, for chaining
		 */
		public Builder setCurrencyCode(String currencyCode) {
			if (currencyCode == null) {
				throw new IllegalArgumentException(
						"currencyCode may not be null.");
			}
			this.currencyCode = currencyCode;
			return this;
		}

		/**
		 * Set the default fraction digits.
		 * 
		 * @see CurrencyUnit#getDefaultFractionDigits()
		 * @param defaultFractionDigits
		 *            the default fraction digits, >= -1;
		 * @return the builder, for chaining
		 */
		public Builder setDefaultFractionDigits(int defaultFractionDigits) {
			if (defaultFractionDigits < -1) {
				throw new IllegalArgumentException(
						"Invalid value for defaultFractionDigits: "
								+ defaultFractionDigits);
			}
			this.defaultFractionDigits = defaultFractionDigits;
			return this;
		}

		/**
		 * Set the numeric currency code.
		 * 
		 * @see CurrencyUnit#getNumericCode()
		 * @param numericCode
		 *            the numeric currency code, >= -1
		 * @return the builder, for chaining
		 */
		public Builder setNumericCode(int numericCode) {
			if (numericCode < -1) {
				throw new IllegalArgumentException(
						"Invalid value for numericCode: " + numericCode);
			}
			this.numericCode = numericCode;
			return this;
		}

		/**
		 * Builds a new {@link Currencies} instance, the instance build is not
		 * cached internally.
		 * 
		 * @see {@link Currencies#of(String)}
		 * @see #build(boolean)
		 * @return a new instance of {@link Currencies}.
		 */
		public CurrencyUnit build() {
			return build(false);
		}

		/**
		 * Builds a new {@link Currencies} instance, the instance build is not
		 * cached internally.
		 * 
		 * @param register
		 *            if {@code true} the created {@link CurrencyUnit} is
		 *            registered into the shared cache.
		 * @see {@link Currencies#of(String)}
		 * @see #build(boolean)
		 * @return a new instance of {@link Currencies}.
		 * @throws IllegalStateException
		 *             if on registration, an corresponding instance is already
		 *             provided, or registered.
		 */
		public CurrencyUnit build(boolean register) {
			CurrencyUnit unit = new CustomCurrency(currencyCode, numericCode,
					defaultFractionDigits);
			if (register) {
				if (!isAvailable(unit.getCurrencyCode())) {
					CurrencyUnit regUnit = REGISTERED.get(unit
							.getCurrencyCode());
					if (regUnit != null) {
						throw new IllegalStateException(
								"Registrytion of CurrencyUnit failed - already registered: "
										+ regUnit);
					}
					REGISTERED.put(unit.getCurrencyCode(), unit);
				}
				else {
					throw new IllegalStateException(
							"Registrytion of CurrencyUnit failed"
									+ " - already provided: "
									+ unit);
				}
			}
			return unit;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "CustomCurrency.Builder [currencyCode=" + currencyCode
					+ ", numericCode="
					+ numericCode + ", defaultFractionDigits="
					+ defaultFractionDigits + "]";
		}

	}
}
