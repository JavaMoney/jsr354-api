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
public class CurrencyMismatchException extends IllegalArgumentException {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3277879391197687869L;

	/** The source currrency */
	private CurrencyUnit source;

	/** The target currrency */
	private CurrencyUnit target;

	/**
	 * Constructor.
	 * 
	 * @param source
	 *            the source currency, not {@code null}.
	 * @param target
	 *            the target currency, not {@code null}.
	 */
	public CurrencyMismatchException(CurrencyUnit source, CurrencyUnit target) {
		super("Currency mismatch: " + source + " != " + target);
		if (source == null || target == null) {
			throw new IllegalArgumentException(
					"Source or target currency may not be null.");
		}
		this.source = source;
		this.target = target;
	}

	/**
	 * Access the source {@link CurrencyUnit} instance.
	 * 
	 * @return the source currency, not {@code null}
	 */
	public CurrencyUnit getSourceCurrency() {
		return source;
	}

	/**
	 * Access the target {@link CurrencyUnit} instance.
	 * 
	 * @return the target currency, not {@code null}
	 */
	public CurrencyUnit getTargetCurrency() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CurrencyMismatchException [source=" + source + ", target="
				+ target + "]";
	}

}
