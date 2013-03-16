/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */

package javax.money.provider;

import java.util.Enumeration;

import javax.money.MonetaryAmount;

/**
 * Component for accessing {@link MonetaryAmount} instances. It is provided by
 * the Money singleton.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryAmountProvider {

	/**
	 * This method defines the number class used by this factory instances. The
	 * Monetary singleton allows to use monetary amounts using different numeric
	 * representations.
	 * 
	 * @return The number representation class used by this factory, never null.
	 */
	public Enumeration<Class<?>> getSupportedNumberClasses();

	/**
	 * Allows to check if a specific number representation is currently
	 * supported.
	 * 
	 * @param numberClass
	 *            the required number representation class.
	 * @return true, if a {@link MonetaryAmountFactory} can be accessed for the
	 *         given class.
	 * @see #getMonetaryAmountFactory()
	 */
	public boolean isNumberClassSupported(Class<?> numberClass);

	/**
	 * Access the number class used for representation by default.
	 * 
	 * @return the default number class, never null.
	 */
	public Class<?> getDefaultNumberClass();

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param numberClass
	 *            The required number representation class.
	 * @return The {@link MonetaryAmountFactory} instance.
	 * @throws IllegalArgumentException
	 *             , if the required {@code numberClass} is not supported.
	 * @see #getSupportedNumberClasses()
	 * @see #isNumberClassSupported(Class)
	 */
	public MonetaryAmountFactory getMonetaryAmountFactory(Class<?> numberClass);

	/**
	 * Access a {@link MonetaryAmountFactory}, based on the default number
	 * representation class.
	 * 
	 * @see #getDefaultNumberClass()
	 * 
	 * @return The {@link MonetaryAmountFactory} instance.
	 */
	public MonetaryAmountFactory getMonetaryAmountFactory();

}
