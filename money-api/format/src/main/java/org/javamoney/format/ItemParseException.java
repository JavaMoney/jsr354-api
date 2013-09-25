/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package org.javamoney.format;

/**
 * Exception thrown during parsing of an item.
 * <p>
 * This exception makes no guarantees about immutability or thread-safety.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * @author Stephen Colebourne
 */
public class ItemParseException extends Exception {

	/** Serialization lock. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor taking a message.
	 * 
	 * @param message
	 *            the message
	 */
	public ItemParseException(String message) {
		super(message);
	}

	/**
	 * Constructor taking a message and cause.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the exception cause
	 */
	public ItemParseException(String message, Throwable cause) {
		super(message, cause);
	}

}
