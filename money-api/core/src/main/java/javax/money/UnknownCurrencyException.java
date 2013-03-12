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
