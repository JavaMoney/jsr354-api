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
import java.math.BigInteger;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

/**
 * Default immutable implementation of {@link MonetaryAmount}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class IntegralMoney implements MonetaryAmount,
		Comparable<MonetaryAmount> {

	/** The numeric part of this amount. */
	private final long number;

	private int scale;

	/** The currency of this amount. */
	private final CurrencyUnit currency;

	/**
	 * Creates a new instance os {@link IntegralMoney}.
	 * 
	 * @param currency
	 *            the currency, not null.
	 * @param number
	 *            the amount, not null.
	 */
	public IntegralMoney(CurrencyUnit currency, Number number) {
		if (currency == null) {
			throw new IllegalArgumentException("Currency is required.");
		}
		if (number == null) {
			throw new IllegalArgumentException("Number is required.");
		}
		checkNumber(number);
		this.currency = currency;
		this.number = number.longValue();
	}
	

	/**
	 * Static factory method for creating a new instance of
	 * {@link IntegralMoney}.
	 * 
	 * @param currency
	 *            The target currency, not null.
	 * @param number
	 *            The numeric part, not null.
	 * @return A new instance of {@link IntegralMoney}.
	 */
	public static IntegralMoney valueOf(CurrencyUnit currency, Number number) {
		// TODO caching
		return new IntegralMoney(currency, number);
	}

	/**
	 * Static factory method for creating a new instance of
	 * {@link IntegralMoney}.
	 * 
	 * @param isoCurrencyCode
	 *            The target currency as ISO currency code.
	 * @param number
	 *            The numeric part, not null.
	 * @return A new instance of {@link IntegralMoney}.
	 */
	public static IntegralMoney valueOf(String isoCurrencyCode, Number number) {
		return new IntegralMoney(MoneyCurrency.getInstance(isoCurrencyCode),
				number);
	}

/**
	 * Facory method creating a zero instance with the given {@code currency);
	 * @param currency the target currency of the amount being created.
	 * @return
	 */
	public static MonetaryAmount zero(CurrencyUnit currency) {
		return new IntegralMoney(currency, 0L);
	}

	/**
	 * Get the number represnetation type, E.g. {@code java.math.BigDecimal}.
	 * 
	 * @return
	 */
	public static Class<?> getNumberClass() {
		return Long.class;
	}

	/*
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(MonetaryAmount o) {
		int compare = -1;
		if (this.currency.equals(o.getCurrency())) {
			if (this.number < o.longValue()) {
				compare = 0;
			} else if (this.number < o.longValue()) {
				compare = -1;
			} else {
				compare = 1;
			}
		}
		return compare;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + (int) number;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntegralMoney other = (IntegralMoney) obj;
		if (currency == null) {
			if (other.getCurrency() != null)
				return false;
		} else if (!currency.equals(other.getCurrency()))
			return false;
		if (number != other.number)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getCurrency()
	 */
	public CurrencyUnit getCurrency() {
		return currency;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#abs()
	 */
	public MonetaryAmount abs() {
		if (this.isPositiveOrZero()) {
			return this;
		}
		return this.negate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#min(javax.money.MonetaryAmount)
	 */
	public MonetaryAmount min(MonetaryAmount amount) {
		checkAmountParameter(amount);
		if (amount.lessThan(this)) {
			return amount;
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#max(javax.money.MonetaryAmount)
	 */
	public MonetaryAmount max(MonetaryAmount amount) {
		checkAmountParameter(amount);
		if (amount.greaterThan(this)) {
			return amount;
		}
		return this;
	}

	// Arithmetic Operations

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#add(javax.money.MonetaryAmount)
	 */
	public MonetaryAmount add(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return new IntegralMoney(this.currency, this.number + amount.longValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#add(java.lang.Number)
	 */
	public MonetaryAmount add(Number number) {
		checkNumber(number);
		return new IntegralMoney(this.currency, this.number
				+ number.longValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divide(javax.money.MonetaryAmount)
	 */
	public MonetaryAmount divide(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		return new IntegralMoney(this.currency, this.number
				/ divisor.longValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divide(java.lang.Number)
	 */
	public MonetaryAmount divide(Number divisor) {
		checkNumber(divisor);
		return new IntegralMoney(this.currency, this.number
				/ divisor.longValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#divideAndRemainder(javax.money.MonetaryAmount)
	 */
	public MonetaryAmount[] divideAndRemainder(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		long divisorAsLong = divisor.longValue();
		return new MonetaryAmount[] {
				new IntegralMoney(this.currency, this.number / divisorAsLong),
				new IntegralMoney(this.currency, this.number % divisorAsLong) };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideAndRemainder(java.lang.Number)
	 */
	public MonetaryAmount[] divideAndRemainder(Number divisor) {
		checkNumber(divisor);
		long divisorAsLong = divisor.longValue();
		return new MonetaryAmount[] {
				new IntegralMoney(this.currency, this.number / divisorAsLong),
				new IntegralMoney(this.currency, this.number % divisorAsLong) };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#divideToIntegralValue(javax.money.MonetaryAmount
	 * )
	 */
	public MonetaryAmount divideToIntegralValue(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		return new IntegralMoney(this.currency, this.number
				/ divisor.longValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideToIntegralValue(java.lang.Number)
	 */
	public MonetaryAmount divideToIntegralValue(Number divisor) {
		checkNumber(divisor);
		return new IntegralMoney(this.currency, this.number
				/ divisor.longValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#multiply(javax.money.MonetaryAmount)
	 */
	public MonetaryAmount multiply(MonetaryAmount multiplicand) {
		checkAmountParameter(multiplicand);
		return new IntegralMoney(this.currency, this.number
				* multiplicand.longValue());
	}

	public MonetaryAmount multiply(Number multiplicand) {
		checkNumber(multiplicand);
		return new IntegralMoney(this.currency, this.number
				* multiplicand.longValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#negate()
	 */
	public MonetaryAmount negate() {
		if (this.number <= 0) {
			return this;
		}
		return new IntegralMoney(this.currency, this.number * -1L);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#plus()
	 */
	public MonetaryAmount plus() {
		if (this.number > 0) {
			return this;
		}
		return new IntegralMoney(this.currency, Math.abs(this.number));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#subtract(javax.money.MonetaryAmount)
	 */
	public MonetaryAmount subtract(MonetaryAmount subtrahend) {
		checkAmountParameter(subtrahend);
		return new IntegralMoney(this.currency, this.number
				- subtrahend.longValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#subtract(java.lang.Number)
	 */
	public MonetaryAmount subtract(Number subtrahend) {
		checkNumber(subtrahend);
		return new IntegralMoney(this.currency, this.number
				- subtrahend.longValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#pow(int)
	 */
	public MonetaryAmount pow(int n) {
		return new IntegralMoney(this.currency, this.number ^ n);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#ulp()
	 */
	public MonetaryAmount ulp() {
		return new IntegralMoney(this.currency, new BigDecimal(BigInteger.ONE,
				getScale()).longValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#remainder(javax.money.MonetaryAmount)
	 */
	public MonetaryAmount remainder(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		return new IntegralMoney(this.currency, this.number
				% divisor.longValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#remainder(java.lang.Number)
	 */
	public MonetaryAmount remainder(Number divisor) {
		checkNumber(divisor);
		return new IntegralMoney(this.currency, this.number
				% divisor.longValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#scaleByPowerOfTen(int)
	 */
	public MonetaryAmount scaleByPowerOfTen(int n) {
		return new IntegralMoney(this.currency, this.number * (10 ^ n));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMajorLong()
	 */
	public long getMajorLong() {
		return this.number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMajorInt()
	 */
	public int getMajorInt() {
		return (int) this.number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMinorLong()
	 */
	public long getMinorLong() {
		return 0L;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMinorInt()
	 */
	public int getMinorInt() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isZero()
	 */
	public boolean isZero() {
		return this.number != 0L;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isPositive()
	 */
	public boolean isPositive() {
		return this.number > 0L;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isPositiveOrZero()
	 */
	public boolean isPositiveOrZero() {
		return this.number >= 0L;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNegative()
	 */
	public boolean isNegative() {
		return this.number < 0L;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNegativeOrZero()
	 */
	public boolean isNegativeOrZero() {
		return this.number <= 0L;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#with(java.lang.Number)
	 */
	@Override
	public MonetaryAmount withAmount(Number number) {
		return new IntegralMoney(this.currency, number.longValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getScale()
	 */
	public int getScale() {
		return this.scale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getPrecision()
	 */
	public int getPrecision() {
		return String.valueOf(this.number).length();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#intValue()
	 */
	public int intValue() {
		return (int) this.number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#intValueExact()
	 */
	public int intValueExact() {
		if (this.number > Integer.MAX_VALUE) {
			throw new ArithmeticException("Number > Integer.MAX_VALUE");
		}
		return (int) this.number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#longValue()
	 */
	public long longValue() {
		return this.number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#longValueExact()
	 */
	public long longValueExact() {
		return this.number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#floatValue()
	 */
	public float floatValue() {
		return (float) this.number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#doubleValue()
	 */
	public double doubleValue() {
		return (double) this.number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#byteValue()
	 */
	public byte byteValue() {
		return (byte) this.number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#shortValue()
	 */
	public short shortValue() {
		return (short) this.number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#shortValueExact()
	 */
	public short shortValueExact() {
		if (this.number > Short.MAX_VALUE) {
			throw new ArithmeticException("Number > Short.MAX_VALUE");
		}
		return (short) this.number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#signum()
	 */

	public int signum() {
		if (this.number < 0) {
			return -1;
		}
		if (this.number == 0) {
			return 0;
		}
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#toEngineeringString()
	 */
	public String toEngineeringString() {
		return this.currency.toString() + ' '
				+ BigDecimal.valueOf(this.number).toEngineeringString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#toPlainString()
	 */
	public String toPlainString() {
		return this.currency.toString() + ' ' + String.valueOf(this.number);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#lessThan(javax.money.MonetaryAmount)
	 */
	public boolean lessThan(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return this.number < amount.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#lessThan(java.lang.Number)
	 */
	public boolean lessThan(Number number) {
		checkNumber(number);
		return this.number < number.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#lessThanOrEqualTo(javax.money.MonetaryAmount)
	 */
	public boolean lessThanOrEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return this.number <= amount.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#lessThanOrEqualTo(java.lang.Number)
	 */
	public boolean lessThanOrEqualTo(Number number) {
		checkNumber(number);
		return this.number <= number.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#greaterThan(javax.money.MonetaryAmount)
	 */
	public boolean greaterThan(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return this.number > amount.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#greaterThan(java.lang.Number)
	 */
	public boolean greaterThan(Number number) {
		checkNumber(number);
		return this.number > number.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#greaterThanOrEqualTo(javax.money.MonetaryAmount
	 * ) #see
	 */
	public boolean greaterThanOrEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return this.number >= amount.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#greaterThanOrEqualTo(java.lang.Number)
	 */
	public boolean greaterThanOrEqualTo(Number number) {
		checkNumber(number);
		return this.number >= number.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isEqualTo(javax.money.MonetaryAmount)
	 */
	public boolean isEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		try {
			return this.number == amount.longValueExact();
		} catch (ArithmeticException e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#hasSameNumberAs(java.lang.Number)
	 */
	public boolean hasSameNumberAs(Number number) {
		checkNumber(number);
		try {
			return this.number == number.doubleValue();
		} catch (ArithmeticException e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNotEqualTo(javax.money.MonetaryAmount)
	 */
	public boolean isNotEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		try {
			return this.number != amount.longValueExact();
		} catch (ArithmeticException e) {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNotEqualTo(java.lang.Number)
	 */
	public boolean isNotEqualTo(Number number) {
		checkNumber(number);
		return this.number != number.doubleValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMajorPart()
	 */
	public MonetaryAmount getMajorPart() {
		return new IntegralMoney(this.currency, getMajorLong());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMinorPart()
	 */
	public MonetaryAmount getMinorPart() {
		return new IntegralMoney(this.currency, 0L);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getNumberType()
	 */
	public Class<?> getNumberType() {
		return Long.class;
	}

	/*
	 * @see javax.money.MonetaryAmount#asType(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T> T asType(Class<T> type) {
		if (BigDecimal.class.equals(type)) {
			final T asType = (T) BigDecimal.valueOf(this.number);
			return asType;
		}
		if (Number.class.equals(type)) {
			final T asType = (T) Long.valueOf(this.number);
			return asType;
		}
		if (Double.class.equals(type)) {
			return (T) Double.valueOf(Long.valueOf(this.number).doubleValue());
		}
		if (Float.class.equals(type)) {
			return (T) Float.valueOf(Long.valueOf(this.number).floatValue());
		}
		if (Long.class.equals(type)) {
			return (T) Long.valueOf(Long.valueOf(this.number));
		}
		if (Integer.class.equals(type)) {
			return (T) Integer.valueOf(Integer.valueOf((int) this.number));
		}
		if (Short.class.equals(type)) {
			return (T) Short.valueOf(Short.valueOf((short) this.number));
		}
		if (Byte.class.equals(type)) {
			return (T) Byte.valueOf(Byte.valueOf((byte) this.number));
		}
		if (BigInteger.class.equals(type)) {
			return (T) BigInteger.valueOf(this.number);
		}
		throw new IllegalArgumentException("Unsupported representation type: "
				+ type);
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
	public static IntegralMoney valueOf(CurrencyUnit currency, BigDecimal number) {
		return new IntegralMoney(currency, number);
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
	public boolean hasSameCurrencyAs(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount must not be null.");
		}
		return this.currency.getNamespace().equals(
				amount.getCurrency().getNamespace())
				&& this.currency.getCurrencyCode().equals(
						amount.getCurrency().getCurrencyCode());
	}

}
