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

import java.io.Serializable;
import java.util.Collections;
import java.util.Currency;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Adapter that implements the new {@link CurrencyUnit} interface using the
 * JDK's {@link Currency}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MoneyCurrency implements CurrencyUnit, Serializable, Comparable<CurrencyUnit> {

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

	public static CurrencyUnit getInstance(Currency currency) {
		String key = CurrencyUnit.ISO_NAMESPACE + ':'
				+ currency.getCurrencyCode();
		CurrencyUnit cachedItem = CACHED.get(key);
		if (cachedItem == null) {
			cachedItem = new JDKCurrencyAdapter(currency);
			CACHED.put(key, cachedItem);
		}
		return cachedItem;
	}

	public static CurrencyUnit getInstance(String isoCurrency) {
		return getInstance(Currency.getInstance(isoCurrency));
	}

	public static CurrencyUnit getInstance(String namespace, String currencyCode) {
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
		if (CurrencyUnit.ISO_NAMESPACE.equals(namespace)) {
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
			this(CurrencyUnit.ISO_NAMESPACE, currencyCode);
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

		public boolean hasLegalTender() {
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
				throw new IllegalStateException("Can not build MoneyCurrency.");
			}
			if (cache && validFrom == null && validUntil == null) {
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
			}
			else if (compare == 0 && currency.getValidUntil() != null) {
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
