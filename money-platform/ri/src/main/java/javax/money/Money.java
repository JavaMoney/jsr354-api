/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Default immutable implementation of {@link MonetaryAmount}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class Money implements MonetaryAmount, Comparable<MonetaryAmount> {

	/** The numeric part of this amount. */
	private final BigDecimal number;

	/** The currency of this amount. */
	private final CurrencyUnit currency;

	private static final MathContext DEFAULT_MATH_CONTEXT = initDefaultMathContext();

	/** tHE DEFAULT {@link MathContext} used by this instance, e.g. on division. */
	private final MathContext mathContext;

	/**
	 * Creates a new instance os {@link Money}.
	 * 
	 * @param currency
	 *            the currency, not null.
	 * @param number
	 *            the amount, not null.
	 */
	public Money(CurrencyUnit currency, Number number) {
		this(currency, number, getDefaultMathContext());
	}

	private static MathContext initDefaultMathContext() {
		// TODO Initialize default, e.g. by system properties, or better:
		// classpath properties!
		return MathContext.DECIMAL64;
	}

	/**
	 * Creates a new instance os {@link Money}.
	 * 
	 * @param currency
	 *            the currency, not null.
	 * @param number
	 *            the amount, not null.
	 */
	public Money(CurrencyUnit currency, Number number, MathContext mathContext) {
		if (currency == null) {
			throw new IllegalArgumentException("Currency is required.");
		}
		if (number == null) {
			throw new IllegalArgumentException("Number is required.");
		}
		if (mathContext == null) {
			throw new IllegalArgumentException("MathContext is required.");
		}
		checkNumber(number);
		this.currency = currency;
		this.mathContext = mathContext;
		if (number instanceof BigDecimal) {
			this.number = (BigDecimal) number;
		} else {
			this.number = new BigDecimal(number.doubleValue(), mathContext);
		}
	}

	/**
	 * Access the default {@link MathContext} used by instances of this class,
	 * if not configured on creation.
	 * 
	 * @return the default {@link MathContext}, never null.
	 */
	public static final MathContext getDefaultMathContext() {
		// TODO the accessor to a public static final constant member variable seems of little use. Why not use constant directly?
		return DEFAULT_MATH_CONTEXT;
	}

	// Static Factory Methods
	/**
	 * Translates a {@code BigDecimal} value and a {@code CurrencyUnit} currency
	 * into a {@code Money}.
	 * 
	 * @param number
	 *            numeric value of the {@code Money}.
	 * @param currency
	 *            currency unit of the {@code Money}.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 */
	public static Money of(CurrencyUnit currency, BigDecimal number) {
		return new Money(currency, number, getDefaultMathContext());
	}

	/**
	 * Translates a {@code BigDecimal} value and a {@code CurrencyUnit} currency
	 * into a {@code Money}.
	 * 
	 * @param number
	 *            numeric value of the {@code Money}.
	 * @param currency
	 *            currency unit of the {@code Money}.
	 * @param mathContext
	 *            the {@link MathContext} to be used.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 */
	public static Money of(CurrencyUnit currency, BigDecimal number,
			MathContext mathContext) {
		return new Money(currency, number, mathContext);
	}

	/**
	 * Static factory method for creating a new instance of {@link Money}.
	 * 
	 * @param currency
	 *            The target currency, not null.
	 * @param number
	 *            The numeric part, not null.
	 * @return A new instance of {@link Money}.
	 */
	public static Money of(CurrencyUnit currency, Number number) {
		// TODO caching
		return new Money(currency, number, getDefaultMathContext());
	}

	/**
	 * Static factory method for creating a new instance of {@link Money}.
	 * 
	 * @param currency
	 *            The target currency, not null.
	 * @param number
	 *            The numeric part, not null.
	 * @return A new instance of {@link Money}.
	 */
	public static Money of(CurrencyUnit currency, Number number,
			MathContext mathContext) {
		// TODO caching
		return new Money(currency, number, mathContext);
	}

	/**
	 * Static factory method for creating a new instance of {@link Money}.
	 * 
	 * @param isoCurrencyCode
	 *            The target currency as ISO currency code.
	 * @param number
	 *            The numeric part, not null.
	 * @return A new instance of {@link Money}.
	 */
	public static Money of(String currencyCode, Number number) {
		return new Money(MoneyCurrency.of(currencyCode), number,
				getDefaultMathContext());
	}

	/**
	 * Static factory method for creating a new instance of {@link Money}.
	 * 
	 * @param isoCurrencyCode
	 *            The target currency as ISO currency code.
	 * @param number
	 *            The numeric part, not null.
	 * @return A new instance of {@link Money}.
	 */
	public static Money of(String currencyCode, Number number,
			MathContext mathContext) {
		return new Money(MoneyCurrency.of(currencyCode), number, mathContext);
	}

/**
	 * Factory method creating a zero instance with the given {@code currency);
	 * @param currency the target currency of the amount being created.
	 * @return
	 */
	public static Money ofZero(CurrencyUnit currency) {
		return new Money(currency, BigDecimal.ZERO, getDefaultMathContext());
	}

/**
	 * Factory method creating a zero instance with the given {@code currency);
	 * @param currency the target currency of the amount being created.
	 * @return
	 */
	public static Money ofZero(String currency) {
		return ofZero(MoneyCurrency.of(currency));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Money other = (Money) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	/*
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(MonetaryAmount o) {
		checkAmountParameter(o);
		int compare = -1;
		if (this.currency.equals(o.getCurrency())) {
			compare = this.number.compareTo(o.asType(BigDecimal.class));
		} else {
			compare = this.currency.getNamespace().compareTo(
					o.getCurrency().getNamespace());
			if (compare == 0) {
				compare = this.currency.getCurrencyCode().compareTo(
						o.getCurrency().getCurrencyCode());
			}
		}
		return compare;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getCurrency()
	 */
	public CurrencyUnit getCurrency() {
		return currency;
	}

	/**
	 * Access the {@link MathContext} used by this instance.
	 * 
	 * @return the {@link MathContext} used, never null.
	 */
	public MathContext getMathContext() {
		return this.mathContext;
	}

	/**
	 * Allows to change the {@link MathContext}. The context will used, on
	 * subsequent operation, where feasible and also propagated to child results
	 * of arithmetic calculations.
	 * 
	 * @param mathContext
	 *            The new {@link MathContext}, not null.
	 * @return a new {@link Money} instance, with the new {@link MathContext}.
	 */
	public Money setMathContext(MathContext mathContext) {
		if (mathContext == null) {
			throw new IllegalArgumentException("MathContext required.");
		}
		return new Money(this.currency, this.number, mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#abs()
	 */
	public Money abs() {
		if (this.isPositiveOrZero()) {
			return this;
		}
		return this.negate();
	}

	// Arithmetic Operations

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#add(javax.money.MonetaryAmount)
	 */
	public Money add(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return new Money(this.currency, this.number.add(
				amount.asType(BigDecimal.class), this.mathContext),
				this.mathContext);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#add(Number)
	 */
	public Money add(Number amount) {
		checkNumber(amount);
		return new Money(this.currency, this.number.add(
				getBigDecimal(amount), this.mathContext),
				this.mathContext);
	}

	private BigDecimal getBigDecimal(Number num) {
		if (num instanceof BigDecimal) {
			return (BigDecimal) num;
		}
		if (num instanceof Long) {
			return BigDecimal.valueOf(num.longValue());
		}
		return BigDecimal.valueOf(num.doubleValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divide(javax.money.MonetaryAmount)
	 */
	public Money divide(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		BigDecimal dec = this.number.divide(divisor.asType(BigDecimal.class),
				this.mathContext);
		return new Money(this.currency, dec, this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divide(javax.money.MonetaryAmount)
	 */
	public Money divide(Number divisor) {
		BigDecimal dec = this.number.divide(getBigDecimal(divisor),
				this.mathContext);
		return new Money(this.currency, dec, this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#divideAndRemainder(javax.money.MonetaryAmount)
	 */
	public Money[] divideAndRemainder(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		BigDecimal[] dec = this.number.divideAndRemainder(
				divisor.asType(BigDecimal.class), this.mathContext);
		return new Money[] {
				new Money(this.currency, dec[0], this.mathContext),
				new Money(this.currency, dec[1], this.mathContext) };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#divideAndRemainder(javax.money.MonetaryAmount)
	 */
	public Money[] divideAndRemainder(Number divisor) {
		BigDecimal[] dec = this.number.divideAndRemainder(
				getBigDecimal(divisor), this.mathContext);
		return new Money[] {
				new Money(this.currency, dec[0], this.mathContext),
				new Money(this.currency, dec[1], this.mathContext) };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#divideToIntegralValue(javax.money.MonetaryAmount
	 * )
	 */
	public Money divideToIntegralValue(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		BigDecimal dec = this.number.divideToIntegralValue(
				divisor.asType(BigDecimal.class), this.mathContext);
		return new Money(this.currency, dec, this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#divideToIntegralValue(Number) )D
	 */
	public Money divideToIntegralValue(Number divisor) {
		BigDecimal dec = this.number.divideToIntegralValue(
				getBigDecimal(divisor), this.mathContext);
		return new Money(this.currency, dec, this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#multiply(javax.money.MonetaryAmount)
	 */
	public Money multiply(MonetaryAmount multiplicand) {
		checkAmountParameter(multiplicand);
		BigDecimal dec = this.number.multiply(
				multiplicand.asType(BigDecimal.class), this.mathContext);
		return new Money(this.currency, dec, this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#multiply(Number)
	 */
	public Money multiply(Number multiplicand) {
		BigDecimal dec = this.number.multiply(getBigDecimal(multiplicand),
				this.mathContext);
		return new Money(this.currency, dec, this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#negate()
	 */
	public Money negate() {
		return new Money(this.currency, this.number.negate(this.mathContext),
				this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#plus()
	 */
	public Money plus() {
		return new Money(this.currency, this.number.plus(this.mathContext),
				this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#subtract(javax.money.MonetaryAmount)
	 */
	public Money subtract(MonetaryAmount subtrahend) {
		checkAmountParameter(subtrahend);
		return new Money(this.currency, this.number.subtract(
				subtrahend.asType(BigDecimal.class), this.mathContext),
				this.mathContext);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#subtract(Number)
	 */
	public Money subtract(Number subtrahend) {
		checkNumber(subtrahend);
		return new Money(this.currency, this.number.subtract(
				getBigDecimal(subtrahend), this.mathContext),
				this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#pow(int)
	 */
	public Money pow(int n) {
		return new Money(this.currency, this.number.pow(n, this.mathContext),
				this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#ulp()
	 */
	public Money ulp() {
		return new Money(this.currency, this.number.ulp());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#remainder(javax.money.MonetaryAmount)
	 */
	public Money remainder(MonetaryAmount divisor) {
		checkAmountParameter(divisor);
		return new Money(this.currency, this.number.remainder(
				divisor.asType(BigDecimal.class), this.mathContext),
				this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#remainder(Number)
	 */
	public Money remainder(Number divisor) {
		return new Money(this.currency, this.number.remainder(
				getBigDecimal(divisor), this.mathContext), this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#scaleByPowerOfTen(int)
	 */
	public Money scaleByPowerOfTen(int n) {
		return new Money(this.currency, this.number.scaleByPowerOfTen(n),
				this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMajorLong()
	 */
	public long getMajorLong() {
		return this.number.setScale(0, RoundingMode.DOWN).longValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMajorInt()
	 */
	public int getMajorInt() {
		return this.number.setScale(0, RoundingMode.DOWN).intValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMinorLong()
	 */
	public long getMinorLong() {
		return this.number.movePointRight(this.number.precision())
				.longValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMinorInt()
	 */
	public int getMinorInt() {
		return this.number.movePointRight(this.number.precision())
				.intValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isZero()
	 */
	public boolean isZero() {
		return this.number.signum() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isPositive()
	 */
	public boolean isPositive() {
		return signum() == 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isPositiveOrZero()
	 */
	public boolean isPositiveOrZero() {
		return signum() >= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNegative()
	 */
	public boolean isNegative() {
		return signum() == -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNegativeOrZero()
	 */
	public boolean isNegativeOrZero() {
		return signum() <= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#with(java.lang.Number)
	 */
	public Money from(Number amount) {
		checkNumber(amount);
		return new Money(this.currency, getBigDecimal(amount), this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getScale()
	 */
	public int getScale() {
		return this.number.scale();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getPrecision()
	 */
	public int getPrecision() {
		return this.number.precision();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#intValue()
	 */
	public int intValue() {
		return this.number.intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#intValueExact()
	 */
	public int intValueExact() {
		return this.number.intValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#longValue()
	 */
	public long longValue() {
		return this.number.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#longValueExact()
	 */
	public long longValueExact() {
		return this.number.longValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#floatValue()
	 */
	public float floatValue() {
		return this.number.floatValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#doubleValue()
	 */
	public double doubleValue() {
		return this.number.doubleValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#byteValue()
	 */
	public byte byteValue() {
		return this.number.byteValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#shortValue()
	 */
	public short shortValue() {
		return this.number.shortValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#shortValueExact()
	 */
	public short shortValueExact() {
		return number.shortValueExact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#signum()
	 */

	public int signum() {
		return this.number.signum();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#toEngineeringString()
	 */
	public String toEngineeringString() {
		return this.currency.toString() + ' '
				+ this.number.toEngineeringString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#toPlainString()
	 */
	public String toPlainString() {
		return this.currency.toString() + ' ' + this.number.toPlainString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#lessThan(javax.money.MonetaryAmount)
	 */
	public boolean isLessThan(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) < 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#lessThanOrEqualTo(javax.money.MonetaryAmount)
	 */
	public boolean isLessThanOrEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) <= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#greaterThan(javax.money.MonetaryAmount)
	 */
	public boolean isGreaterThan(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.MonetaryAmount#greaterThanOrEqualTo(javax.money.MonetaryAmount
	 * ) #see
	 */
	public boolean isGreaterThanOrEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) >= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isEqualTo(javax.money.MonetaryAmount)
	 */
	public boolean isEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#isNotEqualTo(javax.money.MonetaryAmount)
	 */
	public boolean isNotEqualTo(MonetaryAmount amount) {
		checkAmountParameter(amount);
		return number.compareTo(amount.asType(BigDecimal.class)) != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMajorPart()
	 */
	public Money getMajorPart() {
		return new Money(this.currency, getMajorLong(), this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getMinorPart()
	 */
	public Money getMinorPart() {
		return new Money(this.currency, getMinorLong(), this.mathContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#getNumberType()
	 */
	public Class<?> getNumberType() {
		return BigDecimal.class;
	}

	/*
	 * }(non-Javadoc)
	 * @see javax.money.MonetaryAmount#adjust(javax.money.AmountAdjuster)
	 */
	@Override
	public MonetaryAmount with(MonetaryAdjuster adjuster) {
		return new Money(this.currency, adjuster.adjust(this).asType(BigDecimal.class));
	}
	
	/*
	 * @see javax.money.MonetaryAmount#asType(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T> T asType(Class<T> type) {
		if (BigDecimal.class.equals(type)) {
			return (T) this.number;
		}
		if (Number.class.equals(type)) {
			final T asType = (T) this.number;
			return asType;
		}
		if (Double.class.equals(type)) {
			return (T) Double.valueOf(this.number.doubleValue());
		}
		if (Float.class.equals(type)) {
			return (T) Float.valueOf(this.number.floatValue());
		}
		if (Long.class.equals(type)) {
			return (T) Long.valueOf(this.number.longValue());
		}
		if (Integer.class.equals(type)) {
			return (T) Integer.valueOf(this.number.intValue());
		}
		if (Short.class.equals(type)) {
			return (T) Short.valueOf(this.number.shortValue());
		}
		if (Byte.class.equals(type)) {
			return (T) Byte.valueOf(this.number.byteValue());
		}
		if (BigInteger.class.equals(type)) {
			return (T) this.number.toBigInteger();
		}
		throw new IllegalArgumentException("Unsupported representation type: "
				+ type);
	}

	/*
	 * }(non-Javadoc)
	 * 
	 * @see javax.money.MonetaryAmount#asType(java.lang.Class,
	 * javax.money.Rounding)
	 */
	public <T> T asType(Class<T> type, MonetaryAdjuster adjuster) {
		MonetaryAmount amount = adjuster.adjust(this);
		return amount.asType(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return currency.toString() + ' ' + number;
	}

	// Internal helper methods

	/**
	 * Internal method to check for correct number parameter.
	 * 
	 * @param number
	 * @throws IllegalArgumentException
	 *             If the number is null
	 */
	private void checkNumber(Number number) {
		if (number == null) {
			throw new IllegalArgumentException("Number is required.");
		}
	}

	/**
	 * Method to check if a currency is compatible with this amount instance.
	 * 
	 * @param amount
	 *            The monetary amount to be compared to, never null.
	 * @throws IllegalArgumentException
	 *             If the amount is null, or the amount's currency is not
	 *             compatible (same {@link CurrencyUnit#getNamespace()} and same
	 *             {@link CurrencyUnit#getCurrencyCode()}).
	 */
	private void checkAmountParameter(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount must not be null.");
		}
		if (!(this.currency.getNamespace().equals(currency.getNamespace()) || !(this.currency
				.getCurrencyCode().equals(currency.getCurrencyCode())))) {
			throw new IllegalArgumentException("Currency mismatch: "
					+ this.currency + '/' + currency);
		}
	}

	/**
	 * Allows to check, if the currency of the two amounts are the same. This
	 * means that corresponding currency's namespace and code must match.
	 * 
	 * @param amount
	 *            The amount to comapre to, not {@code null}.
	 * @return true, if the {@link CurrencyUnit} of this instance has the same
	 *         namespace and code.
	 */
	@Override
	public boolean hasSameCurrencyAs(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount must not be null.");
		}
		return this.currency.getNamespace().equals(
				amount.getCurrency().getNamespace())
				&& this.currency.getCurrencyCode().equals(
						amount.getCurrency().getCurrencyCode());
	}

}
