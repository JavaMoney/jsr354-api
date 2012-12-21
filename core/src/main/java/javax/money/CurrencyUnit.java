package javax.money;

import java.util.Currency;

/**
 * * A unit of currency.
 * <p>
 * * This class represents a unit of currency such as the British Pound, Euro or
 * US Dollar, Linden Dollars, BitCoins or other.
 * <p>
 * * The set of loaded currencies is provided by an instance of
 * {@link CurrencyUnitDataProvider}. * The provider used is determined by the
 * system property {@code javax.money.CurrencyUnitDataProvider} * which should
 * be the fully qualified class name of the provider. The default provider loads
 * the first * resource named {@code /MoneyData.csv} on the classpath.
 * <p>
 * 
 * @author Werner Keil
 * @author Stephen Colebourne
 * @author Anatole Tresch
 */
public interface CurrencyUnit extends Comparable<CurrencyUnit> {

	/**
	 * Defines the name space for the currency code. If the CurrencyUnit is an
	 * instance of {@link java.util.Currency} this method returns 'ISO-4217',
	 * whereas for other currency schemes, e.g. virtual currencies or internal
	 * legacy currencies different values are possible.
	 * 
	 * @return the name space of the currency, never null.
	 */
	public String getNamespace();

	/**
	 * Gets the currency code, the effective code depends on the currency and
	 * the name space. It is possible that the two currency may have the same
	 * code, but different name spaces.
	 * <p>
	 * Each currency is uniquely identified within its name space by this code.
	 * 
	 * @return the currency code. Instances of {@link java.util.Currency} return
	 *         the three letter ISO-4217 or equivalent currency code, never
	 *         null.
	 */
	public String getCurrencyCode();

	/**
	 * * Gets the ISO-4217 numeric currency code.
	 * <p>
	 * * The numeric code is an alternative to the standard three letter code. * @return
	 * the numeric currency code
	 */
	public int getNumericCode();

	/**
	 * Gets the number of fractional digits typically used by this currency.
	 * <p>
	 * Different currencies have different numbers of fractional digits by
	 * default. * For example, 'GBP' has 2 fractional digits, but 'JPY' has
	 * zero. * Pseudo-currencies are indicated by -1. *
	 * <p>
	 * This method matches the API of {@link Currency}. * The alternative method
	 * {@link #getDecimalPlaces()} may be more useful. * *
	 * 
	 * @return the fractional digits, from 0 to 9 (normally 0, 2 or 3), or -1
	 *         for pseudo-currencies
	 * 
	 */
	public int getDefaultFractionDigits();

	/**
	 * Checks if this is a virtual currency, such as BitCoins or Linden Dollars.
	 * 
	 * @return true if this is a virtual currency.
	 */
	public boolean isVirtualCurrency();

}
