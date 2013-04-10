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
 * This interface defines a {@link MonetaryAdjuster}. It is hereby important to
 * distinguish between <i>internal rounding</i> such as implied by the maximal
 * precision/scale of an amount, and <i>rounding</i> applied to a
 * {@link MonetaryAmount} or a calculation algorithm. Since different use cases
 * may require <i>roundings</i> done at very different stages and differently
 * within a complex financial calculation, {@link MonetaryAdjuster} is not directly
 * attached to a monetary type, e.g. a {@link MonetaryAmount}.
 * <p>
 * Nevertheless the JSR's extensions provide a RoundingMonetaryAmount, which
 * wraps a {@link MonetaryAmount} and adds implicit rounding.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryAdjuster {

	/**
	 * This method is called for rounding an amount.
	 * 
	 * @param amount
	 *            the amount to be rounded
	 * @return the rounded amount.
	 * @throws ArithmeticException
	 *             if rounding fails.
	 */
	public <T extends MonetaryAmount> T adjust(T amount);

}