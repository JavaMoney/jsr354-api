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
import java.math.RoundingMode;

import javax.money.MonetaryOperator;
import javax.money.MonetaryAmount;

/**
 * This class allows to extract the major part of a {@link MonetaryAmount}
 * instance.
 * 
 * @author Anatole Tresch
 */
public final class MajorPart implements MonetaryOperator {

	/**
	 * The shared instance of this class.
	 */
	private static final MajorPart INSTANCE = new MajorPart();

	/**
	 * Access the shared instance of {@link MajorPart} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	private MajorPart() {
	}

	public static MajorPart of() {
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
	 * Gets the amount in major units as a {@code long}.
	 * <p>
	 * This returns the monetary amount in terms of the major units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 2, and 'BHD -1.345' will return -1.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the major units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for a {@code long}
	 */
	@SuppressWarnings("unchecked")
	public static <T extends MonetaryAmount> T fromAmount(T amount) {
		if (amount == null) {
			throw new NullPointerException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return (T) amount.from(number.setScale(0,
				RoundingMode.DOWN));
	}

	
	/**
	 * Gets the amount in major units as a {@code long}.
	 * <p>
	 * This returns the monetary amount in terms of the major units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 2, and 'BHD -1.345' will return -1.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the major units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for a {@code long}
	 */
	public static long fromAsLong(MonetaryAmount amount) {
		if(amount==null){
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return number.setScale(0, RoundingMode.DOWN).longValueExact();
	}

	/**
	 * Gets the amount in major units as an {@code int}.
	 * <p>
	 * This returns the monetary amount in terms of the major units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 2, and 'BHD -1.345' will return -1.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the major units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for an {@code int}
	 */
	public static int fromAsInteger(MonetaryAmount amount) {
		if(amount==null){
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return number.setScale(0, RoundingMode.DOWN).intValueExact();
	}
}

