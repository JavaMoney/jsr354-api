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
package javax.money;

/**
 * This class decorates an arbitrary {@link MonetaryAmount} instance and ensure
 * the given {@link Predicate} is always {@code true}.
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
final class ConstraintAmount implements MonetaryAmount {
	/** The amount's predicate. */
	private Predicate<MonetaryAmount> predicate;
	/** The underlying amount. */
	private final MonetaryAmount amount;

	/**
	 * Creates a new wrapper instance.
	 * 
	 * @param amount
	 *            the underlying amount, not null and not negative.
	 * @throws IllegalArgumentException
	 *             if the amount passed is negative.
	 */
	ConstraintAmount(MonetaryAmount amount,
			Predicate<MonetaryAmount> predicate) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount required.");
		}
		if (predicate == null) {
			throw new IllegalArgumentException("predicate required.");
		}
		if (!predicate.apply(amount)) {
			throw new IllegalArgumentException("Constraint failed: "
					+ predicate + " with " + amount);
		}
		this.amount = amount;
		this.predicate = predicate;
	}

	/**
	 * Access an {@link ConstraintAmount} based on the given
	 * {@link MonetaryAmount}.
	 * 
	 * @param amount
	 * @return
	 */
	private ConstraintAmount of(MonetaryAmount amount,
			Predicate<MonetaryAmount> predicate) {
		return new ConstraintAmount(amount, predicate);
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
	@Override
	public ConstraintAmount abs() {
		return of(this.amount.abs(), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#add(javax.money.MonetaryAmount)
	 */
	@Override
	public ConstraintAmount add(MonetaryAmount augend) {
		return of(this.amount.add(augend), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divide(java.lang.Number)
	 */
	@Override
	public ConstraintAmount divide(Number divisor) {
		return of(this.amount.divide(divisor), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideAndRemainder(java.lang.Number)
	 */
	@Override
	public ConstraintAmount[] divideAndRemainder(Number divisor) {
		MonetaryAmount[] res = this.amount.divideAndRemainder(divisor);
		return new ConstraintAmount[] { of(res[0], predicate),
				of(res[1], predicate) };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideToIntegralValue(java.lang.Number)
	 */
	@Override
	public ConstraintAmount divideToIntegralValue(Number divisor) {
		return of(this.amount.divideToIntegralValue(divisor), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#multiply(java.lang.Number)
	 */
	@Override
	public ConstraintAmount multiply(Number multiplicand) {
		return of(this.amount.multiply(multiplicand), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#negate()
	 */
	@Override
	public ConstraintAmount negate() {
		return of(this.amount.negate(), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#plus()
	 */
	@Override
	public ConstraintAmount plus() {
		return of(this.amount.plus(), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#subtract(javax.money.MonetaryAmount)
	 */
	@Override
	public ConstraintAmount subtract(MonetaryAmount subtrahend) {
		return of(this.amount.subtract(subtrahend), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#pow(int)
	 */
	@Override
	public ConstraintAmount pow(int n) {
		return of(this.amount.pow(n), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#ulp()
	 */
	@Override
	public ConstraintAmount ulp() {
		return of(this.amount.ulp(), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#remainder(java.lang.Number)
	 */
	@Override
	public ConstraintAmount remainder(Number divisor) {
		return of(this.amount.remainder(divisor), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#scaleByPowerOfTen(int)
	 */
	@Override
	public ConstraintAmount scaleByPowerOfTen(int n) {
		return of(this.amount.scaleByPowerOfTen(n), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isZero()
	 */
	@Override
	public boolean isZero() {
		return this.amount.isZero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isPositive()
	 */
	@Override
	public boolean isPositive() {
		return this.amount.isPositive();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isPositiveOrZero()
	 */
	@Override
	public boolean isPositiveOrZero() {
		return this.amount.isPositiveOrZero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNegative()
	 */
	@Override
	public boolean isNegative() {
		return this.amount.isNegative();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNegativeOrZero()
	 */
	@Override
	public boolean isNegativeOrZero() {
		return this.amount.isNegativeOrZero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#from(java.lang.Number)
	 */
	@Override
	public ConstraintAmount from(Number amount) {
		return of(this.amount.from(amount), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#from(javax.money.CurrencyUnit,
	 * java.lang.Number)
	 */
	@Override
	public ConstraintAmount from(CurrencyUnit currency, Number amount) {
		return of(this.amount.from(currency, amount), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#with(javax.money.MonetaryOperator)
	 */
	@Override
	public ConstraintAmount with(MonetaryOperator adjuster) {
		return of(this.amount.with(adjuster), predicate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getScale()
	 */
	@Override
	public int getScale() {
		return this.amount.getScale();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getPrecision()
	 */
	@Override
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
	@Override
	public long longValue() {
		return this.amount.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#longValueExact()
	 */
	@Override
	public long longValueExact() {
		return this.amount.longValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#doubleValue()
	 */
	@Override
	public double doubleValue() {
		return this.amount.doubleValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#signum()
	 */
	@Override
	public int signum() {
		return this.amount.signum();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isLessThan(javax.money.MonetaryAmount)
	 */
	@Override
	public boolean isLessThan(MonetaryAmount amount) {
		return this.amount.isLessThan(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#isLessThanOrEqualTo(javax.money.MonetaryAmount
	 * )
	 */
	@Override
	public boolean isLessThanOrEqualTo(MonetaryAmount amount) {
		return this.amount.isLessThanOrEqualTo(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isGreaterThan(javax.money.MonetaryAmount)
	 */
	@Override
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
	@Override
	public boolean isGreaterThanOrEqualTo(MonetaryAmount amount) {
		return this.amount.isGreaterThanOrEqualTo(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isEqualTo(javax.money.MonetaryAmount)
	 */
	@Override
	public boolean isEqualTo(MonetaryAmount amount) {
		return this.amount.isEqualTo(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNotEqualTo(javax.money.MonetaryAmount)
	 */
	@Override
	public boolean isNotEqualTo(MonetaryAmount amount) {
		return this.amount.isNotEqualTo(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#asType(java.lang.Class)
	 */
	@Override
	public <T> T asType(Class<T> type) {
		return this.amount.asType(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getNumberType()
	 */
	@Override
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
}
