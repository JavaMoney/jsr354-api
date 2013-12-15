/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import javax.money.DummyAmountFactory.DummyAmount;

public final class DummyAmountFactory extends
		AbstractAmountFactory<DummyAmount> {

	private MonetaryContext<DummyAmount> DUMMY_CONTEXT = new MonetaryContext.Builder()
			.setFixedScale(true).setMaxScale(0)
			.setPrecision(0).build(DummyAmount.class);

	@Override
	public Class<DummyAmount> getImplementationType() {
		return DummyAmount.class;
	}

	@Override
	protected MonetaryContext<DummyAmount> loadDefaultMonetaryContext() {
		return DUMMY_CONTEXT;
	}

	@Override
	protected MonetaryContext<DummyAmount> loadMaxMonetaryContext() {
		return DUMMY_CONTEXT;
	}

	@Override
	public DummyAmount getAmount(CurrencyUnit currency, long number,
			MonetaryContext<?> monetaryContext) {
		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmount(CurrencyUnit currency, double number,
			MonetaryContext<?> monetaryContext) {
		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmount(CurrencyUnit currency, Number number,
			MonetaryContext<?> monetaryContext) {
		return new DummyAmount();
	}

	public static final class DummyAmount implements
			MonetaryAmount<DummyAmount> {

		@Override
		public CurrencyUnit getCurrency() {

			return null;
		}

		@Override
		public int getPrecision() {
			return 0;
		}

		@Override
		public int getScale() {

			return 0;
		}

		@Override
		public MonetaryContext getMonetaryContext() {

			return null;
		}

		@Override
		public Number getNumber() {
			return null;
		}

		@Override
		public <N extends Number> N getNumber(Class<N> type) {

			return null;
		}

		@Override
		public <N extends Number> N getNumberExact(Class<N> type) {

			return null;
		}

		@Override
		public <R> R query(MonetaryQuery<R> query) {

			return null;
		}

		@Override
		public DummyAmount with(MonetaryOperator operator) {

			return null;
		}

		@Override
		public DummyAmount with(CurrencyUnit unit) {

			return null;
		}

		@Override
		public DummyAmount with(CurrencyUnit unit, long amount) {

			return null;
		}

		@Override
		public DummyAmount with(CurrencyUnit unit, double amount) {

			return null;
		}

		@Override
		public DummyAmount with(CurrencyUnit unit, Number amount) {

			return null;
		}

		@Override
		public boolean isGreaterThan(MonetaryAmount<?> amount) {

			return false;
		}

		@Override
		public boolean isGreaterThanOrEqualTo(MonetaryAmount<?> amt) {

			return false;
		}

		@Override
		public boolean isLessThan(MonetaryAmount<?> amt) {

			return false;
		}

		@Override
		public boolean isLessThanOrEqualTo(MonetaryAmount<?> amt) {

			return false;
		}

		@Override
		public boolean isEqualTo(MonetaryAmount<?> amount) {

			return false;
		}

		@Override
		public boolean isNegative() {

			return false;
		}

		@Override
		public boolean isNegativeOrZero() {

			return false;
		}

		@Override
		public boolean isPositive() {

			return false;
		}

		@Override
		public boolean isPositiveOrZero() {

			return false;
		}

		@Override
		public boolean isZero() {

			return false;
		}

		@Override
		public int signum() {

			return 0;
		}

		@Override
		public DummyAmount add(MonetaryAmount<?> amount) {

			return null;
		}

		@Override
		public DummyAmount subtract(MonetaryAmount<?> amount) {

			return null;
		}

		@Override
		public DummyAmount multiply(long multiplicand) {

			return null;
		}

		@Override
		public DummyAmount multiply(double multiplicand) {

			return null;
		}

		@Override
		public DummyAmount multiply(Number multiplicand) {

			return null;
		}

		@Override
		public DummyAmount divide(long amount) {

			return null;
		}

		@Override
		public DummyAmount divide(double amount) {

			return null;
		}

		@Override
		public DummyAmount divide(Number amount) {

			return null;
		}

		@Override
		public DummyAmount remainder(long amount) {

			return null;
		}

		@Override
		public DummyAmount remainder(double amount) {

			return null;
		}

		@Override
		public DummyAmount remainder(Number amount) {

			return null;
		}

		@Override
		public DummyAmount[] divideAndRemainder(long amount) {

			return null;
		}

		@Override
		public DummyAmount[] divideAndRemainder(double amount) {

			return null;
		}

		@Override
		public DummyAmount[] divideAndRemainder(Number amount) {

			return null;
		}

		@Override
		public DummyAmount divideToIntegralValue(long divisor) {

			return null;
		}

		@Override
		public DummyAmount divideToIntegralValue(double divisor) {

			return null;
		}

		@Override
		public DummyAmount divideToIntegralValue(Number divisor) {

			return null;
		}

		@Override
		public DummyAmount scaleByPowerOfTen(int power) {

			return null;
		}

		@Override
		public DummyAmount abs() {

			return null;
		}

		@Override
		public DummyAmount negate() {

			return null;
		}

		@Override
		public DummyAmount plus() {

			return null;
		}

		@Override
		public DummyAmount pow(int power) {

			return null;
		}

		@Override
		public DummyAmount stripTrailingZeros() {

			return null;
		}

	}

}
