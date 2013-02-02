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
package javax.money.spi;

import javax.money.MonetaryAmount;
import javax.money.CurrencyUnit;

/**
 * The Money provider models the SPI interface that allows to exchange the Java
 * {@link MonetaryAmount} implementation for different platforms or usage
 * scenarios.<br/>
 * Hereby each factory supports one specific representation type.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryAmountFactorySPI {

	/**
	 * The representation type used by this factory instance, e.g.
	 * <code>java.lang.Double</code> or <code>java.math.BigDecimal</code>.
	 */
	public Class<?> getRepresentationType();

	/**
	 * Creates a new {@link MonetaryAmount} instances using the given
	 * {@code CurrencyUnit} and {@code Number}.
	 * 
	 * @param currency
	 *            The currency, not null.
	 * @param number
	 *            The amount's number, not null.
	 * @return The new amount instance.
	 */
	public MonetaryAmount create(CurrencyUnit currency, Number number);

	/**
	 * Creates a new {@link MonetaryAmount} instances using the given
	 * {@code CurrencyUnit} and {@code byte}.
	 * 
	 * @param currency
	 *            The currency, not null.
	 * @param number
	 *            The amount's number, not null.
	 * @return The new amount instance.
	 */
	public MonetaryAmount create(CurrencyUnit currency, byte number);

	/**
	 * Creates a new {@link MonetaryAmount} instances using the given
	 * {@code CurrencyUnit} and {@code short}.
	 * 
	 * @param currency
	 *            The currency, not null.
	 * @param number
	 *            The amount's number, not null.
	 * @return The new amount instance.
	 */
	public MonetaryAmount create(CurrencyUnit currency, short number);

	/**
	 * Creates a new {@link MonetaryAmount} instances using the given
	 * {@code CurrencyUnit} and {@code int}.
	 * 
	 * @param currency
	 *            The currency, not null.
	 * @param number
	 *            The amount's number, not null.
	 * @return The new amount instance.
	 */
	public MonetaryAmount create(CurrencyUnit currency, int number);

	/**
	 * Creates a new {@link MonetaryAmount} instances using the given
	 * {@code CurrencyUnit} and {@code float}.
	 * 
	 * @param currency
	 *            The currency, not null.
	 * @param number
	 *            The amount's number, not null.
	 * @return The new amount instance.
	 */
	public MonetaryAmount create(CurrencyUnit currency, float number);

	/**
	 * Creates a new {@link MonetaryAmount} instances using the given
	 * {@code CurrencyUnit} and {@code double}.
	 * 
	 * @param currency
	 *            The currency, not null.
	 * @param number
	 *            The amount's number, not null.
	 * @return The new amount instance.
	 */
	public MonetaryAmount create(CurrencyUnit currency, double number);

	/**
	 * Creates a new {@link MonetaryAmount} instances using the given decimal
	 * parts.
	 * 
	 * @param major
	 *            The major part of the decimal number.
	 * @param minor
	 *            The minor part of the decimal number, not negative.
	 * @return The new amount instance.
	 */
	public MonetaryAmount create(CurrencyUnit currency, long major, long minor);

}
