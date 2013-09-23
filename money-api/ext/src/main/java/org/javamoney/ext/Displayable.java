/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package org.javamoney.ext;

import java.util.Currency;
import java.util.Locale;

/**
 * This interface is implemented by types that are displayable for particular
 * locales. It was defined to provide a simple mechanism for modeling
 * localization as also available on {@link Currency}. Nevertheless it is highly
 * recommended to separate formatting aspects from the types, e.g. by using the
 * JSRs formatting features ({@code ItemFormat}), to format according instances.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.9
 */
public interface Displayable {

	/**
	 * Gets the name that is suitable for displaying this item for the specified
	 * locale.
	 * 
	 * @param locale
	 *            the locale for which a display name is needed
	 * @return the display name for the specified locale
	 * @exception NullPointerException
	 *                if <code>locale</code> is null
	 */
	public String getDisplayName(Locale locale);
}
