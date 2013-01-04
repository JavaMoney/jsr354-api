/*
<<<<<<< HEAD
 * Copyright (c) 2012, Credit Suisse (Anatole Tresch), Werner Keil
=======
 * Copyright (c) 2012-2013, Credit Suisse
>>>>>>> a63812e1178535e10625662ed6299a6669e1d3fa
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
<<<<<<< HEAD
 *  * Neither the name of JSR-310 nor the names of its contributors
=======
 *  * Neither the name of JSR-354 nor the names of its contributors
>>>>>>> a63812e1178535e10625662ed6299a6669e1d3fa
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
package javax.money.format;

import java.io.IOException;

/**
<<<<<<< HEAD
 * Exception thrown during monetary formatting.
 * <p>
 * This exception makes no guarantees about immutability or thread-safety.
 *
 * @author Stephen Colebourne, Werner Keil
=======
 * Exception thrown during formatting.
 * <p>
 * This exception makes no guarantees about immutability or thread-safety.
 *
 * @author Stephen Colebourne
>>>>>>> a63812e1178535e10625662ed6299a6669e1d3fa
 */
public class FormatException extends RuntimeException {

    /** Serialization lock. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor taking a message.
     * 
     * @param message  the message
     */
    public FormatException(String message) {
    	// TODO Not Implemented yet
    }

    /**
     * Constructor taking a message and cause.
     * 
     * @param message  the message
     * @param cause  the exception cause
     */
    public FormatException(String message, Throwable cause) {
    	// TODO Not Implemented yet
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
