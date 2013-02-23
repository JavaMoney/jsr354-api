/*
 * Copyright (c) 2012-2013, Credit Suisse
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.java.javamoney.ri;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import javax.money.AmountAdjuster;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

/**
 * Factory class for creating {@link AmountAdjuster} instances that implement
 * roundings, based on precision and standard {@link RoundingMode} settings..
 * 
 * @author Anatole Tresch
 * 
 */
public class StandardRoundings {

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
	public static AmountAdjuster getRounding(CurrencyUnit currency) {
		int scale = currency.getDefaultFractionDigits();
		// TODO get according rounding mode
		return getRounding(scale, RoundingMode.HALF_UP);
	}

	/**
	 * Creates an {@link AmountAdjuster} for rounding given a precision and a
	 * {@link RoundingMode}.
	 * 
	 * @param scale
	 *            the required scale
	 * @param rounding
	 *            the {@link RoundingMode}, not null.
	 * @return a new instance {@link AmountAdjuster} implementing the rounding.
	 */
	public static AmountAdjuster getRounding(int scale, RoundingMode rounding) {
		return new MathRounder(scale, rounding);
	}

	/**
	 * Internal simple implementation class that supports rounding encaspulated
	 * as {@link AmountAdjuster}.
	 * 
	 * @author Anatole Tresch
	 */
	private final static class MathRounder implements AmountAdjuster {
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
		public MathRounder(int scale, RoundingMode roundingMode) {
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
		 * @see javax.money.AmountAdjuster#adjust(javax.money.MonetaryAmount)
		 */
		@Override
		public MonetaryAmount adjust(MonetaryAmount amount) {
			BigDecimal dec = amount.asType(BigDecimal.class);
			dec = dec.setScale(this.scale, this.roundingMode);
			return amount.setValue(dec);
		}

	}
}
