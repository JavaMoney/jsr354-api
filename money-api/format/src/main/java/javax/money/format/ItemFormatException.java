/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * Contributors:
 *    Anatole Tresch - initial version.
 */
package javax.money.format;

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
