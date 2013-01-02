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
		this(number, currency, (AmountAdjuster[])null);
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

	public CurrencyUnit getCurrency() {
		return currency;
	}

	public Amount abs() {
		if (this.isPositiveOrZero()) {
			return this;
		}
		return this.negate();
	}

	public Amount min(Amount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount rewuired.");
		}
		if (amount.lessThan(this)) {
			return amount;
		}
		return this;
	}

	public Amount max(Amount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount rewuired.");
		}
		if (amount.greaterThan(this)) {
			return amount;
		}
		return this;
	}

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

	public Amount add(Number number) {
		BigDecimal dec = this.number.add(BigDecimal.valueOf(number
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	public Amount divide(Amount divisor) {
		BigDecimal dec = this.number.divide(BigDecimal.valueOf(number
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	public Amount divide(Number divisor) {
		BigDecimal dec = this.number.divide(BigDecimal.valueOf(divisor
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	public Amount divide(Amount divisor, AmountAdjuster... adjusters) {
		BigDecimal dec = this.number.divide(BigDecimal.valueOf(divisor
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency, adjusters);
	}

	public Amount divide(Number divisor, AmountAdjuster... adjusters) {
		BigDecimal dec = this.number.divide(BigDecimal.valueOf(divisor
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency, adjusters);
	}

	public Amount[] divideAndRemainder(Amount divisor) {
		BigDecimal[] dec = this.number.divideAndRemainder(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new Amount[] {
				new BigDecimalAmount(dec[0], this.currency, adjusters),
				new BigDecimalAmount(dec[1], this.currency, adjusters) };
	}

	public Amount[] divideAndRemainder(Number divisor) {
		BigDecimal[] dec = this.number.divideAndRemainder(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new Amount[] {
				new BigDecimalAmount(dec[0], this.currency, adjusters),
				new BigDecimalAmount(dec[1], this.currency, adjusters) };
	}

	public Amount divideToIntegralValue(Amount divisor) {
		BigDecimal dec = this.number.divideToIntegralValue(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	public Amount divideToIntegralValue(Number divisor) {
		BigDecimal dec = this.number.divideToIntegralValue(BigDecimal
				.valueOf(divisor.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	public Amount multiply(Amount multiplicand, MathContext ctx) {
		BigDecimal dec = this.number.multiply(
				BigDecimal.valueOf(multiplicand.doubleValue()), ctx);
		return new BigDecimalAmount(dec, this.currency);
	}

	public Amount multiply(Number multiplicand, MathContext ctx) {
		BigDecimal dec = this.number.multiply(
				BigDecimal.valueOf(multiplicand.doubleValue()), ctx);
		return new BigDecimalAmount(dec, this.currency);
	}

	public Amount multiply(Amount multiplicand) {
		BigDecimal dec = this.number.multiply(BigDecimal.valueOf(multiplicand
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	public Amount multiply(Number multiplicand) {
		BigDecimal dec = this.number.multiply(BigDecimal.valueOf(multiplicand
				.doubleValue()));
		return new BigDecimalAmount(dec, this.currency);
	}

	public Amount negate() {
		return new BigDecimalAmount(this.number.negate(), this.currency);
	}

	public Amount plus() {
		return new BigDecimalAmount(this.number.plus(), this.currency);
	}

	public Amount subtract(Amount subtrahend) {
		return new BigDecimalAmount(this.number.subtract(subtrahend
				.asType(BigDecimal.class)), this.currency);
	}

	public Amount subtract(Number subtrahend) {
		return new BigDecimalAmount(this.number.subtract(BigDecimal
				.valueOf(subtrahend.doubleValue())), this.currency);
	}

	public Amount pow(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	public Amount ulp() {
		// TODO Auto-generated method stub
		return null;
	}

	public Amount remainder(Amount divisor) {
		// TODO Auto-generated method stub
		return null;
	}

	public Amount remainder(Number divisor) {
		// TODO Auto-generated method stub
		return null;
	}

	public Amount scaleByPowerOfTen(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	public Amount with(CurrencyUnit currency) {
		// TODO Auto-generated method stub
		return null;
	}

	public Amount with(CurrencyUnit currency, AmountAdjuster... adjusters) {
		// TODO Auto-generated method stub
		return null;
	}

	public Amount getMajor() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getMajorLong() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMajorInt() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Amount getMinor() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getMinorLong() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMinorInt() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMinorPart() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isZero() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPositive() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPositiveOrZero() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isNegative() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isNegativeOrZero() {
		// TODO Auto-generated method stub
		return false;
	}

	public Amount with(Number amount) {
		// TODO Auto-generated method stub
		return null;
	}

	public Amount with(AmountAdjuster... adjuster) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getScale() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getDecimals() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getPrecision() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int intValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int intValueExact() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long longValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long longValueExact() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float floatValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double doubleValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	public byte byteValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	public short shortValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	public short shortValueExact() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int signum() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String toEngineeringString() {
		// TODO Auto-generated method stub
		return null;
	}

	public String toPlainString() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean lessThan(Amount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean lessThan(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean lessThanOrEqualTo(Amount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean lessThanOrEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean greaterThan(Amount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean greaterThan(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean greaterThanOrEqualTo(Amount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean greaterThanOrEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEqualTo(Amount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isNotEqualTo(Amount amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isNotEqualTo(Number number) {
		// TODO Auto-generated method stub
		return false;
	}

	public Amount getMajorPart() {
		// TODO Auto-generated method stub
		return null;
	}

	public Amount getAdjusted() {
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
		Amount amount = this;
		for (int i = 0; i < adjustment.length; i++) {
			amount = adjustment[i].adjust(amount);
		}
		return amount.asType(type);
	}

	public <T> T asType(Class<T> type, boolean performRounding) {
		// TODO Auto-generated method stub
		return null;
	}


	public Amount[] divideAndSeparate(Number divisor,
			boolean addDifferenceToLastValue) {
		// TODO Auto-generated method stub
		return null;
	}

}
