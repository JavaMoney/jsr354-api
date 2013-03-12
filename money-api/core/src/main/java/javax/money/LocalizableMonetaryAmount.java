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
