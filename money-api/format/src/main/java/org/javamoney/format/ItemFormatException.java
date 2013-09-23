/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package org.javamoney.format;

import java.io.IOException;

import javax.money.MonetaryException;

/**
 * Exception thrown during monetary formatting.
 *
 * @author Stephen Colebourne, Werner Keil
 */
public class ItemFormatException extends MonetaryException {

    /**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2966663514205132233L;

	/**
     * Constructor taking a message.
     * 
     * @param message  the message
     */
    public ItemFormatException(String message) {
    	super(message);
    }

    /**
     * Constructor taking a message and cause.
     * 
     * @param message  the message
     * @param cause  the exception cause
     */
    public ItemFormatException(String message, Throwable cause) {
    	super(message, cause);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the cause of this exception was an IOException, and if so re-throws it
     * <p>
     * This method is useful if you call a printer with an open stream or
     * writer and want to ensure that IOExceptions are not lost.
     * <pre>
     * try {
     *   printer.print(writer, money);
     * } catch (CalendricalFormatException ex) {
     *   ex.rethrowIOException();
     *   // if code reaches here exception was caused by issues other than IO
     * }
     * </pre>
     * Note that calling this method will re-throw the original IOException,
     * causing this MoneyFormatException to be lost.
     *
     * @throws IOException if the cause of this exception is an IOException
     */
    public void rethrowIOException() throws IOException {
    	// TODO Not Implemented yet
    }

}
