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
package javax.money;

/**
 * Strategy for adjusting a monetary amount.
 * <p>
 * Adjusters are a key tool for modifying monetary amounts. They match the
 * strategy design pattern, allowing different types of adjustment to be easily
 * captured. Examples might be an adjuster that rounds the amount to the nearest
 * 1000, or one that performs currency conversion.
 * <p>
 * There are two equivalent ways of using a {@code MonetaryAdjuster}. The first
 * is to invoke the method on this interface. The second is to use
 * {@link MonetaryAmount#with(MonetaryAdjuster)}, whereas implementations of
 * {@link MonetaryAmount#with(MonetaryAdjuster)} must return the concrete type,
 * instead of the interface to support a fluent style:
 * 
 * <pre>
 * // these two variants are equivalent
 * monetary = thisAdjuster.adjustInto(monetary);
 * monetary = anotherAdjuster.adjustInto(monetary);
 * 
 * // second, recommended, approach, using a fluent API
 * monetary = monetary.with(thisAdjuster).with(anotherAdjuster);
 * </pre>
 * 
 * It is recommended to use the second approach, {@code with(MonetaryAdjuster)},
 * as it is a lot clearer to read in code.
 * <h4>Implementation specification</h4>
 * This interface places no restrictions on the mutability of implementations,
 * however immutability is strongly recommended.
 */
// @FunctionalInterface for Java 9
public interface MonetaryAdjuster {

	/**
	 * Adjusts the specified monetary object.
	 * <p>
	 * This adjusts the specified monetary object using the logic encapsulated
	 * in the implementing class. Examples might be an adjuster that rounds the
	 * amount to the nearest 1000, or one that performs currency conversion.
	 * <p>
	 * There are two equivalent ways of using a {@code MonetaryAdjuster}. The
	 * first is to invoke the method on this interface. The second is to use
	 * {@link MonetaryAmount#with(MonetaryAdjuster)}:
	 * 
	 * <pre>
	 * // these two lines are equivalent, but the second approach is recommended
	 * monetary = thisAdjuster.adjustInto(monetary);
	 * monetary = monetary.with(thisAdjuster);
	 * </pre>
	 * 
	 * It is recommended to use the second approach,
	 * {@code with(MonetaryAdjuster)}, as it is a lot clearer to read in code.
	 * 
	 * <h4>Implementation specification</h4>
	 * The implementation must take the input object and adjust it. The
	 * implementation defines the logic of the adjustment and is responsible for
	 * documenting that logic. It may use any method on {@code MonetaryAmount}
	 * to determine the result.
	 * <p>
	 * The input object must not be altered. Instead, an adjusted copy of the
	 * original must be returned. This provides equivalent, safe behavior for
	 * immutable and mutable monetary amounts.
	 * <p>
	 * This method may be called from multiple threads in parallel. It must be
	 * thread-safe when invoked.
	 * 
	 * @param amount
	 *            the amount to adjust, not null
	 * @return a monetary amount with the adjustment made, not null
	 * @throws MonetaryException
	 *             if unable to make the adjustment
	 * @throws ArithmeticException
	 *             if numeric overflow occurs
	 */
	public MonetaryAmount adjustInto(MonetaryAmount amount);

}