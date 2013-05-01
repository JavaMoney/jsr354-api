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
 * This interface is implemented by types that are localiable for display.
 * 
 * @TODO check if this class can be moved to {@code java.util} or at least "format" module.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface Localizable {

	/**
	 * Access a display name for the item, that can be shown for display.
	 * 
	 * @param locale
	 *            The {@link Locale} to be used.
	 * @return the formatted display name
	 */
	public String getDisplayName(Locale locale);

}
