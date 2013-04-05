/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.convert;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.money.CurrencyUnit;

/**
 * This class models an exchange rate between two currencies.
 * 
 * @author Werner Keil
 * @author Anatole Tresch
 */
public class ExchangeRate implements Serializable, Comparable<ExchangeRate> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5077295306570465837L;
	/** The base currency. */
	private final CurrencyUnit base;
	/** The terminating currency. */
	private final CurrencyUnit term;
	/** The conversion factor. */
	private final BigDecimal factor;
	/** The {@link ExchangeRateType}. */
	private final ExchangeRateType exchangeRateType;
	/** The timestamp from when this instance is valid, or {@code null}. */
	private Long validFrom;
	/** The timestamp until when this instance is valid, or {@code null}. */
	private Long validUntil;
	/** The provider name, or {@code null}. */
	private String provider;
	/** The full chain, at least one instance long. */
	private ExchangeRate[] chain = new ExchangeRate[] { this };

	/**
	 * Creates a simple <i>direct rate</i>.
	 * 
	 * @param conversionType
	 *            The conversion type, never {@code null}.
	 * @param base
	 *            the base {@link CurrencyUnit}
	 * @param term
	 *            the terminating {@link CurrencyUnit}
	 * @param factor
	 *            the conversion factor
	 */
	public ExchangeRate(ExchangeRateType conversionType, CurrencyUnit base,
			CurrencyUnit term, Number factor, String provider) {
		if (base == null) {
			throw new IllegalArgumentException("base may not be null.");
		}
		if (term == null) {
			throw new IllegalArgumentException("term may not be null.");
		}
		if (factor == null) {
			throw new IllegalArgumentException("factor may not be null.");
		}
		if (conversionType == null) {
			throw new IllegalArgumentException(
					"exchangeRateType may not be null.");
		}
		this.base = base;
		this.term = term;
		if (factor instanceof BigDecimal) {
			this.factor = (BigDecimal) factor;
		} else {
			this.factor = BigDecimal.valueOf(factor.doubleValue());
		}
		this.exchangeRateType = conversionType;
		this.provider = provider;
	}

	/**
	 * Creates a simple <i>direct rate</i>.
	 * 
	 * @param conversionType
	 *            The conversion type, never {@code null}.
	 * @param base
	 *            the base {@link CurrencyUnit}
	 * @param term
	 *            the terminating {@link CurrencyUnit}
	 * @param factor
	 *            the conversion factor
	 * @param validFrom
	 *            the UTC timestamp from when this rate is valid from, or
	 *            {@code null}
	 * @param validUntil
	 *            the UTC timestamp until when this rate is valid from, or
	 *            {@code null}
	 */
	public ExchangeRate(ExchangeRateType conversionType, CurrencyUnit base,
			CurrencyUnit term, Number factor, String provider,
			Long validFrom, Long validUntil) {
		this(conversionType, base, term, factor, provider);
		this.validFrom = validFrom;
		this.validUntil = validUntil;
	}

	/**
	 * Creates a new instance with a custom chain of {@link ExchangeRateType},
	 * e.g. or creating <i>derived</i> rates.
	 * 
	 * @param conversionType
	 *            The conversion type, never {@code null}.
	 * @param base
	 *            the base {@link CurrencyUnit}
	 * @param term
	 *            the terminating {@link CurrencyUnit}
	 * @param factor
	 *            the conversion factor
	 * @param chain
	 *            the rate chain, never {@code null}, not empty.
	 */
	public ExchangeRate(ExchangeRateType conversionType, CurrencyUnit base,
			CurrencyUnit term, Number factor, String provider,
			ExchangeRate[] chain) {
		this(conversionType, base, term, factor, provider);
		setExchangeRateChain(chain);
	}

	/**
	 * Creates a new instance with a custom chain of {@link ExchangeRateType},
	 * e.g. or creating <i>derived</i> rates.
	 * 
	 * @param conversionType
	 *            The conversion type, never {@code null}.
	 * @param base
	 *            the base {@link CurrencyUnit}
	 * @param term
	 *            the terminating {@link CurrencyUnit}
	 * @param factor
	 *            the conversion factor
	 * @param chain
	 *            the rate chain, never {@code null}, not empty.
	 * @param validFrom
	 *            the UTC timestamp from when this rate is valid from, or
	 *            {@code null}
	 * @param validUntil
	 *            the UTC timestamp until when this rate is valid from, or
	 *            {@code null}
	 */
	public ExchangeRate(ExchangeRateType conversionType, CurrencyUnit base,
			CurrencyUnit term, Number factor, String provider,
			ExchangeRate[] chain, Long validFrom, Long validUntil) {
		this(conversionType, base, term, factor, provider);
		setExchangeRateChain(chain);
		this.validFrom = validFrom;
		this.validUntil = validUntil;
	}

	/**
	 * Internal method to set the rate chain, which also ensure that the chain
	 * passed, when not null, contains valid elements.
	 * 
	 * @param chain
	 *            the chain to set.
	 */
	private void setExchangeRateChain(ExchangeRate[] chain) {
		if (chain == null || chain.length == 0) {
			this.chain = new ExchangeRate[] { this };
		}
		for (int i = 0; i < chain.length; i++) {
			if (chain[i] == null) {
				throw new IllegalArgumentException(
						"Chain element can not be null.");
			}
		}
		this.chain = chain;
	}

	/**
	 * Access the type of exchange rate.
	 * 
	 * @return the type of this rate, never null.
	 */
	public final ExchangeRateType getExchangeRateType() {
		return this.exchangeRateType;
	}

	/**
	 * Get the source currency.
	 * 
	 * @return the source currency.
	 */
	public final CurrencyUnit getBase() {
		return this.base;
	}

	/**
	 * Get the target currency.
	 * 
	 * @return the target currency.
	 */
	public final CurrencyUnit getTerm() {
		return this.term;
	}

	/**
	 * Access the rate's bid factor.
	 * 
	 * @return the bid factor for this exchange rate, or {@code null}.
	 */
	public final BigDecimal getFactor() {
		return this.factor;
	}

	/**
	 * Returns the UTC timestamp defining from what date/time this rate is
	 * valid.
	 * 
	 * @return The UTC timestamp of the rate, defining valid from, or
	 *         {@code null}.
	 */
	public final Long getValidFrom() {
		return this.validFrom;
	}

	/**
	 * Get the data validity timestamp of this rate in milliseconds. This can be
	 * useful, when a rate in a system only should be used within some specified
	 * time.
	 * 
	 * @return the duration of validity in milliseconds, or {@code null} if no
	 *         validity constraints apply.
	 */
	public final Long getValidUntil() {
		return this.validUntil;
	}

	/**
	 * Allows to check if a rate is still valid according to its data validity
	 * timestamp.
	 * 
	 * @see #getValidUntil()
	 * @return true, if the rate is valid for use.
	 */
	public final boolean isValid() {
		return validUntil == null
				|| validUntil.longValue() <= System.currentTimeMillis();
	}

	/**
	 * Get the provider of this rate. The provider of a rate can have different
	 * contexts in different usage scenarios, such as the service type or the
	 * stock exchange.
	 * 
	 * @return the provider, or {code null}.
	 */
	public String getProvider() {
		return this.provider;
	}

	/**
	 * Access the chain of exchange rates.
	 * 
	 * @return the chain of rates, in case of a derived rate, this may be
	 *         several instances. For a direct exchange rate, this equals to
	 *         <code>new ConversionRate[]{this}</code>.
	 */
	public final ExchangeRate[] getExchangeRateChain() {
		return this.chain.clone();
	}

	/**
	 * Allows to evaluate if this exchange rate is a derived exchange rate.
	 * Derived exchange rates are defined by an ordered list of subconversions
	 * with intermediate steps, whereas a direct conversion is possible in one
	 * steps.
	 * 
	 * @return true, if the exchange rate is derived.
	 */
	public final boolean isDerived() {
		return this.chain.length > 1;
	}

	@Override
	public int compareTo(ExchangeRate o) {
		if (o == null) {
			return -1;
		}
		int compare = ((Comparable<ExchangeRateType>) this
				.getExchangeRateType()).compareTo(o.getExchangeRateType());
		if (compare == 0) {
			if (provider != null) {
				compare = this.provider.compareTo(o.getProvider());
			} else if (o.getProvider() != null) {
				compare = o.getProvider().compareTo(this.provider);
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
		return "CurrencyExchangeRate [exchangeRateType=" + exchangeRateType
				+ ", base=" + base + ", term=" + term + ", factor=" + factor
				+ ", validFrom=" + validFrom + ", validUntil=" + validUntil
				+ ", provider=" + provider + "]";
	}

	/**
	 * Builder for creating new instances of {@link CurrencyExchangeRate}. Note
	 * that instances of this class are not thread-safe.
	 * 
	 * @author Anatole Tresch
	 */
	public static class Builder {

		private ExchangeRateType exchangeRateType;
		private CurrencyUnit base;
		private CurrencyUnit term;
		private BigDecimal factor;
		private String provider;
		private Long validFrom;
		private Long validUntil;
		private ExchangeRate[] rateChain;

		/**
		 * Sets the {@link ExchangeRateType}
		 * 
		 * @param exchangeRateType
		 *            to be applied
		 * @return the builder instance
		 */
		public Builder setExchangeRateType(ExchangeRateType exchangeRateType) {
			this.exchangeRateType = exchangeRateType;
			return this;
		}

		/**
		 * Get the configured {@link ExchangeRateType}.
		 * 
		 * @return the {@link ExchangeRateType}, or null.
		 */
		public ExchangeRateType getExchangeRateType() {
			return exchangeRateType;
		}

		/**
		 * Sets the base {@link CurrencyUnit}
		 * 
		 * @param base
		 *            to base {@link CurrencyUnit} to be applied
		 * @return the builder instance
		 */
		public Builder setBase(CurrencyUnit base) {
			this.base = base;
			return this;
		}

		/**
		 * Get the configured base {@link CurrencyUnit}.
		 * 
		 * @return the base {@link CurrencyUnit}, or null.
		 */
		public CurrencyUnit getBase() {
			return base;
		}

		/**
		 * Sets the terminating {@link CurrencyUnit}
		 * 
		 * @param term
		 *            to terminating {@link CurrencyUnit} to be applied
		 * @return the builder instance
		 */
		public Builder setTerm(CurrencyUnit term) {
			this.term = term;
			return this;
		}

		/**
		 * Get the configured terminating {@link CurrencyUnit}.
		 * 
		 * @return the terminating {@link CurrencyUnit}, or null.
		 */
		public CurrencyUnit getTerm() {
			return term;
		}

		/**
		 * Sets the validFrom timestamp
		 * 
		 * @param base
		 *            to validFrom timestamp to be applied
		 * @return the builder instance
		 */
		public Builder setValidFrom(Long validFrom) {
			this.validFrom = validFrom;
			return this;
		}

		/**
		 * Get the configured validFrom timestamp.
		 * 
		 * @return the timestamp, or null.
		 */
		public Long getValidFrom() {
			return validFrom;
		}

		/**
		 * Sets the validUntil timestamp
		 * 
		 * @param base
		 *            to validUntil timestamp to be applied
		 * @return the builder instance
		 */
		public Builder setValidUntil(Long validUntil) {
			this.validUntil = validUntil;
			return this;
		}

		/**
		 * Get the configured validUntil timestamp.
		 * 
		 * @return the timestamp, or null.
		 */
		public Long getValidUntil() {
			return validUntil;
		}

		/**
		 * Sets the {@link ExchangeRate} chain.
		 * 
		 * @param exchangeRates
		 *            the {@link ExchangeRate} chain to be applied
		 * @return the builder instance
		 */
		public Builder setExchangeRateChain(ExchangeRate... exchangeRates) {
			if (exchangeRates != null) {
				this.rateChain = exchangeRates.clone();
			} else {
				this.rateChain = null;
			}
			return this;
		}

		/**
		 * Get the configured rate chain.
		 * 
		 * @return the rate chain, or null.
		 */
		public ExchangeRate[] getExchangeRateChain() {
			if (rateChain != null) {
				return rateChain.clone();
			}
			return null;
		}

		/**
		 * Sets the conversion factor, as the factor
		 * {@code base * factor = target}.
		 * 
		 * @param factor
		 *            the factor.
		 * @return The builder instance.
		 */
		public Builder setBaseLeadingFactor(BigDecimal factor) {
			this.factor = factor;
			return this;
		}

		/**
		 * Sets the conversion factor, as the factor
		 * {@code term * factor = base}.
		 * 
		 * @param factor
		 *            the factor.
		 * @return The builder instance.
		 */
		public Builder setTermLeadingFactor(BigDecimal factor) {
			this.factor = BigDecimal.ONE.divide(factor);
			return this;
		}

		/**
		 * Sets the conversion factor, as the factor
		 * {@code term * factor = base}.
		 * 
		 * @param factor
		 *            the factor.
		 * @return The builder instance.
		 */
		public Builder setTermLeadingFactor(Number factor) {
			this.factor = BigDecimal.ONE.divide(BigDecimal.valueOf(factor
					.doubleValue()));
			return this;
		}

		/**
		 * Sets the provider to be applied.
		 * 
		 * @param provider
		 *            the provider, or null.
		 * @return The builder.
		 */
		public Builder setProvider(String provider) {
			this.provider = provider;
			return this;
		}

		/**
		 * Get the configured provider.
		 * 
		 * @return the provider, or null.
		 */
		public String getProvider() {
			return provider;
		}

		/**
		 * Get the configured factor.
		 * 
		 * @return the factor, or null.
		 */
		public BigDecimal getFactor() {
			return factor;
		}

		/**
		 * Determines if the current instance can build a new instance of
		 * {@link ExchangeRate}.
		 * 
		 * @return true, if a new rate can be build.
		 * @see #build()
		 */
		public boolean isBuildeable() {
			if (this.base == null) {
				return false;
			}
			if (this.term == null) {
				return false;
			}
			if (this.factor == null) {
				return false;
			}
			if (this.exchangeRateType == null) {
				return false;
			}
			return true;
		}

		/**
		 * Builds a new instance of {@link ExchangeRate}.
		 * 
		 * @return a new instance of {@link ExchangeRate}.
		 * @throws IllegalArgumentException
		 *             if the rate could not be build.
		 * @see #isBuildeable()
		 */
		public ExchangeRate build() {
			return new ExchangeRate(exchangeRateType, base, term, factor,
					provider, rateChain, validFrom, validUntil);
		}

	}

}
