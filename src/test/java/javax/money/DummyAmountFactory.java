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

import java.math.BigDecimal;

import javax.money.DummyAmountFactory.DummyAmount;

public final class DummyAmountFactory implements
		MonetaryAmountFactory<DummyAmount> {

	private static MonetaryContext<DummyAmount> DUMMY_CONTEXT = new MonetaryContext.Builder()
			.setFixedScale(true).setMaxScale(0)
			.setPrecision(0).build(DummyAmount.class);

	@Override
	public Class<DummyAmount> getAmountType() {
		return DummyAmount.class;
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
			return DUMMY_CONTEXT;
		}

		@Override
		public Number getNumber() {
			return BigDecimal.ZERO;
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

			return new DummyAmount();
		}

		@Override
		public DummyAmount with(CurrencyUnit unit) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount with(CurrencyUnit unit, long amount) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount with(CurrencyUnit unit, double amount) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount with(CurrencyUnit unit, Number amount) {

			return new DummyAmount();
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

			return new DummyAmount();
		}

		@Override
		public DummyAmount subtract(MonetaryAmount<?> amount) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount multiply(long multiplicand) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount multiply(double multiplicand) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount multiply(Number multiplicand) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount divide(long amount) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount divide(double amount) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount divide(Number amount) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount remainder(long amount) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount remainder(double amount) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount remainder(Number amount) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount[] divideAndRemainder(long amount) {
			return new DummyAmount[] { new DummyAmount(), new DummyAmount() };
		}

		@Override
		public DummyAmount[] divideAndRemainder(double amount) {
			return new DummyAmount[] { new DummyAmount(), new DummyAmount() };
		}

		@Override
		public DummyAmount[] divideAndRemainder(Number amount) {
			return new DummyAmount[] { new DummyAmount(), new DummyAmount() };
		}

		@Override
		public DummyAmount divideToIntegralValue(long divisor) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount divideToIntegralValue(double divisor) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount divideToIntegralValue(Number divisor) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount scaleByPowerOfTen(int power) {

			return new DummyAmount();
		}

		@Override
		public DummyAmount abs() {

			return new DummyAmount();
		}

		@Override
		public DummyAmount negate() {

			return new DummyAmount();
		}

		@Override
		public DummyAmount plus() {

			return new DummyAmount();
		}

		@Override
		public DummyAmount stripTrailingZeros() {

			return new DummyAmount();
		}

	}

	@Override
	public DummyAmount getAmount(CurrencyUnit currency, long number) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmount(CurrencyUnit currency, double number) {
		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmount(CurrencyUnit currency, Number number) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmount(String currencyCode, long number) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmount(String currencyCode, double number) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmount(String currencyCode, Number number) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmount(String currencyCode, long number,
			MonetaryContext<?> context) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmount(String currencyCode, double number,
			MonetaryContext<?> context) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmount(String currencyCode, Number number,
			MonetaryContext<?> monetaryContext) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmountZero(CurrencyUnit currency) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmountZero(String currencyCode) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmountZero(CurrencyUnit currency,
			MonetaryContext<?> monetaryContext) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount getAmountZero(String currencyCode,
			MonetaryContext<?> monetaryContext) {

		return new DummyAmount();
	}

	@Override
	public MonetaryContext<DummyAmount> getDefaultMonetaryContext() {
		return DUMMY_CONTEXT;
	}

	@Override
	public MonetaryContext<DummyAmount> getMaximalMonetaryContext() {
		return DUMMY_CONTEXT;
	}

	@Override
	public DummyAmount getAmountFrom(MonetaryAmount<?> amt,
			MonetaryContext<?> monetaryContext) {

		return new DummyAmount();
	}

}
