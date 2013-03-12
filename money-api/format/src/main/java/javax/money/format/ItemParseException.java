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
