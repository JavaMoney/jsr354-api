/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAdjuster;
import javax.money.MonetaryAmount;
import javax.money.MonetaryQuery;
import javax.money.Money;

import org.javamoney.ext.Predicate;

/**
 * Platform RI: This class decorates an arbitrary {@link MonetaryAmount}
 * instance and ensure the given {@link Predicate} is always {@code true}.
 * <p>
 * As required by the {@link MonetaryAmount} interface, this class is
 * <ul>
 * <li>immutable</li>
 * <li>final</li>
 * <li>thread-safe/li>
 * <li>serializable</li>
 * </ul>
 * 
 * As a consequence all this attributes must also be true for the
 * {@link Predicate} used.
 * 
 * @author Anatole Tresch
 */
final class ConstraintMoney implements MonetaryAmount {
	/** The amount's predicate. */
	private Predicate<MonetaryAmount> predicate;
	/** The underlying amount. */
	private final Money amount;

	/**
	 * Creates a new wrapper instance.
	 * 
	 * @param amount
	 *            the underlying amount, not null and not negative.
	 * @throws IllegalArgumentException
	 *             if the amount passed is negative.
	 */
	ConstraintMoney(Money amount,
			Predicate<MonetaryAmount> predicate) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount required.");
		}
		if (predicate == null) {
			throw new IllegalArgumentException("predicate required.");
		}
		if (!predicate.isPredicateTrue(amount)) {
			throw new IllegalArgumentException("Constraint failed: "
					+ predicate + " with " + amount);
		}
		this.amount = amount;
		this.predicate = predicate;
	}

	/**
	 * Access an {@link ConstraintMoney} based on the given
	 * {@link MonetaryAmount}.
	 * 
	 * @param amount
	 * @return
	 */
	private static ConstraintMoney of(Money amount,
			Predicate<MonetaryAmount> predicate) {
		return new ConstraintMoney(amount, predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getCurrency()
	 */
	@Override
	public CurrencyUnit getCurrency() {
		return this.amount.getCurrency();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#abs()
	 */
	public ConstraintMoney abs() {
		return of(this.amount.abs(), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#add(javax.money.MonetaryAmount)
	 */
	public ConstraintMoney add(Money augend) {
		return of(this.amount.add(augend), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divide(java.lang.Number)
	 */
	public ConstraintMoney divide(Number divisor) {
		return of(this.amount.divide(divisor), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideAndRemainder(java.lang.Number)
	 */
	public ConstraintMoney[] divideAndRemainder(Number divisor) {
		MonetaryAmount[] res = this.amount.divideAndRemainder(divisor);
		return new ConstraintMoney[] { of(Money.from(res[0]), predicate),
				of(Money.from(res[1]), predicate) };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideToIntegralValue(java.lang.Number)
	 */
	public ConstraintMoney divideToIntegralValue(Number divisor) {
		return of(this.amount.divideToIntegralValue(divisor), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#multiply(java.lang.Number)
	 */
	public ConstraintMoney multiply(Number multiplicand) {
		return of(this.amount.multiply(multiplicand), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#negate()
	 */
	public ConstraintMoney negate() {
		return of(this.amount.negate(), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#plus()
	 */
	public ConstraintMoney plus() {
		return of(this.amount.plus(), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#subtract(javax.money.MonetaryAmount)
	 */
	public ConstraintMoney subtract(Money subtrahend) {
		return of(this.amount.subtract(subtrahend), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#pow(int)
	 */
	public ConstraintMoney pow(int n) {
		return of(this.amount.pow(n), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#ulp()
	 */
	public ConstraintMoney ulp() {
		return of(this.amount.ulp(), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#remainder(java.lang.Number)
	 */
	public ConstraintMoney remainder(Number divisor) {
		return of(this.amount.remainder(divisor), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#scaleByPowerOfTen(int)
	 */
	public ConstraintMoney scaleByPowerOfTen(int n) {
		return of(this.amount.scaleByPowerOfTen(n), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isZero()
	 */
	public boolean isZero() {
		return this.amount.isZero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isPositive()
	 */
	public boolean isPositive() {
		return this.amount.isPositive();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isPositiveOrZero()
	 */
	public boolean isPositiveOrZero() {
		return this.amount.isPositiveOrZero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNegative()
	 */
	public boolean isNegative() {
		return this.amount.isNegative();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNegativeOrZero()
	 */
	public boolean isNegativeOrZero() {
		return this.amount.isNegativeOrZero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#from(java.lang.Number)
	 */
	public ConstraintMoney with(Number amount) {
		return of(this.amount.with(amount), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#from(javax.money.CurrencyUnit,
	 * java.lang.Number)
	 */
	public ConstraintMoney with(CurrencyUnit currency, Number amount) {
		return of(this.amount.with(currency, amount), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#with(javax.money.MonetaryOperator)
	 */
	@Override
	public ConstraintMoney with(MonetaryAdjuster adjuster) {
		return of(this.amount.with(adjuster), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getScale()
	 */
	public int getScale() {
		return this.amount.getScale();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getPrecision()
	 */
	public int getPrecision() {
		return this.amount.getPrecision();
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see javax.money.MonetaryAmount#intValue()
	// */
	// @Override
	// public int intValue() {
	// return this.amount.intValue();
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see javax.money.MonetaryAmount#intValueExact()
	// */
	// @Override
	// public int intValueExact() {
	// return this.amount.intValueExact();
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#longValue()
	 */
	public long longValue() {
		return this.amount.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#longValueExact()
	 */
	public long longValueExact() {
		return this.amount.longValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#doubleValue()
	 */
	public double doubleValue() {
		return this.amount.doubleValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#signum()
	 */
	public int signum() {
		return this.amount.signum();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isLessThan(javax.money.MonetaryAmount)
	 */
	public boolean isLessThan(Money amount) {
		return this.amount.isLessThan(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#isLessThanOrEqualTo(javax.money.MonetaryAmount
	 * )
	 */
	public boolean isLessThanOrEqualTo(Money amount) {
		return this.amount.isLessThanOrEqualTo(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isGreaterThan(javax.money.MonetaryAmount)
	 */
	public boolean isGreaterThan(MonetaryAmount amount) {
		return this.amount.isGreaterThan(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#isGreaterThanOrEqualTo(javax.money.MonetaryAmount
	 * )
	 */
	public boolean isGreaterThanOrEqualTo(MonetaryAmount amount) {
		return this.amount.isGreaterThanOrEqualTo(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isEqualTo(javax.money.MonetaryAmount)
	 */
	public boolean isEqualTo(MonetaryAmount amount) {
		return this.amount.isEqualTo(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNotEqualTo(javax.money.MonetaryAmount)
	 */
	public boolean isNotEqualTo(MonetaryAmount amount) {
		return this.amount.isNotEqualTo(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#asType(java.lang.Class)
	 */
	public <T> T asType(Class<T> type) {
		return this.amount.asType(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getNumberType()
	 */
	public Class<?> getNumberType() {
		return this.amount.getNumberType();
	}

	/**
	 * This method allows to access the internal base amount, which can be used
	 * for normal calculations, that also may be negative.
	 * 
	 * @return the base {@link MonetaryAmount} instance.
	 */
	public MonetaryAmount toSignedAmount() {
		return this.amount;
	}

	@Override
	public <R> R query(MonetaryQuery<R> query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getAmountWhole() {
		return this.amount.getAmountWhole();
	}

	@Override
	public long getAmountFractionNumerator() {
		return this.amount.getAmountFractionNumerator();
	}

	@Override
	public long getAmountFractionDenominator() {
		return this.amount.getAmountFractionDenominator();
	}
}
