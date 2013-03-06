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

import java.util.Locale;

/**
 * This interface extends a {@link MonetaryAmount} by the most common localization
 * methods.
 * 
 * @author Anatole Tresch
 */
public interface LocalizableMonetaryAmount extends MonetaryAmount {

	/**
	 * Gets the localized value of this amount for the default locale, e.g.
	 * CHF 1234.15.
	 * 
	 * @return the localized value of this amount for the default locale
	 */
	public String getLocalized();

	/**
	 * Gets the localized value of this amount for the specified locale, e.g.
	 * CHF 1234.15.
	 * 
	 * @param locale
	 *            the locale for which a localized version for this amount is
	 *            needed, not {@code null}.
	 * @return the localized value of this amount for the specified locale
	 * @exception IllegalArgumentException
	 *                if <code>locale</code> is null
	 */
	public String getLocalized(Locale locale);

	/**
	 * Gets the number that is suitable for displaying this amount for the
	 * default locale.
	 * 
	 * @return the localized number of this amount for the default locale
	 */
	public String getLocalizedNumber();

	/**
	 * Gets the number that is suitable for displaying this amount for the
	 * specified locale.
	 * 
	 * @param locale
	 *            the locale for which a number for this currency is needed, not
	 *            {@code null}{.
	 * @return the display name of this currency for the specified locale
	 * @exception IllegalArgumentException
	 *                if <code>locale</code> is null
	 */
	public String getLocalizedNumber(Locale locale);

	/**
	 * Access the {@link MonetaryAmount} {@link CurrencyUnit} as a
	 * {@link LocalizableCurrencyUnit}.
	 * 
	 * @return A {@link LocalizableCurrencyUnit} corresponding to the
	 *         {@link CurrencyUnit} of this instance, never {@code null}
	 */
	public LocalizableCurrencyUnit getLocalizableCurrencyUnit();
}
