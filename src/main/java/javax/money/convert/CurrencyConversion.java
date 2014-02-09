/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.convert;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryException;
import javax.money.MonetaryOperator;
import javax.money.spi.MonetaryConversionsSpi;

/**
 * This interface defines a {@link CurrencyConversion} that is converting to a
 * specific target {@link CurrencyUnit}. Each instance of this class is bound to
 * a specific {@link ExchangeRateProvider}, a term {@link CurrencyUnit} and
 * (optionally) a target timestamp.<br/>
 * This interface serves a an API for the clients, but also must be implemented
 * and registered as SPI to the mechanisms required by the
 * {@link MonetaryConversionsSpi} implementation.
 * <p>
 * Instances of this class are required to be thread-safe, but it is not a
 * requirement that they are serializable. In a EE context they can be
 * implemented using contextual beans.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface CurrencyConversion extends MonetaryOperator {

	/**
	 * Access the terminating {@link CurrencyUnit} of this conversion instance.
	 * 
	 * @return the terminating {@link CurrencyUnit} , never {@code null}.
	 */
	public CurrencyUnit getTermCurrency();

	/**
	 * Access the target {@link ConversionContext} of this conversion instance.
	 * 
	 * @return the target {@link ConversionContext}.
	 */
	public ConversionContext getConversionContext();

	/**
	 * Get the {@link ExchangeRate} applied for the given {@link MonetaryAmount}
	 * .
	 * 
	 * @param sourceAmount
	 *            the amount to be converted.
	 * @return the {@link ExchangeRate} applied.
	 * @throws MonetaryException
	 *             if the amount can not be converted.
	 */
	public ExchangeRate getExchangeRate(MonetaryAmount sourceAmount);

	/**
	 * Get a new {@link CurrencyConversion}using the given
	 * {@link ConversionContext} with additional attributes.
	 * 
	 * @param conversionContext
	 *            the {@link ConversionContext} to be applied, not {@code null}
	 * @return a new instance of {@link CurrencyConversion}, based on this
	 *         instance, but with a changed {@link ConversionContext} to be
	 *         applied. if the amount can not be converted.
	 */
	public CurrencyConversion with(ConversionContext conversionContext);

}
