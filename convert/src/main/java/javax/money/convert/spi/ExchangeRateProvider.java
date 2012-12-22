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
	
	public ExchangeRate getExchangeRate(CurrencyUnit source,
			CurrencyUnit target);

	public ExchangeRate getExchangeRate(CurrencyUnit source,
			CurrencyUnit target, Date targetDate);
}
