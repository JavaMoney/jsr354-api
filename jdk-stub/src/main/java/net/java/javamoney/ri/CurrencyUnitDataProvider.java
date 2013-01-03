
package net.java.javamoney.ri;

import java.util.List;

/**
 * Provider for available currencies.
 * This doesn't need to be part of the API at this point, contains only protected or package-local classes.
 * 
 * @author Stephen Colebourne, Werner Keil
 */
abstract class CurrencyUnitDataProvider {

    /**
     * Registers all the currencies known by this provider.
     * 
     * @throws Exception if an error occurs
     */
    protected abstract void registerCurrencies() throws Exception;

    /**
     * Registers a currency allowing it to be used.
     * <p>
     * This method is called by {@link #registerCurrencies()} to perform the
     * actual creation of a currency.
     *
     * @param currencyCode  the currency code, not null
     * @param numericCurrencyCode  the numeric currency code, -1 if none
     * @param decimalPlaces  the number of decimal places that the currency
     *  normally has, from 0 to 3, or -1 for a pseudo-currency
     * @param countryCodes  the country codes to register the currency under, not null
     */
    protected final void registerCurrency(String currencyCode, int numericCurrencyCode, int decimalPlaces, List<String> countryCodes) {
    	// TODO Not Implemented yet
    }

}
