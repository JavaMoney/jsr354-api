/**
 * Copyright (c) 2012, Werner Keil.
 *
 * Contributors:
 *    Werner Keil - initial API
 */
package javax.money.convert.spi;

import java.util.Date;

import javax.money.CurrencyUnit;
import javax.money.convert.ExchangeRate;

/**
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author Anatole Tresch
 * @version 0.1.1
 */
public interface ExchangeRateProvider {

	/**
	 * Get an exchange rate for the given parameters.
	 * 
	 * @param source
	 *            the source currency.
	 * @param target
	 *            the target currency.
	 * @param deferred
	 *            If the quote should be deferred.
	 * @return the according exchange rate, or null.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit source,
			CurrencyUnit target, boolean deferred);

	/**
	 * Get an exchange rate for the given parameters.
	 * 
	 * @param source
	 *            the source currency.
	 * @param target
	 *            the target currency.
	 * @param deferred
	 *            If the quote should be deferred.
	 * @param validityDuration
	 *            how long the quote should be considered valid.
	 * @return the according exchange rate, or null.
	 */
	public ExchangeRate getExchangeRate(CurrencyUnit source,
			CurrencyUnit target, boolean deferred, long validityDuration);

	/**
	 * Access a historic exchange rate.
	 * 
	 * @param source
	 *            the source currency.
	 * @param target
	 *            the target currency.
	 * @param targetDate
	 *            the target timestamp for the quote (unitemporal history).
	 * @return the according exchange rate, or null.
	 */
	public ExchangeRate getHistoricExchangeRate(CurrencyUnit source,
			CurrencyUnit target, Date targetDate);
}
