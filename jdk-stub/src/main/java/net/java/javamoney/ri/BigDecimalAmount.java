package net.java.javamoney.ri;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.money.Amount;
import javax.money.AmountAdjuster;
import javax.money.CurrencyUnit;

public class BigDecimalAmount implements Amount {

	private BigDecimal number;
	private CurrencyUnit currency;
	private AmountAdjuster[] adjusters;

	public BigDecimalAmount(BigDecimal number, CurrencyUnit currency) {
		this(number, currency, (AmountAdjuster[]) null);
	}

	public BigDecimalAmount(BigDecimal number, CurrencyUnit currency,
			AmountAdjuster... adjusters) {
		if (currency == null) {
			throw new IllegalArgumentException("Currency is required.");
		}
		this.currency = currency;
		this.adjusters = adjusters;
		this.number = number;
	}

	public int compareTo(Amount o) {
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
	public Amount abs() {
		if (this.isPositiveOrZero()) {
			return this;
		}
		return this.negate();
	}

	@Override
	public Amount min(Amount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount rewuired.");
		}
		if (amount.lessThan(this)) {
			return amount;
		}
		return this;
	}

	@Override
	public Amount max(Amount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount rewuired.");
		}
		if (amount.greaterThan(this)) {
			return amount;
		}
		return this;
	}

	@Override
	public Amount add(Amount amount) {
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
	public Amount add(Number number) {
		BigDecimal dec = this.number.add(BigDecimal.valueOf(number
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public Amount divide(Amount divisor) {
		BigDecimal dec = this.number.divide(BigDecimal.valueOf(number
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public Amount divide(Number divisor) {
		BigDecimal dec = this.number.divide(BigDecimal.valueOf(divisor
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public Amount divide(Amount divisor, AmountAdjuster... adjusters) {
		BigDecimal dec = this.number.divide(BigDecimal.valueOf(divisor
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency, adjusters);
	}

	@Override
	public Amount divide(Number divisor, AmountAdjuster... adjusters) {
		BigDecimal dec = this.number.divide(BigDecimal.valueOf(divisor
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency, adjusters);
	}

	@Override
	public Amount[] divideAndRemainder(Amount divisor) {
		BigDecimal[] dec = this.number.divideAndRemainder(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new Amount[] {
				new BigDecimalAmount(dec[0], this.currency, adjusters),
				new BigDecimalAmount(dec[1], this.currency, adjusters) };
	}

	@Override
	public Amount[] divideAndRemainder(Number divisor) {
		BigDecimal[] dec = this.number.divideAndRemainder(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new Amount[] {
				new BigDecimalAmount(dec[0], this.currency, adjusters),
				new BigDecimalAmount(dec[1], this.currency, adjusters) };
	}

	@Override
	public Amount divideToIntegralValue(Amount divisor) {
		BigDecimal dec = this.number.divideToIntegralValue(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public Amount divideToIntegralValue(Number divisor) {
		BigDecimal dec = this.number.divideToIntegralValue(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public Amount multiply(Amount multiplicand, MathContext ctx) {
		BigDecimal dec = this.number.multiply(
				BigDecimal.valueOf(multiplicand.doubleValue()), ctx);
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public Amount multiply(Number multiplicand, MathContext ctx) {
		BigDecimal dec = this.number.multiply(
				BigDecimal.valueOf(multiplicand.doubleValue()), ctx);
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public Amount multiply(Amount multiplicand) {
		BigDecimal dec = this.number.multiply(BigDecimal.valueOf(multiplicand
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public Amount multiply(Number multiplicand) {
		BigDecimal dec = this.number.multiply(BigDecimal.valueOf(multiplicand
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	@Override
	public Amount negate() {
		return new BigDecimalAmount(this.number.negate(), this.currency);
	}

	@Override
	public Amount plus() {
		return new BigDecimalAmount(this.number.plus(), this.currency);
	}

	@Override
	public Amount subtract(Amount subtrahend) {
		return new BigDecimalAmount(this.number.subtract(subtrahend
				.asType(BigDecimal.class)), this.currency);
	}

	@Override
	public Amount subtract(Number subtrahend) {
		return new BigDecimalAmount(this.number.subtract(BigDecimal
				.valueOf(subtrahend.doubleValue())), this.currency);
	}

	@Override
	public Amount pow(int n) {
		return new BigDecimalAmount(this.number.pow(n), this.currency);
	}

	@Override
	public Amount ulp() {
		return new BigDecimalAmount(this.number.ulp(), this.currency);
	}

	@Override
	public Amount remainder(Amount divisor) {
		return new BigDecimalAmount(this.number.remainder(divisor
				.asType(BigDecimal.class)), this.currency);
	}

	@Override
	public Amount remainder(Number divisor) {
		return new BigDecimalAmount(this.number.remainder(BigDecimal
				.valueOf(divisor.doubleValue())), this.currency);
	}

	@Override
	public Amount scaleByPowerOfTen(int n) {
		return new BigDecimalAmount(this.number.scaleByPowerOfTen(n),
				this.currency);
	}

	@Override
	public Amount with(CurrencyUnit currency) {
		return new BigDecimalAmount(this.number, currency);
	}

	@Override
	public Amount with(CurrencyUnit currency, AmountAdjuster... adjusters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getMajorLong() {
		return this.number.longValue();
	}

	@Override
	public int getMajorInt() {
		return this.number.intValue();
	}

	@Override
	public long getMinorLong() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinorInt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isZero() {
		// TODO Auto-generated method stub
		return false;
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
	public Amount with(Number amount) {
		return new BigDecimalAmount(BigDecimal.valueOf(amount.doubleValue()),
				currency);
	}

	@Override
	public Amount with(AmountAdjuster... adjuster) {
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
	public boolean lessThan(Amount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lessThan(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lessThanOrEqualTo(Amount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lessThanOrEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean greaterThan(Amount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean greaterThan(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean greaterThanOrEqualTo(Amount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean greaterThanOrEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqualTo(Amount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNotEqualTo(Amount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNotEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Amount getMajorPart() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Amount getMinorPart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Amount getAdjusted() {
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
		Amount amount = this;
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
	public Amount[] divideAndSeparate(Number divisor,
			boolean addDifferenceToLastValue) {
		// TODO Auto-generated method stub
		return null;
	}

}
