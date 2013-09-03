/**
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
package javax.money.convert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import javax.money.CurrencyUnit;

/**
 * This class models an exchange rate between two currencies.
 * 
 * @version 0.4
 * @see <a
 *      href="https://en.wikipedia.org/wiki/Exchange_rate#Quotations">Wikipedia:
 *      Exchange Rate (Quotations)</a>
 * 
 * @author Werner Keil
 * @author Anatole Tresch
 */
public class ExchangeRate implements Serializable, Comparable<ExchangeRate> {

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
	 * The {@link ExchangeRateType}.
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
			Long validTo) {
		this(conversionType, base, term, factor, provider);
		this.validFrom = validFrom;
		this.validTo = validTo;
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
			Long validTo, ExchangeRate... chain) {
		this(conversionType, base, term, factor, provider);
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
	 * Access the type of {@link ExchangeRate}.
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
	 * 
	 * @return The UTC timestamp of the rate, defining valid from, or
	 *         {@code null}.
	 */
	public final Long getValidFromTimeInMillis() {
		return this.validFrom;
	}

	/**
	 * Access the starting {@link GregorianCalendar} from which the item T is
	 * valid, related to R.
	 * 
	 * @return the starting {@link GregorianCalendar}, or {@code null}.
	 */
	public GregorianCalendar getValidFrom() {
		return getValidFrom(GregorianCalendar.class);
	}

	/**
	 * Access the starting {@link Calendar} from which the item {@code T} is
	 * valid, related to {@code R}.
	 * 
	 * @param type
	 *            The calendar type required. The type must have a public
	 *            parameterless constructor and must be initializable by calling
	 *            {@link Calendar#setTimeInMillis(Long)}.
	 * @return the starting {@link Calendar} instance, or {@code null}.
	 */
	public <C extends Calendar> C getValidFrom(Class<C> type) {
		if (validFrom != null) {
			C cal;
			try {
				cal = (C) type.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalArgumentException(
						"Calendar type is not instantiatable.", e);
			}
			cal.setTimeInMillis(validFrom);
			return cal;
		}
		return null;
	}

	/**
	 * Get the data validity timestamp of this rate in milliseconds. This can be
	 * useful, when a rate in a system only should be used within some specified
	 * time.
	 * 
	 * @return the duration of validity in milliseconds, or {@code null} if no
	 *         validity constraints apply.
	 */
	public final Long getValidToTimeInMillis() {
		return this.validTo;
	}

	/**
	 * Access the starting GregorianCalendar from which the item {@code T} is
	 * valid, related to {@code R}.
	 * 
	 * @return the starting {@link GregorianCalendar}, or {@code null}.
	 */
	public GregorianCalendar getValidTo() {
		return getValidTo(GregorianCalendar.class);
	}

	/**
	 * Access the starting {@link Calendar} from which the item {@code T} is
	 * valid, related to {@code R}.
	 * 
	 * @param type
	 *            The calendar type required. The type must have a public
	 *            parameterless constructor and must be initializable by calling
	 *            {@link Calendar#setTimeInMillis(Long)}.
	 * @return the starting Calendar instance, or null.
	 */
	public <C extends Calendar> C getValidTo(Class<C> type) {
		if (validTo != null) {
			C cal;
			try {
				cal = (C) type.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalArgumentException(
						"Calendar type is not instantiatable.", e);
			}
			cal.setTimeInMillis(validTo);
			return cal;
		}
		return null;
	}

	/**
	 * Allows to check if a rate is still valid according to its data validity
	 * timestamp.
	 * 
	 * @see #getValidUntil()
	 * @return {@code true}, if the rate is valid for use.
	 */
	public final boolean isValid() {
		return isValid(null);
	}

	/**
	 * Method to quickly check if an {@link ExchangeRate} is valid for a given
	 * timestamp.
	 * 
	 * @param timestamp
	 *            the timestamp, or null.
	 * @return {@code true}, if the rate is valid.
	 */
	public boolean isValid(Long timestamp) {
		long ts = System.currentTimeMillis();
		if (timestamp == null) {
			if (validTo != null && validTo.longValue() < ts) {
				return false;
			}
			if (validFrom != null && validFrom.longValue() > ts) {
				return false;
			}
		} else {
			ts = timestamp.longValue();
			if (validTo != null && validTo.longValue() < ts) {
				return false;
			}
			if (validFrom != null && validFrom.longValue() > ts) {
				return false;
			}
		}
		return true;
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
		 * The base currency.
		 */
		private CurrencyUnit base;
		/**
		 * The term currency.
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
		 * Sets the validFrom timestamp
		 * 
		 * @param base
		 *            to validFrom timestamp to be applied
		 * @return the builder instance
		 */
		public Builder setValidFrom(Calendar validFrom) {
			if (validFrom != null) {
				this.validFrom = validFrom.getTimeInMillis();
			}
			return this;
		}

		/**
		 * Method to quickly determine if a validity is valid for the current
		 * timestamp. A Validity is considered valid, if all the following is
		 * {@code true}:
		 * <ul>
		 * <li><@code from == null || from <= current UTC timestamp}</li>
		 * <li><@code to == null || to >= current UTC timestamp}</li>
		 * </ul>
		 * 
		 * @return {@code true} if the validity is currently valid.
		 */
		public boolean isValid() {
			long ts = System.currentTimeMillis();
			return (validFrom == null || validFrom <= ts)
					&& (validTo == null || validTo >= ts);
		}

		/**
		 * Method to easily check if the {@code from} is not {@code null}.
		 * 
		 * @return {@code true} if {@code from} is not {@code null}.
		 */
		public boolean isLowerBound() {
			return validFrom != null;
		}

		/**
		 * Method to easily check if the {@code from} is not {@code null}.
		 * 
		 * @return {@code true} if {@code from} is not {@code null}.
		 */
		public boolean isUpperBound() {
			return validTo != null;
		}

		/**
		 * Access the starting UTC timestamp from which the item T is valid,
		 * related to R.
		 * 
		 * @return the starting UTC timestamp, or null.
		 */
		public Long getValidFromTimeInMillis() {
			if (validFrom != null) {
				return validFrom;
			}
			return null;
		}

		/**
		 * Access the ending UTC timestamp until the item T is valid, related to
		 * R.
		 * 
		 * @return the ending UTC timestamp, or null.
		 */
		public Long getValidToTimeInMillis() {
			if (validTo != null) {
				return validTo;
			}
			return null;
		}

		/**
		 * Access the starting GregorianCalendar from which the item T is valid,
		 * related to R.
		 * 
		 * @return the starting GregorianCalendar, or null.
		 */
		public GregorianCalendar getValidFrom() {
			return getValidFrom(GregorianCalendar.class);
		}

		/**
		 * Access the starting Calendar from which the item T is valid, related
		 * to R.
		 * 
		 * @param type
		 *            The calendar type required. The type must have a public
		 *            parameterless constructor and must be initializable by
		 *            calling Calendar#setTimeInMillis(Long).
		 * @return the starting Calendar instance, or null.
		 */
		public <C extends Calendar> C getValidFrom(Class<C> type) {
			if (validFrom != null) {
				C cal;
				try {
					cal = (C) type.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					throw new IllegalArgumentException(
							"Calendar type is not instantiatable.", e);
				}
				cal.setTimeInMillis(validFrom);
				return cal;
			}
			return null;
		}

		/**
		 * Access the ending GregorianCalendar until which the item T is valid,
		 * related to R.
		 * 
		 * @return the ending GregorianCalendar, or null.
		 */
		public GregorianCalendar getValidTo() {
			return getValidTo(GregorianCalendar.class);
		}

		/**
		 * Access the starting Calendar until which the item T is valid, related
		 * to R.
		 * 
		 * @param type
		 *            The calendar type required. The type must have a public
		 *            parameterless constructor and must be initializable by
		 *            calling Calendar#setTimeInMillis(Long).
		 * @return the ending Calendar instance, or null.
		 */
		public <C extends Calendar> C getValidTo(Class<C> type) {
			if (validTo != null) {
				C cal;
				try {
					cal = (C) type.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					throw new IllegalArgumentException(
							"Calendar type is not instantiatable.", e);
				}
				cal.setTimeInMillis(validTo);
				return cal;
			}
			return null;
		}

		/**
		 * Sets the validUntil timestamp
		 * 
		 * @param base
		 *            to validUntil timestamp to be applied
		 * @return the builder instance
		 */
		public Builder setValidTo(Long validTo) {
			if (validTo != null) {
				this.validTo = validTo;
			}
			return this;
		}

		/**
		 * Sets the validUntil timestamp
		 * 
		 * @param base
		 *            to validUntil timestamp to be applied
		 * @return the builder instance
		 */
		public Builder setValidTo(Calendar validTo) {
			if (validTo != null) {
				this.validTo = validTo.getTimeInMillis();
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
			if (factor != null) {
				if (factor instanceof BigDecimal) {
					this.factor = (BigDecimal)factor;
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
					provider, validFrom, validTo, rateChain);
		}
	}
}
