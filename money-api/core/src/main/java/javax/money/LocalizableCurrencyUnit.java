/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money;

import java.util.Locale;

/**
 * This interface extends a {@link CurrencyUnit} by the most common localization
 * methods, similar to the ones defined in {@link java.util.Currency}.
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
