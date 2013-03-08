/*
 * Copyright (c) 2012-2013, Credit Suisse
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
 *  * Neither the name of JSR-354 nor the names of its contributors
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
package javax.money.convert;

import javax.money.CurrencyUnit;
import javax.money.IllegalCurrencyException;

/**
 * Exception thrown when a monetary conversion operation fails.
 * 
 * @author Werner Keil
 * @author Stephen Colebourne
 */
public class CurrencyConversionException extends IllegalCurrencyException {

	/** Serialization lock. */
	private static final long serialVersionUID = -7743240650686883450L;

	/** Source currency. */
	private CurrencyUnit source;
	/** Target currency. */
	private CurrencyUnit target;
	
    /**
     * Constructs an <code>CurrencyConversionException</code> with the
     * specified detail message, source and target currency.
     *
	 * @param source
	 *            the source currency, may be null.
	 *            
	 * @param target
	 *            the target currency, may be null.
	 *            
	 * @param message
	 * 			   the detail message.
	 */
	public CurrencyConversionException(CurrencyUnit source,
			CurrencyUnit target, String message) {
		super(message);
		this.source = source;
		this.target = target;
	}
	
    /**
     * Constructs an <code>CurrencyConversionException</code> with the
     * specified source and target currency.
     *
	 * @param source
	 *            the source currency, may be null.
	 *            
	 * @param target
	 *            the target currency, may be null.
	 */
	public CurrencyConversionException(CurrencyUnit source,
			CurrencyUnit target) {
		super("Cannot convert " + String.valueOf(source) + " into " + String.valueOf(target));
		this.source = source;
		this.target = target;
	}

    /**
     * Constructs a new exception with the specified source and target currency, detail message and
     * cause.
     *
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param source
	 *            the source currency, may be null.            
	 * @param target
	 *            the target currency, may be null.         
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
	 */
	public CurrencyConversionException(CurrencyUnit source,
			CurrencyUnit target, String message, Throwable cause) {
		super(message, cause);
		this.source = source;
		this.target = target;
	}

	/**
	 * Gets the first currency at fault.
	 * 
	 * @return the currency at fault, may be null
	 */
	public CurrencyUnit getSource() {
		return source;
	}

	/**
	 * Gets the second currency at fault.
	 * 
	 * @return the currency at fault, may be null
	 */
	public CurrencyUnit getTarget() {
		return target;
	}

}
