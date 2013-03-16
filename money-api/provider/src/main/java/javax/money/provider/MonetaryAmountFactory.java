/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.provider;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

/**
 * Component for accessing {@link MonetaryAmount} instances. It is provided by
 * the {@link MonetaryAmountProvider} instance.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryAmountFactory {

	/**
	 * This method defines the number class used by this factory instances. The
	 * Monetary singleton allows to use monetary amounts using different numeric
	 * representations.
	 * 
	 * @return The number representation class used by this factory, never null.
	 */
	public Class<?> getNumberClass();

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param number
	 *            The required numeric value, not null.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, Number number);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, byte value);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, short value);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, int value);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, long value);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param major
	 *            The required major decimal number part.
	 * @param minor
	 *            The required minor decimal number part, not negative.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, long major, long minor);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, float value);

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MonetaryAmount get(CurrencyUnit currency, double value);

	/**
	 * Obtains an instance of {@code Money} representing zero.
	 * <p>
	 * For example, {@code zero(USD)} creates the instance {@code USD 0.00}.
	 * 
	 * @param currency
	 *            the currency, not null
	 * @return the instance representing zero, never null
	 */
	public MonetaryAmount zero(CurrencyUnit currency);

}
