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
	 * Creates an {@link AmountAdjuster} that rounds a value given the
	 * {@link MathContext} provided.
	 * 
	 * @param mathContext
	 *            The precision and {@link RoundingMode}.
	 * @return the {@link AmountAdjuster} that implements the required rounding.
	 */
	public static AmountAdjuster getRounding(MathContext mathContext) {
		return new MathRounder(mathContext);
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
	public static AmountAdjuster getRounding(CurrencyUnit currency) {
		int precision = currency.getDefaultFractionDigits();
		if (precision < 0) {
			// or throw an Exception?
			precision = 2;
		}
		// TODO get according rounding mode
		return getRounding(precision, RoundingMode.HALF_UP);
	}

	/**
	 * Creates an {@link AmountAdjuster} for rounding given a precision and a
	 * {@link RoundingMode}.
	 * 
	 * @param precision
	 *            the precision
	 * @param rounding
	 *            the {@link RoundingMode}, not null.
	 * @return a new instance {@link AmountAdjuster} implementing the rounding.
	 */
	public static AmountAdjuster getRounding(int precision,
			RoundingMode rounding) {
		return new MathRounder(new MathContext(precision, rounding));
	}

	/**
	 * Internal simple implementation class that supports rounding encaspulated
	 * as {@link AmountAdjuster}.
	 * 
	 * @author Anatole Tresch
	 */
	private final static class MathRounder implements AmountAdjuster {
		/** The {@link MathContext} used. */
		private MathContext mathContext;

		/**
		 * Creates an rounder instance.
		 * 
		 * @param mathContext
		 *            The {@link MathContext} to be used, not {@code null}.
		 */
		public MathRounder(MathContext mathContext) {
			this.mathContext = mathContext;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.AmountAdjuster#adjust(javax.money.MonetaryAmount)
		 */
		@Override
		public MonetaryAmount adjust(MonetaryAmount amount) {
			BigDecimal dec = amount.asType(BigDecimal.class);
			dec = dec.round(this.mathContext);
			return amount.setValue(dec);
		}

	}
}
