/*
 * JSR 354: Money and Currency API
 */
package javax.money;

import java.util.Locale;


/**
 * A unit of currency.
 * <p>
 * This class represents a unit of currency such as the British Pound, Euro
 * or US Dollar.
 * <p>
 * The set of loaded currencies is provided by an instance of {@link CurrencyUnitDataProvider}.
 * The provider used is determined by the system property {@code javax.money.CurrencyUnitDataProvider}
 * which should be the fully qualified class name of the provider. The default provider loads the first
 * resource named {@code /MoneyData.csv} on the classpath.
 * <p>
 * @author Werner Keil
 * @author Stephen Colebourne
 */
public interface CurrencyUnit extends Comparable<CurrencyUnit> {


    //-----------------------------------------------------------------------
    /**
     * Gets the (ISO-4217) three letter currency code.
     * <p>
     * Each currency is uniquely identified by a three-letter code.
     * 
     * @return the three letter ISO-4217 or equivalent currency code, never null
     */
    public String getCode();

    /**
     * Gets the ISO-4217 numeric currency code.
     * <p>
     * The numeric code is an alternative to the standard three letter code.
     * 
     * @return the numeric currency code
     */
    public int getNumericCode();

    /**
     * Gets the ISO-4217 numeric currency code as a three digit string.
     * <p>
     * This formats the numeric code as a three digit string prefixed by zeroes if necessary.
     * If there is no valid code, then an empty string is returned.
     * 
     * @return the three digit numeric currency code, empty is no code, never null
     */
    //public String getNumeric3Code();
    
    /**
     * Gets the name that is suitable for displaying this currency for
     * the default locale.  If there is no suitable display name found
     * for the default locale, the ISO 4217 currency code is returned.
     *
     * @return the display name of this currency for the default locale
     */
    public String getDisplayName();
    
    /**
     * Gets the number of decimal places typically used by this currency.
     * <p>
     * Different currencies have different numbers of decimal places by default.
     * For example, 'GBP' has 2 decimal places, but 'JPY' has zero.
     * Pseudo-currencies will return zero.
     * <p>
     * See also {@link #getDefaultFractionDigits()}.
     * 
     * @return the decimal places, from 0 to 9 (normally 0, 2 or 3)
     */
    public int getDecimalPlaces();
   
    /**
     * Checks if this is a pseudo-currency.
     * TODO maybe find a better name for this?
     * @return true if this is a pseudo-currency
     */
    public boolean isPseudoCurrency();

    //-----------------------------------------------------------------------
    /**
     * Gets the ISO-4217 three-letter currency code.
     * <p>
     * This method matches the API of {@link Currency}.
     * 
     * @return the currency code, never null
     */
    public String getCurrencyCode();

    /**
     * Gets the number of fractional digits typically used by this currency.
     * <p>
     * Different currencies have different numbers of fractional digits by default.
     * For example, 'GBP' has 2 fractional digits, but 'JPY' has zero.
     * Pseudo-currencies are indicated by -1.
     * <p>
     * This method matches the API of {@link Currency}.
     * The alternative method {@link #getDecimalPlaces()} may be more useful.
     * 
     * @return the fractional digits, from 0 to 9 (normally 0, 2 or 3), or -1 for pseudo-currencies
     */
    public int getDefaultFractionDigits();

    //-----------------------------------------------------------------------
    /**
     * Gets the symbol for this locale from the JDK.
     * <p>
     * If this currency doesn't have a JDK equivalent, then the currency code
     * is returned.
     * <p>
     * This method matches the API of {@link Currency}.
     * 
     * @return the JDK currency instance, never null
     */
    public String getSymbol();

    /**
     * Gets the symbol for this locale from the JDK.
     * <p>
     * If this currency doesn't have a JDK equivalent, then the currency code
     * is returned.
     * <p>
     * This method matches the API of {@link Currency}.
     * 
     * @param locale  the locale to get the symbol for, not null
     * @return the JDK currency instance, never null
     */
    public String getSymbol(Locale locale);
}
