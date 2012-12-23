/**
 * Copyright (c) 2005, 2012, Werner Keil.
 * All rights reserved. 
 * Contributors:
 *    Werner Keil - initial API and implementation
 *    Anatole Tresch - extensions and adaptions.
 */
package javax.money.convert;

import javax.money.CurrencyUnit;

/**
 * @author Werner Keil
 * @author Anatole Tresch
 * @version 0.2.2
 */
public interface ExchangeRate {

	/**
	 * Get the source currency.
	 * 
	 * @return the source currency.
	 */
	public CurrencyUnit getSourceCurrency();

	/**
	 * Get the target currency.
	 * 
	 * @return the target currency.
	 */
	public CurrencyUnit getTargetCurrency();

	/**
	 * Get the exchange factor.
	 * 
	 * @return the exchange factor.
	 */
	public <T> T getAdaptedFactor(Class<T> targetAdapterClass);

	/**
	 * Access the rate's factor.
	 * 
	 * @return the factor for this exchange rate.
	 */
	public Number getFactor();

	/**
	 * Access the chain of exchange rates.
	 * 
	 * @return the chain of rates, in case of a derived rate, this may be
	 *         several instances.
	 */
	public ExchangeRate[] getExchangeRateChain();

	/**
	 * Access the type of exchange rate.
	 * 
	 * @return the type of this rate.
	 */
	public ExchangeRateType getExchangeRateType();

}
