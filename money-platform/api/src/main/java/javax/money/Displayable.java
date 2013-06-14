/*
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
 * This interface is implemented by types that are displayble for particular locales.
 * 
 * @TODO check if this class can be moved to {@code java.util} or at least "format" module.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface Displayable {

	/**
	 * Gets the name that is suitable for displaying this item for
     * the specified locale. 
	 * 
     * @param locale the locale for which a display name is
     * needed
     * @return the display name for the specified locale
     * @exception NullPointerException if <code>locale</code> is null
	 */
	public String getDisplayName(Locale locale);

}
