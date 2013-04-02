/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 *    Anatole Tresch - initial version.
 */
package net.java.javamoney.ri.convert;

import java.util.HashMap;
import java.util.Map;

import javax.money.CurrencyUnit;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateType;

import net.java.javamoney.ri.common.AbstractAttributableItem;

/**
 * Implementation of a value type for an {@link ExchangeRate}.
 * 
 * @author Anatole Tresch
 */
public final class CurrencyExchangeRate extends AbstractAttributableItem
		implements ExchangeRate, Comparable<ExchangeRate> {

	private final CurrencyUnit base;
	private final CurrencyUnit term;
	private final Number factor;
	private final ExchangeRateType exchangeRateType;
	private Long validFrom;
	private Long validUntil;
	private String provider;
	private ExchangeRate[] chain = new ExchangeRate[] { this };

	public CurrencyExchangeRate(ExchangeRateType conversionType,
			CurrencyUnit base, CurrencyUnit term, Number factor) {
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
		this.factor = factor;
		this.exchangeRateType = conversionType;
	}

	public CurrencyExchangeRate(ExchangeRateType conversionType,
			CurrencyUnit base, CurrencyUnit term, Number factor,
			ExchangeRate[] chain) {
		this(conversionType, base, term, factor);
		setExchangeRateChain(chain);
	}

	public CurrencyExchangeRate(ExchangeRateType conversionType,
			CurrencyUnit base, CurrencyUnit term, Number factor,
			ExchangeRate[] chain, Long validFrom, Long validUntil) {
		this(conversionType, base, term, factor);
		if (chain != null) {
			setExchangeRateChain(chain);
		}
		setValidFrom(validFrom);
		setValidUntil(validUntil);
	}

	public CurrencyExchangeRate(ExchangeRateType conversionType,
			CurrencyUnit base, CurrencyUnit term, Number factor,
			Long validFrom, Long validUntil) {
		this(conversionType, base, term, factor);
		setValidFrom(validFrom);
		setValidUntil(validUntil);
	}

	public final CurrencyUnit getBase() {
		return base;
	}

	public final CurrencyUnit getTerm() {
		return term;
	}

	public final Number getFactor() {
		return factor;
	}

	public final ExchangeRateType getExchangeRateType() {
		return exchangeRateType;
	}

	@Override
	public final Long getValidFrom() {
		return validFrom;
	}

	public final void setValidFrom(Long validFrom) {
		ensureWritable();
		this.validFrom = validFrom;
	}

	@Override
	public Long getValidUntil() {
		return validUntil;
	}

	public final void setValidUntil(Long validUntil) {
		ensureWritable();
		this.validUntil = validUntil;
	}

	@Override
	public boolean isValid() {
		return validUntil == null
				|| validUntil.longValue() <= System.currentTimeMillis();
	}

	@Override
	public final String getProvider() {
		return provider;
	}

	public final void setProvider(String provider) {
		ensureWritable();
		this.provider = provider;
	}

	@Override
	public boolean isDerived() {
		return this.chain.length > 1;
	}

	public void setExchangeRateChain(ExchangeRate[] chain) {
		if (chain == null) {
			throw new IllegalArgumentException("Chain may not be null.");
		}
		// TODO check chain validity
		this.chain = chain.clone();
	}

	@Override
	public ExchangeRate[] getExchangeRateChain() {
		return this.chain.clone();
	}

	@Override
	public boolean isIdentity() {
		return getFactor().doubleValue() == 1.0d;
	}

	@Override
	public int compareTo(ExchangeRate o) {
		if (o == null) {
			return -1;
		}
		@SuppressWarnings("unchecked")
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
				+ ", base=" + base + ", term=" + term + ", factor="
				+ factor + ", validFrom=" + validFrom + ", validUntil="
				+ validUntil + ", provider=" + provider + "]";
	}

	/**
	 * Builder for creating new instances of {@link CurrencyExchangeRate}. Note
	 * that instances of this class are not thread-safe.
	 * 
	 * @author Anatole Tresch
	 */
	public static final class Builder {

		private ExchangeRateType exchangeRateType;
		private CurrencyUnit base;
		private CurrencyUnit term;
		private Number factor;
		private String provider;
		private Long validFrom;
		private Long validUntil;
		private ExchangeRate[] rateChain;
		private Map<String, Object> attributes = new HashMap<String, Object>();

		@SuppressWarnings("unchecked")
		public <T> T getAttribute(String key, Class<T> type) {
			return (T) this.attributes.get(key);
		}

		public void clearAttributes() {
			this.attributes.clear();
		}

		public final Builder setAttribute(String key, Object value) {
			this.attributes.put(key, value);
			return this;
		}

		public Builder setExchangeRateType(ExchangeRateType exchangeRateType) {
			this.exchangeRateType = exchangeRateType;
			return this;
		}

		public ExchangeRateType getExchangeRateType() {
			return exchangeRateType;
		}

		public Builder setBase(CurrencyUnit base) {
			this.base = base;
			return this;
		}

		public CurrencyUnit getBase() {
			return base;
		}

		public Builder setTerm(CurrencyUnit term) {
			this.term = term;
			return this;
		}

		public CurrencyUnit getTerm() {
			return term;
		}

		public Builder setValidFrom(Long validFrom) {
			this.validFrom = validFrom;
			return this;
		}

		public Long getValidFrom() {
			return validFrom;
		}

		public Builder setValidUntil(Long validUntil) {
			this.validUntil = validUntil;
			return this;
		}

		public Long getValidUntil() {
			return validUntil;
		}

		public Builder setExchangeRateChain(ExchangeRate... exchangeRates) {
			if (exchangeRates != null) {
				this.rateChain = exchangeRates.clone();
			} else {
				this.rateChain = null;
			}
			return this;
		}

		public ExchangeRate[] getExchangeRateChain() {
			if (rateChain != null) {
				return rateChain.clone();
			}
			return null;
		}

		public Builder setSourceLeadingFactor(Number factor) {
			this.factor = factor;
			return this;
		}

		public Builder setTargetLeadingFactor(Number factor) {
			this.factor = 1.0d / factor.doubleValue();
			return this;
		}

		public Builder setProvider(String provider) {
			this.provider = provider;
			return this;
		}

		public String getProvider() {
			return provider;
		}

		public Number getFactor() {
			return factor;
		}

		public boolean isBuildeable() {
			// TODO improve this implementation!
			try {
				build();
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		public ExchangeRate build() {
			CurrencyExchangeRate rate = new CurrencyExchangeRate(
					exchangeRateType, base, term, factor, rateChain,
					validFrom, validUntil);
			rate.setProvider(this.provider);
			rate.setReadOnly();
			return rate;
		}
	}

}
