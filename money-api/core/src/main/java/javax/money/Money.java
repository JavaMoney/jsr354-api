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
package javax.money;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

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
	public Money(CurrencyUnit currency, Number number) {
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
		return new Money(MoneyCurrency.getInstance(isoCurrencyCode), number);
	}

/**
	 * Facory method creating a zero instance with the given {@code currency);
	 * @param currency the target currency of the amount being created.
	 * @return
	 */
	public static MonetaryAmount zero(CurrencyUnit currency) {
		return new Money(currency, BigDecimal.ZERO);
	}

	/**
	 * Get the number represnetation type, E.g. {@code java.math.BigDecimal}.
	 * 
	 * @return
	 */
	public static Class<?> getNumberClass() {
		return BigDecimal.class;
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
		return new Money(this.currency, this.number.add(amount
				.asType(BigDecimal.class)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#add(java.lang.Number)
	 */
	public MonetaryAmount add(Number number) {
		checkNumber(number);
		BigDecimal dec = this.number.add(BigDecimal.valueOf(number
				.doubleValue()));
		return new Money(this.currency, dec);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divide(javax.money.MonetaryAmount)
	 */
	public MonetaryAmount divide(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		BigDecimal dec = this.number.divide(divisor.asType(BigDecimal.class));
		return new Money(this.currency, dec);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divide(java.lang.Number)
	 */
	public MonetaryAmount divide(Number divisor) {
		checkNumber(divisor);
		BigDecimal dec = this.number.divide(BigDecimal.valueOf(divisor
				.doubleValue()));
		return new Money(this.currency, dec);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#divideAndRemainder(javax.money.MonetaryAmount)
	 */
	public MonetaryAmount[] divideAndRemainder(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		BigDecimal[] dec = this.number.divideAndRemainder(divisor
				.asType(BigDecimal.class));
		return new MonetaryAmount[] { new Money(this.currency, dec[0]),
				new Money(this.currency, dec[1]) };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideAndRemainder(java.lang.Number)
	 */
	public MonetaryAmount[] divideAndRemainder(Number divisor) {
		checkNumber(divisor);
		BigDecimal[] dec = this.number.divideAndRemainder(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new MonetaryAmount[] { new Money(this.currency, dec[0]),
				new Money(this.currency, dec[1]) };
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
		BigDecimal dec = this.number.divideToIntegralValue(divisor
				.asType(BigDecimal.class));
		return new Money(this.currency, dec);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideToIntegralValue(java.lang.Number)
	 */
	public MonetaryAmount divideToIntegralValue(Number divisor) {
		checkNumber(divisor);
		BigDecimal dec = this.number.divideToIntegralValue(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new Money(this.currency, dec);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#multiply(javax.money.MonetaryAmount)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#negate()
	 */
	public MonetaryAmount negate() {
		return new Money(this.currency, this.number.negate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#plus()
	 */
	public MonetaryAmount plus() {
		return new Money(this.currency, this.number.plus());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#subtract(javax.money.MonetaryAmount)
	 */
	public MonetaryAmount subtract(MonetaryAmount subtrahend) {
		checkAmountParameter(subtrahend);
		return new Money(this.currency, this.number.subtract(subtrahend
				.asType(BigDecimal.class)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#subtract(java.lang.Number)
	 */
	public MonetaryAmount subtract(Number subtrahend) {
		checkNumber(subtrahend);
		return new Money(this.currency, this.number.subtract(BigDecimal
				.valueOf(subtrahend.doubleValue())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#pow(int)
	 */
	public MonetaryAmount pow(int n) {
		return new Money(this.currency, this.number.pow(n));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#ulp()
	 */
	public MonetaryAmount ulp() {
		return new Money(this.currency, this.number.ulp());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#remainder(javax.money.MonetaryAmount)
	 */
	public MonetaryAmount remainder(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		return new Money(this.currency, this.number.remainder(divisor
				.asType(BigDecimal.class)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#remainder(java.lang.Number)
	 */
	public MonetaryAmount remainder(Number divisor) {
		checkNumber(divisor);
		return new Money(this.currency, this.number.remainder(BigDecimal
				.valueOf(divisor.doubleValue())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#scaleByPowerOfTen(int)
	 */
	public MonetaryAmount scaleByPowerOfTen(int n) {
		return new Money(this.currency, this.number.scaleByPowerOfTen(n));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMajorLong()
	 */
	public long getMajorLong() {
		return this.number.setScale(0, RoundingMode.DOWN).longValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMajorInt()
	 */
	public int getMajorInt() {
		return this.number.setScale(0, RoundingMode.DOWN).intValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMinorLong()
	 */
	public long getMinorLong() {
		return this.number.movePointRight(this.number.precision())
				.longValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMinorInt()
	 */
	public int getMinorInt() {
		return this.number.movePointRight(this.number.precision())
				.intValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isZero()
	 */
	public boolean isZero() {
		return this.number.signum() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isPositive()
	 */
	public boolean isPositive() {
		return signum() == 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isPositiveOrZero()
	 */
	public boolean isPositiveOrZero() {
		return signum() >= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNegative()
	 */
	public boolean isNegative() {
		return signum() == -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNegativeOrZero()
	 */
	public boolean isNegativeOrZero() {
		return signum() <= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#with(java.lang.Number)
	 */
	public MonetaryAmount with(Number amount) {
		checkNumber(amount);
		return new Money(this.currency,
				BigDecimal.valueOf(amount.doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#with(javax.money.AmountAdjuster[])
	 */
	public MonetaryAmount with(AmountAdjuster... adjuster) {
		MonetaryAmount amount = this;
		for (AmountAdjuster amountAdjuster : adjuster) {
			amount = amountAdjuster.adjust(amount);
		}
		return amount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getScale()
	 */
	public int getScale() {
		return this.number.scale();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getPrecision()
	 */
	public int getPrecision() {
		return this.number.precision();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#intValue()
	 */
	public int intValue() {
		return this.number.intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#intValueExact()
	 */
	public int intValueExact() {
		return this.number.intValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#longValue()
	 */
	public long longValue() {
		return this.number.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#longValueExact()
	 */
	public long longValueExact() {
		return this.number.longValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#floatValue()
	 */
	public float floatValue() {
		return this.number.floatValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#doubleValue()
	 */
	public double doubleValue() {
		return this.number.doubleValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#byteValue()
	 */
	public byte byteValue() {
		return this.number.byteValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#shortValue()
	 */
	public short shortValue() {
		return this.number.shortValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#shortValueExact()
	 */
	public short shortValueExact() {
		return number.shortValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#signum()
	 */

	public int signum() {
		return this.number.signum();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#toEngineeringString()
	 */
	public String toEngineeringString() {
		return this.currency.toString() + ' '
				+ this.number.toEngineeringString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#toPlainString()
	 */
	public String toPlainString() {
		return this.currency.toString() + ' ' + this.number.toPlainString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#lessThan(javax.money.MonetaryAmount)
	 */
	public boolean lessThan(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) < 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#lessThan(java.lang.Number)
	 */
	public boolean lessThan(Number number) {
		checkNumber(number);
		return this.number.compareTo(BigDecimal.valueOf(number.doubleValue())) < 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#lessThanOrEqualTo(javax.money.MonetaryAmount)
	 */
	public boolean lessThanOrEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) <= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#lessThanOrEqualTo(java.lang.Number)
	 */
	public boolean lessThanOrEqualTo(Number number) {
		checkNumber(number);
		return this.number.compareTo(BigDecimal.valueOf(number.doubleValue())) <= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#greaterThan(javax.money.MonetaryAmount)
	 */
	public boolean greaterThan(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#greaterThan(java.lang.Number)
	 */
	public boolean greaterThan(Number number) {
		checkNumber(number);
		return this.number.compareTo(BigDecimal.valueOf(number.doubleValue())) > 0;
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
		return number.compareTo(amount.asType(BigDecimal.class)) >= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#greaterThanOrEqualTo(java.lang.Number)
	 */
	public boolean greaterThanOrEqualTo(Number number) {
		checkNumber(number);
		return this.number.compareTo(BigDecimal.valueOf(number.doubleValue())) >= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isEqualTo(javax.money.MonetaryAmount)
	 */
	public boolean isEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#hasSameNumberAs(java.lang.Number)
	 */
	public boolean hasSameNumberAs(Number number) {
		checkNumber(number);
		return this.number.compareTo(BigDecimal.valueOf(number.doubleValue())) == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNotEqualTo(javax.money.MonetaryAmount)
	 */
	public boolean isNotEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNotEqualTo(java.lang.Number)
	 */
	public boolean isNotEqualTo(Number number) {
		checkNumber(number);
		return this.number.compareTo(BigDecimal.valueOf(number.doubleValue())) != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMajorPart()
	 */
	public MonetaryAmount getMajorPart() {
		return new Money(this.currency, getMajorLong());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMinorPart()
	 */
	public MonetaryAmount getMinorPart() {
		return new Money(this.currency, getMinorLong());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getNumberType()
	 */
	public Class<?> getNumberType() {
		return BigDecimal.class;
	}

	/*
	 * @see javax.money.MonetaryAmount#asType(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T> T asType(Class<T> type) {
		if (BigDecimal.class.equals(type)) {
			final T asType = (T) this.number;
			return asType;
		}
		if (Number.class.equals(type)) {
			final T asType = (T) this.number;
			return asType;
		}
		if (Double.class.equals(type)) {
			return (T) Double.valueOf(this.number.doubleValue());
		}
		if (Float.class.equals(type)) {
			return (T) Float.valueOf(this.number.floatValue());
		}
		if (Long.class.equals(type)) {
			return (T) Long.valueOf(this.number.longValue());
		}
		if (Integer.class.equals(type)) {
			return (T) Integer.valueOf(this.number.intValue());
		}
		if (Short.class.equals(type)) {
			return (T) Short.valueOf(this.number.shortValue());
		}
		if (Byte.class.equals(type)) {
			return (T) Byte.valueOf(this.number.byteValue());
		}
		if (BigInteger.class.equals(type)) {
			return (T) this.number.toBigInteger();
		}
		throw new IllegalArgumentException("Unsupported representation type: "
				+ type);
	}

	/*
	 * }(non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#asType(java.lang.Class,
	 * javax.money.AmountAdjuster[])
	 */
	public <T> T asType(Class<T> type, AmountAdjuster... adjustment) {
		MonetaryAmount amount = this;
		for (int i = 0; i < adjustment.length; i++) {
			amount = adjustment[i].adjust(amount);
		}
		return amount.asType(type);
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
	public static Money valueOf(CurrencyUnit currency, BigDecimal number) {
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
	public boolean hasSameCurrencyAs(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount must not be null.");
		}
		return this.currency.getNamespace().equals(
				amount.getCurrency().getNamespace())
				&& this.currency.getCurrencyCode().equals(
						amount.getCurrency().getCurrencyCode());
	}

	@Override
	public MonetaryAmount setValue(Number number) {
		if (number instanceof BigDecimal) {
			return new Money(this.currency, number);
		}
		return null;
	}

}
