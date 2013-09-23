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
package net.java.javamoney.ri.convert;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.CurrencyConversionException;
import javax.money.convert.ExchangeRate;

/**
 * Abstract base class used for implementing currency conversion.
 * 
 * @author Anatole Tresch
 */
public abstract class AbstractCurrencyConversion implements CurrencyConversion {

	/**
	 * Get the exchange rate type that this {@link MonetaryOperator} instance is
	 * using for conversion.
	 * 
	 * @see #apply(MonetaryAmount)
	 * @return the {@link ExchangeRate} to be used, or null, if this conversion
	 *         is not supported (will lead to a
	 *         {@link CurrencyConversionException}.
	 */
	protected abstract ExchangeRate getExchangeRate(MonetaryAmount amount);

	/**
	 * Method that converts the source {@link MonetaryAmount} to an
	 * {@link MonetaryAmount} based on the {@link ExchangeRate} of this
	 * conversion.<br/>
	 * 
	 * @see #getExchangeRate(MonetaryAmount)
	 * @param amount
	 *            The source amount
	 * @return The converted amount, never null.
	 * @throws CurrencyConversionException
	 *             if conversion failed, or the required data is not available.
	 */
	public MonetaryAmount apply(MonetaryAmount amount) {
		ExchangeRate rate = getExchangeRate(amount);
		if (rate == null || !amount.getCurrency().equals(rate.getBase())) {
			throw new CurrencyConversionException(amount.getCurrency(),
					rate == null ? null : rate.getTerm(), null);
		}
		return amount.with(rate.getTerm(), amount.multiply(rate.getFactor())
				.asType(BigDecimal.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " [MonetaryAmount -> MonetaryAmount"
				+ "]";
	}

}
