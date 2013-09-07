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
 */
package javax.money;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * Platform RI: Implementation class that models rounding based on standard JDK math, a scale
 * and {@link RoundingMode}..
 * 
 * @author Anatole Tresch
 * @see RoundingMode
 */
public final class MoneyRounding implements MonetaryOperator {

	private static final MonetaryOperator DEFAULT_ROUNDING = new DefaultCurrencyRounding();
	/** The {@link RoundingMode} used. */
	private final RoundingMode roundingMode;
	/** The scale to be applied. */
	private final int scale;

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

	/**
	 * Creates a rounding that can be added as {@link MonetaryFunction} to
	 * chained calculations. The instance will lookup the concrete
	 * {@link MoneyRounding} based on the input {@link MonetaryAmount}'s
	 * {@link CurrencyUnit}.
	 * 
	 * @return the (shared= rounding instance.
	 */
	public static MonetaryOperator of() {
		return DEFAULT_ROUNDING;
	}

	/**
	 * Creates an {@link MoneyRounding} for rounding {@link MonetaryAmount}
	 * instances given a currency.
	 * 
	 * @param currency
	 *            The currency, which determines the required precision. As
	 *            {@link RoundingMode}, by default, {@link RoundingMode#HALF_UP}
	 *            is sued.
	 * @return a new instance {@link MonetaryOperator} implementing the
	 *         rounding.
	 */
	public static MoneyRounding of(CurrencyUnit currency,
			RoundingMode roundingMode) {
		int scale = currency.getDefaultFractionDigits();
		return of(scale, roundingMode);
	}

	/**
	 * Creates an {@link MonetaryOperator} for rounding {@link MonetaryAmount}
	 * instances given a currency.
	 * 
	 * @param currency
	 *            The currency, which determines the required precision. As
	 *            {@link RoundingMode}, by default, {@link RoundingMode#HALF_UP}
	 *            is sued.
	 * @return a new instance {@link MonetaryOperator} implementing the
	 *         rounding.
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
	 * @return a new instance {@link MonetaryOperator} implementing the
	 *         rounding.
	 */
	public static MoneyRounding of(int scale, RoundingMode rounding) {
		return new MoneyRounding(scale, rounding);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryFunction#apply(java.lang.Object)
	 */
	@Override
	public MonetaryAmount apply(MonetaryAmount value) {
		return value.from(value.asType(BigDecimal.class).setScale(this.scale,
				this.roundingMode));
	}

	/**
	 * Platform RI: Default Rounding that rounds a {@link MonetaryAmount} based on tis
	 * {@link Currency}.
	 * 
	 * @author Anatole Tresch
	 */
	private static final class DefaultCurrencyRounding implements
			MonetaryOperator {

		@Override
		public MonetaryAmount apply(MonetaryAmount amount) {
			MoneyRounding r = MoneyRounding.of(amount.getCurrency());
			return r.apply(amount);
		}

	}

}
