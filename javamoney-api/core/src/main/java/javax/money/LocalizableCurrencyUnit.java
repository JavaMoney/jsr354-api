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
 * This interface extends a {@link CurrencyUnit} by the most common localization
 * methods, similar to the ones defined in java.util.Currency.
 * 
 * @author Anatole Tresch
 */
public interface LocalizableCurrencyUnit extends CurrencyUnit {

	/**
	 * Gets the name that is suitable for displaying this currency for the
	 * specified locale. If there is no suitable display name found for the
	 * specified locale, the ISO 4217 currency code is returned.
	 * 
	 * @param locale
	 *            the locale for which a display name for this currency is
	 *            needed
	 * @return the display name of this currency for the specified locale
	 * @exception NullPointerException
	 *                if <code>locale</code> is null
	 * @since 1.7
	 */
	public String getDisplayName(Locale locale);

	/**
	 * Gets the symbol of this currency for the specified locale. For example,
	 * for the US Dollar, the symbol is "$" if the specified locale is the US,
	 * while for other locales it may be "US$". If no symbol can be determined,
	 * the ISO 4217 currency code is returned.
	 * 
	 * @param locale
	 *            the locale for which a display name for this currency is
	 *            needed
	 * @return the symbol of this currency for the specified locale
	 * @exception NullPointerException
	 *                if <code>locale</code> is null
	 */
	public String getSymbol(Locale locale);

}
