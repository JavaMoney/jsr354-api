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
import java.math.MathContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAdjuster;
import javax.money.MonetaryAmount;

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
		this.factor = getBigDecimal(factor);
		this.exchangeRateType = conversionType;
		this.provider = provider;
	}

	private BigDecimal getBigDecimal(Number num) {
		if (num instanceof BigDecimal) {
			return (BigDecimal) num;
		}
		if (num instanceof Long) {
			return BigDecimal.valueOf(num.longValue());
		}
		return BigDecimal.valueOf(num.doubleValue());
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
			CurrencyUnit term, Number factor, String provider, Long validFrom,
			Long validUntil) {
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
			ExchangeRate... chain) {
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
			CurrencyUnit term, Number factor, String provider, Long validFrom,
			Long validUntil, ExchangeRate... chain) {
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
	private void setExchangeRateChain(ExchangeRate... chain) {
		if (chain == null || chain.length == 0) {
			this.chain = new ExchangeRate[] { this };
		} else {
			for (int i = 0; i < chain.length; i++) {
				if (chain[i] == null) {
					throw new IllegalArgumentException(
							"Chain element can not be null.");
				}
			}
			this.chain = chain;
		}
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
	 * Creates a {@link MonetaryAdjuster} that is performing conversion as
	 * defined by this rate instance.
	 * 
	 * @return an instance of {@link MonetaryAdjuster} performing rate
	 *         conversion from base to term, or vice versa, never null.
	 * @see ConvertingAdjuster
	 */
	public MonetaryAdjuster asAdjuster() {
		return new ConvertingAdjuster(this);
	}

	/**
	 * Creates a {@link MonetaryAdjuster} that is performing conversion as
	 * defined by this rate instance.
	 * 
	 * @param mathContext
	 *            The {@link MathContext} to be used for conversion an rate
	 *            calculation (e.g. for calculating reverse rates).
	 * @return an instance of {@link MonetaryAdjuster} performing rate
	 *         conversion from base to term, or vice versa, never null.
	 * @see ConvertingAdjuster
	 */
	public MonetaryAdjuster asAdjuster(MathContext mathContext) {
		return new ConvertingAdjuster(this).withMathContext(mathContext);
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
	public final List<ExchangeRate> getExchangeRateChain() {
		return Arrays.asList(this.chain);
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
		return "ExchangeRate [type=" + exchangeRateType.getId() + ", base="
				+ base + ", term=" + term + ", factor=" + factor
				+ ", validFrom=" + validFrom + ", validUntil=" + validUntil
				+ ", provider=" + provider + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((base == null) ? 0 : base.hashCode());
		result = prime
				* result
				+ ((exchangeRateType == null) ? 0 : exchangeRateType.hashCode());
		result = prime * result + ((factor == null) ? 0 : factor.hashCode());
		result = prime * result
				+ ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
		result = prime * result
				+ ((validFrom == null) ? 0 : validFrom.hashCode());
		result = prime * result
				+ ((validUntil == null) ? 0 : validUntil.hashCode());
		if (chain[0] != this) {
			result = prime * result + Arrays.hashCode(chain);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExchangeRate other = (ExchangeRate) obj;
		if (base == null) {
			if (other.base != null)
				return false;
		} else if (!base.equals(other.base))
			return false;
		if (chain[0] != this) {
			if (!Arrays.equals(chain, other.chain))
				return false;
		}
		if (exchangeRateType == null) {
			if (other.exchangeRateType != null)
				return false;
		} else if (!exchangeRateType.equals(other.exchangeRateType))
			return false;
		if (factor == null) {
			if (other.factor != null)
				return false;
		} else if (!factor.equals(other.factor))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		if (validFrom == null) {
			if (other.validFrom != null)
				return false;
		} else if (!validFrom.equals(other.validFrom))
			return false;
		if (validUntil == null) {
			if (other.validUntil != null)
				return false;
		} else if (!validUntil.equals(other.validUntil))
			return false;
		return true;
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
		 * Sets the {@link ExchangeRateType}
		 * 
		 * @param exchangeRateType
		 *            to be applied
		 * @return the builder instance
		 */
		public Builder setExchangeRateType(String exchangeRateType) {
			this.exchangeRateType = ExchangeRateType.of(exchangeRateType);
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
		public List<ExchangeRate> getExchangeRateChain() {
			if (rateChain != null) {
				return Arrays.asList(rateChain);
			}
			return Collections.emptyList();
		}

		/**
		 * Sets the conversion factor, as the factor
		 * {@code base * factor = target}.
		 * 
		 * @param factor
		 *            the factor.
		 * @return The builder instance.
		 */
		public Builder setFactor(Number factor) {
			this.factor = BigDecimal.valueOf(factor.doubleValue());
			return this;
		}

		/**
		 * Sets the conversion factor, as the factor
		 * {@code base * factor = target}.
		 * 
		 * @param factor
		 *            the factor.
		 * @return The builder instance.
		 */
		public Builder setFactor(Long factor) {
			this.factor = BigDecimal.valueOf(factor.longValue());
			return this;
		}

		/**
		 * Sets the conversion factor, as the factor
		 * {@code base * factor = target}.
		 * 
		 * @param factor
		 *            the factor.
		 * @return The builder instance.
		 */
		public Builder setFactor(BigDecimal factor) {
			this.factor = factor;
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
					provider, validFrom, validUntil, rateChain);
		}

	}

	/**
	 * Adjuster implementation that models currency conversion, eigther from
	 * source to term or vice versa, based on the given {@link ExchangeRate}.
	 * 
	 * @author Anatole Tresch
	 */
	public static final class ConvertingAdjuster implements MonetaryAdjuster {
		/** THe underlying exchange rate. */
		private ExchangeRate rate;
		/** The MathContext used. */
		private MathContext mathContext = MathContext.DECIMAL64;

		/**
		 * Creates a new converting adjuster based on the given
		 * {@link ExchangeRate}.
		 * 
		 * @param rate
		 *            the base rate, not null.
		 */
		public ConvertingAdjuster(ExchangeRate rate) {
			if (rate == null) {
				throw new IllegalArgumentException("Rate is required.");
			}
			this.rate = rate;
		}

		/**
		 * Access the {@link ExchangeRate} this adjuster is based on.
		 * 
		 * @return the according rate, never null.
		 */
		public ExchangeRate getExchangeRate() {
			return rate;
		}

		/**
		 * Access the {@link MathContext} used by this adjuster.
		 * 
		 * @return {@link MathContext} used.
		 */
		public MathContext getMathContext() {
			return mathContext;
		}

		/**
		 * Builder styled setter for the {@link MathContext} to be used.
		 * 
		 * @param mathContext
		 *            the {@link MathContext}, not null.
		 * @return this adjuster instance.
		 */
		public ConvertingAdjuster withMathContext(MathContext mathContext) {
			if (mathContext == null) {
				throw new IllegalArgumentException("MathContext is required.");
			}
			this.mathContext = mathContext;
			return this;
		}

		/**
		 * Adjuster method that converts eighter from source to term or vice
		 * versa, using the underlying {@link ExchangeRate}.
		 * 
		 * @param amount
		 *            the amount in base or term currency units.
		 * @return the converted amount in term or base currency.
		 * @throws CurrencyConversionException
		 *             if the amount is not convertible.
		 */
		public MonetaryAmount apply(MonetaryAmount amount) {
			CurrencyUnit curr = amount.getCurrency();
			if (curr.equals(rate.getBase())) {
				return amount.from(amount.asType(BigDecimal.class)
						.multiply(rate.getFactor(), this.mathContext));
			}
			if (curr.equals(rate.getTerm())) {
				BigDecimal reverseFactor = BigDecimal.ONE.divide(
						rate.getFactor(), amount.getScale());
				return amount.from(amount.asType(BigDecimal.class)
						.multiply(reverseFactor, MathContext.DECIMAL64));
			}
			throw new CurrencyConversionException(rate.getBase(),
					rate.getTerm(), System.currentTimeMillis(),
					"Incompatible currency in amount: " + amount.getCurrency());
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "ConvertingAdjuster [rate=" + rate + ", mathContext="
					+ mathContext + "]";
		}
		

	}

}
