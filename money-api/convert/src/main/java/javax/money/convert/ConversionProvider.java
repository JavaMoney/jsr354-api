/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.convert;

import java.util.Enumeration;

/**
 * This interface defines access to the exchange and currency conversion logic
 * of JavaMoney.
 * 
 * @author Anatole Tresch
 */
public interface ConversionProvider {

	/**
	 * Access an instance of {@link CurrencyConverter} for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param type
	 *            the exchange rate type that this provider instance is
	 *            providing data for, not {@code null}.
	 * 
	 * @return the {@link ExchangeRateType} if this instance.
	 */
	public CurrencyConverter getCurrencyConverter(ExchangeRateType type);

	/**
	 * Access an instance of {@link ExchangeRateProvider} for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param type
	 *            the exchange rate type that this provider instance is
	 *            providing data for, not {@code null}.
	 * 
	 * @return the {@link ExchangeRateType} if this instance.
	 */
	public ExchangeRateProvider getExchangeRateProvider(ExchangeRateType type);

	/**
	 * Return all supported {@link ExchangeRateType} instances for which
	 * {@link ExchangeRateProvider} instances can be obtained.
	 * 
	 * @return all supported {@link ExchangeRateType} instances, never
	 *         {@code null}.
	 */
	public Enumeration<ExchangeRateType> getSupportedExchangeRateTypes();

	/**
	 * CHecks if a {@link ExchangeRateProvider} can be accessed for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param type
	 *            the required {@link ExchangeRateType}, not {@code null}.
	 * @return true, if a {@link ExchangeRateProvider} for this
	 *         {@link ExchangeRateType} can be obtained from this
	 *         {@link ConversionProvider} instance.
	 */
	public boolean isSupportedExchangeRateType(ExchangeRateType type);

}
