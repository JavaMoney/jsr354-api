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

import javax.money.spi.MonetaryAmountProviderSpi;

public final class TestMonetaryAmountProvider implements
		MonetaryAmountProviderSpi {

	@Override
	public MonetaryAmount<?> getAmount(CurrencyUnit currency, long number,
			MonetaryContext monetaryContext) {
		return new DummyAmount();
	}

	@Override
	public MonetaryAmount<?> getAmount(CurrencyUnit currency, double number,
			MonetaryContext monetaryContext) {
		return new DummyAmount();
	}

	@Override
	public MonetaryAmount<?> getAmount(CurrencyUnit currency, Number number,
			MonetaryContext monetaryContext) {
		return new DummyAmount();
	}

	@Override
	public MonetaryAmount<?> getAmountFrom(MonetaryAmount<?> amt,
			MonetaryContext monetaryContext) {
		return new DummyAmount();
	}

	private static final class DummyAmount implements
			MonetaryAmount<DummyAmount> {

		@Override
		public CurrencyUnit getCurrency() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getPrecision() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getScale() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public MonetaryContext getMonetaryContext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Number getNumber() {
			return null;
		}

		@Override
		public <N extends Number> N getNumber(Class<N> type) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <N extends Number> N getNumberExact(Class<N> type) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <R> R query(MonetaryQuery<R> query) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount with(MonetaryOperator operator) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount with(CurrencyUnit unit) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount with(CurrencyUnit unit, long amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount with(CurrencyUnit unit, double amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount with(CurrencyUnit unit, Number amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isGreaterThan(MonetaryAmount<?> amount) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isGreaterThanOrEqualTo(MonetaryAmount<?> amt) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isLessThan(MonetaryAmount<?> amt) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isLessThanOrEqualTo(MonetaryAmount<?> amt) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEqualTo(MonetaryAmount<?> amount) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isNegative() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isNegativeOrZero() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isPositive() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isPositiveOrZero() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isZero() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int signum() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public DummyAmount add(MonetaryAmount<?> amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount subtract(MonetaryAmount<?> amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount multiply(long multiplicand) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount multiply(double multiplicand) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount multiply(Number multiplicand) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount divide(long amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount divide(double amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount divide(Number amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount remainder(long amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount remainder(double amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount remainder(Number amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount[] divideAndRemainder(long amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount[] divideAndRemainder(double amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount[] divideAndRemainder(Number amount) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount divideToIntegralValue(long divisor) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount divideToIntegralValue(double divisor) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount divideToIntegralValue(Number divisor) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount scaleByPowerOfTen(int power) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount abs() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount negate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount plus() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount pow(int power) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DummyAmount stripTrailingZeros() {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
