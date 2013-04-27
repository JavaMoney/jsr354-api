/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money;

/**
 * This interface defines a {@link MonetaryFunction}. It is considered to be
 * adapted/compatible with {@code java.util.function.Function} as introduced in
 * Java 8.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.9
 * @param <T>
 *            The input type of the function.
 * @param <R>
 *            The result type of the function.
 */
//@FunctionalInterface for Java 9
public interface MonetaryFunction<T, R> {

	/**
	 * Apply a function to the input argument T, yielding an appropriate result R.
	 * @param value the input value
	 * @return the result of the function
	 */
	public R apply(T value);

}