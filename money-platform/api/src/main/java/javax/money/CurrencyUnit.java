/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package javax.money;

/**
 * A unit of currency.
 * <p>
 * This interface represents a unit of currency such as the British Pound, Euro,
 * US Dollar, Bitcoin or other.
 * <p>
 * Currencies can be distinguished within separate arbitrary currency name
 * spaces, whereas as {@link #ISO_NAMESPACE} will be the the most commonly used
 * one, similar to {@link java.util.Currency}.
 * 
 * @version 0.4
 * @author Werner Keil
 * @author Stephen Colebourne
 * @author Anatole Tresch
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Currency">Wikipedia: Currency</a>
 */
public interface CurrencyUnit {

    /**
     * Defines the name space for the currency code. If the CurrencyUnit is an
     * instance of <type>java.util.Currency</type> this method returns
     * 'ISO-4217', whereas for other currency schemes, e.g. virtual currencies
     * or internal legacy currencies different values are possible.
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
     * <p>
     * This method matches the API of <type>java.util.Currency</type>.
     * 
     * @return the currency code. Instances of <type>java.util.Currency</type>
     *         return the three letter ISO-4217 or equivalent currency code,
     *         never null.
     */
    public String getCurrencyCode();

    /**
     * Gets a numeric currency code. within the ISO-4217 name space, this equals
     * to the ISO numeric code. In other currency name spaces this number may be
     * different, or even undefined (-1).
     * <p>
     * The numeric code is an optional alternative to the standard currency
     * code.
     * <p>
     * This method matches the API of <type>java.util.Currency</type>.
     * 
     * @return the numeric currency code
     */
    public int getNumericCode();

    /**
     * Gets the number of fractional digits typically used by this currency.
     * <p>
     * Different currencies have different numbers of fractional digits by
     * default. * For example, 'GBP' has 2 fractional digits, but 'JPY' has
     * zero. * virtual currencies or those with no applicable fractional are
     * indicated by -1. *
     * <p>
     * This method matches the API of <type>java.util.Currency</type>.
     * 
     * @return the fractional digits, from 0 to 9 (normally 0, 2 or 3), or -1
     *         for pseudo-currencies.
     * 
     */
    public int getDefaultFractionDigits();

    /**
     * Get the rounding for when using this currency in bar.
     * 
     * @return the cash rounding, or -1, if not defined.
     */
    public int getCashRounding();

    /**
     * Checks if this is a currency that has a legal tender.
     * 
     * @return true if this currency has a legal tender.
     */
    public boolean isLegalTender();

    /**
     * Checks if this is a virtual currency, such as Bitcoin or Linden Dollar.
     * 
     * @return true if this is a virtual currency.
     */
    public boolean isVirtual();

}
