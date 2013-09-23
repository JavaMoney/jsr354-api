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

import java.io.Serializable;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Adapter that implements the new {@link CurrencyUnit} interface using the
 * JDK's {@link Currency}.
 * 
 * @version 0.5.1
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class MoneyCurrency implements CurrencyUnit, Serializable,
		Comparable<CurrencyUnit> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2523936311372374236L;

	/** {@link CurrencyNamespace} for this currency. */
	private CurrencyNamespace namespace;
	/** currency code for this currency. */
	private String currencyCode;
	/** numeric code, or -1. */
	private int numericCode;
	/** fraction digits, or -1. */
	private int defaultFractionDigits;
	/** The cache rounding value, -1 if not defined. */
	private int cacheRounding = -1;

	private static final Map<String, MoneyCurrency> CACHED = new ConcurrentHashMap<String, MoneyCurrency>();

	/**
	 * Private constructor.
	 * 
	 * @param currency
	 */
	private MoneyCurrency(CurrencyNamespace namespace, String code,
			int numCode,
			int fractionDigits) {
		this.namespace = namespace;
		this.currencyCode = code;
		this.numericCode = numCode;
		this.defaultFractionDigits = fractionDigits;
	}

	/**
	 * Private constructor.
	 * 
	 * @param currency
	 */
	private MoneyCurrency(Currency currency) {
		if (currency == null) {
			throw new IllegalArgumentException("Currency required.");
		}
		this.namespace = CurrencyNamespace.ISO_NAMESPACE;
		this.currencyCode = currency.getCurrencyCode();
		this.numericCode = currency.getNumericCode();
		this.defaultFractionDigits = currency.getDefaultFractionDigits();
	}

	/**
	 * Access a new instance based on {@link Currency}.
	 * 
	 * @param currency
	 *            the currency unit not null.
	 * @return the new instance, never null.
	 */
	public static MoneyCurrency of(Currency currency) {
		String key = CurrencyNamespace.ISO_NAMESPACE.getId() + ':'
				+ currency.getCurrencyCode();
		MoneyCurrency cachedItem = CACHED.get(key);
		if (cachedItem == null) {
			cachedItem = new JDKCurrencyAdapter(currency);
			CACHED.put(key, cachedItem);
		}
		return cachedItem;
	}

	/**
	 * Access a new instance based on the ISO currency code. The code must
	 * return a {@link Currency} when passed to
	 * {@link Currency#getInstance(String)}.
	 * 
	 * @param currencyCode
	 *            the ISO currency code, not null.
	 * @return the corresponding {@link MonetaryCurrency} instance.
	 */
	public static MoneyCurrency of(String currencyCode) {
		return of(Currency.getInstance(currencyCode));
	}

	/**
	 * Access a new instance based on the ISO currency code. The code must
	 * return a {@link Currency} when passed to
	 * {@link Currency#getInstance(String)}.
	 * 
	 * @param namespace
	 *            the target namespace.
	 * @param currencyCode
	 *            the ISO currency code, not null.
	 * @return the corresponding {@link MonetaryCurrency} instance.
	 */
	public static MoneyCurrency of(CurrencyNamespace namespace,
			String currencyCode) {
		String key = namespace.getId() + ':' + currencyCode;
		MoneyCurrency cu = CACHED.get(key);
		if (cu == null
				&& namespace.getId().equals(CurrencyNamespace.ISO_NAMESPACE)) {
			return of(currencyCode);
		}
		return cu;
	}

	/**
	 * Access a new instance based on the ISO currency code. The code must
	 * return a {@link Currency} when passed to
	 * {@link Currency#getInstance(String)}.
	 * 
	 * @param namespace
	 *            the target namespace id, must be resolvable by
	 *            {@link CurrencyNamespace#of(String)}.
	 * @param currencyCode
	 *            the ISO currency code, not null.
	 * @return the corresponding {@link MonetaryCurrency} instance.
	 * @throws IllegalArgumentException
	 *             if the {@link CurrencyNamespace} is not defined.
	 */
	public static CurrencyUnit of(String namespaceId, String currencyCode) {
		return of(CurrencyNamespace.of(namespaceId), currencyCode);
	}

	/**
	 * Get the namepsace of this {@link CurrencyUnit}, returns 'ISO-4217'.
	 */
	public CurrencyNamespace getNamespace() {
		return namespace;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnit#getCurrencyCode()
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnit#getNumericCode()
	 */
	public int getNumericCode() {
		return numericCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnit#getDefaultFractionDigits()
	 */
	public int getDefaultFractionDigits() {
		return defaultFractionDigits;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnit#getCashRounding()
	 */
	@Override
	public int getCashRounding() {
		return cacheRounding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(CurrencyUnit currency) {
		int compare = getNamespace().compareTo(currency.getNamespace());
		if (compare == 0) {
			compare = getCurrencyCode().compareTo(currency.getCurrencyCode());
		}
		return compare;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (CurrencyNamespace.ISO_NAMESPACE.equals(namespace.getId())) {
			return currencyCode;
		}
		return namespace.getId() + ':' + currencyCode;
	}

	/**
	 * Platform RI: Builder class that supports building complex instances of
	 * {@link MoneyCurrency}.
	 * 
	 * @author Anatole Tresch
	 */
	public static final class Builder {
		/** namespace for this currency. */
		private CurrencyNamespace namespace;
		/** currency code for this currency. */
		private String currencyCode;
		/** numeric code, or -1. */
		private int numericCode = -1;
		/** fraction digits, or -1. */
		private int defaultFractionDigits = -1;
		/** Cache rounding. */
		private int cacheRounding = -1;

		/**
		 * Creates a new {@link Builder}.
		 */
		public Builder() {
		}

		/**
		 * Set the namespace.
		 * 
		 * @param namespace
		 *            the namespace, not null
		 * @return the builder, for chaining
		 */
		public Builder withNamespace(CurrencyNamespace namespace) {
			if (namespace == null) {
				throw new IllegalArgumentException("namespace may not be null.");
			}
			this.namespace = namespace;
			return this;
		}

		/**
		 * Set the namespace, using the namespace id.
		 * 
		 * @see CurrencyNamespace#of(String)
		 * 
		 * @param namespace
		 *            the namespace, not null
		 * @return the builder, for chaining
		 */
		public Builder withNamespace(String namespace) {
			if (namespace == null) {
				throw new IllegalArgumentException("namespace may not be null.");
			}
			this.namespace = CurrencyNamespace.of(namespace);
			return this;
		}

		/**
		 * Set the currency code.
		 * 
		 * @param namespace
		 *            the currency code, not null
		 * @return the builder, for chaining
		 */
		public Builder withCurrencyCode(String currencyCode) {
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
		 * @param defaultFractionDigits
		 *            the default fraction digits
		 * @return the builder, for chaining
		 */
		public Builder withDefaultFractionDigits(int defaultFractionDigits) {
			if (defaultFractionDigits < -1) {
				throw new IllegalArgumentException(
						"Invalid value for defaultFractionDigits: "
								+ defaultFractionDigits);
			}
			this.defaultFractionDigits = defaultFractionDigits;
			return this;
		}

		/**
		 * Set the default fraction digits.
		 * 
		 * @param defaultFractionDigits
		 *            the default fraction digits
		 * @return the builder, for chaining
		 */
		public Builder withCashRounding(int cacheRounding) {
			if (cacheRounding < -1) {
				throw new IllegalArgumentException(
						"Invalid value for cacheRounding: " + cacheRounding);
			}
			this.cacheRounding = cacheRounding;
			return this;
		}

		/**
		 * Set the numeric currency code.
		 * 
		 * @param numericCode
		 *            the numeric currency code
		 * @return the builder, for chaining
		 */
		public Builder withNumericCode(int numericCode) {
			if (numericCode < -1) {
				throw new IllegalArgumentException(
						"Invalid value for numericCode: " + numericCode);
			}
			this.numericCode = numericCode;
			return this;
		}

		/**
		 * Builds a new currency instance, the instance build is not cached
		 * internally.
		 * 
		 * @see #build(boolean)
		 * @return a new instance of {@link MoneyCurrency}.
		 */
		public MoneyCurrency build() {
			return build(true);
		}

		/**
		 * Builds a new currency instance, which ia additinoally stored to the
		 * internal cache for reuse.
		 * 
		 * @param cache
		 *            flag to optionally store the instance created into the
		 *            locale cache.
		 * @return a new instance of {@link MoneyCurrency}.
		 */
		public MoneyCurrency build(boolean cache) {
			if (namespace == null) {
				throw new IllegalArgumentException("namespace null.");
			}
			if (cache) {
				String key = namespace.getId() + ':' + currencyCode;
				MoneyCurrency current = CACHED.get(key);
				if (current == null) {
					current = new MoneyCurrency(namespace, currencyCode,
							numericCode, defaultFractionDigits);
					CACHED.put(key, current);
				}
				return current;
			}
			return new MoneyCurrency(namespace, currencyCode, numericCode,
					defaultFractionDigits);
		}
	}

	/**
	 * Platform RI: Adapter that implements the new {@link CurrencyUnit}
	 * interface using the JDK's {@link Currency}.
	 * <p>
	 * This adapter will be removed in the final platform implementation.
	 * 
	 * @author Anatole Tresch
	 * @author Werner Keil
	 */
	private final static class JDKCurrencyAdapter extends MoneyCurrency
			implements Displayable {

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
			super(currency);
			this.currency = currency;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.Displayable#getDisplayName(java.util.Locale)
		 */
		public String getDisplayName(Locale locale) {
			return currency.getDisplayName(locale);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return CurrencyNamespace.ISO_NAMESPACE.getId() + ':'
					+ getCurrencyCode();
		}

	}

}
