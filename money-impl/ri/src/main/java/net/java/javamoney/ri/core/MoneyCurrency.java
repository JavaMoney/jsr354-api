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
 *    Anatole Tresch - initial implementation
 *    Wernner Keil - extensions and adaptions.
 */
package net.java.javamoney.ri.core;

import java.io.Serializable;
import java.util.Collections;
import java.util.Currency;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.money.CurrencyUnit;
import javax.money.LocalizableCurrencyUnit;

/**
 * Adapter that implements the new {@link CurrencyUnit} interface using the
 * JDK's {@link Currency}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MoneyCurrency implements CurrencyUnit, Serializable,
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

	/** namespace for this currency. */
	private final String namespace;
	/** currency code for this currency. */
	private final String currencyCode;
	/** numeric code, or -1. */
	private final int numericCode;
	/** fraction digits, or -1. */
	private final int defaultFractionDigits;
	/** valid from, or {@code null}. */
	private final Long validFrom;
	/** valid until, or {@code null}. */
	private final Long validUntil;
	/** Any additional attributes, or {@code null}. */
	private Map<String, Object> attributes;
	/** true, if legal tender. */
	private final boolean legalTender;
	/** true, if it is a virtual currency. */
	private final boolean virtual;

	private static final Map<String, CurrencyUnit> CACHED = new ConcurrentHashMap<String, CurrencyUnit>();

	private static final Logger LOGGER = Logger.getLogger(MoneyCurrency.class
			.getName());

	/**
	 * Private constructor.
	 * 
	 * @param currency
	 */
	private MoneyCurrency(String namespace, String code, int numCode,
			int fractionDigits, Long validFrom, Long validUntil, boolean legal,
			boolean virtual, Map<String, Object> attributes) {
		this.namespace = namespace;
		this.currencyCode = code;
		this.numericCode = numCode;
		this.defaultFractionDigits = fractionDigits;
		this.validFrom = validFrom;
		this.validUntil = validUntil;
		this.legalTender = legal;
		this.virtual = virtual;
		this.attributes = attributes;
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

	public static CurrencyUnit of(String currencyCode) {
		return of(Currency.getInstance(currencyCode));
	}

	public static CurrencyUnit of(String namespace, String currencyCode) {
		String key = namespace + ':' + currencyCode;
		return CACHED.get(key);
	}

	public boolean isVirtual() {
		return virtual;
	}

	/**
	 * Get the namepsace of this {@link CurrencyUnit}, returns 'ISO-4217'.
	 */
	public String getNamespace() {
		return namespace;
	}

	public Long getValidFrom() {
		return validFrom;
	}

	public Long getValidUntil() {
		return validUntil;
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAttribute(String key, Class<T> type) {
		if (key == null) {
			throw new IllegalArgumentException("key may not b enull.");
		}
		if (type == null) {
			throw new IllegalArgumentException("type may not b enull.");
		}
		if (this.attributes != null) {
			return (T) this.attributes.get(key);
		}
		return null;
	}

	@Override
	public Enumeration<String> getAttributeKeys() {
		if (this.attributes != null) {
			return Collections.enumeration(this.attributes.keySet());
		}
		return Collections.emptyEnumeration();
	}

	@Override
	public Class<?> getAttributeType(String key) {
		if (key == null) {
			throw new IllegalArgumentException("key may not b enull.");
		}
		if (this.attributes != null) {
			Object value = this.attributes.get(key);
			if (value != null) {
				return value.getClass();
			}
		}
		return null;
	}

	@Override
	public boolean isLegalTender() {
		return legalTender;
	}

	public int compareTo(CurrencyUnit currency) {
		int compare = getNamespace().compareTo(currency.getNamespace());
		if (compare == 0) {
			compare = getCurrencyCode().compareTo(currency.getCurrencyCode());
		}
		if (compare == 0) {
			if (validFrom == null && currency.getValidFrom() != null) {
				compare = -1;
			} else if (validFrom != null && currency.getValidFrom() == null) {
				compare = 1;
			} else if (validFrom != null) {
				compare = validFrom.compareTo(currency.getValidFrom());
			}
		}
		if (compare == 0) {
			if (validUntil == null && currency.getValidUntil() != null) {
				compare = -1;
			} else if (validUntil != null && currency.getValidUntil() == null) {
				compare = 1;
			} else if (validUntil != null) {
				compare = validUntil.compareTo(currency.getValidUntil());
			}
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
		if (ISO_NAMESPACE.equals(namespace)) {
			return currencyCode;
		}
		return namespace + ':' + currencyCode;
	}

	public static final class Builder {
		/** namespace for this currency. */
		private String namespace;
		/** currency code for this currency. */
		private String currencyCode;
		/** numeric code, or -1. */
		private int numericCode = -1;
		/** fraction digits, or -1. */
		private int defaultFractionDigits = -1;
		/** valid from, or {@code null}. */
		private Long validFrom;
		/** valid until, or {@code null}. */
		private Long validUntil;
		/** Any additional attributes, or {@code null}. */
		private Map<String, Object> attributes;
		/** true, if legal tender. */
		private boolean legalTender = true;
		/** true for virtual currencies. */
		private boolean virtual = false;

		public Builder() {
		}

		public Builder(String currencyCode) {
			this(ISO_NAMESPACE, currencyCode);
		}

		public Builder(String namespace, String currencyCode) {
			setNamespace(namespace);
			setCurrencyCode(currencyCode);
		}

		public Builder setNamespace(String namespace) {
			if (namespace == null) {
				throw new IllegalArgumentException("namespace may not be null.");
			}
			this.namespace = namespace;
			return this;
		}

		public Builder setCurrencyCode(String currencyCode) {
			if (currencyCode == null) {
				throw new IllegalArgumentException(
						"currencyCode may not be null.");
			}
			this.currencyCode = currencyCode;
			return this;
		}

		public Builder setDefaultFractionDigits(int defaultFractionDigits) {
			if (defaultFractionDigits < -1) {
				throw new IllegalArgumentException(
						"Invalid value for defaultFractionDigits: "
								+ defaultFractionDigits);
			}
			this.defaultFractionDigits = defaultFractionDigits;
			return this;
		}

		public Builder setNumericCode(int numericCode) {
			if (numericCode < -1) {
				throw new IllegalArgumentException(
						"Invalid value for numericCode: " + numericCode);
			}
			this.numericCode = numericCode;
			return this;
		}

		public Builder setValidFrom(Long validFrom) {
			this.validFrom = validFrom;
			return this;
		}

		public Builder setValidUntil(Long validUntil) {
			this.validUntil = validUntil;
			return this;
		}

		public Builder setLegalTender(boolean legalTender) {
			this.legalTender = legalTender;
			return this;
		}

		public Builder setVirtual(boolean virtual) {
			this.virtual = virtual;
			return this;
		}

		public Builder setAttribute(String key, Object value) {
			if (this.attributes == null) {
				this.attributes = new HashMap<String, Object>();
			}
			this.attributes.put(key, value);
			return this;
		}

		public Object removeAttribute(String key) {
			if (this.attributes == null) {
				return null;
			}
			return this.attributes.remove(key);
		}

		public Enumeration<String> getAttributeKeys() {
			if (this.attributes != null) {
				return Collections.enumeration(this.attributes.keySet());
			}
			return Collections.emptyEnumeration();
		}

		public Class<?> getAttributeType(String key) {
			if (key == null) {
				throw new IllegalArgumentException("key may not b enull.");
			}
			if (this.attributes != null) {
				Object value = this.attributes.get(key);
				if (value != null) {
					return value.getClass();
				}
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		public <T> T getAttribute(String key, Class<T> type) {
			if (this.attributes == null) {
				return null;
			}
			return (T) this.attributes.get(key);
		}

		public String getNamespace() {
			return this.namespace;
		}

		public String getCurrencyCode() {
			return this.currencyCode;
		}

		public int getDefaultFractionDigits() {
			return this.defaultFractionDigits;
		}

		public int getNumericCode() {
			return this.numericCode;
		}

		public Long getValidFrom() {
			return this.validFrom;
		}

		public Long getValidUntil() {
			return this.validUntil;
		}

		public boolean isLegalTender() {
			return this.legalTender;
		}

		public boolean isVirtual() {
			return this.virtual;
		}

		public boolean isBuildable() {
			return namespace != null && currencyCode != null;
		}

		public CurrencyUnit build() {
			return build(true);
		}

		public CurrencyUnit build(boolean cache) {
			if (!isBuildable()) {
				throw new IllegalStateException(
						"Can not build CurrencyUnitImpl.");
			}
			if (cache) {
				if (validUntil != null) {
					LOGGER.warning("CurrencyUnit build: Can only cache currencies that have no validity constraints.");
					cache = false;
				}
				if (validFrom != null) {
					if (validFrom.longValue() > System.currentTimeMillis()) {
						LOGGER.warning("CurrencyUnit build: Can only cache currencies that are already valid.");
						cache = false;
					}
				}
			}
			if (cache) {
				String key = namespace + ':' + currencyCode;
				CurrencyUnit current = CACHED.get(key);
				if (current == null) {
					current = new MoneyCurrency(namespace, currencyCode,
							numericCode, defaultFractionDigits, validFrom,
							validUntil, legalTender, virtual, attributes);
					CACHED.put(key, current);
				}
				return current;
			}
			return new MoneyCurrency(namespace, currencyCode, numericCode,
					defaultFractionDigits, validFrom, validUntil, legalTender,
					virtual, attributes);
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
	private final static class JDKCurrencyAdapter implements
			LocalizableCurrencyUnit, Serializable, Comparable<CurrencyUnit> {

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

		public boolean isVirtual() {
			return false;
		}

		/**
		 * Get the namepsace of this {@link CurrencyUnit}, returns 'ISO-4217'.
		 */

		public String getNamespace() {
			return ISO_NAMESPACE;
		}

		public Long getValidFrom() {
			return null;
		}

		public Long getValidUntil() {
			return null;
		}

		public int compareTo(CurrencyUnit currency) {
			int compare = getNamespace().compareTo(currency.getNamespace());
			if (compare == 0) {
				compare = getCurrencyCode().compareTo(
						currency.getCurrencyCode());
			}
			if (compare == 0 && currency.getValidFrom() != null) {
				compare = 1;
			} else if (compare == 0 && currency.getValidUntil() != null) {
				compare = 1;
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

		@Override
		public <T> T getAttribute(String key, Class<T> type) {
			return null;
		}

		@Override
		public Enumeration<String> getAttributeKeys() {
			return Collections.emptyEnumeration();
		}

		@Override
		public Class<?> getAttributeType(String key) {
			return null;
		}

		@Override
		public boolean isLegalTender() {
			if (getCurrencyCode().startsWith("X")) {
				return false;
			}
			return true;
		}

		@Override
		public String getSymbol() {
			return this.currency.getSymbol();
		}

		@Override
		public String getSymbol(Locale locale) {
			return this.currency.getSymbol(locale);
		}

		@Override
		public String getDisplayName() {
			return this.currency.getDisplayName();
		}

		@Override
		public String getDisplayName(Locale locale) {
			return this.currency.getDisplayName(locale);
		}
	}

}
