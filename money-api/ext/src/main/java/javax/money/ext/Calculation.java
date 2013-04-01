/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

import java.util.concurrent.Callable;

import javax.money.MonetaryAmount;

/**
 * This interface models a simple calculation that is based on a single
 * {@link MonetaryAmount}.
 * 
 * @author Anatole Tresch
 * 
 * @param <R>
 *            the result type of the calculation.
 * @param <T>
 *            the type of the input parameters.
 */
public interface Calculation<R, T> extends Callable<R> {

	/**
	 * Result type of this {@link Calculation}, required to evaluate the result
	 * type during runtime.
	 * 
	 * @return the result type.
	 */
	public Class<R> getResultType();

	/**
	 * Access the parameter type of this {@link Calculation}, required to
	 * evaluate the parameter type during runtime.
	 * 
	 * @return the result type.
	 */
	public Class<T> getParameterType();

	/**
	 * Returns a result calculated using the given {@link MonetaryAmount}.
	 * 
	 * @param amount
	 *            the amount to use, not null
	 * @return the calculation result, never null
	 * @throws ArithmeticException
	 *             if the adjustment fails
	 */
	public void setParameters(@SuppressWarnings("unchecked") T... params);

}
