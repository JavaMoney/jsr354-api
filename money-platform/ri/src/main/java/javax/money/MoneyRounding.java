/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Implementation class that models rounding based on standard JDK math, a scale
 * and {@link RoundingMode}..
 * 
 * @author Anatole Tresch
 * @see RoundingMode
 */
public final class MoneyRounding implements Rounding {

	/** The {@link RoundingMode} used. */
	private RoundingMode roundingMode;
	/** The scale to be applied. */
	private int scale;

	/**
	 * Creates an rounder instance.
	 * 
	 * @param mathContext
	 *            The {@link MathContext} to be used, not {@code null}.
	 */
	public MoneyRounding(int scale, RoundingMode roundingMode) {
		if (scale < 0) {
			throw new IllegalArgumentException("scale < 0");
		}
		if (roundingMode == null) {
			throw new IllegalArgumentException("roundingMode missing");
		}
		this.scale = scale;
		this.roundingMode = roundingMode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.Rounding#round(javax.money.MonetaryAmount)
	 */
	@Override
	public MonetaryAmount round(MonetaryAmount amount) {
		BigDecimal dec = amount.asType(BigDecimal.class);
		dec = dec.setScale(this.scale, this.roundingMode);
		return amount.withAmount(dec);
	}

	/**
	 * Creates an {@link MoneyRounding} for rounding {@link MonetaryAmount}
	 * instances given a currency.
	 * 
	 * @param currency
	 *            The currency, which determines the required precision. As
	 *            {@link RoundingMode}, by default, {@link RoundingMode#HALF_UP}
	 *            is sued.
	 * @return a new instance {@link AmountAdjuster} implementing the rounding.
	 */
	public static MoneyRounding of(CurrencyUnit currency,
			RoundingMode roundingMode) {
		int scale = currency.getDefaultFractionDigits();
		return of(scale, roundingMode);
	}

	/**
	 * Creates an {@link AmountAdjuster} for rounding {@link MonetaryAmount}
	 * instances given a currency.
	 * 
	 * @param currency
	 *            The currency, which determines the required precision. As
	 *            {@link RoundingMode}, by default, {@link RoundingMode#HALF_UP}
	 *            is sued.
	 * @return a new instance {@link AmountAdjuster} implementing the rounding.
	 */
	public static MoneyRounding of(CurrencyUnit currency) {
		int scale = currency.getDefaultFractionDigits();
		// TODO get according rounding mode
		return of(scale, RoundingMode.HALF_UP);
	}

	/**
	 * Creates an {@link MoneyRounding} for rounding given a precision and a
	 * {@link RoundingMode}.
	 * 
	 * @param scale
	 *            the required scale
	 * @param rounding
	 *            the {@link RoundingMode}, not null.
	 * @return a new instance {@link AmountAdjuster} implementing the rounding.
	 */
	public static MoneyRounding of(int scale, RoundingMode rounding) {
		return new MoneyRounding(scale, rounding);
	}

}
