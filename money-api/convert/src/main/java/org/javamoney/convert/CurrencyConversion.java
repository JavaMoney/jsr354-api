/*
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
import javax.money.MonetaryAdjuster;

import org.javamoney.convert.spi.MonetaryConversionsSingletonSpi;

/**
 * This interface defines a {@link CurrencyConversion} that is converting to a
 * specific target {@link CurrencyUnit}. Each instance of this class is bound to
 * a specific {@link ConversionProvider}, a term {@link CurrencyUnit} and
 * (optionally) a target timestamp.<br/>
 * This interface serves a an API for the clients, but also must be implemented
 * and registered as SPI to the mechanisms required by the
 * {@link MonetaryConversionsSingletonSpi} implementation.
 * <p>
 * Instances of this class are required to be thread-safe, but it is not a
 * requirement that they are serializable. In a EE context they can be
 * implemented using contextual beans.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface CurrencyConversion extends MonetaryAdjuster {

	/**
	 * Access the terminating {@link CurrencyUnit} of this conversion instance.
	 * 
	 * @return the terminating {@link CurrencyUnit} , never {@code null}.
	 */
	public CurrencyUnit getTermCurrency();

	/**
	 * Access the target timestamp of this conversion instance.
	 * 
	 * @return the target timestamp , or {@code null} for latest rates.
	 */
	public Long getTargetTimestamp();

	/**
	 * Get the {@link ExchangeRateType} of this conversion instance.
	 * 
	 * @return the exchange rate type of this conversion instance, never
	 *         {@code null}.
	 */
	public ExchangeRateType getRateType();

}
