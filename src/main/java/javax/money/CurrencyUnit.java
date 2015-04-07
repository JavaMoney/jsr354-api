/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2015, Credit Suisse All rights
 * reserved.
 */
package javax.money;

/**
 * A unit of currency.
 * <p>
 * This interface represents a unit of currency such as the British Pound, Euro,
 * US Dollar, Bitcoin or other. It provides interoperability between different
 * implementations.
 * <p>
 * Currencies can be distinguished by separate {@link #getCurrencyCode()} codes,
 * similar to {@link java.util.Currency}.
 * <h4>Implementation specification</h4>
 * Implementation of this class
 * <ul>
 * <li>are required to implement {@code equals/hashCode} considering the
 * concrete implementation type and currency code.
 * <li>are required to be thread-safe
 * <li>are required to be immutable
 * <li>are required to be comparable
 * <li>should be serializable (on platforms providing serialization).
 * </ul>
 * 
 * @author Werner Keil
 * @author Stephen Colebourne
 * @author Anatole Tresch
 * 
 * @version 1.0
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Currency">Wikipedia: Currency</a>
 */
public interface CurrencyUnit extends Comparable<CurrencyUnit>{

   /**
    * Gets the unique currency code, the effective code depends on the
    * currency.
    * <p>
    * Since each currency is identified by this code, the currency code is
    * required to be defined for every {@link CurrencyUnit} and not
    * {@code null} or empty.
    * <p>
    * For ISO codes the 3-letter ISO code should be returned. For non ISO
    * currencies no constraints are defined.
    * 
    * @return the currency code, never {@code null}. For ISO-4217 this this
    *         will be the three letter ISO-4217 code. However, alternate
    *         currencies can have different codes. Also there is no constraint
    *         about the formatting of alternate codes, despite the fact that
    *         the currency codes must be unique.
    */
    String getCurrencyCode();

   /**
    * Gets a numeric currency code. within the ISO-4217 name space, this equals
    * to the ISO numeric code. In other currency name spaces this number may be
    * different, or even undefined (-1).
    * <p>
    * The numeric code is an optional alternative to the standard currency
    * code. If defined, the numeric code is required to be unique.
    * <p>
    * This method matches the API of {@link java.util.Currency}.
    * 
    * @return the numeric currency code
    */
    int getNumericCode();

   /**
    * Gets the number of fractional digits typically used by this currency.
    * <p>
    * Different currencies have different numbers of fractional digits by
    * default. For example, 'GBP' has 2 fractional digits, but 'JPY' has zero.
    * virtual currencies or those with no applicable fractional are indicated
    * by -1.
    * <p>
    * This method matches the API of {@link java.util.Currency}.
    * 
    * @return the fractional digits, from 0 to 9 (normally 0, 2 or 3), or 0 for
    *         pseudo-currencies.
    * 
    */
    int getDefaultFractionDigits();

    /**
     * Returns the {@link javax.money.CurrencyContext} of a currency. This context contains additional information
     * about the type and capabilities of a CurrencyUnit, e.g. its provider and more.
     * @return the currency's context, never null.
     */
    CurrencyContext getContext();
}
