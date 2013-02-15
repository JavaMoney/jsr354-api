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
 *    Anatole Tresch
 *    Werner Keil
 *    Claus Nielsen
 */
package net.java.javamoney.ri.core;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.money.AmountAdjuster;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.Rounding;
import javax.money.RoundingProvider;

/**
 * Default immutable implementation of {@link MonetaryAmount}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class Money implements MonetaryAmount, Comparable<MonetaryAmount> {

	/** The numeric part of this amount. */
	private final BigDecimal number;

	/** The currency of this amount. */
	private final CurrencyUnit currency;

	/**
	 * Creates a new instance os {@link Money}.
	 * 
	 * @param currency
	 *            the currency, not null.
	 * @param number
	 *            the amount, not null.
	 */
	Money(CurrencyUnit currency, Number number) {
		if (currency == null) {
			throw new IllegalArgumentException("Currency is required.");
		}
		if (number == null) {
			throw new IllegalArgumentException("Number is required.");
		}
		checkNumber(number);
		this.currency = currency;
		if (BigDecimal.class.isAssignableFrom(number.getClass())) {
			this.number = (BigDecimal) number;
		} else {
			this.number = BigDecimal.valueOf(number.doubleValue());
		}
		// TODO ensure internal precision!
	}

	/**
	 * Static factory method for creating a new instance of {@link Money}.
	 * 
	 * @param currency
	 *            The target currency, not null.
	 * @param number
	 *            The numeric part, not null.
	 * @return A new instance of {@link Money}.
	 */
	public static Money valueOf(CurrencyUnit currency, Number number) {
		// TODO caching
		return new Money(currency, number);
	}

	/**
	 * Static factory method for creating a new instance of {@link Money}.
	 * 
	 * @param isoCurrencyCode
	 *            The target currency as ISO currency code.
	 * @param number
	 *            The numeric part, not null.
	 * @return A new instance of {@link Money}.
	 */
	public static Money valueOf(String isoCurrencyCode, Number number) {
		// TODO caching
		return new Money(JDKCurrencyAdapter.getInstance(isoCurrencyCode),
				number);
	}

	/*
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(MonetaryAmount o) {
		int compare = -1;
		if (this.currency.equals(o.getCurrency())) {
			compare = this.number.compareTo(o.asType(BigDecimal.class));
		}
		return compare;
	}

	public CurrencyUnit getCurrency() {
		return currency;
	}

	public MonetaryAmount abs() {
		if (this.isPositiveOrZero()) {
			return this;
		}
		return this.negate();
	}

	public MonetaryAmount min(MonetaryAmount amount) {
		checkAmountParameter(amount);
		if (amount.lessThan(this)) {
			return amount;
		}
		return this;
	}

	public MonetaryAmount max(MonetaryAmount amount) {
		checkAmountParameter(amount);
		if (amount.greaterThan(this)) {
			return amount;
		}
		return this;
	}

	// Arithmetic Operations

	public MonetaryAmount add(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return new Money(this.currency, this.number.add(amount
				.asType(BigDecimal.class)));
	}

	public MonetaryAmount add(Number number) {
		checkNumber(number);
		BigDecimal dec = this.number.add(BigDecimal.valueOf(number
				.doubleValue()));
		return new Money(this.currency, dec);
	}

	public MonetaryAmount divide(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		BigDecimal dec = this.number.divide(divisor.asType(BigDecimal.class));
		return new Money(this.currency, dec);
	}

	public MonetaryAmount divide(Number divisor) {
		checkNumber(divisor);
		BigDecimal dec = this.number.divide(BigDecimal.valueOf(divisor
				.doubleValue()));
		return new Money(this.currency, dec);
	}

	public MonetaryAmount[] divideAndRemainder(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		BigDecimal[] dec = this.number.divideAndRemainder(divisor
				.asType(BigDecimal.class));
		return new MonetaryAmount[] { new Money(this.currency, dec[0]),
				new Money(this.currency, dec[1]) };
	}

	public MonetaryAmount[] divideAndRemainder(Number divisor) {
		checkNumber(divisor);
		BigDecimal[] dec = this.number.divideAndRemainder(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new MonetaryAmount[] { new Money(this.currency, dec[0]),
				new Money(this.currency, dec[1]) };
	}

	public MonetaryAmount divideToIntegralValue(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		BigDecimal dec = this.number.divideToIntegralValue(divisor
				.asType(BigDecimal.class));
		return new Money(this.currency, dec);
	}

	public MonetaryAmount divideToIntegralValue(Number divisor) {
		checkNumber(divisor);
		BigDecimal dec = this.number.divideToIntegralValue(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new Money(this.currency, dec);
	}

	public MonetaryAmount multiply(MonetaryAmount multiplicand) {
		checkAmountParameter(multiplicand);
		BigDecimal dec = this.number.multiply(multiplicand
				.asType(BigDecimal.class));
		return new Money(this.currency, dec);
	}

	public MonetaryAmount multiply(Number multiplicand) {
		checkNumber(multiplicand);
		BigDecimal dec = this.number.multiply(BigDecimal.valueOf(multiplicand
				.doubleValue()));
		return new Money(this.currency, dec);
	}

	public MonetaryAmount negate() {
		return new Money(this.currency, this.number.negate());
	}

	public MonetaryAmount plus() {
		return new Money(this.currency, this.number.plus());
	}

	public MonetaryAmount subtract(MonetaryAmount subtrahend) {
		checkAmountParameter(subtrahend);
		return new Money(this.currency, this.number.subtract(subtrahend
				.asType(BigDecimal.class)));
	}

	public MonetaryAmount subtract(Number subtrahend) {
		checkNumber(subtrahend);
		return new Money(this.currency, this.number.subtract(BigDecimal
				.valueOf(subtrahend.doubleValue())));
	}

	public MonetaryAmount pow(int n) {
		return new Money(this.currency, this.number.pow(n));
	}

	public MonetaryAmount ulp() {
		return new Money(this.currency, this.number.ulp());
	}

	public MonetaryAmount remainder(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		return new Money(this.currency, this.number.remainder(divisor
				.asType(BigDecimal.class)));
	}

	public MonetaryAmount remainder(Number divisor) {
		checkNumber(divisor);
		return new Money(this.currency, this.number.remainder(BigDecimal
				.valueOf(divisor.doubleValue())));
	}

	public MonetaryAmount scaleByPowerOfTen(int n) {
		return new Money(this.currency, this.number.scaleByPowerOfTen(n));
	}

	public long getMajorLong() {
		return this.number.setScale(0, RoundingMode.DOWN).longValueExact();
	}

	public int getMajorInt() {
		return this.number.setScale(0, RoundingMode.DOWN).intValueExact();
	}

	public long getMinorLong() {
		return this.number.movePointRight(this.number.precision())
				.longValueExact();
	}

	public int getMinorInt() {
		return this.number.movePointRight(this.number.precision())
				.intValueExact();
	}

	public boolean isZero() {
		return this.number.signum() == 0;
	}

	public boolean isPositive() {
		return signum() == 1;
	}

	public boolean isPositiveOrZero() {
		return signum() >= 0;
	}

	public boolean isNegative() {
		return signum() == -1;
	}

	public boolean isNegativeOrZero() {
		return signum() <= 0;
	}

	public MonetaryAmount with(Number amount) {
		checkNumber(amount);
		return new Money(this.currency,
				BigDecimal.valueOf(amount.doubleValue()));
	}

	public MonetaryAmount with(AmountAdjuster... adjuster) {
		MonetaryAmount amount = this;
		for (AmountAdjuster amountAdjuster : adjuster) {
			amount = amountAdjuster.adjust(amount);
		}
		return amount;
	}

	public int getScale() {
		return this.number.scale();
	}

	public int getPrecision() {
		return this.number.precision();
	}

	public int intValue() {
		return this.number.intValue();
	}

	public int intValueExact() {
		return this.number.intValueExact();
	}

	public long longValue() {
		return this.number.longValue();
	}

	public long longValueExact() {
		return this.number.longValueExact();
	}

	public float floatValue() {
		return this.number.floatValue();
	}

	public double doubleValue() {
		return this.number.doubleValue();
	}

	public byte byteValue() {
		return this.number.byteValue();
	}

	public short shortValue() {
		return this.number.shortValue();
	}

	public short shortValueExact() {
		return number.shortValueExact();
	}

	public int signum() {
		return this.number.signum();
	}

	public String toEngineeringString() {
		return this.currency.toString() + ' '
				+ this.number.toEngineeringString();
	}

	public String toPlainString() {
		return this.currency.toString() + ' ' + this.number.toPlainString();
	}

	public boolean lessThan(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) < 0;
	}

	public boolean lessThan(Number number) {
		checkNumber(number);
		return this.number.compareTo(BigDecimal.valueOf(number.doubleValue())) < 0;
	}

	public boolean lessThanOrEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) <= 0;
	}

	public boolean lessThanOrEqualTo(Number number) {
		checkNumber(number);
		return this.number.compareTo(BigDecimal.valueOf(number.doubleValue())) <= 0;
	}

	public boolean greaterThan(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) > 0;
	}

	public boolean greaterThan(Number number) {
		checkNumber(number);
		return this.number.compareTo(BigDecimal.valueOf(number.doubleValue())) > 0;
	}

	public boolean greaterThanOrEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) >= 0;
	}

	public boolean greaterThanOrEqualTo(Number number) {
		checkNumber(number);
		return this.number.compareTo(BigDecimal.valueOf(number.doubleValue())) >= 0;
	}

	public boolean isEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) == 0;
	}

	public boolean isEqualTo(Number number) {
		checkNumber(number);
		return this.number.compareTo(BigDecimal.valueOf(number.doubleValue())) == 0;
	}

	public boolean isNotEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) != 0;
	}

	public boolean isNotEqualTo(Number number) {
		checkNumber(number);
		return this.number.compareTo(BigDecimal.valueOf(number.doubleValue())) != 0;
	}

	public MonetaryAmount getMajorPart() {
		// TODO Auto-generated method stub
		return null;
	}

	public MonetaryAmount getMinorPart() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Access the rounded value corresponding to the current
	 * {@link CurrencyUnit}. The {@link Rounding} must be provided by the
	 * {@link RoundingProvider}.
	 * 
	 * @return the rounded value, never null.
	 * @throws IllegalStateException
	 *             if no Rounding could be evaluated.
	 */
	public MonetaryAmount getAdjusted() {
		Rounding rounding = Monetary.getRoundingProvider().getRounding(
				this.currency);
		if (rounding != null) {
			return rounding.adjust(this);
		}
		throw new IllegalStateException("No Rounding available for currency: "
				+ this.currency);
	}

	public Class<?> getNumberType() {
		return BigDecimal.class;
	}

	public <T> T asType(Class<T> type) {
		if (BigDecimal.class.equals(type)) {

			@SuppressWarnings("unchecked")
			final T asType = (T) this.number;
			return asType;
		}
		// TODO add Number types...
		throw new IllegalArgumentException("Unsupported representation type: "
				+ type);
	}

	public <T> T asType(Class<T> type, AmountAdjuster... adjustment) {
		MonetaryAmount amount = this;
		for (int i = 0; i < adjustment.length; i++) {
			amount = adjustment[i].adjust(amount);
		}
		return amount.asType(type);
	}

	public <T> T asType(Class<T> type, boolean performRounding) {
		// TODO Auto-generated method stub
		return null;
	}

	public MonetaryAmount[] divideAndSeparate(Number divisor,
			boolean addDifferenceToLastValue) {
		checkNumber(divisor);
		// TODO Auto-generated method stub
		return null;
	}

	// Static Factory Methods
	/**
	 * Translates a {@code BigDecimal} value and a {@code CurrencyUnit} currency
	 * into a {@code Money}.
	 * 
	 * @param number
	 *            numeric value of the {@code Money}.
	 * @param currency
	 *            currency unit of the {@code Money}.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 */
	public static Money valueOf(BigDecimal number, CurrencyUnit currency) {
		return new Money(currency, number);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return currency.toString() + ' ' + number;
	}

	// Internal helper methods

	/**
	 * Internal method to check for correct number parameter.
	 * 
	 * @param number
	 * @throws IllegalArgumentException
	 *             If the number is null
	 */
	public void checkNumber(Number number) {
		if (number == null) {
			throw new IllegalArgumentException("Number is required.");
		}
	}

	/**
	 * Method to check if a currency is compatible with this amount instance.
	 * 
	 * @param amount
	 *            The monetary amount to be compared to, never null.
	 * @throws IllegalArgumentException
	 *             If the amount is null, or the amount's currency is not
	 *             compatible (same {@link CurrencyUnit#getNamespace()} and same
	 *             {@link CurrencyUnit#getCurrencyCode()}).
	 */
	private void checkAmountParameter(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount must not be null.");
		}
		if (!(this.currency.getNamespace().equals(currency.getNamespace()) || !(this.currency
				.getCurrencyCode().equals(currency.getCurrencyCode())))) {
			throw new IllegalArgumentException("Currency mismatch: "
					+ this.currency + '/' + currency);
		}
	}

	/**
	 * Allows to check, if the currency of the two amounts are the same. This
	 * means that corresponding currency's namespace and code must match.
	 * 
	 * @param amount
	 *            The amount to comapre to, not {@code null}.
	 * @return true, if the {@link CurrencyUnit} of this instance has the same
	 *         namespace and code.
	 */
	@Override
	public boolean isSameCurrencyAs(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount must not be null.");
		}
		return this.currency.getNamespace().equals(
				amount.getCurrency().getNamespace())
				&& this.currency.getCurrencyCode().equals(
						amount.getCurrency().getCurrencyCode());
	}

}
