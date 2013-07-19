/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.function;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * This class allows to extract the reciprocal value (multiplcative inversion)
 * of a {@link MonetaryAmount} instance.
 * 
 * @author Anatole Tresch
 */
public final class Reciprocal implements MonetaryOperator {

	/**
	 * The shared instance of this class.
	 */
	private Reciprocal INSTANCE = new Reciprocal();

	/**
	 * Access the shared instance of {@link Reciprocal} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	private Reciprocal() {
	}

	public Reciprocal of() {
		return INSTANCE;
	}

	/**
	 * Gets the amount as reciprocal / multiplcative inversed value (1/n).
	 * <p>
	 * E.g. 'EUR 2.0' will be converted to 'EUR 0.5'.
	 * 
	 * @return the reciprocal / multiplcative inversed of the amount
	 * @throws ArithmeticException
	 *             if the arithmetic operation failed
	 */
	@Override
	public MonetaryAmount apply(MonetaryAmount amount) {
		return fromAmount(amount);
	}

	/**
	 * Gets the amount as reciprocal / multiplcative inversed value (1/n).
	 * <p>
	 * E.g. 'EUR 2.0' will be converted to 'EUR 0.5'.
	 * 
	 * @return the reciprocal / multiplcative inversed of the amount
	 * @throws ArithmeticException
	 *             if the arithmetic operation failed
	 */
	@SuppressWarnings("unchecked")
	public static <T extends MonetaryAmount> T fromAmount(T amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount required.");
		}
		return (T) amount.from(BigDecimal.ONE.divide(
				amount.asType(BigDecimal.class), MathContext.DECIMAL128));
	}

}
