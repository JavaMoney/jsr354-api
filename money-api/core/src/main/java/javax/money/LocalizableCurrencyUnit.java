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
 * This interface extends a {@link CurrencyUnit} by the most common localization
 * methods, similar to the ones defined in <code>java.util.Currency</code>.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface LocalizableCurrencyUnit extends CurrencyUnit {
	
    /**
     * Gets the symbol of this currency for the default locale.
     * For example, for the US Dollar, the symbol is "$" if the default
     * locale is the US, while for other locales it may be "US$". If no
     * symbol can be determined, the currency code is returned.
     *
     * @return the symbol of this currency for the default locale
     */
    public String getSymbol();
    
	/**
	 * Gets the symbol of this currency for the specified locale. For example,
	 * for the US Dollar, the symbol is "$" if the specified locale is the US,
	 * while for other locales it may be "US$". If no symbol can be determined,
	 * the currency code is returned.
	 * 
	 * @param locale
	 *            the locale for which a display name for this currency is
	 *            needed
	 * @return the symbol of this currency for the specified locale
	 * @exception NullPointerException
	 *                if <code>locale</code> is null
	 */
	public String getSymbol(Locale locale);
	
    /**
     * Gets the name that is suitable for displaying this currency for
     * the default locale.  If there is no suitable display name found
     * for the default locale, the currency code is returned.
     *
     * @return the display name of this currency for the default locale
     * @since 1.7
     */
    public String getDisplayName();
    
	/**
	 * Gets the name that is suitable for displaying this currency for the
	 * specified locale. If there is no suitable display name found for the
	 * specified locale, the currency code is returned.
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
}
