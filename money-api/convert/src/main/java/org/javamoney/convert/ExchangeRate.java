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
package org.javamoney.convert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.money.CurrencyUnit;

/**
 * This class models an exchange rate between two currencies. Hereby
 * <ul>
 * <li>an exchange rate always models one rate from a base (source) to a term
 * (target) {@link CurrencyUnit}.</li>
 * <li>an exchange rate is always bound to a rate type, which typically matches
 * the data source of the conversion data, e.g. different credit card providers
 * may use different rates for the same conversion.</li>
 * <li>an exchange rate may restrict its validity. In most of the use cases a
 * rates' validity will be well defined, but it is also possible that the data
 * provider is not able to support the rate's validity, leaving it undefined-</li>
 * <li>an exchange rate has a provider, which is responsible for defining the
 * rate. A provider hereby may be, but must not be the same as the rate's data
 * source.</li>
 * <li>an exchange rate can be a <i>direct</i> rate, where its factor is
 * represented by a single conversion step. Or it can model a <i>derived</i>
 * rate, where multiple conversion steps are required to define the overall
 * base/term conversion. In case of derived rates the chained rates define the
 * overall factor, by multiplying the individual chain rate factors. Of course,
 * this also requires that each subsequent rate's base currency in the chain
 * does match the previous term currency (and vice versa):</li>
 * <li>Whereas the factor should be directly implied by the internal rate chain
 * for derived rates, this is obviously not the case for the validity range,
 * since rates can have a undefined validity range. Nevertheless in many cases
 * also the validity range can (but must not) be derived from the rate chain.</li>
 * <li>Finally a conversion rate is always unidirectional. There might be cases
 * where the reciprocal value of {@link #factor} matches the correct reverse
 * rate. But in most use cases the reverse rate either has a different rate (not
 * equal to the reciprocal value), or might not be defined at all. Therefore for
 * reversing a ExchangeRate one must access an {@link ConversionProvider} and
 * query for the reverse rate.</li>
 * </ul>
 * <p>
 * The class also implements {@link Comparable} to allow sorting of multiple
 * exchange rates using the following sorting order;
 * <ul>
 * <li>Exchange rate type</li>
 * <li>Exchange rate provider</li>
 * <li>base currency</li>
 * <li>term currency</li>
 * </ul>
 * <p>
 * Finally ExchangeRate is modeled as an immutable and thread safe type. Also
 * exchange rates are {@link Serializable}, hereby serializing in the following
 * form and order:
 * <ul>
 * <li>The {@link ExchangeRateType}
 * <li>The base {@link CurrencyUnit}
 * <li>The target {@link CurrencyUnit}
 * <li>The factor (BigDecimal)
 * <li>The provider (String)
 * <li>The validFrom timestamp (Long)
 * <li>The validTo timestamp (Long)
 * </ul>
 * 
 * @see <a
 *      href="https://en.wikipedia.org/wiki/Exchange_rate#Quotations">Wikipedia:
 *      Exchange Rate (Quotations)</a>
 * 
 * @author Werner Keil
 * @author Anatole Tresch
 */
public final class ExchangeRate implements Serializable,
		Comparable<ExchangeRate> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5077295306570465837L;
	/**
	 * The base currency.
	 */
	private final CurrencyUnit base;
	/**
	 * The terminating currency.
	 */
	private final CurrencyUnit term;
	/**
	 * The conversion factor.
	 */
	private final BigDecimal factor;
	/**
	 * The {@link ExchangeRateType}
	 */
	private final ExchangeRateType exchangeRateType;
	/**
	 * The timestamp from when this instance is valid, or {@code null}.
	 */
	private Long validFrom;
	/**
	 * The timestamp until when this instance is valid, or {@code null}.
	 */
	private Long validTo;
	/**
	 * The provider name, or {@code null}.
	 */
	private String provider;
	/**
	 * The full chain, at least one instance long.
	 */
	private ExchangeRate[] chain = new ExchangeRate[] { this };

	/**
	 * Evaluate a {@link BigDecimal} from a {@link Number} preserving maximal
	 * information.
	 * 
	 * @param num
	 *            the number
	 * @return a BigDecimal representing the number.
	 */
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
	 * Creates a new instance with a custom chain of exchange rate type, e.g. or
	 * creating <i>derived</i> rates.
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
	private ExchangeRate(ExchangeRateType exchangeRateType, CurrencyUnit base,
			CurrencyUnit term, Number factor, String provider, Long validFrom,
			Long validTo, ExchangeRate... chain) {
		if (base == null) {
			throw new IllegalArgumentException("base may not be null.");
		}
		if (term == null) {
			throw new IllegalArgumentException("term may not be null.");
		}
		if (factor == null) {
			throw new IllegalArgumentException("factor may not be null.");
		}
		if (exchangeRateType == null) {
			throw new IllegalArgumentException(
					"exchangeRateType may not be null.");
		}
		this.base = base;
		this.term = term;
		this.factor = getBigDecimal(factor);
		this.exchangeRateType = exchangeRateType;
		this.provider = provider;
		setExchangeRateChain(chain);
		this.validFrom = validFrom;
		this.validTo = validTo;
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
	 * Access the {@link ExchangeRateType} of {@link ExchangeRate}.
	 * 
	 * @return the type of this rate, never null.
	 */
	public final ExchangeRateType getExchangeRateType() {
		return this.exchangeRateType;
	}

	/**
	 * Get the base (source) {@link CurrencyUnit}.
	 * 
	 * @return the base {@link CurrencyUnit}.
	 */
	public final CurrencyUnit getBase() {
		return this.base;
	}

	/**
	 * Get the term (target) {@link CurrencyUnit}.
	 * 
	 * @return the term {@link CurrencyUnit}.
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
	 * <p>
	 * This is modelled as {@link Long} instaed of {@code long}, since it is
	 * possible, that an {@link ExchangeRate} does not have starting validity
	 * range. This also can be queried by calling {@link #isLowerBound()}.
	 * 
	 * @return The UTC timestamp of the rate, defining valid from, or
	 *         {@code null}, if no starting validity constraint is set.
	 */
	public final Long getValidFromMillis() {
		return this.validFrom;
	}

	/**
	 * Get the data validity timestamp of this rate in milliseconds. This can be
	 * useful, when a rate in a system only should be used within some specified
	 * time. *
	 * <p>
	 * This is modelled as {@link Long} instaed of {@code long}, since it is
	 * possible, that an {@link ExchangeRate} does not have ending validity
	 * range. This also can be queried by calling {@link #isUpperBound()}.
	 * 
	 * @return the duration of validity in milliseconds, or {@code null} if no
	 *         ending validity constraint is set.
	 */
	public final Long getValidToMillis() {
		return this.validTo;
	}

	/**
	 * Method to quickly check if an {@link ExchangeRate} is valid for a given
	 * UTC timestamp.
	 * 
	 * @param timestamp
	 *            the UTC timestamp.
	 * @return {@code true}, if the rate is valid.
	 */
	public boolean isValid(long timestamp) {
		if (validTo != null && validTo.longValue() < timestamp) {
			return false;
		}
		if (validFrom != null && validFrom.longValue() > timestamp) {
			return false;
		}
		return true;
	}

	/**
	 * Method to easily check if the {@link #getValidFromMillis()} is not
	 * {@code null}.
	 * 
	 * @return {@code true} if {@link #getValidFromMillis()} is not {@code null}
	 *         .
	 */
	public boolean isLowerBound() {
		return validFrom != null;
	}

	/**
	 * Method to easily check if the {@link #getValidToMillis()} is not
	 * {@code null}.
	 * 
	 * @return {@code true} if {@link #getValidToMillis()} is not {@code null}.
	 */
	public boolean isUpperBound() {
		return validTo != null;
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
	 * <p>
	 * This method always returns {@code true}, if the chain contains more than
	 * one rate. Direct rates, have also a chain, but with exact one rate.
	 * 
	 * @return true, if the exchange rate is derived.
	 */
	public final boolean isDerived() {
		return this.chain.length > 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ExchangeRate o) {
		if (o == null) {
			return -1;
		}
		int compare = this
				.getExchangeRateType().compareTo(o.getExchangeRateType());
		if (compare == 0) {
			if (provider != null) {
				compare = this.provider.compareTo(o.getProvider());
			} else if (o.getProvider() != null) {
				compare = o.getProvider().compareTo(this.provider);
			}
		}
		if (compare == 0) {
			compare = this.getBase().getCurrencyCode()
					.compareTo(o.getBase().getCurrencyCode());
		}
		if (compare == 0) {
			compare = this.getTerm().getCurrencyCode()
					.compareTo(o.getTerm().getCurrencyCode());
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
				+ ", validFrom=" + validFrom + ", validTo=" + validTo
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
				+ ((validTo == null) ? 0 : validTo.hashCode());
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ExchangeRate other = (ExchangeRate) obj;
		if (base == null) {
			if (other.base != null) {
				return false;
			}
		} else if (!base.equals(other.base)) {
			return false;
		}
		if (chain[0] != this) {
			if (!Arrays.equals(chain, other.chain)) {
				return false;
			}
		}
		if (exchangeRateType == null) {
			if (other.exchangeRateType != null) {
				return false;
			}
		} else if (!exchangeRateType.equals(other.exchangeRateType)) {
			return false;
		}
		if (factor == null) {
			if (other.factor != null) {
				return false;
			}
		} else if (!factor.equals(other.factor)) {
			return false;
		}
		if (provider == null) {
			if (other.provider != null) {
				return false;
			}
		} else if (!provider.equals(other.provider)) {
			return false;
		}
		if (term == null) {
			if (other.term != null) {
				return false;
			}
		} else if (!term.equals(other.term)) {
			return false;
		}
		if (validFrom == null) {
			if (other.validFrom != null) {
				return false;
			}
		} else if (!validFrom.equals(other.validFrom)) {
			return false;
		}
		if (validTo == null) {
			if (other.validTo != null) {
				return false;
			}
		} else if (!validTo.equals(other.validTo)) {
			return false;
		}
		return true;
	}

	/**
	 * Builder for creating new instances of {@link ExchangeRate}. Note that
	 * instances of this class are not thread-safe.
	 * 
	 * @author Anatole Tresch
	 */
	public static class Builder {

		/**
		 * The rate type.
		 */
		private ExchangeRateType exchangeRateType;
		/**
		 * The base (source) currency.
		 */
		private CurrencyUnit base;
		/**
		 * The term (target) currency.
		 */
		private CurrencyUnit term;
		/**
		 * The conversion factor.
		 */
		private BigDecimal factor;
		/**
		 * The rate provider.
		 */
		private String provider;
		/**
		 * The timestamp of the rate.
		 */
		private Long validFrom;
		/**
		 * The ending validity range.
		 */
		private Long validTo;
		/**
		 * The chain of invovled rates.
		 */
		private ExchangeRate[] rateChain;

		/**
		 * Sets the exchange rate type
		 * 
		 * @param exchangeRateType
		 *            to be applied
		 * @return the builder instance
		 */
		public Builder withExchangeRateType(ExchangeRateType exchangeRateType) {
			this.exchangeRateType = exchangeRateType;
			return this;
		}

		/**
		 * Sets the base {@link CurrencyUnit}
		 * 
		 * @param base
		 *            to base (source) {@link CurrencyUnit} to be applied
		 * @return the builder instance
		 */
		public Builder withBase(CurrencyUnit base) {
			this.base = base;
			return this;
		}

		/**
		 * Sets the terminating (target) {@link CurrencyUnit}
		 * 
		 * @param term
		 *            to terminating {@link CurrencyUnit} to be applied
		 * @return the builder instance
		 */
		public Builder withTerm(CurrencyUnit term) {
			this.term = term;
			return this;
		}

		/**
		 * Sets the validFrom timestamp
		 * 
		 * @param base
		 *            to validFrom timestamp to be applied
		 * @return the builder instance
		 */
		public Builder withValidFromMillis(Long validFrom) {
			this.validFrom = validFrom;
			return this;
		}

		/**
		 * Sets the validUntil timestamp
		 * 
		 * @param base
		 *            to validUntil timestamp to be applied
		 * @return the builder instance
		 */
		public Builder withValidToMillis(Long validTo) {
			if (validTo != null) {
				this.validTo = validTo;
			}
			return this;
		}

		/**
		 * Sets the {@link ExchangeRate} chain.
		 * 
		 * @param exchangeRates
		 *            the {@link ExchangeRate} chain to be applied
		 * @return the builder instance
		 */
		public Builder withExchangeRateChain(ExchangeRate... exchangeRates) {
			if (exchangeRates != null) {
				this.rateChain = exchangeRates.clone();
			} else {
				this.rateChain = null;
			}
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
		public Builder withFactor(Number factor) {
			if (factor != null) {
				if (factor instanceof BigDecimal) {
					this.factor = (BigDecimal) factor;
				} else {
					this.factor = BigDecimal.valueOf(factor.doubleValue());
				}
			}
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
		public Builder withFactor(BigDecimal factor) {
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
		public Builder withProvider(String provider) {
			this.provider = provider;
			return this;
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
					provider, validFrom, validTo, rateChain);
		}

		/**
		 * Initialize the {@link Builder} with an {@link ExchangeRate}. This is
		 * useful for creating a new rate, reusing some properties from an
		 * existing one.
		 * 
		 * @param rate
		 *            the base rate
		 * @return the Builder, for chaining.
		 */
		public Builder withExchangeRate(ExchangeRate rate) {
			this.base = rate.getBase();
			this.term = rate.getTerm();
			this.exchangeRateType = rate.getExchangeRateType();
			this.factor = rate.getFactor();
			this.provider = rate.getProvider();
			this.rateChain = rate.chain;
			this.term = rate.getTerm();
			this.validFrom = rate.getValidFromMillis();
			this.validTo = rate.getValidToMillis();
			return this;
		}
	}
}
