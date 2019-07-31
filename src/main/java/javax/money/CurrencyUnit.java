/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2019 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * <h3>Implementation specification</h3>
 * Implementation of this class
 * <ul>
 * <li>are required to implement {@code equals/hashCode} considering the
 * concrete implementation type and currency code.</li>
 * <li>are required to be thread-safe</li>
 * <li>are required to be immutable</li>
 * <li>are required to be comparable</li>
 * <li>should be serializable (on platforms providing serialization).</li>
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
