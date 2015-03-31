/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
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
