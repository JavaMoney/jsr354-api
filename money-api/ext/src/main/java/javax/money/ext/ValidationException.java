/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

import javax.money.MonetaryException;

/**
 * Exception that is thrown when a {@link CompoundValue} or a value in a
 * {@link CompoundValue} could not be validated successfully.
 * 
 * @author Anatole Tresch
 */
public class ValidationException extends MonetaryException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5308404907335737203L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the exception message
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the exception message
	 * @param cause
	 *            the cause, or {@code null}
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
