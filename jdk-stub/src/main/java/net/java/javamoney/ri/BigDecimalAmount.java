/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch).
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
 */
package net.java.javamoney.ri;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.money.AmountAdjuster;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

public class BigDecimalAmount implements MonetaryAmount {

	private BigDecimal number;
	private CurrencyUnit currency;

	public BigDecimalAmount(BigDecimal number, CurrencyUnit currency) {
		if (currency == null) {
			throw new IllegalArgumentException("Currency is required.");
		}
		if(number==null){
			throw new IllegalArgumentException("Number is required.");
		}
		this.currency = currency;
		this.number = number;
	}

	public int compareTo(MonetaryAmount o) {
		int compare = this.currency.compareTo(o.getCurrency());
		if (compare == 0) {
			compare = this.number.compareTo(o.asType(BigDecimal.class));
		}
		return compare;
	}

	@Override
	public CurrencyUnit getCurrency() {
		return currency;
	}

	@Override
	public MonetaryAmount abs() {
		if (this.isPositiveOrZero()) {
			return this;
		}
		return this.negate();
	}

	@Override
	public MonetaryAmount min(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount rewuired.");
		}
		if (amount.lessThan(this)) {
			return amount;
		}
		return this;
	}

	@Override
	public MonetaryAmount max(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount rewuired.");
		}
		if (amount.greaterThan(this)) {
			return amount;
		}
		return this;
	}

	@Override
	public MonetaryAmount add(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount must not be null.");
		}
		if (!this.currency.equals(amount.getCurrency())) {
			throw new IllegalArgumentException("Currency mismatch: required : "
					+ this.currency + ", but was " + amount.getCurrency());
		}
		return new BigDecimalAmount(this.number.add(amount
				.asType(BigDecimal.class)), this.currency);
	}

	@Override
	public MonetaryAmount add(Number number) {
		BigDecimal dec = this.number.add(BigDecimal.valueOf(number
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public MonetaryAmount divide(MonetaryAmount divisor) {
		BigDecimal dec = this.number.divide(divisor.asType(BigDecimal.class));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public MonetaryAmount divide(Number divisor) {
		BigDecimal dec = this.number.divide(BigDecimal.valueOf(divisor
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public MonetaryAmount[] divideAndRemainder(MonetaryAmount divisor) {
		BigDecimal[] dec = this.number.divideAndRemainder(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new MonetaryAmount[] {
				new BigDecimalAmount(dec[0], this.currency),
				new BigDecimalAmount(dec[1], this.currency) };
	}

	@Override
	public MonetaryAmount[] divideAndRemainder(Number divisor) {
		BigDecimal[] dec = this.number.divideAndRemainder(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new MonetaryAmount[] {
				new BigDecimalAmount(dec[0], this.currency),
				new BigDecimalAmount(dec[1], this.currency) };
	}

	@Override
	public MonetaryAmount divideToIntegralValue(MonetaryAmount divisor) {
		BigDecimal dec = this.number.divideToIntegralValue(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public MonetaryAmount divideToIntegralValue(Number divisor) {
		BigDecimal dec = this.number.divideToIntegralValue(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public MonetaryAmount multiply(MonetaryAmount multiplicand) {
		BigDecimal dec = this.number.multiply(
				multiplicand.asType(BigDecimal.class));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public MonetaryAmount multiply(Number multiplicand) {
		BigDecimal dec = this.number.multiply(BigDecimal.valueOf(multiplicand
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public MonetaryAmount negate() {
		return new BigDecimalAmount(this.number.negate(), this.currency);
	}

	@Override
	public MonetaryAmount plus() {
		return new BigDecimalAmount(this.number.plus(), this.currency);
	}

	@Override
	public MonetaryAmount subtract(MonetaryAmount subtrahend) {
		return new BigDecimalAmount(this.number.subtract(subtrahend
				.asType(BigDecimal.class)), this.currency);
	}

	@Override
	public MonetaryAmount subtract(Number subtrahend) {
		return new BigDecimalAmount(this.number.subtract(BigDecimal
				.valueOf(subtrahend.doubleValue())), this.currency);
	}

	@Override
	public MonetaryAmount pow(int n) {
		return new BigDecimalAmount(this.number.pow(n), this.currency);
	}

	@Override
	public MonetaryAmount ulp() {
		return new BigDecimalAmount(this.number.ulp(), this.currency);
	}

	@Override
	public MonetaryAmount remainder(MonetaryAmount divisor) {
		return new BigDecimalAmount(this.number.remainder(divisor
				.asType(BigDecimal.class)), this.currency);
	}

	@Override
	public MonetaryAmount remainder(Number divisor) {
		return new BigDecimalAmount(this.number.remainder(BigDecimal
				.valueOf(divisor.doubleValue())), this.currency);
	}

	@Override
	public MonetaryAmount scaleByPowerOfTen(int n) {
		return new BigDecimalAmount(this.number.scaleByPowerOfTen(n),
				this.currency);
	}

	@Override
	public MonetaryAmount with(CurrencyUnit currency) {
		return new BigDecimalAmount(this.number, currency);
	}

	@Override
	public MonetaryAmount with(CurrencyUnit currency, AmountAdjuster... adjusters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getMajorLong() {
		return this.number.setScale(0, RoundingMode.DOWN).longValueExact();
	}

	@Override
	public int getMajorInt() {
		return this.number.setScale(0, RoundingMode.DOWN).intValueExact();
	}

	@Override
	public long getMinorLong() {
		return this.number.movePointRight(this.number.precision()).longValueExact();
	}

	@Override
	public int getMinorInt() {
		return this.number.movePointRight(this.number.precision()).intValueExact();
	}

	@Override
	public boolean isZero() {
		return this.number.signum()==0;
	}

	@Override
	public boolean isPositive() {
		// TODO Auto-generated method stub
		return signum() == 1;
	}

	@Override
	public boolean isPositiveOrZero() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNegative() {
		return signum() == -1;
	}

	@Override
	public boolean isNegativeOrZero() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MonetaryAmount with(Number amount) {
		return new BigDecimalAmount(BigDecimal.valueOf(amount.doubleValue()),
				currency);
	}

	@Override
	public MonetaryAmount with(AmountAdjuster... adjuster) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScale() {
		return this.number.scale();
	}

	@Override
	public int getPrecision() {
		return this.number.precision();
	}

	@Override
	public int intValue() {
		return this.number.intValue();
	}

	@Override
	public int intValueExact() {
		return this.number.intValueExact();
	}

	@Override
	public long longValue() {
		return this.number.longValue();
	}

	@Override
	public long longValueExact() {
		return this.number.longValueExact();
	}

	@Override
	public float floatValue() {
		return this.number.floatValue();
	}

	@Override
	public double doubleValue() {
		return this.number.doubleValue();
	}

	@Override
	public byte byteValue() {
		return this.number.byteValue();
	}

	@Override
	public short shortValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short shortValueExact() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int signum() {
		return this.number.signum();
	}

	@Override
	public String toEngineeringString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toPlainString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean lessThan(MonetaryAmount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lessThan(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lessThanOrEqualTo(MonetaryAmount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lessThanOrEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean greaterThan(MonetaryAmount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean greaterThan(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean greaterThanOrEqualTo(MonetaryAmount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean greaterThanOrEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqualTo(MonetaryAmount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNotEqualTo(MonetaryAmount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNotEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MonetaryAmount getMajorPart() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public MonetaryAmount getMinorPart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount getAdjusted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getNumberType() {
		return BigDecimal.class;
	}

	@Override
	public <T> T asType(Class<T> type) {
		if (BigDecimal.class.equals(type)) {
			return (T) this.number;
		}
		// TODO add Number types...
		throw new IllegalArgumentException("Unsupported representation type: "
				+ type);
	}

	@Override
	public <T> T asType(Class<T> type, AmountAdjuster... adjustment) {
		MonetaryAmount amount = this;
		for (int i = 0; i < adjustment.length; i++) {
			amount = adjustment[i].adjust(amount);
		}
		return amount.asType(type);
	}

	@Override
	public <T> T asType(Class<T> type, boolean performRounding) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount[] divideAndSeparate(Number divisor,
			boolean addDifferenceToLastValue) {
		// TODO Auto-generated method stub
		return null;
	}

}
