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
