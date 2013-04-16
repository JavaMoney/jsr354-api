/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money;

import java.io.Serializable;
import java.util.Currency;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Adapter that implements the new {@link CurrencyUnit} interface using the
 * JDK's {@link Currency}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class MoneyCurrency implements CurrencyUnit, Serializable,
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
	/** true, if legal tender. */
	private final boolean legalTender;
	/** true, if it is a virtual currency. */
	private final boolean virtual;

	private static final Map<String, MoneyCurrency> CACHED = new ConcurrentHashMap<String, MoneyCurrency>();

	private static final Logger LOGGER = Logger.getLogger(MoneyCurrency.class
			.getName());

	/**
	 * Private constructor.
	 * 
	 * @param currency
	 */
	private MoneyCurrency(String namespace, String code, int numCode,
			int fractionDigits, Long validFrom, Long validUntil, boolean legal,
			boolean virtual) {
		this.namespace = namespace;
		this.currencyCode = code;
		this.numericCode = numCode;
		this.defaultFractionDigits = fractionDigits;
		this.validFrom = validFrom;
		this.validUntil = validUntil;
		this.legalTender = legal;
		this.virtual = virtual;
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
		this.namespace = ISO_NAMESPACE;
		this.currencyCode = currency.getCurrencyCode();
		this.numericCode = currency.getNumericCode();
		this.defaultFractionDigits = currency.getDefaultFractionDigits();
		this.validFrom = null;
		this.validUntil = null; // TODO Adapt for hisotoric one, e.g. AFA
		this.legalTender = !this.currencyCode.startsWith("X"); // TODO check for
																// each code in
																// util.Currency
																// here;
		this.virtual = this.currencyCode.equals("XXX"); // TODO check for each
														// code in util.Currency
														// here;
	}

	/**
	 * Access a new instance based on {@link Currency}.
	 * 
	 * @param currency
	 *            the currency unitm not null.
	 * @return the new instance, never null.
	 */
	public static MoneyCurrency of(Currency currency) {
		String key = ISO_NAMESPACE + ':' + currency.getCurrencyCode();
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
	public static MoneyCurrency of(String namespace, String currencyCode) {
		String key = namespace + ':' + currencyCode;
		MoneyCurrency cu = CACHED.get(key);
		if (cu == null && namespace.equals(ISO_NAMESPACE)) {
			return of(currencyCode);
		}
		return cu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnit#isVirtual()
	 */
	@Override
	public boolean isVirtual() {
		return virtual;
	}

	/**
	 * Get the namepsace of this {@link CurrencyUnit}, returns 'ISO-4217'.
	 */
	@Override
	public String getNamespace() {
		return namespace;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnit#getValidFrom()
	 */
	@Override
	public Long getValidFrom() {
		return validFrom;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnit#getValidUntil()
	 */
	@Override
	public Long getValidUntil() {
		return validUntil;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnit#getCurrencyCode()
	 */
	@Override
	public String getCurrencyCode() {
		return currencyCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnit#getNumericCode()
	 */
	@Override
	public int getNumericCode() {
		return numericCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnit#getDefaultFractionDigits()
	 */
	@Override
	public int getDefaultFractionDigits() {
		return defaultFractionDigits;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.CurrencyUnit#isLegalTender()
	 */
	@Override
	public boolean isLegalTender() {
		return legalTender;
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

	/**
	 * Builder class that supports building complex instances of
	 * {@link MoneyCurrency}.
	 * 
	 * @author Anatole Tresch
	 */
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
		/** true, if legal tender. */
		private boolean legalTender = true;
		/** true for virtual currencies. */
		private boolean virtual = false;

		/**
		 * Creates a new {@link Builder}.
		 */
		public Builder() {
		}

		/**
		 * Creates a new {@link Builder}, starting with the according ISO
		 * currency.
		 * 
		 * @param currencyCode
		 *            the ISO currency code.
		 */
		public Builder(String currencyCode) {
			this(ISO_NAMESPACE, currencyCode);
		}

		/**
		 * Creates a new {@link Builder}, starting with the namespace and code
		 * given.
		 * 
		 * @param namespace
		 *            the taregt namespace
		 * @param currencyCode
		 *            the currency code
		 */
		public Builder(String namespace, String currencyCode) {
			setNamespace(namespace);
			setCurrencyCode(currencyCode);
		}

		/**
		 * Set the namespace.
		 * 
		 * @param namespace
		 *            the namespace, not null
		 * @return the builder, for chaining
		 */
		public Builder setNamespace(String namespace) {
			if (namespace == null) {
				throw new IllegalArgumentException("namespace may not be null.");
			}
			this.namespace = namespace;
			return this;
		}

		/**
		 * Set the currency code.
		 * 
		 * @param namespace
		 *            the currency code, not null
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
		 * @param defaultFractionDigits
		 *            the default fraction digits
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
		 * @param numericCode
		 *            the numeric currency code
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
		 * Sets the start UTC timestamp for the currenciy's validity.
		 * 
		 * @param validFrom
		 *            the start UTC timestamp
		 * @return the builder, for chaining
		 */
		public Builder setValidFrom(Long validFrom) {
			this.validFrom = validFrom;
			return this;
		}

		/**
		 * Sets the end UTC timestamp for the currenciy's validity.
		 * 
		 * @param validUntil
		 *            the ending UTC timestamp
		 * @return the builder, for chaining
		 */
		public Builder setValidUntil(Long validUntil) {
			this.validUntil = validUntil;
			return this;
		}

		/**
		 * Sets the legal tender attribute.
		 * 
		 * @param legalTender
		 *            true, if the currency is a legal tender
		 * @return the builder, for chaining
		 */
		public Builder setLegalTender(boolean legalTender) {
			this.legalTender = legalTender;
			return this;
		}

		/**
		 * Sets the virtual attribute.
		 * 
		 * @param virtual
		 *            true, if the currency is a virtual currency.
		 * @return the builder, for chaining
		 */
		public Builder setVirtual(boolean virtual) {
			this.virtual = virtual;
			return this;
		}

		/**
		 * Get the current namespace attribute set.
		 * 
		 * @return the namespace value, or null.
		 */
		public String getNamespace() {
			return this.namespace;
		}

		/**
		 * Get the current currency code attribute set.
		 * 
		 * @return the currency code value, or null.
		 */
		public String getCurrencyCode() {
			return this.currencyCode;
		}

		/**
		 * Get the current fraction digits attribute set.
		 * 
		 * @return the currency fraction digits value.
		 */
		public int getDefaultFractionDigits() {
			return this.defaultFractionDigits;
		}

		/**
		 * Get the current numeric code attribute set.
		 * 
		 * @return the numeric code value.
		 */
		public int getNumericCode() {
			return this.numericCode;
		}

		/**
		 * Get the starting validity period timestamp.
		 * 
		 * @return the starting validity period tiemstamp, or null..
		 */
		public Long getValidFrom() {
			return this.validFrom;
		}

		/**
		 * Get the ending validity period timestamp.
		 * 
		 * @return the ending validity period tiemstamp, or null..
		 */
		public Long getValidUntil() {
			return this.validUntil;
		}

		/**
		 * Access the legal tender attribute.
		 * 
		 * @return the attribute value.
		 */
		public boolean isLegalTender() {
			return this.legalTender;
		}

		/**
		 * Access the virtual attribute.
		 * 
		 * @return the attribute value.
		 */
		public boolean isVirtual() {
			return this.virtual;
		}

		/**
		 * Checks if this {@link Builder} instance can create a
		 * {@link MoneyCurrency}.
		 * 
		 * @see #build()
		 * @return true, if the builder can build.
		 */
		public boolean isBuildable() {
			return namespace != null && currencyCode != null;
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
				MoneyCurrency current = CACHED.get(key);
				if (current == null) {
					current = new MoneyCurrency(namespace, currencyCode,
							numericCode, defaultFractionDigits, validFrom,
							validUntil, legalTender, virtual);
					CACHED.put(key, current);
				}
				return current;
			}
			return new MoneyCurrency(namespace, currencyCode, numericCode,
					defaultFractionDigits, validFrom, validUntil, legalTender,
					virtual);
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
	private final static class JDKCurrencyAdapter extends MoneyCurrency {

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
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return ISO_NAMESPACE + ':' + getCurrencyCode();
		}

	}

}
