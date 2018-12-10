/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.money;

/**
 * Exception thrown when an error occurs during monetary operations.
 * 
 * @author Werner Keil
 * @author Anatole Tresch
 */
public class MonetaryException extends RuntimeException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -9039026008242959369L;

	/**
     * Creates an instance.
     * 
     * @param message  the message
     */
	public MonetaryException(String message) {
		super(message);
	}

	/**
	 * Creates an instance with the specified detail message and cause.
	 * 
	 * @param message
	 *            the detail message (which is saved for later retrieval by the
	 *            {@link Throwable#getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link Throwable#getCause()} method). (A <tt>null</tt> value
	 *            is permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 */
	public MonetaryException(String message, Throwable cause) {
		super(message, cause);
	}
}
