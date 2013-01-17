/*
 *  Copyright (c) 2005, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 *    Werner Keil - initial API and implementation
 *    Anatole Tresch - extensions and adaptions.
 */
package net.java.javamoney.ri.convert;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Enumeration;

import javax.money.CurrencyUnit;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateType;

/**
 * @author Werner Keil
 * @author Anatole Tresch
 * @version 0.2.2
 */
public class ExchangeRateImpl implements ExchangeRate {

	/**
	 * The type of exchange rate.
	 */
	private ExchangeRateType exchangeRateType;

	/**
	 * Holds the source CurrencyUnit.
	 */
	private final CurrencyUnit source;

	/**
	 * Holds the target CurrencyUnit.
	 */
	private final CurrencyUnit target;

	/**
	 * Holds the exchange factor.
	 */
	private final BigDecimal factor;

	/**
	 * In case of a derived exchange rate, this is the underlying chain of
	 * rates.
	 */
	private final ExchangeRate[] rateChain;

	/** The timestamp of this exchange rate. */
	private long timestamp;

	/** Validity ttl timestamp. */
	private long validUntil;

	/**
	 * Creates a {@link ExchangeRateType#DIRECT} exchange rate using the given
	 * chain of rates.
	 * 
	 * @param source
	 *            source currency.
	 * @param target
	 *            target currency.
	 * @param factor
	 *            the exchange rate factor from source to target currency.
	 */
	@SuppressWarnings("unchecked")
	public ExchangeRateImpl(CurrencyUnit source, CurrencyUnit target,
			BigDecimal factor) {
		this.source = source;
		this.target = target;
		this.factor = factor;
		this.rateChain = new ExchangeRate[] { this };
	}

	/**
	 * Creates a {@link ExchangeRateType#DIRECT} exchange rate using the given
	 * chain of rates.
	 * 
	 * @param source
	 *            source currency.
	 * @param target
	 *            target currency.
	 * @param factor
	 *            the exchange rate factor from source to target currency.
	 * @param quoteTS
	 *            the timestamp of this quote.
	 * @param validityDuration
	 *            duration how long this rate is considered valid.
	 */
	@SuppressWarnings("unchecked")
	public ExchangeRateImpl(CurrencyUnit source, CurrencyUnit target,
			BigDecimal factor, long quoteTS, long validdityDuration) {
		super();
		this.source = source;
		this.target = target;
		this.factor = factor;
		this.timestamp = quoteTS;
		this.rateChain = new ExchangeRate[] { this };
	}

	/**
	 * Creates a {@link ExchangeRateType#DERIVED} exchange rate using the given
	 * chain of rates.
	 * 
	 * @param exchangeRates
	 *            the chain of rates that define a derived exchange rate from
	 *            the source currency of the first item in the chain to the
	 *            target currency in the last item of the chain. In between
	 *            every target currency must match the source currency of the
	 *            next rate within the chain.
	 * @throws IllegalArgumentException
	 *             if the chain passed is inconsistent.
	 */
	public ExchangeRateImpl(ExchangeRate... exchangeRates) {
		// TODO check consistency and not null.
		this.source = exchangeRates[0].getSourceCurrency();
		this.target = exchangeRates[exchangeRates.length - 1]
				.getTargetCurrency();
		// TODO Calculate exchange factor overall...
		this.factor = null;
		this.rateChain = exchangeRates.clone();
	}

	/**
	 * Get the source currency.
	 * 
	 * @return the source currency.
	 */
	public CurrencyUnit getSourceCurrency() {
		return source;
	}

	/**
	 * Get the target currency.
	 * 
	 * @return the target currency.
	 */
	public CurrencyUnit getTargetCurrency() {
		return target;
	}

	/**
	 * Get the exchange factor.
	 * 
	 * @return the exchange factor.
	 */
	public BigDecimal getFactor() {
		return factor;
	}

	/**
	 * Access the chain of exchange rates.
	 * 
	 * @return the chain of rates, in case of a derived rate, this may be
	 *         several instances.
	 */
	public ExchangeRate[] getExchangeRateChain() {
		return this.rateChain.clone();
	}

	/**
	 * Access the type of exchange rate.
	 * 
	 * @return the type of this rate.
	 */
	public ExchangeRateType getExchangeRateType() {
		return this.exchangeRateType;
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
		result = prime
				* result
				+ ((exchangeRateType == null) ? 0 : exchangeRateType.hashCode());
		result = prime * result + ((factor == null) ? 0 : factor.hashCode());
		result = prime * result + Arrays.hashCode(rateChain);
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
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
		ExchangeRateImpl other = (ExchangeRateImpl) obj;
		if (exchangeRateType != other.exchangeRateType)
			return false;
		if (factor == null) {
			if (other.factor != null)
				return false;
		} else if (!factor.equals(other.factor))
			return false;
		if (!Arrays.equals(rateChain, other.rateChain))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExchangeRate [exchangeRateType=" + exchangeRateType
				+ ", source=" + source + ", target=" + target + ", factor="
				+ factor + ", rateChain=" + Arrays.toString(rateChain)
				+ ", timestamp=" + timestamp + ", validUntil=" + validUntil
				+ "]";
	}

	public <T> T getFactorAsType(Class<T> targetClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDeferred() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDataProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getAttribute(String key, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getAttributeKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getAttributeType(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(ExchangeRate o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDerived() {
		// TODO Auto-generated method stub
		return false;
	}

}
