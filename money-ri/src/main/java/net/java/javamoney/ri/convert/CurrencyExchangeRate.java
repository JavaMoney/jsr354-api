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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.money.CurrencyUnit;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateBuilder;
import javax.money.convert.ExchangeRateType;

import net.java.javamoney.ri.common.AbstractAttributableItem;

/**
 * Implementation of a value type for an {@link ExchangeRate}.
 * 
 * @author Anatole Tresch
 */
public final class CurrencyExchangeRate extends AbstractAttributableItem
		implements ExchangeRate, Comparable<ExchangeRate> {

	private final CurrencyUnit source;
	private final CurrencyUnit target;
	private final Number factor;
	private final ExchangeRateType exchangeRateType;
	private Long timestamp;
	private Long validUntil;
	private String location;
	private String dataProvider;
	private ExchangeRate[] chain = new ExchangeRate[] { this };

	public CurrencyExchangeRate(ExchangeRateType conversionType,
			CurrencyUnit source, CurrencyUnit target, Number factor) {
		if (source == null) {
			throw new IllegalArgumentException("source may not be null.");
		}
		if (target == null) {
			throw new IllegalArgumentException("target may not be null.");
		}
		if (factor == null) {
			throw new IllegalArgumentException("factor may not be null.");
		}
		if (conversionType == null) {
			throw new IllegalArgumentException(
					"exchangeRateType may not be null.");
		}
		this.source = source;
		this.target = target;
		this.factor = factor;
		this.exchangeRateType = conversionType;
	}

	public CurrencyExchangeRate(ExchangeRateType conversionType,
			CurrencyUnit source, CurrencyUnit target, Number factor,
			ExchangeRate[] chain) {
		this(conversionType, source, target, factor);
		setExchangeRateChain(chain);
	}

	public CurrencyExchangeRate(ExchangeRateType conversionType,
			CurrencyUnit source, CurrencyUnit target, Number factor,
			ExchangeRate[] chain, Long timestamp, Long validUntil) {
		this(conversionType, source, target, factor);
		setExchangeRateChain(chain);
		setTimestamp(timestamp);
		setValidUntil(validUntil);
	}

	public CurrencyExchangeRate(ExchangeRateType conversionType,
			CurrencyUnit source, CurrencyUnit target, Number factor,
			Long timestamp, Long validUntil) {
		this(conversionType, source, target, factor);
		setTimestamp(timestamp);
		setValidUntil(validUntil);
	}

	public final CurrencyUnit getSource() {
		return source;
	}

	public final CurrencyUnit getTarget() {
		return target;
	}

	public final Number getFactor() {
		return factor;
	}

	public final ExchangeRateType getExchangeRateType() {
		return exchangeRateType;
	}

	@Override
	public final Long getTimestamp() {
		return timestamp;
	}

	public final void setTimestamp(Long timestamp) {
		ensureWritable();
		this.timestamp = timestamp;
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
	public final String getLocation() {
		return location;
	}

	public final void setLocation(String location) {
		ensureWritable();
		this.location = location;
	}

	@Override
	public final String getDataProvider() {
		return dataProvider;
	}

	@Override
	public boolean isDerived() {
		return this.chain.length > 1;
	}

	public final void setDataProvider(String dataProvider) {
		ensureWritable();
		this.dataProvider = dataProvider;
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
	public int compareTo(ExchangeRate o) {
		if (o == null) {
			return -1;
		}
		int compare = this.getExchangeRateType().compareTo(
				o.getExchangeRateType());
		if (compare == 0) {
			if (location != null) {
				compare = this.location.compareTo(o.getLocation());
			} else if (o.getLocation() != null) {
				compare = o.getLocation().compareTo(this.location);
			}
		}
		return compare;
	}

	/**
	 * Builder for creating new instances of {@link CurrencyExchangeRate}. Note
	 * that instances of this class are not thread-safe.
	 * 
	 * @author Anatole Tresch
	 */
	public static final class Builder implements ExchangeRateBuilder {

		private ExchangeRateType exchangeRateType;
		private CurrencyUnit source;
		private CurrencyUnit target;
		private Number factor;
		private String dataProvider;
		private String location;
		private Long timestamp;
		private Long validUntil;
		private ExchangeRate[] rateChain;
		private Map<String, Object> attributes = new HashMap<String, Object>();

		@Override
		public ExchangeRateBuilder setConversionType(
				ExchangeRateType exchangeRateType) {
			this.exchangeRateType = exchangeRateType;
			return this;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T getAttribute(String key, Class<T> type) {
			return (T) this.attributes.get(key);
		}

		@Override
		public void clearAttributes() {
			this.attributes.clear();
		}

		@Override
		public final ExchangeRateBuilder setAttribute(String key, Object value) {
			this.attributes.put(key, value);
			return this;
		}

		@Override
		public ExchangeRateType getExchangeRateType() {
			return exchangeRateType;
		}

		@Override
		public ExchangeRateBuilder setSource(CurrencyUnit currency) {
			this.source = currency;
			return this;
		}

		@Override
		public ExchangeRateBuilder setTimestamp(Long timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		@Override
		public Long getTimestamp() {
			return timestamp;
		}

		@Override
		public ExchangeRateBuilder setValidUntil(Long validUntil) {
			this.validUntil = validUntil;
			return this;
		}

		@Override
		public Long getValidUntil() {
			return validUntil;
		}

		@Override
		public ExchangeRateBuilder setExchangeRateChain(
				ExchangeRate... exchangeRates) {
			if (exchangeRates != null) {
				this.rateChain = exchangeRates.clone();
			} else {
				this.rateChain = null;
			}
			return this;
		}

		@Override
		public ExchangeRate[] getExchangeRateChain() {
			if (rateChain != null) {
				return rateChain.clone();
			}
			return null;
		}

		@Override
		public ExchangeRateBuilder setSourceLeadingFactor(Number factor) {
			this.factor = factor;
			return this;
		}

		@Override
		public ExchangeRateBuilder setTargetLeadingFactor(Number factor) {
			this.factor = BigDecimal.ONE.divide(new BigDecimal(factor
					.toString()));
			return this;
		}

		@Override
		public ExchangeRateBuilder setLocation(String location) {
			this.location = location;
			return this;
		}

		@Override
		public String getLocation() {
			return location;
		}

		@Override
		public ExchangeRateBuilder setDataProvider(String dataProvider) {
			this.dataProvider = dataProvider;
			return this;
		}

		@Override
		public ExchangeRate build() {
			return new CurrencyExchangeRate(exchangeRateType, source, target,
					factor, rateChain, timestamp, validUntil);
		}

		@Override
		public CurrencyUnit getSource() {
			return source;
		}

		@Override
		public ExchangeRateBuilder setTarget(CurrencyUnit currency) {
			this.target = currency;
			return this;
		}

		@Override
		public CurrencyUnit getTarget() {
			return target;
		}

		@Override
		public Number getFactor() {
			return factor;
		}

		@Override
		public String getDataProvider() {
			return dataProvider;
		}

		@Override
		public boolean isBuildeable() {
			// TODO improve this implementation!
			try {
				build();
				return true;
			} catch (Exception e) {
				return false;
			}
		}

	}

}
