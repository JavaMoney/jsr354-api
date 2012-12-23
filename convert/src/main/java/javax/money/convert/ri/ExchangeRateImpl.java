/**
 * Copyright (c) 2005, 2012, Werner Keil.
 * All rights reserved. 
 * Contributors:
 *    Werner Keil - initial API and implementation
 *    Anatole Tresch - extensions and adaptions.
 */
package javax.money.convert.ri;

import java.math.BigDecimal;
import java.util.Arrays;

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

	public <T> T getAdaptedFactor(Class<T> targetAdapterClass) {
		// TODO Auto-generated method stub
		return null;
	}

}
