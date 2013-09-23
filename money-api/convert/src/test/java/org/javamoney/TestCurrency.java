/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package org.javamoney;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.money.CurrencyUnit;

/**
 * Adapter that implements the new {@link CurrencyUnit} interface using the
 * JDK's {@link Currency}.
 * 
 * @version 0.5
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class TestCurrency implements CurrencyUnit, Serializable,
		Comparable<CurrencyUnit> {

	/**
	 * The predefined name space for ISO 4217 currencies, similar to
	 * {@link Currency}.
	 */
	public static final String ISO_NAMESPACE = "ISO-4217";

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2523936311372374236L;

	/** currency code for this currency. */
	private final String currencyCode;
	/** numeric code, or -1. */
	private final int numericCode;
	/** fraction digits, or -1. */
	private final int defaultFractionDigits;

	private static final Map<String, CurrencyUnit> CACHED = new ConcurrentHashMap<String, CurrencyUnit>();

	private static final Logger LOGGER = Logger.getLogger(TestCurrency.class
			.getName());

	/**
	 * Private constructor.
	 * 
	 * @param currency
	 */
	private TestCurrency(String code, int numCode,
			int fractionDigits) {
		this.currencyCode = code;
		this.numericCode = numCode;
		this.defaultFractionDigits = fractionDigits;
	}

	public static CurrencyUnit of(Currency currency) {
		String key = ISO_NAMESPACE + ':' + currency.getCurrencyCode();
		CurrencyUnit cachedItem = CACHED.get(key);
		if (cachedItem == null) {
			cachedItem = new JDKCurrencyAdapter(currency);
			CACHED.put(key, cachedItem);
		}
		return cachedItem;
	}


	public static CurrencyUnit of(
			String currencyCode) {
		CurrencyUnit cu = CACHED.get(currencyCode);
		if (cu == null) {
			Currency cur = Currency.getInstance(currencyCode);
			if(cur!=null){
				return of(cur);
			}
		}
		return cu;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public int getNumericCode() {
		return numericCode;
	}

	public int getDefaultFractionDigits() {
		return defaultFractionDigits;
	}

	public int getCashRounding() {
		throw new UnsupportedOperationException("Not supported yet."); // To
		// change
		// body
		// of
		// generated
		// methods,
		// choose
		// Tools
		// |
		// Templates.
	}

	public int compareTo(CurrencyUnit currency) {
		return getCurrencyCode().compareTo(currency.getCurrencyCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return currencyCode;
	}

	public static final class Builder {
		/** currency code for this currency. */
		private String currencyCode;
		/** numeric code, or -1. */
		private int numericCode = -1;
		/** fraction digits, or -1. */
		private int defaultFractionDigits = -1;

		public Builder() {
		}

		public Builder(String currencyCode) {
			withCurrencyCode(currencyCode);
		}

		public Builder withCurrencyCode(String currencyCode) {
			if (currencyCode == null) {
				throw new IllegalArgumentException(
						"currencyCode may not be null.");
			}
			this.currencyCode = currencyCode;
			return this;
		}

		public Builder withDefaultFractionDigits(int defaultFractionDigits) {
			if (defaultFractionDigits < -1) {
				throw new IllegalArgumentException(
						"Invalid value for defaultFractionDigits: "
								+ defaultFractionDigits);
			}
			this.defaultFractionDigits = defaultFractionDigits;
			return this;
		}

		public Builder withNumericCode(int numericCode) {
			if (numericCode < -1) {
				throw new IllegalArgumentException(
						"Invalid value for numericCode: " + numericCode);
			}
			this.numericCode = numericCode;
			return this;
		}


		public CurrencyUnit build() {
			return build(true);
		}

		public CurrencyUnit build(boolean cache) {
			if (currencyCode == null || currencyCode.isEmpty()) {
				throw new IllegalStateException(
						"Can not build TestCurrencyUnit.");
			}
			if (cache) {
				String key = currencyCode;
				CurrencyUnit current = CACHED.get(key);
				if (current == null) {
					current = new TestCurrency(
							currencyCode,
							numericCode, defaultFractionDigits);
					CACHED.put(key, current);
				}
				return current;
			}
			return new TestCurrency(
					currencyCode, numericCode,
					defaultFractionDigits);
		}
	}

	/**
	 * Adapter that implements the new {@link CurrencyUnit} interface using the
	 * JDK's {@link Currency}.
	 * <p>
	 * This adapter will be removed in the final platform implementation.
	 * 
	 * @author Anatole Tresch
	 * @author Werner Keil
	 */
	private final static class JDKCurrencyAdapter implements CurrencyUnit,
			Serializable,
			Comparable<CurrencyUnit> {

		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = -2523936311372374236L;

		/**
		 * ISO 4217 currency code for this currency.
		 * 
		 * @serial
		 */
		private final Currency currency;

		/**
		 * Private constructor.
		 * 
		 * @param currency
		 */
		private JDKCurrencyAdapter(Currency currency) {
			if (currency == null) {
				throw new IllegalArgumentException("Currency required.");
			}
			this.currency = currency;
		}

		// public Long getValidFrom() {
		// return null;
		// }
		//
		// public Long getValidUntil() {
		// return null;
		// }

		public int compareTo(CurrencyUnit currency) {
			int compare = getCurrencyCode().compareTo(
					currency.getCurrencyCode());
			if (compare == 0) {
				compare = getCurrencyCode().compareTo(
						currency.getCurrencyCode());
			}
			return compare;
		}

		public String getCurrencyCode() {
			return this.currency.getCurrencyCode();
		}

		public int getNumericCode() {
			return this.currency.getNumericCode();
		}

		public int getDefaultFractionDigits() {
			return this.currency.getDefaultFractionDigits();
		}

		public String toString() {
			return this.currency.toString();
		}

		public String getDisplayName(Locale locale) {
			return this.currency.getDisplayName(locale);
		}

		public int getCashRounding() {
			throw new UnsupportedOperationException("Not supported yet."); // To
			// change
			// body
			// of
			// generated
			// methods,
			// choose
			// Tools
			// |
			// Templates.
		}
	}

	// it be used and if so,
	// consider changing it to a pattern similar as getAvailableCurrencies()
	// (including the type Set, unless Collection provides value)
	public static Collection<CurrencyUnit> getAllMatching(
			String expression) {
		return Collections.emptySet();
	}

}
