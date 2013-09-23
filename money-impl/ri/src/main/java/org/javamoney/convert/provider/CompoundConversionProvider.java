/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Contributors: Anatole Tresch - initial implementation.
 */
package org.javamoney.convert.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.money.CurrencyUnit;

import org.javamoney.convert.ConversionProvider;
import org.javamoney.convert.CurrencyConverter;
import org.javamoney.convert.ExchangeRate;
import org.javamoney.convert.ExchangeRateType;

/**
 * This class implements a {@link ConversionProvider} that delegates calls to a
 * collection of child {@link ConversionProvider} instance.
 * 
 * @author Anatole Tresch
 */
public class CompoundConversionProvider implements
		ConversionProvider {
	/** The exchange rate type this instance is providing. */
	private final ExchangeRateType exchangeRateType;
	/** THe contained providers. */
	private final List<ConversionProvider> providers = new ArrayList<ConversionProvider>();
	/**
	 * The read write lock used, for synchronizing od adding additional
	 * providers and ansering queries.
	 */
	private ReadWriteLock rwLock = new ReentrantReadWriteLock();

	/**
	 * Constructor.
	 * 
	 * @param exchangeRateType
	 *            The exchange rate type this instance is providing.
	 *            Providers added must return the same on
	 *            {@link ConversionProvider#getExchangeRateType()}.
	 */
	public CompoundConversionProvider(ExchangeRateType exchangeRateType) {
		if (exchangeRateType == null) {
			throw new IllegalArgumentException("exchangeRateType required");
		}
		this.exchangeRateType = exchangeRateType;
	}

	/**
	 * Add an additional {@link ConversionProvider} to the instance's delegate
	 * list. Hereby {@link ConversionProvider#getExchangeRateType()} of the
	 * provider added must be equal to {@link #getExchangeRateType()}.
	 * 
	 * @param prov
	 *            The {@link ConversionProvider} to be added, not {@code null}.
	 * @throws IllegalArgumentException
	 *             if {@link ConversionProvider#getExchangeRateType()} of the
	 *             provider added is not equal to {@link #getExchangeRateType()}
	 *             .
	 */
	public void addProvider(ConversionProvider prov) {
		if (prov == null) {
			throw new IllegalArgumentException("ConversionProvider required.");
		}
		if (!getExchangeRateType().equals(prov.getExchangeRateType())) {
			throw new IllegalArgumentException(
					"Incompatible provider: this instance is providing rates for: "
							+ getExchangeRateType()
							+ ", but added provider declares "
							+ prov.getExchangeRateType());
		}
		Lock lock = rwLock.writeLock();
		try {
			lock.lock();
			providers.add(prov);
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.convert.ConversionProvider#getExchangeRateType()
	 */
	@Override
	public ExchangeRateType getExchangeRateType() {
		return exchangeRateType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.convert.ConversionProvider#isAvailable(javax.money.CurrencyUnit
	 * , javax.money.CurrencyUnit)
	 */
	@Override
	public boolean isAvailable(CurrencyUnit base, CurrencyUnit term) {
		Lock lock = rwLock.readLock();
		try {
			lock.lock();
			for (ConversionProvider prov : this.providers) {
				if (prov.isAvailable(base, term)) {
					return true;
				}
			}
			return false;
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.convert.ConversionProvider#isAvailable(javax.money.CurrencyUnit
	 * , javax.money.CurrencyUnit, long)
	 */
	@Override
	public boolean isAvailable(CurrencyUnit base, CurrencyUnit term,
			long timestamp) {
		Lock lock = rwLock.readLock();
		try {
			lock.lock();
			for (ConversionProvider prov : this.providers) {
				if (prov.isAvailable(base, term, timestamp)) {
					return true;
				}
			}
			return false;
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.convert.ConversionProvider#getExchangeRate(javax.money.
	 * CurrencyUnit, javax.money.CurrencyUnit, long)
	 */
	@Override
	public ExchangeRate getExchangeRate(CurrencyUnit base,
			CurrencyUnit term, long timestamp) {
		Lock lock = rwLock.readLock();
		try {
			lock.lock();
			for (ConversionProvider prov : this.providers) {
				ExchangeRate rate = prov.getExchangeRate(base, term,
						timestamp);
				if (rate != null) {
					return rate;
				}
			}
			return null;
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.convert.ConversionProvider#getExchangeRate(javax.money.
	 * CurrencyUnit, javax.money.CurrencyUnit)
	 */
	@Override
	public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term) {
		Lock lock = rwLock.readLock();
		try {
			lock.lock();
			for (ConversionProvider prov : this.providers) {
				ExchangeRate rate = prov.getExchangeRate(base, term);
				if (rate != null) {
					return rate;
				}
			}
			return null;
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.convert.ConversionProvider#getReversed(javax.money.convert
	 * .ExchangeRate)
	 */
	@Override
	public ExchangeRate getReversed(ExchangeRate rate) {
		Lock lock = rwLock.readLock();
		try {
			lock.lock();
			for (ConversionProvider prov : this.providers) {
				ExchangeRate revRate = prov.getReversed(rate);
				if (revRate != null) {
					return revRate;
				}
			}
			return null;
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.convert.ConversionProvider#getConverter()
	 */
	@Override
	public CurrencyConverter getConverter() {
		return new DefaultCurrencyConverter(this);
	}

}