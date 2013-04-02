/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
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
 *    Anatole Tresch - initial implementation.
 */
package net.java.javamoney.ri.convert.provider;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.convert.ConversionProvider;
import javax.money.convert.CurrencyConversionException;
import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.ExchangeRateType;
import javax.money.provider.Monetary;
import javax.money.provider.MonetaryAmountProvider;

/**
 * Implementation of a {@link ConversionProvider} that is simply adapting an
 * existing {@link ExchangeRateProvider}.
 * 
 * @author Anatole Tresch
 */
public class DefaultCurrencyConverter implements CurrencyConverter {

	private ExchangeRateType exchangeRateType;

	public DefaultCurrencyConverter(ExchangeRateType exchangeRateType) {
		if (exchangeRateType == null) {
			throw new IllegalArgumentException(
					"exchangeRateType may not be null.");
		}
		this.exchangeRateType = exchangeRateType;
	}

	@Override
	public ExchangeRateType getExchangeRateType() {
		return exchangeRateType;
	}

	@Override
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target) {
		ExchangeRateProvider provider = Monetary.getConversionProvider()
				.getExchangeRateProvider(exchangeRateType);
		if (provider == null) {
			throw new CurrencyConversionException(amount.getCurrency(), target,
					null, "Undefined exchange rate type: "
							+ this.exchangeRateType);
		}
		ExchangeRate rate = provider.getExchangeRate(amount.getCurrency(),
				target);
		if (rate == null) {
			throw new CurrencyConversionException(amount.getCurrency(), target,
					null, "No rate available.");
		}
		MonetaryAmountProvider amountProvider = Monetary
				.getMonetaryAmountProvider(amount.getNumberType());
		return amountProvider.getMonetaryAmountFactory().get(target,
				amount.multiply(rate.getFactor()).asType(Number.class));
	}

	@Override
	public MonetaryAmount convert(MonetaryAmount amount, CurrencyUnit target,
			Long timestamp) {
		ExchangeRateProvider provider = Monetary.getConversionProvider()
				.getExchangeRateProvider(exchangeRateType);
		if (provider == null) {
			throw new CurrencyConversionException(amount.getCurrency(), target,
					timestamp, "Undefined exchange rate type: "
							+ this.exchangeRateType);
		}
		ExchangeRate rate = provider.getExchangeRate(amount.getCurrency(),
				target, timestamp);
		if (rate == null) {
			throw new CurrencyConversionException(amount.getCurrency(), target,
					timestamp, "Timestamp: " + timestamp);
		}
		MonetaryAmountProvider amountProvider = Monetary
				.getMonetaryAmountProvider(amount.getNumberType());
		return amountProvider.getMonetaryAmountFactory().get(target,
				amount.multiply(rate.getFactor()).asType(Number.class));
	}

	@Override
	public MonetaryAmount convert(Number amount, CurrencyUnit sourceCurrency,
			CurrencyUnit targetCurrency) {
		ExchangeRateProvider provider = Monetary.getConversionProvider()
				.getExchangeRateProvider(exchangeRateType);
		if (provider == null) {
			throw new CurrencyConversionException(sourceCurrency,
					targetCurrency, null, "Undefined exchange rate type: "
							+ this.exchangeRateType);
		}
		ExchangeRate rate = provider.getExchangeRate(sourceCurrency,
				targetCurrency);
		if (rate == null) {
			throw new CurrencyConversionException(sourceCurrency,
					targetCurrency, null);
		}
		MonetaryAmountProvider amountProvider = Monetary
				.getMonetaryAmountProvider();
		return amountProvider.getMonetaryAmountFactory()
				.get(targetCurrency, amount).multiply(rate.getFactor());
	}

	@Override
	public MonetaryAmount convert(Number amount, CurrencyUnit sourceCurrency,
			CurrencyUnit targetCurrency, Long timestamp) {
		ExchangeRateProvider provider = Monetary.getConversionProvider()
				.getExchangeRateProvider(exchangeRateType);
		if (provider == null) {
			throw new CurrencyConversionException(sourceCurrency,
					targetCurrency, timestamp, "Undefined exchange rate type: "
							+ this.exchangeRateType);
		}
		ExchangeRate rate = provider.getExchangeRate(sourceCurrency,
				targetCurrency, timestamp);
		if (rate == null) {
			throw new CurrencyConversionException(sourceCurrency,
					targetCurrency, timestamp, "Timestamp: " + timestamp);
		}
		MonetaryAmountProvider amountProvider = Monetary
				.getMonetaryAmountProvider();
		return amountProvider.getMonetaryAmountFactory()
				.get(targetCurrency, amount).multiply(rate.getFactor());
	}

}
