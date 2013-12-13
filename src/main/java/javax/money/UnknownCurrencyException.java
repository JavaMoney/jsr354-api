/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

/**
 * Exception thrown when a currency code cannot be resolved into a
 * {@link CurrencyUnit}.
 * 
 * @author Anatole Tresch
 */
public class UnknownCurrencyException extends MonetaryException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1421993009305080653L;
	/** The invalid currency code requested. */
	private final String currencyCode;

	/**
	 * Creates a new exception instance.
	 * 
	 * @param message
	 *            The error message
	 * @param code
	 *            The unknown currency code, not null.
	 */
	public UnknownCurrencyException(String code) {
		super("Unknown currency code: " + code);
		this.currencyCode = code;
	}

	/**
	 * Access the invalid currency code.
	 * 
	 * @return the invalid currency code, or {@null}.
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UnknownCurrencyException [currencyCode=" + currencyCode + "]";
	}

}
