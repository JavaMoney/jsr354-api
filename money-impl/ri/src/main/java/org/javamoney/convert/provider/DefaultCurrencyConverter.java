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

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

import org.javamoney.convert.ConversionProvider;
import org.javamoney.convert.CurrencyConversion;
import org.javamoney.convert.CurrencyConversionException;
import org.javamoney.convert.CurrencyConverter;
import org.javamoney.convert.ExchangeRate;
import org.javamoney.convert.ExchangeRateType;
import org.javamoney.convert.FixedCurrencyConversion;
import org.javamoney.convert.LazyBoundCurrencyConversion;


/**
 * Implements a {@link CurrencyConverter} backed up by a corresponding
 * {@link ConversionProvider} instance.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class DefaultCurrencyConverter implements CurrencyConverter {

	private final ConversionProvider rateProvider;

	public DefaultCurrencyConverter(ConversionProvider rateProvider) {
		if (rateProvider == null) {
			throw new IllegalArgumentException("rateProvider required.");
		}
		this.rateProvider = rateProvider;
	}

	@Override
	public ExchangeRateType getExchangeRateType() {
		return this.rateProvider.getExchangeRateType();
	}

	@Override
	public <T extends MonetaryAmount> T convert(T amount, CurrencyUnit target) {
		ExchangeRate rate = this.rateProvider.getExchangeRate(
				amount.getCurrency(), target);
		if (rate == null) {
			throw new CurrencyConversionException(amount.getCurrency(), target,
					null, "No rate available.");
		}
		return (T) new FixedCurrencyConversion(rate).adjustInto(amount);
	}

	@Override
	public <T extends MonetaryAmount> T convert(T amount, CurrencyUnit target,
			Long timestamp) {
		ExchangeRate rate = this.rateProvider.getExchangeRate(
				amount.getCurrency(), target, timestamp);
		if (rate == null) {
			throw new CurrencyConversionException(amount.getCurrency(), target,
					null, "No rate available.");
		}
		return (T) new FixedCurrencyConversion(rate).adjustInto(amount);
	}

	@Override
	public CurrencyConversion getConversion(CurrencyUnit base, CurrencyUnit term) {
		ExchangeRate rate = this.rateProvider.getExchangeRate(base, term);
		if (rate == null) {
			throw new CurrencyConversionException(base, term, null,
					"No rate available.");
		}
		return new FixedCurrencyConversion(rate);
	}

	@Override
	public CurrencyConversion getConversion(CurrencyUnit base,
			CurrencyUnit term, long targetTimestamp) {
		ExchangeRate rate = this.rateProvider.getExchangeRate(base, term,
				targetTimestamp);
		if (rate == null) {
			throw new CurrencyConversionException(base, term, targetTimestamp,
					"No rate available.");
		}
		return new FixedCurrencyConversion(rate);
	}

	@Override
	public CurrencyConversion getConversion(CurrencyUnit term) {
		return new LazyBoundCurrencyConversion(term, this.rateProvider);
	}

	@Override
	public CurrencyConversion getConversion(CurrencyUnit term,
			long targetTimestamp) {
		return new LazyBoundCurrencyConversion(term, this.rateProvider,
				targetTimestamp);
	}

}
