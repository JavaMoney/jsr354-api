/**
 * Copyright (c) 2005, 2012, Werner Keil.
 *
 * Contributors:
 *    Werner Keil - initial API
 */
package javax.money.convert;

import javax.money.Amount;
import javax.money.CurrencyUnit;
import javax.money.convert.spi.ExchangeRateProvider;

/**
 * <p>
 * This class represents a converter between two currencies.
 * </p>
 * 
 * <p>
 * CurrencyUnit converters convert values based upon the current exchange rate
 * {@link CurrencyUnit#getExchangeRate() exchange rate}. If the
 * {@link CurrencyUnit#getExchangeRate() exchange rate} from the target
 * CurrencyUnit to the source CurrencyUnit is not set, conversion fails. In
 * others words, the converter from a CurrencyUnit <code>A</code> to a
 * CurrencyUnit <code>B</code> is independant from the converter from
 * <code>B</code> to <code>A</code>.
 * </p>
 * 
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.2.4
 */
public interface CurrencyConverter {

	// private CurrencyUnit fromJDK(java.util.Currency jdkCurrency) {
	// return CurrencyUnit.getInstance(CurrencyUnit.getCurrencyCode());
	// }

	/**
	 * Returns the source CurrencyUnit.
	 * 
	 * @return the source CurrencyUnit.
	 */
	public CurrencyUnit getSource();

	/**
	 * Returns the target CurrencyUnit.
	 * 
	 * @return the target CurrencyUnit.
	 */
	public CurrencyUnit getTarget();

	public CurrencyConverter inverse();

	public CurrencyConverter negate();

	public double convert(double value);
	
	public double convertInverse(double value);

	public abstract Amount convert(Amount value);

	public boolean isLinear();

	public boolean isIdentity();

	public ExchangeRate getExchangeRate();

}
