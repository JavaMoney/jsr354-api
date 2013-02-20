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
public class UnknownCurrencyException extends IllegalArgumentException {

	private static final long serialVersionUID = 3277879391197687869L;
	/** The requested namespace. */
	private String namespace;
	/** The requested currency code. */
	private String currencyCode;

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
