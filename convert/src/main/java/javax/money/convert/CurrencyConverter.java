/*
 * Copyright (c) 2012-2013,  Credit Suisse (Anatole Tresch), Werner Keil
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

import javax.money.Amount;
import javax.money.CurrencyUnit;

/**
 * <p>
 * This class represents a converter between two currencies.
 * </p>
 * 
 * <p>
 * CurrencyUnit converters convert values based upon the current exchange rate
 * {@link CurrencyUnit#getExchangeRate() exchange rate}. If the
 * {@link CurrencyUnit#getExchangeRate() exchange rate} from the target
 * CurrencyUnit to the source CurrencyUnit is not set, conversion fails. In
 * others words, the converter from a CurrencyUnit <code>A</code> to a
 * CurrencyUnit <code>B</code> is independant from the converter from
 * <code>B</code> to <code>A</code>.
 * </p>
 * 
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.2.4
 */
public interface CurrencyConverter {

	// private CurrencyUnit fromJDK(java.util.Currency jdkCurrency) {
	// return CurrencyUnit.getInstance(CurrencyUnit.getCurrencyCode());
	// }

	/**
	 * Returns the source CurrencyUnit.
	 * 
	 * @return the source CurrencyUnit.
	 */
	public CurrencyUnit getSource();

	/**
	 * Returns the target CurrencyUnit.
	 * 
	 * @return the target CurrencyUnit.
	 */
	public CurrencyUnit getTarget();

	public CurrencyConverter inverse();

	public CurrencyConverter negate();

	public double convert(double value);
	
	public double convertInverse(double value);

	public abstract Amount convert(Amount value);

	public boolean isLinear();

	public boolean isIdentity();

	public ExchangeRate getExchangeRate();

}
