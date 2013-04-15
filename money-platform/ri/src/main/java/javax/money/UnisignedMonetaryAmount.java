/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money;

/**
 * This class decorates an arbitrary {@link MonetaryAmount} instance and ensure
 * no negative values can be created using this instance.
 * 
 * @author Anatole Tresch
 */
public final class UnisignedMonetaryAmount implements MonetaryAmount {

	private MonetaryAmount amount;

	public UnisignedMonetaryAmount of(MonetaryAmount amount) {
		return new UnisignedMonetaryAmount(amount);
	}

	private UnisignedMonetaryAmount(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount required.");
		}
		if (amount.isNegative()) {
			throw new IllegalArgumentException("Amount must be >= 0.");
		}
		this.amount = amount;
	}

	@Override
	public CurrencyUnit getCurrency() {
		return this.amount.getCurrency();
	}

	@Override
	public UnisignedMonetaryAmount abs() {
		return of(this.amount.abs());
	}

	@Override
	public UnisignedMonetaryAmount add(MonetaryAmount augend) {
		return of(this.amount.add(augend));
	}

	@Override
	public UnisignedMonetaryAmount add(Number augend) {
		return of(this.amount.add(augend));
	}

	@Override
	public UnisignedMonetaryAmount divide(MonetaryAmount divisor) {
		return of(this.amount.divide(divisor));
	}

	@Override
	public UnisignedMonetaryAmount divide(Number divisor) {
		return of(this.amount.divide(divisor));
	}

	@Override
	public UnisignedMonetaryAmount[] divideAndRemainder(MonetaryAmount divisor) {
		MonetaryAmount[] res = this.amount.divideAndRemainder(divisor);
		return new UnisignedMonetaryAmount[] { of(res[0]), of(res[1]) };
	}

	@Override
	public UnisignedMonetaryAmount[] divideAndRemainder(Number divisor) {
		MonetaryAmount[] res = this.amount.divideAndRemainder(divisor);
		return new UnisignedMonetaryAmount[] { of(res[0]), of(res[1]) };
	}

	@Override
	public UnisignedMonetaryAmount divideToIntegralValue(MonetaryAmount divisor) {
		return of(this.amount.divideToIntegralValue(divisor));
	}

	@Override
	public UnisignedMonetaryAmount divideToIntegralValue(Number divisor) {
		return of(this.amount.divideToIntegralValue(divisor));
	}

	@Override
	public UnisignedMonetaryAmount multiply(MonetaryAmount multiplicand) {
		return of(this.amount.multiply(multiplicand));
	}

	@Override
	public UnisignedMonetaryAmount multiply(Number multiplicand) {
		return of(this.amount.multiply(multiplicand));
	}

	@Override
	public UnisignedMonetaryAmount negate() {
		return of(this.amount.negate());
	}

	@Override
	public UnisignedMonetaryAmount plus() {
		return of(this.amount.plus());
	}

	@Override
	public UnisignedMonetaryAmount subtract(MonetaryAmount subtrahend) {
		return of(this.amount.subtract(subtrahend));
	}

	@Override
	public UnisignedMonetaryAmount subtract(Number subtrahend) {
		return of(this.amount.subtract(subtrahend));
	}

	@Override
	public UnisignedMonetaryAmount pow(int n) {
		return of(this.amount.pow(n));
	}

	@Override
	public UnisignedMonetaryAmount ulp() {
		return of(this.amount.ulp());
	}

	@Override
	public UnisignedMonetaryAmount remainder(MonetaryAmount divisor) {
		return of(this.amount.remainder(divisor));
	}

	@Override
	public UnisignedMonetaryAmount remainder(Number divisor) {
		return of(this.amount.remainder(divisor));
	}

	@Override
	public UnisignedMonetaryAmount scaleByPowerOfTen(int n) {
		return of(this.amount.scaleByPowerOfTen(n));
	}

	@Override
	public boolean isZero() {
		return this.amount.isZero();
	}

	@Override
	public boolean isPositive() {
		return this.amount.isPositive();
	}

	@Override
	public boolean isPositiveOrZero() {
		return this.amount.isPositiveOrZero();
	}

	@Override
	public boolean isNegative() {
		return this.amount.isNegative();
	}

	@Override
	public boolean isNegativeOrZero() {
		return this.amount.isNegativeOrZero();
	}

	@Override
	public UnisignedMonetaryAmount from(Number amount) {
		return of(this.amount.from(amount));
	}

	@Override
	public UnisignedMonetaryAmount from(CurrencyUnit currency, Number amount) {
		return of(this.amount.from(currency, amount));
	}

	@Override
	public UnisignedMonetaryAmount with(MonetaryOperator adjuster) {
		return of(this.amount.with(adjuster));
	}

	@Override
	public int getScale() {
		return this.amount.getScale();
	}

	@Override
	public int getPrecision() {
		return this.amount.getPrecision();
	}

	@Override
	public int intValue() {
		return this.amount.intValue();
	}

	@Override
	public int intValueExact() {
		return this.amount.intValueExact();
	}

	@Override
	public long longValue() {
		return this.amount.longValue();
	}

	@Override
	public long longValueExact() {
		return this.amount.longValueExact();
	}

	@Override
	public float floatValue() {
		return this.amount.floatValue();
	}

	@Override
	public double doubleValue() {
		return this.amount.doubleValue();
	}

	@Override
	public byte byteValue() {
		return this.amount.byteValue();
	}

	@Override
	public short shortValue() {
		return this.amount.shortValue();
	}

	@Override
	public short shortValueExact() {
		return this.amount.shortValueExact();
	}

	@Override
	public int signum() {
		return this.amount.signum();
	}

	@Override
	public boolean isLessThan(MonetaryAmount amount) {
		return this.amount.isLessThan(amount);
	}

	@Override
	public boolean isLessThanOrEqualTo(MonetaryAmount amount) {
		return this.amount.isLessThanOrEqualTo(amount);
	}

	@Override
	public boolean isGreaterThan(MonetaryAmount amount) {
		return this.amount.isGreaterThan(amount);
	}

	@Override
	public boolean isGreaterThanOrEqualTo(MonetaryAmount amount) {
		return this.amount.isGreaterThanOrEqualTo(amount);
	}

	@Override
	public boolean isEqualTo(MonetaryAmount amount) {
		return this.amount.isEqualTo(amount);
	}

	@Override
	public boolean isNotEqualTo(MonetaryAmount amount) {
		return this.amount.isNotEqualTo(amount);
	}

	@Override
	public <T> T asType(Class<T> type) {
		return this.amount.asType(type);
	}

	@Override
	public Class<?> getNumberType() {
		return this.amount.getNumberType();
	}

	public MonetaryAmount toSignedAmount() {
		return this.amount;
	}
}
