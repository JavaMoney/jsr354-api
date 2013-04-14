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

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * This class allows to extract the minor part of a {@link MonetaryAmount}
 * instance.
 * 
 * @author Anatole Tresch
 */
public final class MinorPart implements MonetaryOperator {

	/**
	 * The shared instance of this class.
	 */
	private MinorPart INSTANCE = new MinorPart();

	/**
	 * Private constructor, there is only one instance of this class, accessible
	 * calling {@link #of()}.
	 */
	private MinorPart() {
	}

	/**
	 * Access the shared instance of {@link MinorPart} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public MinorPart of() {
		return INSTANCE;
	}

	/**
	 * Gets the amount in major units as a {@code MonetaryAmount} with scale 0.
	 * <p>
	 * This returns the monetary amount in terms of the major units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 'EUR 2', and 'BHD -1.345' will return 'BHD -1'.
	 * <p>
	 * This is returned as a {@code MonetaryAmount} rather than a
	 * {@code BigInteger} . This is to allow further calculations to be
	 * performed on the result. Should you need a {@code BigInteger}, simply
	 * call {@code asType(BigInteger.class)}.
	 * 
	 * @return the major units part of the amount, never {@code null}
	 */
	@Override
	public MonetaryAmount apply(MonetaryAmount amount) {
		return fromAmount(amount);
	}

	/**
	 * Gets the amount in minor units as a {@code long}.
	 * <p>
	 * This returns the monetary amount in terms of the minor units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 235, and 'BHD -1.345' will return -1345.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the minor units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for a {@code long}
	 */
	public static long fromAsLong(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return number.movePointRight(number.precision()).longValueExact();
	}

	/**
	 * Gets the amount in minor units as an {@code int}.
	 * <p>
	 * This returns the monetary amount in terms of the minor units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 235, and 'BHD -1.345' will return -1345.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the minor units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for an {@code int}
	 */
	public static int fromAsInt(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return number.movePointRight(number.precision()).intValueExact();
	}

	/**
	 * Gets the amount in minor units as a {@code MonetaryAmount} with scale 0.
	 * <p>
	 * This returns the monetary amount in terms of the minor units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 'EUR 235', and 'BHD -1.345' will return 'BHD -1345'.
	 * <p>
	 * This is returned as a {@code MonetaryAmount} rather than a
	 * {@code BigInteger} . This is to allow further calculations to be
	 * performed on the result. Should you need a {@code BigInteger}, simply
	 * call {@link asType(BigInteger.class)}.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the minor units part of the amount, never null
	 */
	@SuppressWarnings("unchecked")
	public static <T extends MonetaryAmount> T fromAmount(T amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return (T) amount.from(number.movePointRight(number.precision())
				.longValueExact());
	}
}
