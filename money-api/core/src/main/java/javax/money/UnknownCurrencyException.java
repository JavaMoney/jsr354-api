/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money;

/**
 * Exception thrown when the requested currency is unknown to the currency
 * system in use.
 * <p>
 * For example, this exception would be thrown when trying to obtain a currency
 * using an unrecognized currency code or locale.
 * 
 * @author Werner Keil
 */
public class UnknownCurrencyException extends IllegalCurrencyException {

	private static final long serialVersionUID = 3277879391197687869L;
	/** The requested namespace. */
	private final String namespace;
	/** The requested currency code. */
	private final String currencyCode;

	/**
	 * Constructor.
	 * 
	 * @param namespace
	 *            the namespace, not {@code null}.
	 * @param currencyCode
	 *            the currencyCode, not {@code null}.
	 */
	public UnknownCurrencyException(String namespace, String currencyCode) {
		super("Unknown currency - " + namespace + ':' + currencyCode);
		if (namespace == null) {
			throw new IllegalArgumentException("namespace may not be null.");
		}
		if (currencyCode == null) {
			throw new IllegalArgumentException("currencyCode may not be null.");
		}
		this.namespace = namespace;
		this.currencyCode = currencyCode;
	}

	/**
	 * Access the namespace of the unknown currency.
	 * 
	 * @return the namespace of the unknown currency.
	 */
	public String getNamespace() {
		return this.namespace;
	}

	/**
	 * Access the currency code of the unknown currency.
	 * 
	 * @return the currency code of the unknown currency.
	 */
	public String getCurrencyCode() {
		return this.currencyCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UnknownCurrencyException [namespace=" + namespace
				+ ", currencyCode=" + currencyCode + "]";
	}

}
