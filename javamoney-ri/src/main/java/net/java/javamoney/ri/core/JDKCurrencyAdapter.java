/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial implementation.
 *    Werner Keil - extension and adjustment.
 */
package net.java.javamoney.ri.core;

import java.io.Serializable;
import java.util.Collections;
import java.util.Currency;
import java.util.Enumeration;

import javax.money.CurrencyUnit;

/**
 * Adapter that implements the new {@link CurrencyUnit} interface using the
 * JDK's {@link Currency}.
 * 
 * @author Anatole Tresch
 */
public final class JDKCurrencyAdapter implements CurrencyUnit, Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2523936311372374236L;

	/**
	 * ISO 4217 currency code for this currency.
	 * 
	 * @serial
	 */
	private Currency currency;

	/**
	 * Private constructor.
	 * 
	 * @param currency
	 */
	JDKCurrencyAdapter(Currency currency) {
		if (currency == null) {
			throw new IllegalArgumentException("Currency required.");
		}
		this.currency = currency;
	}

	// TODO could we harmonize this like in Currency by calling getInstance()?
	public static CurrencyUnit getInstance(Currency currency) {
		// TODO implement caching!
		return new JDKCurrencyAdapter(currency);
	}

	// TODO could we harmonize this like in Currency by calling getInstance()?
	public static CurrencyUnit getInstance(String isoCurrency) {
		// TODO implement caching!
		return new JDKCurrencyAdapter(Currency.getInstance(isoCurrency));
	}

	public boolean isVirtual() {
		return false;
	}

	/**
	 * Get the namepsace of this {@link CurrencyUnit}, returns 'ISO-4217'.
	 */

	public String getNamespace() {
		return "ISO-4217";
	}

	public Long getValidFrom() {
		return null;
	}

	public Long getValidUntil() {
		return null;
	}

	public int compareTo(CurrencyUnit currency) {
		// TODO Auto-generated method stub
		int compare = getNamespace().compareTo(currency.getNamespace());
		if (compare == 0) {
			compare = getCurrencyCode().compareTo(currency.getCurrencyCode());
		}
		// TODO check for validFrom, until
		return compare;
	}

	public String getCurrencyCode() {
		return this.currency.getCurrencyCode();
	}

	public int getNumericCode() {
		return this.currency.getNumericCode();
	}

	public int getDefaultFractionDigits() {
		return this.currency.getDefaultFractionDigits();
	}

	public String toString() {
		return this.currency.toString();
	}

	@Override
	public <T> T getAttribute(String key, Class<T> type) {
		return null;
	}

	@Override
	public Enumeration<String> getAttributeKeys() {
		return Collections.emptyEnumeration();
	}

	@Override
	public Class<?> getAttributeType(String key) {
		return null;
	}
}
