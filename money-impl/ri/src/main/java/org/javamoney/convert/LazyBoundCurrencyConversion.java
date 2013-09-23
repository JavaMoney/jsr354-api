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
package org.javamoney.convert;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

import org.javamoney.convert.ConversionProvider;
import org.javamoney.convert.CurrencyConversion;
import org.javamoney.convert.ExchangeRate;
import org.javamoney.convert.ExchangeRateType;

/**
 * This class defines a {@link CurrencyConversion} that is converting to a
 * specific target {@link CurrencyUnit}. Each instance of this class is bound to
 * a specific {@link ConversionProvider}, a term {@link CurrencyUnit} and a
 * target timestamp.
 * 
 * @author Anatole Tresch
 */
public class LazyBoundCurrencyConversion extends AbstractCurrencyConversion
		implements CurrencyConversion {

	private ConversionProvider rateProvider;
	private CurrencyUnit termCurrency;
	private Long targetTimestamp;

	public LazyBoundCurrencyConversion(CurrencyUnit termCurrency,
			ConversionProvider rateProvider) {
		if (rateProvider == null) {
			throw new IllegalArgumentException("ExchangeRateProvider required.");
		}
		this.rateProvider = rateProvider;
		if (termCurrency == null) {
			throw new IllegalArgumentException("Term CurrencyUnit required.");
		}
		this.termCurrency = termCurrency;
	}

	public LazyBoundCurrencyConversion(CurrencyUnit termCurrency,
			ConversionProvider rateProvider, long targetTimestamp) {
		this(termCurrency, rateProvider);
		this.targetTimestamp = targetTimestamp;
	}

	/**
	 * Access the terminating {@link CurrencyUnit} of this conversion instance.
	 * 
	 * @return the terminating {@link CurrencyUnit} , never null.
	 */
	public CurrencyUnit getTermCurrency() {
		return this.termCurrency;
	}

	/**
	 * Access the target timestamp of this conversion instance.
	 * 
	 * @return the target timestamp , or null for latest rates.
	 */
	public Long getTargetTimestamp() {
		return this.targetTimestamp;
	}

	/**
	 * Get the {@link ExchangeRateType} of this conversion instance.
	 * 
	 * @return the exchange rate type of this conversion instance, never null.
	 */
	public ExchangeRateType getRateType() {
		return this.rateProvider.getExchangeRateType();
	}

	/**
	 * Get the exchange rate type that this provider instance is providing data
	 * for.
	 * 
	 * @return the exchange rate type if this instance.
	 */
	protected ExchangeRate getExchangeRate(MonetaryAmount amount) {
		return this.rateProvider.getExchangeRate(amount.getCurrency(),
				termCurrency);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CurrencyConversion [MonetaryAmount -> MonetaryAmount; rateType="
				+ rateProvider.getExchangeRateType()
				+ ", termCurrency="
				+ termCurrency + ", targetTimestamp=" + targetTimestamp + "]";
	}

}
