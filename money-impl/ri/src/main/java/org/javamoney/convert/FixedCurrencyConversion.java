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

import java.math.BigDecimal;
import java.math.MathContext;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Money;

/**
 * This interface defines access to the exchange conversion logic of JavaMoney.
 * It is provided by the Money singleton. It is provided by the Money singleton.
 * 
 * @author Anatole Tresch
 */
public class FixedCurrencyConversion extends AbstractCurrencyConversion {

	private ExchangeRate rate;

	public FixedCurrencyConversion(ExchangeRate rate) {
		if (rate == null) {
			throw new IllegalArgumentException("Rate required.");
		}
		this.rate = rate;
	}

	@Override
	public CurrencyUnit getTermCurrency() {
		return this.rate.getTerm();
	}

	@Override
	public Long getTargetTimestamp() {
		return this.rate.getValidFromMillis();
	}

	@Override
	public ExchangeRateType getRateType() {
		return this.rate.getExchangeRateType();
	}

	/**
	 * Get the exchange rate type that this provider instance is providing data
	 * for.
	 * 
	 * @return the {@link ExchangeRateType} if this instance.
	 */
	protected ExchangeRate getExchangeRate(MonetaryAmount amount) {
		if (!amount.getCurrency().equals(this.rate.getBase())) {
			throw new CurrencyConversionException(amount.getCurrency(),
					rate.getTerm(), null);
		}
		return this.rate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FixedCurrencyConversion [MonetaryAmount -> MonetaryAmount; rate="
				+ rate + "]";
	}

	@Override
	public MonetaryAmount adjustInto(MonetaryAmount amount) {
		ExchangeRate rate = getExchangeRate(amount);
		Money money = Money.from(amount);
		return Money.of(rate.getTerm(), money.asType(BigDecimal.class)
				.multiply(rate.getFactor(), MathContext.DECIMAL64));
	}
}
