/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.convert;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * This interface defines a {@link CurrencyConversion} that is converting to a
 * specific target {@link CurrencyUnit}. Each instance of this class is bound to
 * a specific {@link ConversionProvider}, a term {@link CurrencyUnit} and a
 * target timestamp.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyConversion extends MonetaryOperator {


	/**
	 * Access the terminating {@link CurrencyUnit} of this conversion instance.
	 * 
	 * @return the terminating {@link CurrencyUnit} , never null.
	 */
	public CurrencyUnit getTermCurrency();

	/**
	 * Access the target timestamp of this conversion instance.
	 * 
	 * @return the target timestamp , or null for latest rates.
	 */
	public Long getTargetTimestamp();

	/**
	 * Get the {@link ExchangeRateType} of this conversion instance.
	 * 
	 * @return the {@link ExchangeRateType} of this conversion instance, never
	 *         null.
	 */
	public ExchangeRateType getRateType();

}
