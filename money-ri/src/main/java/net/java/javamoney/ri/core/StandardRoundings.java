/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial implementation
 *    Wernner Keil - extensions and adaptions.
 */
package net.java.javamoney.ri.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Rounding;

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
	public static Rounding getRounding(CurrencyUnit currency,
			RoundingMode roundingMode) {
		int scale = currency.getDefaultFractionDigits();
		return getRounding(scale, roundingMode);
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
	public static Rounding getRounding(CurrencyUnit currency) {
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
	public static Rounding getRounding(int scale, RoundingMode rounding) {
		return new MathRounder(scale, rounding);
	}

	/**
	 * Internal simple implementation class that supports rounding encaspulated
	 * as {@link AmountAdjuster}.
	 * 
	 * @author Anatole Tresch
	 */
	private final static class MathRounder implements Rounding {
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
			return amount.with(dec);
		}

	}
}
