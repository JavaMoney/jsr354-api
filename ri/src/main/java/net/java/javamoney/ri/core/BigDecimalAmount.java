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
import java.math.RoundingMode;

import javax.money.AmountAdjuster;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

class BigDecimalAmount implements MonetaryAmount {

	private BigDecimal number;
	private CurrencyUnit currency;

	BigDecimalAmount(CurrencyUnit currency, BigDecimal number) {
		if (currency == null) {
			throw new IllegalArgumentException("Currency is required.");
		}
		if (number == null) {
			throw new IllegalArgumentException("Number is required.");
		}
		this.currency = currency;
		this.number = number;
		// TODO ensure internal precision!
	}

	public BigDecimalAmount(CurrencyUnit currency, Number number) {
		if (currency == null) {
			throw new IllegalArgumentException("Currency is required.");
		}
		if (number == null) {
			throw new IllegalArgumentException("Number is required.");
		}
		this.currency = currency;
		this.number = BigDecimal.valueOf(number.doubleValue());
		// TODO ensure internal precision!
	}

	public int compareTo(MonetaryAmount o) {
		int compare = this.currency.compareTo(o.getCurrency());
		if (compare == 0) {
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
		if (amount == null) {
			throw new IllegalArgumentException("Amount rewuired.");
		}
		if (amount.lessThan(this)) {
			return amount;
		}
		return this;
	}

	public MonetaryAmount max(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount rewuired.");
		}
		if (amount.greaterThan(this)) {
			return amount;
		}
		return this;
	}

	// Arithmetic Operations

	public MonetaryAmount add(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount must not be null.");
		}
		if (!this.currency.equals(amount.getCurrency())) {
			throw new IllegalArgumentException("Currency mismatch: required : "
					+ this.currency + ", but was " + amount.getCurrency());
		}
		return new BigDecimalAmount(this.currency, this.number.add(amount
				.asType(BigDecimal.class)));
	}

	public MonetaryAmount add(Number number) {
		BigDecimal dec = this.number.add(BigDecimal.valueOf(number
				.doubleValue()));
		return new BigDecimalAmount(this.currency, dec);
	}

	public MonetaryAmount divide(MonetaryAmount divisor) {
		// TODO is division OK with different currencies?
		BigDecimal dec = this.number.divide(divisor.asType(BigDecimal.class));
		return new BigDecimalAmount(this.currency, dec);
	}

	public MonetaryAmount divide(Number divisor) {
		BigDecimal dec = this.number.divide(BigDecimal.valueOf(divisor
				.doubleValue()));
		return new BigDecimalAmount(this.currency, dec);
	}

	public MonetaryAmount[] divideAndRemainder(MonetaryAmount divisor) {
		BigDecimal[] dec = this.number.divideAndRemainder(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new MonetaryAmount[] {
				new BigDecimalAmount(this.currency, dec[0]),
				new BigDecimalAmount(this.currency, dec[1]) };
	}

	public MonetaryAmount[] divideAndRemainder(Number divisor) {
		BigDecimal[] dec = this.number.divideAndRemainder(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new MonetaryAmount[] {
				new BigDecimalAmount(this.currency, dec[0]),
				new BigDecimalAmount(this.currency, dec[1]) };
	}

	public MonetaryAmount divideToIntegralValue(MonetaryAmount divisor) {
		BigDecimal dec = this.number.divideToIntegralValue(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new BigDecimalAmount(this.currency, dec);
	}

	public MonetaryAmount divideToIntegralValue(Number divisor) {
		BigDecimal dec = this.number.divideToIntegralValue(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new BigDecimalAmount(this.currency, dec);
	}

	public MonetaryAmount multiply(MonetaryAmount multiplicand) {
		BigDecimal dec = this.number.multiply(multiplicand
				.asType(BigDecimal.class));
		return new BigDecimalAmount(this.currency, dec);
	}

	public MonetaryAmount multiply(Number multiplicand) {
		BigDecimal dec = this.number.multiply(BigDecimal.valueOf(multiplicand
				.doubleValue()));
		return new BigDecimalAmount(this.currency, dec);
	}

	public MonetaryAmount negate() {
		return new BigDecimalAmount(this.currency, this.number.negate());
	}

	public MonetaryAmount plus() {
		return new BigDecimalAmount(this.currency, this.number.plus());
	}

	public MonetaryAmount subtract(MonetaryAmount subtrahend) {
		return new BigDecimalAmount(this.currency,
				this.number.subtract(subtrahend.asType(BigDecimal.class)));
	}

	public MonetaryAmount subtract(Number subtrahend) {
		return new BigDecimalAmount(this.currency,
				this.number.subtract(BigDecimal.valueOf(subtrahend
						.doubleValue())));
	}

	public MonetaryAmount pow(int n) {
		return new BigDecimalAmount(this.currency, this.number.pow(n));
	}

	public MonetaryAmount ulp() {
		return new BigDecimalAmount(this.currency, this.number.ulp());
	}

	public MonetaryAmount remainder(MonetaryAmount divisor) {
		return new BigDecimalAmount(this.currency,
				this.number.remainder(divisor.asType(BigDecimal.class)));
	}

	public MonetaryAmount remainder(Number divisor) {
		return new BigDecimalAmount(
				this.currency,
				this.number.remainder(BigDecimal.valueOf(divisor.doubleValue())));
	}

	public MonetaryAmount scaleByPowerOfTen(int n) {
		return new BigDecimalAmount(this.currency,
				this.number.scaleByPowerOfTen(n));
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
		// TODO Auto-generated method stub
		return signum() == 1;
	}

	public boolean isPositiveOrZero() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isNegative() {
		return signum() == -1;
	}

	public boolean isNegativeOrZero() {
		// TODO Auto-generated method stub
		return false;
	}

	public MonetaryAmount with(Number amount) {
		return new BigDecimalAmount(this.currency, BigDecimal.valueOf(amount
				.doubleValue()));
	}

	public MonetaryAmount with(AmountAdjuster... adjuster) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return 0;
	}

	public int signum() {
		return this.number.signum();
	}

	public String toEngineeringString() {
		// TODO consider currency or not?
		return this.number.toEngineeringString();
	}

	public String toPlainString() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean lessThan(MonetaryAmount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean lessThan(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean lessThanOrEqualTo(MonetaryAmount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean lessThanOrEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean greaterThan(MonetaryAmount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean greaterThan(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean greaterThanOrEqualTo(MonetaryAmount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean greaterThanOrEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEqualTo(MonetaryAmount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isNotEqualTo(MonetaryAmount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isNotEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	public MonetaryAmount getMajorPart() {
		// TODO Auto-generated method stub
		return null;
	}

	public MonetaryAmount getMinorPart() {
		// TODO Auto-generated method stub
		return null;
	}

	public MonetaryAmount getAdjusted() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<?> getNumberType() {
		return BigDecimal.class;
	}

	public <T> T asType(Class<T> type) {
		if (BigDecimal.class.equals(type)) {
			return (T) this.number;
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
		// TODO Auto-generated method stub
		return null;
	}

	// Static Factory Methods
	/**
	 * Translates a {@code BigDecimal} value and a {@code CurrencyUnit} currency
	 * into a {@code BigDecimalAmount}.
	 * 
	 * @param number
	 *            numeric value of the {@code BigDecimalAmount}.
	 * @param currency
	 *            currency unit of the {@code BigDecimalAmount}.
	 * @return a {@code BigDecimalAmount} combining the numeric value and
	 *         currency unit.
	 */
	public static BigDecimalAmount valueOf(BigDecimal number,
			CurrencyUnit currency) {
		return new BigDecimalAmount(currency, number);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return currency.toString() + ' ' + number;
	}
	
	
}
