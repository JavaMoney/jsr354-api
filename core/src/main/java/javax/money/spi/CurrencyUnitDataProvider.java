/*
 * Copyright (c) 2012, Credit Suisse (Anatole Tresch), Werner Keil
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.money.spi;

import java.util.List;

/**
 * Provider for available currencies.
 * This is part of the SPI
 * 
 * @author Stephen Colebourne, Werner Keil
 */
public abstract class CurrencyUnitDataProvider {

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
