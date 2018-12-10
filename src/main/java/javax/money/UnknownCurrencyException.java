/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
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

import java.util.Locale;
import java.util.Objects;

/**
 * Exception thrown when a currency code cannot be resolved into a {@link CurrencyUnit}.
 * 
 * @author Werner Keil
 * @author Anatole Tresch
 * @version 0.8
 */
public class UnknownCurrencyException extends MonetaryException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1421993009305080653L;
	/** The invalid currency code requested. */
	private final String currencyCode;
	/** The invalid {@link Locale} requested. */
	private final Locale locale;

	/**
	 * Creates a new exception instance when a {@link CurrencyUnit} could not be evaluated given a
	 * currency code.
	 * 
	 * @see Monetary#getCurrency(String, String...)
	 * @param code
	 *            The unknown currency code (the message is constructed automatically), not null.
	 */
	public UnknownCurrencyException(String code) {
		super("Unknown currency code: " + code);
		this.currencyCode = code;
		this.locale = null;
	}

	/**
	 * Creates a new exception instance when a {@link CurrencyUnit} could not be evaluated given a
	 * (country) {@link Locale}.
	 * 
	 * @see Monetary#getCurrency(Locale,String...)
	 * @param locale
	 *            The unknown {@link Locale}, for which a {@link CurrencyUnit} was queried (the
	 *            message is constructed automatically), not null.
	 */
	public UnknownCurrencyException(Locale locale) {
		super("No currency for found for Locale: " + locale);
		this.locale = locale;
		this.currencyCode = null;
	}

	/**
	 * Access the invalid currency code.
	 * 
	 * @return the invalid currency code, or {@code null}.
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * Access the invalid {@link Locale}.
	 * 
	 * @return the invalid {@link Locale}, or {@code null}.
	 */
	public Locale getLocale() {
		return locale;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
        if (Objects.isNull(locale)) {
		    return "UnknownCurrencyException [currencyCode=" + currencyCode + "]";
        }
        else{
            return "UnknownCurrencyException [locale=" + locale + "]";
        }
	}

}
