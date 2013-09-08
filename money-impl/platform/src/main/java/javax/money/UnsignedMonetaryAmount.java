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

/**
 * This class decorates an arbitrary {@link MonetaryAmount} instance and ensure
 * no negative values can be created using this instance.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class UnsignedMonetaryAmount implements MonetaryAmount {
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
	private UnsignedMonetaryAmount(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount required.");
		}
		if (amount.isNegative()) {
			throw new IllegalArgumentException("Amount must be >= 0.");
		}
		this.amount = amount;
	}

	/**
	 * Access an {@link UnsignedMonetaryAmount} based on the given
	 * {@link MonetaryAmount}.
	 * 
	 * @param amount
	 * @return
	 */
	public UnsignedMonetaryAmount of(MonetaryAmount amount) {
		// TODO caching?
		return new UnsignedMonetaryAmount(amount);
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
	public UnsignedMonetaryAmount abs() {
		return of(this.amount.abs());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#add(javax.money.MonetaryAmount)
	 */
	@Override
	public UnsignedMonetaryAmount add(MonetaryAmount augend) {
		return of(this.amount.add(augend));
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divide(java.lang.Number)
	 */
	@Override
	public UnsignedMonetaryAmount divide(Number divisor) {
		return of(this.amount.divide(divisor));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideAndRemainder(java.lang.Number)
	 */
	@Override
	public UnsignedMonetaryAmount[] divideAndRemainder(Number divisor) {
		MonetaryAmount[] res = this.amount.divideAndRemainder(divisor);
		return new UnsignedMonetaryAmount[] { of(res[0]), of(res[1]) };
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideToIntegralValue(java.lang.Number)
	 */
	@Override
	public UnsignedMonetaryAmount divideToIntegralValue(Number divisor) {
		return of(this.amount.divideToIntegralValue(divisor));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#multiply(java.lang.Number)
	 */
	@Override
	public UnsignedMonetaryAmount multiply(Number multiplicand) {
		return of(this.amount.multiply(multiplicand));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#negate()
	 */
	@Override
	public UnsignedMonetaryAmount negate() {
		return of(this.amount.negate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#plus()
	 */
	@Override
	public UnsignedMonetaryAmount plus() {
		return of(this.amount.plus());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#subtract(javax.money.MonetaryAmount)
	 */
	@Override
	public UnsignedMonetaryAmount subtract(MonetaryAmount subtrahend) {
		return of(this.amount.subtract(subtrahend));
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#pow(int)
	 */
	@Override
	public UnsignedMonetaryAmount pow(int n) {
		return of(this.amount.pow(n));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#ulp()
	 */
	@Override
	public UnsignedMonetaryAmount ulp() {
		return of(this.amount.ulp());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#remainder(java.lang.Number)
	 */
	@Override
	public UnsignedMonetaryAmount remainder(Number divisor) {
		return of(this.amount.remainder(divisor));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#scaleByPowerOfTen(int)
	 */
	@Override
	public UnsignedMonetaryAmount scaleByPowerOfTen(int n) {
		return of(this.amount.scaleByPowerOfTen(n));
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
	public UnsignedMonetaryAmount from(Number amount) {
		return of(this.amount.from(amount));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#from(javax.money.CurrencyUnit,
	 * java.lang.Number)
	 */
	@Override
	public UnsignedMonetaryAmount from(CurrencyUnit currency, Number amount) {
		return of(this.amount.from(currency, amount));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#with(javax.money.MonetaryOperator)
	 */
	@Override
	public UnsignedMonetaryAmount with(MonetaryOperator adjuster) {
		return of(this.amount.with(adjuster));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#intValue()
	 */
	@Override
	public int intValue() {
		return this.amount.intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#intValueExact()
	 */
	@Override
	public int intValueExact() {
		return this.amount.intValueExact();
	}

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
	 * @see javax.money.MonetaryAmount#floatValue()
	 */
	@Override
	public float floatValue() {
		return this.amount.floatValue();
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
	 * @see javax.money.MonetaryAmount#byteValue()
	 */
	@Override
	public byte byteValue() {
		return this.amount.byteValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#shortValue()
	 */
	@Override
	public short shortValue() {
		return this.amount.shortValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#shortValueExact()
	 */
	@Override
	public short shortValueExact() {
		return this.amount.shortValueExact();
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
